package application.model.sueldo;

public class ConceptoCalculado {
    private double montoCalculado;
    private ConceptoSueldo conceptoSueldo;

    private double factor;

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

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
