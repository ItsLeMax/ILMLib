package de.max.ilmlib.libraries;

import de.max.ilmlib.utility.HoverText;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class MessageLib {
    private boolean addSpacing;
    private boolean seperateLine;
    private String prefix;

    private final char COLOR_DEFAULT = '7';

    public static char COLOR_SUCCESS;
    public static char COLOR_WARNING;
    public static char COLOR_ERROR;

    public static Sound SOUND_SUCCESS;
    public static Sound SOUND_WARNING;
    public static Sound SOUND_ERROR;

    public static Float VOLUME_SUCCESS;
    public static Float VOLUME_WARNING;
    public static Float VOLUME_ERROR;

    public static String SUFFIX_SUCCESS;
    public static String SUFFIX_WARNING;
    public static String SUFFIX_ERROR;

    public MessageLib addSpacing() {
        addSpacing = true;
        return this;
    }

    public MessageLib setPrefix(@NotNull String prefix) {
        setPrefixVariable(prefix, false);
        return this;
    }

    public MessageLib setPrefix(@NotNull String prefix, boolean seperateLine) {
        setPrefixVariable(prefix, seperateLine);
        return this;
    }

    private void setPrefixVariable(String prefix, boolean seperateLine) {
        this.seperateLine = seperateLine;
        this.prefix = prefix;
    }

    public MessageLib createDefaults() {
        COLOR_SUCCESS = 'a';
        COLOR_WARNING = 'e';
        COLOR_ERROR = 'c';

        try {
            SOUND_SUCCESS = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
            SOUND_WARNING = Sound.UI_BUTTON_CLICK;
            SOUND_ERROR = Sound.BLOCK_ANVIL_PLACE;
        } catch (NoSuchFieldError ignored) {
            Bukkit.getLogger().warning("\n" +
                    "A sound from the 'createDefaults' method call inside one of your plugins is not available " +
                    "in your server version. Please set the sounds manually using the template classes (as seen in the docs)." + "\n" +
                    "You may use this resource as help: https://helpch.at/docs/" +
                    Bukkit.getServer().getVersion().split("MC: ")[1].split("\\)")[0] +
                    "/overview-summary.html"
            );
        }

        SUFFIX_SUCCESS = "Success! §7»";
        SUFFIX_WARNING = "Warning! §7»";
        SUFFIX_ERROR = "Error! §7»";
        return this;
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull String message) {
        send(sender, COLOR_DEFAULT, message, null);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull char colorCode, @NotNull String message) {
        send(sender, colorCode, message, null);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, COLOR_DEFAULT, message, hoverText);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull char colorCode, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, colorCode, message, hoverText);
    }

    private void send(CommandSender sender, char colorCode, String message, HoverText hoverText) {
        if (colorCode == '\u0000') {
            colorCode = COLOR_DEFAULT;
        }

        if (sender == null || message == null) {
            throw new NullPointerException("The sendInfo method requires parameter 'sender' and 'message' to not be null. The message was not created.");
        }

        TextComponent textContainer = new TextComponent();
        String suffix = new String();

        if (colorCode == COLOR_SUCCESS) suffix = SUFFIX_SUCCESS;
        if (colorCode == COLOR_WARNING) suffix = SUFFIX_WARNING;
        if (colorCode == COLOR_ERROR) suffix = SUFFIX_ERROR;

        if (addSpacing) sender.sendMessage("");

        String text = new String();

        if (prefix != null) text += prefix + " ";
        if (suffix != null) text += "§" + colorCode + suffix + " ";
        if (seperateLine) text += "\n";

        text += "§" + colorCode + message;
        textContainer.setText(text);

        if (hoverText != null) {
            textContainer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText.retrieve()).create()));
        }

        sender.spigot().sendMessage(textContainer);

        if (addSpacing) sender.sendMessage("");

        if (!(sender instanceof Player player)) {
            return;
        }

        Location location = player.getLocation();
        if (SOUND_SUCCESS != null && colorCode == COLOR_SUCCESS) {
            player.playSound(location, SOUND_SUCCESS, VOLUME_SUCCESS != null ? VOLUME_SUCCESS : 1, 1);
        }
        if (SOUND_WARNING != null && colorCode == COLOR_WARNING) {
            player.playSound(location, SOUND_WARNING, VOLUME_WARNING != null ? VOLUME_WARNING : 1, 1);
        }
        if (SOUND_ERROR != null && colorCode == COLOR_ERROR) {
            player.playSound(location, SOUND_ERROR, VOLUME_ERROR != null ? VOLUME_ERROR : 1, 1);
        }
    }
}