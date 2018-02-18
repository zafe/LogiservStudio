package application.model.info;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Familiar {
    private IntegerProperty idFAMILIAR;
    private StringProperty parentesco;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty fechaNacimiento;
    private Empleado empleado;

    public int getIdFAMILIAR() {
        return idFAMILIAR.get();
    }

    public IntegerProperty idFAMILIARProperty() {
        return idFAMILIAR;
    }

    public void setIdFAMILIAR(int idFAMILIAR) {
        this.idFAMILIAR.set(idFAMILIAR);
    }

    public String getParentesco() {
        return parentesco.get();
    }

    public StringProperty parentescoProperty() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco.set(parentesco);
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

    public String getApellido() {
        return apellido.get();
    }

    public StringProperty apellidoProperty() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido.set(apellido);
    }

    public String getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public StringProperty fechaNacimientoProperty() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Familiar(Integer idFAMILIAR, String parentesco, String nombre, String apellido, String fechaNacimiento, Empleado empleado) {
        this.idFAMILIAR = new SimpleIntegerProperty(idFAMILIAR);
        this.parentesco = new SimpleStringProperty(parentesco);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.fechaNacimiento = new SimpleStringProperty(fechaNacimiento);
        this.empleado = empleado;
    }

    public Familiar() {
        this(0,null,null,null,null,null);
    }
}
