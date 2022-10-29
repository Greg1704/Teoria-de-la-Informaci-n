package ejercicios;

import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

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
        try (InputStream in = new FileInputStream("datosGrupo11.txt");
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
            double apariciones;
            System.out.println("mapA = " + mapA.size());
            File file = new File("Ej2IncisoA_CantidadDeInformacion.txt");
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Palabras de 3 caracteres\n");
            bw.write("Casos encontrados: " + mapA.size() + "\n");
            for(Map.Entry<String, Double> entry : mapA.entrySet()){
                apariciones = entry.getValue();
                entry.setValue(entry.getValue()/Math.floor(10000/A));
                System.out.println("Simbolo = " + entry.getKey() + "  cantidad de apariciones = " + apariciones + "   probabilidad = "
                        + entry.getValue() + "  Cantidad informacion = " + cantidadInformacion(entry.getValue()) + " unidades de orden 3");
                bw.write("Simbolo = " + entry.getKey() + "  cantidad de apariciones = " + apariciones + "   probabilidad = "
                        + entry.getValue() + "  Cantidad informacion = " + cantidadInformacion(entry.getValue()) + " unidades de orden 3\n");
            }
            System.out.println();
            bw.write("\n\nPalabras de 5 caracteres\n");
            bw.write( "Casos encontrados: " + mapB.size() + "\n");
            for(Map.Entry<String, Double> entry : mapB.entrySet()){
                apariciones = entry.getValue();
                entry.setValue(entry.getValue()/Math.floor(10000/B));
                System.out.println("Simbolo = " + entry.getKey() + "  cantidad de apariciones = " + apariciones + "   probabilidad = "
                        + entry.getValue() + "  Cantidad informacion = " + cantidadInformacion(entry.getValue()) + " unidades de orden 3");
                bw.write("Simbolo = " + entry.getKey() + "  cantidad de apariciones = " + apariciones + "   probabilidad = "
                        + entry.getValue() + "  Cantidad informacion = " + cantidadInformacion(entry.getValue()) + " unidades de orden 3\n");
            }
            System.out.println();
            bw.write("\n\nPalabras de 7 caracteres\n");
            bw.write( "Casos encontrados: " + mapC.size() + "\n");
            for(Map.Entry<String, Double> entry : mapC.entrySet()){
                apariciones = entry.getValue();
                entry.setValue(entry.getValue()/Math.floor(10000/C));
                System.out.println("Simbolo = " + entry.getKey() + "  cantidad de apariciones = " + apariciones + "   probabilidad = "
                        + entry.getValue() + "  Cantidad informacion = " + cantidadInformacion(entry.getValue()) + " unidades de orden 3");
                bw.write("Simbolo = " + entry.getKey() + "  cantidad de apariciones = " + apariciones + "   probabilidad = "
                        + entry.getValue() + "  Cantidad informacion = " + cantidadInformacion(entry.getValue()) + " unidades de orden 3\n");
            }
            bw.close();
            fw.close();
            
            File file1 = new File("Ej2IncisoA_Entropia.txt");
            if (!file1.exists())
                file1.createNewFile();
            FileWriter fw1 = new FileWriter(file1);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            
            mapEntropia.put(A,calculoEntropia(mapA,A));
            bw1.write("Entropia de fuente de "+ A + " caracteres es = " + mapEntropia.get(A) + "\n");
            mapEntropia.put(B,calculoEntropia(mapB,B));
            bw1.write("Entropia de fuente de "+ B + " caracteres es = " + mapEntropia.get(B) + "\n");
            mapEntropia.put(C,calculoEntropia(mapC,C));
            bw1.write("Entropia de fuente de "+ C + " caracteres es = " + mapEntropia.get(C) + "\n");
            bw1.close();
            fw1.close();
            
            inecKraft();
            
            File file3 = new File("Ej2IncisoCLongMediaCodigo.txt");
            if (!file3.exists())
                file3.createNewFile();
            FileWriter fw3 = new FileWriter(file3);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            
            mapLongMedia.put(A,calculoLongMediaCodigo(mapA,A));
            bw3.write("Longitud media de codigo de " + A  + " caracteres es = " + calculoLongMediaCodigo(mapA,A) + "\n");
            mapLongMedia.put(B,calculoLongMediaCodigo(mapB,B));
            bw3.write("Longitud media de codigo de " + B  + " caracteres es = " + calculoLongMediaCodigo(mapB,B) + "\n");
            mapLongMedia.put(C,calculoLongMediaCodigo(mapC,C));
            bw3.write("Longitud media de codigo de " + C  + " caracteres es = " + calculoLongMediaCodigo(mapC,C) + "\n");
            bw3.close();
            fw3.close();
            
            codigoCompacto(mapA,A);
            codigoCompacto(mapB,B);
            codigoCompacto(mapC,C);

            // Inciso d
            File file2 = new File("Ej2IncisoD.txt");
            if (!file2.exists())
                file2.createNewFile();
            FileWriter fw2 = new FileWriter(file2);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            
            System.out.println("El rendimiento de " + A + " caracteres es " + rendimiento(A));
            bw2.write("El rendimiento de " + A + " caracteres es " + rendimiento(A) + "\n");
            System.out.println("El rendimiento de " + B + " caracteres es " + rendimiento(B));
            bw2.write("El rendimiento de " + B + " caracteres es " + rendimiento(B) + "\n");
            System.out.println("El rendimiento de " + C + " caracteres es " + rendimiento(C));
            bw2.write("El rendimiento de " + C + " caracteres es " + rendimiento(C) + "\n");

            System.out.println("La redundancia de " + A + " caracteres es " + (1.0 - rendimiento(A)));
            bw2.write("La redundancia de " + A + " caracteres es " + (1.0 - rendimiento(A)) + "\n");
            System.out.println("La redundancia de " + B + " caracteres es " + (1.0 - rendimiento(B)));
            bw2.write("La redundancia de " + B + " caracteres es " + (1.0 - rendimiento(B)) + "\n");
            System.out.println("La redundancia de " + C + " caracteres es " + (1.0 - rendimiento(C)) + "\n");
            bw2.write("La redundancia de " + C + " caracteres es " + (1.0 - rendimiento(C)) + "\n");
            bw2.close();
            fw2.close();
            
            Huffman(mapA,"HuffmanA.txt",A);
            System.out.println("\n");
            Huffman(mapB,"HuffmanB.txt",B);
            System.out.println("\n");
            Huffman(mapC,"HuffmanC.txt",C);


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double cantidadInformacion(double value){
        return Math.log(value)/-Math.log(3);
    }
    public static double calculoEntropia(Map<String,Double> map, int cantChar){
        double auxentrop=0,auxprob=0;
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            auxprob=entry.getValue();
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(3));
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


    public static void inecKraft() {
        try{
            double valorAcum=0;
            File file = new File("Ej2IncisoCKraft.txt");
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for(Map.Entry<String, Double> entry : mapA.entrySet()) {
                valorAcum+=Math.pow(alfabetoCodigo,- entry.getKey().length());
            }
            System.out.println("la inecuacion de kraft para el caso de " + A + " caracteres es " + valorAcum);
            bw.write("la inecuacion de kraft para el caso de " + A + " caracteres es " + valorAcum + "\n");
            valorAcum=0;
            for(Map.Entry<String, Double> entry : mapB.entrySet()) {
                valorAcum+=Math.pow(alfabetoCodigo,- entry.getKey().length());
            }
            System.out.println("la inecuacion de kraft para el caso de " + B + " caracteres es " + valorAcum);
            bw.write("la inecuacion de kraft para el caso de " + A + " caracteres es " + valorAcum + "\n");
            valorAcum=0;
            for(Map.Entry<String, Double> entry : mapC.entrySet()) {
                valorAcum+=Math.pow(alfabetoCodigo,- entry.getKey().length());
            }
            System.out.println("la inecuacion de kraft para el caso de " + C + " caracteres es " + valorAcum);
            bw.write("la inecuacion de kraft para el caso de " + C + " caracteres es " + valorAcum + "\n");
            bw.close();
            fw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void codigoCompacto(Map<String,Double> map,int cantChar){
        boolean compacto;
        compacto = condicionCompacto(map);
        if(compacto)
            System.out.println("El codigo " + cantChar + " es compacto");   
        else
            System.out.println("El codigo " + cantChar + " no es compacto");
    }
    
    public static boolean condicionCompacto(Map<String,Double> map){
        for(Map.Entry<String, Double> entry : map.entrySet()){
            //System.out.println(entry.getKey() + " " + Math.log(entry.getValue())/-Math.log(3));
            if(entry.getKey().length() - 0.05 >= (Math.log(entry.getValue())/-Math.log(3)) || 
               (Math.log(entry.getValue())/-Math.log(3))>= entry.getKey().length() + 0.05)
                return false;
        }  
        return true;
    }

    public static double rendimiento(int N) {
        double entrop = mapEntropia.get(N);
        double longit = mapLongMedia.get(N);
        return (entrop / longit);
    }

    public static void Huffman(Map<String,Double> map,String archivo,int cantSimbolos){
        Map <String,Double> Huffmap = new HashMap<String,Double>();
        for(Map.Entry<String, Double> entry : map.entrySet())
           Huffmap.put(entry.getKey(),entry.getValue()); //TAL VEZ HABRIA QUE INICIALIZAR LAS KEYS DE OTRA MANERA


        metodoHuffman(Huffmap);

        /*for(Map.Entry<String, Double> entry : map.entrySet())
            System.out.println("entry = " + entry.getKey() + "  " + entry.getValue());
        for(Map.Entry<String, Double> entry : Huffmap.entrySet())
            System.out.println("entry = " + entry.getKey() + "  " + entry.getValue()); */
        try {
            File file = new File(archivo);

            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            List<Entry<String,Double>> binList = ordenar(Huffmap);
            for (int i= binList.size();i>0;i--){
                //System.out.println("  key = " + binList.get(i-1).getKey() + "  value = " + binList.get(i-1).getValue());
                bw.write(binList.get(i-1).getKey() + "  " + binList.get(i-1).getValue() + "\n");
            }
            bw.close();
            fw.close();
            List<Entry<String,Double>> origList = ordenar(map);
            Map<Integer,Integer> mapStringABinario = new HashMap<Integer,Integer>();
            for (int i= binList.size();i>0;i--)
            mapStringABinario.put(stringAInteger(origList.get(i-1).getKey()),Integer.parseInt(binList.get(i-1).getKey(),2));
            reconstruccionArchivo(mapStringABinario,cantSimbolos);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void metodoHuffman(Map <String,Double> auxmap){
        String key1 = null,key2=null;
        double menor1=1,menor2=1;
        if(auxmap.size()>2){
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
            auxmap.remove(key1);
            auxmap.remove(key2);
            auxmap.put(key1,menor1 + menor2);
            metodoHuffman(auxmap);
            //Metodo inverso donde se le asigna a cada valor su key binaria
            String key3=recuperaKeyMenor(auxmap,menor1,menor2);
            auxmap.remove(key3);
            key1=key3 + "0";
            key2=key3 + "1";
            auxmap.put(key1,menor1);
            auxmap.put(key2,menor2);

        }else{
            //Caso SE TERMINO PASO 1
            int i=0;
            for(Map.Entry<String, Double> entry : auxmap.entrySet()) {
                //System.out.println("entry.getKey() = " + entry.getKey() + " entry.getValue() = " + entry.getValue());
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
            for (i=0;i<2;i++){
                if (i==0)
                    auxmap.put(key1,menor1);
                else
                    auxmap.put(key2,menor2);
            }
            System.out.println("\n");
            System.out.println("\nPASO 1 COMPLETADO");
            System.out.println("\n");
            for(Map.Entry<String, Double> entry : auxmap.entrySet())
                System.out.println("entry.getKey() = " + entry.getKey() + " entry.getValue() = " + entry.getValue());
            System.out.println("\n");
            System.out.println("INICIA PASO 2\n");
        }
    }

    public static String recuperaKeyMenor(Map <String,Double> auxmap,double menor1,double menor2){
        for(Map.Entry<String, Double> entry : auxmap.entrySet()){
            if (entry.getValue() == menor1+menor2)
                return entry.getKey();
        }
        System.out.println("Esto no deberia ocurrir");
        return null;
    }

    public static List ordenar(Map<String,Double> map){
        List<Entry<String,Double>> list = new ArrayList<Entry<String,Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
                         public int compare(Map.Entry<String, Double> ent1, Map.Entry<String, Double> ent2) {
                            return ent1.getValue().compareTo(ent2.getValue());
                        }});
        return list;
    }

    public static void reconstruccionArchivo(Map<Integer,Integer> map,int cantSimbolos){
        try (InputStream in = new FileInputStream("datosGrupo11.txt");
             Reader reader = new InputStreamReader(in)) {
            int simb,i=0,bin;
            String nombreArchivo = "Codificado" + cantSimbolos + ".dat";
            String identificador="";
            File archivo = new File(nombreArchivo);
            FileOutputStream fos = new FileOutputStream(archivo);
            ObjectOutputStream escribir = new ObjectOutputStream(fos);
            escribir.writeObject(map); //Ingreso tabla como header
            while ((simb = reader.read()) != -1) {
                i++;
                if (i < cantSimbolos) {
                    identificador += (char) simb;
                } else if (i == cantSimbolos) {
                    identificador += (char) simb;
                    bin=buscaValue(map,identificador);
                    escribir.write(bin);
                    i=0;
                    identificador="";
                }
            }
            escribir.close();
            fos.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer buscaValue(Map<Integer,Integer> map,String buscado){
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getKey() == stringAInteger(buscado))
                return entry.getValue();
        }
        return null; //Nunca deberia llegar a este return
    }
    
    public static int stringAInteger(String alf){
            int aux=0;
            for (int i=0;i<alf.length();i++){
                aux+=(int) alf.indexOf(i);
            }
            return aux;
    }
}


