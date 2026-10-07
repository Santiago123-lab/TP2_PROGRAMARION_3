package interfaz;

import javax.swing.*;
import java.awt.event.ActionListener;

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
    private JTextArea resultadoRegiones;

    public MainView() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel escribaProvinciaText = new JLabel("Escriba una provincia:");
        escribaProvinciaText.setBounds(10, 11, 117, 14);
        frame.getContentPane().add(escribaProvinciaText);
        
        provinciaUsuario = new JTextField();
        provinciaUsuario.setBounds(132, 8, 86, 20);
        frame.getContentPane().add(provinciaUsuario);
        provinciaUsuario.setColumns(15);
        
        agregarProvinciaBoton = new JButton("Agregar provincia");
        agregarProvinciaBoton.setBounds(249, 7, 130, 23);
        frame.getContentPane().add(agregarProvinciaBoton);
        
        JLabel coloqueConexionText = new JLabel("Coloque su conexion:");
        coloqueConexionText.setBounds(10, 45, 117, 14);
        frame.getContentPane().add(coloqueConexionText);
        
        primeraProvinciaUsuario = new JTextField();
        primeraProvinciaUsuario.setText("Primera provincia");
        primeraProvinciaUsuario.setBounds(10, 70, 100, 20);
        frame.getContentPane().add(primeraProvinciaUsuario);
        
        segundaProvinciaUsuario = new JTextField();
        segundaProvinciaUsuario.setText("Segunda provincia");
        segundaProvinciaUsuario.setBounds(115, 70, 105, 20);
        frame.getContentPane().add(segundaProvinciaUsuario);
        
        pesoUsuario = new JTextField();
        pesoUsuario.setText("Peso");
        pesoUsuario.setBounds(225, 70, 45, 20);
        frame.getContentPane().add(pesoUsuario);
        
        conectarBoton = new JButton("Conectar");
        conectarBoton.setBounds(275, 69, 104, 23);
        frame.getContentPane().add(conectarBoton);
        
        JLabel cantRegionesText = new JLabel("Cantidad regiones:");
        cantRegionesText.setBounds(10, 113, 117, 14);
        frame.getContentPane().add(cantRegionesText);
        
        cantRegionesUsuario = new JTextField();
        cantRegionesUsuario.setBounds(132, 110, 86, 20);
        frame.getContentPane().add(cantRegionesUsuario);
        
        generarRegionesBoton = new JButton("Generar regiones");
        generarRegionesBoton.setBounds(230, 109, 149, 23);
        frame.getContentPane().add(generarRegionesBoton);
        
        resultadoRegiones = new JTextArea();
        resultadoRegiones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoRegiones);
        scrollPane.setBounds(10, 150, 414, 110);
        frame.getContentPane().add(scrollPane);
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    // --- GETTERS PARA QUE EL PRESENTER LEA LOS INPUTS ---
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

    // --- LISTENERS PARA CONECTAR CON EL PRESENTER ---
    public void setAgregarProvinciaListener(ActionListener l) { agregarProvinciaBoton.addActionListener(l); }
    public void setConectarListener(ActionListener l) { conectarBoton.addActionListener(l); }
    public void setGenerarRegionesListener(ActionListener l) { generarRegionesBoton.addActionListener(l); }
}