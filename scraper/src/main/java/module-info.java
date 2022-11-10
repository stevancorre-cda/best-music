module com.stevancorre.cda.scraper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires sib.api.v3.sdk;
    requires htmlunit;
    requires org.mybatis;
    requires com.google.common;

    opens com.stevancorre.cda.scraper.controllers to javafx.fxml;
    opens com.stevancorre.cda.scraper.controllers.files to javafx.fxml;
    opens com.stevancorre.cda.scraper.controllers.settings to javafx.fxml;
    exports com.stevancorre.cda.scraper;
}
