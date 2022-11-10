package com.stevancorre.cda.scraper.controls;

import javafx.scene.control.Alert;

/**
 * Helper class to generate error alerts
 */
public final class ErrorAlert extends Alert {
    /**
     * The constructor
     *
     * @param title The alert's title
     * @param header The alert's header
     * @param content The alert's message
     */
    public ErrorAlert(final String title, final String header, final String content) {
        super(Alert.AlertType.ERROR);

        setTitle(title);
        setHeaderText(header);
        setContentText(content);
    }
}
