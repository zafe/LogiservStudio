package application.model.sueldo;

import javafx.beans.property.*;

public class DetalleLiquidacionEmpleado {
    private IntegerProperty idDetalleLiquidacionEmpleado;
    private DoubleProperty cantidad;
    private StringProperty descripcionSueldo;
    private DoubleProperty monto;

    public int getIdDetalleLiquidacionEmpleado() {
        return idDetalleLiquidacionEmpleado.get();
    }

    public IntegerProperty idDetalleLiquidacionEmpleadoProperty() {
        return idDetalleLiquidacionEmpleado;
    }

    public void setIdDetalleLiquidacionEmpleado(int idDetalleLiquidacionEmpleado) {
        this.idDetalleLiquidacionEmpleado.set(idDetalleLiquidacionEmpleado);
    }

    public double getCantidad() {
        return cantidad.get();
    }

    public DoubleProperty cantidadProperty() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad.set(cantidad);
    }

    public String getDescripcionSueldo() {
        return descripcionSueldo.get();
    }

    public StringProperty descripcionSueldoProperty() {
        return descripcionSueldo;
    }

    public void setDescripcionSueldo(String descripcionSueldo) {
        this.descripcionSueldo.set(descripcionSueldo);
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

    public DetalleLiquidacionEmpleado(Integer idDetalleLiquidacionEmpleado, Double cantidad,
                                      String descripcionSueldo, Double monto) {
        this.idDetalleLiquidacionEmpleado = new SimpleIntegerProperty(idDetalleLiquidacionEmpleado);
        this.cantidad = new SimpleDoubleProperty(cantidad);
        this.descripcionSueldo = new SimpleStringProperty(descripcionSueldo);
        this.monto = new SimpleDoubleProperty(monto);
    }

    public DetalleLiquidacionEmpleado() {
        this(0,0.0,null,0.0);
    }
}
