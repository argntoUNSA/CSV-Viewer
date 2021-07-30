package source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        try{
            setIconImage(getIconImage());
        }catch(Exception e){
        
        }
        
        this.contentPane = getContentPane();
        
        cargarPaneles();
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        this.setLocation(250, 250);
        //setSize(FrameW, FrameH);
        pack();
        setResizable(false);
    }
    @Override
    public Image getIconImage() {
       Image retValue = Toolkit.getDefaultToolkit()
        .getImage(getClass().getResource("logoCSV.png"));
       return retValue;
    }
    public void cargarPaneles(){
        this.isCSV = false;
        cargarComponentes();
        armarPanel();
    }
    
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
                fileChooser.setCurrentDirectory(new java.io.File("."));
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
                
                AbrirCSV();
            }
        });
       
        //CheckBox para mostrar como JSON
        this.checkJSON = new JCheckBox("ver como JSON");
        
    }
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
                FrameTablaFinal newFrame = new FrameTablaFinal(this, csvFile, true);
            }else{
                //abrir panel configuracion previa
                FramePreConfigCSV newFrame = new FramePreConfigCSV(this, csvFile);
            }
            dispose();
        }
    }
}
    

