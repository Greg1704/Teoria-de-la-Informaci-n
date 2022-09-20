import java.io.*;

import java.util.HashMap;
import java.util.Map;

public class Ej2 {
    static Map<String,Integer> map3 = new HashMap<String,Integer>();
    static Map<String,Integer> map5 = new HashMap<String,Integer>();
    static Map<String,Integer> map7 = new HashMap<String,Integer>();
    public static void main(String[] args) {
        leerArch();
    }

    public static void leerArch(){
        int simb;
        String identificador3= "", identificador5 ="", identificador7 ="";
        int i = 0,j=0,k=0; //i para 3 caracteres,j para 5, y k para 7
        try (InputStream in = new FileInputStream("src/datosGrupo11.txt");
             Reader reader = new InputStreamReader(in)) {
            while ((simb = reader.read()) != -1){
                i++;
                j++;
                k++;
                if(i < 3){
                    identificador3+= (char) simb;
                }else if(i == 3){
                    identificador3+= (char) simb;
                    if(map3.containsKey(identificador3))
                        map3.put(identificador3,map3.get(identificador3) + 1);
                    else
                        map3.put(identificador3,1);
                    i=0;
                    identificador3="";
                }
                if(j<5){
                    identificador5+=(char) simb;
                }else if(j == 5){
                    identificador5+=(char) simb;
                    if(map5.containsKey(identificador5))
                        map5.put(identificador5,map5.get(identificador5) + 1);
                    else
                        map5.put(identificador5,1);
                    j=0;
                    identificador5="";
                }
                if(k<7){
                    identificador7+=(char) simb;
                }else if(k == 7){
                    identificador7+=(char) simb;
                    if(map7.containsKey(identificador7))
                        map7.put(identificador7,map7.get(identificador7) + 1);
                    else
                        map7.put(identificador7,1);
                    k=0;
                    identificador7="";
                }
            }
            System.out.println("map3 = " + map3.size());
            map3.forEach((key,value) ->{
                System.out.println("key = " + key + "   value = " + value + "  Cantidad informacion = " +
                        cantidadInformacion(value,Math.floor(10000/3)));
            });
            System.out.println();
            System.out.println("map5 = " + map5.size());
            map5.forEach((key,value) ->{
                System.out.println("key = " + key + "   value = " + value + "  Cantidad informacion = " +
                        cantidadInformacion(value,Math.floor(10000/5)));
            });
            System.out.println("map7= " + map7.size());
            map7.forEach((key,value) ->{
                System.out.println("key = " + key + "   value = " + value + "  Cantidad informacion = " +
                        cantidadInformacion(value,Math.floor(10000/7)));
            });

            //Faltaria calcular entropia de cada una de las fuentes
            //Faltaria calcular entropia de cada una de las fuentes
            //Faltaria calcular entropia de cada una de las fuentes
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double cantidadInformacion(int value,double size){
        return Math.log(1/((double)value/size));
    }
}
