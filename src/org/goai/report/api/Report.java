package org.goai.report.api;

import java.io.File;

/**
 *
 * @author Zoran Sevarac
 */
public class Report {
    File file;
    ReportFormat format;

    public Report(File file, ReportFormat format) {
        this.file = file;
        this.format = format;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ReportFormat getFormat() {
        return format;
    }

    public void setFormat(ReportFormat format) {
        this.format = format;
    }
    
    
    
}
