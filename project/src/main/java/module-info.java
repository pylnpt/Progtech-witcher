module project.progtechwitcher {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.apache.logging.log4j;

    opens project.progtechwitcher to javafx.fxml;
    exports project.progtechwitcher;
    exports project.progtechwitcher.Views;
    opens project.progtechwitcher.Views to javafx.fxml;
    opens project.progtechwitcher.models to javafx.base;
    exports project.progtechwitcher.Controllers;
    opens project.progtechwitcher.Controllers to javafx.fxml;
}