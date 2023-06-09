/**Archivo de descripci�n del objeto celula
 *
 *
*/
#include <cstdio>
using namespace std;

class celula
{
private:
    short int _estado;
    short int _estado_futuro;
    int _periodos;
    //objeto de interfaz
public:
    //constantes estaticas
    const static short int estado_celula_sana = 0;
    const static short int estado_celula_infentada_A = 1;
    const static short int estado_celula_infectada_B = 2;
    const static short int estado_celula_muerta = 3;

    // Constructor por defecto, inicializa una celula sana
    celula(){
        this->_estado = celula::estado_celula_sana;
        this->_periodos = 0;
    }
    // Constructor con estado inicial
    celula(short int estado){
        this->_estado = estado;
        this->_periodos = 0;
    }

    // Obtener el estado actual de la celula
    short int get_estado(){
        return this->_estado;
    }

    // Establecer el estado de la celula
    void set_estado(short int nuevo_estado){
        this->_estado = nuevo_estado;
    }

    // Obtener el estado futuro de la celulca
    short int get_estado_futuro(){
        return this->_estado_futuro;
    }

    // Establecer el estado al cual va a cambiar la celula
    void set_estado_futuro(short int nuevo_estado){
        this->_estado_futuro = nuevo_estado;
    }

    // Obtener el n�mero de periodos restantes para una celula infectada tipo A
    int get_periodo(){
        return this->_periodos;
    }

    // Establecer el n�mero de periodos para una celula infectada tipo A
    void set_periodos(int t){
        this->_periodos = t;
    }

    // Disminuir el n�mero de periodos restantes para una celula infectada tipo A
    void paso_periodo(){
        this->_periodos--;
    }
};

