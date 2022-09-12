#include <stdio.h>
#include <stdlib.h>
#include "string.h"
#define N 3

void leeArch(int M[N][N]); // parámetro N por si llega a haber una letra de más o de menos
void init(int M[N][N], int V[N]);
void generaMIdentidad(int MIdentidad[N][N]);
void igualoMatrices(float MPasaje[N][N], int MIdentidad[N][N], float MAux[N][N]);
int esErgodica(float MPasaje[N][N]);
void calculaProbabilidades(float MPasaje[N][N], int M[N][N], int V[N]);

int main() {
    int M[N][N];
    //char c = 'A'-'A';
   // printf("%c", c);
    leeArch(M);
    return 0;
}

void init(int M[N][N], int V[N]) { // M va a guardar en cada mij la ocurrencia de i despues de la ocurrencia de j
    int i, j;
    for (i=0 ; i<N ; i++) {
        V[i]=0;
        for (j=0 ; j<N ; j++) {
            M[i][j]=0;
        }
    }
}

void generaMIdentidad(int MIdentidad[N][N]) {
    int i, j;
    for (i=0 ; i<N ; i++) {
        for (j=0 ; i<N ; i++) {
            MIdentidad[i][j] = (i==j);
        }
    }
}

void igualoMatrices(float MPasaje[N][N], int MIdentidad[N][N], float MAux[N][N]) {
    int i, j;
    for (i=0 ; i<N ; i++)
        for (j=0; j<N ; j++) 
            MAux[i][j]=MPasaje[i][j]-MIdentidad[i][j];
}

void leeArch(int M[N][N]) {
    float MPasaje[N][N];
    int V[N];
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

void calculaProbabilidades(float MPasaje[N][N], int M[N][N], int V[N]) {
    int i, j, MIdentidad[N][N];
    float MAux[N][N];
    for (j=0 ; j<N ; j++) {
        for (i=0 ; i<N ; i++) {
            MPasaje[i][j] = ((float) M[i][j] / (float) V[j]); // divido ocurrencias de i despues de j sobre ocurrencias total de j. obteniendo esto se puede sacar la conclusion
            // de si es de memoria nula o no
            printf("i = %d  j = %d  MPasaje = %f \n",i,j,MPasaje[i][j]);
        }
    }
    printf("%f \n", MPasaje[0][0] + MPasaje[1][0] + MPasaje[2][0]);
    printf("%f \n", MPasaje[0][1] + MPasaje[1][1] + MPasaje[2][1]);
    printf("%f \n", MPasaje[0][2] + MPasaje[1][2] + MPasaje[2][2]);
    generaMIdentidad(MIdentidad);
    igualoMatrices(MPasaje, MIdentidad, MAux); // resultado = MAux = (M-I) para poder hacer (M-I)V* = 0
    //Dan los tres 1(Fuente de Markov)
}

int esErgodica(float MPasaje[N][N]) {
    int aux=0; //false para inicializar
    float MAux[N][N];
}

/*int calculoEntropia(float MPasaje[N][N], int M[N][N], int V[N]) {
    int aux=0, i, j;
    for (i=0; i<N ; i++) {
        for (j=0; i<N ; i++)
        
    }
}
*/