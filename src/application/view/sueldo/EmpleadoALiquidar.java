package application.view.sueldo;

import application.model.info.Empleado;
import application.model.sueldo.ConceptoSueldo;
import application.repository.sueldo.ConceptoSueldoRepository;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoALiquidar {

    private Empleado empleado;
    private ObservableList<ConceptoSueldo> conceptoSueldos;
    private List<ConceptoSueldo> remunerativos =  new ArrayList<>();
    private List<ConceptoSueldo> noRemunerativos = new ArrayList<>();
    private List<ConceptoSueldo> retenciones = new ArrayList<>();;
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

    public List<ConceptoSueldo> getRemunerativos() {
        return remunerativos;
    }

    public List<ConceptoSueldo> getNoRemunerativos() {
        return noRemunerativos;
    }

    public List<ConceptoSueldo> getRetenciones() {
        return retenciones;
    }

    public void setRemunerativos(List<ConceptoSueldo> remunerativos) {
        this.remunerativos = remunerativos;
    }

    public void setNoRemunerativos(List<ConceptoSueldo> noRemunerativos) {
        this.noRemunerativos = noRemunerativos;
    }

    public void setRetenciones(List<ConceptoSueldo> retenciones) {
        this.retenciones = retenciones;
    }
}
