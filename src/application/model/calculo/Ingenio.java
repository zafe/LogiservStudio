package application.model.calculo;

import javafx.beans.property.*;

public class Ingenio {
    private IntegerProperty idIngenio;
    private DoubleProperty latitud;
    private DoubleProperty longitud;
    private StringProperty nombre;

    public int getIdIngenio() {
        return idIngenio.get();
    }

    public IntegerProperty idIngenioProperty() {
        return idIngenio;
    }

    public void setIdIngenio(int idIngenio) {
        this.idIngenio.set(idIngenio);
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

    public Ingenio(Integer idIngenio, Double latitud, Double longitud, String nombre) {
        this.idIngenio = new SimpleIntegerProperty(idIngenio);
        this.latitud = new SimpleDoubleProperty(latitud);
        this.longitud = new SimpleDoubleProperty(longitud);
        this.nombre = new SimpleStringProperty(nombre);
    }

    public Ingenio() {
        this(null,null,null,null);
    }

}
