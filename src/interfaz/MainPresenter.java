package interfaz;

import estructuras.LimitesArgentina;
import estructuras.Provincia;
import estructuras.Region;
import grafo.Grafo;
import grafo.Nodo;
import algoritmos.Kruskal;
import algoritmos.BuscadorDeRegiones;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openstreetmap.gui.jmapviewer.*;


public class MainPresenter {

    private MainView vista;
    private Grafo<Provincia> grafo;
    private BuscadorDeRegiones buscador;

    private static final Coordinate COORDENADA_DEFAULT =
            new Coordinate(-38.4161, -63.6167);

    private static final Color[] COLORES_REGIONES = {
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.MAGENTA,
            Color.ORANGE,
            Color.CYAN,
            Color.PINK,
            Color.YELLOW
    };

    private static final Map<String, Coordinate> COORDENADAS =
            crearCoordenadas();

    public MainPresenter(MainView vista, Grafo<Provincia> grafoInicial) {
        this.vista = vista;
        this.grafo = grafoInicial;
        this.buscador = new BuscadorDeRegiones();

        configurarListeners();
    }
    
    
    private void configurarListeners() {

        vista.setAgregarProvinciaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProvincia();
            }
        });

        vista.setConectarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectarProvincias();
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
    
    
    private void agregarProvincia() {
        try {
            String nombre = vista.getNombreProvincia();
            Provincia prov = new Provincia(nombre);
            grafo.agregarNodo(prov);
            vista.mostrarMensaje("Provincia agregada: " + prov.getNombre(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.limpiarCampoProvincia();
            vista.agregarProvinciaATabla(prov.getNombre());
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void conectarProvincias() {
        try {
        	String nombreP1 = vista.getPrimeraProvincia();
            String nombreP2 = vista.getSegundaProvincia();
            
            validarProvinciasLimitrofes(nombreP1, nombreP2);
        	
            Provincia p1 = new Provincia(vista.getPrimeraProvincia());
            Provincia p2 = new Provincia(vista.getSegundaProvincia());
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
            
            actualizarMapaVisual(regiones);
            vista.mostrarResultado(sb.toString());
            
        } catch (NumberFormatException ex) {
            vista.mostrarMensaje("La cantidad de regiones debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            vista.mostrarMensaje(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarDesdeArchivo() {
    	grafo = new Grafo<Provincia>();
    	vista.limpiarTodo();
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
                  
                    if (partes.length != 3) {
                    	throw new IllegalArgumentException(
                                "Formato inválido en la línea: " + linea
                        );
                    }
                    
                    String nombreP1 = partes[0].trim();
                    String nombreP2 = partes[1].trim();
                        
                    validarProvinciasLimitrofes(nombreP1, nombreP2);
                    
                    double peso = Double.parseDouble(partes[2].trim());

                    Provincia p1 = new Provincia(nombreP1);
                    Provincia p2 = new Provincia(nombreP2);
                        
                    Nodo<Provincia> nodo1 = buscarNodoProvincia(p1);
                    Nodo<Provincia> nodo2 = buscarNodoProvincia(p2);
                        
                    if (nodo1 == null) { 
                    nodo1 = grafo.agregarNodo(p1);
                    vista.agregarProvinciaATabla(p1.getNombre());
                    }
                    
                    if (nodo2 == null) {
                    nodo2 = grafo.agregarNodo(p2);
                    vista.agregarProvinciaATabla(p2.getNombre());
                    }
                    
                    grafo.agregarArista(nodo1, nodo2, peso);
                    vista.agregarConexionATabla(p1.getNombre(), p2.getNombre(), peso);
                    conexionesAgregadas++;
                }
                
                actualizarMapaVisual(null);
                vista.mostrarMensaje("Archivo cargado exitosamente. Se agregaron " + conexionesAgregadas + " conexiones.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception ex) {
            	grafo = new Grafo<Provincia>();
                vista.limpiarTodo();
                vista.mostrarMensaje("Error al leer el archivo: " + ex.getMessage() + "\nSe ha cancelado la carga.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


	private void validarProvinciasLimitrofes(String nombreP1, String nombreP2) {
		if (!LimitesArgentina.sonLimitrofes(nombreP1, nombreP2)) {
		throw new IllegalArgumentException(  "Las provincias " + nombreP1  + " y " + nombreP2 + " no son limítrofes.");
		}
	}
    
    
    private void actualizarMapaVisual(List<Region> regiones) {
        JMapViewer mapa = vista.getMapa();
        limpiarMapa(mapa);

        if (regiones == null || regiones.isEmpty()) {
            dibujarNodosIniciales(mapa);
        } else {
            dibujarNodosPorRegion(regiones, mapa);
        }

        dibujarAristas(regiones, mapa);
    }

	private void dibujarNodosIniciales(JMapViewer mapa) {

	    for (Nodo<Provincia> nodo : grafo.getTodosLosNodos()) {
	        Provincia provincia = nodo.getValor();

	        Coordinate coordenada = obtenerCoordenada(provincia);

	        mapa.addMapMarker(
	                new MapMarkerDot(
	                        provincia.getNombre(),
	                        coordenada
	                )
	        );
	    } 
	}
	
	private void dibujarNodosPorRegion(List<Region> regiones, JMapViewer mapa) {
	    for (int i = 0; i < regiones.size(); i++) {
	        Region region = regiones.get(i);
	        Color color = COLORES_REGIONES[i % COLORES_REGIONES.length];

	        for (Provincia provincia : region.getProvincias()) {
	            MapMarkerDot marcador = new MapMarkerDot(
	                    provincia.getNombre(),
	                    obtenerCoordenada(provincia)
	            );

	            marcador.setBackColor(color);
	            mapa.addMapMarker(marcador);
	        }
	    }
	}
	
	
	private void dibujarAristas(
	        List<Region> regiones,
	        JMapViewer mapa) {

	    for (grafo.Arista<Provincia> arista : grafo.getTodasLasAristas()) {

	        Provincia origen = arista.getOrigen().getValor();
	        Provincia destino = arista.getDestino().getValor();

	        if (!debeDibujarseArista(origen, destino, regiones)) {
	            continue;
	        }

	        Coordinate coordenadaOrigen = obtenerCoordenada(origen);
	        Coordinate coordenadaDestino = obtenerCoordenada(destino);

	        mapa.addMapPolygon(
	                new MapPolygonImpl(
	                        Arrays.asList(
	                                coordenadaOrigen,
	                                coordenadaDestino,
	                                coordenadaOrigen
	                        )
	                )
	        );
	    }
	}
	
	
	private boolean debeDibujarseArista(
	        Provincia origen,
	        Provincia destino,
	        List<Region> regiones) {

	    if (regiones == null || regiones.isEmpty()) {
	        return true;
	    }

	    for (Region region : regiones) {
	        if (region.getProvincias().contains(origen)
	                && region.getProvincias().contains(destino)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	
	private void limpiarMapa(JMapViewer mapa) {
	    mapa.removeAllMapMarkers();
	    mapa.removeAllMapPolygons();
	}
	
	private Coordinate obtenerCoordenada(Provincia provincia) {
	    String nombre = provincia.getNombre().trim().toLowerCase();

	    return COORDENADAS.getOrDefault(nombre,COORDENADA_DEFAULT);
	}
	
	private static Map<String, Coordinate> crearCoordenadas() {

	    Map<String, Coordinate> coordenadas = new HashMap<>();

	    coordenadas.put("buenos aires",
	            new Coordinate(-34.6037, -58.3816));

	    coordenadas.put("cordoba",
	            new Coordinate(-31.4201, -64.1888));

	    coordenadas.put("santa fe",
	            new Coordinate(-31.6333, -60.7000));

	    coordenadas.put("mendoza",
	            new Coordinate(-32.8908, -68.8272));

	    coordenadas.put("san juan",
	            new Coordinate(-31.5375, -68.5364));

	    coordenadas.put("la pampa",
	            new Coordinate(-36.6167, -64.2833));

	    coordenadas.put("rio negro",
	            new Coordinate(-40.8135, -63.0000));

	    coordenadas.put("neuquen",
	            new Coordinate(-38.9516, -68.0591));

	    coordenadas.put("chubut",
	            new Coordinate(-43.3002, -65.1023));

	    coordenadas.put("santa cruz",
	            new Coordinate(-51.6226, -69.2181));

	    coordenadas.put("tierra del fuego",
	            new Coordinate(-54.8019, -68.3030));

	    coordenadas.put("entre rios",
	            new Coordinate(-31.7331, -60.5299));

	    coordenadas.put("corrientes",
	            new Coordinate(-27.4692, -58.8306));

	    coordenadas.put("misiones",
	            new Coordinate(-27.3671, -55.8961));

	    coordenadas.put("chaco",
	            new Coordinate(-27.4514, -58.9867));

	    coordenadas.put("formosa",
	            new Coordinate(-26.1775, -58.1781));

	    coordenadas.put("santiago del estero",
	            new Coordinate(-27.7951, -64.2615));

	    coordenadas.put("tucuman",
	            new Coordinate(-26.8083, -65.2176));

	    coordenadas.put("salta",
	            new Coordinate(-24.7821, -65.4232));

	    coordenadas.put("jujuy",
	            new Coordinate(-24.1946, -65.2971));

	    coordenadas.put("catamarca",
	            new Coordinate(-28.4696, -65.7852));

	    coordenadas.put("la rioja",
	            new Coordinate(-29.4135, -66.8507));

	    coordenadas.put("san luis",
	            new Coordinate(-33.2950, -66.3356));

	    return coordenadas;
	}
	
	private Nodo<Provincia> buscarNodoProvincia(Provincia provincia) {

	    for (Nodo<Provincia> nodo : grafo.getTodosLosNodos()) {

	        if (nodo.getValor().equals(provincia)) {
	            return nodo;
	        }
	    }

	    return null;
	}
	
}