package application.model.calculo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Camion {
    private IntegerProperty id;
    private StringProperty marca;
    private StringProperty modelo;
    private StringProperty patente;
    private StringProperty motor;
    private StringProperty chasis;

    public String getMotor() {
        return motor.get();
    }

    public StringProperty motorProperty() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor.set(motor);
    }

    public String getChasis() {
        return chasis.get();
    }

    public StringProperty chasisProperty() {
        return chasis;
    }

    public void setChasis(String chasis) {
        this.chasis.set(chasis);
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

    public String getMarca() {
        return marca.get();
    }

    public StringProperty marcaProperty() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca.set(marca);
    }

    public String getModelo() {
        return modelo.get();
    }

    public StringProperty modeloProperty() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
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

    public Camion(Integer id, String marca, String modelo, String patente, String motor, String chasis) {
        this.id = new SimpleIntegerProperty(id);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.patente = new SimpleStringProperty(patente);
        this.motor = new SimpleStringProperty(motor);
        this.chasis = new SimpleStringProperty(chasis);
    }

    public Camion() {
        this(0,null,null,null, null,null);
    }
}
