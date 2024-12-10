package de.max.ilmlib.utility;

import de.max.ilmlib.libraries.MessageLib;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class SuccessTemplate {
    public SuccessTemplate setColorCode(@NotNull char colorCode) {
        MessageLib.COLOR_SUCCESS = colorCode;
        return this;
    }

    public SuccessTemplate setSound(@NotNull Sound sound) {
        set(sound, null);
        return this;
    }

    public SuccessTemplate setSound(@NotNull Sound sound, Float volume) {
        set(sound, volume);
        return this;
    }

    private void set(Sound sound, Float volume) {
        MessageLib.SOUND_SUCCESS = sound;
        MessageLib.VOLUME_SUCCESS = volume;
    }

    public SuccessTemplate setSuffix(@NotNull String suffix) {
        MessageLib.SUFFIX_SUCCESS = suffix;
        return this;
    }
}