module org.example.javafx_example {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;

    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires java.persistence;
    requires java.naming;

    opens org.example.javafx_example to javafx.fxml, com.google.gson;
    exports org.example.javafx_example;
    exports org.example.javafx_example.server;
    opens org.example.javafx_example.server to com.google.gson, javafx.fxml;
}