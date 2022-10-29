package ejercicios;

import java.io.*;

public class Ej1 {
    private static final int N = 3;
    private static final int Z = 20; //Numero de orden de la entropia que se busca en el inciso 1b del trabajo practico

    private static double orden20entrop=0;

    public static void main(String[] args) {
        int[][] M = new int[N][N];
        leeArch(M);
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
        try (InputStream in = new FileInputStream("datosGrupo11.txt");
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
        double [] VProb = new double[N];
        for (j=0 ; j<N ; j++) {
            for (i=0 ; i<N ; i++) {
                MPasaje[i][j] = ((float) M[i][j] / (float) V[j]); // divido ocurrencias de i despues de j sobre ocurrencias total de j. obteniendo esto se puede sacar la conclusion
                // de si es de memoria nula o no
            }
        }
        
        try {
            File file = new File("Ej1IncisoA.txt");
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (i = 0 ; i<N; i++) {
                for (j = 0 ; j<N ; j++) {
                    System.out.print(MPasaje[i][j] + "  ");
                    bw.write(MPasaje[i][j] + "  ");
                }
                System.out.println("");
                bw.write("\n");
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        System.out.println(MPasaje[0][0] + MPasaje[1][0] + MPasaje[2][0]);
        System.out.println(MPasaje[0][1] + MPasaje[1][1] + MPasaje[2][1]);
        System.out.println(MPasaje[0][2] + MPasaje[1][2] + MPasaje[2][2]);

        for(i=0;i<N;i++){
            VProb[i]= (double) V[i]/10000;
            System.out.println("VProb[i] = " + VProb[i]);
        }
        if(verificacionMemoriaNula(MPasaje)){
           calculoEntropiaOrden20("ABC", Z,1,VProb);
            try {
                File file = new File("Ej1IncisoB.txt");
                if (!file.exists())
                    file.createNewFile();
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                System.out.println("Entropia inicial = " + calculoEntropiaInicial(VProb));
                System.out.println("Entropia Orden " + Z + " = " + orden20entrop);
                bw.write("Entropia inicial = " + calculoEntropiaInicial(VProb) + "\nEntropia Orden " + Z + " = " + orden20entrop);
                bw.close();
                fw.close();
            }catch (IOException e) {
            e.printStackTrace();
            }
        }
    }

    public static boolean verificacionMemoriaNula(double[][] MPasaje){
        double mayor=0, menor=0;
        for(int i=0;i<N;i++) {
            for (int j = 0; j < N; j++) {
                if (mayor < MPasaje[i][j]) {
                    if (menor == 0 || menor > mayor)
                        menor = mayor;
                    mayor = MPasaje[i][j];
                } else if (menor == 0 || menor > MPasaje[i][j])
                    menor = MPasaje[i][j];
            }
            if(mayor - menor > 0.03)
                return false;
            mayor=0;
            menor=0;
        }
        return true;
    }

    public static double calculoEntropiaInicial(double[] VProb){
        double auxentrop=0;
        for(int i=0;i<N;i++)
            auxentrop+=VProb[i]*(Math.log(VProb[i])/-Math.log(3));
        return auxentrop;
    }

        public  static void calculoEntropiaOrden20(String input, int orden, double auxentrop, double[] VProb) {//Duracion estimada, entre 1 y 2 minutos
            if (orden == 0) {
                auxentrop=auxentrop*(Math.log(auxentrop) / -Math.log(3));
                orden20entrop+=auxentrop;
            } else {
                for (int i = 0; i < input.length(); i++) {
                    auxentrop*=VProb[input.charAt(i)-65];
                    calculoEntropiaOrden20(input, orden - 1,auxentrop,VProb);
                    auxentrop/=VProb[input.charAt(i)-65];
                }
            }
        }
}
