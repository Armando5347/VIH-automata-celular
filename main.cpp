/**Programa principal del
 * 
*/
#include "automata_celular.cpp"

int main(){
    automata_celular modelo_VIH = automata_celular(); //deben de meterse los parametros
    int bandera_finalizacion = 0;
    while(bandera_finalizacion == 0){
        //cada iteracion es un periodo
        modelo_VIH.actualizar_automata();
        //algo para esperar?
        //algo que asigne bandera_finalizacion igual a uno
    }

    return 0;
}