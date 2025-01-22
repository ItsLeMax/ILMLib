package de.max.ilmlib.init;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class HoverText {
    private String hoverText;

    public HoverText(@NotNull String hoverText) {
        this.hoverText = hoverText;
    }

    public String retrieve() {
        return hoverText;
    }
}