package application.reports.classes;

import application.database.JDBCConnection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJasperReports {
    private static JasperReport report;
    private static JasperPrint reportFilled;
    private static JasperFX viewer;
    public static void createReport(String path){
        try {
            Connection connection = JDBCConnection.getInstanceConnection();
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report,null, connection);
        }catch (JRException e){
            e.printStackTrace();
        }
    }
    public static void createReport(String path,String nombreParametro, int id){
        try {
            Connection connection = JDBCConnection.getInstanceConnection();
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put(nombreParametro,id);
            reportFilled = JasperFillManager.fillReport(report, parameter, connection);
        }catch (JRException e){
            e.printStackTrace();
        }

    }
    public static void createReport(String path,String nombreParametro, int id, String nombreParamete2, double value){
        try {
            Connection connection = JDBCConnection.getInstanceConnection();
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            Map<String, Object> parameter = new HashMap<>();
            parameter.put(nombreParametro,id);
            parameter.put(nombreParamete2, value);
            reportFilled = JasperFillManager.fillReport(report, parameter, connection);
        }catch (JRException e){
            e.printStackTrace();
        }

    }
    public static void showViewer(){
        viewer = new JasperFX(reportFilled);
        viewer.show();
    }
}
