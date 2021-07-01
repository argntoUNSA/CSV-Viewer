package source;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**Esta clase nos permite visualizar el contenido de un archivo CSV en formato tabla, pero tambien nos
   permite visualizarlo en formato JSON. Si el caso es el primero, se cargara (en el panel correspondiente 
   al contenido a mostrar) un objeto del tipo TableForCSV. Si es el segundo, se cargara un JTextArea. 
   En ambos casos se cargara un boton que permitira cerrar esta ventana para volver a una anterior, por
   defecto esta ventana sera, dependiendo del caso, FramePreConfigCSV o FrameAbrirCSV.
 */
public class FrameTablaFinal extends JFrame{
    private CSV csvFile, csvOriginal;
    private boolean JSON;
    private Container content;
    private JPanel mainPanel;
    private JButton btnVolver;
    
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JTable tabla;
    /**Este constructor nos permite, a partir de un archivo CSV, mostrar una tabla representativa
      *@param csvFile Una instancia de la clase CSV la cual representa a un archivo CSV existente. El contenido de este sera representado como tabla. 
       */
    public FrameTablaFinal(CSV csvFile){
        this(csvFile,false);
    }
    /**Este constructor nos permite, a partir de un archivo CSV y de una variable booleana de control, construir una ventana y  mostrar el contenido en formato de tabla o formato JSON segun sea el valor de la variable de control.
      *@param csvFile Una instancia de la clase CSV la cual representa a un archivo CSV existente. El contenido de este sera representado como tabla. 
      *@param JSON variable booleana de control para determinar en que formato debe mostrarse el contenido del archivo CSV. 
       */
    public FrameTablaFinal(CSV csvFile, boolean JSON){
        super("CSV Viewer - "+ ( (csvFile!=null)? csvFile.getDireccion() : "null"  ) );
        this.csvFile = csvFile;
        this.JSON = JSON;
        
        try{
            this.csvOriginal = new CSV(csvFile.getDireccion());
        }
        catch(Exception e){
            System.out.println("Error al obtenr copia del CSV");
        } 

        this.content = getContentPane();
        
        setBounds(50,50,500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cargaComponentes();
        
        
    }
    /**Este metodo se encarga de cargar los componentes necesarios para mostrar el archivo CSV, puede
      mostrarse en 2 formatos por lo que este metodo decide, mediante una variable de control, cargar
      los componentes pertinentes a dicho formato. Pero en ambos casos, este metodo cargara un boton 
      que permitira regresar a la ventana de preconfiguracion.
       */
    private void cargaComponentes(){
        
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        
        if(JSON){
            this.textArea = new JTextArea();
            this.textArea.setEditable(false);
            this.textArea.setText(csvFile.JSON());
            this.scrollPane = new JScrollPane(this.textArea);
            
        }else{
            String[] cabeceras = null;
            if(this.csvFile.getCabecera() instanceof ArrayList){
                cabeceras = new String[csvFile.getCabecera().size()];
                for(int i=0; i<cabeceras.length; i++){
                    cabeceras[i]= csvFile.getCabecera().get(i);
                }
            }else{
                cabeceras = (String[])(this.csvFile.getCabecera().toArray(new String[csvFile.getCabecera().size()]));
            }
            this.tabla = new TableForCSV(cabeceras);
            ((TableForCSV)this.tabla).addRowGroup(this.csvFile.getCuerpo());
            
            this.scrollPane = new JScrollPane(this.tabla);
            this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            
        }
        
        
        this.btnVolver = new JButton("Volver");
        this.btnVolver.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                if(JSON){
                    FrameAbrirCSV newFrame = new FrameAbrirCSV();
                }else{
                    FramePreConfigCSV newFrame = new FramePreConfigCSV(csvOriginal);
                }
                
                dispose();
            }
        });
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(this.btnVolver);
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(this.scrollPane, BorderLayout.CENTER);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);
        
        
        this.content.add(mainPanel);
    }
    
}

