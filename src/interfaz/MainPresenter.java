package interfaz;

import estructuras.Provincia;
import estructuras.Region;
import grafo.Grafo;
import grafo.Nodo;
import algoritmos.Kruskal;
import algoritmos.BuscadorDeRegiones;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.File;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainPresenter {
    private MainView vista;
    private Grafo<Provincia> grafo;
    private BuscadorDeRegiones buscador;

    public MainPresenter(MainView vista, Grafo<Provincia> grafo) {
        this.vista = vista;
        this.grafo = grafo;
        this.buscador = new BuscadorDeRegiones();

        this.vista.setAgregarProvinciaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProvincia();
            }
        });

        this.vista.setConectarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectarProvincias();
            }
        });

        this.vista.setGenerarRegionesListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarRegiones();
            }
        });
        
        this.vista.setCargarArchivoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDesdeArchivo();
            }
        });
    }

    private void agregarProvincia() {
        try {
            String nombre = vista.getNombreProvincia();
            Provincia prov = new Provincia(nombre);
            grafo.agregarNodo(prov);
            vista.mostrarMensaje("Provincia agregada: " + prov.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampoProvincia();
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void conectarProvincias() {
        try {
            Provincia p1 = new Provincia(vista.getPrimeraProvincia());
            Provincia p2 = new Provincia(vista.getSegundaProvincia());
            double peso = Double.parseDouble(vista.getPesoStr());

            Nodo<Provincia> nodo1 = null;
            Nodo<Provincia> nodo2 = null;

            for (Nodo<Provincia> nodo : grafo.getTodosLosNodos()) {
                if (nodo.getValor().equals(p1)) nodo1 = nodo;
                if (nodo.getValor().equals(p2)) nodo2 = nodo;
            }

            if (nodo1 == null || nodo2 == null) {
                throw new IllegalArgumentException("Ambas provincias deben estar agregadas al grafo previamente.");
            }

            grafo.agregarArista(nodo1, nodo2, peso);
            vista.mostrarMensaje("Conexión agregada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("El peso de la arista debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarRegiones() {
        try {
            int k = Integer.parseInt(vista.getCantRegionesStr());
            
            //calculamos el MST usando Kruskal
            Grafo<Provincia> mst = Kruskal.calcularMST(grafo);
            
            //buscamos las regiones dividiendo el MST
            List<Region> regiones = buscador.agrupar(mst, k);

            //formateamos el resultado para mostrarlo en el JTextArea
            StringBuilder sb = new StringBuilder();
            sb.append("Se generaron ").append(regiones.size()).append(" regiones:\n");
            for (int i = 0; i < regiones.size(); i++) {
                sb.append("Región ").append(i + 1).append(": ").append(regiones.get(i).getProvincias()).append("\n");
            }

            vista.mostrarResultado(sb.toString());
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("La cantidad de regiones debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDesdeArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccione el archivo de provincias (.txt)");

        int seleccion = fileChooser.showOpenDialog(null);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            
            try (FileInputStream fis = new FileInputStream(archivo);
            	Scanner scanner = new Scanner(fis)) {
            	int conexionesAgregadas = 0;
                
                while (scanner.hasNextLine()) {
                    String linea = scanner.nextLine().trim();
                    if (linea.isEmpty()) continue; 
                    
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        String nombreP1 = partes[0].trim();
                        String nombreP2 = partes[1].trim();
                        double peso = Double.parseDouble(partes[2].trim());

                        Provincia p1 = new Provincia(nombreP1);
                        Provincia p2 = new Provincia(nombreP2);

                        Nodo<Provincia> nodo1 = null;
                        Nodo<Provincia> nodo2 = null;

                        // Busca si los nodos ya están, sino los agrega
                        for (Nodo<Provincia> n : grafo.getTodosLosNodos()) {
                            if (n.getValor().equals(p1)) nodo1 = n;
                            if (n.getValor().equals(p2)) nodo2 = n;
                        }
                        if (nodo1 == null) nodo1 = grafo.agregarNodo(p1);
                        if (nodo2 == null) nodo2 = grafo.agregarNodo(p2);

                        grafo.agregarArista(nodo1, nodo2, peso);
                        conexionesAgregadas++;
                    }
                }
                vista.mostrarMensaje("Archivo cargado exitosamente. Se agregaron " + conexionesAgregadas + " conexiones.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                // Manejo de errores como muestra la diapositiva[cite: 19, 21]
                vista.mostrarMensaje("Error al leer el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}