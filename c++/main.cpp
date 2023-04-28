#include <iostream>
#include "automata_celular.cpp"

using namespace std;

void mostrar_matriz(celula **matriz_celulas, int L) {
    for (int i = 0; i < L; i++) {
        for (int j = 0; j < L; j++) {
            char simbolo;
            switch (matriz_celulas[i][j].get_estado()) {
                case celula::estado_celula_sana:
                    simbolo = 'S';
                    break;
                case celula::estado_celula_infentada_A:
                    simbolo = 'A';
                    break;
                case celula::estado_celula_infectada_B:
                    simbolo = 'B';
                    break;
                case celula::estado_celula_muerta:
                    simbolo = 'M';
                    break;
                default:
                    simbolo = '?';
                    break;
            }
            cout << simbolo << "|";
        }
        cout << endl;
    }
}

int main() {
    srand(time(NULL)); // Inicializar la semilla para n�meros aleatorios

    int L;
    short int RA, RB;
    int t;
    double pinfec, prepl, pVIH;

    cout << "Ingrese el tama�o de la matriz (L): ";
    cin >> L;
    cout << "Ingrese el n�mero de c�lulas infectadas tipo A necesarias para infectar a una c�lula sana (RA): ";
    cin >> RA;
    cout << "Ingrese el n�mero de c�lulas infectadas tipo B necesarias para infectar a una c�lula sana (RB): ";
    cin >> RB;
    cout << "Ingrese el n�mero de 'time steps' necesarios para que una c�lula infectada tipo A se transforme en una c�lula infectada tipo B (t): ";
    cin >> t;
    cout << "Ingrese la probabilidad de que una c�lula muerta sea reemplazada por una c�lula infectada (pinfec): ";
    cin >> pinfec;
    cout << "Porcion de celulas infectadas en la matriz inicial: ";
    cin >> pVIH;

    automata_celular modelo_VIH(L, RA, RB, t, pinfec, pVIH);
    celula **matriz_celulas = modelo_VIH.get_matriz_celular();

    cout << "Matriz inicial:" << endl;
    mostrar_matriz(matriz_celulas, L);

    // Ejecuta el modelo una vez
    for(int i=0; i< 104; i++)
    {
         printf("\nITERACION NUMERO %d\n",i+1);
        modelo_VIH.actualizar_automata();
        mostrar_matriz(matriz_celulas, L);
        cout << "Densidades actuales: " << endl;
cout << "Sanas:" << modelo_VIH.get_densidad_actual()->densidas_celulas_sanas<< endl;
cout << "Infectadas:" << modelo_VIH.get_densidad_actual()->densidas_celulas_infectadas<< endl;
cout << "Muertas:" << modelo_VIH.get_densidad_actual()->densidas_celulas_muertas<< endl;
    }



    return 0;
}
