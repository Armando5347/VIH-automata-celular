#include <iostream>
#include "automata_celular.cpp"

using namespace std;

int main() {
    srand(time(NULL)); // Inicializar la semilla para números aleatorios

    int L;
    short int RA, RB;
    int t;
    double pinfec, pvih;

    cout << "Ingrese el tamaño de la matriz (L): ";
    cin >> L;

    cout << "Ingrese el número de células infectadas tipo A necesarias para infectar a una célula sana (RA): ";
    cin >> RA;

    cout << "Ingrese el número de células infectadas tipo B necesarias para infectar a una célula sana (RB): ";
    cin >> RB;

    cout << "Ingrese el número de 'time steps' necesarios para que una célula infectada tipo A se transforme en una célula infectada tipo B (t): ";
    cin >> t;

    cout << "Ingrese la probabilidad de que una célula muerta sea reemplazada por una célula infectada (pinfec): ";
    cin >> pinfec;

    //lo voy a ser estatico momentaneamente
    automata_celular modelo_VIH(10, 1, 4, 4, 0.01, 0.05);
    //modelo_VIH

    int bandera_finalizacion = 0;
    while (bandera_finalizacion == 0) {
        // Cada iteración es un periodo
        modelo_VIH.actualizar_automata();

        // Algo para esperar (por ejemplo, un temporizador)
        // Algo que asigne bandera_finalizacion igual a uno (por ejemplo, una condición de parada)
        bandera_finalizacion = 1;
    }

    return 0;
}
