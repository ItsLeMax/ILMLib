package de.max.ilmlib.init;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class ILMLib extends JavaPlugin {

    /**
     * 1. "plugin" darf offenbar nicht static (und vielleicht auch nicht public) sein, da
     * die Bibilothek sich sonst selbst überschreibt, wenn diese über mehrere Plugins verwendet
     * wird (e.g. "Plugin geladen"-Nachricht überall gleich).
     * <p>
     * 2. Beim erstmaligen Initialisieren der Konfigurationsdateien kommt durch ein übereiltes
     * Laden gewisser Methoden ein Fehler zustande, welcher beim Neustart permanent verschwindet.
     * Dieser ist hier zu sehen:
     * <pre>
     * java.lang.NullPointerException: Cannot invoke "java.util.HashMap.get(Object)" because
     * the return value of "java.util.HashMap.get(Object)" is null
     * at de.max.ilmlib.init.ConfigLib.getConfig(ConfigLib.java:42) ~[ILMLib.jar:?]
     * at de.max.ilmlib.init.ConfigLib.lang(ConfigLib.java:88) ~[ILMLib.jar:?]
     * at de.max.mobilecrafting.inventories.Recipe.register(Recipe.java:31) ~[MobileCrafting-1.20_v1.1.jar:?]
     * <pre>
     * - Die Zeilen weichen -womöglich- leicht ab, der Fehlercode ist einige Änderungen abseits.
     * - Die Methode des letzten "at" wird ausgeführt, obwohl laut Code diese erst
     *   nach dem Initialisieren der Bibliothek ausgeführt werden sollte, also
     *   - annehmbar - wenn alles initialisiert ist?
     *   Das Problem ließ sich nicht mal lösen mit 5 Sekunden Delay via
     *   Bukkit.getScheduler().scheduleSyncDelayedTask() in meinen Plugins
     *   und ist somit offenbar eines der Library.
     * Es hängt vielleicht mit der Standard "config.yml" zusammen. Das Problem
     * bestand, wenn ich mich richtig entsinne, nicht, als ich saveDefaultConfig() genutzt habe.
     */

    public static JavaPlugin plugin;

    /**
     * @see #set(JavaPlugin, String, String)
     */
    public static void init(final JavaPlugin plugin) {
        set(plugin);
    }

    /**
     * @see #set(JavaPlugin, String, String)
     */
    public static void init(final JavaPlugin plugin, String... options) {
        if (options.length > 2) {
            throw new RuntimeException("The ILMLib initializer does not allow more than two options.");
        }

        set(plugin, options.length >= 1 ? options[0] : null, options.length == 2 ? options[1] : null);
    }

    /**
     * @see #set(JavaPlugin, String, String)
     */
    private static void set(JavaPlugin plugin) {
        ILMLib.plugin = plugin;
    }

    /**
     * Initialisiert die Bibliothek
     * <p>
     * Initializes the library
     * <pre>
     *     set(this);
     *     set(this, plugin.getServer().getWorldContainer());
     *     set(this, plugin.getServer().getWorldContainer(), "MyPlugin");
     * </pre>
     *
     * @param pluginFolderPath individueller Pfad zum Pluginordner, in welchem die Konfigurationsdateien gespeichert werden
     *                         <p>
     *                         individual path to the plugin folder, in which the configs are stored
     * @param pluginFolderName individueller Name des Pluginordners
     *                         <p>
     *                         individual name of the pluginfolder
     * @author ItsLeMax
     * @see <a href="https://github.com/itslemax/ilmlib">GitHub Repository</a>
     */
    private static void set(JavaPlugin plugin, String pluginFolderPath, String pluginFolderName) {
        ILMLib.plugin = plugin;

        if (pluginFolderPath != null) ConfigLib.pluginFolderPath = pluginFolderPath;
        if (pluginFolderName != null) ConfigLib.pluginFolderName = pluginFolderName;
    }
}