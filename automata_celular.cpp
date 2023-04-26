/**Archivo que describe la clase automata celular
 * 
 * 
*/
#include "celula.cpp"
#include <cstdio>
#include <cstdlib>
#include <ctime>
using namespace std;

class automata_celular
{
private:
    int _L; //número de celdas => L*L
    short int _RA; //número de células infectadas tipo A necesarias para infectar a una célula sana
    short int _RB; //número de células infectadas tipo B necesarias para infectar a una célula sana
    celula ** matriz_celulas; //la representación de las células en la matriz L*L
    int _t; //número de "time steps" necesarios para que una célula infectada tipo A se transforme en una célula infectada tipo B
    double _prepl; //probabilidad de que una célula muerta sea reemplazada por una célula sana
    double _pinfec; //probabilidad de que una célula muerta sea reemplazada por una célula infectada
    double _pVIH; //proporcion de celulas infectadas en la matriz de celulas sanas
public:
    automata_celular(){}

    automata_celular(int L, short ra, short rb, int t, double p_infec, double p_vih){
        this->_L = L;
        this->_RA = ra;
        this->_RB = rb;
        this->_t = t;
        this->_pinfec = p_infec;
        this->_prepl = 1 - p_infec;
        this->_pVIH = p_vih;
        this->matriz_celulas = new celula*[L]; //se inicializa la matriz de células
        for (int i = 0; i < L; i++){
            this->matriz_celulas[i] = new celula[L];
            for (int j = 0; j < L; j++){
                this->matriz_celulas[i][j].set_estado(celula::estado_celula_sana);
                this->matriz_celulas[i][j].set_periodos(t);
            }
        }
        //se colocan las primeras celulas infectadas
        int total_celulas_infectadas = (L * L * p_vih) / 1; //funcion piso improvisada
        while (total_celulas_infectadas > 0){
            matriz_celulas[rand() % L][rand() % L].set_estado(celula::estado_celula_infentada_A);
            total_celulas_infectadas-- ;
        }
    }

    //este método ocurre cada "time step"
    void actualizar_automata(){
        int iterador1, iterador2;
        //primero se calculan los valores futuros
        for ( iterador1 = 0; iterador1 < this->_L; iterador1++)
            for ( iterador2 = 0; iterador2 < this->_L; iterador2++)
                actualizar_celula(&this->matriz_celulas[iterador1][iterador2], iterador1, iterador2);    
        //luego, se actualizan los valores
        for ( iterador1 = 0; iterador1 < this->_L; iterador1++)
            for ( iterador2 = 0; iterador2 < this->_L; iterador2++)
                matriz_celulas[iterador1][iterador2].set_estado(matriz_celulas[iterador1][iterador2].get_estado_futuro());
        
    }

    //analizar vecindad de Moore
    void analizar_vecindad(int pos_x, int pos_y, short int *celulas_infectadas_adyacentes_tipo_A, short int *celulas_infectadas_adyacentes_tipo_B) {
        // Iterar sobre las 8 celdas vecinas
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Evitar la celda central (0, 0)
                if (dx == 0 && dy == 0) continue;

                int vecino_x = (pos_x + dx + _L) % _L;
                int vecino_y = (pos_y + dy + _L) % _L;

                // No es necesario verificar si esta dentro de la matriz ya que con el modulo funciona como un toroide
                celula &vecino = matriz_celulas[vecino_x][vecino_y];

                if (vecino.get_estado() == celula::estado_celula_infentada_A) {
                    *celulas_infectadas_adyacentes_tipo_A++;
                } else if (vecino.get_estado() == celula::estado_celula_infectada_B) {
                    *celulas_infectadas_adyacentes_tipo_B++;
                }
            }
        }
    }

    void actualizar_celula(celula *celula_a_actualizar, int pos_x, int pos_y){
        celula_a_actualizar->set_estado_futuro(celula_a_actualizar->get_estado()); //se plantea que, en principio, no cambia de estado
        short int celulas_infectadas_adyacentes_tipo_A = 0;
        short int celulas_infectadas_adyacentes_tipo_B = 0;
        analizar_vecindad(pos_x, pos_y, &celulas_infectadas_adyacentes_tipo_A, &celulas_infectadas_adyacentes_tipo_B);

        switch (celula_a_actualizar->get_estado())
        {
            case celula::estado_celula_sana:
            {
                // Aplicar las reglas de cambio de estado
                if (celulas_infectadas_adyacentes_tipo_A >= _RA || celulas_infectadas_adyacentes_tipo_B >= _RB) {
                    celula_a_actualizar->set_estado_futuro(celula::estado_celula_infentada_A);
                }
                break;
            }
            case celula::estado_celula_infentada_A :
            {
                //se revisa si pasaron los 't' periodos o no
                if(celula_a_actualizar->get_periodo() > 0)
                    celula_a_actualizar->paso_periodo();
                else
                    celula_a_actualizar->set_estado_futuro(celula::estado_celula_infectada_B);
                break;
            }
            case celula::estado_celula_infectada_B :
            {
                celula_a_actualizar->set_estado_futuro(celula::estado_celula_muerta);
                break;
            }
            case celula::estado_celula_muerta:
            {
                // Generar un número aleatorio en el rango [0, 1]
                double random_prob = static_cast<double>(rand()) / static_cast<double>(RAND_MAX);

                // Comparar el número aleatorio con las probabilidades
                if (random_prob <= _pinfec) {
                    celula_a_actualizar->set_estado_futuro(celula::estado_celula_infentada_A); // Reemplazar por una célula infectada tipo A
                } else if (random_prob <= (_pinfec + _prepl)) {
                    celula_a_actualizar->set_estado_futuro(celula::estado_celula_sana); // Reemplazar por una célula sana
                }
                break;
            }
            default:
                //"Si llegamos aquí, hubo un error en las estados de una célula"<<endl;
                break;
        }
    }

    //faltan getters y setters    
    celula** get_matriz_celular(){
        return this->matriz_celulas;
    }
};

                

