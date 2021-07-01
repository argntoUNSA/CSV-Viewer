package source;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;


/**Esta clase nos permite visualizar el archivo CSV existente en formato de tabla, Es una extencion de la clase JTable*/
public class TableForCSV extends JTable{
    private DefaultTableModel myTablaModel;
    /**Este es un constructor que arma la cabecera tabla tomando como contenido de dicha tabla al array de String pasado por parametro
     * @param cabecera Array de String que representa la cabecera de la tabla.
       */
    public TableForCSV(String[] cabecera) {
        this.myTablaModel = new DefaultTableModel(cabecera, 0);
        this.setAutoResizeMode(AUTO_RESIZE_OFF);
        this.setModel(this.myTablaModel);
    }
    /**Este es un constructor que arma la cabecera tabla tomando como contenido de dicha cabecera a la Lista de String pasada por parametro
     * @param cabecera List<String> que representa la cabecera de la tabla.
       */
    public TableForCSV(List<String> cabecera){
        this((String[])cabecera.toArray());
    }
    /**Este es un constructor que arma la cabecera y el cuerpo de la tabla tomando como contenido de la tabla a los parametros
     * @param cabecera List<String> que representa la cabecera de la tabla.
     * @param cuerpo List<List<String>> que representa el cuerpo de la tabla.
       */
    public TableForCSV(List<String> cabecera, List<List<String>> cuerpo){
        this(cabecera);
        addRowGroup(cuerpo);
    }
    /**Este es un metodo perteneciente a JTable que se sobreescribe para poder utilizarlo con un parametro de un tipo especifico, en este caso del tipo List<String>
     * @param fila Fila que queremos agregar, esta fila es del tipo List<String>.
       */
    public void addRow(List<String> fila) {
        addRow((String[])fila.toArray());
    }
    /**Este es un metodo perteneciente a JTable que se sobreescribe para poder utilizarlo con un parametro de un tipo especifico, en este caso del tipo String[].
     * @param fila Fila que queremos agregar, esta fila es del tipo String[].
       */
    public void addRow(String[] fila) {
        ((DefaultTableModel)this.getModel()).addRow(fila);
    }
    /**Este metodo recibe un grupo de filas, lo separa en filas individuales para construir el cuerpo de la tabla, fila por fila
     * @param grupoFilas Grupo de filas que representaran al cuerpo de la tabla
       */
    public void addRowGroup(List<List<String>> grupoFilas){
        for(int i=0; i<grupoFilas.size(); i++){
            String[] fila = new String[grupoFilas.get(i).size()];
            for(int j=0; j<fila.length; j++){
                fila[j]=grupoFilas.get(i).get(j);
            }
            addRow(fila);
        }
    }
}
