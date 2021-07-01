package source;
/**Esta clase instancia un objeto del tipo FrameAbrirCSV, decidimos hacerlo de esta manera para organizar
   mejor nuestras clases, esta clase seria la que represente nuestra nueva version. Esta clase crea una
   nueva ventana la cual permitira seleccionar un archivo CSV para poder luego visualizar su contenido
   en formato JSON o preconfigurarlo para poder visualizarlo en formato de tabla. 
 *  Esta version es capaz de realizar los mismos cambios al archivo antes de visualizarlo pero tiene 
    soluciones a los problemas encontrados en la version anterior.
 * Error1: Si uno queria ordenar una columna con el metodo sort para luego limitarla se encontraba con que
             primero se limitaba y luego se ordenaba. Lo mismo pasaba si se queria filtrar y luego limitar.
             Esto representaba un error en la jerarquiqa de las operaciones.
 * Error2: Otro error encontrado en la version anterior fue la problematica sobre el ingreso de parametros
             ya que se debian tener en cuenta las comillas, los espacios, las comas, etc. Recordemos que se 
             trabajaba desde consola y por lo tanto se presentaban problemas al ingresarlos. Esto lo solucionamos
             con la creacion de una interfaz grafica de usuario.
 * Error3: Se encontraron errores en el control de varias variables utilizadas, por ejemplo si se ingresaba
             una direccion que no referenciaba a un archivoi CSV, se copiaba igual el contenido y luego surgian 
             errores al operar ese contenido estos errores eran del tipo RuntimeException. Si ya se tenia una
             una direccion que referencie a un archivo CSV pero este estaba vacio entonces tambien se 
             generaban errores del tipo RuntimeException. Cuando la direccion refernciaba a un archivo inexistente
             entonces generaba errores del tipo IOException. Cada vez que se usaban las operaciones (SORT,LIMIT,
             FILTER, ORDER, JSON) y no se controlaba el contenido o la instanciacion del archivo CSV entonces
             generaba errores del tipo RunTimeException (IndexOutOfIndexException, NullPointerException).
    *Estos errores se solucionaron con la GUI creada, por eso decidimos crearla, porque todo dependia de los
     parametros que se ingresaban.
        */
public class CSVViewer {
    /**Este metodo se encarga de instanciar e inicializar un objeto de la clase FrameAbrirCSV, el cual
       le permitira al usuario empezar a utilizar nuestro programa, bajo la GUI desarrollada. 
       */
    public static void main(String[] args){
        FrameAbrirCSV frame = new FrameAbrirCSV();
    }
}