module com.example.speedtype {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.speedtype to javafx.fxml;
    exports com.example.speedtype;
}