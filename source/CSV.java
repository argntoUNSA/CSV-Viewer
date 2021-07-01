package source;

import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.*;
/**Esta clase nos ayuda a simular un archivo CSV. Esto a partir de almacenar el contenido de un archivo CSV
   dentro de los atributos cabecera y un cuerpo, tambien nos brinda metodos para editar estos atributos,
   ademas tiene un metodo para visualizar el contenido en formato JSON.
  *Especificaciones:
        -SORT : Ordena una columna indicada, ascendente o descendentemente, tambien segun lo indicado.
        -LIMIT : Limita la cantidad de filas de la tabla a mostrar.
        -FILTER : Filtra una tabla indicada, segun una condicion indicada. 
        -ORDER : Ordena los encabezados ascendente, descendente o de manera personalizada.
        -JSON : Almacena en el atributo JSON el contenido del archivo en formato JSON.
    */
public class CSV{
    private List<String> archivo;
    private List<String> cabecera,copiaCabecera;
    private List<List<String>> cuerpo,copiaCuerpo;
    private int filas,columnas;
    private String direccion;
    
    
    /**Este constructor, a partir de una direccion dada, creara una copia del contenido del archivo CSV
       que tiene como referencia, la direccion indicada. Lo almacenara en atributo la cabecera y el cuerpo
       de la tabla.
      *@param direcc Es la direccion del archivo CSV a visualizar. esta direccion debe referenciar a 
         un archivo existente y de formato CSV. Si esto se cumple, entonces se almacenara el contendio del CSV
         en el atributo 'archivo' luego se crea la 'cabecera' y el 'cuerpo' de la tabla. 
      *@throws RuntimeException 
      *@Si la direccion no cumple las condiciones, entonces  
    */
    public CSV(String direcc)throws Exception
    {   
        setDireccion(direcc);
        setColumnas(0);
        setFilas(0);
        creaArchivo();
        creaCabecera(",");
        creaCuerpo(",");
    }
    /**Este metodo modifica el atributo direccion, sin realizar los controles pertinentes a la nueva 
      direccion. 
     * @param nueva Nueva direccion para el archivo CSV. 
     */
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
    /**Este metodo nos sirve para obtener el valor actual del atributo direccion
     * @return Devuelve el valor actual del atributo direccion
       */
    public String getDireccion(){
        return this.direccion;
    }
    /**Este metodo nos permite modificar directamente el atributo 'archivo'. Sin controlar previamente
     * @param nuevo nuevo contenido para el atributo archivo.
     * @throws FileNotFoundException Si se ingresa una direccion que referencie a un archivo inexistente. 
       */
    public void setArchivo(String path) throws Exception{
        this.archivo = Files.readAllLines(Paths.get(path));
    }
    /**Este metodo es un nexo entre el constructor y el modificador del atributo archivo.
       */
    public void creaArchivo()throws Exception{
        setArchivo(getDireccion());
    }
    /**Este metodo nos sirve para acceder al contenido del atributo 'archivo'..
     * @return Nos devuelve el valor actual del atributo 'archivo'.
       */
    public List<String> getArchivo(){
        return this.archivo;
    }
    /**Este metodo modifica directqamente el estado actual del atributo 'columnas'. Sin los controles 
       correspondientes.
     * @param nueva Nueva cantidad de'columnas'. 
       */
    public void setColumnas(int cant){
        this.columnas=cant;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'columnas'.
     * @return int correspondiente a la cantidad actual del atributo 'columnas'.
       */
    public int getColumnas(){
        return this.columnas;
    }
    /**Este metodo modifica el estado actual del atributo 'filas'. Sin los controles correspondientes.
     * @param nueva Nueva cantidad de 'filas'. 
       */
    public void setFilas(int cant){
        this.filas=cant;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'filas'.
     * @return int correspondiente a la cantidad actual del atributo 'filas'.
       */
    public int getFilas(){
        return this.filas;
    }
    /**Este metodo realiza la construccion de la cabecera de la tabla, esto lo realiza leyendo el contenido 
       del atributo archivo. Por lo que este metodo se ejecuta despues de haber leido y copiado el contenido
       del archivo
     * @param separador Es el separador que se tendra en cuenta en la particion de la cabecera. Se debe
     * entender que el archivo identidica las cabecerasgracias al separador.
     */
    public void creaCabecera(String separador){
        String linea=this.archivo.get(0);
        setCabecera(linea,separador);
        setCopiaCabecera(getCabecera());
    
    }
    /**Este metodo modifica el estado actual del atributo 'cabecera'. No se realizan los controles correspondientes
     * @param linea Primera linea del archivo en donde se almacena la cabecera de la tabla.
     * @param separador Un String donde se almacene el separador utilizando en el archivo.
       */
    public void setCabecera(String linea, String separador){
        this.cabecera = Arrays.asList(linea.split(separador));
        setColumnas(this.cabecera.size()); 
    }
    /**Este metodo modifica el estado actual del atributo 'copiaCabecera'.No se realizan los controles correspondientes
     * @param nueva Nueva lista de String para el atributo'copiaCabecera'. 
       */
    public void setCopiaCabecera(List<String> lista){
        this.copiaCabecera=lista;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'cabecera'. No se realizan los 
       controles correspondientes.
     * @return el estado actual del atributo 'cabecera'.
       */
    public List<String> getCabecera(){
            return this.cabecera;
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'copiaCabecera'. No se realizan 
       los controles correspondientes.
     * @return El estado actual del atributo 'copiaCabecera'.
       */
    public List<String> getCopiaCabecera(){
        return this.copiaCabecera;
    }
    /**Este metodo controla si una cabecera existe o no.
     * 
     * @param cabecera Es una columna, la cual se quiere determinar si existe o no.
     * @return Una variable booleana que señala si la cabecera existe (true) o no (false).
       */
    public boolean existeCabecera(String clave){
        return this.cabecera.indexOf(clave)==-1;   
    }  
    /**Este metodo modifica el estado actual del atributo 'cuerpo'. No se realizan los controles 
       correspondientes.
     * @param nuevo Nuevo contenido para el atributo'cuerpo'. 
       */
    public void setCuerpo(String separador){
        this.cuerpo=getArchivo().stream().map(e -> Arrays.asList(e.split(separador))).collect(Collectors.toList());
        eliminaFila(0);
        setFilas(this.cuerpo.size());
    }
    /**Este metodo modifica el estado actual del atributo 'copiaCuerpo'. No se realizan los controles
       correspondientes.
     * @param nuevo Nuevo contenido para el atributo'copiaCuerpo'. 
       */
    public void setCopiaCuerpo(List<List<String>> lista){
        this.copiaCuerpo=lista; 
    }
    /**Este metodo es un nexo entre el constructor y el modificador del atributo archivo.
       */
    public void creaCuerpo(String separador){
        setCuerpo(separador);
    }
    /**Este metodo elimina la fila señalada por el parametro posicion, esto afecta a los atributos 
       'cuerpo' y 'filas'. No se realizan los controles correspondientes.
     * @param posicion posicion valida de la fila a eliminar, si no es valida se imprimira un mensaje.
       Una posicion es valida si es un entero positivo, menor que la cantidad de filas actualmente 
       registrada.
       */
    public void eliminaFila(int pos){
        this.cuerpo.remove(pos);
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'cuerpo'. No se realizan los 
       controles correspondientes.
     * @return El estado actual del atributo 'cuerpo'.
       */
    public List<List<String>> getCuerpo(){
        return this.cuerpo;
    
    }
    /**Este metodo nos permite acceder al estado actual del atributo 'copiaCuerpo'. No se realizan los
       controles correspondientes.
     * @return El estado actual del atributo 'copiaCuerpo'.
       */
    public List<List<String>> getCopiaCuerpo(){
        return this.copiaCuerpo;
    }
    /**Este metodo es el que convierte el contenido de los atributos 'cabecera' y 'cuerpo' en una cadena
       que representa la conversion del contenido del CSV a un archivo JSON, esta conversion necesita
       que el contenido del CSV ya este copiado en el atributo 'archivo' y que ya se hayan definido los
       atributos 'cabecera' y 'cuerpo'. 
     * @return  retorna un string formado por el contenido almacenado en el atributo 'cabecera' y 'cuerpo'
       convertido al formato JSON.
       */
    public String JSON(){
        String json = "";
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
        return json;
    }
    /**Este metodo modifica el atributo 'cuerpo'. Por lo que se deberia haber instanciado e inicializado
       dicho atributo. Se buscara la columna señalada por el parametro "columna" dentro del
       atributo 'cabecera' y ordenara todos los elementos del atributo 'cuerpo' respecto al orden de dicha
       columna segun el parametro "order" lo señale.
     *@param columna Es el nombre de la columna columna a ordenar. Debe ser el nombre de un encabezado de 
      la tabla
     *@param order Es un boolean que señala la direccion del ordenamiento . El valor true señala un 
      orden ascendente y false descendente.
       */
    public void Sort(String columna, boolean order){
        
        int col = getCabecera().indexOf(columna);
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
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
    /**Este metodo modifica el atributo 'cuerpo'. Por lo que se deberia haber instanciado e inicializado
        de lo contrario no se realizaran cambios. Este metodo elimina las filas posteriores a un numero dado
     *@param opt Es el numero de filas que se desean conservar en el atributo 'cuerpo'. Debe ser un numero entero positivo y menor o igual que el valor del atributo 'filas' , de lo contrario no se realizaran cambios.
     */
    public void Limit(int opt){
        if(opt>=0 && opt<= this.cuerpo.size() ){
            this.cuerpo = this.cuerpo.subList(0, opt);
        }else
            System.out.println("NUMERO INVALIDO");
    }
   /**Este metodo recibe el nombre de una columna numerica existente y una condicion construida a partir
      de la comparacion entre los elementos de la columna indicada y el parametro "valor", esta comparacion
      esta dada por el parametro "condicion". Todos los elementos, de la columna indicada, que respeten
      dicha condicion se conservaran en el cuerpo. Tambien se conservaran todos los elementos que pertenezcan
      a las filas filtradas corresponientes.
     *Este metodo modifica el atributo 'cuerpo'. Por lo que se deberia haber almacenado algo en dicho
      atributo. Se realizaran las modificaciones corresponientes, dentro del atributo 'cuerpo'.
     *
     *@param columna Es el nombre de la columna que se desea filtrar, debe ser un nombre de una columna
      perteneciente al atributo 'cabecera'. de lo contrario no se realizaran cambios.
     *@param condicion Es la condicion que deben cumplir los elementos de la columna señalada, esta 
      ncondicion debe ser "<" ">" o "=", de lo contrario no se realizaran cambios.
     *@param valor Es un nro real (Double) que utilizaremos junto con el parametro "condicion" para filtrar
      la columna señalada. 
     */
   public void Filter(String columna, String condicion, double valor){
        
        int index = getCabecera().indexOf(columna);

        if (condicion.equals("="))
            this.cuerpo = this.cuerpo.stream()
                    .filter(e -> Double.parseDouble(e.get(index)) == valor)
                    .collect(Collectors.toList());
        else if (condicion.equals("<"))
            this.cuerpo = this.cuerpo.stream()
                    .filter(e -> Double.parseDouble(e.get(index)) < valor)
                    .collect(Collectors.toList());
        else if (condicion.equals(">"))
            this.cuerpo = this.cuerpo.stream()
                    .filter(e -> Double.parseDouble(e.get(index)) > valor)
                    .collect(Collectors.toList());
    }
    /**Este metodo ordena los encabezados de la tabla, el orden de estos vendra de la mano del parametro
       es decir, que el orden de las columnas debe ser ingresado como un array de string, donde sus elementos
       seran algunos o todos los nombres de encabezados de la tabla. Modificara los atributos 'cabecera'
       y 'cuerpo', teniendo en cuenta dicho ordenamiento. 
      *@param columnas Un ordenamiento de todas/algunas columnas pertenecientes a la cabecera de la 
       tabla. Este ordenamiento puede contener repeticiones de una misma columna, pero si posee el nombre
       de una columna inexistente entonces arrojara un error del tipo RuntimeException
      
       */
   public void Order(String[] columnas){
        List<Integer> ind = new ArrayList<>();
        for (String o : columnas) {
            ind.add(this.cabecera.indexOf(o));
        }
        List<List<String>> tmp = new ArrayList<>();
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

        this.cabecera = header2;
        this.cuerpo = tmp;
    }
}