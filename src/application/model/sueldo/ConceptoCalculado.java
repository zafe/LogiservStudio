package application.model.sueldo;

public abstract class ConceptoCalculado {
    private double montoCalculado;
    private ConceptoSueldo conceptoSueldo;

    public double getMontoCalculado() {
        return montoCalculado;
    }

    public void setMontoCalculado(double montoCalculado) {
        this.montoCalculado = montoCalculado;
    }

    public ConceptoSueldo getConceptoSueldo() {
        return conceptoSueldo;
    }

    public void setConceptoSueldo(ConceptoSueldo conceptoSueldo) {
        this.conceptoSueldo = conceptoSueldo;
    }
}
