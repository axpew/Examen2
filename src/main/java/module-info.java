module ucr.examen2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.examen2 to javafx.fxml;
    exports ucr.examen2;
}