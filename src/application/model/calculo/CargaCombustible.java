package application.model.calculo;

import application.model.compra.FacturaCompra;
import application.model.info.Empleado;
import javafx.beans.property.*;

public class CargaCombustible {

    private IntegerProperty idCargaCombustible;
    private StringProperty fechaCarga;
    private StringProperty horaCarga;
    private DoubleProperty cantidadLitros;
    private Empleado conductor;
    private Camion camion;
    private FacturaCompra facturaCompra;


    public CargaCombustible(Integer idCargaCombustible, String fechaCarga, String horaCarga,
                            Double cantidadLitros, Empleado conductor, Camion camion, FacturaCompra facturaCompra) {
        this.idCargaCombustible = new SimpleIntegerProperty(idCargaCombustible);
        this.fechaCarga = new SimpleStringProperty(fechaCarga);
        this.horaCarga = new SimpleStringProperty(horaCarga);
        this.cantidadLitros = new SimpleDoubleProperty(cantidadLitros);
        this.conductor = conductor;
        this.camion = camion;
        this.facturaCompra = facturaCompra;
    }

    public CargaCombustible(){
        this(0, null,null,0.0,null,null,null);
    }

    public int getIdCargaCombustible() {
        return idCargaCombustible.get();
    }

    public IntegerProperty idCargaCombustibleProperty() {
        return idCargaCombustible;
    }

    public void setIdCargaCombustible(int idCargaCombustible) {
        this.idCargaCombustible.set(idCargaCombustible);
    }

    public String getFechaCarga() {
        return fechaCarga.get();
    }

    public StringProperty fechaCargaProperty() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga.set(fechaCarga);
    }

    public String getHoraCarga() {
        return horaCarga.get();
    }

    public StringProperty horaCargaProperty() {
        return horaCarga;
    }

    public void setHoraCarga(String horaCarga) {
        this.horaCarga.set(horaCarga);
    }

    public double getCantidadLitros() {
        return cantidadLitros.get();
    }

    public DoubleProperty cantidadLitrosProperty() {
        return cantidadLitros;
    }

    public void setCantidadLitros(double cantidadLitros) {
        this.cantidadLitros.set(cantidadLitros);
    }

    public Empleado getConductor() {
        return conductor;
    }

    public void setConductor(Empleado conductor) {
        this.conductor = conductor;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public FacturaCompra getFacturaCompra() {
        return facturaCompra;
    }

    public void setFacturaCompra(FacturaCompra facturaCompra) {
        this.facturaCompra = facturaCompra;
    }
}