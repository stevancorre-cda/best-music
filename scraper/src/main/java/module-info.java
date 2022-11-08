module com.stevancorre.cda.scraper {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires sib.api.v3.sdk;
    requires htmlunit;
    requires com.google.common;
    requires org.mybatis;

    opens com.stevancorre.cda.scraper.controllers to javafx.fxml;
    exports com.stevancorre.cda.scraper;
}