package de.max.ilmlib.utility;

import de.max.ilmlib.libraries.MessageLib;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class WarningTemplate {
    public WarningTemplate setColorCode(@NotNull char colorCode) {
        MessageLib.COLOR_WARNING = colorCode;
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