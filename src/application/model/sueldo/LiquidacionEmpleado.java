package application.model.sueldo;

import application.model.info.Empleado;
import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class LiquidacionEmpleado {
    private IntegerProperty id;
    private StringProperty fechaLiquidacion;
    private DoubleProperty importeNeto;
    private DoubleProperty totalHaberesRemunerativos;
    private DoubleProperty totalHaberesNoRemunerativos;
    private DoubleProperty totalRetenciones;
    private DoubleProperty totalBruto;
    private StringProperty nombre;
    private StringProperty inicioPeriodo;
    private StringProperty finPeriodo;
    private Empleado empleado;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }



    public List<ConceptoCalculado> getConceptosLiquidados() {
        return conceptosLiquidados;
    }

    public void setConceptosLiquidados(List<ConceptoCalculado> conceptosLiquidados) {
        this.conceptosLiquidados = conceptosLiquidados;
    }

    private List<ConceptoCalculado> conceptosLiquidados;

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

    public double getImporteNeto() {
        return importeNeto.get();
    }

    public DoubleProperty importeNetoProperty() {
        return importeNeto;
    }

    public void setImporteNeto(double importeNeto) {
        this.importeNeto.set(importeNeto);
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

    public double getTotalBruto() {
        return totalBruto.get();
    }

    public DoubleProperty totalBrutoProperty() {
        return totalBruto;
    }

    public void setTotalBruto(double totalBruto) {
        this.totalBruto.set(totalBruto);
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

    public String getInicioPeriodo() {
        return inicioPeriodo.get();
    }

    public StringProperty inicioPeriodoProperty() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(String inicioPeriodo) {
        this.inicioPeriodo.set(inicioPeriodo);
    }

    public String getFinPeriodo() {
        return finPeriodo.get();
    }

    public StringProperty finPeriodoProperty() {
        return finPeriodo;
    }

    public void setFinPeriodo(String finPeriodo) {
        this.finPeriodo.set(finPeriodo);
    }

    public LiquidacionEmpleado(Integer id, String fechaLiquidacion, Double importeNeto,
                               Double totalHaberesRemunerativos, Double totalHaberesNoRemunerativos,
                               Double totalRetenciones, Double totalBruto,
                               String nombre, String inicioPeriodo, String finPeriodo) {
        this.id = new SimpleIntegerProperty(id);
        this.fechaLiquidacion = new SimpleStringProperty(fechaLiquidacion);
        this.importeNeto = new SimpleDoubleProperty(importeNeto);
        this.totalHaberesRemunerativos = new SimpleDoubleProperty(totalHaberesRemunerativos);
        this.totalHaberesNoRemunerativos = new SimpleDoubleProperty(totalHaberesNoRemunerativos);
        this.totalRetenciones = new SimpleDoubleProperty(totalRetenciones);
        this.totalBruto = new SimpleDoubleProperty(totalBruto);
        this.nombre = new SimpleStringProperty(nombre);
        this.inicioPeriodo = new SimpleStringProperty(inicioPeriodo);
        this.finPeriodo = new SimpleStringProperty(finPeriodo);
    }

    public LiquidacionEmpleado() {
        this(0,null,0.0,
                0.0,0.0,
                0.0,0.0,null,
                null,null);
    }
    private double calcularSumaRemunerativos(List<HaberesRemunerativos> haberesRemunerativos){
        double total =0;
        for (HaberesRemunerativos remunerativo : haberesRemunerativos){
            total+=remunerativo.getMontoCalculado();
        }
        return total;
    }
    private double calcularRetenciones(List<Retencion> retenciones){
        double total =0;
        for (Retencion retencion : retenciones){
            total+= retencion.getMontoCalculado();
        }
        return total;
    }
    private double calcularSumaNoRemunerativos(List<HaberNoRemunerativo> haberNoRemunerativos){
        double total =0;
        for (HaberNoRemunerativo noRemunerativo : haberNoRemunerativos){
            total+=noRemunerativo.getMontoCalculado();
        }
        return total;
    }
    private double calcularSueldoBlanco(List<HaberesRemunerativos> haberesRemunerativos,List<Retencion> retenciones){
        double sueldoBlanco = calcularSumaRemunerativos(haberesRemunerativos) - calcularRetenciones(retenciones);
        return sueldoBlanco;
    }
    public double calcularSueldoTotal(List<HaberNoRemunerativo> haberNoRemunerativos,
                                      List<HaberesRemunerativos> haberesRemunerativos,
                                      List<Retencion> retenciones){
        double sueldoTotal = calcularSueldoBlanco(haberesRemunerativos, retenciones)
                + calcularSumaNoRemunerativos(haberNoRemunerativos);
        return sueldoTotal;

    }


}
