#include <stdio.h>
#include <stdlib.h>
#include "string.h"

void leeArch(int M[3][3]); // parámetro N por si llega a haber una letra de más o de menos
void init(int M[][], int V[]);
void calculaProbabilidades(int MPasaje[][]);

int main() {
    int M[3][3];
    leeArch(M);
    return 0;
}

void init(int M[3][3], int V[3]) { // M va a guardar en cada mij la ocurrencia de i despues de la ocurrencia de j
    int i, j;
    for (i=0 ; i<3 ; i++) {
        V[i]=0;
        for (j=0 ; j<3 ; j++) {
            M[i][j]=0;
        }
    }
}

void leeArch(int M[3][3]) {
    int i=0, ocA=0, ocB=0, ocC=0; // si fuesen muchos simb haria un vector de ocurrencias
    int MPasaje[3][3]
    char simb, ultSimb;
    FILE* archt; // archivo de texto

    initMatriz(M);
    archt = fopen("datos.txt", "r");
    fscanf(archt, "%c", &simb)
    while (!feof(archt)) {
        V[simb-97]++; // hago un vector para ir guardando las ocurrencias
        M[simb][ultSimb]+=1; // el simbolo "simb" aparecio despues de la ocurrencia de "ultSimb"
        //cadena[i] = simb;
        ultSimb=simb;
        fscanf(archt, "%c", &simb);
        i++
    }
    calculaProbabilidades(MPasaje, M, V)
}

void calculaProbabilidades(int MPasaje[3][3], int M[3][3], int V[]) {
    int i, j;
    for (j=0 ; j<3 ; i++) {
        for (i=0 ; i<3 ; j++) {
            MPasaje[i][j] = (M[i][j] / V[i]);
        }
    }
}