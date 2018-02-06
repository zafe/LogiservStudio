package application.model.sueldo;

import javafx.beans.property.*;
import javafx.scene.control.CheckBox;

public class ConceptoSueldo {
    private IntegerProperty idConceptoSueldo;
    private StringProperty descripcion;
    private FloatProperty cantidad;
    private StringProperty tipoConcepto;
    private StringProperty tipoCantidad;
    private CheckBox select;
    private DoubleProperty factor;

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    public double getFactor() {
        return factor.get();
    }

    public DoubleProperty factorProperty() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor.set(factor);
    }

    public int getIdConceptoSueldo() {
        return idConceptoSueldo.get();
    }

    public IntegerProperty idConceptoSueldoProperty() {
        return idConceptoSueldo;
    }

    public void setIdConceptoSueldo(int idConceptoSueldo) {
        this.idConceptoSueldo.set(idConceptoSueldo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public float getCantidad() {
        return cantidad.get();
    }

    public FloatProperty cantidadProperty() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad.set(cantidad);
    }

    public String getTipoConcepto() {
        return tipoConcepto.get();
    }

    public StringProperty tipoConceptoProperty() {
        return tipoConcepto;
    }

    public void setTipoConcepto(String tipoConcepto) {
        this.tipoConcepto.set(tipoConcepto);
    }

    public String getTipoCantidad() {
        return tipoCantidad.get();
    }

    public StringProperty tipoCantidadProperty() {
        return tipoCantidad;
    }

    public void setTipoCantidad(String tipoCantidad) {
        this.tipoCantidad.set(tipoCantidad);
    }

    public ConceptoSueldo(Integer idConceptoSueldo, String descripcion, Float cantidad,
                          String tipoConcepto, String tipoCantidad, Double factor) {
        this.idConceptoSueldo = new SimpleIntegerProperty(idConceptoSueldo);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.cantidad = new SimpleFloatProperty(cantidad);
        this.tipoConcepto = new SimpleStringProperty(tipoConcepto);
        this.tipoCantidad = new SimpleStringProperty(tipoCantidad);
        this.factor = new SimpleDoubleProperty(factor);
        this.select = new CheckBox();
    }
    
    public ConceptoSueldo(){
    	this(0, null, 0f, null, null, 1.0);
    }

}
