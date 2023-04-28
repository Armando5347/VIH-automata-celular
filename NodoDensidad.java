public class NodoDensidad {
    public double densidadCelulasSanas;
    public double densidadCelulasInfectadas;
    public double densidadCelulasMuertas;
    public NodoDensidad siguienteDensidad;

    public NodoDensidad() {
        densidadCelulasSanas = 0;
        densidadCelulasInfectadas = 0;
        densidadCelulasMuertas = 0;
        siguienteDensidad = null;
    }
}
