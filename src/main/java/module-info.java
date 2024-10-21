module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports org.example.demo.controllers;
    opens org.example.demo.controllers to javafx.fxml;
    opens org.example.demo.models ;
    opens org.example.demo.util;
    opens org.example.demo.util.annotations;
    opens org.example.demo.util.dbhelpers;
    opens org.example.demo.models.additionals;
}