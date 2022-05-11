module com.TeamPingui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;

    opens com.TeamPingui.controllers to javafx.fxml;
    opens com.TeamPingui.Models to javafx.fxml;
    //opens com.TeamPingui.exceptions to javafx.fxml;
    //opens com.TeamPingui.interfaces to javafx.fxml;
    exports com.TeamPingui.controllers to javafx.fxml;
    exports com.TeamPingui;
}
