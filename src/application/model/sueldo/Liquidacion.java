package application.model.sueldo;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class Liquidacion {
    private IntegerProperty id;
    private StringProperty fechaLiquidacion;
    private DoubleProperty totalHaberesRemunerativos;
    private DoubleProperty totalHaberesNoRemunerativos;
    private DoubleProperty totalRetenciones;
    private List<LiquidacionEmpleado> liquidacionesEmpleados = new ArrayList<>();

    public Liquidacion() {
        this(0,null,0.0,
                0.0,0.0);
    }

    public Liquidacion(Integer id, String fechaLiquidacion, Double totalHaberesRemunerativos, Double totalHaberesNoRemunerativos,
                       Double totalRetenciones) {
        this.id = new SimpleIntegerProperty(id);
        this.fechaLiquidacion = new SimpleStringProperty(fechaLiquidacion);

        this.totalHaberesRemunerativos = new SimpleDoubleProperty(totalHaberesRemunerativos);
        this.totalHaberesNoRemunerativos = new SimpleDoubleProperty(totalHaberesNoRemunerativos);
        this.totalRetenciones = new SimpleDoubleProperty(totalRetenciones);

    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion.get();
    }

    public StringProperty fechaLiquidacionProperty() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion.set(fechaLiquidacion);
    }

    public double getTotalHaberesRemunerativos() {
        return totalHaberesRemunerativos.get();
    }

    public DoubleProperty totalHaberesRemunerativosProperty() {
        return totalHaberesRemunerativos;
    }

    public void setTotalHaberesRemunerativos(double totalHaberesRemunerativos) {
        this.totalHaberesRemunerativos.set(totalHaberesRemunerativos);
    }

    public double getTotalHaberesNoRemunerativos() {
        return totalHaberesNoRemunerativos.get();
    }

    public DoubleProperty totalHaberesNoRemunerativosProperty() {
        return totalHaberesNoRemunerativos;
    }

    public void setTotalHaberesNoRemunerativos(double totalHaberesNoRemunerativos) {
        this.totalHaberesNoRemunerativos.set(totalHaberesNoRemunerativos);
    }

    public double getTotalRetenciones() {
        return totalRetenciones.get();
    }

    public DoubleProperty totalRetencionesProperty() {
        return totalRetenciones;
    }

    public void setTotalRetenciones(double totalRetenciones) {
        this.totalRetenciones.set(totalRetenciones);
    }

    public List<LiquidacionEmpleado> getLiquidacionesEmpleados() {
        return liquidacionesEmpleados;
    }

    public void setLiquidacionesEmpleados(List<LiquidacionEmpleado> liquidacionesEmpleados) {
        this.liquidacionesEmpleados = liquidacionesEmpleados;
    }
}