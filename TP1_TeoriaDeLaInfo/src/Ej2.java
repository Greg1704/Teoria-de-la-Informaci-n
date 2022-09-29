import java.io.*;

import java.util.HashMap;
import java.util.Map;

/*
 * PENDIENTE: HACER MACMILLAN
 * PENDIENTE: HACER EJ B
 * */

public class Ej2 {
    static Map<String,Double> mapA = new HashMap<String,Double>();
    static Map<String,Double> mapB = new HashMap<String,Double>();
    static Map<String,Double> mapC = new HashMap<String,Double>();
    static Map<Integer,Double> mapEntropia = new HashMap<Integer,Double>();
    static Map<Integer,Double> mapLongMedia = new HashMap<Integer,Double>();
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
                }else if(i == A){
                    identificadorA+= (char) simb;
                    if(mapA.containsKey(identificadorA))
                        mapA.put(identificadorA, mapA.get(identificadorA) + 1);
                    else
                        mapA.put(identificadorA,1.0);
                    i=0;
                    identificadorA="";
                }
                if(j<B){
                    identificadorB+=(char) simb;
                }else if(j == B){
                    identificadorB+=(char) simb;
                    if(mapB.containsKey(identificadorB))
                        mapB.put(identificadorB, mapB.get(identificadorB) + 1);
                    else
                        mapB.put(identificadorB,1.0);
                    j=0;
                    identificadorB="";
                }
                if(k<C){
                    identificadorC+=(char) simb;
                }else if(k == C){
                    identificadorC+=(char) simb;
                    if(mapC.containsKey(identificadorC))
                        mapC.put(identificadorC, mapC.get(identificadorC) + 1);
                    else
                        mapC.put(identificadorC,1.0);
                    k=0;
                    identificadorC="";
                }
            }

            System.out.println("mapA = " + mapA.size());
            for(Map.Entry<String, Double> entry : mapA.entrySet()){
                entry.setValue(entry.getValue()/Math.floor(10000/A));
                System.out.println("key = " + entry.getKey() + "   value = " + entry.getValue() + "  Cantidad informacion = " +
                        cantidadInformacion(entry.getValue()));
            }
            System.out.println();
            for(Map.Entry<String, Double> entry : mapB.entrySet()){
                entry.setValue(entry.getValue()/Math.floor(10000/B));
                System.out.println("key = " + entry.getKey() + "   value = " + entry.getValue() + "  Cantidad informacion = " +
                        cantidadInformacion(entry.getValue()));
            }
            System.out.println();
            for(Map.Entry<String, Double> entry : mapC.entrySet()){
                entry.setValue(entry.getValue()/Math.floor(10000/C));
                System.out.println("key = " + entry.getKey() + "   value = " + entry.getValue() + "  Cantidad informacion = " +
                        cantidadInformacion(entry.getValue()));
            }

            mapEntropia.put(A,calculoEntropia(mapA,A));
            mapEntropia.put(B,calculoEntropia(mapB,B));
            mapEntropia.put(C,calculoEntropia(mapC,C));
            inecKraft();
            mapLongMedia.put(A,calculoLongMediaCodigo(mapA,A));
            mapLongMedia.put(B,calculoLongMediaCodigo(mapB,B));
            mapLongMedia.put(C,calculoLongMediaCodigo(mapC,C));
            codigoCompacto(A,mapEntropia.get(A),mapLongMedia.get(A));
            codigoCompacto(B,mapEntropia.get(B),mapLongMedia.get(B));
            codigoCompacto(C,mapEntropia.get(C),mapLongMedia.get(C));

            // Inciso d

            System.out.println("El rendimiento de " + A + " caracteres es " + rendimiento(A));
            System.out.println("El rendimiento de " + B + " caracteres es " + rendimiento(B));
            System.out.println("El rendimiento de " + C + " caracteres es " + rendimiento(C));

            System.out.println("La redundancia de " + A + " caracteres es " + (1.0 - rendimiento(A)));
            System.out.println("La redundancia de " + B + " caracteres es " + (1.0 - rendimiento(B)));
            System.out.println("La redundancia de " + C + " caracteres es " + (1.0 - rendimiento(C)));

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double cantidadInformacion(double value){
        return Math.log(value)/-Math.log(2);
    }
    public static double calculoEntropia(Map<String,Double> map, int cantChar){
        double auxentrop=0,auxprob=0;
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            auxprob=entry.getValue();
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(2));
        }
        System.out.println("Entropia de fuente de "+ cantChar + " caracteres = " + auxentrop);
        return auxentrop;
    }

    public static double calculoLongMediaCodigo(Map<String,Double> map, int cantChar){
        double aux=0;
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            aux+=entry.getValue()*entry.getKey().length();
        }
        System.out.println("Longitud media de codigo " + cantChar  + " = " + aux);
        return aux;
    }

    /*
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
            auxprob=entry.getValue()/Math.floor(10000/C);
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(2));
        }
        System.out.println("Entropia de fuente de "+ C + " caracteres = " + auxentrop);
    }
    */

    public static void inecKraft() {
        double valorAcum=0;
        for(Map.Entry<String, Double> entry : mapA.entrySet()) {
            valorAcum+=Math.pow(alfabetoCodigo,- entry.getKey().length());
        }
        System.out.println("la inecuacion de kraft para el primer caso es " + valorAcum);
        valorAcum=0;
        for(Map.Entry<String, Double> entry : mapB.entrySet()) {
            valorAcum+=Math.pow(alfabetoCodigo,- entry.getKey().length());
        }
        System.out.println("la inecuacion de kraft para el segundo caso es " + valorAcum);
        valorAcum=0;
        for(Map.Entry<String, Double> entry : mapC.entrySet()) {
            valorAcum+=Math.pow(alfabetoCodigo,- entry.getKey().length());
        }
        System.out.println("la inecuacion de kraft para el tercer caso es " + valorAcum);
    }

    /*
    public static void longitudMediaCodigo(){
        double aux=0;
        for(Map.Entry<String, Integer> entry : mapA.entrySet()) {
            aux+=(entry.getValue()/Math.floor(10000/A))*entry.getKey().length();
        }
        System.out.println("Longitud media de codigo " + A  + " = " + aux);

        aux=0;
        for(Map.Entry<String, Integer> entry : mapB.entrySet()) {
            aux+=(entry.getValue()/Math.floor(10000/B))*entry.getKey().length();
        }
        System.out.println("Longitud media de codigo " + B  + " = " + aux);

        aux=0;
        for(Map.Entry<String, Integer> entry : mapC.entrySet()) {
            aux+=(entry.getValue()/Math.floor(10000/C))*entry.getKey().length();
        }
        System.out.println("Longitud media de codigo " + C  + " = " + aux);
    }
    */
    public static void codigoCompacto(int cantChar,double entropia,double longMediaCodigo){
        if (entropia<=longMediaCodigo)
            System.out.println("El codigo " + cantChar + " es compacto");
        else
            System.out.println("El codigo " + cantChar + " no es compacto");
    }

    public static double rendimiento(int N) {
        double entrop = mapEntropia.get(N);
        double longit = mapLongMedia.get(N);
        return (entrop / longit);
    }

}


