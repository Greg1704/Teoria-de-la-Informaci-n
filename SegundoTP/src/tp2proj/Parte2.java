

public class Parte2 {
    public static final int NumGrupo = 11;
    public static final int N1 = 5;
    public static final int M1 = 3; // matrices de 5x3

    public static final int N2 = 4;
    public static final int M2 = 4;

    public static final int N3 = 6;
    public static final int M3 = 4;

    public static void main(String[] args) {

        int i = 0;
        double VProb1[] = {0.2, 0.1, 0.3, 0.3, 0.1};
        double VProb2[] = {0.25, 0.33, 0.27, 0.15};
        double VProb3[] = {0.15, 0.1, 0.20, 0.25, 0.14, 0.16};

        double MC1[][] = new double[N1][M1];
        double MC2[][] = new double[N2][M2];
        double MC3[][] = new double[N3][M3];

        double VProbSalida1[] = new double[M1];
        double VProbSalida2[] = new double[M2];
        double VProbSalida3[] = new double[M3];

        double MCInversa1[][] = new double[M1][N1];
        double MCInversa2[][] = new double[M2][N2];
        double MCInversa3[][] = new double[M3][N3];

        cargaMatrices(MC1, MC2, MC3); // CARGO TODAS LAS MATRICES DE CANAL

        calculaProbSalidas(MC1, VProb1, VProbSalida1, N1, M1); // CARGO LAS PROBABILIDADES DE SALIDA
        calculaProbSalidas(MC2, VProb2, VProbSalida2, N2, M2);
        calculaProbSalidas(MC3, VProb3, VProbSalida3, N3, M3);

        matrizCanalInversa(MCInversa1, MC1, VProb1, VProbSalida1); // EMPIEZO A CARGAR LAS MATRICES Ai/Bj
        matrizCanalInversa(MCInversa2, MC2, VProb2, VProbSalida2);
        matrizCanalInversa(MCInversa3, MC3, VProb3, VProbSalida3);

        double[][] MSS1 = new double[N1][M1];
        double[][] MSS2 = new double[N2][M2];
        double[][] MSS3 = new double[N3][M3];

        matrizSucesoSimultaneo(MSS1, MC1, VProb1, M1); // CARGO MATRICES (A,B)
        matrizSucesoSimultaneo(MSS2, MC2, VProb2, M2);
        matrizSucesoSimultaneo(MSS3, MC3, VProb3, M3);


        System.out.println("CANAL 1");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Entropia 'a priori' : " + entropiaAPriori(VProb1));
        System.out.println("Entropia salida : " + entropiaSalida(VProbSalida1));
        System.out.println("Entropia 'afin' : " + entropiaAFin(MSS1));
        System.out.println("Equivocacion H(A/B) : " + equivocacion(MC1, VProb1, VProbSalida1));
        System.out.println("Informacion mutua I(A/B): " + informacionMutua(MC1, VProb1, VProbSalida1));
        System.out.println("Equivocacion H(B/A) : " + equivocacion(MCInversa1, VProbSalida1, VProb1));
        System.out.println("Informacion mutua I(B/A) : " + informacionMutua(MCInversa1, VProbSalida1, VProb1));

        for (int h = 0 ; h < M1 ; h++) {
            System.out.println("Entropia 'a posteriori' H(A/b" + (h+1) + ") = " + entropiaAPosteriori(MC1, VProb1, VProbSalida1, h));
        }

        System.out.println("");








        System.out.println("CANAL 2");
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Entropia 'a priori' : " + entropiaAPriori(VProb2));
        System.out.println("Entropia salida : " + entropiaSalida(VProbSalida2));
        System.out.println("Entropia 'afin' : " + entropiaAFin(MSS2));
        System.out.println("Equivocacion H(A/B) : " + equivocacion(MC2, VProb2, VProbSalida2));
        System.out.println("Informacion mutua I(A/B) : " + informacionMutua(MC2, VProb2, VProbSalida2));
        System.out.println("Equivocacion H(B/A) : " + equivocacion(MCInversa2, VProbSalida2, VProb2));
        System.out.println("Informacion mutua I(B/A) : " + informacionMutua(MCInversa2, VProbSalida2, VProb2));

        for (int h = 0 ; h < M2 ; h++) {
            System.out.println("Entropia 'a posteriori' H(A/b" + (h+1) + ") = " + entropiaAPosteriori(MC2, VProb2, VProbSalida2, h));
        }

        System.out.println("");





        System.out.println("CANAL 3");
        System.out.println("---------------------------------------------------------------------");

        System.out.println("Entropia 'a priori' : " + entropiaAPriori(VProb3));
        System.out.println("Entropia salida : " + entropiaSalida(VProbSalida3));
        System.out.println("Entropia 'afin' : " + entropiaAFin(MSS3));
        System.out.println("Equivocacion H(A/B) : " + equivocacion(MC3, VProb3, VProbSalida3));
        System.out.println("Informacion mutua I(A/B) : " + informacionMutua(MC3, VProb3, VProbSalida3));
        System.out.println("Equivocacion H(B/A) : " + equivocacion(MCInversa3, VProbSalida3, VProb3));
        System.out.println("Informacion mutua I(B/A) : " + informacionMutua(MCInversa3, VProbSalida3, VProb3));

        for (int h = 0 ; h < M3 ; h++) {
            System.out.println("Entropia 'a posteriori' H(A/b" + (h+1) + ") = " + entropiaAPosteriori(MC3, VProb3, VProbSalida3, h));
        }

        System.out.println("");





        System.out.println("MATRIZ DE CANAL 1");
        System.out.println("------------------------------------------------");
        System.out.println(MC1[0][0] + " " + MC1[0][1] + " " + MC1[0][2]);
        System.out.println(MC1[1][0] + " " + MC1[1][1] + " " + MC1[1][2]);
        System.out.println(MC1[2][0] + " " + MC1[2][1] + " " + MC1[2][2]);
        System.out.println(MC1[3][0] + " " + MC1[3][1] + " " + MC1[3][2]);
        System.out.println(MC1[4][0] + " " + MC1[4][1] + " " + MC1[4][2]);

        System.out.println("------------------------------------------------");

        System.out.println("MATRIZ DE CANAL 2");
        System.out.println("------------------------------------------------");
        System.out.println(MC2[0][0] + " " + MC2[0][1] + " " + MC2[0][2] + " " + MC2[0][3]);
        System.out.println(MC2[1][0] + " " + MC2[1][1] + " " + MC2[1][2] + " " + MC2[1][3]);
        System.out.println(MC2[2][0] + " " + MC2[2][1] + " " + MC2[2][2] + " " + MC2[2][3]);
        System.out.println(MC2[3][0] + " " + MC2[3][1] + " " + MC2[3][2] + " " + MC2[3][3]);


        System.out.println("MATRIZ DE CANAL 3");
        System.out.println("------------------------------------------------");
        System.out.println(MC3[0][0] + " " + MC3[0][1] + " " + MC3[0][2] + " " + MC3[0][3]);
        System.out.println(MC3[1][0] + " " + MC3[1][1] + " " + MC3[1][2] + " " + MC3[1][3]);
        System.out.println(MC3[2][0] + " " + MC3[2][1] + " " + MC3[2][2] + " " + MC3[2][3]);
        System.out.println(MC3[3][0] + " " + MC3[3][1] + " " + MC3[3][2] + " " + MC3[3][3]);
        System.out.println(MC3[4][0] + " " + MC3[4][1] + " " + MC3[4][2] + " " + MC3[4][3]);
        System.out.println(MC3[5][0] + " " + MC3[5][1] + " " + MC3[5][2] + " " + MC3[5][3]);

        System.out.println("------------------------------------------------");

        System.out.println("MATRIZ DE CANAL INVERSA 1");
        System.out.println("------------------------------------------------");
        System.out.println(MCInversa1[0][0] + " " + MCInversa1[0][1] + " " + MCInversa1[0][2] + " "  + MCInversa1[0][3] + " " + MCInversa1[0][4]);
        System.out.println(MCInversa1[1][0] + " " + MCInversa1[1][1] + " " + MCInversa1[1][2] + " "  + MCInversa1[1][3] + " " + MCInversa1[1][4]);
        System.out.println(MCInversa1[2][0] + " " + MCInversa1[2][1] + " " + MCInversa1[2][2] + " "  + MCInversa1[2][3] + " " + MCInversa1[2][4]);



    }

