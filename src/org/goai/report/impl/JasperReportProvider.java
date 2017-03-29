/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.goai.report.impl;

import org.goai.report.api.ReportProvider;

/**
 * http://java-bytes.blogspot.com/2009/06/jasper-reports-example.html
 * 
 * http://ensode.net/jasperreports_intro.html
 * http://www.tutorialspoint.com/jasper_reports/jasper_getting_started.htm
 * 
 * @author zoran
 */
//public class JasperReportProvider implements ReportProvider <JasperReport, Object, Object> {
//
//    
////    JasperReport jasperReport;
////    JasperPrint jasperPrint;
////    try
////    {
////      jasperReport = JasperCompileManager.compileReport(
////          "reports/jasperreports_demo.jrxml");
////      jasperPrint = JasperFillManager.fillReport(
////          jasperReport, new HashMap(), new JREmptyDataSource());
////      JasperExportManager.exportReportToPdfFile(
////          jasperPrint, "reports/simple_report.pdf");
////    }
////    catch (JRException e)
////    {
////      e.printStackTrace();
////    }    
//    
//    private void createReport() {
//        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
//        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
//        JasperExportManager.exportReportToPdfFile(jasperPrint, "c:/reports/test_jasper.pdf");         
//    }
//    
//    @Override
//    public JasperReport getReport(Object data) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public JasperReport getReport(Object data, Object template) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    
//}
