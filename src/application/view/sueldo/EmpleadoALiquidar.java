package application.view.sueldo;

import application.model.info.Empleado;
import application.model.sueldo.ConceptoSueldo;
import application.repository.sueldo.ConceptoSueldoRepository;
import javafx.collections.ObservableList;

public class EmpleadoALiquidar {

    private Empleado empleado;
    private ObservableList<ConceptoSueldo> conceptoSueldos;
    private ConceptoSueldoRepository repository = new ConceptoSueldoRepository();

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public EmpleadoALiquidar(Empleado empleado) {
        this.empleado = empleado;
        this.conceptoSueldos = repository.getConceptosByEmpleadoId(empleado.getIdEmpleado());
    }

    public ObservableList<ConceptoSueldo> getConceptos(){
        return this.conceptoSueldos;

    }

}
