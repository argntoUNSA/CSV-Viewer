package source;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Image;
import java.awt.Toolkit;

public class FramePreConfigCSV extends JFrame{
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private Container contentPane;
    private CSV csvFile;
    
    //Panel SORT
    private JPanel panelSORT;
    private JPanel subpanelSORT;
    private JCheckBox checkSORT;
    private Boolean esASCENDENTE, selectSORT;
    private JRadioButton btnASCENDsort, btnDESCsort;
    private JComboBox<String> comboSORT;
    private String itemComboSORT;
    private JLabel lbMensajeSORT;
    
    //Panel LIMIT
    private JPanel panelLIMIT;
    private JPanel subpanelLIMIT;
    private JCheckBox checkLIMIT;
    private Boolean selectLIMIT, LIMITok;
    private JTextField tfNumLIMIT;
    private int numLIMIT;
    private JLabel lbMensajeLIMIT;
    
    //Panel FILTER
    private JPanel panelFILTER;
    private JPanel subpanelFILTER, subpanelErrorFILTER;
    private JCheckBox checkFILTER;
    private Boolean selectFILTER, FILTERok;
    private JComboBox<String> comboFILTER;
    private String itemComboFILTER;
    private String[] cabecerasNumericas;
    private JLabel lbMensajeFILTER;
    private ButtonGroup grupoBotones;
    private JRadioButton btnMayor, btnMenor, btnIgual;
    private JTextField tfNumFILTER;
    private double numFILTER;
    
    //Panel ORDER
    private JPanel panelORDER;
    private JPanel subpanelORDER;
    private JCheckBox checkORDER;
    private Boolean selectORDER;
    private ArrayList<String> listaCabecerasPosiblesORDER,listaOrdenadaAscendentementeORDER,listaOrdenadaDescendentementeORDER,listaSeleccionORDER;
    private JRadioButton btnAscendenteORDER, btnDescendenteORDER, btnPersonalizadoORDER;
    private ButtonGroup grupoBotonesORDER;
    private JComboBox<String> comboBoxORDER;
    private JLabel lbMensajeORDER;
    private JTextArea ordenColumnasORDER;
    private JScrollPane scrollPaneORDER;
    private boolean AscendenteORDER,DescendenteORDER,PersonalizadoORDER;
    
    //Panel Botones
    private JPanel panelBotones;
    private JButton btnCancelar, btnVer;
    private Boolean allOK;
    
    private FramePreConfigCSV instance;
    private JFrame panelAnterior;
    
    public FramePreConfigCSV(JFrame panelAnterior, CSV csvFile){
        super("CSV Viewer - "+ ( (csvFile!=null)? csvFile.getDireccion() : "null"  ));
        setIconImage(getIconImage());
        
        this.panelAnterior = panelAnterior;
        
        this.contentPane = getContentPane();
        this.csvFile = csvFile;
        
        this.contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
        cargarComponentes();
        armarPanel();
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setBounds(50, 50, 700, 500);
        //device.setFullScreenWindow(this);
        
        this.instance =this;
    }
    @Override
    public Image getIconImage() {
       Image retValue = Toolkit.getDefaultToolkit()
        .getImage(getClass().getResource("logoCSV.png"));
       return retValue;
    }
    
    private void cargarComponentes(){
        cargarPanelSort();
        cargarPanelLimit();
        cargarPanelFilter();
        cargarPanelOrder();
        
        cargaPanelBotones();
    }
    
