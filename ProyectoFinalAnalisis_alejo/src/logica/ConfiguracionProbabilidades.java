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

    private double rock;
    private double pop;
    private double bachata;
    private double reggaeton;
    private double balada;
    private double salsa;
    private double vallenato;
    private double ranchera;
    private double merengue;

    public ConfiguracionProbabilidades(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
                
        leer_archivo();
    }

    /**
     * metodo que permite cargar la información del objetvo desde el fichero
     * @return boolean
     */
    public boolean leer_archivo() {

        Properties propiedades = new Properties();
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

        // obtenemos las propiedades y las imprimimos            
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
     * @return 
     */
    public boolean guardar() {

        Properties propiedades = new Properties();
        OutputStream salida = null;

        try {
            salida = new FileOutputStream(ruta_archivo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConfiguracionProbabilidades.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        // asignamos los valores a las propiedades
        propiedades.setProperty("rock", Double.toString(rock));
        propiedades.setProperty("pop", Double.toString(pop));
        propiedades.setProperty("bachata", Double.toString(bachata));
        propiedades.setProperty("reggaeton", Double.toString(reggaeton));
        propiedades.setProperty("balada", Double.toString(balada));
        propiedades.setProperty("salsa", Double.toString(salsa));
        propiedades.setProperty("vallenato", Double.toString(vallenato));
        propiedades.setProperty("ranchera", Double.toString(ranchera));
        propiedades.setProperty("merengue", Double.toString(merengue));

        try {
            // guardamos el archivo de propiedades en la carpeta de aplicación
            propiedades.store(salida, null);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracionProbabilidades.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    
    
    public String getRuta_archivo() {
        return ruta_archivo;
    }

    public void setRuta_archivo(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
    }

    public double getRock() {
        return rock;
    }

    public void setRock(double rock) {
        this.rock = rock;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public double getBachata() {
        return bachata;
    }

    public void setBachata(double bachata) {
        this.bachata = bachata;
    }

    public double getReggaeton() {
        return reggaeton;
    }

    public void setReggaeton(double reggaeton) {
        this.reggaeton = reggaeton;
    }

    public double getBalada() {
        return balada;
    }

    public void setBalada(double balada) {
        this.balada = balada;
    }

    public double getSalsa() {
        return salsa;
    }

    public void setSalsa(double salsa) {
        this.salsa = salsa;
    }

    public double getVallenato() {
        return vallenato;
    }

    public void setVallenato(double vallenato) {
        this.vallenato = vallenato;
    }

    public double getRanchera() {
        return ranchera;
    }

    public void setRanchera(double ranchera) {
        this.ranchera = ranchera;
    }

    public double getMerengue() {
        return merengue;
    }

    public void setMerengue(double merengue) {
        this.merengue = merengue;
    }
    
    
    

}
