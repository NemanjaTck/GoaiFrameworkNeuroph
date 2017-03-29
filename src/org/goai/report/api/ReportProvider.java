package org.goai.report.api;

/**
 * IText Jasper Reports Jeff
 * generisi: fajl plain, text, rtf, pdf, xml, html
 * predefinisani templejti
 * xml kao osnovni format
 * 
 * 
 * output definitivno fajl ili stream
 * 
 * @author zoran
 */
public interface ReportProvider <D, T> {
    public Report getReport(D data, ReportFormat outputFormat);    // , Template      
}
