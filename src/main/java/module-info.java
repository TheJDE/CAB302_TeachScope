module com.cab302.teachscope {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires org.jfree.jfreechart;
    requires org.apache.pdfbox;
    requires jakarta.mail;

    opens com.cab302.teachscope to javafx.fxml;
    exports com.cab302.teachscope;
    exports com.cab302.teachscope.controllers;
    opens com.cab302.teachscope.controllers to javafx.fxml;
}