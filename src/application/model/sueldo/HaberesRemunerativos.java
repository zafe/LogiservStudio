package application.model.sueldo;

public class HaberesRemunerativos extends ConceptoCalculado implements Calculeable {


    @Override
    public double calcularMontoFijo() {
        return super.getConceptoSueldo().getCantidad();
    }

    @Override
    public double calcularMontoPorcentual(double monto) {
        double total = monto + (monto*super.getConceptoSueldo().getCantidad())/100;
        return total;
    }

    @Override
    public double calcularMontoUnidad(double cantidad) {
        return (super.getConceptoSueldo().getCantidad()*cantidad);
    }

    public HaberesRemunerativos(double numero, ConceptoSueldo conceptoSueldo) {
        super.setConceptoSueldo(conceptoSueldo);
        switch (getConceptoSueldo().getTipoConcepto()){
            case "PORCENTAJE":
                setMontoCalculado(calcularMontoPorcentual(numero));
                break;
            case "FIJO":
                setMontoCalculado(calcularMontoFijo());
            break;
            case "UNIDAD":
                setMontoCalculado(calcularMontoFijo());
                break;
        }
    }
}
