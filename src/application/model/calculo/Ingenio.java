package application.model.calculo;

import javafx.beans.property.*;

public class Ingenio {
    private IntegerProperty idIngenio;
    private DoubleProperty latitud;
    private DoubleProperty longitud;
    private StringProperty nombre;
    private DoubleProperty arranque;

    public double getArranque() {
        return arranque.get();
    }

    public DoubleProperty arranqueProperty() {
        return arranque;
    }

    public void setArranque(double arranque) {
        this.arranque.set(arranque);
    }

    public double getTarifa() {
        return tarifa.get();
    }

    public DoubleProperty tarifaProperty() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa.set(tarifa);
    }

    private DoubleProperty tarifa;

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

    public Ingenio(Integer idIngenio, Double latitud, Double longitud, String nombre, Double arranque, Double tarifa) {
        this.idIngenio = new SimpleIntegerProperty(idIngenio);
        this.latitud = new SimpleDoubleProperty(latitud);
        this.longitud = new SimpleDoubleProperty(longitud);
        this.nombre = new SimpleStringProperty(nombre);
        this.arranque = new SimpleDoubleProperty(arranque);
        this.tarifa = new SimpleDoubleProperty(tarifa);

    }

    public Ingenio() {
        this(0,0.0,0.0,null,0.0,0.0);
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
