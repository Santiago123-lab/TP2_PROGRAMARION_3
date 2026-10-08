package interfaz;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
    private JTextArea resultadoRegiones;
    

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
        provinciaUsuario.setBounds(15, 27, 100, 21);
        panelProvincias.add(provinciaUsuario);
        provinciaUsuario.setColumns(15);
        
        agregarProvinciaBoton = new JButton("Agregar provincia");
        agregarProvinciaBoton.setToolTipText("Agrega la provincia escrita al sistema");
        agregarProvinciaBoton.setBounds(125, 26, 120, 23);
        panelProvincias.add(agregarProvinciaBoton);
        
        cargarArchivoBoton = new JButton("Cargar .TXT");
        cargarArchivoBoton.setToolTipText("Carga múltiples provincias y conexiones desde un archivo");
        cargarArchivoBoton.setBounds(255, 26, 120, 23); 
        panelProvincias.add(cargarArchivoBoton);
        
        //conexiones (aristas)
        JPanel panelConexiones = new JPanel();
        panelConexiones.setBorder(new TitledBorder("Conexiones (Aristas)"));
        panelConexiones.setBounds(10, 105, 440, 75);
        panelConexiones.setLayout(null);
        frame.getContentPane().add(panelConexiones);
        
        primeraProvinciaUsuario = new JTextField("Provincia 1");
        primeraProvinciaUsuario.setBounds(15, 30, 95, 21);
        panelConexiones.add(primeraProvinciaUsuario);
        
        segundaProvinciaUsuario = new JTextField("Provincia 2");
        segundaProvinciaUsuario.setBounds(120, 30, 95, 21);
        panelConexiones.add(segundaProvinciaUsuario);
        
        pesoUsuario = new JTextField("Peso");
        pesoUsuario.setBounds(225, 30, 50, 21);
        panelConexiones.add(pesoUsuario);
        
        conectarBoton = new JButton("Conectar");
        conectarBoton.setBounds(285, 29, 140, 23);
        conectarBoton.setToolTipText("Crea la arista entre las dos provincias");
        panelConexiones.add(conectarBoton);        
        
        //generación de regiones
        JPanel panelResultados = new JPanel();
        panelResultados.setBorder(new TitledBorder("Generación de Regiones"));
        panelResultados.setBounds(10, 190, 440, 350);
        panelResultados.setLayout(null);
        frame.getContentPane().add(panelResultados);
        
        JLabel cantRegionesText = new JLabel("Cantidad regiones:");
        cantRegionesText.setBounds(16, 29, 98, 17);
        panelResultados.add(cantRegionesText);
        
        cantRegionesUsuario = new JTextField();
        cantRegionesUsuario.setBounds(118, 27, 50, 21);
        panelResultados.add(cantRegionesUsuario);
        
        generarRegionesBoton = new JButton("Generar regiones");
        generarRegionesBoton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        generarRegionesBoton.setToolTipText("Aplica Kruskal para dividir el grafo");
        generarRegionesBoton.setBounds(178, 26, 150, 23);
        panelResultados.add(generarRegionesBoton);
        
        resultadoRegiones = new JTextArea();
        resultadoRegiones.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(resultadoRegiones);
        scrollResultados.setBounds(15, 60, 410, 105);
        panelResultados.add(scrollResultados);
    }

    public void mostrar() {
        frame.setVisible(true);
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

    //LISTENERS PARA CONECTAR CON EL PRESENTER
    public void setAgregarProvinciaListener(ActionListener l) { agregarProvinciaBoton.addActionListener(l); }
    public void setConectarListener(ActionListener l) { conectarBoton.addActionListener(l); }
    public void setGenerarRegionesListener(ActionListener l) { generarRegionesBoton.addActionListener(l); }
    public void setCargarArchivoListener(ActionListener l) { cargarArchivoBoton.addActionListener(l);
    }
}