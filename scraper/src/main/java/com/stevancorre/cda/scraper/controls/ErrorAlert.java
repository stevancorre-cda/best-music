package com.stevancorre.cda.scraper.controls;

import javafx.scene.control.Alert;

public final class ErrorAlert extends Alert {
    public ErrorAlert(final String title, final String header, final String content) {
        super(Alert.AlertType.ERROR);

        setTitle(title);
        setHeaderText(header);
        setContentText(content);
    }
}
