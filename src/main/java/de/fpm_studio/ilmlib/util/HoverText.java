package de.fpm_studio.ilmlib.util;

import org.jetbrains.annotations.NotNull;

/**
 * Contains the hovertext for method overloading parameter differentiation
 * <br>
 * Birgt den Hovertext für das Unterscheiden von Parametern beim "Überladen von Methoden"
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
     * <br>
     * Entnimmt den Hovertext von der Klasse
     *
     * @return String of hovertext <br> String des Hovertextes
     * @author ItsLeMax
     */
    public String retrieve() {
        return hoverText;
    }
}