package application.view.venta;

import application.model.venta.Cheque;
import application.model.venta.Viaje;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.stage.Stage;
import application.repository.venta.ChequeRepository;
import application.repository.venta.ViajeRepository;

public class CalendarioController {

    CalendarView calendarView = new CalendarView();

    Calendar cheques = new Calendar("Cheques");
    Calendar pagos = new Calendar("Pagos");
    Calendar viajes = new Calendar("Viajes");

    public CalendarView initialize(Stage primaryStage) {


        cheques.setStyle(Calendar.Style.STYLE1);
        pagos.setStyle(Calendar.Style.STYLE2);
        viajes.setStyle(Calendar.Style.STYLE7);

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");
        myCalendarSource.getCalendars().addAll(cheques, pagos,viajes);


        calendarView.getCalendarSources().addAll(myCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());

        populateCalendar();

        return calendarView;
    }

    private void populateCalendar(){


        ChequeRepository chequeRepository = new ChequeRepository();

        for(Cheque c : chequeRepository.view()){

            Entry<String> chequeAppointment = new Entry<>("Cobro cheque " + c.getBanco() + " por $" + c.getMonto());
            LocalDate chequeFecha = LocalDate.parse(c.getFechaPago());
            chequeAppointment.setInterval(chequeFecha);
            cheques.addEntry(chequeAppointment);
        }

        ViajeRepository viajeRepository = new ViajeRepository();

        for (Viaje v : viajeRepository.view()){
            Entry<String> viajeAppointment = new Entry<>("Viaje realizado por " + v.getConductor().getNombre()
                                                            + " " + v.getConductor().getApellido() );

            LocalDate viajeFecha = LocalDate.parse(v.getFecha());
            LocalTime viajeHora = LocalTime.parse(v.getHoraEntrada());
            LocalDateTime viajeFechaHora = LocalDateTime.of(viajeFecha,viajeHora);
            viajeAppointment.setInterval(viajeFecha);
            viajeAppointment.setLocation(v.getIngenio().getNombre());
            viajeAppointment.setInterval(viajeFechaHora,viajeFechaHora.plusHours(1));

            viajes.addEntry(viajeAppointment);
        }


    }

}
