#include <stdio.h>
#include <stdlib.h>
#include "string.h"

void leeArch(char cadena[]); // parámetro N por si llega a haber una letra de más o de menos

int main() {

    leeArch()
    return 0;
}

void leeArch(char cadena[]) {
    int i=0, ocA=0, ocB=0, ocC=0; // si fuesen muchos simb haria un vector de ocurrencias
    char simb;
    FILE* archt; // archivo de texto
    archt = fopen("datos.txt", "r");
    fscanf(archt, "%c", &simb)
    while (!feof(archt)) {
        if (simb == 'a') {
            ocA+=1;
        } else
            if (simb == 'b')
                ocB+=1;
            else
                if (simb == 'c') // verifico tambien el caso de C por si llega a haber algun simbolo que no corresponda
                    ocC+=1;
        cadena[i] = simb;
        fscanf(archt, "%c", &simb);
        i++;
    }
}