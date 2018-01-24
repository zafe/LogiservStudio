package application.model.calculo;

import javafx.beans.property.*;

public class Finca {
    private IntegerProperty idFinca;
    private DoubleProperty latitud;
    private DoubleProperty longitud;
    private StringProperty nombre;

    public int getIdFinca() {
        return idFinca.get();
    }

    public IntegerProperty idFincaProperty() {
        return idFinca;
    }

    public void setIdFinca(int idFinca) {
        this.idFinca.set(idFinca);
    }

    public double getLatitud() {
        return latitud.get();
    }

    public DoubleProperty latitudProperty() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud.set(latitud);
    }

    public double getLongitud() {
        return longitud.get();
    }

    public DoubleProperty longitudProperty() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud.set(longitud);
    }

    public String getNombre() {
        return nombre.get();
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public Finca(Integer idFinca, Double latitud, Double longitud, String nombre) {
        this.idFinca = new SimpleIntegerProperty(idFinca);
        this.latitud = new SimpleDoubleProperty(latitud);
        this.longitud = new SimpleDoubleProperty(longitud);
        this.nombre = new SimpleStringProperty(nombre);
    }

    public Finca() {
        this(0,0.0,0.0,null);
    }

    @Override
    public String toString() {
        return  this.getNombre();
    }
}
