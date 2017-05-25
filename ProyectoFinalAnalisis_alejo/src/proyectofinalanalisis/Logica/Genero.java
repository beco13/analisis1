/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalanalisis.Logica;

/**
 *
 * @author alejo
 */
public class Genero implements Runnable{

    private String nombre;
    private String ruta_carpeta;
    private String[][] frecuencia_acumulada;
    private String[][] matriz_transicion;
    private ConfiguracionProbabilidades configuracion ;

    public Genero(String ruta_carpeta) {
        this.ruta_carpeta = ruta_carpeta;

        frecuencia_acumulada = Utilidades.getMoldeTabla();
        matriz_transicion = Utilidades.getMoldeTabla();

    }

    @Override
    public void run() {
        
        
        
        
    }

}
