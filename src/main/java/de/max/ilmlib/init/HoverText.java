package de.max.ilmlib.init;

@SuppressWarnings("all")
public class HoverText {
    private String hoverText;

    public HoverText(String hoverText) {
        this.hoverText = hoverText;
    }

    public String retrieve() {
        return hoverText;
    }
}