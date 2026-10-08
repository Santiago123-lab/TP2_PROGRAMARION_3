package interfaz;

import estructuras.Provincia;
import estructuras.Region;
import grafo.Grafo;
import grafo.Nodo;
import algoritmos.Kruskal;
import algoritmos.BuscadorDeRegiones;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashSet;
//import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.*;

//import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import org.openstreetmap.gui.jmapviewer.*;


public class MainPresenter {

    private MainView vista;
    private Grafo<Provincia> grafo;
    private BuscadorDeRegiones buscador;

//    private static final Color[] COLORES_REGIONES = {
//            Color.RED,
//            Color.BLUE,
//            Color.GREEN,
//            Color.MAGENTA,
//            Color.ORANGE,
//            Color.CYAN,
//            Color.PINK,
//            Color.YELLOW
//    };
//    
    public MainPresenter(MainView vista, Grafo<Provincia> grafoInicial) {
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
            Provincia prov = new Provincia(nombre);
            grafo.agregarNodo(prov);
            vista.mostrarMensaje("Provincia agregada: " + prov.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampoVertice();
            vista.agregarVerticeATabla(prov.getNombre());
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void conectarVertices() {
        try {
            Provincia p1 = new Provincia(vista.getPrimerVertice());
            Provincia p2 = new Provincia(vista.getSegundoVertice());
            double peso = Double.parseDouble(vista.getPesoStr());

            Nodo<Provincia> nodo1 = buscarNodoProvincia(p1);
            Nodo<Provincia> nodo2 = buscarNodoProvincia(p2);

            if (nodo1 == null || nodo2 == null) {
                throw new IllegalArgumentException("Ambas provincias deben estar agregadas al grafo previamente.");
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
                throw new IllegalStateException("El grafo está vacío. Cargue provincias y conexiones primero.");
            }
            
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
        fileChooser.setDialogTitle("Seleccione el archivo de provincias (.txt)");

        int seleccion = fileChooser.showOpenDialog(null);
        
        if (seleccion != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        File archivo = fileChooser.getSelectedFile();
            
        try (	
        	FileInputStream fis = new FileInputStream(archivo);
        	Scanner scanner = new Scanner(fis);
        ) {
        	Grafo<Provincia> nuevoGrafo = new Grafo<>();
        	
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

                Provincia v1 = new Provincia(nombreV1);
                Provincia v2 = new Provincia(nombreV2);

                Nodo<Provincia> nodo1 = buscarNodoProvincia(nuevoGrafo, v1);

                Nodo<Provincia> nodo2 = buscarNodoProvincia(nuevoGrafo, v2);

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
 
//    private void actualizarMapaVisual(List<Region> regiones) {
//        JMapViewer mapa = vista.getMapa();
//        limpiarMapa(mapa);
//
//        if (regiones == null || regiones.isEmpty()) {
//            dibujarNodosIniciales(mapa);
//        } else {
//            dibujarNodosPorRegion(regiones, mapa);
//        }
//
//        dibujarAristas(regiones, mapa);
//    }
//
//	private void dibujarNodosIniciales(JMapViewer mapa) {
//
//	    for (Nodo<Provincia> nodo : grafo.getTodosLosNodos()) {
//	        Provincia provincia = nodo.getValor();
//
//	        Coordinate coordenada = obtenerCoordenada(provincia);
//
//	        mapa.addMapMarker(
//	                new MapMarkerDot(
//	                        provincia.getNombre(),
//	                        coordenada
//	                )
//	        );
//	    } 
//	}
//	
//	private void dibujarNodosPorRegion(List<Region> regiones, JMapViewer mapa) {
//	    for (int i = 0; i < regiones.size(); i++) {
//	        Region region = regiones.get(i);
//	        Color color = COLORES_REGIONES[i % COLORES_REGIONES.length];
//
//	        for (Provincia provincia : region.getProvincias()) {
//	            MapMarkerDot marcador = new MapMarkerDot(
//	                    provincia.getNombre(),
//	                    obtenerCoordenada(provincia)
//	            );
//
//	            marcador.setBackColor(color);
//	            mapa.addMapMarker(marcador);
//	        }
//	    }
//	}
//	
//	
//	private void dibujarAristas(
//	        List<Region> regiones,
//	        JMapViewer mapa) {
//
//	    for (grafo.Arista<Provincia> arista : grafo.getTodasLasAristas()) {
//
//	        Provincia origen = arista.getOrigen().getValor();
//	        Provincia destino = arista.getDestino().getValor();
//
//	        if (!debeDibujarseArista(origen, destino, regiones)) {
//	            continue;
//	        }
//
//	        Coordinate coordenadaOrigen = obtenerCoordenada(origen);
//	        Coordinate coordenadaDestino = obtenerCoordenada(destino);
//
//	        mapa.addMapPolygon(
//	                new MapPolygonImpl(
//	                        Arrays.asList(
//	                                coordenadaOrigen,
//	                                coordenadaDestino,
//	                                coordenadaOrigen
//	                        )
//	                )
//	        );
//	    }
//	}
	
	
//	private boolean debeDibujarseArista(
//	        Provincia origen,
//	        Provincia destino,
//	        List<Region> regiones) {
//
//	    if (regiones == null || regiones.isEmpty()) {
//	        return true;
//	    }
//
//	    for (Region region : regiones) {
//	        if (region.getProvincias().contains(origen)
//	                && region.getProvincias().contains(destino)) {
//	            return true;
//	        }
//	    }
//
//	    return false;
//	}
//	
//	
//	private void limpiarMapa(JMapViewer mapa) {
//	    mapa.removeAllMapMarkers();
//	    mapa.removeAllMapPolygons();
//	}
//	
	private Nodo<Provincia> buscarNodoProvincia(Provincia provincia) {
	    for (Nodo<Provincia> nodo : grafo.getTodosLosNodos()) {
	        if (nodo.getValor().equals(provincia)) {
	            return nodo;
	        }
	    }
	    return null;
	}
	
	private Nodo<Provincia> buscarNodoProvincia(Grafo<Provincia> grafo, Provincia provincia) {
	    for (Nodo<Provincia> nodo : grafo.getTodosLosNodos()) {

	        if (nodo.getValor().equals(provincia)) {
	            return nodo;
	        }
	    }

	    return null;
	}
	
}