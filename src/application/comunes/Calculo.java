package application.comunes;

import java.math.BigDecimal;

public class Calculo {

    public double calcularPesoNeto(double bruto, double tara){
        double pesoNeto = 0;
        BigDecimal brutoD = BigDecimal.valueOf(bruto);
        BigDecimal taraD = BigDecimal.valueOf(tara);
        BigDecimal netoD = brutoD.subtract(taraD);
        pesoNeto = netoD.doubleValue();
        return pesoNeto;
    }

    public double calcularMonto(double distancia, double pesoNeto, double tarifa, double arranque){
        BigDecimal distanciaD = BigDecimal.valueOf(distancia);
        BigDecimal pesoNetoD = BigDecimal.valueOf(pesoNeto);
        BigDecimal precioKmD = BigDecimal.valueOf(tarifa);
        BigDecimal arranqueD = BigDecimal.valueOf(arranque);
        BigDecimal precioUnitarioD = distanciaD.multiply(precioKmD).add(arranqueD);
        BigDecimal montoViajeD = precioUnitarioD.multiply(pesoNetoD).multiply(BigDecimal.valueOf(0.001));

        return  montoViajeD.doubleValue();

    }

    public Calculo() {
    }
}
