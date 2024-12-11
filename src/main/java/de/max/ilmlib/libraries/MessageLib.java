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

    private final char FORMATTING_DEFAULT = '7';

    public static char FORMATTING_SUCCESS;
    public static char FORMATTING_WARNING;
    public static char FORMATTING_ERROR;

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
        FORMATTING_SUCCESS = 'a';
        FORMATTING_WARNING = 'e';
        FORMATTING_ERROR = 'c';

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
        send(sender, FORMATTING_DEFAULT, message, null);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull char formattingCode, @NotNull String message) {
        send(sender, formattingCode, message, null);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, FORMATTING_DEFAULT, message, hoverText);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull char formattingCode, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, formattingCode, message, hoverText);
    }

    private void send(CommandSender sender, char formattingCode, String message, HoverText hoverText) {
        if (formattingCode == '\u0000') {
            formattingCode = FORMATTING_DEFAULT;
        }

        if (sender == null || message == null) {
            throw new NullPointerException("The sendInfo method requires parameter 'sender' and 'message' to not be null. The message was not created.");
        }

        TextComponent textContainer = new TextComponent();
        String suffix = new String();

        if (formattingCode == FORMATTING_SUCCESS) suffix = SUFFIX_SUCCESS;
        if (formattingCode == FORMATTING_WARNING) suffix = SUFFIX_WARNING;
        if (formattingCode == FORMATTING_ERROR) suffix = SUFFIX_ERROR;

        if (addSpacing) sender.sendMessage("");

        String text = new String();

        if (prefix != null) text += prefix + " ";
        if (suffix != null) text += "§" + formattingCode + suffix + " ";
        if (seperateLine) text += "\n";

        text += "§" + formattingCode + message;
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
        if (SOUND_SUCCESS != null && formattingCode == FORMATTING_SUCCESS) {
            player.playSound(location, SOUND_SUCCESS, VOLUME_SUCCESS != null ? VOLUME_SUCCESS : 1, 1);
        }
        if (SOUND_WARNING != null && formattingCode == FORMATTING_WARNING) {
            player.playSound(location, SOUND_WARNING, VOLUME_WARNING != null ? VOLUME_WARNING : 1, 1);
        }
        if (SOUND_ERROR != null && formattingCode == FORMATTING_ERROR) {
            player.playSound(location, SOUND_ERROR, VOLUME_ERROR != null ? VOLUME_ERROR : 1, 1);
        }
    }
}