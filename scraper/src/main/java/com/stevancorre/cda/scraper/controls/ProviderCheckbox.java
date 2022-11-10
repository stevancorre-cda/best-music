package com.stevancorre.cda.scraper.controls;

import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Helper class to wrap checkbox with label associated with a provider
 */
public final class ProviderCheckbox extends Pane {
    private final Provider provider;
    private final CheckBox checkBox;

    /**
     * Constructor
     *
     * @param provider The associated provider
     */
    public ProviderCheckbox(final Provider provider) {
        super();

        this.provider = provider;
        this.checkBox = new CheckBox();

        final List<Node> children = getChildren();
        children.add(checkBox);
        children.add(new Label(provider.getName()) {{
            setTranslateX(30);
        }});
    }

    /**
     * Get the associated provider
     *
     * @return The provider
     */
    public Provider getProvider() {
        return provider;
    }

    /**
     * Check if the checkbox is selected
     *
     * @return True if the checkbox is selected. Otherwise false
     */
    public boolean isSelected() {
        return checkBox.isSelected();
    }

    /**
     * Select or unselect the checkbox
     *
     * @param selected True to select, false to unselect
     */
    public void setSelected(final boolean selected) {
        checkBox.setSelected(selected);
    }
}
