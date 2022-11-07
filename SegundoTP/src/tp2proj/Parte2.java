package tp2proj;

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
        double V1[] = {0.2, 0.1, 0.3, 0.3, 0.1};
        double V2[] = {0.25, 0.33, 0.27, 0.15};
        double V3[] = {0.15, 0.1, 0.20, 0.25, 0.14, 0.16};

        double MC1[][] = new double[N1][M1];
        double MC2[][] = new double[N2][M2];
        double MC3[][] = new double[N3][M3];

        cargaMatrices(MC1, MC2, MC3);
        System.out.println("La entropia 'a priori' de la entrada" + 1 + "es de " + entropiaAPriori(V1));
        System.out.println("La entropia 'a priori' de la entrada" + 2 + "es de " + entropiaAPriori(V2));
        System.out.println("La entropia 'a priori' de la entrada" + 3 + "es de " + entropiaAPriori(V3));
    }

    public static void cargaMatrices(double MC1[][], double MC2[][], double MC3[][]) {
        MC1[0][0] = 0.3;
        MC1[0][1] = defineElemMC1('a');
        MC1[0][2] = 1 - MC1[0][0] - MC1[0][1];

        MC1[1][0] = defineElemMC1('b');
        MC1[1][1] = 0.4;
        MC1[1][2] = 1 - MC1[1][0] - MC1[1][1];;

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
        MC3[4][2] = defineElemMC1('a');
        MC3[4][3] = 1 - MC3[4][0] - MC3[4][1] - MC3[4][2];

        MC3[5][0] = defineElemMC1('b');
        MC3[5][1] = defineElemMC1('c');
        MC3[5][2] = 0.3;
        MC3[5][3] = 1 - MC3[5][0] - MC3[5][1] - MC3[5][2];
    }

    public static double defineElemMC1(char c) {
        double aux = 0;
        if (c == 'a') {
            aux = 0.03 * NumGrupo;
            if ((aux < 0.1) ||(aux > 0.3))
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
            if ((aux < 0.1) ||(aux > 0.3))
                aux = 0.3;
        } else if (c == 'b') {
            aux = 0.02 * NumGrupo;
            if (aux < 0.1 || aux > 0.2) {
                aux = 0.2;
            } else
                if (c == 'c') {
                    aux = 0.03 * NumGrupo;
                    if (aux < 0.1 || aux > 0.3) {
                        aux = 0.3;
                    }
                }
        }
        return aux;
    }

    public static double defineElemMC3(char c) {
        double aux = 0;
        if (c == 'a') {
            aux = 0.03 * NumGrupo;
            if ((aux < 0.1) ||(aux > 0.3))
                aux = 0.3;
        } else if (c == 'b') {
            aux = 0.02 * NumGrupo;
            if (aux < 0.1 || aux > 0.2) {
                aux = 0.2;
            } else
            if (c == 'c') {
                aux = 0.03 * NumGrupo;
                if (aux < 0.1 || aux > 0.3) {
                    aux = 0.3;
                }
            }
        }
        return aux;
    }

    public static double entropiaAPriori(double[] VProb) {
        double auxEntrop = 0;
        int i = 0;
        for (i = 0 ; i < VProb.length ; i++)
            auxEntrop += (VProb[i] * (-Math.log(VProb[i])) / Math.log(2));
        return auxEntrop;
    }

    public static double equivocacion(double M[][]) {
        double auxEquivoc = 0;
        return auxEquivoc;
    }

}

