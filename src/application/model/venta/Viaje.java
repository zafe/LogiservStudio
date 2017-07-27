package application.model.venta;

import javafx.beans.property.*;

public class Viaje {
    private IntegerProperty idRemito;
    private StringProperty fecha;
    private StringProperty horaEntrada;
    private DoubleProperty bruto;
    private DoubleProperty tara;
    private StringProperty distanciaRecorrida;
    private StringProperty fincaOrigen;
    private StringProperty ingenioDestino;
    private StringProperty camion;
    private StringProperty facturaVenta;

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

    public String getCamion() {
        return camion.get();
    }

    public StringProperty camionProperty() {
        return camion;
    }

    public void setCamion(String camion) {
        this.camion.set(camion);
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

    public Viaje(Integer idRemito, String fecha, String horaEntrada,
                 Double bruto, Double tara, String distanciaRecorrida,
                 String fincaOrigen, String ingenioDestino, String camion,
                 String facturaVenta) {
        this.idRemito = new SimpleIntegerProperty(idRemito);
        this.fecha = new SimpleStringProperty(fecha);
        this.horaEntrada = new SimpleStringProperty(horaEntrada);
        this.bruto = new SimpleDoubleProperty(bruto);
        this.tara = new SimpleDoubleProperty(tara);
        this.distanciaRecorrida = new SimpleStringProperty(distanciaRecorrida);
        this.fincaOrigen = new SimpleStringProperty(fincaOrigen);
        this.ingenioDestino = new SimpleStringProperty(ingenioDestino);
        this.camion = new SimpleStringProperty(camion);
        this.facturaVenta = new SimpleStringProperty(facturaVenta);
    }

    public Viaje() {
        this(null,null,null,null,null,
                null,null,null,null,null);
    }
}
