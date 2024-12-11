package de.max.ilmlib.utility;

import de.max.ilmlib.libraries.MessageLib;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class WarningTemplate {
    public WarningTemplate setFormattingCode(@NotNull char formattingCode) {
        MessageLib.FORMATTING_WARNING = formattingCode;
        return this;
    }

    public WarningTemplate setSound(@NotNull Sound sound) {
        set(sound, null);
        return this;
    }

    public WarningTemplate setSound(@NotNull Sound sound, Float volume) {
        set(sound, volume);
        return this;
    }

    private void set(Sound sound, Float volume) {
        MessageLib.SOUND_WARNING = sound;
        MessageLib.VOLUME_WARNING = volume;
    }

    public WarningTemplate setSuffix(@NotNull String suffix) {
        MessageLib.SUFFIX_WARNING = suffix;
        return this;
    }
}