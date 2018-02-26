package application.model.sueldo;

import java.math.BigDecimal;

public class ConceptoCalculado extends ConceptoSueldo{

    private double montoCalculado;

    public ConceptoCalculado (ConceptoSueldo conceptoSueldo){
        super.setIdConceptoSueldo(conceptoSueldo.getIdConceptoSueldo());
        super.setDescripcion(conceptoSueldo.getDescripcion());
        super.setCantidad(conceptoSueldo.getCantidad());
        super.setTipoConcepto(conceptoSueldo.getTipoConcepto());
        super.setTipoCantidad(conceptoSueldo.getTipoCantidad());
        super.setFactor(conceptoSueldo.getFactor());
        super.setSelect(conceptoSueldo.getSelect().isSelected());
        calcularMontoCalculado();
    }

    public double getMontoCalculado() {
        return montoCalculado;
    }

    public void setMontoCalculado(double montoCalculado) {
        this.montoCalculado = montoCalculado;
    }

    private void calcularMontoCalculado(){
        BigDecimal cantidad = BigDecimal.valueOf(super.getCantidad());
        BigDecimal factor = BigDecimal.valueOf(super.getFactor());

        switch (super.getTipoCantidad()){
            case "FIJO":
                this.montoCalculado = cantidad.doubleValue();
                break;
            case "UNIDAD":
                //No hagan esto en casa
                cantidad = cantidad.multiply(factor);
                this.montoCalculado = cantidad.doubleValue();
                break;
            case "PORCENTAJE":
                //Esto tampoco
                cantidad = cantidad.multiply(factor);
                cantidad = cantidad.divide(BigDecimal.valueOf(100.0));
                this.montoCalculado = cantidad.doubleValue();
                break;
        }
    }

    @Override
    public String toString() {
        return "ConceptoCalculado{" +
                "montoCalculado=" + montoCalculado + "\n"+ super.toString()+
                '}';
    }
}
