/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author xJoni
 */
public class Compositor implements Runnable {

    private boolean ejecutando;
    private Thread hiloPrincipal;
    private Thread[] hilosGenero;
    private Properties propiedades;
    private String[] nombreArchivos;
    private int cantidadGeneros;
    private File[] archivosTexto;
    private int progreso;
    private int numHilo = 0;
    private ProgressBar barraProgreso;

    //Nuevo
    private ArrayList<ArrayList> bachata;
    private ArrayList<ArrayList> balada;
    private ArrayList<ArrayList> merengue;
    private ArrayList<ArrayList> pop;
    private ArrayList<ArrayList> ranchera;
    private ArrayList<ArrayList> reggaeton;
    private ArrayList<ArrayList> rock;
    private ArrayList<ArrayList> salsa;
    private ArrayList<ArrayList> vallenato;

    private int numeroGenero;

    private Thread hiloBachata;
    private Thread hiloBalada;
    private Thread hiloMerengue;
    private Thread hiloPop;
    private Thread hiloRanchera;
    private Thread hiloReggaeton;
    private Thread hiloRock;
    private Thread hiloSalsa;
    private Thread hiloVallenato;

    //Punto 7
    private char[] caracteres;
    private double[] probabilidades;

    //Tiempo
    private String tiempo;

    public Compositor() {
        ejecutando = false;
        propiedades = new Properties();
        bachata = new ArrayList<>();
        balada = new ArrayList<>();
        merengue = new ArrayList<>();
        pop = new ArrayList<>();
        ranchera = new ArrayList<>();
        reggaeton = new ArrayList<>();
        rock = new ArrayList<>();
        salsa = new ArrayList<>();
        vallenato = new ArrayList<>();
    }

    public void inicializarProgressBar(JLabel lblGenerando, JProgressBar progressbar) {
        barraProgreso = new ProgressBar(lblGenerando, progressbar);
        barraProgreso.execute();
    }

