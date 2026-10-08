package interfaz;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class MainView {
    private JFrame frame;
    private JTextField provinciaUsuario;
    private JTextField primeraProvinciaUsuario;
    private JTextField segundaProvinciaUsuario;
    private JTextField pesoUsuario;
    private JTextField cantRegionesUsuario;
    private JButton agregarProvinciaBoton;
    private JButton conectarBoton;
    private JButton generarRegionesBoton;
    private JButton cargarArchivoBoton;
    private JButton reiniciarBoton;
    private JTextArea resultadoRegiones;
    private DefaultTableModel modeloProvincias;
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
        
        // Gestión de Provincias
        JPanel panelProvincias = new JPanel();
        panelProvincias.setBorder(new TitledBorder("Gestión de Provincias"));
        panelProvincias.setBounds(10, 11, 440, 170);
        panelProvincias.setLayout(null);
        frame.getContentPane().add(panelProvincias);
        
        JLabel escribaProvinciaText = new JLabel("Escriba una provincia:");
        escribaProvinciaText.setBounds(15, 30, 60, 14);
        frame.getContentPane().add(escribaProvinciaText);
        
        provinciaUsuario = new JTextField();
        provinciaUsuario.setBounds(15, 27, 91, 21);
        panelProvincias.add(provinciaUsuario);
        provinciaUsuario.setColumns(15);
        
        agregarProvinciaBoton = new JButton("Agregar provincia");
        agregarProvinciaBoton.setToolTipText("Agrega la provincia escrita al sistema");
        agregarProvinciaBoton.setBounds(109, 26, 122, 23);
        panelProvincias.add(agregarProvinciaBoton);
        
        cargarArchivoBoton = new JButton("Cargar .TXT");
        cargarArchivoBoton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        cargarArchivoBoton.setToolTipText("Carga múltiples provincias y conexiones desde un archivo");
        cargarArchivoBoton.setBounds(233, 26, 91, 23); 
        panelProvincias.add(cargarArchivoBoton);
        
        modeloProvincias = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloProvincias.addColumn("Provincias Ingresadas");
        
        JTable tablaProvincias = new JTable(modeloProvincias);
        JScrollPane scrollProvincias = new JScrollPane(tablaProvincias);
        scrollProvincias.setBounds(15, 55, 410, 100);
        panelProvincias.add(scrollProvincias);
        
        reiniciarBoton = new JButton("Reiniciar Todo");
        reiniciarBoton.setForeground(Color.RED);
        reiniciarBoton.setBackground(Color.RED);
        reiniciarBoton.setToolTipText("Borra el grafo, las tablas y el mapa actual");
        reiniciarBoton.setBounds(326, 26, 99, 23); 
        panelProvincias.add(reiniciarBoton);
        
        //conexiones (aristas)
        JPanel panelConexiones = new JPanel();
        panelConexiones.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Conexiones (Aristas)", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panelConexiones.setBounds(10, 190, 440, 170);
        panelConexiones.setLayout(null);
        frame.getContentPane().add(panelConexiones);
        
        primeraProvinciaUsuario = new JTextField("Provincia 1");
        primeraProvinciaUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        primeraProvinciaUsuario.setBounds(15, 30, 95, 21);
        panelConexiones.add(primeraProvinciaUsuario);
        
        segundaProvinciaUsuario = new JTextField("Provincia 2");
        segundaProvinciaUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        segundaProvinciaUsuario.setBounds(120, 30, 95, 21);
        panelConexiones.add(segundaProvinciaUsuario);
        
        pesoUsuario = new JTextField("Peso");
        pesoUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        pesoUsuario.setBounds(225, 30, 50, 21);
        panelConexiones.add(pesoUsuario);
        
        conectarBoton = new JButton("Conectar");
        conectarBoton.setBounds(285, 29, 141, 23);
        conectarBoton.setToolTipText("Crea la arista entre las dos provincias");
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
        
        generarRegionesBoton = new JButton("Calcular y Dibujar Regiones");
        generarRegionesBoton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        generarRegionesBoton.setToolTipText("Aplica Kruskal para dividir el grafo y DSU");
        generarRegionesBoton.setBounds(178, 26, 150, 23);
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
        
        Coordinate centroArgentina = new Coordinate(-40.3, -63);
        mapa.setDisplayPosition(centroArgentina, 4); 
        
        panelMapa.add(mapa);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public void agregarProvinciaATabla(String nombre) {
        modeloProvincias.addRow(new Object[]{nombre});
    }
    
    public void agregarConexionATabla(String p1, String p2, double peso) {
        modeloConexiones.addRow(new Object[]{p1, p2, peso});
    }
    
    public void limpiarTodo() {
        modeloProvincias.setRowCount(0);
        modeloConexiones.setRowCount(0); 
        resultadoRegiones.setText("");
        mapa.removeAllMapMarkers();
        mapa.removeAllMapPolygons();
    }
    
    //GETTERS PARA QUE EL PRESENTER LEA LOS INPUTS
    public String getNombreProvincia() { return provinciaUsuario.getText().trim(); }
    public String getPrimeraProvincia() { return primeraProvinciaUsuario.getText().trim(); }
    public String getSegundaProvincia() { return segundaProvinciaUsuario.getText().trim(); }
    public String getPesoStr() { return pesoUsuario.getText().trim(); }
    public String getCantRegionesStr() { return cantRegionesUsuario.getText().trim(); }

    public void limpiarCampoProvincia() { provinciaUsuario.setText(""); }
    
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(frame, mensaje, titulo, tipo);
    }

    public void mostrarResultado(String texto) {
        resultadoRegiones.setText(texto);
    }
    
    public JMapViewer getMapa() { return mapa; }

    //LISTENERS PARA CONECTAR CON EL PRESENTER
    public void setReiniciarListener(ActionListener l) { reiniciarBoton.addActionListener(l); }
    public void setAgregarProvinciaListener(ActionListener l) { agregarProvinciaBoton.addActionListener(l); }
    public void setConectarListener(ActionListener l) { conectarBoton.addActionListener(l); }
    public void setGenerarRegionesListener(ActionListener l) { generarRegionesBoton.addActionListener(l); }
    public void setCargarArchivoListener(ActionListener l) { cargarArchivoBoton.addActionListener(l);
    }
}