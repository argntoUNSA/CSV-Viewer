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
    /**Esta clase permitira, mediante un conjunto de paneles, configurar nuestra 
       tabla antes de visualizarlas. Esta preconfiguracion incluira, ordenamiento de 
       la tabla segun el ordenamiento de una columna, ordenamiento de los encabezados
       Filtrar una columna indicada, con una condicion tambien indicada y Limitar el 
       numero de filas a mostrar.
      * Especificaciones:
        - Ordenamiento de la tabla segun el ordenamiento de una columna. Esto se 
            realiza primero ya que necesitamos el contenido original para un correcto
            ordenamiento
        - Ordenamiento de los encabezados (y por ende del cuerpo) pertenecientes 
            a la tabla, esto tambien necesita el contenido original de archivo CSV.
            Ordena las columnas indicadas, pero tambien brinda opciones de ordenamiento
            diferentes. Si se ordenaron las filas con anterioridad se mantendra ese
            orden.
        - Filtrar una columna indicada, con una condicion tambien dada y mostrar solo
            aquellas filas que cumplan con la condicion. Esto altera el contenido de
            la tabla eliminando aquellas que no cumplan dicha condicon. Si se ordeno
            con anterioridad se mantendra ese orden.
        - Limitar el numero de filas a mostrar dado un numero por el usuario, esto se
            realiza al ultimo ya que elimina las filas necesarias y por lo tanto
            altera el contenido de la tabla. Si se ordeno y/o se filtro se trabajara
            sobre el contendio obtenido luego de esas operaciones.
            
      * Como pudimos observar las tareas de ordenamiento tienen prioridad sobre las
        demas, ya que necesitan del contenido ortriginal obteniendo un ordenamiento
        o filtrado real.
        Por ultimo esta clase nos permitira, luego de configurar los datos, visualizar
        dicho archivo o elegir otro*/
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
        /**Este constructor se encarga de crear la ventana con los 4 paneles desde donde 
           donde se podran ingresar las configuraciones . Este constructor a parte de hacer
           un uso de los metodos de la clase JFrame utiliza dos metodos pertenecientes a
           esta misma clase(FramePreconfigCSV) los cuales son cargarComponentes() y 
           armarPanel().
          *@param csvFile Objeto de la clase CSV que reprensente un archivo del tipo
                            CSV. Dicho objeto debe ser instanciado e inicializado con
                            anterioridad. 
           */
        public FramePreConfigCSV(CSV csvFile){
            super("CSV Viewer - "+ ( (csvFile!=null)? csvFile.getDireccion() : "null"  ));
            this.contentPane = getContentPane();
            this.csvFile = csvFile;
            
            this.contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 30));
            cargarComponentes();
            armarPanel();
            
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            setBounds(50, 50, 700, 500);
            //device.setFullScreenWindow(this);
        }
        /**Este metodo es un nexo entre el constructor y los metodos que cargan los 
           diferentes paneles de preconfiguracion y al panel de botones. 
           */
        private void cargarComponentes(){
            cargarPanelSort();
            cargarPanelLimit();
            cargarPanelFilter();
            cargarPanelOrder();
            
            cargaPanelBotones();
        }
        /**Este metodo se encarga de cargar el 1er panel de ordenamiento:
               -ordenamiento de la tabla segun el ordenamiento de una columna. Esto
                se realiza primero ya que necesitamos el contenido original para un 
                correcto ordenamiento.
            *Especificaciones:
                -JCheckBox "ORDENAR ALFABETICAMENTE" el cual habilitara el subpanel
                -ButtonGroup donde se agrupara a:
                    -JRadioButton "Ascendente" 
                    -JRadioButton "Descendente"
                -JComboBox<String> el cual contiene las cabeceras del archivo CSV.
                -JLabel que informara sobre algun error .
           *Estos componentes cargados le permitiran al usuario elegir una columa
            y elegir la direccion de ordenamiento de la columna. Todo esto evitando
            que el usuario ingrese datos erroneos*/
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
        /**Este metodo se encarga de cargar el subpanel de limitar:
               -Limitar el numero de filas a mostrar dado un numero por el usuario,
               esto se realiza al ultimo ya que elimina las filas necesarias y por
               lo tanto altera el contenido de la tabla. Si se ordeno y/o se filtro
               se trabajara sobre el contendio obtenido luego de esas operaciones.
          *Especificacioness:
                -JCheckBox "LIMITAR CANTIDAD DE FILAS" el cual habilitara el subpanel.
                -JTextField donde,por defecto, tendra el valor '0' pero se podra editar.
                -JLabel el cual informara de algun error.
          *Estos componentes cargados le permitiran al usuario elegir una cantidad de 
            columa a mostrar.Todo esto evitando que el usuario ingrese datos erroneos
            */
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
        /**Este metodo se encarga de cargar el subpanel de Filtro:
               -Filtrar una columna indicada, con una condicion tambien dada y mostrar solo
                aquellas filas que cumplan con la condicion. Esto altera el contenido de
                la tabla eliminando aquellas que no cumplan dicha condicon. Si se ordeno
                con anterioridad se mantendra ese orden.
                
        *  Especificaciones:
                -JCheckBox "FILTRADO POR COLUMNA" el cual habilitara el subpanel.
                -JComboBox donde,por defecto, tendra el valor de la primera cabecera
                    pero se podra elegir otra.
                -ButtonGroup donde se cargara a:
                    -JRadioButton "<"
                    -JRadioButton ">"
                    -JRadioButton "="
                    Estos botones permiten elegir la 1era parte de la condicion.
                -JTextField donde colocara la 2da parte de la condicion, un nro real 
                -JLabel el cual informara de algun error.
        *   Estos componentes cargados le permitiran al usuario elegir una columna, 
            filtrarla (mediante una condicion del tipo "mayor"/"menor"/"igual que" y un
            nro real) para luego mostrar solo aquellas filas que cumplan dicha codnicion.
            Todo esto evitando que el usuario ingrese datos erroneos.
            */
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
        
        /**Este metodo se encarga de cargar el subpanel de order:
            - Ordenamiento de los encabezados (y por ende del cuerpo) pertenecientes 
                a la tabla, esto tambien necesita el contenido original de archivo CSV.    
                ordena las columnas indicadas, pero tambien brinda opciones de ordenamiento
                diferentes. Si se ordenaron las filas con anterioridad se mantendra ese orden.
                
          *Especfificaciones:
            -JCheckBox "ORDENAR LOS ENCABEZADOS" el cual habilitara el subpanel.
            -ButtonGroup donde se cargara a:
                -JRadioButton "Ascendente"
                -JRadioButton "Descendente"
                -JRadioButton "Personalizado"
                Estos botones permiten elegir la manera de ordenamiento de los 
                encabezados.Este ultimo ("Personalizado") habilitara, ademas, un
                panel donde se encontraran los sgtes componentes:
                    -JScrollPane que almacena un JTextArea que muestra el orden
                        que ira eligiendo el usuario.
                    -JComboBox el cual contendra como Items a todos los encabezados
                        de la tabla.
                Este ultimo panel permitira al usuario ir definiendo el orden 
                de los encabezados seleccionando los items del combo box. Si 
                selecciona alguno ya seleccionado entonces se quitara del orden
                personalizado.
            -JLabel el cual informara de algun error.
        *Estos componentes cargados le permitiran al usuario elegir el orden
         de las columnas de la tabla, este orden personalizado, el cual desarrollamos
         anteriormente, le permitira tambien elegir algunas o todas las columnas
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
        //btnPersonalizadoORDER.setSelected(true);
        //Se inicializa el mensaje de error
        this.lbMensajeORDER = new JLabel();
        this.lbMensajeORDER.setVisible(false);
        
    }
    /**Esta es una clase interna, la cual no se si sera parte de la documentacion.
     * La motivacion de su uso es organizativa, nos ayuda a documentar mas facilmente
     * nuestro codigo.
     * 
       
       */
    public class btnAscendenteORDERListener implements ActionListener{
        /**Este metodo ordena un ArrayListzString> ingresado por parametro, de
            manera ascendente, tomando en cuenta que los elementos a ordenar seran
            Strings que representaran las cabeceras de la tabla.
         * @param aux ArrayList<String> a ordenar, debemos tener en cuenta que
            la comparacion que se utiliza es lexogramatica, utilizamos por lo 
            tanto el metodo compareToIgnoreCase().
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
        /**Este metodo seria el que se ejecutara cada vez que sea seleccionada
            la opcion "Ascendente" del panel ORDER.
         * @param e evento producido al seleccionar el JRadioButton de la opcion
                     "Ascendente"
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
        /**Esta es una clase interna, la cual no se si sera parte de la documentacion.
         * La motivacion de su uso es organizativa, nos ayuda a documentar mas facilmente
         * nuestro codigo.
          */
     
      public class btnDescendenteORDERListener implements ActionListener{
        /**Este metodo ordena un ArrayListzString> ingresado por parametro, de
            manera descendente, tomando en cuenta que los elementos a ordenar seran
            Strings que representaran las cabeceras de la tabla.
         * @param aux ArrayList<String> a ordenar, debemos tener en cuenta que
            la comparacion que se utiliza es lexogramatica, utilizamos por lo 
            tanto el metodo compareToIgnoreCase().
           */
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
        /**Este metodo seria el que se ejecutara cada vez que sea seleccionada
            la opcion "Descendente" del panel ORDER.
         * @param e evento producido al seleccionar el JRadioButton de la opcion
                     "Descendente"
           */
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
    /**Esta es una clase interna, la cual no se si sera parte de la documentacion.
     * La motivacion de su uso es organizativa, nos ayuda a documentar mas facilmente
     * nuestro codigo.
       */
    public class btnPersonalizadoORDERListener implements ActionListener{
        /**Este metodo seria el que se ejecutara cada vez que sea seleccionada
            la opcion "Personalizado" del panel ORDER.
         * @param e evento producido al seleccionar el JRadioButton de la opcion
                     "Personalizado"
           */
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
    /**Esta es una clase interna, la cual no se si sera parte de la documentacion.
     * La motivacion de su uso es organizativa, nos ayuda a documentar mas facilmente
     * nuestro codigo.
       */
    public class seleccioncomboBoxListenerORDER implements ActionListener{
        /**Este metodo seria el que se ejecutara cada vez que sea seleccionada
            un Item del Combo box almacenado en el panel ORDER.
         * @param e evento producido al seleccionar un Item perteneciente a el
                     combo box. Si el item no fue seleccionado se agregara al JTextField
                     para informar el orden actual. Si ya fue seleccionado
                     anteriormente se quitara del orden actual.
           */
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
    /**Este metodo se encarga de cargar el panel de botones pricipales.
     * Especificaciones:
        -JButton "Cancelar" cancela la prefconfiguracion y permite volver a la
            instancia anterior.
        -JButton "Ver" con el cual se acepta la preconfiguracion y permite
            ir a otra instancia.
        -JLabel que informara sobre algun error .
     * Estos componentes cargados le permitiran al usuario elegir una accion entre:
        -"Cancelar" la preconfiguracion para poder elegir otro archivo u otra
            manera de visualizarlo.
        -"Ver" la configuracion actual y abrir una nueva ventana en donde
                segun lo que se configuro, se  visualizaria la tabla.
        Todo esto evitando que el usuario ingrese datos erroneos.
    */
    private void cargaPanelBotones(){
        this.panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        this.btnCancelar= new JButton("Cancelar");
        this.btnCancelar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                FrameAbrirCSV newFrame = new FrameAbrirCSV();
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
                    FrameTablaFinal newFrame = new FrameTablaFinal(csvFile);
                    dispose();
                }
            }
        });
    }
    
    /**Este metodo posiciona en la ventana principal todos los componentes cargados
       anteriormente.
       *Especificaciones:
        -paneles de botones : al sur del panel principal
        -panel opciones: donde se cargan todos los paneles de configuracion. Estos se 
            cargaran en dicho panel posicionados dentro de una matriz de 2x2.
       *Los componentes no seran distribuidos literalmente como lo  descrito anteriormente
       mas bien eso fue a modo de simplificar la explicacion.
            */
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
    /**Este metodo posiciona en el subpanel "SORT" todos los componentes correspondientes
       cargados con anterioridad. se los distribuyo por "cajas":
       *Especificaciones:
        -Caja superior: : Donde se posiciona un JCheckBox.
        -Caja medio1: Donde se se posicionan dos JRadioButton.
        -Caja medio2: Donde se se posiciona un JComboBox.
        -Caja inferior: Donde se se posiciona un JLabel.
       *Los componentes no seran distribuidos literalmente como lo  descrito anteriormente
       mas bien eso fue a modo de simplificar la explicacion.
        */
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
    /**Este metodo posiciona en el subpanel "LIMIT" todos los componentes correspondientes
       cargados con anterioridad. se los distribuyo por "cajas".
       *Especificaciones:
        -Caja superior: Donde se posiciona un JCheckBox.
        -Caja medio: Donde se se posicionan dos JTextField.
        -Caja inferior: Donde se se posiciona un JLabel.
       *Los componentes no seran distribuidos literalmente como lo  descrito anteriormente
       mas bien eso fue a modo de simplificar la explicacion.
        */
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
    /**Este metodo posiciona en el subpanel "LIMIT" todos los componentes correspondientes
       cargados con anterioridad. se los distribuyo por "cajas".
       *Especificaciones:
        -Caja superior: Donde se posiciona un JCheckBox.
        -Caja medio1: Donde se se posicionan un JComboBox.
        -Caja medio2: Donde se posicionan unos JRadioButton.
        -Caja medio3: Donde se posiciona un JLabel y un JTextField.
        -Caja inferior: Donde se se posiciona un JLabel.
       *Los componentes no seran distribuidos literalmente como lo  descrito anteriormente
       mas bien esto fue a modo de simplificar la explicacion.
        */
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
    /**Este metodo posiciona en el subpanel "ORDER" todos los componentes correspondientes
       cargados con anterioridad. se los distribuyo por "cajas".
      *Especificaciones:
        -Caja superior: Donde se posiciona un JCheckBox.
        -Caja medio1: Donde se se posicionan unos JRadioButton.
        -Caja medio2: Donde se posicionan unos JTextField.
        -Caja medio2: Donde se posiciona un JComboBox.
        -Caja inferior: Donde se se posiciona un JLabel.
       *Los componentes no seran distribuidos literalmente como lo  descrito anteriormente
       mas bien eso fue a modo de simplificar la explicacion.
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
    /**Este metodo arma el panel "Botones", en el se almacenan los botones para ir o volver a una 
       instancia diferente.
      *Especificaciones:
       -Sur derecha1: Donde se posiciona un JButton.
       -Sur derecha2: Donde se posiciona un JButton.
      *Los componentes no seran distribuidos literalmente como lo  descrito anteriormente
       mas bien eso fue a modo de simplificar la explicacion.
      */
    private void armarPanelBotones(){
        this.panelBotones.add(this.btnCancelar);
        this.panelBotones.add(this.btnVer);
    }
}