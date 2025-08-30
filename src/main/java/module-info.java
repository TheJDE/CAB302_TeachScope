module com.cab302.teachscope {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cab302.teachscope to javafx.fxml;
    exports com.cab302.teachscope;
    exports com.cab302.teachscope.controllers;
    opens com.cab302.teachscope.controllers to javafx.fxml;
}