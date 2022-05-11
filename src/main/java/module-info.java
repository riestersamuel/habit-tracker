module com.TeamPingui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires org.apache.logging.log4j;

    opens com.teampingui.controllers to javafx.fxml;
    opens com.teampingui.models to javafx.fxml;
    opens com.teampingui to org.apache.logging.log4j;
    //opens com.TeamPingui.exceptions to javafx.fxml;
    //opens com.TeamPingui.interfaces to javafx.fxml;
    exports com.teampingui.controllers to javafx.fxml, org.apache.logging.log4j;
    exports com.teampingui;
}