    private void cargarPanelSort(){
        this.panelSORT = new JPanel();
        
        selectSORT=false;
        this.checkSORT = new JCheckBox("ORDENAR ALFABETICAMENTE");
        this.checkSORT.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                selectSORT = checkSORT.isSelected();
                subpanelSORT.setVisible(selectSORT);
            }
        });
        
        esASCENDENTE=true;
        this.btnASCENDsort = new JRadioButton("Ascendente");
        this.btnDESCsort = new JRadioButton("Descendente");
        ButtonGroup sortGroup = new ButtonGroup();
        sortGroup.add(this.btnASCENDsort);
        sortGroup.add(this.btnDESCsort);
        this.btnASCENDsort.setSelected(true);
        
        String[] cabeceras = new String[this.csvFile.getColumnas()];
        for(int i=0; i<cabeceras.length; i++){
            cabeceras[i]= this.csvFile.getCabecera().get(i);
        }
        this.comboSORT = new JComboBox<String>(cabeceras);
        this.comboSORT.setSelectedIndex(0);
        this.itemComboSORT = (String)comboSORT.getSelectedItem();
        this.comboSORT.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                 itemComboSORT = (String)comboSORT.getSelectedItem();
                //System.out.println("item: "+(String)comboSORT.getSelectedItem());
            }
        });
        
        this.lbMensajeSORT = new JLabel();
        this.lbMensajeSORT.setVisible(false);
    }
    
    private void cargarPanelLimit(){
        this.panelLIMIT = new JPanel();
        
        selectLIMIT=false;
        this.checkLIMIT = new JCheckBox("LIMITAR CANTIDAD DE FILAS");
        this.checkLIMIT.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                selectLIMIT = checkLIMIT.isSelected();
                subpanelLIMIT.setVisible(selectLIMIT);
            }
        });
        
        LIMITok = true;
        numLIMIT = 0;
        this.tfNumLIMIT = new JTextField("0",5);
        this.tfNumLIMIT.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                LIMITok = false;
                
                //trbajar con ascii 48 - 57
                //System.out.println("keycode: "+ ((int)e.getKeyChar()) );
                int asciiKey = (int)e.getKeyChar();
                if(asciiKey<48 || asciiKey>57){
                    e.consume();
                    LIMITok= false;
                }else{
                    LIMITok = true;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(!tfNumLIMIT.getText().isEmpty()){
                    try{
                        numLIMIT = Integer.parseInt(tfNumLIMIT.getText());
                        LIMITok = true;
                    }catch(Exception exception){
                        //exception.printStackTrace();
                        LIMITok = false;
                    }
                }
                //System.out.println("EN TEXTLIMIT: "+numLIMIT);
                
                if(!LIMITok){
                    lbMensajeLIMIT.setText("Ingresa un Numero entero valido");
                    lbMensajeLIMIT.setForeground(Color.RED);
                    lbMensajeLIMIT.setVisible(true);
                }else{
                    lbMensajeLIMIT.setVisible(false);
                    LIMITok = true;
                }
            }
        });
        
        this.lbMensajeLIMIT = new JLabel();
        this.lbMensajeLIMIT.setVisible(false);
    }
    
    private void cargarPanelFilter(){
        this.panelFILTER = new JPanel();
        
        selectFILTER=false;
        this.checkFILTER = new JCheckBox("FILTRADO POR COLUMNA");
        this.checkFILTER.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                selectFILTER = checkFILTER.isSelected();
                if(cabecerasNumericas.length>0){
                    subpanelFILTER.setVisible(selectFILTER);
                    //System.out.println("hay cabeceras");
                }else{
                    //mostrar panel auxiliar
                    subpanelErrorFILTER.setVisible(selectFILTER);
                    //System.out.println("No hay cabeceras");
                }
            }
        });
        
        ArrayList<String> cabeceras = new ArrayList<String>();
        int i=0;
        while(i< this.csvFile.getColumnas()){
            int j=0;
            try{
                while(j<this.csvFile.getFilas()){
                    //System.out.print("("+j+","+i+") "+this.csvFile.getCuerpo().get(j).get(i)+"   ");
                    Double num = Double.parseDouble(this.csvFile.getCuerpo().get(j).get(i));
                    j++;
                }
                cabeceras.add(this.csvFile.getCabecera().get(i));
                //System.out.println("Agregado: "+this.csvFile.getCabecera().get(i));
            }catch(Exception e){
                //System.out.println("No se pudo Parsear");
            }
            i++;
        }
        this.cabecerasNumericas = new String[cabeceras.size()];
        for(int ind=0; ind<cabeceras.size(); ind++){
            cabecerasNumericas[ind] = cabeceras.get(ind);
        }
        
        this.comboFILTER = new JComboBox<String>(cabecerasNumericas);
        if(comboFILTER.getItemCount()>0){
            this.comboFILTER.setSelectedIndex(0);
            this.itemComboFILTER = (String) comboFILTER.getSelectedItem();
        }
        
        this.comboFILTER.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                itemComboFILTER = (String) comboFILTER.getSelectedItem();
            }
        });
        
        this.lbMensajeFILTER = new JLabel();
        this.lbMensajeFILTER.setVisible(false);
        
        this.btnMayor = new JRadioButton(">");
        this.btnMenor = new JRadioButton("<");
        this.btnIgual = new JRadioButton("=");
        
        this.grupoBotones = new ButtonGroup();
        this.grupoBotones.add(this.btnMayor);
        this.grupoBotones.add(this.btnMenor);
        this.grupoBotones.add(this.btnIgual);
        this.btnMayor.setSelected(true);
        
        FILTERok = true;
        numFILTER = 0;
        this.tfNumFILTER = new JTextField("0",5);
        this.tfNumFILTER.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                FILTERok = false;
                
                //System.out.println("keycode: "+ ((int)e.getKeyChar()) );
                int asciiKey = (int)e.getKeyChar();
                if((asciiKey<48 || asciiKey>57) && asciiKey!=46 ){
                    e.consume();
                    FILTERok= false;
                }else{
                    FILTERok = true;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if(!tfNumFILTER.getText().isEmpty()){
                    try{
                        numFILTER = Double.parseDouble(tfNumFILTER.getText());
                        FILTERok = true;
                    }catch(Exception exception){
                        //exception.printStackTrace();
                        FILTERok = false;
                    }
                }
                
                if(!FILTERok){
                    lbMensajeFILTER.setText("Ingresa un Numero valido");
                    lbMensajeFILTER.setForeground(Color.RED);
                    lbMensajeFILTER.setVisible(true);
                }else{
                    lbMensajeFILTER.setVisible(false);
                    FILTERok = true;
                }
            }
        });
        
        
    }
    
    /**Este metodo nos permite cargar los componentes pertenecientes al subpanel que permite configurar el orden de las columnas a visualizar sORDER*/
