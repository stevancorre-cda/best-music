package com.stevancorre.cda.scraper.controls;

import javafx.scene.control.Alert;

public final class SuccessAlert extends Alert {
    public SuccessAlert(final String title, final String header, final String content) {
        super(AlertType.CONFIRMATION);

        setTitle(title);
        setHeaderText(header);
        setContentText(content);
    }

    public SuccessAlert(final String title, final String header) {
        super(AlertType.CONFIRMATION);

        setTitle(title);
        setHeaderText(header);
    }
}
