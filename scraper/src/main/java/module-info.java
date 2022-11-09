module com.stevancorre.cda.scraper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires sib.api.v3.sdk;
    requires htmlunit;
    requires org.mybatis;
    requires com.google.common;

    opens com.stevancorre.cda.scraper.controllers to javafx.fxml;
    exports com.stevancorre.cda.scraper;
}
