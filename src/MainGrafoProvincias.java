import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
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
