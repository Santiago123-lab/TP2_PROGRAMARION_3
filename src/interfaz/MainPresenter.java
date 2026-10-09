package interfaz;

import estructuras.Vertice;
import estructuras.Region;
import grafo.Grafo;
import grafo.Nodo;
import algoritmos.Kruskal;
import algoritmos.BuscadorDeRegiones;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashSet;


import java.util.List;

import java.util.Scanner;
import java.util.Set;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPresenter {

    private MainView vista;
    private Grafo<Vertice> grafo;
    private BuscadorDeRegiones buscador;

    public MainPresenter(MainView vista, Grafo<Vertice> grafoInicial) {
        this.vista = vista;
        this.grafo = grafoInicial;
        this.buscador = new BuscadorDeRegiones();

        configurarListeners();
    }
    
    
    private void configurarListeners() {

        vista.setAgregarVerticeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarVertice();
            }
        });

        vista.setConectarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectarVertices();
            }
        });

        vista.setGenerarRegionesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarRegiones();
            }
        });

        vista.setCargarArchivoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDesdeArchivo();
            }
        });

        vista.setReiniciarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciar();
            }
        });
    }

    private void reiniciar() {
        grafo = new Grafo<>();
        vista.limpiarTodo();

        vista.mostrarMensaje(
                "El sistema ha sido reiniciado.",
                "Reinicio",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    
    private void agregarVertice() {
    	
        try {
            String nombre = vista.getNombreVertice();
            
            if (nombre.trim().isEmpty()) {
                throw new IllegalArgumentException(
                    "Por favor, ingrese un nombre para el vértice."
                );
            }
            Vertice vertice = new Vertice(nombre);
            grafo.agregarNodo(vertice);
            vista.mostrarMensaje("Vértice agregado: " + vertice.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampoVertice();
            vista.agregarVerticeATabla(vertice.getNombre());
            

        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void conectarVertices() {
        try {
            Vertice p1 = new Vertice(vista.getPrimerVertice());
            Vertice p2 = new Vertice(vista.getSegundoVertice());
            double peso = Double.parseDouble(vista.getPesoStr());

            Nodo<Vertice> nodo1 = buscarNodoProvincia(p1);
            Nodo<Vertice> nodo2 = buscarNodoProvincia(p2);

            if (nodo1 == null || nodo2 == null) {
                throw new IllegalArgumentException("Ambos vertices deben estar agregados al grafo previamente.");
            }

            grafo.agregarArista(nodo1, nodo2, peso);
            vista.mostrarMensaje("Conexión agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.agregarConexionATabla(p1.getNombre(), p2.getNombre(), peso);
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("El peso de la arista debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarRegiones() {
        try {
            int k = Integer.parseInt(vista.getCantRegionesStr());
            
            if (grafo.getTodosLosNodos().isEmpty()) {
                throw new IllegalStateException("El grafo está vacío. Cargue los vértices y conexiones primero.");
            }
            
            //calculamos el MST usando Kruskal
            Grafo<Vertice> mst = Kruskal.calcularMST(grafo);
            
            //buscamos las regiones dividiendo el MST
            List<Region> regiones = buscador.agrupar(mst, k);

            //formateamos el resultado para mostrarlo en el JTextArea
            StringBuilder sb = new StringBuilder();
            sb.append("Se generaron ").append(regiones.size()).append(" regiones:\n");
            for (int i = 0; i < regiones.size(); i++) {
                sb.append("Región ").append(i + 1).append(": ").append(regiones.get(i).getVertices()).append("\n");
            }
            
//            actualizarMapaVisual(regiones);
            vista.mostrarResultado(sb.toString());
            
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("La cantidad de regiones debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDesdeArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione el archivo (.txt)");

        int seleccion = fileChooser.showOpenDialog(null);
        
        if (seleccion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File archivo = fileChooser.getSelectedFile();
            
        try (	
        	FileInputStream fis = new FileInputStream(archivo);
        	Scanner scanner = new Scanner(fis);
        ) {
        	Grafo<Vertice> nuevoGrafo = new Grafo<>();
        	
            int conexionesAgregadas = 0;
            List<String[]> conexionesCargadas = new java.util.ArrayList<>();
            Set<String> verticesCargados = new LinkedHashSet<>();
            
            while (scanner.hasNextLine()) {
            	String linea = scanner.nextLine().trim();
                    
                if (linea.isEmpty()) continue; 
                    
                String[] partes = linea.split(",");
                  
                if (partes.length != 3) {
                	throw new IllegalArgumentException("Formato inválido en la línea: " + linea);
                }
                    
                String nombreV1 = partes[0].trim();
                String nombreV2 = partes[1].trim();                       
                double peso = Double.parseDouble(partes[2].trim());

                if (nombreV1.isEmpty() || nombreV2.isEmpty()) {
                    throw new IllegalArgumentException(
                        "Los vértices no pueden tener nombres vacíos."
                    );
                }

                Vertice v1 = new Vertice(nombreV1);
                Vertice v2 = new Vertice(nombreV2);

                Nodo<Vertice> nodo1 = buscarNodoVertice(nuevoGrafo, v1);

                Nodo<Vertice> nodo2 = buscarNodoVertice(nuevoGrafo, v2);

                if (nodo1 == null) {
                    nodo1 = nuevoGrafo.agregarNodo(v1);
                    verticesCargados.add(v1.getNombre());
                }
                if (nodo2 == null) {
                    nodo2 = nuevoGrafo.agregarNodo(v2);
                    verticesCargados.add(v2.getNombre());
                }

                nuevoGrafo.agregarArista(nodo1, nodo2, peso);
                conexionesCargadas.add(new String[] { v1.getNombre(), v2.getNombre(), String.valueOf(peso)});
                conexionesAgregadas++;
            }
            
            grafo = nuevoGrafo;
            vista.limpiarTodo();

            for (String nombre : verticesCargados) {
                vista.agregarVerticeATabla(nombre);
            }

            for (String[] conexion : conexionesCargadas) {
                vista.agregarConexionATabla(
                    conexion[0],
                    conexion[1],
                    Double.parseDouble(conexion[2])
                );
            }
                
//                actualizarMapaVisual(null);
                vista.mostrarMensaje("Archivo cargado exitosamente. Se agregaron " + conexionesAgregadas + " conexiones.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
                vista.mostrarMensaje("Error al leer el archivo: " + ex.getMessage() + "\nSe ha cancelado la carga.", "Error", JOptionPane.ERROR_MESSAGE);
            }
     }
 
	private Nodo<Vertice> buscarNodoProvincia(Vertice provincia) {
	    for (Nodo<Vertice> nodo : grafo.getTodosLosNodos()) {
	        if (nodo.getValor().equals(provincia)) {
	            return nodo;
	        }
	    }
	    return null;
	}
	
	private Nodo<Vertice> buscarNodoVertice(Grafo<Vertice> grafo, Vertice provincia) {
	    for (Nodo<Vertice> nodo : grafo.getTodosLosNodos()) {

	        if (nodo.getValor().equals(provincia)) {
	            return nodo;
	        }
	    }

	    return null;
	}
	
}