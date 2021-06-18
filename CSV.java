import java.lang.RuntimeException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;
/**Esta clase representa un archivo CSV,sus atributos son tales que representan las caracteristicas principales de dicho tipo de archivo
  *Se intentara representar por medio de esta clase un archivo CSV, por lo quer si el contenido del archivo CSV no respeta dicho formato (referido a su contenido) traera problemas en el funcionamiento de la clase.
  *
   */
public class CSV
{
    
    private List<String> archivo;
    private List<String> cabecera,copiaCabecera;
    private List<List<String>> cuerpo,copiaCuerpo;
    private int filas,columnas;
    private String direccion;
    private boolean formato;
    
    
    /**Este constructor intentara a partir de una direccion dada, crear una copia del contenido del archivo CSV tambien crea la cabecera y el cuerpo de la tabla.
    * @param direcc Es la direccion del archivo CSV a visualizar. esta direccion debe referenciar a un archivo existente y de formato CSV. Si esto se cumple, entonces se almacenara el contendio del CSV en el atributo 'archivo' luego se crea la 'cabecera' y el 'cuerpo' de la tabla.  
    */
    public CSV(String direcc){
        creaDireccion(direcc);
        creaArchivo(getDireccion());
        creaCabecera(",");
        creaCuerpo(",");
        
        
    }
    /**Este constructor nos permitira instanciar todos nuestros atributos en un estado inicial.
    */
    public CSV(){
         setDireccion("");
         setFilas(0);
         setColumnas(0);
         setArchivo((List<String>)null);
         setFormato(false);
         setCabecera(null);
         setCuerpo(null);
         
    }
    /**Este metodo modifica el atributo direccion, sin realizar los controles pertinentes a la nueva direccion.
     * @param nueva Nueva direccion para el archivo CSV. 
     */
    public void setDireccion(String nueva){
        this.direccion=nueva;
    }
    /**Este metodo modifica el atributo 'direccion' y el atributo 'formato'. 
     * @param direccion La direccion de un archivo CSV, si no referencia a un archivo CSV o es una direccion eronea entonces los atributos 'formato' y 'direccion' se pondran en un estado inicial.
     */
    public void creaDireccion(String direccion){
        String auxiliar;
        if(direccion!=null){        
             auxiliar = direccion.substring(direccion.lastIndexOf(".")+1, direccion.length());
        }
        else auxiliar="";
        if(auxiliar.compareTo("csv")==0 || auxiliar.compareTo("CSV")==0) {
           setFormato(true); 
           setDireccion(direccion);
        }
        else{
            setFormato(false);
            setDireccion("");
        }
        
    }
    /**Este metodo nos sirve para obtener el valor actual del atributo direccion
     * @return Devuelve el valor actual del atributo direccion
       */
    public String getDireccion(){
        return this.direccion;
    }

    /**Este metodo nos permite modificar directamente el atributo 'archivo'.
     * @param nuevo nuevo contenido para el atributo archivo.
       */
    public void setArchivo(List<String> nuevo){
        this.archivo=nuevo;
    }
    /**Este metodo tiene como finalidad modificar el atributo 'archivo', en donde almacenara todas las lineas correspondientes al archivo CSV.
     * Lo que primero se realiza, es el control del formato CSV, si no es de dicho formato realizamos la asignacion this.archivo=null.
     * Luego, si la direccion cumple las condiciones dichas, se copiara el contenido del CSV en el atributo archivog.
     * 
     * @param direccion direccion de un archivo existente y del tipo CSV. Si no se cumple con la existencia producira un error. 
       */
    public void creaArchivo(String direccion){
        if(esArchivoCSV()){
            try{
                    setArchivo(Files.readAllLines(Paths.get(direccion)));
                }
                catch(IOException e){
                    setArchivo(null);
                    System.out.println("Problemas al abrir el archivo");
                }
        }
        else setArchivo(null);
    }
    /**Este metodo nos sirve para acceder al contenido del atributo 'archivo'..
     * @return Nos devuelve el valor actual del atributo 'archivo'.
       */
    
