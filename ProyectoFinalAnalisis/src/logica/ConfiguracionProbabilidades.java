/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class ConfiguracionProbabilidades {

    private String ruta_archivo;
    private Properties propiedades;

    private double rock;
    private double pop;
    private double bachata;
    private double reggaeton;
    private double balada;
    private double salsa;
    private double vallenato;
    private double ranchera;
    private double merengue;

    /**
     * metodo constructor que recibe la ruta al archivo 
     * @param ruta_archivo 
     */
    public ConfiguracionProbabilidades(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    /**
     * metodo que permite cargar la información del objetvo desde el fichero
     *
     * @return boolean
     */
    public boolean leer_archivo() {

        propiedades = new Properties();
        InputStream entrada = null;

        try {
            // cargamos el archivo de propiedades
            entrada = new FileInputStream(ruta_archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfiguracionProbabilidades.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            // leemos el archivo propiedades
            propiedades.load(entrada);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracionProbabilidades.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        // Guardamos los valores de las propiedades localmente           
        rock = Double.parseDouble(propiedades.getProperty("rock"));
        pop = Double.parseDouble(propiedades.getProperty("pop"));
        bachata = Double.parseDouble(propiedades.getProperty("bachata"));
        reggaeton = Double.parseDouble(propiedades.getProperty("reggaeton"));
        balada = Double.parseDouble(propiedades.getProperty("balada"));
        salsa = Double.parseDouble(propiedades.getProperty("salsa"));
        vallenato = Double.parseDouble(propiedades.getProperty("vallenato"));
        ranchera = Double.parseDouble(propiedades.getProperty("ranchera"));
        merengue = Double.parseDouble(propiedades.getProperty("merengue"));

        return true;
    }

    /**
     * metodo que permite guardar la información del objetvo en el fichero
     *
     * @param propiedades
     * @return
     */
    public boolean guardar(Properties propiedades) {

        OutputStream salida = null;

        try {
            salida = new FileOutputStream(ruta_archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfiguracionProbabilidades.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            // guardamos el archivo de propiedades en la carpeta de aplicación
            propiedades.store(salida, null);

        } catch (IOException ex) {
            Logger.getLogger(ConfiguracionProbabilidades.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        // Guardamos los valores de las propiedades localmente           
        rock = Double.parseDouble(propiedades.getProperty("rock"));
        pop = Double.parseDouble(propiedades.getProperty("pop"));
        bachata = Double.parseDouble(propiedades.getProperty("bachata"));
        reggaeton = Double.parseDouble(propiedades.getProperty("reggaeton"));
        balada = Double.parseDouble(propiedades.getProperty("balada"));
        salsa = Double.parseDouble(propiedades.getProperty("salsa"));
        vallenato = Double.parseDouble(propiedades.getProperty("vallenato"));
        ranchera = Double.parseDouble(propiedades.getProperty("ranchera"));
        merengue = Double.parseDouble(propiedades.getProperty("merengue"));

        return true;
    }

    /**
     * metodo que permite obtener la ruta del archivo de configuracion
     * @return 
     */
    public String getRuta_archivo() {
        return ruta_archivo;
    }

    /**
     * metodo que permite establecer la ruta al archivo de configuracion
     * @param ruta_archivo 
     */
    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    /**
     * metodo que permite obtener las propiedades
     * @return 
     */
    public Properties getPropiedades() {
        return propiedades;
    }

    /**
     * metodo que permite obtener el valor de rock
     * @return 
     */
    public double getRock() {
        return rock;
    }

    /**
     * metodo que permite establecer el valor de rock
     * @param rock 
     */
    public void setRock(double rock) {
        this.rock = rock;
    }

    /**
     * metodo que permite obtener el valor de Pop
     * @return 
     */
    public double getPop() {
        return pop;
    }

    /**
     * metodo que permite establecer el valor de pop
     * @param pop 
     */
    public void setPop(double pop) {
        this.pop = pop;
    }

    /**
     * metodo que permite obtener el valor de bachata
     * @return 
     */
    public double getBachata() {
        return bachata;
    }

    /**
     * metodo que permite establecer el valor de bachata
     * @param bachata 
     */
    public void setBachata(double bachata) {
        this.bachata = bachata;
    }

    /**
     * metodo que permite obtener el valor de reggeaton
     * @return 
     */
    public double getReggaeton() {
        return reggaeton;
    }

    /**
     * metodo que permite establecer el valor de regueaton
     * @param reggaeton 
     */
    public void setReggaeton(double reggaeton) {
        this.reggaeton = reggaeton;
    }

    /**
     * metodo que permite obtener el valor de balada
     * @return 
     */
    public double getBalada() {
        return balada;
    }

    /**
     * metodo que pemite establecer el valor de balada
     * @param balada
     */
    public void setBalada(double balada) {
        this.balada = balada;
    }

    /**
     * metodo que permite obtener el valor de Salsa
     * @return 
     */
    public double getSalsa() {
        return salsa;
    }

    /**
     * metodo que permite establecer el valor de salsa
     * @param salsa 
     */
    public void setSalsa(double salsa) {
        this.salsa = salsa;
    }

    /**
     * metodo que permite obtener el valor de vallenato
     * @return 
     */
    public double getVallenato() {
        return vallenato;
    }

    /**
     * metodo que permite establecer el valor de vallenato
     * @param vallenato 
     */
    public void setVallenato(double vallenato) {
        this.vallenato = vallenato;
    }

    /**
     * metodo que permite obtener el valor de ranchera
     * @return 
     */
    public double getRanchera() {
        return ranchera;
    }

    /**
     * metodo que permite establecer el valor de ranchera
     * @param ranchera
     */ 
    public void setRanchera(double ranchera) {
        this.ranchera = ranchera;
    }

    /**
     * metodo que permite obtener el valor del merengue
     * @return 
     */
    public double getMerengue() {
        return merengue;
    }

    /** 
     * metodo que permite establecer el valor de merengue
     * @param merengue 
     */
    public void setMerengue(double merengue) {
        this.merengue = merengue;
    }

}
