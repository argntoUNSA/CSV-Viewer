    import java.util.*;
    import static com.github.chrisgleissner.jutil.table.TablePrinter.DefaultTablePrinter;
    /**Esta clase permite visualizar, segun el criterio del usuario, el contenido de un archivo CSV.
    No edita, ni crea archivos CSV.
    El formato que soporta la aplicacion es CSV (valores separados por comas), con otro tipo de formato se obtenran errores
    *Los atributos del visor, permiten el manejo de parametros pasados por consola*/
    public class CSVViewer{
    
    private static Map<String,String> opciones;
    private static String argumentos, direccion;
    private static CSV csvFile;
    
    /**Este metodo ejecuta de la misma manera el proceso del programa original, solo se refactorizo, es decir se mantiene el orden y la metodologia de las funcionalidades pricipales pero se las reorganizo.
     * Primero toma la direccion y crea el archivo, luego toma otros parametros. Por lo que si no se ingresa el parametro -p no se ejecutara el programa normalmente
     * Existe una jerarquia que se debe tener en cuenta, si se iongresa el parametro -j no se tomaran en cuenta los otros.
     * Otro ejemplo es si se quiere filtrar y luego eliminar filas, o viceversa, se hara en un mismo orden. primero elimina las filas y luego filtra
     * El orden es el siguiente:
     *  *-p Tomara el string siguiente como direccion
     *  *-j Imprimira en formato JSON el contenido del archivo CSV, sin tomar en cuenta los otros parametros
     *  *-s Ordenara (segun le señale el String siguiente al parametro) una columna en orden acendente(ASC) y descendente (DESC).
     *  *-l Modifica la cantidad de filas a ser mostradas, debe ser un numero entero positivo
     *  *-f Filtrara (segun le señale el String siguiente al parametro) una columna segun cumpla o no una condicion (tambien señalada en el Strign a continuacion del parametro).
     *  *-o Ordenara (segun le señale el String siguiente al parametro) las columnas, sean todas o sean algunas. Cuando solo son algunas, se mostraran las que sean nombradas en el orden nombrado.
     * Se debe tener en cuenta esta jerarquia para evitar resultados innesperados. Otra cuestion a tener en cuenta es la sintaxis, si no se la respeto en el ingreso de los parametros entonces se produciran errores de ejecucion en el futuro. 
       */
    public static void main(String [] args){
        if(args.length==0){
            showMenu();
        }else{
            creaOpciones(args);
            if(getValor("p")!="." && getValor("p").length()>0){
                setDireccion(getValor("p"));
                csvFile=new CSV(getDireccion());
                if(csvFile.esArchivoCSV()){
                    if(existeOpcion("j")){
                        System.out.println(csvFile.JSON());
                    }else{
                       if(existeOpcion("s")){
                           //Sort
                           String sort_str = getValor("s");
                           String[] split = sort_str.split(" ");
                           boolean order = true;
                           if (split.length == 2) {
                                if (split[1].equals("DESC"))
                                    order = false;
                           }
                           csvFile.Sort(split[0], order);
                       }
                       if(existeOpcion("l")){
                            //Limit
                            int limite = Integer.parseInt(getValor("l"));
                            csvFile.Limit(limite);
                       }
                       if(existeOpcion("f")){
                            //Filter
                            String condition = getValor("f");
                            String[] split = condition.split(" ");
        
                            csvFile.Filter(split[0],split[1],Double.parseDouble(split[2]));
                       }
                       if(existeOpcion("o")){
                            //Order
                            String[] columnas = getValor("o").split(",");
                            csvFile.Order(columnas);
                       }
                        
                        print(csvFile.getCabecera(), csvFile.getCuerpo());
                    }
                }else{
                    System.out.println("Ingrese direccion de un archivo .CSV");
                }
                
            }
            else{
                System.out.println("No se ingreso ninguna direccion");
            }
            
        }
    }
    
    
    /**Este metodo modifica el atributo 'direccion'
     * @param nueva Nuevo valor para el atributo 'direccion'.
      */
    public static void setDireccion(String nueva){
        direccion=nueva;
    }
    /**Este metodo permite acceder al valor del atributo 'direccion'.
     * @return Devuelve el valor actual del atributo 'direccion'.
       */
    public static String getDireccion(){
        return direccion;
    }
    
    
    /**Este metodo modifica el atributo 'opciones'.
     * @param nueva Nuevo valor para el atributo 'opciones'.
      */
    public static void setOpciones(){
        opciones = new HashMap<>();
    }
    /**Este metodo nos permite reconocer los parametros pasados por consola
     * Este metodo distribuye los parametros en un mapa<parametro,contenido> , por lo que si no se respeta la sintaxis puede generar problemas a futuro.
       *Si se quiere saber la sintaxis de los mismos se debe ejecutar el archivo por lote 'csv-viewer' sin pasarle ningun parametro
       *
       *@param cadena es un array de String, el cual representa la entrada por consola. Si no se respeta la sintaxis, no se poduciran errores en este modulo. pero cualquier otro que lo utilice podria funcionar de manera erronea.   
       *
       *
       */
    public static void creaOpciones(String[] cadena){
        setOpciones();
        for (int i = 0; i < cadena.length; i++) {
                if (cadena[i].charAt(0) == '-') {
                    // System.out.printf("%d %d \n",i,cadena.length);
                    getOpciones().put(cadena[i].charAt(1) + "", i + 1 == cadena.length ? "." : cadena[i + 1]);
                    
                }
            }
    }
    /**Este metodo permite acceder al valor del atributo 'opciones'.
     * @return Devuelve el valor actual del atributo 'opciones'.
       */
    public static Map<String,String> getOpciones(){ 
        return opciones;
    }
    /**Este metodo elimina una opcion y el valor que acompaña a esa opcion del atributo 'opciones'.
      *@param clave Una cadena de caracteres perteneciente al nombre de una opcion a eliminar, dicha opcion deberia pertenecer al atributo 'opciones', de lo contrario no se realizara ningun cambio.
      */
    public static void borrarOpcion(String clave){
        if(existeOpcion(clave)){
            opciones.remove(clave);
        } 
        else{
            System.out.println("Nombre de opcion incorrecto");
        }
    }
    /**Este metodo nos permitira saber si una opcion existe o no.
      *@param clave Una cadena de caracteres, la cual deseamos conocer su existencia dentro del atributo 'cabecera'.
      *@return true si es que la opcion existe, false si no existe tal opcion
      */
    public static boolean existeOpcion(String clave){
        if(getOpciones()!=null){
            return getOpciones().containsKey(clave);
        }
        else return false;
    }
    /**Este metodo nos permite conocer el contenido de cada parametros leido
       *@param clave Es el nombre del parametro cuyo contenido se requiere. La opcion deberia existir en el atributo 'opciones'.
       *@return Retorna una cadena de caracteres que representa el contenido del parametro señalado.
       */
    public static String getValor(String clave){
        if(getOpciones()!=null && existeOpcion(clave)){
            return getOpciones().get(clave);
        }
        else{
            System.out.println("La clave no pertenece a las opciones ingresadas");
            return "";
        }
    }
    
    
    /**Este metodo nos permite mostrar por consola los posibles parametros de entrada y señala su correcta sintaxis.
       *Este se muestra cuando no se agrega ningun parametro al ejecutar el programa. 
       */
    public static void showMenu(){
        String cad="";
        cad+="Comandos para usar Visor-CSV :\n"
            +"\t-f string \n"
            +"\t\t Mostrara todas las\n"
            +"\t\tcolumnas que coincidan con\n"
            +"\t\tel string filtro \n"
            +"\t-l int\n"
            +"\t\tModifica el nro maximo de filas\n"
            +"\t\t a mostrar\n"
            +"\t-p string\n"
            +"\t\tModifica la ruta del archivo CSV\n"
            +"\t-o \"col1,col2,..\"\n"
            +"\t\tAsigna una posicion a las columnas\n"
            +"\t-s string\n"
            +"\t\t-Ordena los elementos de una columna\n"
            +"\t\tej. \"col [ASC/DESC]\"\n"
            +"\t -j\n"
            +"\t\tfor json output\n";
        System.out.println(cad);
    }
    /**Este metodo nos permite, mostrar por consola el contenido del CSV, en formato de tabla.
       *El formato de tabla es generado por una funcion llamada print perteneciente a la clase DefaultTablePrinter 
       *
       *@param header es un objeto instanciado, perteneciente a una clase que implementa a List<String>, el cual contiene la lista de cabeceras contenidas en el archivo CSV
       *@param data es un objeto instanciado, perteneciente a una clase que implementa a List<List<String>>, el cual contiene todo el cuerpo contenido en el archivo CSV
       *
       */
    static void print(List<String> header, List<List<String>> data) {
        System.out.println(DefaultTablePrinter.print(header, data));
    }
}