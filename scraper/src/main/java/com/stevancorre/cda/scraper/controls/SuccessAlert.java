package com.stevancorre.cda.scraper.controls;

import javafx.scene.control.Alert;

/**
 * Helper class to generate success alerts
 */
public final class SuccessAlert extends Alert {
    /**
     * The constructor
     *
     * @param title   The alert's title
     * @param header  The alert's header
     * @param content The alert's message
     */
    public SuccessAlert(final String title, final String header, final String content) {
        super(AlertType.CONFIRMATION);

        setTitle(title);
        setHeaderText(header);
        setContentText(content);
    }

    /**
     * The constructor
     *
     * @param title  The alert's title
     * @param header The alert's header
     */
    public SuccessAlert(final String title, final String header) {
        super(AlertType.CONFIRMATION);

        setTitle(title);
        setHeaderText(header);
    }
}
