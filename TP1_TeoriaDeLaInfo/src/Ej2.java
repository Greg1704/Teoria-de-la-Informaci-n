import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
        try (InputStream in = new FileInputStream("TP1_TeoriaDeLaInfo/src/datosGrupo11.txt");
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
            System.out.println("La redundancia de " + C + " caracteres es " + (1.0 - rendimiento(C)) + "\n");

           /* Huffman(mapA,"HuffmanA");
            System.out.println("\n");
            Huffman(mapB,"HuffmanB");
            System.out.println("\n");
            Huffman(mapC,"HuffmanC");
            */

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
        System.out.println("Entropia de fuente de "+ cantChar + " caracteres es = " + auxentrop);
        return auxentrop;
    }

    public static double calculoLongMediaCodigo(Map<String,Double> map, int cantChar){
        double aux=0;
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            aux+=entry.getValue()*entry.getKey().length();
        }
        System.out.println("Longitud media de codigo de " + cantChar  + " caracteres es = " + aux);
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

    public static void Huffman(Map<String,Double> map,String archivo){
        Map <String,Double> Huffmap = new HashMap<String,Double>();
        for(Map.Entry<String, Double> entry : map.entrySet())
           Huffmap.put(entry.getKey(),entry.getValue()); //TAL VEZ HABRIA QUE INICIALIZAR LAS KEYS DE OTRA MANERA


        metodoHuffman(Huffmap);

        try {
            File file = new File(archivo);

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            int i=1;
            for(Map.Entry<String, Double> entry : Huffmap.entrySet()) {
                System.out.println(i + "  key = " + entry.getKey() + "  value = " + entry.getValue());
                bw.write(i + "  key = " + entry.getKey() + "  value = " + entry.getValue() + "\n");
                i++;
            }
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void metodoHuffman(Map <String,Double> auxmap){
        String key1 = null,key2=null;
        double menor1=1,menor2=1;
        if(auxmap.size()>2){
            //Caso PASO 1 EN PROGRESO
            for(Map.Entry<String, Double> entry : auxmap.entrySet()){
                if(entry.getValue()<menor1){
                    if(menor1<menor2) {
                        key2 = key1;
                        menor2 = menor1;
                    }
                    key1= entry.getKey();
                    menor1=entry.getValue();
                }else if(entry.getValue()<menor2){
                    key2= entry.getKey();
                    menor2=entry.getValue();
                }
            } //Del for salen las dos keys con menores probabilidades
            auxmap.remove(key1,menor1); //Por ahi es al pedo(Consultar con GREGO)
            auxmap.remove(key2,menor2);
            auxmap.put(key1,menor1 + menor2);
            metodoHuffman(auxmap);
            //Metodo inverso donde se le asigna a cada valor su key binaria
            String key3=recuperaKeyMenor(auxmap,menor1,menor2);
            auxmap.remove(key3,menor1+menor2);
            key1=key3 + "0";
            key2=key3 + "1";
            auxmap.put(key1,menor1);
            auxmap.put(key2,menor2);

        }else{
            //Caso SE TERMINO PASO 1
            int i=0;
            for(Map.Entry<String, Double> entry : auxmap.entrySet()) {
                System.out.println("entry.getKey() = " + entry.getKey() + " entry.getValue() = " + entry.getValue());
                if(i==0){
                    key1="0";
                    menor1=entry.getValue();
                }else{
                    key2="1";
                    menor2=entry.getValue();
                }
                i++;
            }
            auxmap.clear();
            for (i=0;i<2;i++){ //A REVISAR(NO TERMINA DE FUNCIONAR ESTO)
                if (i==0)
                    auxmap.put(key1,menor1);
                else
                    auxmap.put(key2,menor2);
            }
            System.out.println("\n");
            for(Map.Entry<String, Double> entry : auxmap.entrySet())
                System.out.println("entry.getKey() = " + entry.getKey() + " entry.getValue() = " + entry.getValue());
            System.out.println("\nPASO 1 COMPLETADO");
            System.out.println("INICA PASO 2\n");

        }
    }

    public static String recuperaKeyMenor(Map <String,Double> auxmap,double menor1,double menor2){
        for(Map.Entry<String, Double> entry : auxmap.entrySet()){
            if (entry.getValue() == menor1+menor2)
                return entry.getKey();
        }
        System.out.println("Esto no deberia ocurrir");
        return null; //No deberia ocurrir nunca
    }
}


