/**Archivo de descripción del objeto celula
 * 
 * 
*/

#include <cstdio>
using namespace std;

class celula
{
private:
    short int _estado;
    int _periodos;
    //objeto de interfaz
public:
    //constantes estaticas
    const static short int estado_celula_sana = 0;
    const static short int estado_celula_infentada_A = 1;
    const static short int estado_celula_infectada_B = 2;
    const static short int estado_celula_muerta = 3;

    celula(){
        this->_estado = celula::estado_celula_sana;
    }
    celula(short int estado){
        this->_estado = estado;
    }

    short int get_estado(){
        return this->_estado;
    }

    void set_estado(short int nuevo_estado){
        this->_estado = nuevo_estado;
    }

     int get_periodo(){
        return this->_periodos;
    }

    void set_periodos(int t){
        this->_periodos = t;
    }

    void paso_periodo(){
        this->_periodos --;
    }

};

