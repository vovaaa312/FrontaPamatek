module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.GUI to javafx.fxml;
    exports com.example.GUI;
    exports com.example.GUI.MainWindow;
    opens com.example.GUI.MainWindow to javafx.fxml;
    exports com.example.GUI.Dialog;
    opens com.example.GUI.Dialog to javafx.fxml;
}