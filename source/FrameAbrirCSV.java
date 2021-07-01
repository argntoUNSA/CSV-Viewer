package source;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/**Esta clase muestra una pantalla para buscar y almacenar el contenido de un archivo CSV, la direccion
   debe ser valida (referenciar un archivo CSV existente). Dicha pantalla tambien contiene un checkbox
   ("JSON") y un boton ("Aceptar"). El primero nos permitira decidir si lo visualizamos en formato JSON
   o tabla, el segundo nos llevara hacia una nueva ventana. Esta nueva ventana por defectoo sera una 
   instancia de la clase FrameTablaFinal o FramePreConfig.
   
 * En el caso de que la direccion proporcionada cumpla las condiciones se podra optar por 2 opciones:
 *  1- Seleccionar la generacion directa de un JSON correspondiente al archivo.("JSON" activado + "Aceptar")
 *  2- Poder preconfigurarlo para luego visualizarlo con formato de tabla ("JSON" desactivado + "Aceptar").
 * .
 * 
*/
public class FrameAbrirCSV extends JFrame{
    private static final int FrameW=350;
    private static final int FrameH=350;
    private Container contentPane; 
    
    private JFileChooser fileChooser;
    private JLabel lbMensaje;
    private JTextField tfPath;
    private JButton btnBuscar, btnAbrir;
    private JCheckBox checkJSON;
    private String pathCSV="";
    private Boolean isCSV;
    private CSV csvFile;
    
    public FrameAbrirCSV(){
        super("CSV Viewer");
        this.contentPane = getContentPane();
        
        cargarPaneles();
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(250, 250);
        //setSize(FrameW, FrameH);
        pack();
        setResizable(false);
    }
    /**Este es un metodo nexo entre el constructor y los 2 metodos principales, los cuales se encargan de 
       cargar los componentes a mostrar y de armar el panel. Pero ademas de esto determina si el archivo 
       seleccionado es del tipo CSV.*/
    public void cargarPaneles(){
        this.isCSV = false;
        cargarComponentes();
        armarPanel();
    }
    /**Este metodo se encarga de cargar:
        -un JLabel donde se alojara un mensaje que informa de un error.
        -un JTextField donde se alojara la direccion ingresada u obtenida.
        -un JButton llamado "Buscar", el cual crea un JFileChooser para obtener asi la direccion de un
            archivo CSV.
        -un JButton llamado "Abrir", el cual (si se cargo una direccion valida) abrira una nueva ventana
            del tipo FramePreconfigCSV o FrameAbrirCSV segun corresponda.
        -un JCheckçBox llamado "JSON", que activado y desactivado definira el comportamiento del boton
            "Abrir". si "JSON" esta activado instancia un objeto de la clase FrameTablaFinal y de lo
            contrario de la clase FramePreconfigCSV.
       */
    public void cargarComponentes(){
        
        //Label para el mensaje de error
        this.lbMensaje = new JLabel("");
        this.lbMensaje.setVisible(false);
        
        //TextField para el path
        this.tfPath = new JTextField(25);
        //this.tfPath.setEditable(false);
        this.tfPath.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                lbMensaje.setVisible(false);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
            
        });
        
        //Boton para Buscar archivo
        this.btnBuscar = new JButton("Buscar");
        this.btnBuscar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                lbMensaje.setVisible(false);
                
                int status;
                fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivo CSV","csv"));
                status = fileChooser.showOpenDialog(null);
                if(status == fileChooser.APPROVE_OPTION){
                    pathCSV = fileChooser.getSelectedFile().getPath();
                    //System.out.println("Path: "+pathCSV);
                    tfPath.setText(pathCSV);
                }
            }
        });
        
        //Boton para abrir el Archivo seleccionado
        this.btnAbrir = new JButton("Abrir");
        this.btnAbrir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                if(!tfPath.getText().isEmpty()){
                    pathCSV = tfPath.getText();
                    
                    String extension = (pathCSV.contains("."))? pathCSV.substring(pathCSV.lastIndexOf("."),pathCSV.length()) : " ";
                    if(extension.equals(".csv")){
                        isCSV = true;
                    }else{
                        isCSV = false;
                    }
                }else{
                    isCSV = false;
                }
                //Antes de llamar al metodo, asegura si el tipo de archivo es del tipo CSV
                AbrirCSV();
            }
        });
       
        //CheckBox para mostrar como JSON
        this.checkJSON = new JCheckBox("ver como JSON");
        
    }
    /**Este metodo armara el panel principal de esta ventana, en el cual se alojaran todos los componentes
       cargados con anterioridad. Estos se ubicaran de la siguiente manera:
        -JLabel "ingrese diirecicon"
        -JTextField - JButton "Buscar" - JButton "Abrir"
        -JCheckBox "JSON"
        -JLabel mensaje de error
        
       */
    private void armarPanel(){
        //Armado del Panel
        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.add(lbMensaje, BorderLayout.SOUTH);
        
        Box cajaH0=Box.createHorizontalBox();
        cajaH0.add(new JLabel("Ingrese direccion del Archivo: "));
        cajaH0.add(Box.createGlue());
        
        Box cajaH1=Box.createHorizontalBox();
        cajaH1.add(tfPath);
        cajaH1.add(Box.createHorizontalStrut(10));
        cajaH1.add(btnBuscar);
        cajaH1.add(Box.createHorizontalStrut(10));
        cajaH1.add(btnAbrir);
        
        Box cajaH2=Box.createHorizontalBox();
        cajaH2.add(checkJSON);
        cajaH2.add(Box.createGlue());
        
        Box cajaV1=Box.createVerticalBox();
        cajaV1.add(cajaH0);
        cajaV1.add(cajaH1);
        cajaV1.add(cajaH2);
        
        borderPanel.add(cajaV1, BorderLayout.CENTER);
        
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        mainPanel.add(borderPanel);
        
        this.add(mainPanel);
    }
    
    /**Este metodo se encarga de controlar que el archivo seleccionado sea del tipo CSV para poder 
       abrirlo y, posteriormente, trabajarlo con normalidad. Dicho control depende de de una variable
       booleana la cual me indica si es o no es un archivo CSV y ademas un JCheckBox; todo esto para 
       permitir decidir si instanciar un FrameTablaCSV o un FramePreconfigCSV. 
       Si el archivo no cumple las cosndiciones correspondiente a un archivo CSV, nos imprimira un mensaje
       de error.
       
       */
    private void AbrirCSV(){
        try{
            this.csvFile = new CSV(this.pathCSV);
        }catch(Exception e){
            System.out.println("Error: "+e.getCause());
            isCSV=false;
        }
        
        if(!isCSV){
            lbMensaje.setText("Seleccione un archivo CSV valido!!");
            lbMensaje.setForeground(Color.RED);
            lbMensaje.setVisible(true);
        }else{
            lbMensaje.setVisible(false);
            
            if(checkJSON.isSelected()){
                //abrir Json
                FrameTablaFinal newFrame = new FrameTablaFinal(csvFile, true);
                dispose();
            }else{
                //abrir panel configuracion previa
                FramePreConfigCSV newFrame = new FramePreConfigCSV(csvFile);
                dispose();
            }
        }
    }
}
    


