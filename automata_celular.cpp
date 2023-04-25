/**Archivo que describe la clasel automata celular
 * 
 * 
*/
#include "celula.cpp"
#include <cstdio>
using namespace std;

class automata_celular
{
private:
    int _L; //numero de celdas => L*L
    short int _RA; //numero de celulas infectadas tipo A necesarias para infectar a una celula sana
    short int _RB; //numero de celulas infectadas tipo B necesarias para infectar a una celula sana
    celula ** matriz_celulas; //la representacion de las celulas en la matriz L*L
    int _t; //numero de "times steps" necesarios para que una celula infectada tipo A se transforme en una celula infectada tipo B
    double _prepl; //probabilidad de que una celula muerta sea rememplaza por una celula sana
    double _pinfec; //probabilidad de que una celula muerta sea reemplazada por una celula infectada
public:
    automata_celular(){}

    automata_celular(int L, short ra, short rb, int t, double p_infec){
        this->_L = L;
        this->_RA = ra;
        this->_RB = rb;
        this->_t = t;
        this->_pinfec = p_infec;
        this->_prepl = 1 - p_infec;
        this->matriz_celulas = new celula*[L]; //se inicializa la matriz de celulas
        for (int i = 0; i < L; i++){
            this->matriz_celulas[i] = new celula[L];
            for (int j = 0; j < L; j++){
                this->matriz_celulas[i][j].set_estado(celula::estado_celula_sana);
                this->matriz_celulas[i][j].set_periodos(t);
            }
        }
        
    }
    //este metodo ocurre cada "timestamp"
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
            //se tiene que revisar la vecindad de moore
            short int celulas_infectadas_adyacentes_tipo_A = 0;
            short int celulas_infectadas_adyacentes_tipo_B = 0;
            
            //aquí se INSERTA EL ANALISIS DE LA VECINDAD

            if ( celulas_infectadas_adyacentes_tipo_A >= automata_celular::_RA 
                || celulas_infectadas_adyacentes_tipo_B >= automata_celular::_RB) //tiene suficientes celulas infectadas ceranas
                celula_a_actualizar.set_estado(celula::estado_celula_infentada_A); //pasa a infectada tipo A
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
            //insertar forma de calcular la probabilidad
            }
            break;
        default:
            //"Si llegamos aquí, hubo un error en las estados de una celula"<<endl;
            break;
        }
        
    }

    //faltan getters y setters    

};

