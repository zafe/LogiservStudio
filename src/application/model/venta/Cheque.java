package application.model.venta;

import com.sun.org.apache.xml.internal.utils.StringBufferPool;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Cheque {

    private IntegerProperty idCheque;
    private StringProperty fechaEmision;
    private StringProperty fechaPago;
    private StringProperty codigoBancario;
    private StringProperty banco;
    private FloatProperty monto;
    private StringProperty tipoCheque;
    private StringProperty estadoCheque;
    private FacturaVenta facturaVenta = null;

    public int getIdCheque() {
        return idCheque.get();
    }

    public IntegerProperty idChequeProperty() {
        return idCheque;
    }

    public void setIdCheque(int idCheque) {
        this.idCheque.set(idCheque);
    }

    public String getFechaEmision() {
        return fechaEmision.get();
    }

    public StringProperty fechaEmisionProperty() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision.set(fechaEmision);
    }

    public String getFechaPago() {
        return fechaPago.get();
    }

    public StringProperty fechaPagoProperty() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago.set(fechaPago);
    }

    public String getCodigoBancario() {
        return codigoBancario.get();
    }

    public StringProperty codigoBancarioProperty() {
        return codigoBancario;
    }

    public void setCodigoBancario(String codigoBancario) {
        this.codigoBancario.set(codigoBancario);
    }

    public String getBanco() {
        return banco.get();
    }

    public StringProperty bancoProperty() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco.set(banco);
    }

    public String getTipoCheque() {
        return tipoCheque.get();
    }

    public StringProperty tipoChequeProperty() {
        return tipoCheque;
    }

    public void setTipoCheque(String tipoCheque) {
        this.tipoCheque.set(tipoCheque);
    }

    public String getEstadoCheque() {
        return estadoCheque.get();
    }

    public StringProperty estadoChequeProperty() {
        return estadoCheque;
    }

    public void setEstadoCheque(String estadoCheque) {
        this.estadoCheque.set(estadoCheque);
    }

    public float getMonto() {
        return monto.get();
    }

    public FloatProperty montoProperty() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto.set(monto);
    }

    public FacturaVenta getFacturaVenta() {
        return facturaVenta;
    }

    public void setFacturaVenta(FacturaVenta facturaVenta) {
        this.facturaVenta = facturaVenta;
    }

    public Cheque(Integer idCheque, String fechaEmision, String fechaPago, String codigoBancario, String banco, Float monto,
                  String tipoCheque, String estado) {
        this.idCheque = new SimpleIntegerProperty(idCheque);
        this.fechaEmision = new SimpleStringProperty(fechaEmision);
        this.fechaPago = new SimpleStringProperty(fechaPago);
        this.codigoBancario = new SimpleStringProperty(codigoBancario);
        this.banco = new SimpleStringProperty(banco);
        this.monto = new SimpleFloatProperty(monto);
        this.tipoCheque = new SimpleStringProperty(tipoCheque);
        this.estadoCheque = new SimpleStringProperty(estado);

    }

    public Cheque(){

        this(0,null,null,null,null,0f,null,null);
    }

    public ObservableList<String> getBancos(){

        ObservableList<String> bancos = FXCollections.observableArrayList();
        for(Banco b : Banco.values())  bancos.add(b.getNombre());

        return bancos;
    }

    public ObservableList<String> getTiposCheque(){

        ObservableList<String> tiposCheque = FXCollections.observableArrayList();
        for(TipoCheque tc : TipoCheque.values()) tiposCheque.add(tc.getTipo());

        return tiposCheque;
    }

    public ObservableList<String> getEstadosCheque(){

        ObservableList<String> estadosCheque = FXCollections.observableArrayList();
        for(EstadoCheque ec : EstadoCheque.values()) estadosCheque.add(ec.getEstado());

        return estadosCheque;
    }


    private enum Banco{

        MACRO ("Macro"),
        SANTANDER ("Santander Rio"),
        CREDICOP ("CREDICOP"),
        FRANCES ("BBVA Frances"),
        PATAGONIA ("Patagonia");


        private final String nombre;

        Banco(String nombre){
            this.nombre = nombre;
        }

        public String getNombre(){ return nombre; }

    }

    public enum TipoCheque{

        CRUZADO ("Cruzado"),
        DIFERIDO ("Pago Diferido");

        private final String tipo;

        private String getTipo() {return tipo;}

        TipoCheque(String tipo){
            this.tipo = tipo;
        }

    }

    public enum EstadoCheque{

        COBRADO ("Cobrado"),
        PENDIENTE ("En espera");

        private final String estado;

        private String getEstado(){ return estado;}

        EstadoCheque(String estado){
            this.estado = estado;
        }

    }

}
