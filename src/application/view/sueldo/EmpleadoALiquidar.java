package application.view.sueldo;

import application.model.info.Empleado;
import application.model.sueldo.ConceptoSueldo;

import java.util.List;

public class EmpleadoALiquidar {

    private Empleado empleado;
    private List<ConceptoSueldo> conceptoSueldos;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public EmpleadoALiquidar(Empleado empleado, List<ConceptoSueldo> conceptoSueldos) {
        this.empleado = empleado;
        this.conceptoSueldos = conceptoSueldos;
    }
}
