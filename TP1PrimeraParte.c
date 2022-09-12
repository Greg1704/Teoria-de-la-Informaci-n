#include <stdio.h>
#include <stdlib.h>
#include "string.h"

void leeArch(int M[3][3]); // parámetro N por si llega a haber una letra de más o de menos
void init(int M[3][3], int V[]);
void calculaProbabilidades(int MPasaje[3][3], int M[][3], int V[]);

int main() {
    int M[3][3];
    //char c = 'A'-'A';
   // printf("%c", c);
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
    int MPasaje[3][3], V[3];
    char simb, ultSimb;
    FILE* archt; // archivo de texto

    init(M, V);
    archt = fopen("datosGrupo11.txt", "r");
    fscanf(archt, "%c", &simb); // los datos estan en mayuscula
    if(!feof(archt)){
        ultSimb = simb;
        V[simb-65]++; // hago un vector para ir guardando las ocurrencias
        fscanf(archt, "%c", &simb);
    }
    while (!feof(archt)) {
        V[simb-65]++; 
        M[ultSimb-65][simb-65]+=1; // el simbolo "simb" aparecio despues de la ocurrencia de "ultSimb"
        //cadena[i] = simb;
        ultSimb=simb;
        fscanf(archt, "%c", &simb);
        i++;
    }
    calculaProbabilidades(MPasaje, M, V);
}

void calculaProbabilidades(int MPasaje[3][3], int M[3][3], int V[]) {
    int i, j;
    for (j=0 ; j<3 ; i++) {
        for (i=0 ; i<3 ; j++) {
            MPasaje[i][j] = (M[i][j] / V[i]); // divido ocurrencias de i despues de j sobre ocurrencias total de i. obteniendo esto se puede sacar la conclusion
            // de si es de memoria nula o no
        }
    }
}