    public static void cargaMatrices(double MC1[][], double MC2[][], double MC3[][]) {
        MC1[0][0] = 0.3;
        MC1[0][1] = defineElemMC1('a');
        MC1[0][2] = 1 - MC1[0][0] - MC1[0][1];

        MC1[1][0] = defineElemMC1('b');
        MC1[1][1] = 0.4;
        MC1[1][2] = 1 - MC1[1][0] - MC1[1][1];
        ;

        MC1[2][0] = 0.3;
        MC1[2][1] = defineElemMC1('a');
        MC1[2][2] = 1 - MC1[2][0] - MC1[2][1];

        MC1[3][0] = defineElemMC1('a');
        MC1[3][1] = 0.4;
        MC1[3][2] = 1 - MC1[3][0] - MC1[3][1];

        MC1[4][0] = 0.3;
        MC1[4][1] = defineElemMC1('b');
        MC1[4][2] = 1 - MC1[4][0] - MC1[4][1];

        // MC2

        MC2[0][0] = 0.2;
        MC2[0][1] = defineElemMC2('a');
        MC2[0][2] = defineElemMC2('b');
        MC2[0][3] = 1 - MC2[0][0] - MC2[0][1] - MC2[0][2];

        MC2[1][0] = defineElemMC2('a');
        MC2[1][1] = 0.3;
        MC2[1][2] = defineElemMC2('b');
        MC2[1][3] = 1 - MC2[1][0] - MC2[1][1] - MC2[1][2];

        MC2[2][0] = defineElemMC2('c');
        MC2[2][1] = defineElemMC2('b');
        MC2[2][2] = 0.2;
        MC2[2][3] = 1 - MC2[2][0] - MC2[2][1] - MC2[2][2];

        MC2[3][0] = defineElemMC2('c');
        MC2[3][1] = 0.3;
        MC2[3][2] = defineElemMC2('a');
        MC2[3][3] = 1 - MC2[3][0] - MC2[3][1] - MC2[3][2];

        // MC3

        MC3[0][0] = 0.2;
        MC3[0][1] = defineElemMC3('a');
        MC3[0][2] = defineElemMC3('b');
        MC3[0][3] = 1 - MC3[0][0] - MC3[0][1] - MC3[0][2];

        MC3[1][0] = defineElemMC3('c');
        MC3[1][1] = defineElemMC3('a');
        MC3[1][2] = 0.3;
        MC3[1][3] = 1 - MC3[1][0] - MC3[1][1] - MC3[1][2];

        MC3[2][0] = defineElemMC3('b');
        MC3[2][1] = 0.2;
        MC3[2][2] = defineElemMC3('c');
        MC3[2][3] = 1 - MC3[2][0] - MC3[2][1] - MC3[2][2];

        MC3[3][0] = defineElemMC3('a');
        MC3[3][1] = 0.3;
        MC3[3][2] = defineElemMC3('b');
        MC3[3][3] = 1 - MC3[3][0] - MC3[3][1] - MC3[3][2];

        MC3[4][0] = 0.2;
        MC3[4][1] = defineElemMC3('c');
        MC3[4][2] = defineElemMC3('a');
        MC3[4][3] = 1 - MC3[4][0] - MC3[4][1] - MC3[4][2];

        MC3[5][0] = defineElemMC3('b');
        MC3[5][1] = defineElemMC3('c');
        MC3[5][2] = 0.3;
        MC3[5][3] = 1 - MC3[5][0] - MC3[5][1] - MC3[5][2];
    }