/**Este metodo instancia e inicializa los elementos que seran luego cargados al panel de ORDER. Ademas de asignarles, a cada elemento correspondiente, un oyente. Los oyentes , por organizacion, seran definidos mediante clases internas. Se necesitarian que los atributos usados ensten definidos.
  *Especificaciones: 
     *  Se necesitara instanciar los siguientes tipos de componentes:
     *  JPanel, JCheckBox, JGroupButtons, ArrayList<String>, JComboBox, JTextArea, JRadioButton, ButtonGroup y JLabel
     *  Todos estos para representar el panel ORDER, el cual tiene como mision poder darle a elegir al usuario de que modo se ordenaran las columnas de la tabla. Existen 3 tipos de opciones de ordenamiento: Ascendente; Descedente; Personalizado.
     *  Estas 3 opciones no modifican la tabla original, solo modifican unos atributos. Estos son modificados para luego pasarlos por parametros a la funcion ORDER del atributo de clase CSv.  
     *  Por ultimo se inicializa un mensaje de error preparandolo para que luego sea actualizado 
        */
    private void cargarPanelOrder(){
        //Instancia CheckBox pricipal de ORDER
        this.panelORDER = new JPanel();
        this.subpanelORDER = new JPanel();
        selectORDER = false;
        this.checkORDER = new JCheckBox("ORDENAR LOS ENCABEZADOS");
        this.checkORDER.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                selectORDER = checkORDER.isSelected();
                subpanelORDER.setVisible(selectORDER);
            }
        });
        //Llenar la lista de posibles cabeceras, el checkBox y el scrollPane
        String[] cabeceras;
        cabeceras = new String[this.csvFile.getColumnas()];
        this.listaCabecerasPosiblesORDER = new ArrayList<String>();
        this.listaSeleccionORDER = new ArrayList<String>();
        this.comboBoxORDER=new JComboBox();
        this.ordenColumnasORDER = new JTextArea("Orden");
        this.ordenColumnasORDER.setEnabled(false);
        this.scrollPaneORDER = new JScrollPane(this.ordenColumnasORDER);
        for(int i=0; i<cabeceras.length; i++){
            cabeceras[i]= this.csvFile.getCabecera().get(i);
            this.listaCabecerasPosiblesORDER.add(this.csvFile.getCabecera().get(i));
            this.comboBoxORDER.addItem(cabeceras[i]/*this.listaSeleccionORDER.get(i)*/);
            //aux = new JCheckBox();
            //this.listaCheckSeleccionORDER.add(i,aux);
        }
        this.ordenColumnasORDER.setLineWrap(true);
        this.ordenColumnasORDER.setWrapStyleWord(true);
        this.comboBoxORDER.addActionListener(new seleccioncomboBoxListenerORDER());
        this.comboBoxORDER.setVisible(false);
        this.scrollPaneORDER.setVisible(false);
        
        
        //Relaciona las opciones de ordenamiento con sus respectivos oyentes
        btnAscendenteORDER = new JRadioButton("Ascendente");
        btnDescendenteORDER = new JRadioButton("Desscendente");
        btnPersonalizadoORDER = new JRadioButton("Personalizado");
        btnAscendenteORDER.addActionListener(new btnAscendenteORDERListener());
        btnDescendenteORDER.addActionListener(new btnDescendenteORDERListener());
        btnPersonalizadoORDER.addActionListener(new btnPersonalizadoORDERListener());
        //Agrupa las diferentes opciones de ordenamiento
        this.grupoBotonesORDER = new ButtonGroup();
        grupoBotonesORDER.add(btnAscendenteORDER);
        grupoBotonesORDER.add(btnDescendenteORDER);
        grupoBotonesORDER.add(btnPersonalizadoORDER);
        //btnAscendenteORDER.setSelected(true);
        //Se inicializa el mensaje de error
        this.lbMensajeORDER = new JLabel();
        this.lbMensajeORDER.setVisible(false);
        
    }
    /**Esta es una clase interna, la cual no se si sera parte de la documentacion.
     * La motivacion de su uso es organizativa, nos ayuda a documentar mas facilmente nuestro codigo
     * 
       
       */
    public class btnAscendenteORDERListener implements ActionListener{
        /**Este metodo ordena un ArrayListzString> ingresado por parametro, de manera ascendente, tomando en cuenta que los elementos a ordenar seran String
         * @param aux ArrayList<String> a ordenar, debemos tener en cuenta que la comparacion que se utiliza es lexogramatica, utilizam0os por lo tanto el metodo compareToIgnoreCase()
           */
        public void ordenar(ArrayList<String> aux){
            int indice;
            String cabecera;
            aux.add(0,listaCabecerasPosiblesORDER.get(0));
            for(int i=1; i<listaCabecerasPosiblesORDER.size();i++){
                indice=i-1;
                cabecera=listaCabecerasPosiblesORDER.get(i);
                while(indice>=0 && (cabecera.compareToIgnoreCase(aux.get(indice))<0)){
                        indice--;
                    }
                aux.add(indice+1,cabecera);
            }
            
        }
        /**Este metodo seria el que se ejecutara cada vez que sea seleccionada la opcion "Ascendente" del panel ORDER.
         * @param e evento producido al seleccionar y deseleccionar el JRadioButton de la opcion "Ascendente"
           */
        public void actionPerformed(ActionEvent e) {
            if(btnAscendenteORDER.isSelected()==true){
                //Modifica el estados de variables de control ORDER
                AscendenteORDER = true;
                ordenColumnasORDER.setVisible(false);
                scrollPaneORDER.setVisible(false);
                comboBoxORDER.setVisible(false);
                
                //Ordenamiento
                if(listaOrdenadaAscendentementeORDER==null){
                   listaOrdenadaAscendentementeORDER = new ArrayList<String>();
                   ordenar(listaOrdenadaAscendentementeORDER);
                }
                //System.out.println("ListaOA"+listaOrdenadaAscendentementeORDER.toString());
                    
            }
            else{
                AscendenteORDER = false;
            }
        }
    }
    /**
       */
    public class btnDescendenteORDERListener implements ActionListener{
        public void ordenar(ArrayList<String> aux){
            int indice;
            String cabecera;
           // aux=new ArrayList<String>(listaCabecerasPosiblesORDER.size());
            aux.add(0,listaCabecerasPosiblesORDER.get(0));
            for(int i=1; i<listaCabecerasPosiblesORDER.size();i++){
                indice=i-1;
                cabecera=listaCabecerasPosiblesORDER.get(i);
                
                while(indice>=0 && (cabecera.compareToIgnoreCase(aux.get(indice))>0)){
                    //System.out,println("se comparo: "+cabecera+" con "+ aux.get(indice)+" se obtuvo: "+ cabecera.compareToIgnoreCase(aux.get(indice)));  
                    indice--;
                    }
                aux.add(indice+1,cabecera);
                    
            }
            
        }
        
        public void actionPerformed(ActionEvent e) {
           if(btnDescendenteORDER.isSelected()==true){
              DescendenteORDER = true; 
              ordenColumnasORDER.setVisible(false);
              scrollPaneORDER.setVisible(false);
              comboBoxORDER.setVisible(false);
              
              //Ordenamiento
              if(listaOrdenadaDescendentementeORDER==null){
                   listaOrdenadaDescendentementeORDER = new ArrayList<String>();
                   ordenar(listaOrdenadaDescendentementeORDER);
            }
            //System.out.println("ListaOD"+listaOrdenadaDescendentementeORDER.toString());
                 
            } 
            else{
                DescendenteORDER = false;
            }
        }
    }
    /**
       */
    public class btnPersonalizadoORDERListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            
            if(btnPersonalizadoORDER.isSelected()==true){
                PersonalizadoORDER = true;
                ordenColumnasORDER.setVisible(true);
                scrollPaneORDER.setVisible(true);
                comboBoxORDER.setVisible(true);
                comboBoxORDER.setEnabled(true);
            }
            else{
                PersonalizadoORDER = false;
                comboBoxORDER.setVisible(false);
                comboBoxORDER.setEnabled(false);
            }
        }
    }
    /**
       */
    public class seleccioncomboBoxListenerORDER implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String aux = comboBoxORDER.getSelectedItem().toString();
            int indice = listaSeleccionORDER.indexOf(aux);
           if(indice!=-1){
               listaSeleccionORDER.remove(indice);
            }
            else{
               listaSeleccionORDER.add(aux);
            }
            ordenColumnasORDER = new JTextArea(listaSeleccionORDER.toString());
            scrollPaneORDER.setViewportView(ordenColumnasORDER);
        }
    }
    
    private void cargaPanelBotones(){
        this.panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        this.btnCancelar= new JButton("Cancelar");
        this.btnCancelar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                //FrameAbrirCSV newFrame = new FrameAbrirCSV();
                panelAnterior.setVisible(true);
                dispose();
            }
        });
        
        this.btnVer= new JButton("Ver");
        this.btnVer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                allOK = true;
                if(selectSORT){
                    //ORDENAMOS LAS FILAS DE LA TABLA
                    esASCENDENTE = btnASCENDsort.isSelected();
                    csvFile.Sort(itemComboSORT, esASCENDENTE);
                }
                if(selectORDER){
                    //ORDENAMOS LAS COLUNAS DE LA TABLA
                    String[] aux = null;
                    if(btnAscendenteORDER.isSelected() || btnDescendenteORDER.isSelected()
                            || btnPersonalizadoORDER.isSelected()){
                        if(AscendenteORDER){
                            aux = new String[listaOrdenadaAscendentementeORDER.size()];
                            for(int i=0; i<listaOrdenadaAscendentementeORDER.size(); i++){
                                aux[i]=listaOrdenadaAscendentementeORDER.get(i);
                            }
                        }else if(DescendenteORDER){
                            aux = new String[listaOrdenadaDescendentementeORDER.size()];
                            for(int i=0; i<listaOrdenadaDescendentementeORDER.size(); i++){
                                aux[i]=listaOrdenadaDescendentementeORDER.get(i);
                            }
                        }else if(PersonalizadoORDER){
                            aux = new String[listaSeleccionORDER.size()];
                            for(int i=0; i<listaSeleccionORDER.size(); i++){
                                aux[i]=listaSeleccionORDER.get(i);
                            }
                        }else{
                            allOK=false;
                        }
                    }else{
                        allOK=false;
                    }
                    
                    if(aux!=null){
                        csvFile.Order(aux);
                    }else{
                        System.out.println("Error en la seccion Order");
                        JOptionPane.showMessageDialog(null, "Error al ORDENAR ENCABEZADOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                        allOK=false;
                    }
                }
                if(selectFILTER){
                    //FILTRAMOS LAS FILAS DE LA TABLA
                    if(FILTERok){
                        String condicion;
                        if(btnMayor.isSelected()){
                            condicion=">";
                        }else if(btnMenor.isSelected()){
                            condicion="<";
                        }else{
                            condicion="=";
                        }
                        
                        csvFile.Filter(itemComboFILTER, condicion, numFILTER);
                    }else{
                        //System.out.println("Error en la seccion Filtro");
                        JOptionPane.showMessageDialog(null, "Error en FILTRADO POR COLUMNA", "ERROR", JOptionPane.ERROR_MESSAGE);
                        allOK=false;
                    }
                }
                if(selectLIMIT){
                    //LIMITAMOS LA CANTIDAD DE FILAS DE LA TABLA
                    if(LIMITok){
                        csvFile.Limit(numLIMIT);
                    }else{
                        //System.out.println("Error en la seccion LIMIT");
                        JOptionPane.showMessageDialog(null, "Error al LIMITAR CANTIDAD DE FILAS", "ERROR", JOptionPane.ERROR_MESSAGE);
                        allOK=false;
                    }
                }
                
                if(allOK){
                    //MUESTRO TABLA
                    FrameTablaFinal newFrame = new FrameTablaFinal(instance, csvFile);
                    dispose();
                }
            }
        });
    }
    
    
    private void armarPanel(){
        armarPanelSort();
        armarPanelLimit();
        armarPanelFilter();
        armarPanelOrder();
        
        //Juntar 4 paneles principales
        JPanel panelOp = new JPanel(new GridLayout(2,2));
        panelOp.add(panelSORT);
        panelOp.add(panelORDER);
        panelOp.add(panelLIMIT);
        panelOp.add(panelFILTER);
        
        armarPanelBotones();
        
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(panelOp, BorderLayout.CENTER);
        panelMain.add(this.panelBotones, BorderLayout.SOUTH);
        
        contentPane.add(panelMain);
    }
    
    private void armarPanelSort(){
        Box hBoxSORT1 = Box.createHorizontalBox();
            hBoxSORT1.add(this.checkSORT);
        Box hBoxSORT2 = Box.createHorizontalBox();
            hBoxSORT2.add(Box.createGlue());
            Box vBoxSORTbuttons = Box.createVerticalBox();
                vBoxSORTbuttons.add(this.btnASCENDsort);
                vBoxSORTbuttons.add(this.btnDESCsort);
            hBoxSORT2.add(vBoxSORTbuttons);
        Box hBoxSORT3 = Box.createHorizontalBox();
            hBoxSORT3.add(Box.createGlue());
            hBoxSORT3.add(this.comboSORT);
        Box hBoxSORT4 = Box.createHorizontalBox();
            hBoxSORT4.add(Box.createGlue());
            hBoxSORT4.add(this.lbMensajeSORT);
        Box vBoxSORT1 = Box.createVerticalBox();
            vBoxSORT1.add(hBoxSORT2);
            vBoxSORT1.add(Box.createVerticalGlue());
            vBoxSORT1.add(hBoxSORT3);
            vBoxSORT1.add(hBoxSORT4);
        this.subpanelSORT = new JPanel();
        this.subpanelSORT.add(vBoxSORT1);
        this.subpanelSORT.setVisible(false);
        
        Box vBoxSORT2 = Box.createVerticalBox();
            vBoxSORT2.add(hBoxSORT1);
            vBoxSORT2.add(this.subpanelSORT);
        this.panelSORT.add(vBoxSORT2);
    
    }
    private void armarPanelLimit(){
        Box hBoxLIMIT1 = Box.createHorizontalBox();
            hBoxLIMIT1.add(this.checkLIMIT);
        Box hBoxLIMIT2 = Box.createHorizontalBox();
            hBoxLIMIT2.add(Box.createGlue());
            hBoxLIMIT2.add(this.tfNumLIMIT);
        Box hBoxLIMIT3 = Box.createHorizontalBox();
            hBoxLIMIT3.add(Box.createGlue());
            hBoxLIMIT3.add(this.lbMensajeLIMIT);
        Box vBoxLIMIT1 = Box.createVerticalBox();
            vBoxLIMIT1.add(hBoxLIMIT2);
            vBoxLIMIT1.add(hBoxLIMIT3);
        this.subpanelLIMIT = new JPanel();
        this.subpanelLIMIT.add(vBoxLIMIT1);
        this.subpanelLIMIT.setVisible(false);
        Box vBoxLIMIT2 = Box.createVerticalBox();
            vBoxLIMIT2.add(hBoxLIMIT1);
            vBoxLIMIT2.add(this.subpanelLIMIT);
        this.panelLIMIT.add(vBoxLIMIT2);
        
    }
    private void armarPanelFilter(){
        Box hBoxFILTER1 = Box.createHorizontalBox();
            hBoxFILTER1.add(this.checkFILTER);
        Box hBoxFILTER2 = Box.createHorizontalBox();
            hBoxFILTER2.add(Box.createGlue());
            hBoxFILTER2.add(this.lbMensajeFILTER);
        Box hBoxFILTER3 = Box.createHorizontalBox();
            hBoxFILTER3.add(Box.createGlue());
            hBoxFILTER3.add(this.comboFILTER);
        Box hBoxFILTER4 = Box.createHorizontalBox();
            Box vBoxRButtons = Box.createVerticalBox();
            vBoxRButtons.add(this.btnMayor);
            vBoxRButtons.add(this.btnMenor);
            vBoxRButtons.add(this.btnIgual);
            hBoxFILTER4.add(vBoxRButtons);
        Box hBoxFILTER5 = Box.createHorizontalBox();
            hBoxFILTER5.add(new JLabel("Numero a comparar: "));
            hBoxFILTER5.add(this.tfNumFILTER);
        Box vBoxFILTER1 = Box.createVerticalBox();
            vBoxFILTER1.add(hBoxFILTER3);
            vBoxFILTER1.add(hBoxFILTER4);
            vBoxFILTER1.add(hBoxFILTER5);
            vBoxFILTER1.add(hBoxFILTER2);//mensaje
        this.subpanelFILTER = new JPanel();
        this.subpanelFILTER.add(vBoxFILTER1);
        this.subpanelFILTER.setVisible(false);
        
        this.subpanelErrorFILTER = new JPanel();
            Box vBoxError = Box.createVerticalBox();
            JLabel error1 = new JLabel("SU ARCHIVO CSV NO POSEE ");
            error1.setBackground(Color.red);
            JLabel error2 = new JLabel("COLUMNAS NUMERICAS");
            error2.setBackground(Color.red);
            vBoxError.add(error1);
            vBoxError.add(error2);
        this.subpanelErrorFILTER.add(vBoxError);
        this.subpanelErrorFILTER.setVisible(false);
        
        Box vBoxFILTER2 = Box.createVerticalBox();
            vBoxFILTER2.add(hBoxFILTER1);
            vBoxFILTER2.add(this.subpanelErrorFILTER);
            vBoxFILTER2.add(this.subpanelFILTER);
        this.panelFILTER.add(vBoxFILTER2);
    
    }
    
    /**Este metodo construye el contenido del subpanel correspondiente a la opcion de ordenamietn de columnas.
     * se intento organizar este panel bajo 3 Box:del CSV
       */
    private void armarPanelOrder(){
    Box hBoxORDER1 = Box.createHorizontalBox();
            hBoxORDER1.add(this.checkORDER);
        Box hBoxORDER2 = Box.createHorizontalBox();
            hBoxORDER2.add(Box.createGlue());
            hBoxORDER2.add(this.lbMensajeORDER);
        Box hBoxORDER3 = Box.createHorizontalBox();
            hBoxORDER3.add(Box.createGlue());
            Box vBoxRButtons = Box.createVerticalBox();
                vBoxRButtons.add(this.btnAscendenteORDER);
                vBoxRButtons.add(this.btnDescendenteORDER);
                vBoxRButtons.add(this.btnPersonalizadoORDER);
                vBoxRButtons.add(this.scrollPaneORDER);
                vBoxRButtons.add(this.comboBoxORDER);
            hBoxORDER3.add(vBoxRButtons);
        Box vBoxORDER1 = Box.createVerticalBox();
            vBoxORDER1.add(hBoxORDER3);//radioButons+scrollPane++comoBox
            vBoxORDER1.add(hBoxORDER2);//mensaje
            
        this.subpanelORDER.add(vBoxORDER1);//Agrega a subpanelORDER las opciones de configuracion creadas anteriormente
        this.subpanelORDER.setVisible(false);
        
        Box vBoxORDER2 = Box.createVerticalBox();
            vBoxORDER2.add(hBoxORDER1);//checkBox
            vBoxORDER2.add(this.subpanelORDER);
        this.panelORDER.add(vBoxORDER2);
    }
    
    private void armarPanelBotones(){
        this.panelBotones.add(this.btnCancelar);
        this.panelBotones.add(this.btnVer);
    }
}