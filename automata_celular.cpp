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

public:
    automata_celular(){}

    automata_celular(int L, short ra, short rb, int t, double p_infec){
        this->_L = L;
        this->_RA = ra;
        this->_RB = rb;
        this->_t = t;
        this->_pinfec = p_infec;
        this->_prepl = 1 - p_infec;
        this->matriz_celulas = new celula*[L]; //se inicializa la matriz de células
        for (int i = 0; i < L; i++){
            this->matriz_celulas[i] = new celula[L];
            for (int j = 0; j < L; j++){
                this->matriz_celulas[i][j].set_estado(celula::estado_celula_sana);
                this->matriz_celulas[i][j].set_periodos(t);
            }
        }
    }

    //este método ocurre cada "time step"
    void actualizar_automata(){
        int iterador1, iterador2;
        for ( iterador1 = 0; iterador1 < this->_L; iterador1++)
        {
            for ( iterador2 = 0; iterador2 < this->_L; iterador2++)
            {
                actualizar_celula(this->matriz_celulas[iterador1][iterador2], iterador1, iterador2);
            }
                
        }
        
    }

    void actualizar_celula(celula celula_a_actualizar, int pos_x, int pos_y){

        switch (celula_a_actualizar.get_estado())
        {
        case celula::estado_celula_sana :
            {
                //se tiene que revisar la vecindad de Moore
            
            //aquí se INSERTA EL ANÁLISIS DE LA VECINDAD
            void analizar_vecindad(int pos_x, int pos_y) {
                short int celulas_infectadas_adyacentes_tipo_A = 0;
                short int celulas_infectadas_adyacentes_tipo_B = 0;
                    // Iterar sobre las 8 celdas vecinas
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            // Evitar la celda central (0, 0)
                            if (dx == 0 && dy == 0) continue;

                            int vecino_x = (pos_x + dx + _L) % _L;
                            int vecino_y = (pos_y + dy + _L) % _L;

                            // No es necesario verificar los límites de la matriz, porque las condiciones de frontera toroidal
                            // ya se manejan con el cálculo del módulo

                            celula &vecino = matriz_celulas[vecino_x][vecino_y];

                            if (vecino.get_estado() == celula::estado_celula_infentada_A) {
                                celulas_infectadas_adyacentes_tipo_A++;
                            } else if (vecino.get_estado() == celula::estado_celula_infectada_B) {
                                celulas_infectadas_adyacentes_tipo_B++;
                            }
                        }
                    }
                }
            
                // Aplicar las reglas de cambio de estado
                celula &celula_actual = matriz_celulas[pos_x][pos_y];
                if (celula_actual.get_estado() == celula::estado_celula_sana) {
                    if (celulas_infectadas_adyacentes_tipo_A >= _RA || celulas_infectadas_adyacentes_tipo_B >= _RB) {
                        celula_actual.set_estado(celula::estado_celula_infentada_A);
                    }
                }
            }

            
            break;
        case celula::estado_celula_infentada_A :
            {
            //se revisa si pasaron los 't' periodos o no
            if(celula_a_actualizar.get_periodo() > 0)
                celula_a_actualizar.paso_periodo();
            else
                celula_a_actualizar.set_estado(celula::estado_celula_infectada_B);
            }
            break;
        case celula::estado_celula_infectada_B :
            {
            celula_a_actualizar.set_estado(celula::estado_celula_muerta);
            }
            break;
        case celula::estado_celula_muerta:
            {
            // Generar un número aleatorio en el rango [0, 1]
            double random_prob = static_cast<double>(rand()) / static_cast<double>(RAND_MAX);

            // Comparar el número aleatorio con las probabilidades
            if (random_prob <= _pinfec) {
                celula_a_actualizar.set_estado(celula::estado_celula_infentada_A); // Reemplazar por una célula infectada tipo A
            } else if (random_prob <= (_pinfec + _prepl)) {
                celula_a_actualizar.set_estado(celula::estado_celula_sana); // Reemplazar por una célula sana
            }
            }
            break;
        default:
            //"Si llegamos aquí, hubo un error en las estados de una célula"<<endl;
            break;
        }
        
    }

    //faltan getters y setters    

};

