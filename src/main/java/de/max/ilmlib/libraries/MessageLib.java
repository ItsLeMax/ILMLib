package de.max.ilmlib.libraries;

import de.max.ilmlib.init.HoverText;
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

@SuppressWarnings("all")
public class MessageLib {
    private boolean addSpacing;
    private String prefix;
    private boolean seperateLine;

    private HashMap<Template, HashMap<String, Object>> templateData = new HashMap<>();

    public enum Template {
        SUCCESS,
        WARNING,
        ERROR,
        INFO
    }

    /**
     * Initialisiert die Templates
     * <p>
     * Initializes the templates
     *
     * @author ItsLeMax
     */
    public MessageLib() {
        for (Template template : Template.values()) {
            templateData.put(template, new HashMap() {{
                put("formatting", null);
                put("sound", null);
                put("volume", null);
                put("suffix", null);
            }});
        }
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
     * @see #set(String, Boolean)
     */
    public MessageLib setPrefix(@NotNull String prefix) {
        set(prefix, null);
        return this;
    }

    /**
     * @see #set(String, Boolean)
     */
    public MessageLib setPrefix(@NotNull String prefix, @NotNull boolean seperateLine) {
        set(prefix, seperateLine);
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
     * @param seperateLine Soll eine Extrazeile belegt werden?
     *                     <p>
     *                     Should an additional line be used?
     * @author ItsLeMax
     */
    private void set(String prefix, Boolean seperateLine) {
        this.prefix = prefix;
        this.seperateLine = seperateLine;
    }

    /**
     * Setzt die Standardwerte aller Templates
     * <p>
     * Sets the default values of every template
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
     * Setzt den Formatierungscode zur Anwendung in den Templates
     * <p>
     * Sets the formatting code for usage inside the templates
     *
     * @param formattingCode Formatierungscode, welcher gesetzt werden soll
     *                       <p>
     *                       Formatting code that is supposed to be set
     * @author ItsLeMax
     * @see #setSuffix(Template, String)
     */
    public MessageLib setFormattingCode(@NotNull Template template, @NotNull Character formattingCode) {
        templateData.get(template).put("formatting", formattingCode);
        return this;
    }

    /**
     * @param formattingCodes Formatierungscodes mehrerer Templates
     *                        <p>
     *                        Formatting codes of mulitple templates
     * @see #setFormattingCode(Template, Character)
     */
    public MessageLib setFormattingCode(HashMap<Template, Character> formattingCodes) {
        for (Map.Entry<Template, Character> entry : formattingCodes.entrySet()) {
            setFormattingCode(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * Setzt den Ton zur Anwendung in den Templates
     * <p>
     * Sets the sound for usage inside the templates
     *
     * @param sound Sound, welcher gesetzt werden soll
     *              <p>
     *              Sound that is supposed to be set
     * @author ItsLeMax
     * @see #setSuffix(Template, String)
     */
    public MessageLib setSound(@NotNull Template template, @NotNull Sound sound) {
        templateData.get(template).put("sound", sound);
        return this;
    }

    /**
     * @param volume Lautstärke, welche gesetzt werden soll
     *               <p>
     *               Volume that is supposed to be set
     * @see #setSound(Template, Sound)
     */
    public MessageLib setSound(@NotNull Template template, @NotNull Sound sound, @NotNull Float volume) {
        setSound(template, sound);
        templateData.get(template).put("volume", volume);
        return this;
    }

    /**
     * @param sound Sound mehrerer Templates
     *              <p>
     *              Sound of mulitple templates
     * @see #setSound(Template, Sound)
     */
    public MessageLib setSound(HashMap<Template, Sound> sound) {
        for (Map.Entry<Template, Sound> entry : sound.entrySet()) {
            setSound(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * Setzt den Suffix zur Anwendung in den Templates
     * <p>
     * Sets the suffix for usage inside the templates
     *
     * @param template Template zum Überschreiben
     *                 <p>
     *                 Template to overwrite
     * @param suffix   Suffix, welcher gesetzt werden soll
     *                 <p>
     *                 Suffix that is supposed to be set
     * @author ItsLeMax
     */
    public MessageLib setSuffix(@NotNull Template template, @NotNull String suffix) {
        templateData.get(template).put("suffix", suffix);
        return this;
    }

    /**
     * @param suffix Suffix mehrerer Templates
     *               <p>
     *               Suffix of mulitple templates
     * @see #setSuffix(Template, String)
     */
    public MessageLib setSuffix(HashMap<Template, String> suffix) {
        for (Map.Entry<Template, String> entry : suffix.entrySet()) {
            setSuffix(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * @see #send(CommandSender, Object, String, HoverText)
     */
    public void sendInfo(@NotNull CommandSender sender, @NotNull String message) {
        send(sender, null, message, null);
    }

    /**
     * @see #send(CommandSender, Object, String, HoverText)
     */
    public void sendInfo(@NotNull CommandSender sender, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, null, message, hoverText);
    }

    /**
     * @see #send(CommandSender, Object, String, HoverText)
     */
    public void sendInfo(@NotNull CommandSender sender, @NotNull char formattingCode, @NotNull String message) {
        send(sender, formattingCode, message, null);
    }

    /**
     * @see #send(CommandSender, Object, String, HoverText)
     */
    public void sendInfo(@NotNull CommandSender sender, @NotNull char formattingCode, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, formattingCode, message, hoverText);
    }

    /**
     * @see #send(CommandSender, Object, String, HoverText)
     */
    public void sendInfo(@NotNull CommandSender sender, @NotNull Enum template, @NotNull String message) {
        send(sender, template, message, null);
    }

    /**
     * @see #send(CommandSender, Object, String, HoverText)
     */
    public void sendInfo(@NotNull CommandSender sender, @NotNull Enum template, @NotNull String message, @NotNull HoverText hoverText) {
        send(sender, template, message, hoverText);
    }

    /**
     * Sendet eine schematische Nachricht an einen Benutzer
     * <p>
     * Sends a message with scheme to a user
     *
     * @param sender       Sendender, Spieler oder Konsole
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
        if (sender == null || message == null) {
            throw new NullPointerException("The #sendInfo method of MessageLib requires parameter 'sender' and 'message' to not be null. Use it accordingly.");
        }

        if (formatOrEnum == null || (!(formatOrEnum instanceof Template) && !(formatOrEnum instanceof Character))) {
            formatOrEnum = '7';
        }

        TextComponent textContainer = new TextComponent();

        Object formatting = null;
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

        String text = new String();

        if (prefix != null) text += prefix + " ";
        text += "§" + formatting;
        if (suffix != null) text += suffix + " ";
        if (seperateLine) text += "\n";
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

        if (!(sender instanceof Player player) || sound == null) {
            return;
        }

        player.playSound(player.getLocation(), sound, volume != null ? volume : 1, 1);
    }
}