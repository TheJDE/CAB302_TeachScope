module com.cab302.teachscope {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cab302.teachscope to javafx.fxml;
    exports com.cab302.teachscope;
}