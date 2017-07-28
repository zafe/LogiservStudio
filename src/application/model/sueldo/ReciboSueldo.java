	package application.model.sueldo;

    import javafx.beans.property.*;

    public class ReciboSueldo {
    private IntegerProperty idRecibo;
    private StringProperty fecha;
    private DoubleProperty totalNeto;
    private StringProperty inicioPeriodo;
    private StringProperty finPeriodo;
    private StringProperty nombreEmpleado;

        public int getIdRecibo() {
            return idRecibo.get();
        }

        public IntegerProperty idReciboProperty() {
            return idRecibo;
        }

        public void setIdRecibo(int idRecibo) {
            this.idRecibo.set(idRecibo);
        }

        public String getFecha() {
            return fecha.get();
        }

        public StringProperty fechaProperty() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha.set(fecha);
        }

        public double getTotalNeto() {
            return totalNeto.get();
        }

        public DoubleProperty totalNetoProperty() {
            return totalNeto;
        }

        public void setTotalNeto(double totalNeto) {
            this.totalNeto.set(totalNeto);
        }

        public String getInicioPeriodo() {
            return inicioPeriodo.get();
        }

        public StringProperty inicioPeriodoProperty() {
            return inicioPeriodo;
        }

        public void setInicioPeriodo(String inicioPeriodo) {
            this.inicioPeriodo.set(inicioPeriodo);
        }

        public String getFinPeriodo() {
            return finPeriodo.get();
        }

        public StringProperty finPeriodoProperty() {
            return finPeriodo;
        }

        public void setFinPeriodo(String finPeriodo) {
            this.finPeriodo.set(finPeriodo);
        }

        public String getNombreEmpleado() {
            return nombreEmpleado.get();
        }

        public StringProperty nombreEmpleadoProperty() {
            return nombreEmpleado;
        }

        public void setNombreEmpleado(String nombreEmpleado) {
            this.nombreEmpleado.set(nombreEmpleado);
        }

        public ReciboSueldo(Integer idRecibo, String fecha, Double totalNeto,
                            String inicioPeriodo, String finPeriodo, String nombreEmpleado) {
            this.idRecibo = new SimpleIntegerProperty(idRecibo);
            this.fecha = new SimpleStringProperty(fecha);
            this.totalNeto = new SimpleDoubleProperty(totalNeto);
            this.inicioPeriodo = new SimpleStringProperty(inicioPeriodo);
            this.finPeriodo = new SimpleStringProperty(finPeriodo);
            this.nombreEmpleado = new SimpleStringProperty(nombreEmpleado);
        }

        public ReciboSueldo() {
            this(0,null,0.0,null,null,null);
        }
    }
