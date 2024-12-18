package de.max.ilmlib.libraries;

import de.max.ilmlib.utility.HoverText;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
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

    public enum Template {
        SUCCESS,
        WARNING,
        ERROR
    }

    /**
     * Fügt eine leere Zeile zu Beginn und Ende der Nachricht hinzu
     * <p>
     * Adds an empty line at the beginning and ending of the message
     *
     * @author ItsLeMax
     */
    public MessageLib addSpacing() {
        addSpacing = true;
        return this;
    }

    /**
     * @see #setPrefixVariable(String, boolean)
     */
    public MessageLib setPrefix(@NotNull String prefix) {
        setPrefixVariable(prefix, false);
        return this;
    }

    /**
     * @see #setPrefixVariable(String, boolean)
     */
    public MessageLib setPrefix(@NotNull String prefix, @NotNull boolean seperateLine) {
        setPrefixVariable(prefix, seperateLine);
        return this;
    }

    /**
     * Setzt den Präfix der Nachrichten
     * <p>
     * Sets the prefix of the messages
     *
     * @param prefix       Präfix vor der Nachricht
     *                     <p>
     *                     Prefix in front of the message
     * @param seperateLine Soll eine extra Zeile beansprucht werden
     *                     <p>
     *                     Should an additional line be used?
     * @author ItsLeMax
     */
    private void setPrefixVariable(String prefix, boolean seperateLine) {
        this.seperateLine = seperateLine;
        this.prefix = prefix;
    }

    /**
     * Setzt die Standardwerte aller Templates
     * <p>
     * Sets the default values of every template
     *
     * @author ItsLeMax
     */
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
                    "A sound from the #createDefaults method call inside one of your plugins is not available " +
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

    public void sendInfo(@NotNull CommandSender sender, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, FORMATTING_DEFAULT, message, hoverText);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull char formattingCode, @NotNull String message) {
        send(sender, formattingCode, message, null);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull char formattingCode, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, formattingCode, message, hoverText);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull Enum template, @NotNull String message) {
        send(sender, template, message, null);
    }

    public void sendInfo(@NotNull CommandSender sender, @NotNull Enum template, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, template, message, hoverText);
    }

    /**
     * Sendet eine schematische Nachricht an einen Benutzer
     * <p>
     * Sends a message with scheme to a user
     *
     * @param sender       Sender, Spieler oder Konsole
     *                     <p>
     *                     Sender, player or console
     * @param formatOrEnum Farb- oder Formatierungscode von Minecraft oder Template-Enum
     *                     <p>
     *                     Color or formatting code from Minecraft or template enum
     * @param message      Nachricht für eine Person
     *                     <p>
     *                     Message for a person
     * @param hoverText    Hovertext, welcher erscheint mit dem Mauszeiger über dem Text
     *                     <p>
     *                     Hover text, which will show with the mouse cursor above the text
     * @author ItsLeMax
     */
    private void send(CommandSender sender, Object formatOrEnum, String message, HoverText hoverText) {
        if (formatOrEnum == null) {
            formatOrEnum = FORMATTING_DEFAULT;
        }

        if (sender == null || message == null) {
            throw new NullPointerException("The #sendInfo method of MessageLib requires parameter 'sender' and 'message' to not be null. Use it accordingly.");
        }

        TextComponent textContainer = new TextComponent();

        Object colorCode = null;
        String suffix = null;
        Sound sound = null;

        if (formatOrEnum instanceof Enum) {
            switch ((Template) formatOrEnum) {
                case SUCCESS:
                    colorCode = FORMATTING_SUCCESS;
                    suffix = SUFFIX_SUCCESS;
                    sound = SOUND_SUCCESS;
                    break;
                case WARNING:
                    colorCode = FORMATTING_WARNING;
                    suffix = SUFFIX_WARNING;
                    sound = SOUND_WARNING;
                    break;
                case ERROR:
                    colorCode = FORMATTING_ERROR;
                    suffix = SUFFIX_ERROR;
                    sound = SOUND_ERROR;
                    break;
            }
        }

        if (colorCode == null) {
            colorCode = formatOrEnum;
        }

        if (addSpacing) {
            sender.sendMessage("");
        }

        String text = new String();

        if (prefix != null) text += prefix + " ";
        text += "§" + colorCode;
        if (suffix != null) text += suffix + " ";
        if (seperateLine) text += "\n";
        text += "§" + colorCode;
        text += message;

        textContainer.setText(text);

        if (hoverText != null) {
            textContainer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText.retrieve()).create()));
        }

        sender.spigot().sendMessage(textContainer);

        if (addSpacing) {
            sender.sendMessage("");
        }

        if (!(sender instanceof Player player) || sound == null) {
            return;
        }

        player.playSound(player.getLocation(), sound, VOLUME_ERROR != null ? VOLUME_ERROR : 1, 1);
    }
}