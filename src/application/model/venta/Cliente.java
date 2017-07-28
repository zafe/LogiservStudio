package application.model.venta;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
    private IntegerProperty idCliente;
    private StringProperty nombre;
    private StringProperty cuit;
    private StringProperty domicilio;

    public int getIdCliente() {
        return idCliente.get();
    }

    public IntegerProperty idClienteProperty() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente.set(idCliente);
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

    public String getCuit() {
        return cuit.get();
    }

    public StringProperty cuitProperty() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit.set(cuit);
    }

    public String getDomicilio() {
        return domicilio.get();
    }

    public StringProperty domicilioProperty() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio.set(domicilio);
    }

    public Cliente(Integer idCliente, String nombre, String cuit, String domicilio) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nombre = new SimpleStringProperty(nombre);
        this.cuit = new SimpleStringProperty(cuit);
        this.domicilio = new SimpleStringProperty(domicilio);
    }

    public Cliente() {
        this(0,null,null,null);
    }
}
