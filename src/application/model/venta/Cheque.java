package application.model.venta;

import com.sun.org.apache.xml.internal.utils.StringBufferPool;
import javafx.beans.property.*;

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

}
