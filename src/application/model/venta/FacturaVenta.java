package application.model.venta;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FacturaVenta {
    private IntegerProperty idFacturaVenta;
    private StringProperty cuit;
    private StringProperty nombreEmpresa;
    private StringProperty fechaEmision;
    private StringProperty nombreCliente;
    private StringProperty cuitCliente;

    public int getIdFacturaVenta() {
        return idFacturaVenta.get();
    }

    public IntegerProperty idFacturaVentaProperty() {
        return idFacturaVenta;
    }

    public void setIdFacturaVenta(int idFacturaVenta) {
        this.idFacturaVenta.set(idFacturaVenta);
    }

    public String getCuit() {
        return cuit.get();
    }

    public StringProperty cuitProperty() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit.set(cuit);
    }

    public String getNombreEmpresa() {
        return nombreEmpresa.get();
    }

    public StringProperty nombreEmpresaProperty() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa.set(nombreEmpresa);
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

    public String getNombreCliente() {
        return nombreCliente.get();
    }

    public StringProperty nombreClienteProperty() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente.set(nombreCliente);
    }

    public String getCuitCliente() {
        return cuitCliente.get();
    }

    public StringProperty cuitClienteProperty() {
        return cuitCliente;
    }

    public void setCuitCliente(String cuitCliente) {
        this.cuitCliente.set(cuitCliente);
    }

    public FacturaVenta(Integer idFacturaVenta, String cuit, String nombreEmpresa,
                        String fechaEmision, String nombreCliente, String cuitCliente) {
        this.idFacturaVenta = new SimpleIntegerProperty(idFacturaVenta);
        this.cuit = new SimpleStringProperty(cuit);
        this.nombreEmpresa = new SimpleStringProperty(nombreEmpresa);
        this.fechaEmision = new SimpleStringProperty(fechaEmision);
        this.nombreCliente = new SimpleStringProperty(nombreCliente);
        this.cuitCliente = new SimpleStringProperty(cuitCliente);
    }

    public FacturaVenta() {
        this(null,null,null,null,null,null);
    }
}
