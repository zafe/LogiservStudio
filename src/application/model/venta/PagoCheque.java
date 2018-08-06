package application.model.venta;

import javafx.beans.property.*;

public class PagoCheque {

    private IntegerProperty idPagoCheque;
    private StringProperty fechaCobro;
    private FloatProperty comision;
    private FloatProperty saldoEfectivo;
    private Cheque cheque;

    public int getIdPagoCheque() {
        return idPagoCheque.get();
    }

    public IntegerProperty idPagoChequeProperty() {
        return idPagoCheque;
    }

    public void setIdPagoCheque(int idPagoCheque) {
        this.idPagoCheque.set(idPagoCheque);
    }

    public String getFechaCobro() {
        return fechaCobro.get();
    }

    public StringProperty fechaCobroProperty() {
        return fechaCobro;
    }

    public void setFechaCobro(String fechaCobro) {
        this.fechaCobro.set(fechaCobro);
    }

    public float getComision() {
        return comision.get();
    }

    public FloatProperty comisionProperty() {
        return comision;
    }

    public void setComision(float comision) {
        this.comision.set(comision);
    }

    public float getSaldoEfectivo() {
        return saldoEfectivo.get();
    }

    public FloatProperty saldoEfectivoProperty() {
        return saldoEfectivo;
    }

    public void setSaldoEfectivo(float saldoEfectivo) {
        this.saldoEfectivo.set(saldoEfectivo);
    }

    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
    }

    public PagoCheque(Integer idPagoCheque, String fechaCobro, Float comision, Float saldoEfectivo, Cheque cheque) {
        this.idPagoCheque = new SimpleIntegerProperty(idPagoCheque);
        this.fechaCobro = new SimpleStringProperty(fechaCobro);
        this.comision = new SimpleFloatProperty(comision);
        this.saldoEfectivo = new SimpleFloatProperty(saldoEfectivo);
        this.cheque = cheque;
    }

    public PagoCheque() {
        this(0,null,0f,0f,null);
    }
}
