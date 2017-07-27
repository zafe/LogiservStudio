package application.model.calculo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Acoplado {
    private IntegerProperty id;
    private StringProperty marca;
    private StringProperty patente;

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getMarca() {
        return marca.get();
    }

    public StringProperty marcaProperty() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    public String getPatente() {
        return patente.get();
    }

    public StringProperty patenteProperty() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente.set(patente);
    }

    public Acoplado(Integer id, String marca, String patente) {
        this.id = new SimpleIntegerProperty(id);
        this.marca = new SimpleStringProperty(marca);
        this.patente = new SimpleStringProperty(patente);
    }

    public Acoplado() {
        this(null,null,null);
    }
}
