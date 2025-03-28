package de.fpm_studio.ilmlib.libraries;

import de.fpm_studio.ilmlib.util.HoverText;
import de.fpm_studio.ilmlib.util.Template;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains several methods to create messages with a unified design
 * <br>
 * Beinhaltet einige Methoden zum Erstellen von Nachrichten mit einheitlichem Design
 *
 * @author ItsLeMax
 */
@SuppressWarnings("unused")
public final class MessageLib {
    private boolean addSpacing;
    private String prefix;
    private boolean seperateLine;

    private final HashMap<Template, HashMap<String, Object>> templateData = new HashMap<>();

    /**
     * Initializes the templates
     * <br>
     * Initialisiert die Templates
     *
     * @author ItsLeMax
     */
    public MessageLib() {
        for (final Template template : Template.values()) {
            templateData.put(template, new HashMap<>() {{
                put("formatting", null);
                put("sound", null);
                put("volume", null);
                put("suffix", null);
            }});
        }
    }

    /**
     * Adds an empty line at the beginning and ending of the message
     * <br>
     * Fügt eine leere Zeile zu Beginn und Ende der Nachricht hinzu
     *
     * @author ItsLeMax
     */
    public MessageLib addSpacing() {
        addSpacing = true;
        return this;
    }

    /**
     * @see #prefix(String, boolean)
     */
    public MessageLib setPrefix(@NotNull final String prefix) {
        prefix(prefix, false);
        return this;
    }

    /**
     * @see #prefix(String, boolean)
     */
    public MessageLib setPrefix(@NotNull final String prefix, final boolean seperateLine) {
        prefix(prefix, seperateLine);
        return this;
    }

    /**
     * Sets the prefix of the messages
     * <br>
     * Setzt den Präfix der Nachrichten
     *
     * @param prefix       Prefix in front of the message
     *                     <br>
     *                     Präfix vor der Nachricht
     * @param seperateLine Should an additional line be used?
     *                     <br>
     *                     Soll eine Extrazeile belegt werden?
     * @author ItsLeMax
     */
    private void prefix(final String prefix, final boolean seperateLine) {
        this.prefix = prefix;
        this.seperateLine = seperateLine;
    }