    public Properties cargarConfiguraciones(String nombre) {

        InputStream entrada = null;

        try {
            entrada = getClass().getResourceAsStream("/archivos/config/" + nombre + ".properties");
            propiedades.load(entrada);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return propiedades;
    }

    public boolean guardarConfiguraciones(String nombre, Properties propiedades) {

        FileOutputStream salida = null;

        try {
            salida = new FileOutputStream("src/archivos/config/" + nombre + ".properties");
            propiedades.store(salida, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (salida != null) {
                try {
                    salida.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    private void generarVectores(char caracter, int tamano) {
        caracteres = new char[tamano + 1];
        caracteres[0] = caracter;
        probabilidades = new double[tamano];
        double suma = 0;
        double numAleatorio;
        /*for (int i = 0; i < tamano; i++) {
            if (i != tamano - 1) {
                do {
                    numAleatorio = generarNumeroAleatorio();
                } while ((suma + numAleatorio) >= 1);

                suma += numAleatorio;
                probabilidades[i] = numAleatorio;
            } else {
                double aux = 1 - suma;
                probabilidades[i] = aux;
                System.out.println("Entre?");
            }
        }*/

        for (int i = 0; i < tamano; i++) {
            numAleatorio = Math.random() * 1 + 0;
            probabilidades[i] = numAleatorio;
        }
    }

    /*private double generarNumeroAleatorio() {
        return Math.random() * 0.5 + 0.1;
    }*/
    public boolean generarCancion(char caracter, String genero, int tamano) {
        if (!validarCaracter(caracter)) {
            System.err.println("No es un caracter válido.");
            return false;
        }

        long TInicio, TFin, tiempo; //Variables para determinar el tiempo de ejecución
        TInicio = System.currentTimeMillis();

        generarVectores(caracter, tamano);

        for (int i = 0; i < caracteres.length; i++) {
            System.out.print(caracteres[i] + " ");
        }
        System.out.println("");
        System.out.println("");
        for (int i = 0; i < probabilidades.length; i++) {
            System.out.print(probabilidades[i] + " | ");
        }
        System.out.println("\nListo");
        leerNombreCanciones();
        String[] nombresCanciones = nombreArchivos[numeroGenero].split(".txt");
        leerCanciones(nombresCanciones, bachata);
        numeroGenero++;
        nombresCanciones = nombreArchivos[numeroGenero].split(".txt");
        leerCanciones(nombresCanciones, balada);
        numeroGenero++;

        for (int i = 0; i < bachata.size(); i++) {
            for (int j = 0; j < bachata.get(i).size(); j++) {
                System.out.println(bachata.get(i).get(j));
            }
            System.out.println("-------------------------------------------------------------------------");
        }
        for (int i = 0; i < balada.size(); i++) {
            for (int j = 0; j < balada.get(i).size(); j++) {
                System.out.println(balada.get(i).get(j));
            }
            System.out.println("-------------------------------------------------------------------------");
        }

        TFin = System.currentTimeMillis();
        tiempo = TFin - TInicio;
        tiempo = tiempo / 1000;
        System.out.println("tiempo: " + calcularTiempo(tiempo, 0));        
        return true;
    }

    /**
     * 
     * @param tiempo el tiempo ocupado durante la generación de la canción en segundos
     * @param minutos variable para ir contando los minutos que se tardo en ejecucion a partir de los segundos registrados
     * @return una cadena con el tiempo en minutos y segundos 00:00 minutos
     */
    private String calcularTiempo(long tiempo, int minutos) {
        if (tiempo < 60) {
            if(tiempo < 10){
                return minutos + ":0" + tiempo + " minutos";
            }else{
                return minutos + ":" + tiempo + " minutos";
            }
        } else {
            return calcularTiempo(tiempo - 60, minutos + 1);
        }
    }

    private void iniciarHilos() {
        hiloBachata = new Thread(this);
        hiloBachata.start();
        hiloBalada = new Thread(this);
        hiloBalada.start();

        hiloMerengue = new Thread(this);
        hiloMerengue.start();

        hiloPop = new Thread(this);
        hiloPop.start();

        hiloRanchera = new Thread(this);
        hiloRanchera.start();

        hiloReggaeton = new Thread(this);
        hiloReggaeton.start();

        hiloRock = new Thread(this);
        hiloSalsa = new Thread(this);
        hiloVallenato = new Thread(this);
    }

    private boolean validarCaracter(char caracter) {
        return caracter >= 'a' && caracter <= 'z' || caracter == 'ñ';
    }

    private void leerNombreCanciones() {
        File carpeta = new File("src/archivos/canciones/");

        if (carpeta.exists()) {
            File[] carpetas = carpeta.listFiles();
            cantidadGeneros = carpetas.length;

            nombreArchivos = new String[cantidadGeneros];

            for (int i = 0; i < cantidadGeneros; i++) {
                archivosTexto = carpetas[i].listFiles();
                nombreArchivos[i] = carpetas[i].getPath() + ".txt";
                //modificar ciclo                
                for (int j = 0; j < archivosTexto.length; j++) {
                    nombreArchivos[i] += archivosTexto[j].getName();
                }
                //System.out.println(nombreArchivos[i]);
            }
        }

        /*hilosGenero = new Thread[cantidadGeneros];

        for (int i = 0; i < cantidadGeneros; i++) {
            hilosGenero[i] = new Thread(this);
            hilosGenero[i].start();
        }*/
    }

    private void leerCancion(String path, ArrayList<ArrayList> genero) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String cadena = "";

            ArrayList<String> cancion = new ArrayList<>();
            while ((cadena = bufferedReader.readLine()) != null) {
                cancion.add(cadena);
            }
            genero.add(cancion);
        } catch (IOException e) {
            System.err.println("Error al buscar el archivo, el sistema no encuentra la ruta de acceso especificado");
        }
    }

    private void leerCanciones(String[] nombresCanciones, ArrayList<ArrayList> genero) {
        //Cada hilo deberia arrancar aqui
        //String[] nombresCanciones = nombreArchivos[numeroGenero].split(".txt");        
        for (int i = 1; i < nombresCanciones.length; i++) {
            //System.out.println("prueba" + nombresCanciones[i] + "prueba");
            String path = nombresCanciones[0] + "/" + nombresCanciones[i] + ".txt";
            leerCancion(path, genero);
        }

        // numHilo++;
    }

    public synchronized void start() {
        if (ejecutando) {
            return;
        }
        ejecutando = true;
        hiloPrincipal = new Thread(this);
        hiloPrincipal.start();
    }

    public synchronized void stop() {
        if (!ejecutando) {
            return;
        }
        progreso += 10;
        barraProgreso.setProgreso(progreso);
        ejecutando = false;
        try {
            hiloPrincipal.join();
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void run() {

        /*System.out.println("Hilo numero " + numeroGenero + " me llamo " );
        String[] nombresCanciones = nombreArchivos[numeroGenero].split(".txt");
        if (numeroGenero == 0) {
            leerCanciones(nombresCanciones, bachata);
        } else if (numeroGenero == 1) {
            leerCanciones(nombresCanciones, balada);
        } else if (numeroGenero == 2) {
            leerCanciones(nombresCanciones, merengue);
        } else if (numeroGenero == 3) {
            leerCanciones(nombresCanciones, pop);
        } else if (numeroGenero == 4) {
            leerCanciones(nombresCanciones, ranchera);
        } else if (numeroGenero == 5) {
            leerCanciones(nombresCanciones, reggaeton);
        } else if (numeroGenero == 6) {
            leerCanciones(nombresCanciones, rock);
        } else if (numeroGenero == 7) {
            leerCanciones(nombresCanciones, salsa);
        } else if (numeroGenero == 8) {
            leerCanciones(nombresCanciones, vallenato);
        }*/
 /*int cont = 1;

        while (cont <= 5) {
            leerCancion();
            //System.out.println("Corriendo hilo " + hilosGenero[numHilo].getName() + " numero" + cont);
            cont++;
        }

        stop();*/
    }
}
