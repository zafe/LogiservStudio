package application.model.calculo;

import javafx.beans.property.*;

public class OrigenDestino {
    private IntegerProperty idOrigenDestino;
    private FloatProperty distanciaKM;
    private StringProperty nombreFinca;
    private StringProperty nombreIngenio;

    public int getIdOrigenDestino() {
        return idOrigenDestino.get();
    }

    public IntegerProperty idOrigenDestinoProperty() {
        return idOrigenDestino;
    }

    public void setIdOrigenDestino(int idOrigenDestino) {
        this.idOrigenDestino.set(idOrigenDestino);
    }

    public float getDistanciaKM() {
        return distanciaKM.get();
    }

    public FloatProperty distanciaKMProperty() {
        return distanciaKM;
    }

    public void setDistanciaKM(float distanciaKM) {
        this.distanciaKM.set(distanciaKM);
    }

    public String getNombreFinca() {
        return nombreFinca.get();
    }

    public StringProperty nombreFincaProperty() {
        return nombreFinca;
    }

    public void setNombreFinca(String nombreFinca) {
        this.nombreFinca.set(nombreFinca);
    }

    public String getNombreIngenio() {
        return nombreIngenio.get();
    }

    public StringProperty nombreIngenioProperty() {
        return nombreIngenio;
    }

    public void setNombreIngenio(String nombreIngenio) {
        this.nombreIngenio.set(nombreIngenio);
    }

    public OrigenDestino(Integer idOrigenDestino, Float distanciaKM, String nombreFinca, String nombreIngenio) {
        this.idOrigenDestino = new SimpleIntegerProperty(idOrigenDestino);
        this.distanciaKM = new SimpleFloatProperty(distanciaKM);
        this.nombreFinca = new SimpleStringProperty(nombreFinca);
        this.nombreIngenio = new SimpleStringProperty(nombreIngenio);
    }
    public OrigenDestino(){
        this(null,null,null,null);
    }
}
