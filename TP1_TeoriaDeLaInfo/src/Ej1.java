import java.io.*;

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

    public static void generaMIdentidad(int [][] MId){
        for (int i=0;i<N;i++){
            for (int j=0;j<N;j++)
                if(i == j)
                    MId[i][j] = 1;
                else
                    MId[i][j] = 0;
        }
    }

    public static void igualoMatrices(double[][] MPasaje,int[][] MId,double [][] Maux) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                Maux[i][j] = (float) (MPasaje[i][j] - MId[i][j]);
        }
    }

    public static void leeArch(int[][] M) {
        float[][] MPasaje = new float[N][N];
        int[] V = new int[N];
        char ultSimb='A';
        init(M, V);
        System.out.println("intento leer archivo");
        try (InputStream in = new FileInputStream("datosGrupo11.txt");
             Reader reader = new InputStreamReader(in)) {

            char simb;
            while ((simb = (char) reader.read()) != -1) {
                V[simb-65] += 1;
                M[simb-65][ultSimb-65]+=1;
                ultSimb=simb;
            }
            System.out.println(M);
            System.out.println("entra aca");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void calculaProbabilidades(double[][] MPasaje, int[][] M, int[] V) {
        int i, j;
        int[][] MIdentidad = new int [N][N];
        double[][] MAux = new double[N][N];
        for (j=0 ; j<N ; j++) {
            for (i=0 ; i<N ; i++) {
                MPasaje[i][j] = ((float) M[i][j] / (float) V[j]); // divido ocurrencias de i despues de j sobre ocurrencias total de j. obteniendo esto se puede sacar la conclusion
                // de si es de memoria nula o no
            }
        }

        if(esErgodica(MPasaje)){
            generaMIdentidad(MIdentidad);
            igualoMatrices(MPasaje, MIdentidad, MAux); // resultado = MAux = (M-I) para poder hacer (M-I)V* = 0
            //Dan los tres 1(Fuente de Markov)
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
}