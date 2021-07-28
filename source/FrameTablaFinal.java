package source;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class FrameTablaFinal extends JFrame{
    private CSV csvFile, csvOriginal;
    private boolean JSON;
    private Container content;
    private JPanel mainPanel;
    private JButton btnVolver;
    
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JTable tabla;
    
    private FramePreConfigCSV panelAnterior;
    
    public FrameTablaFinal(FramePreConfigCSV panelAnterior, CSV csvFile){
        this(csvFile,false);
        this.panelAnterior = panelAnterior;
    }
    
    public FrameTablaFinal(CSV csvFile, boolean JSON){
        super("CSV Viewer - "+ ( (csvFile!=null)? csvFile.getDireccion() : "null"  ) );
        this.csvFile = csvFile;
        this.JSON = JSON;
        
        try{
            this.csvOriginal = new CSV(csvFile.getDireccion());
        }catch(Exception e){
            System.out.println("Error al obtenr copia del CSV");
        } 

        this.content = getContentPane();
        
        setBounds(50,50,500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        cargaComponentes();
        
        
    }
    
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
                    //FramePreConfigCSV newFrame = new FramePreConfigCSV(csvOriginal);
                    panelAnterior.setVisible(true);
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

