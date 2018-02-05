package application.model.venta;

import application.model.info.Domicilio;
import application.model.info.Localidad;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
    private IntegerProperty idCliente;
    private StringProperty nombre;
    private StringProperty cuit;
    private Domicilio domicilio;


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

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Cliente(Integer idCliente, String nombre, String cuit, Domicilio domicilio) {
        this.idCliente = new SimpleIntegerProperty(idCliente);
        this.nombre = new SimpleStringProperty(nombre);
        this.cuit = new SimpleStringProperty(cuit);
        this.domicilio = domicilio;
    }

    public Cliente() {
        this(0,null,null,null);
    }
}
