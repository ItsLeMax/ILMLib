package de.max.ilmlib.utility;

import de.max.ilmlib.libraries.MessageLib;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class ErrorTemplate {
    public ErrorTemplate setFormattingCode(@NotNull char formattingCode) {
        MessageLib.FORMATTING_ERROR = formattingCode;
        return this;
    }

    public ErrorTemplate setSound(@NotNull Sound sound) {
        set(sound, null);
        return this;
    }

    public ErrorTemplate setSound(@NotNull Sound sound, Float volume) {
        set(sound, volume);
        return this;
    }

    private void set(Sound sound, Float volume) {
        MessageLib.SOUND_ERROR = sound;
        MessageLib.VOLUME_ERROR = volume;
    }

    public ErrorTemplate setSuffix(@NotNull String suffix) {
        MessageLib.SUFFIX_ERROR = suffix;
        return this;
    }
}