    /**
     * Sets the default values of every template
     * <br>
     * Setzt die Standardwerte aller Templates
     *
     * @author ItsLeMax
     */
    public MessageLib createDefaults() {
        templateData.get(Template.SUCCESS).put("formatting", 'a');
        templateData.get(Template.WARNING).put("formatting", 'e');
        templateData.get(Template.ERROR).put("formatting", 'c');
        templateData.get(Template.INFO).put("formatting", '9');

        try {
            templateData.get(Template.SUCCESS).put("sound", Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            templateData.get(Template.WARNING).put("sound", Sound.UI_BUTTON_CLICK);
            templateData.get(Template.ERROR).put("sound", Sound.BLOCK_ANVIL_PLACE);
        } catch (NoSuchFieldError ignored) {
            Bukkit.getLogger().warning("\n" +
                    "A sound from the #createDefaults method call inside one of your plugins is not available " +
                    "in your server version. Please set the sounds manually using the template classes (as seen in the docs)." + "\n" +
                    "You may use this resource as help: https://helpch.at/docs/" +
                    Bukkit.getServer().getVersion().split("MC: ")[1].split("\\)")[0] +
                    "/overview-summary.html"
            );
        }

        templateData.get(Template.SUCCESS).put("suffix", "Success! §7»");
        templateData.get(Template.WARNING).put("suffix", "Warning! §7»");
        templateData.get(Template.ERROR).put("suffix", "Error! §7»");
        templateData.get(Template.INFO).put("suffix", "Info! §7»");

        return this;
    }

    /**
     * Sets the formatting code for usage inside the templates
     * <br>
     * Setzt den Formatierungscode zur Anwendung in den Templates
     *
     * @param formattingCode Formatting code that is supposed to be set
     *                       <br>
     *                       Formatierungscode, welcher gesetzt werden soll
     * @author ItsLeMax
     * @see #setSuffix(Template, String)
     */
    public MessageLib setFormattingCode(@NotNull final Template template, final char formattingCode) {
        templateData.get(template).put("formatting", formattingCode);
        return this;
    }

    /**
     * @param formattingCodes Formatting codes of mulitple templates
     *                        <br>
     *                        Formatierungscodes mehrerer Templates
     * @see #setFormattingCode(Template, char)
     */
    public MessageLib setFormattingCode(final HashMap<Template, Character> formattingCodes) {
        for (final Map.Entry<Template, Character> entry : formattingCodes.entrySet()) {
            setFormattingCode(entry.getKey(), entry.getValue());
        }

        return this;
    }

    /**
     * Sets the sound for usage inside the templates
     * <br>
     * Setzt den Ton zur Anwendung in den Templates
     *
     * @param sound Sound that is supposed to be set
     *              <br>
     *              Sound, welcher gesetzt werden soll
     * @author ItsLeMax
     * @see #setSuffix(Template, String)
     */
    public MessageLib setSound(@NotNull final Template template, @NotNull final Sound sound) {
        templateData.get(template).put("sound", sound);
        return this;
    }

    /**
     * @param volume Volume that is supposed to be set
     *               <br>
     *               Lautstärke, welche gesetzt werden soll
     * @see #setSound(Template, Sound)
     */
    public MessageLib setSound(@NotNull final Template template, @NotNull final Sound sound, @NotNull final Float volume) {
        setSound(template, sound);
        templateData.get(template).put("volume", volume);
        return this;
    }

    /**
     * @param sounds Sound of mulitple templates
     *               <br>
     *               Sound mehrerer Templates
     * @see #setSound(Template, Sound)
     */
    public MessageLib setSound(final HashMap<Template, Sound> sounds) {
        for (final Map.Entry<Template, Sound> entry : sounds.entrySet()) {
            setSound(entry.getKey(), entry.getValue());
        }

        return this;
    }

    /**
     * Sets the suffix for usage inside the templates
     * <br>
     * Setzt den Suffix zur Anwendung in den Templates
     *
     * @param template Template to overwrite
     *                 <br>
     *                 Template zum Überschreiben
     * @param suffix   Suffix that is supposed to be set
     *                 <br>
     *                 Suffix, welcher gesetzt werden soll
     * @author ItsLeMax
     */
    public MessageLib setSuffix(@NotNull final Template template, @NotNull final String suffix) {
        templateData.get(template).put("suffix", suffix);
        return this;
    }

    /**
     * @param suffixes Suffix of mulitple templates
     *                 <br>
     *                 Suffix mehrerer Templates
     * @see #setSuffix(Template, String)
     */
    public MessageLib setSuffix(final HashMap<Template, String> suffixes) {
        for (final Map.Entry<Template, String> entry : suffixes.entrySet()) {
            setSuffix(entry.getKey(), entry.getValue());
        }

        return this;
    }

    /**
     * @see #info(CommandSender, Object, String, HoverText)
     */
    public void sendMessage(
            @NotNull final CommandSender sender,
            @NotNull final String message) {
        info(sender, null, message, null);
    }

    /**
     * @see #info(CommandSender, Object, String, HoverText)
     */
    public void sendMessage(
            @NotNull final CommandSender sender,
            @NotNull final String message,
            @NotNull final HoverText hoverText) {
        info(sender, null, message, hoverText);
    }

    /**
     * @see #info(CommandSender, Object, String, HoverText)
     */
    public void sendMessage(
            @NotNull final CommandSender sender,
            final char formattingCode,
            @NotNull final String message) {
        info(sender, formattingCode, message, null);
    }

    /**
     * @see #info(CommandSender, Object, String, HoverText)
     */
    public void sendMessage(
            @NotNull final CommandSender sender,
            final char formattingCode,
            @NotNull final String message,
            @NotNull final HoverText hoverText) {
        info(sender, formattingCode, message, hoverText);
    }

    /**
     * @see #info(CommandSender, Object, String, HoverText)
     */
    public void sendMessage(
            @NotNull final CommandSender sender,
            @NotNull final Template template,
            @NotNull final String message) {
        info(sender, template, message, null);
    }

    /**
     * @see #info(CommandSender, Object, String, HoverText)
     */
    public void sendMessage(@NotNull CommandSender sender, @NotNull Template template, @NotNull String message, @NotNull HoverText hoverText) {
        info(sender, template, message, hoverText);
    }

    /**
     * Sends a message with scheme to a user
     * <br>
     * Sendet eine schematische Nachricht an einen Benutzer
     *
     * @param sender       Sender, player or console
     *                     <br>
     *                     Sendender, Spieler oder Konsole
     * @param formatOrEnum Color or formatting code from Minecraft or template enum
     *                     <br>
     *                     Farb- oder Formatierungscode von Minecraft oder Template-Enum
     * @param message      Message for a person
     *                     <br>
     *                     Nachricht für eine Person
     * @param hoverText    Hover text, which will show with the mouse cursor above the text
     *                     <br>
     *                     Hovertext, welcher erscheint mit dem Mauszeiger über dem Text
     * @author ItsLeMax
     */
    @SuppressWarnings("deprecation")
    private void info(final CommandSender sender, Object formatOrEnum, final String message, final HoverText hoverText) {

        if (sender == null || message == null) {
            throw new NullPointerException("The #sendInfo method of MessageLib requires parameter 'sender' and 'message' to not be null. Use it accordingly.");
        }

        if (!(formatOrEnum instanceof Template) && !(formatOrEnum instanceof Character)) {
            formatOrEnum = '7';
        }

        TextComponent textContainer = new TextComponent();

        final Object formatting;
        String suffix = null;
        Sound sound = null;
        Float volume = null;

        if (formatOrEnum instanceof Enum) {
            formatting = templateData.get(formatOrEnum).get("formatting");
            suffix = (String) templateData.get(formatOrEnum).get("suffix");
            sound = (Sound) templateData.get(formatOrEnum).get("sound");
            volume = (Float) templateData.get(formatOrEnum).get("volume");
        } else {
            formatting = formatOrEnum;
        }

        if (addSpacing) {
            sender.sendMessage("");
        }

        String text = "";

        if (prefix != null) {
            text += prefix + " ";
        }

        text += "§" + formatting;

        if (suffix != null) {
            text += suffix + " ";
        }

        if (seperateLine) {
            text += "\n";
        }

        text += "§" + formatting;
        text += message;

        textContainer.setText(text);

        if (hoverText != null) {
            textContainer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7" + hoverText.retrieve()).create()));
        }

        sender.spigot().sendMessage(textContainer);

        if (addSpacing) {
            sender.sendMessage("");
        }

        if (!(sender instanceof final Player player) || sound == null) {
            return;
        }

        player.playSound(player.getLocation(), sound, volume != null ? volume : 1, 1);
    }
}