    public static double defineElemMC1(char c) {
        double aux = 0;
        if (c == 'a') {
            aux = 0.03 * NumGrupo;
            if ((aux < 0.1) || (aux > 0.3))
                aux = 0.3;
        } else if (c == 'b') {
            aux = 0.04 * NumGrupo;
            if (aux < 0.1 || aux > 0.4) {
                aux = 0.4;
            }
        }
        return aux;
    }

    public static double defineElemMC2(char c) {
        double aux = 0;
        if (c == 'a') {
            aux = 0.03 * NumGrupo;
            if ((aux < 0.1) || (aux > 0.3))
                aux = 0.3;
        } else if (c == 'b') {
            aux = 0.02 * NumGrupo;
            if (aux < 0.1 || aux > 0.2) {
                aux = 0.2;
            }
            } else if (c == 'c') {
                aux = 0.03 * NumGrupo;
                if (aux < 0.1 || aux > 0.3) {
                    aux = 0.3;
                }
            }
        return aux;
    }

    public static double defineElemMC3(char c) {
        double aux = 0;
        if (c == 'a') {
            aux = 0.03 * NumGrupo;
            if ((aux < 0.1) || (aux > 0.3))
                aux = 0.3;
        } else if (c == 'b') {
            aux = 0.02 * NumGrupo;
            if (aux < 0.1 || aux > 0.2) {
                aux = 0.2;
            }
            } else if (c == 'c') {
            aux = 0.03 * NumGrupo;
            if (aux < 0.1 || aux > 0.3) {
                aux = 0.3;
            }
        }
        return aux;
    }

