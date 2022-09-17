import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class Ej2 {
    static Map<String,Integer> map3 = new HashMap<String,Integer>();
    public static void main(String[] args) {
        leerArch();
    }

    public static void leerArch(){
        int simb;
        String identificador= "";
        int i = 0;
        try (InputStream in = new FileInputStream("src/datosGrupo11.txt");
             Reader reader = new InputStreamReader(in)) {
            while ((simb = reader.read()) != -1){
                i++;
                if(i < 3){
                    identificador+= (char) simb;
                }else{
                    identificador+= (char) simb;
                    System.out.println("identificador = " + identificador);
                    if(map3.containsKey(identificador))
                        map3.put(identificador,map3.get(identificador) + 1);
                    else
                        map3.put(identificador,0);
                    i=0;
                    identificador="";
                }
            }
            System.out.println("map3 = " + map3.size());
            map3.forEach((key,value) ->{
                System.out.println("key = " + key + "   value = " + value);
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
