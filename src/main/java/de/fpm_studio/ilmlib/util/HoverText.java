package de.fpm_studio.ilmlib.util;

import org.jetbrains.annotations.NotNull;

/**
 * Contains the hovertext for method overloading parameter differentiation
 *
 * @author ItsLeMax
 */
public final class HoverText {

    private final String hoverText;

    public HoverText(@NotNull final String hoverText) {
        this.hoverText = hoverText;
    }

    /**
     * Gets the hovertext from the class
     *
     * @return String of hovertext
     * @author ItsLeMax
     */
    public String get() {
        return hoverText;
    }

}