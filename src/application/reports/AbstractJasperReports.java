package application.reports;

import application.database.JDBCConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;

public abstract class AbstractJasperReports {
    private static JasperReport report;
    private static JasperPrint reportFilled;
    private static JasperViewer viewer;

    public static void createReport(String path){
        try {
            Connection connection = JDBCConnection.getInstanceConnection();
            report = (JasperReport) JRLoader.loadObjectFromFile(path);
            reportFilled = JasperFillManager.fillReport(report,null, connection);
        }catch (JRException e){
            e.printStackTrace();
        }

    }
    public static void showViewer(){
        viewer = new JasperViewer(reportFilled,false);

        viewer.setVisible(true);
    }
    public static void exportToPdf(String path){
        try {
            JasperExportManager.exportReportToPdfFile(reportFilled, path);
        }catch (JRException e){
            e.printStackTrace();
        }

    }
}
