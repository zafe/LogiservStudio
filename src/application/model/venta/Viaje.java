package application.model.venta;

import application.model.calculo.Camion;
import application.model.calculo.Finca;
import application.model.calculo.Ingenio;
import application.model.info.Empleado;
import javafx.beans.property.*;

import java.math.BigDecimal;

public class Viaje {

    private IntegerProperty idRemito;
    private StringProperty fecha;
    private StringProperty horaEntrada;
    private DoubleProperty bruto;
    private DoubleProperty tara;
    private DoubleProperty monto;
    private DoubleProperty pesoNeto;
    private StringProperty distanciaRecorrida;
    private StringProperty fincaOrigen;
    private StringProperty ingenioDestino;
    private StringProperty camionNombre;
    private StringProperty facturaVenta;
    private Empleado conductor;
    private Finca finca;
    private Ingenio ingenio;
    private Camion camion;

    public int getIdRemito() {
        return idRemito.get();
    }

    public IntegerProperty idRemitoProperty() {
        return idRemito;
    }

    public void setIdRemito(int idRemito) {
        this.idRemito.set(idRemito);
    }

    public String getFecha() {
        return fecha.get();
    }

    public StringProperty fechaProperty() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha.set(fecha);
    }

    public String getHoraEntrada() {
        return horaEntrada.get();
    }

    public StringProperty horaEntradaProperty() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada.set(horaEntrada);
    }

    public double getBruto() {
        return bruto.get();
    }

    public DoubleProperty brutoProperty() {
        return bruto;
    }

    public void setBruto(double bruto) {
        this.bruto.set(bruto);
    }

    public double getTara() {
        return tara.get();
    }

    public DoubleProperty taraProperty() {
        return tara;
    }

    public void setTara(double tara) {
        this.tara.set(tara);
    }

    public String getDistanciaRecorrida() {
        return distanciaRecorrida.get();
    }

    public StringProperty distanciaRecorridaProperty() {
        return distanciaRecorrida;
    }

    public void setDistanciaRecorrida(String distanciaRecorrida) {
        this.distanciaRecorrida.set(distanciaRecorrida);
    }

    public String getFincaOrigen() {
        return fincaOrigen.get();
    }

    public StringProperty fincaOrigenProperty() {
        return fincaOrigen;
    }

    public void setFincaOrigen(String fincaOrigen) {
        this.fincaOrigen.set(fincaOrigen);
    }

    public String getIngenioDestino() {
        return ingenioDestino.get();
    }

    public StringProperty ingenioDestinoProperty() {
        return ingenioDestino;
    }

    public void setIngenioDestino(String ingenioDestino) {
        this.ingenioDestino.set(ingenioDestino);
    }

    public String getCamionNombre() {
        return camionNombre.get();
    }

    public StringProperty camionNombreProperty() {
        return camionNombre;
    }

    public void setCamionNombre(String camionNombre) {
        this.camionNombre.set(camionNombre);
    }

    public String getFacturaVenta() {
        return facturaVenta.get();
    }

    public StringProperty facturaVentaProperty() {
        return facturaVenta;
    }

    public void setFacturaVenta(String facturaVenta) {
        this.facturaVenta.set(facturaVenta);
    }

    public double getMonto() {
        return monto.get();
    }

    public DoubleProperty montoProperty() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto.set(monto);
    }

    public Empleado getConductor() {
        return conductor;
    }

    public void setConductor(Empleado conductor) {
        this.conductor = conductor;
    }

    public double getPesoNeto() {
        return pesoNeto.get();
    }

    public DoubleProperty pesoNetoProperty() {
        return pesoNeto;
    }

    public void setPesoNeto(double pesoNeto) {
        this.pesoNeto.set(pesoNeto);
    }

    public Finca getFinca() {
        return finca;
    }

    public void setFinca(Finca finca) {
        this.finca = finca;
    }

    public Ingenio getIngenio() {
        return ingenio;
    }

    public void setIngenio(Ingenio ingenio) {
        this.ingenio = ingenio;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }


    public Viaje(Integer idRemito, String fecha, String horaEntrada,
                 Double bruto, Double tara, String distanciaRecorrida,
                 String fincaOrigen, String ingenioDestino, String camionNombre,
                 String facturaVenta, Empleado conductor) {
        this.idRemito = new SimpleIntegerProperty(idRemito);
        this.fecha = new SimpleStringProperty(fecha);
        this.horaEntrada = new SimpleStringProperty(horaEntrada);
        this.bruto = new SimpleDoubleProperty(bruto);
        this.tara = new SimpleDoubleProperty(tara);
        this.distanciaRecorrida = new SimpleStringProperty(distanciaRecorrida);
        this.fincaOrigen = new SimpleStringProperty(fincaOrigen);
        this.ingenioDestino = new SimpleStringProperty(ingenioDestino);
        this.camionNombre = new SimpleStringProperty(camionNombre);
        this.facturaVenta = new SimpleStringProperty(facturaVenta);
        this.conductor = conductor;
        this.ingenio = new Ingenio();
        this.conductor = new Empleado();
        this.finca = new Finca();
    }

    public Viaje() {
        this(0,null,null,0.0,0.0,
                null,null,null,null,null, null);
        this.setDistanciaRecorrida("0");
        this.setBruto(0);
        this.setTara(0);
    }
}
