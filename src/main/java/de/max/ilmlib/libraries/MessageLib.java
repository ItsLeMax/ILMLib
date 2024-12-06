package de.max.ilmlib.libraries;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("all")
public class MessageLib {
    private String addSpacing;

    public char COLOR_SUCCESS = 'a';
    public char COLOR_WARNING = 'e';
    public char COLOR_ERROR = 'c';

    private String prefix;

    public Sound SOUND_SUCCESS;
    public Sound SOUND_WARNING;
    public Sound SOUND_ERROR;

    public MessageLib addSpacing() {
        addSpacing = "\n ";
        return this;
    }

    public MessageLib setSuccess(char colorCode) {
        COLOR_SUCCESS = colorCode;
        return this;
    }

    public MessageLib setWarning(char colorCode) {
        COLOR_WARNING = colorCode;
        return this;
    }

    public MessageLib setError(char colorCode) {
        COLOR_ERROR = colorCode;
        return this;
    }

    public MessageLib setPrefix(String prefix) {
        setPrefixVariable(prefix);
        return this;
    }

    public MessageLib setPrefix(String prefix, boolean seperateLine) {
        if (seperateLine) {
            prefix += "\n ";
        }

        setPrefixVariable(prefix);
        return this;
    }

    private void setPrefixVariable(String prefix) {
        this.prefix = prefix + " ";
    }

    private void loggerWarning() {
        Bukkit.getConsoleSender().sendMessage("§" + COLOR_WARNING + "\n" +
                "A sound from one MessageLib method in one of your " +
                "plugins uses a Sound not available in the ILMLib plugin version." + "\n" +
                "Please use .set<Level> (Success, Warning, Error) and a sound of your " +
                "choice available instead." + "\n\n" +
                "You may use these resources as help:" + "\n" +
                "Sound availability: https://jd.andross.fr/tools/versions.html#" + "\n" +
                "Spigot Sound Enums: https://helpch.at/docs/1.21/index.html?org/bukkit/Sound.html" + "\n" +
                "(Make sure to replace the version inside the second url to the one of your server)"
        );
    }

    public MessageLib setSuccess(Sound sound) {
        try {
            SOUND_SUCCESS = sound;
        } catch (NoSuchFieldError ignored) {
            loggerWarning();
        }
        return this;
    }

    public MessageLib setWarning(Sound sound) {
        try {
            SOUND_SUCCESS = sound;
        } catch (NoSuchFieldError ignored) {
            loggerWarning();
        }
        return this;
    }

    public MessageLib setError(Sound sound) {
        try {
            SOUND_SUCCESS = sound;
        } catch (NoSuchFieldError ignored) {
            loggerWarning();
        }
        return this;
    }

    public MessageLib createDefaultSounds() {
        try {
            setSuccess(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            setWarning(Sound.UI_BUTTON_CLICK);
            setError(Sound.BLOCK_ANVIL_PLACE);
        } catch (NoSuchFieldError ignored) {
            loggerWarning();
        }
        return this;
    }

    public MessageLib setSuccess(char colorCode, Sound sound) {
        setSuccess(colorCode);
        setSuccess(sound);
        return this;
    }

    public MessageLib setWarning(char colorCode, Sound sound) {
        setWarning(colorCode);
        setWarning(sound);
        return this;
    }

    public MessageLib setError(char colorCode, Sound sound) {
        setError(colorCode);
        setError(sound);
        return this;
    }

    public void sendInfo(CommandSender sender, String message) {
        send(sender, '7', message);
    }

    public void sendInfo(CommandSender sender, char colorCode, String message) {
        send(sender, colorCode, message);
    }

    private void send(CommandSender sender, char colorCode, String message) {
        sender.sendMessage(addSpacing + prefix + "§" + colorCode + message + addSpacing);

        if (!(sender instanceof Player player)) {
            return;
        }

        Location location = player.getLocation();

        if (SOUND_SUCCESS != null && colorCode == COLOR_SUCCESS) {
            player.playSound(location, SOUND_SUCCESS, 1, 1);
        }

        if (SOUND_WARNING != null && colorCode == COLOR_WARNING) {
            player.playSound(location, SOUND_WARNING, 1, 1);
        }

        if (SOUND_ERROR != null && colorCode == COLOR_ERROR) {
            player.playSound(location, SOUND_ERROR, 1, 1);
        }
    }
}