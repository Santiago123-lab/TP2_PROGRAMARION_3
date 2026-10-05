import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import estructuras.Provincia;
import grafo.Grafo;
import grafo.Nodo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class MainGrafoProvincias {

	private JFrame frame;
	private JTextField provinciaUsuario;
	private JTextField primeraProvinciaUsuario;
	private JTextField segundaProvinciaUsuario;
	private JTextField pesoUsuario;
	private JTextField cantRegionesUsuario;
	
	private Grafo<Provincia> grafo = new Grafo<>();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGrafoProvincias window = new MainGrafoProvincias();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public MainGrafoProvincias() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel escribaProvinciaText = new JLabel("Escriba una provincia:");
		escribaProvinciaText.setBounds(10, 11, 117, 14);
		frame.getContentPane().add(escribaProvinciaText);
		
		JButton agregarProvinciaBoton = new JButton("Agregar provincia");
		
		agregarProvinciaBoton.addActionListener(new ActionListener() {
			
		    public void actionPerformed(ActionEvent e) {
		    	
		        String nombre = provinciaUsuario.getText();

		        try {
		            Provincia provincia = new Provincia(nombre);
		            grafo.agregarNodo(provincia);

		            JOptionPane.showMessageDialog(frame,"Provincia agregada: " + provincia.getNombre());

		            provinciaUsuario.setText("");

		        } catch (IllegalArgumentException ex) {
		        	
		            JOptionPane.showMessageDialog(frame,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		agregarProvinciaBoton.setBounds(249, 7, 117, 23);
		frame.getContentPane().add(agregarProvinciaBoton);
		
		provinciaUsuario = new JTextField();
		provinciaUsuario.setBounds(132, 8, 86, 20);
		frame.getContentPane().add(provinciaUsuario);
		provinciaUsuario.setColumns(15);
		
		JLabel coloqueConexionText = new JLabel("Coloque su conexion:");
		coloqueConexionText.setBounds(10, 45, 117, 14);
		frame.getContentPane().add(coloqueConexionText);
		
		primeraProvinciaUsuario = new JTextField();
		primeraProvinciaUsuario.setText("Primera provincia");
		primeraProvinciaUsuario.setBounds(10, 70, 93, 20);
		frame.getContentPane().add(primeraProvinciaUsuario);
		primeraProvinciaUsuario.setColumns(10);
		
		segundaProvinciaUsuario = new JTextField();
		segundaProvinciaUsuario.setText("Segunda provincia");
		segundaProvinciaUsuario.setColumns(10);
		segundaProvinciaUsuario.setBounds(113, 70, 105, 20);
		frame.getContentPane().add(segundaProvinciaUsuario);
		
		pesoUsuario = new JTextField();
		pesoUsuario.setText("Peso");
		pesoUsuario.setColumns(10);
		pesoUsuario.setBounds(228, 70, 37, 20);
		frame.getContentPane().add(pesoUsuario);
		
		JButton conectarBoton = new JButton("Conectar");
		conectarBoton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		        String nombrePrimera = primeraProvinciaUsuario.getText();
		        String nombreSegunda = segundaProvinciaUsuario.getText();

		        try {
		            Provincia primera = new Provincia(nombrePrimera);
		            Provincia segunda = new Provincia(nombreSegunda);

		            double peso = Double.parseDouble(pesoUsuario.getText());

		            Nodo<Provincia> nodoPrimera = null;
		            Nodo<Provincia> nodoSegunda = null;

		            for (Nodo<Provincia> nodo : grafo.getTodosLosNodos()) {

		                if (nodo.getValor().equals(primera)) {
		                    nodoPrimera = nodo;
		                }

		                if (nodo.getValor().equals(segunda)) {
		                    nodoSegunda = nodo;
		                }
		            }

		            if (nodoPrimera == null || nodoSegunda == null) {
		                throw new IllegalArgumentException(
		                        "Ambas provincias deben estar agregadas al grafo.");
		            }

		            grafo.agregarArista(nodoPrimera, nodoSegunda, peso);

		            JOptionPane.showMessageDialog(frame,"Conexión agregada correctamente.");

		        } catch (NumberFormatException ex) {

		            JOptionPane.showMessageDialog(frame,"El peso debe ser un número.","Error",JOptionPane.ERROR_MESSAGE);

		        } catch (IllegalArgumentException ex) {

		            JOptionPane.showMessageDialog(frame,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
	
		conectarBoton.setBounds(275, 69, 89, 23);
		frame.getContentPane().add(conectarBoton);
		
		JLabel cantRegionesText = new JLabel("Cantidad de regiones:");
		cantRegionesText.setBounds(10, 113, 117, 14);
		frame.getContentPane().add(cantRegionesText);
		
		cantRegionesUsuario = new JTextField();
		cantRegionesUsuario.setBounds(132, 110, 86, 20);
		frame.getContentPane().add(cantRegionesUsuario);
		cantRegionesUsuario.setColumns(10);
		
		JButton generarRegionesBoton = new JButton("Generar regiones");
		generarRegionesBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		generarRegionesBoton.setBounds(243, 109, 123, 23);
		frame.getContentPane().add(generarRegionesBoton);
		
		JTextArea ResultadoRegiones = new JTextArea();
		ResultadoRegiones.setColumns(30);
		ResultadoRegiones.setRows(8);
		ResultadoRegiones.setEditable(false);
		ResultadoRegiones.setBounds(17, 169, 86, 81);
		frame.getContentPane().add(ResultadoRegiones);
	}
}
