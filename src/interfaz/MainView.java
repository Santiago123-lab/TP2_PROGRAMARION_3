package interfaz;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.Color;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
//import org.openstreetmap.gui.jmapviewer.Coordinate;

public class MainView {
    private JFrame frame;
    private JTextField verticeUsuario;
    private JTextField primerVerticeUsuario;
    private JTextField segundoVerticeUsuario;
    private JTextField pesoUsuario;
    private JTextField cantRegionesUsuario;
    private JButton agregarVerticeBoton;
    private JButton conectarBoton;
    private JButton generarRegionesBoton;
    private JButton cargarArchivoBoton;
    private JButton reiniciarBoton;
    private JTextArea resultadoRegiones;
    private DefaultTableModel modeloVertices;
    private DefaultTableModel modeloConexiones;
    private JMapViewer mapa;
    

    public MainView() {
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) { 
            e.printStackTrace(); 
        }
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Buscador de Regiones - TP2");
        frame.setBounds(100, 100, 1100, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        // Gestión de vértices
        JPanel panelVertices = new JPanel();
        panelVertices.setBorder(new TitledBorder("Gestión de Vértices"));
        panelVertices.setBounds(10, 11, 440, 170);
        panelVertices.setLayout(null);
        frame.getContentPane().add(panelVertices);   
        
        verticeUsuario = new JTextField();
        verticeUsuario.setBounds(15, 27, 91, 21);
        panelVertices.add(verticeUsuario);
        verticeUsuario.setColumns(15);
        
        agregarVerticeBoton = new JButton("Agregar vértice");
        agregarVerticeBoton.setToolTipText("Agrega vértice al sistema");
        agregarVerticeBoton.setBounds(109, 26, 122, 23);
        panelVertices.add(agregarVerticeBoton);
        
        cargarArchivoBoton = new JButton("Cargar .TXT");
        cargarArchivoBoton.setToolTipText("Carga múltiples vértices y conexiones desde un archivo");
        cargarArchivoBoton.setBounds(233, 26, 91, 23); 
        panelVertices.add(cargarArchivoBoton);
        
        modeloVertices = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloVertices.addColumn("Vértices Ingresados");
        
        JTable tablaVertices = new JTable(modeloVertices);
        JScrollPane scrollVertices = new JScrollPane(tablaVertices);
        scrollVertices.setBounds(15, 55, 410, 100);
        panelVertices.add(scrollVertices);
        
        reiniciarBoton = new JButton("Reiniciar Todo");
        reiniciarBoton.setForeground(Color.RED);
        reiniciarBoton.setBackground(Color.RED);
        reiniciarBoton.setToolTipText("Borra el grafo, las tablas y el mapa actual");
        reiniciarBoton.setBounds(326, 26, 99, 23); 
        panelVertices.add(reiniciarBoton);
        
        //conexiones (aristas)
        JPanel panelConexiones = new JPanel();
        panelConexiones.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Conexiones (Aristas)", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelConexiones.setBounds(10, 190, 440, 170);
        panelConexiones.setLayout(null);
        frame.getContentPane().add(panelConexiones);
        
        primerVerticeUsuario = new JTextField();
        primerVerticeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        primerVerticeUsuario.setBounds(15, 30, 95, 21);
        panelConexiones.add(primerVerticeUsuario);
        
        segundoVerticeUsuario = new JTextField();
        segundoVerticeUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        segundoVerticeUsuario.setBounds(120, 30, 95, 21);
        panelConexiones.add(segundoVerticeUsuario);
        
        pesoUsuario = new JTextField();
        pesoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        pesoUsuario.setBounds(225, 30, 50, 21);
        panelConexiones.add(pesoUsuario);
        
        JLabel vertice1Text = new JLabel("Vértice 1");
        vertice1Text.setHorizontalAlignment(SwingConstants.CENTER);
        vertice1Text.setBounds(15, 15, 95, 15);
        panelConexiones.add(vertice1Text);

        JLabel vertice2Text = new JLabel("Vértice 2");
        vertice2Text.setHorizontalAlignment(SwingConstants.CENTER);
        vertice2Text.setBounds(120, 15, 95, 15);
        panelConexiones.add(vertice2Text);

        JLabel pesoText = new JLabel("Peso");
        pesoText.setHorizontalAlignment(SwingConstants.CENTER);
        pesoText.setBounds(225, 15, 50, 15);
        panelConexiones.add(pesoText);
        
        conectarBoton = new JButton("Conectar");
        conectarBoton.setBounds(285, 29, 141, 23);
        conectarBoton.setToolTipText("Crea la arista entre los dos Vértices");
        panelConexiones.add(conectarBoton);        
        
        modeloConexiones = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloConexiones.addColumn("Origen");
        modeloConexiones.addColumn("Destino");
        modeloConexiones.addColumn("Peso");
        
        JTable tablaConexiones = new JTable(modeloConexiones);
        JScrollPane scrollConexiones = new JScrollPane(tablaConexiones);
        scrollConexiones.setBounds(15, 55, 410, 100);
        panelConexiones.add(scrollConexiones);
        
        //generación de regiones
        JPanel panelResultados = new JPanel();
        panelResultados.setBorder(new TitledBorder("Generación de Regiones"));
        panelResultados.setBounds(10, 370, 440, 175);
        panelResultados.setLayout(null);
        frame.getContentPane().add(panelResultados);
        
        JLabel cantRegionesText = new JLabel("Cantidad regiones:");
        cantRegionesText.setBounds(16, 29, 98, 17);
        panelResultados.add(cantRegionesText);
        
        cantRegionesUsuario = new JTextField();
        cantRegionesUsuario.setBounds(118, 27, 50, 21);
        panelResultados.add(cantRegionesUsuario);
        
        generarRegionesBoton = new JButton("Calcular Regiones");
        generarRegionesBoton.setToolTipText("Aplica Kruskal para dividir el grafo y DSU");
        generarRegionesBoton.setBounds(178, 26, 248, 23);
        panelResultados.add(generarRegionesBoton);
        
        resultadoRegiones = new JTextArea();
        resultadoRegiones.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(resultadoRegiones);
        scrollResultados.setBounds(15, 55, 410, 105);
        panelResultados.add(scrollResultados);
        
        
        //Panel del mapa
        JPanel panelMapa = new JPanel();
        panelMapa.setBorder(new TitledBorder("Visualización Geográfica"));
        panelMapa.setBounds(460, 11, 610, 535);
        panelMapa.setLayout(null); 
        frame.getContentPane().add(panelMapa);
        
        mapa = new JMapViewer();
        mapa.setBounds(15, 25, 580, 495); 
        mapa.setZoomControlsVisible(false);
        
        panelMapa.add(mapa);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public void agregarVerticeATabla(String nombre) {
        modeloVertices.addRow(new Object[]{nombre});
    }
    
    public void agregarConexionATabla(String p1, String p2, double peso) {
        modeloConexiones.addRow(new Object[]{p1, p2, peso});
    }
    
    public void limpiarTodo() {
        modeloVertices.setRowCount(0);
        modeloConexiones.setRowCount(0); 
        resultadoRegiones.setText("");
        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();
    }
    
    //GETTERS PARA QUE EL PRESENTER LEA LOS INPUTS
    public String getNombreVertice() { return verticeUsuario.getText().trim(); }
    public String getPrimerVertice() { return primerVerticeUsuario.getText().trim(); }
    public String getSegundoVertice() { return segundoVerticeUsuario.getText().trim(); }
    public String getPesoStr() { return pesoUsuario.getText().trim(); }
    public String getCantRegionesStr() { return cantRegionesUsuario.getText().trim(); }

    public void limpiarCampoVertice() { verticeUsuario.setText(""); }
    
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(frame, mensaje, titulo, tipo);
    }

    public void mostrarResultado(String texto) {
        resultadoRegiones.setText(texto);
    }
    
    public JMapViewer getMapa() { return mapa; }

    //LISTENERS PARA CONECTAR CON EL PRESENTER
    public void setReiniciarListener(ActionListener l) { reiniciarBoton.addActionListener(l); }
    public void setAgregarVerticeListener(ActionListener l) { agregarVerticeBoton.addActionListener(l); }
    public void setConectarListener(ActionListener l) { conectarBoton.addActionListener(l); }
    public void setGenerarRegionesListener(ActionListener l) { generarRegionesBoton.addActionListener(l); }
    public void setCargarArchivoListener(ActionListener l) { cargarArchivoBoton.addActionListener(l);
    }
}