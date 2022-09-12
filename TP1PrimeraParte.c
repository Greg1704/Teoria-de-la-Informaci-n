#include <stdio.h>
#include <stdlib.h>
#include "string.h"

void leeArch(int M[3][3]); // parámetro N por si llega a haber una letra de más o de menos
void init(int M[3][3], int V[]);
void calculaProbabilidades(float MPasaje[3][3], int M[][3], int V[]);

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
    float MPasaje[3][3];
    int V[3];
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
        M[simb-65][ultSimb-65]+=1; // el simbolo "simb" aparecio despues de la ocurrencia de "ultSimb"
        ultSimb=simb;
        fscanf(archt, "%c", &simb);
    }
    calculaProbabilidades(MPasaje, M, V);
}

void calculaProbabilidades(float MPasaje[3][3], int M[3][3], int V[]) {
    int i, j;
    for (j=0 ; j<3 ; j++) {
        for (i=0 ; i<3 ; i++) {
            MPasaje[i][j] = ((float) M[i][j] / (float) V[j]); // divido ocurrencias de i despues de j sobre ocurrencias total de i. obteniendo esto se puede sacar la conclusion
            // de si es de memoria nula o no
            printf("i = %d  j = %d  MPasaje = %f \n",i,j,MPasaje[i][j]);
        }
    }
    printf("%f \n", MPasaje[0][0] + MPasaje[1][0] + MPasaje[2][0]);
    printf("%f \n", MPasaje[0][1] + MPasaje[1][1] + MPasaje[2][1]);
    printf("%f \n", MPasaje[0][2] + MPasaje[1][2] + MPasaje[2][2]);
    //Dan los tres 1(Fuente de Markov)
}