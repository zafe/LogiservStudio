package application.model.venta;

import application.model.info.Domicilio;
import application.model.info.Empleado;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Organizacion {

    private IntegerProperty idOrganizacion;
    private StringProperty nombreOrg;
    private StringProperty cuitOrg;
    private Empleado apoderadoOrg;
    private Domicilio domicilioOrg;

    public int getIdOrganizacion() {
        return idOrganizacion.get();
    }

    public IntegerProperty idOrganizacionProperty() {
        return idOrganizacion;
    }

    public void setIdOrganizacion(int idOrganizacion) {
        this.idOrganizacion.set(idOrganizacion);
    }

    public String getNombreOrg() {
        return nombreOrg.get();
    }

    public StringProperty nombreOrgProperty() {
        return nombreOrg;
    }

    public void setNombreOrg(String nombreOrg) {
        this.nombreOrg.set(nombreOrg);
    }

    public String getCuitOrg() {
        return cuitOrg.get();
    }

    public StringProperty cuitOrgProperty() {
        return cuitOrg;
    }

    public void setCuitOrg(String cuitOrg) {
        this.cuitOrg.set(cuitOrg);
    }

    public Empleado getApoderadoOrg() {
        return apoderadoOrg;
    }

    public void setApoderadoOrg(Empleado apoderadoOrg) {
        this.apoderadoOrg = apoderadoOrg;
    }

    public Domicilio getDomicilioOrg() {
        return domicilioOrg;
    }

    public void setDomicilioOrg(Domicilio domicilioOrg) {
        this.domicilioOrg = domicilioOrg;
    }

    public Organizacion() {
    }

    public Organizacion(IntegerProperty idOrganizacion, StringProperty nombreOrg, StringProperty cuitOrg,
                        Empleado apoderadoOrg, Domicilio domicilioOrg) {
        this.idOrganizacion = idOrganizacion;
        this.nombreOrg = nombreOrg;
        this.cuitOrg = cuitOrg;
        this.apoderadoOrg = apoderadoOrg;
        this.domicilioOrg = domicilioOrg;
    }
}
