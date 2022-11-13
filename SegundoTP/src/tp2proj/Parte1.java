package tp2proj;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;

import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.lang.Math;

public class Parte1 {


    public static void main(String[] args) {
        tasaDeCompresion("Huffman");
        tasaDeCompresion("ShannonFano");
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
    
    public static void metodoShannonFano(Map <String,Double> auxmap, Map <String, String> keyACodificado){
        Map<String,Double> mapOrdenado = new HashMap<String,Double>();
        mapOrdenado.putAll(auxmap);
        
        
        
    }
    
    public static void recShannonFano(LinkedHashMap<String,Double> auxmap, String keyAcumulada, Map <String, String> salida) {
        if (auxmap.size() > 2) {
            LinkedHashMap <String, Double> mapIzq = new LinkedHashMap<String,Double>();
            LinkedHashMap <String, Double> mapDer = new LinkedHashMap<String,Double>();
            
            //Calcula probabilidad total del auxmap dado
            double probTotal = 0.0f;
            double probGrupoMenor = 0.0f;
            for (double prob : auxmap.values()) {
                probTotal += prob;
            }
            for (Map.Entry<String, Double> entry : auxmap.entrySet())
            {
                if (probGrupoMenor + entry.getValue() < (float)probTotal / 2) 
                {
                    auxmap.remove(entry);
                    mapIzq.put(entry.getKey(), entry.getValue());
                    probGrupoMenor += entry.getValue();
                }
                //al meter la entrada en el grupo menor este tendría mas del 50% de probabilidad
                else 
                {
                    //Si al agregar la ultima entrada al grupo menor quedaria mas cerca de 50/50, agregarla. Caso contrario, se pasa el resto al grupo        
                    if (Math.abs(probGrupoMenor + entry.getValue() - (float)probTotal / 2) < Math.abs(probGrupoMenor - (float)probTotal / 2)) 
                    {
                        auxmap.remove(entry);
                        mapIzq.put(entry.getKey(), entry.getValue());
                        probGrupoMenor += entry.getValue();
                    }
                }
            }
            //pasar el resto a la derecha
            for (Map.Entry<String, Double> entry : auxmap.entrySet()) 
            {
                auxmap.remove(entry);
                mapDer.put(entry.getKey(), entry.getValue());
            }
            recShannonFano(mapIzq, keyAcumulada + "0", salida);
            recShannonFano(mapDer, keyAcumulada + "1", salida);
        }
        
        
    }
    
    public static void tasaDeCompresion(String metodoUsado){
        File file = new File("tp2_grupo11.txt");
        File file2 = new File("FALTAASINGARNOMBRE.TXT"); //Seria el archivo comprimido
        System.out.println("La tasa de compresion es de " + (file.length() - file2.length()) + " caracteres");
        try{
            File file3 = new File("Parte1TasaDeCompresion" + metodoUsado +".txt");
            if (!file3.exists())
                file3.createNewFile();
            FileWriter fw3 = new FileWriter(file3);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            bw3.write("La tasa de compresion es de " + (file.length() - file2.length()) + " caracteres");
            bw3.close();
            fw3.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static double calculoEntropia(Map<String,Double> map){
        double auxentrop=0,auxprob=0;
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            auxprob=entry.getValue();
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(2));
        }
        System.out.println("Entropia de fuente post-Huffman = " + auxentrop);
        return auxentrop;
    }
    
    public static double calculoLongMediaCodigo(Map<String,Double> map){
        double aux=0;
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            aux+=entry.getValue()*entry.getKey().length();
        }
        System.out.println("Longitud media de codigo de la fuente post-Huffman es " + aux);
        return aux;
    }
    
    public static double rendimiento(Map<String,Double> map) {
        return calculoEntropia(map) / calculoLongMediaCodigo(map);
    }
    
    public static double rendundancia(Map<String,Double> map) {
        return 1 - rendimiento(map);
    }
}
