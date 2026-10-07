package interfaz;

import java.awt.EventQueue;
import estructuras.Provincia;
import grafo.Grafo;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Grafo<Provincia> modeloGrafo = new Grafo<>();
                    MainView vista = new MainView();
                    MainPresenter presentador = new MainPresenter(vista, modeloGrafo);
                    vista.mostrar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}