import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class Ej2 {
    static Map<String,Integer> mapA = new HashMap<String,Integer>();
    static Map<String,Integer> mapB = new HashMap<String,Integer>();
    static Map<String,Integer> mapC = new HashMap<String,Integer>();
    final static int A=3;
    final static int B=5;
    final static int C=7;
    final static int alfabetoCodigo=3;
    public static void main(String[] args) {
        leerArch();
    }

    public static void leerArch(){
        int simb;
        String identificadorA= "", identificadorB ="", identificadorC ="";
        int i = 0,j=0,k=0; //i para 3 caracteres,j para 5, y k para 7
        try (InputStream in = new FileInputStream("src/datosGrupo11.txt");
             Reader reader = new InputStreamReader(in)) {
            while ((simb = reader.read()) != -1){
                i++;
                j++;
                k++;
                if(i < A){
                    identificadorA+= (char) simb;
                }else if(i == 3){
                    identificadorA+= (char) simb;
                    if(mapA.containsKey(identificadorA))
                        mapA.put(identificadorA, mapA.get(identificadorA) + 1);
                    else
                        mapA.put(identificadorA,1);
                    i=0;
                    identificadorA="";
                }
                if(j<B){
                    identificadorB+=(char) simb;
                }else if(j == 5){
                    identificadorB+=(char) simb;
                    if(mapB.containsKey(identificadorB))
                        mapB.put(identificadorB, mapB.get(identificadorB) + 1);
                    else
                        mapB.put(identificadorB,1);
                    j=0;
                    identificadorB="";
                }
                if(k<C){
                    identificadorC+=(char) simb;
                }else if(k == 7){
                    identificadorC+=(char) simb;
                    if(mapC.containsKey(identificadorC))
                        mapC.put(identificadorC, mapC.get(identificadorC) + 1);
                    else
                        mapC.put(identificadorC,1);
                    k=0;
                    identificadorC="";
                }
            }

            System.out.println("mapA = " + mapA.size());
            mapA.forEach((key,value) ->{
                System.out.println("key = " + key + "   value = " + value + "  Cantidad informacion = " +
                        cantidadInformacion(value,Math.floor(10000/A)));
            });
            System.out.println();
            System.out.println("mapB = " + mapB.size());
            mapB.forEach((key,value) ->{
                System.out.println("key = " + key + "   value = " + value + "  Cantidad informacion = " +
                        cantidadInformacion(value,Math.floor(10000/B)));
            });
            System.out.println("mapC = " + mapC.size());
            mapC.forEach((key,value) ->{
                System.out.println("key = " + key + "   value = " + value + "  Cantidad informacion = " +
                        cantidadInformacion(value,Math.floor(10000/C)));
            });

            calculoEntropia();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double cantidadInformacion(int value,double size){
        return Math.log(((double)value/size))/-Math.log(2);
    }

    public static void calculoEntropia(){
        double auxentrop=0,auxprob=0;
        for(Map.Entry<String, Integer> entry : mapA.entrySet()) {
            auxprob=entry.getValue()/Math.floor(10000/A);
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(2));
        }
        System.out.println("Entropia de fuente de "+ A + " caracteres = " + auxentrop);

        auxprob=0;
        auxentrop=0;

        for(Map.Entry<String, Integer> entry : mapB.entrySet()) {
            auxprob=entry.getValue()/Math.floor(10000/B);;
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(2));
        }
        System.out.println("Entropia de fuente de "+ B + " caracteres = " + auxentrop);

        auxprob=0;
        auxentrop=0;

        for(Map.Entry<String, Integer> entry : mapC.entrySet()) {
            auxprob=entry.getValue()/Math.floor(10000/C);;
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(2));
        }
        System.out.println("Entropia de fuente de "+ C + " caracteres = " + auxentrop);
    }
}