    public static double entropiaAPriori(double[] VProb) {
        double auxEntrop = 0;
        int i = 0;
        for (i = 0; i < VProb.length; i++)
            auxEntrop += (VProb[i] * (-Math.log(VProb[i])) / Math.log(2));
        return auxEntrop;
    }

    public static double entropiaSalida(double[] VProbSalida) {
        double auxEntrop = 0;
        int i = 0;
        for (i = 0 ; i < VProbSalida.length ; i++) {
            auxEntrop += VProbSalida[i] * (-Math.log(VProbSalida[i])) / Math.log(2);
        }
        return auxEntrop;
    }

    public static void calculaProbSalidas(double[][] MC, double[] VProb, double[] VProbSalida, int N, int M) {
        int i;
        for (i = 0 ; i < M ; i++) {
            VProbSalida[i] = 0;
            for (int j = 0 ; j < N ; j++) {
                VProbSalida[i] += VProb[j] * MC[j][i];
            }
        }
    }

    public static void matrizCanalInversa(double MCInversa[][], double[][] MC, double[] VProb, double[] VProbSalida) {
        int i = 0;
        for(i = 0 ; i < VProbSalida.length ; i++){
            for(int j = 0 ; j < VProb.length ; j++){
                MCInversa[i][j] = (MC[j][i] * VProb[j]) / VProbSalida[i]; // P(Bj / Ai) . P(Ai) / P(Bj) // quedan las filas como las salidas y las columnas como entradas
            }
        }
    }

    public static double entropiaAPosteriori(double[][] MC, double[] VProb, double[] VProbSalida, int j) {
        double aux = 0;
        int i = 0;
        for (i = 0 ; i < VProb.length ; i++) {
            double prob = (MC[i][j] * VProb[i]) / VProbSalida[j]; // (P(bj / ai) * P(ai)) / P(bj)
            if (prob > 0) {
                aux +=  prob * (-Math.log(prob) / Math.log(2)); // prob * -log(prob)
            }
        }
        return aux;
    }

    public static double equivocacion(double[][] MC, double[] VProb, double[] VProbSalida) {
        double auxEquivocacion = 0;
        for(int j=0 ; j < VProbSalida.length ; j++) {
            auxEquivocacion += entropiaAPosteriori(MC, VProb, VProbSalida, j) * VProbSalida[j];
        }
        return auxEquivocacion;
    }

    public static double informacionMutua(double[][] MC, double [] VProb, double[] VProbSalida) {
        return entropiaAPriori(VProb) - equivocacion(MC, VProb, VProbSalida);
    }

    public static void matrizSucesoSimultaneo(double[][] MSS, double[][] MC, double[] VProb, int M) {
        int i, j;
        for (i = 0 ; i < VProb.length ; i++) {
            for (j = 0 ; j < M ; j++) {
                MSS[i][j] = MC[i][j] * VProb[i];
            }
        }
    }

    public static double entropiaAFin(double[][] MSS) {
        double auxEntrop = 0;
        for (double[] VProb : MSS) {
            for (double prob: VProb){ // recorre toda la matriz
                if (prob > 0)
                    auxEntrop += prob * (-Math.log(prob) / Math.log(2));
            }
        }
        return auxEntrop;
    }

}
