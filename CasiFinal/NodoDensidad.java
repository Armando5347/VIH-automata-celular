/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatacelular;

public class NodoDensidad {
    public double densidadCelulasSanas;
    public double densidadCelulasInfectadas;
    public double densidadCelulasMuertas;
    public int tiempo;
    public NodoDensidad siguienteDensidad;

    public NodoDensidad() {
        densidadCelulasSanas = 0;
        densidadCelulasInfectadas = 0;
        densidadCelulasMuertas = 0;
        tiempo = 0;
        siguienteDensidad = null;
    }

    public NodoDensidad(double densidadSanas, double densidadInfectadas, double densidadMuertas) {
        this.densidadCelulasSanas = densidadSanas;
        this.densidadCelulasInfectadas = densidadInfectadas;
        this.densidadCelulasMuertas = densidadMuertas;
        this.siguienteDensidad = null;
    }

    public double getDensidadCelulasSanas() {
        return densidadCelulasSanas;
    }

    public double getDensidadCelulasInfectadas() {
        return densidadCelulasInfectadas;
    }

    public double getDensidadCelulasMuertas() {
        return densidadCelulasMuertas;
    }
    
    public int getTiempo(){
        return tiempo;
    }

    public void setSiguiente(NodoDensidad siguiente) {
        this.siguienteDensidad = siguiente;
    }

    public NodoDensidad getSiguiente() {
        return siguienteDensidad;
    }
}