    public List<String> getArchivo(){
        return this.archivo;
    }
    /**Este metodo nos permite saber si nos encontramos con un archivo almacenado que sea CSV esto nos lo indicara el atributo 'formato'.
     * @return El valor actual del atributo 'formato', Si el atributo 'archivo'es actualmente CSV entonces devolvera true, de lo contrario false.
       */
    public boolean esArchivoCSV(){
        return getFormato();
    }
    /**Este metodo nos permite saber si nos encontramos con un archivo almacenado que este vacio, esto nos lo indicara el cumplimiento de ciertas condiciones aplicadas sobre estado actual del atributo 'archivo'.
     * @return Un boolean dependiente del valor actual del atributo 'archivo', Si el atributo 'archivo'esta actualmente vacio o aun no ha sido instanciado entonces devolvera tru, de lo contrario false.
       */
    public boolean esArchivoVacio(){
        return(this.archivo==null || this.archivo.size()<=0);
    }
    /**Este metodo modifica el estado actual del atributo 'formato'.
     * @param nuevo Nuevo estado del atributo 'formato'. 
       */
    public void setFormato(boolean nuevo){
        this.formato=nuevo;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'formato'.
     * @return boolean correspondiente al estado actual del atributo 'formato'
       */
    public boolean getFormato(){
        return this.formato;
    }
    /**Este metodo modifica el estado actual del atributo 'columnas'.
     * @param nueva Nueva cantidad de'columnas'. 
       */
    public void setColumnas(int nueva){
        this.columnas=nueva;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'columnas'.
     * @return int correspondiente a la cantidad actual del atributo 'columnas'.
       */
    public int getColumnas(){
        return this.columnas;
    }
    /**Este metodo modifica el estado actual del atributo 'filas'.
     * @param nueva Nueva cantidad de 'filas'. 
       */
    public void setFilas(int nueva){
        this.filas=nueva;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'filas'.
     * @return int correspondiente a la cantidad actual del atributo 'filas'.
       */
    public int getFilas(){
        return this.filas;
    }
    /**Este modulo se ejecuta despues de haber creado el archivo, por lo que si aun no se ejecuto el modulo creaArchivo() y se quiere ejecutar este generaran problemas.
     * Primero detecta si el archivo esta vacio, si el separador y la primera fila del archivo son Strings no vacios. Si cumple esto, entonces a la primera fila la designa como cabecera.
     * 
     * @param separador Es el separador que se tendra en cuenta en la particion de cada fila. Se debe entender que el archivo identidica columnas al separar teniendo en cuenta el separador pero identifica las filas por teniendo en cuenta los saltos de linea.
     * 
       */
    public void creaCabecera(String separador){
        String linea;
        if(!esArchivoVacio())
            linea=this.archivo.get(0);
        else linea="";
        if(separador!=null && separador.length()>0 && linea.length()>0){
            setCabecera(linea,separador);
            setCopiaCabecera(getCabecera());
        }
        else{
            setCabecera(null);
            System.out.println("Problemas al crear cabecera");
        }
    }
    /**Este metodo modifica el estado actual del atributo 'cabecera'.
     * @param linea Un String en donde se almacene la cabecera de la tabla.
     * @param separador Un String donde se almacene un separador de columnas.
       */
    public void setCabecera(String linea, String separador){
        this.cabecera = Arrays.asList(linea.split(separador));
        setColumnas(this.cabecera.size()); 
    }
    /**Este metodo modifica el estado actual del atributo 'cabecera'.
     * @param nueva Nueva lista de Strings para el atributo 'cabecera'. 
       */
    public void setCabecera(List<String> nueva){
        this.cabecera=nueva;
    }
    /**Este metodo modifica el estado actual del atributo 'copiaCabecera'.
     * @param nueva Nueva lista de String para el atributo'copiaCabecera'. 
       */
    public void setCopiaCabecera(List<String> nueva){
        this.copiaCabecera=nueva;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'cabecera'.
     * @return el estado ad actual del atributo 'cabecera'.
       */
    public List<String> getCabecera(){
            return this.cabecera;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'copiaCabecera'.
     * @return el estado actual del atributo 'copiaCabecera'.
       */
    public List<String> getCopiaCabecera(){
        return this.copiaCabecera;
    }
    /**Este metodo controla si una cabecera existe o no.
     * 
     * @param cabecera Es una columna, la cual se quiere determinar si existe o no.
     * @return Una variable booleana que señala si la cabecera existe (true) o no (false).
       */
    public boolean existeCabecera(String cabecera){
        if(!estaCabeceraVacia())
            return this.cabecera.indexOf(cabecera)!=-1;  
        else return false;
    }  
    /**Este metodo comprueba si la cabecera esta vacia o no, si aun no ha sido instanciado el atributpo cabecera entonces
     * 
     * @return boolean que determina si la cabecera esta vacia (true) o no (false).
       */
    public boolean estaCabeceraVacia(){
        return (this.cabecera==null || this.cabecera.size()==0);
    }
    
    /**Este modulo se ejecuta despues de haber creado el archivo, por lo que si aun no se ejecuto el modulo creaArchivo() y se quiere ejecutar este, se generaran problemas.
      * Primero detecta si el archivo esta vacio o aun no fue instanciado y si el separador es un String no vacio. Si no lo esta, modifica el atributo 'cuerpo', 'filas'  y la primera fila del archivo son Strings no vacios. Si cumple esto, entonces a la primera fila la designa como cabecera.
      * @param separador Es el String que separara las columnas, ya que el salto de linea seria el separador de filas
      */
    public void creaCuerpo(String separador){
        if(!esArchivoVacio() && separador!=null &&separador.length()>0){
            setCuerpo(getArchivo().stream().map(e -> Arrays.asList(e.split(separador))).collect(Collectors.toList()));
            setCopiaCuerpo(getCuerpo());
            setFilas(getCuerpo().size());
            eliminaFila(0);
            
            
        }
        else{ 
            setCuerpo(null);
            System.out.println("Hubo problemas con la creacion del cuerpo");
        }
    }
    /**Este metodo modifica el estado actual del atributo 'cuerpo'.
     * @param nuevo Nuevo contenido para el atributo'cuerpo'. 
       */
    public void setCuerpo(List<List<String>> nuevo){
        this.cuerpo=nuevo;
    }
    /**Este metodo modifica el estado actual del atributo 'copiaCuerpo'.
     * @param nuevo Nuevo contenido para el atributo'copiaCuerpo'. 
       */
    public void setCopiaCuerpo(List<List<String>> nuevo){
    }
    /**Este metodo elimina la fila señalada por el parametro posicion, esto afecta a los atributos 'cuerpo' y 'filas'. 
     * @param posicion posicion valida de la fila a eliminar, si no es valida se imprimira un mensaje. Una posicion es valida si es un entero positivo, menor que la cantidad de filas actualmente registrada.
       */
    public void eliminaFila(int posicion){
        if(posicion>=0 && posicion<getFilas()){
            this.cuerpo.remove(posicion);
            setFilas(getFilas()-1);
        }
        else{
            System.out.println("Posicion invalida");
        }
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'cuerpo'.
     * @return El estado actual del atributo 'cuerpo'.
       */
    public List<List<String>> getCuerpo(){
        return this.cuerpo;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'copiaCuerpo'.
     * @return el estado actual del atributo 'copiaCuerpo'.
       */
    public List<List<String>> getCopiaCuerpo(){
        return this.copiaCuerpo;
    }
    /**Este metodo comprueba si el cuerpo esta vacio o no.
     * 
     * @return boolean que determina si el cuerpo esta vacio (true) o no (false).
       */
    public boolean estaCuerpoVacio(){
        return(getCuerpo()==null || getCuerpo().size()<=0);
    }
    /**Este metodo es el que convierte el contenido de los atributos 'cabecera' y 'cuerpo' en una cadena que representa la converesion del contenido del CSV a un archivo JSON , esta conversion necesita que el contenido del CSV ya este copiado en el atributo 'archivo' y que ya se hayan definido los atributos 'cabecera' y 'cuerpo'. De lo contrario se retornara un String vacio. 
     * @return  retorna un string formado por el contenido almacenado en el atributo 'cabecera' y 'cuerpo'todo esto distribuidi con el formato JSON .
       */
    public String JSON(){
        String json = "";
        if(!esArchivoVacio() && esArchivoCSV() && !estaCabeceraVacia() && !estaCuerpoVacio()){
            json+=("[")+"\n";
            for (int i = 0; i < getCuerpo().size(); i++) {
                json+=("\t{")+"\n";
                for (int j = 0; j < getCuerpo().get(i).size(); j++) {
                    json+=("\t\t");
                    json+=("\"" + getCabecera().get(j) + "\":\"" + getCuerpo().get(i).get(j) + "\"");
                    json+=(j == getCuerpo().get(i).size() - 1 ? "" : ",")+"\n";
                }

                json+=(i == getCuerpo().size() - 1 ? "\t}" : "\t},")+"\n";
            }
            json+=("]")+"\n";
        }
        else System.out.println("Archivo o cabecera o cuerpo vacio");
        return json;
    }
    
    /**Este metodo modifica los atributos 'cabecera' y 'cuerpo'. Por lo que se deberia haber almacenado algo en dichos atributos, de lo contrario no se realizaran cambios. Si ambos atributos contienen algo entonces buscara la columna señalada por el parametro "columna" dentro del atributo 'cabecera' y ordenara todos los elementos del atributo 'cuerpo' pertenecientes a dicha columna segun el parametro "order" lo señale.
     *@param columna Es el nombre de la columna columna a ordenar. Debe ser un nombre perteneciente a un elemento de lacabecera de lo contrario no se realizaran cambios.
     *@param order Es un boolean que señala la direccion del ordenamiento . El valor true señala un orden ascendente y false descendente 
       */
    public void Sort(String columna, boolean order){
        int col ;
        Pattern pattern; 
        if(!esArchivoVacio() && !estaCabeceraVacia() && !estaCuerpoVacio() && existeCabecera(columna) ){
            col = getCabecera().indexOf(columna);
            pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(getCuerpo().get(0).get(col)).matches()) {
                // numerical sorting
                try {
                    // Comparator cmp = Comparator.comparing(e->e.get(col));
                    if (order)
                        getCuerpo().sort(Comparator.comparing((List<String> e) -> Double.parseDouble(e.get(col))));
                    else
                        // data.sort(Comparator.comparing((List<String> e)-> e.get(col)).reversed());
                        getCuerpo().sort(Comparator.comparing((List<String> e) -> Double.parseDouble(e.get(col)))
                                .reversed());
    
                } catch (Exception e) {
                    System.out.println("INVALID COLUMN NUMBER : Data cannot be sorted .");
                }
            } else {
                try {
                    // Comparator cmp = Comparator.comparing(e->e.get(col));
                    if (order)
                        getCuerpo().sort(Comparator.comparing((List<String> e) -> e.get(col)));
                    else
                        getCuerpo().sort(Comparator.comparing((List<String> e) -> e.get(col)).reversed());
    
                } catch (Exception e) {
                    System.out.println("INVALID COLUMN NUMBER : Data cannot be sorted .");
                }
    
            }
        }
        else {
            System.out.println("Ingrese un nonmbre valido de la columna, asegurese de no tener vacio ni el archivo ni la cabecera ni el cuerpo ");
        }
    }
    /**Este metodo modifica el atributo 'cuerpo'. Por lo que se deberia haber almacenado algo en dicho atributo, de lo contrario no se realizaran cambios. Si el atributs contienen algo entonces se eliminara del 'cuerpo' los elementos tal que solo queden los que pasen el filtro.
     *@param opt Es el numero de filas que se desean conservar en el atributo 'cuerpo'. Debe ser un numero entero positivo y menor o igual que el valor del atributo 'filas' , de lo contrario no se realizaran cambios.
     */
    public void Limit(int opt){
        if(opt>=0 && opt<=getFilas()){
            setCuerpo(getCuerpo().subList(0, opt));
            setFilas(getCuerpo().size());
        }
        else
            System.out.println("NUMERO INVALIDO");
    }
   /**Este metodo recibe el nombre de una columna numerica existente y una condicion construida a partir de la comparacion entre los elemtnetos de la columna indicada y el parametro "valor", esta comparacion esta dada por el parametro "condicion". todos los elementos que respeten dicha condicion se conservaran en el cuerpo. Tambien se conservaran todos los elementos que pertenezcan a las filas filtradas corresponientes.
     *Este metodo modifica el atributo 'cuerpo'. Por lo que se deberia haber almacenado algo en dicho atributo, de lo contrario no se realizaran cambios. Si el atributo contiene algo entonces se realizaran las modificaciones corresponientes. 
     *@param columna Es el nombre de la columna que se desea filtrar, debe ser un nombre de una columna perteneciente al atributo 'cabecera'. de lo contrario no se realizaran cambios.
     *@param condicion Es la condicion que deben cumplir los elementos de la columna señalada, esta condicion debe ser "<" ">" o "=", de lo contrario no se realizaran cambios.
     *@param valor Es un nro real (Double) que utilizaremos junto con el parametro "condicion" para filtrar la columna señalada, si no se ingresa un nro no se realizaran cambios. 
     */
    public void Filter(String columna, String condicion, double valor){
        
        int index;
        if(!esArchivoVacio() && !estaCabeceraVacia() && !estaCuerpoVacio() && existeCabecera(columna)){
            index = getCabecera().indexOf(columna);
            if (condicion.equals("=")){
                try{
                    setCuerpo(getCuerpo().stream().filter(e -> e.get(index) == ""+valor).collect(Collectors.toList()));
                    setFilas(getCuerpo().size());
                }
                catch(RuntimeException e){
                    System.out.println("valor ingresado incorrecto");
                }
            }
            else{ 
                if (condicion.equals("<")){
                    setCuerpo(getCuerpo().stream()
                                .filter(e -> Double.parseDouble(e.get(index)) < valor)
                                .collect(Collectors.toList()));
                    setFilas(getCuerpo().size());
                }
                else{ 
                    if (condicion.equals(">")){
                        setCuerpo(getCuerpo().stream()
                                    .filter(e -> Double.parseDouble(e.get(index)) > valor)
                                    .collect(Collectors.toList()));
                        setFilas(getCuerpo().size());
                    }
                    else System.out.println("No se ingreso condicion correcta");
                }
            }
        }
        else{
            System.out.println("Archivo/Cabecera/Cuerpo vacio");
        } 
        
    }
/**Este metodo recibe un ordenamiento de una cierta cantidad de columnas existentes en el CSV, para poder modificar los atributos 'cabecera' y 'cuerpo', teniendo en cuenta dicho ordenamiento.' 
  *Este metodo modifica el atributo 'cuerpo'. Por lo que se deberia haber almacenado algo en dicho atributo, de lo contrario no se realizaran cambios. Si el atributo contiene algo entonces se realizaran las modificaciones corresponientes.
  *@param columnas Un ordenamiento de todas/algunas columnas pertenecientes a la cabecerade la tabla. Este ordenamiento puede contener repeticiones de una misma columna, pero si posee el nombre de una columna inexistente entonces arrojara un error del tipoRuntimeException
  *
  */
    public void Order(String[] columnas){
        List<Integer> ind;
        List<List<String>> tmp;
        
        if(columnas.length>1 || (columnas.length==1 && columnas[0]!=".")){
            ind = new ArrayList<>();
            for (String o : columnas) {
                ind.add(this.cabecera.indexOf(o));
            }
            tmp = new ArrayList<>();
            for (int i = 0; i < getFilas(); i++) {
                tmp.add(new ArrayList<>());
                // tmp.get(i).
                for (int j = 0; j < ind.size(); j++) {
                    String ele = getCuerpo().get(i).get(ind.get(j));
                    tmp.get(i).add(ele);
                }
            }
            List<String> header2 = new ArrayList<>();
            for (int s : ind) {
                header2.add(cabecera.get(s));
    
            }
    
            setCabecera(header2);
            setColumnas(getCabecera().size());
            setCuerpo(tmp);
            setFilas(getCuerpo().size());
    }
    else{
        System.out.println("Ingrese al menos una columna");
    }
}
}
