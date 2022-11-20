package tp2proj;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.lang.Math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class Parte1 {

    static Map<String,Double> map = new HashMap<String,Double>();
    static Map<String,String> map2 = new HashMap<String,String>();
    static int palabrasTotales;

    public static void main(String[] args) {
        int simb;
        try (InputStream in = new FileInputStream("tp2_grupo11.txt");
                     Reader reader = new InputStreamReader(in)) {
            String palabra = "";
            System.out.println("Leyendo archivo...");
            while((simb = reader.read()) != -1){
                if(simb != ' ' && simb != '\n')
                   palabra += (char) simb;
                else{
                   if(map.containsKey(palabra))
                       map.put(palabra,map.get(palabra) + 1);
                   else{
                       map.put(palabra,1.0); 
                   }
                   palabra = "";
                } 
            }
            
            File file = new File("Ej1Diccionario.txt");
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Casos encontrados: " + map.size() + "\n");
            for(Map.Entry<String, Double> entry : map.entrySet()){
                //System.out.println("key: " + entry.getKey() + "  value: " + entry.getValue());
                bw.write("key: " + entry.getKey() + "  value: " + entry.getValue() + "\n");
                palabrasTotales += entry.getValue();
            }
            System.out.println(map.size());
            System.out.println(palabrasTotales);
            bw.close();
            fw.close();
            metodoHuffman(map);
            metodoShannonFano(map,map2);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
        //tasaDeCompresion("Huffman");
        //tasaDeCompresion("ShannonFano");
    }
    
    
    public static void metodoHuffman(Map <String,Double> auxmap) throws IOException {
        Map <String,Double> Huffmap = new HashMap<String,Double>();
        for(Map.Entry<String, Double> entry : auxmap.entrySet())
            Huffmap.put(entry.getKey(),entry.getValue());
        Huffman(Huffmap);
        File file = new File("Ej1DiccionarioBinario.txt");
        if (!file.exists())
            file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("Casos encontrados: " + map.size() + "\n");
        for(Map.Entry<String, Double> entry : Huffmap.entrySet()){
            //System.out.println("key: " + entry.getKey() + "  value: " + entry.getValue());
            bw.write("key: " + entry.getKey() + "  value: " + entry.getValue() + "\n");
        }
        bw.close();
        fw.close();
        System.out.println("Entropía para Huffman  " + calculoEntropia(Huffmap));
        System.out.println("Longitud media para Huffman  " + calculoLongMediaCodigo(Huffmap));
        System.out.println("Rendimiento para Huffman " + rendimiento(Huffmap));
        System.out.println("Redundancia para Huffman " + redundancia(Huffmap));
        List<Entry<String,Double>> origList = ordenar(map);
        List<Entry<String,Double>> binList = ordenar(Huffmap);
        LinkedHashMap<String,String> mapStringABinario = new LinkedHashMap<String,String>();
        for (int i= binList.size();i>0;i--)
            mapStringABinario.put(origList.get(i-1).getKey(),binList.get(i-1).getKey());
        codificacion(mapStringABinario,"Huffman",".Huf");
        decodificacion("Huffman",".Huf");
    }
    
    public static List ordenar(Map<String,Double> map){
        List<Entry<String,Double>> list = new ArrayList<Entry<String,Double>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
                        public int compare(Map.Entry<String, Double> ent1, Map.Entry<String, Double> ent2) {
                            return ent1.getValue().compareTo(ent2.getValue());
                        }});
        return list;
    }
    
    public static void Huffman(Map <String,Double> auxmap){
            String key1 = null,key2=null;
            double menor1=10000,menor2=10000;
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
                //System.out.println(key1 + "  " + key2);
                auxmap.remove(key1);
                auxmap.remove(key2);
                //System.out.println(auxmap.size());
                auxmap.put(key1,menor1 + menor2);
                Huffman(auxmap);
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
        
        double aux;
        
        LinkedHashMap<String,Double> mapOrdenado = ordenaMap(auxmap);
        recShannonFano(mapOrdenado, "", keyACodificado);
        
        Map <String,Double> mapBinarioYApariciones = new HashMap<String,Double>();
        for(Map.Entry<String, String> entry : keyACodificado.entrySet()){
            aux = buscaValueDouble(mapOrdenado,entry.getKey());
            mapBinarioYApariciones.put(entry.getValue(),aux);
        }
        
        System.out.println("Entropía para Shannon-Fano  " + calculoEntropia(mapBinarioYApariciones));
        System.out.println("Longitud media para Shannon-Fano  " + calculoLongMediaCodigo(mapBinarioYApariciones));
        System.out.println("Rendimiento para Shannon-Fano " + rendimiento(mapBinarioYApariciones));
        System.out.println("Redundancia para Shannon-Fano " + redundancia(mapBinarioYApariciones));
        
        
    }
    
    
    public static void recShannonFano(LinkedHashMap<String,Double> auxmap, String keyAcumulada, Map<String, String> salida) {
        if (auxmap.size() > 2) {
            LinkedHashMap <String, Double> mapIzq = new LinkedHashMap<String,Double>();
            LinkedHashMap <String, Double> mapDer = new LinkedHashMap<String,Double>();
            Iterator iteratorPalabras = new ArrayList<Entry<String,Double>>(auxmap.entrySet()).iterator();
            
            //Calcula probabilidad total del auxmap dado
            double probTotal = 0.0f;
            double probGrupoIzquierdo = 0.0f;
            for (double prob : auxmap.values()) {
                probTotal += prob;
            }
            //for (Map.Entry<String, Double> entry : listaPalabras)
            while (iteratorPalabras.hasNext())
            {
                Map.Entry<String, Double> entry = (Map.Entry<String, Double>)iteratorPalabras.next();
                if (probGrupoIzquierdo + entry.getValue() < (float)probTotal / 2) 
                {
                    mapIzq.put(entry.getKey(), entry.getValue());
                    probGrupoIzquierdo += entry.getValue();
                }
                //al meter la entrada en el grupo menor este tendría mas del 50% de probabilidad
                else 
                {
                    //Si al agregar la ultima entrada al grupo menor quedaria mas cerca de 50/50, agregarla. Caso contrario, se corta y pasa el resto al grupo derecho        
                    if (Math.abs(probGrupoIzquierdo + entry.getValue() - (float)probTotal / 2) < Math.abs(probGrupoIzquierdo - (float)probTotal / 2)) 
                    {
                        mapIzq.put(entry.getKey(), entry.getValue());
                    }
                    else 
                    {
                        mapDer.put(entry.getKey(), entry.getValue());
                    }
                    break;
                }
            }
            
            //pasar el resto a la derecha
            //for (Map.Entry<String, Double> entry : listaPalabras) 
            while (iteratorPalabras.hasNext())
            {
                Map.Entry<String, Double> entry = (Map.Entry<String, Double>)iteratorPalabras.next();
                mapDer.put(entry.getKey(), entry.getValue());
            }
            recShannonFano(mapIzq, keyAcumulada + "0", salida);
            recShannonFano(mapDer, keyAcumulada + "1", salida);
        }
        //termina recursion
        else {
            List<Entry<String,Double>> palabras = new ArrayList<Entry<String,Double>>(auxmap.entrySet());
            if (auxmap.size() == 1) {
                salida.put(palabras.get(0).getKey(), keyAcumulada);
            }
            else {
                for (Integer i = 0; i < palabras.size(); i++) {
                    salida.put(palabras.get(i).getKey(), keyAcumulada + i.toString());
                }
            }
        }
    }
    
    //Ordena probabilidades en orden descendiente
    public static LinkedHashMap<String, Double> ordenaMap(Map<String, Double> mapa) {
        List<Entry<String,Double>> listaOrdenada = new ArrayList<Entry<String,Double>>(mapa.entrySet());
        LinkedHashMap<String, Double> salida = new LinkedHashMap<String, Double>();
        Collections.sort(listaOrdenada, new Comparator<Map.Entry<String, Double>>(){
             public int compare(Map.Entry<String, Double> ent1, Map.Entry<String, Double> ent2) {
                return ent2.getValue().compareTo(ent1.getValue());
            }});
        for (Entry<String,Double> entrada : listaOrdenada){
            salida.put(entrada.getKey(), entrada.getValue());
        }
        return salida;
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
    
    public static double calculoEntropia(Map<String,Double> auxmap){
        double auxentrop=0,auxprob=0;
        for(Map.Entry<String, Double> entry : auxmap.entrySet()) {
            auxprob=entry.getValue()/ palabrasTotales;
            auxentrop+=auxprob*(Math.log(auxprob)/-Math.log(2));
        }
        return auxentrop;
    }
    
    public static double calculoLongMediaCodigo(Map<String,Double> auxmap){
        double aux=0;
        for(Map.Entry<String, Double> entry : auxmap.entrySet()) {
            aux+=entry.getValue()*entry.getKey().length();
        }
        aux=aux/palabrasTotales;
        return aux;
    }
    
    public static double rendimiento(Map<String,Double> auxmap) {
        return calculoEntropia(auxmap) / calculoLongMediaCodigo(auxmap);
    }
    
    public static double redundancia(Map<String,Double> auxmap) {
        return 1 - rendimiento(auxmap);
    }
    
    public static void codificacion(LinkedHashMap<String,String> auxmap,String metodo,String extension){
        try (InputStream in = new FileInputStream("tp2_grupo11.txt");
                    Reader reader = new InputStreamReader(in)) {
            int simb,bin,longmax = encontrarLongMaxima(auxmap);
            LinkedHashMap<String,Integer> codifMap = new LinkedHashMap<String,Integer> ();
            System.out.println(longmax);
            for(Map.Entry<String, String> entry : auxmap.entrySet()){
                String auxpalabra = entry.getValue();
                for(int i = auxpalabra.length()-1;i<longmax;i++){
                    auxpalabra += "0";
                }
                codifMap.put(entry.getKey(),Integer.parseInt(auxpalabra, 2));
            }
            /*for(Map.Entry<String, Integer> entry : codifMap.entrySet()){
                System.out.println("key: " + entry.getKey() + "  value: " + entry.getValue());
            }*/
            LinkedHashMap<String,Integer> mapOrdenado = ordenaMapInteger(codifMap);
            File file2 = new File(metodo + "ChequeamosTabla.txt");
            if (!file2.exists())
                file2.createNewFile();
            FileWriter fw2 = new FileWriter(file2);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            for(Map.Entry<String, Integer> entry : codifMap.entrySet()){
                bw2.write("key: " + entry.getKey() + "  value: " + entry.getValue() + "\n");
            }
            bw2.close();
            fw2.close();
            
            
            String nombreArchivo2 = "tablaSola.Huf";
            File archivo2 = new File(nombreArchivo2);
            FileOutputStream fos2 = new FileOutputStream(archivo2);
            ObjectOutputStream escribir2 = new ObjectOutputStream(fos2);
            escribir2.writeObject(mapOrdenado);
            escribir2.close();
            fos2.close();
            
            String nombreArchivo = metodo + "Codificado" + extension,palabra="";
            File archivo = new File(nombreArchivo);
            FileOutputStream fos = new FileOutputStream(archivo);
            ObjectOutputStream escribir = new ObjectOutputStream(fos);
            //escribir.write(longmax);
            escribir.writeObject(mapOrdenado); 
            
            File file3 = new File(metodo + "textoEnInt.txt");
            if (!file2.exists())
                file2.createNewFile();
            FileWriter fw3 = new FileWriter(file3);
            BufferedWriter bw3 = new BufferedWriter(fw3);
            
            
            while ((simb = reader.read()) != -1){
                if(simb != ' ' && simb != '\n')
                   palabra += (char) simb;
                else{
                   bin = buscaValueInt(codifMap, palabra);
                   escribir.write(bin);
                   palabra = "";
                    
                    
                   bw3.write(bin + "\n");
                    
                }
            }
            escribir.close();
            fos.close();
            
            bw3.close();
            fw3.close();
            
            
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int buscaValueInt(LinkedHashMap<String,Integer> auxmap,String buscado){
        for(Map.Entry<String,Integer> entry : auxmap.entrySet()) {
            if(entry.getKey().equals(buscado)){
                return entry.getValue();
            }
        }
        return -1; //No deberia llegar aca
    }
    
    public static double buscaValueDouble(LinkedHashMap<String,Double> auxmap,String buscado){
        for(Map.Entry<String,Double> entry : auxmap.entrySet()) {
            if(entry.getKey().equals(buscado)){
                return entry.getValue();
            }
        }
        return -1; //No deberia llegar aca
    }
    
    public static void decodificacion(String metodo,String extension){
        File file= new File(metodo + "Codificado" + extension);
        LinkedHashMap <String, Integer> auxmap = new LinkedHashMap<String,Integer>();
        if(file.exists()){
            try {
                int simb,cantBytes,auxBytesContador=0;
                String aux="",palabra,auxlongitud;
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream objectStream = new ObjectInputStream(inputStream);
                System.out.println("El archivo existe");
                cantBytes = 2; 
                System.out.println("Cantidad de bytes  " + cantBytes);
                auxmap = (LinkedHashMap <String, Integer>) objectStream.readObject();
                for(Map.Entry<String, Integer> entry : auxmap.entrySet()){
                    //System.out.println("key: " + entry.getKey() + "  value: " + entry.getValue());
                }
                File file2 = new File(metodo + "ArchivoDecodificado.txt");
                if (!file2.exists())
                    file2.createNewFile();
                FileWriter fw2 = new FileWriter(file2);
                BufferedWriter bw2 = new BufferedWriter(fw2);
                
                File file3 = new File(metodo + "textoEnIntRecuperado.txt");
                if (!file2.exists())
                    file2.createNewFile();
                FileWriter fw3 = new FileWriter(file3);
                BufferedWriter bw3 = new BufferedWriter(fw3);
                
                while((simb = inputStream.read()) != -1){
                    /*Habria que agarrar valor por valor.
                      A cada valor que agarramos, crearle un String que muestre su valor binario
                      y luego buscar este en el HashMap recuperado.
                      Luego de esto habria que escribir la palabra en el archivo e ingresar un espacio*/
                    //aux = Integer.toBinaryString(simb);
                    bw3.write(simb + "\n");
                    if(auxBytesContador<cantBytes){
                        auxlongitud = Integer.toBinaryString(simb);
                        for(int i = auxlongitud.length()-1;i<8;i++){
                            auxlongitud = "0" + auxlongitud;
                        }
                        aux += auxlongitud;
                        auxBytesContador++;
                    }else if(auxmap.containsValue(Integer.parseInt(aux, 2))){
                            System.out.println("entro");
                            palabra=buscaKey(auxmap,Integer.parseInt(aux, 2));
                            bw2.write(palabra + " ");
                            auxBytesContador = 0;
                            aux = "";
                    }
                }
                bw2.close();
                fw2.close();
                objectStream.close();
                inputStream.close();
                
                bw3.close();
                fw3.close();
                
                
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        } 
    }
    
    public static String buscaKey(LinkedHashMap <String, Integer> auxmap, int binario){
        
        for(Map.Entry<String, Integer> entry : auxmap.entrySet()){
            if(entry.getValue() == binario)
                return entry.getKey();
        }
        
        return null; //No deberia llegar aca
    }
    
    public static ArrayList<Boolean> StringABinario(String str) {
            ArrayList<Boolean> salida = new ArrayList<Boolean>();
            
            for (char c : str.toCharArray()) {
                if (c == '0'){
                    salida.add(false);
                }
                else {
                    salida.add(true);
                }
            }
            return salida;
    }
    
    public static int encontrarLongMaxima(LinkedHashMap<String,String> auxmap){
        int longitud =0;
        for(Map.Entry<String, String> entry : auxmap.entrySet()){
            if(entry.getValue().length()>longitud)
                longitud = entry.getValue().length();
        }
        while(longitud % 8 != 0){
            longitud++;
        }
        return longitud;
    }
    
    //Ordena probabilidades en orden descendiente
    public static LinkedHashMap<String, Integer> ordenaMapInteger(Map<String, Integer> mapa) {
        List<Entry<String,Integer>> listaOrdenada = new ArrayList<Entry<String,Integer>>(mapa.entrySet());
        LinkedHashMap<String, Integer> salida = new LinkedHashMap<String, Integer>();
        Collections.sort(listaOrdenada, new Comparator<Map.Entry<String, Integer>>(){
             public int compare(Map.Entry<String, Integer> ent1, Map.Entry<String, Integer> ent2) {
                return ent2.getValue().compareTo(ent1.getValue());
            }});
        for (Entry<String,Integer> entrada : listaOrdenada){
            salida.put(entrada.getKey(), entrada.getValue());
        }
        return salida;
    }
}
