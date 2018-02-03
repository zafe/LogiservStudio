package application.model.sueldo;

public interface Calculeable {
    public double calcularMontoFijo();
    public double calcularMontoPorcentual(double monto);
    public double calcularMontoUnidad(double cantidad);
}
