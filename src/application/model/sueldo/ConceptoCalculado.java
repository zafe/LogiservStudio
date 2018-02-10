package application.model.sueldo;

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
    }

    public double getMontoCalculado() {
        return montoCalculado;
    }

    public void setMontoCalculado(double montoCalculado) {
        this.montoCalculado = montoCalculado;
    }

    public void calcularMontoCalculado(){
        switch (super.getTipoConcepto()){
            case "FIJO":
                this.montoCalculado = super.getCantidad();
                break;
            case "UNIDAD":
                this.montoCalculado = super.getCantidad() * super.getFactor();
                break;
            case "PORCENTAJE":
                this.montoCalculado = (super.getCantidad() * super.getFactor()) / 100;
                break;
        }
    }
}
