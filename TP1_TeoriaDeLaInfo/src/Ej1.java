import java.io.*;

import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.linear.LinearSystemSolver;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

public class Ej1 {
    private static final int N = 3;
    public static void main(String[] args) {
        int[][] M = new int[N][N];
        leeArch(M);
        System.out.println("LOL");
    }

    public static void init(int [][] M, int[] V){
        for (int i=0;i<N;i++){
            V[i]=0;
            for (int j=0;j<N;j++)
                M[i][j]=0;
        }
    }

    public static void leeArch(int[][] M) {
        double[][] MPasaje = new double[N][N];
        int[] V = new int[N];
        double[] VEstacionario = new double[N];
        int ultSimb=65;
        init(M, V);
        System.out.println("intento leer archivo");
        try (InputStream in = new FileInputStream("src/datosGrupo11.txt");
             Reader reader = new InputStreamReader(in)) {
            //leer primero
            if((ultSimb = reader.read()) != -1)
                V[ultSimb - 65]++;
            int simb;
            while ((simb =  reader.read()) != -1) {
                V[simb-65] += 1;
                M[simb-65][ultSimb-65]+=1;
                ultSimb=simb;
            }
            calculaProbabilidades(MPasaje, M, V , VEstacionario);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void calculaProbabilidades(double[][] MPasaje, int[][] M, int[] V, double[] VEstacionario) {
        int i, j;
        for (j=0 ; j<N ; j++) {
            for (i=0 ; i<N ; i++) {
                MPasaje[i][j] = ((float) M[i][j] / (float) V[j]); // divido ocurrencias de i despues de j sobre ocurrencias total de j. obteniendo esto se puede sacar la conclusion
                // de si es de memoria nula o no
            }
        }
        for (i = 0 ; i<N; i++) {
            for (j = 0 ; j<N ; j++) {
                System.out.print(MPasaje[i][j] + "  ");
            }
            System.out.println("");
        }

        System.out.println(MPasaje[0][0] + MPasaje[1][0] + MPasaje[2][0]);
        System.out.println(MPasaje[0][1] + MPasaje[1][1] + MPasaje[2][1]);
        System.out.println(MPasaje[0][2] + MPasaje[1][2] + MPasaje[2][2]);
        //Dan los tres 1(Fuente de Markov)
        if(esErgodica(MPasaje)){
            resolvedorDeSistemas(MPasaje, VEstacionario);
            calculaEntropia(MPasaje, VEstacionario);
            //HAY QUE CALCULAR VECTOR ESTACIONARIO
        }
    }

    public static boolean esErgodica(double[][] MPasaje){
        int[][] M = new int [N][N]; // esta es una matrix auxiliar solo para usar warshall
        int fila=0,columna=0;
        for (int i = 0; i<N ; i++)
            for (int j = 0; j <N ; j++){
                M[i][j]=0;
            }
        for (int k = 0; k <N ; k++)
            for (int i = 0; i<N ; i++)
                for (int j = 0; j <N ; j++){
                    if(MPasaje[i][j] > 0 || (MPasaje[i][k] + MPasaje[k][j] > 0))
                        M[i][j] = 1;
                }
        while(fila<3){
            while(columna<3){
                if(M[fila][columna] !=1)
                    return false;   //Entra en este if si no se cumple la condicion de Ergodica
                columna++;
            }
            fila++;
            columna=0;
        }
        return true; //Llega a esta linea si se cumplio la condición de que sea Ergodica
    }

    public static void calculaEntropia(double[][] MPasaje, double[] VEstacionario) {
        double entrop=0;
        double auxCol;
        for (int j=0 ; j<N ; j++) {
            auxCol=0;
            for (int i=0 ; i<N ; i++) {
                if (MPasaje[i][j] != 0)
                    auxCol += MPasaje[i][j] * Math.log(MPasaje[i][j]) / -Math.log(2);
            }
            entrop+=auxCol*VEstacionario[j];
        }
        System.out.println("La entropia es " + entrop);
    }

    private static void resolvedorDeSistemas(double[][] MPasaje, double[] VEstacionario) {
        double[][] MAux = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                MAux[i][j] = MPasaje[i][j];
                if (i == j)
                    MAux[i][j]--; // acá hago (M-I) para hacer la cuenta de (M-I)V*
            }
        }
        for (int j = 0; j < N; j++)
            MAux[N-1][j] = 1;
        Matrix a = new Basic2DMatrix(MAux); //Arma la matriz para que pueda resolverse con la libreria
        Vector b = new BasicVector(new double[]{0, 0, 1}); // esto depende de N estrictamente
        LinearSystemSolver solver = a.withSolver(LinearAlgebra.FORWARD_BACK_SUBSTITUTION);
        Vector VAux = solver.solve(b);
        for (int i = 0; i < N; i++)
            VEstacionario[i] = VAux.get(i);
        System.out.println("Vector Estacionario de la matriz: V* = [" + VAux + "]");
    }
}