package de.fpm_studio.ilmlib.util;

import org.jetbrains.annotations.NotNull;

/**
 * Contains the hovertext for method overloading parameter differentiation
 *
 * @author ItsLeMax
 * @since 1.2.0
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
     *
     * @author ItsLeMax
     * @since 1.2.0
     */
    public String get() {
        return hoverText;
    }

}