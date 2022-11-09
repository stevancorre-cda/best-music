package com.stevancorre.cda.scraper.controls;

import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.List;

public final class ProviderCheckbox extends Pane {
    private final Provider provider;
    private final CheckBox checkBox;

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

    public Provider getProvider() {
        return provider;
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }
}
