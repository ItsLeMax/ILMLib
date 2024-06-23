package de.max.ilmlib.init;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class ILMLib extends JavaPlugin {
    public static JavaPlugin plugin;

    /**
     * @see #set(JavaPlugin, String, String)
     */
    public static void init(final JavaPlugin plugin) {
        set(plugin, null, null);
    }

    /**
     * @see #set(JavaPlugin, String, String)
     */
    public static void init(final JavaPlugin plugin, String path, String subFolderName) {
        set(plugin, path, subFolderName);
    }

    /**
     * Initialisiert die Bibliothek
     * <p>
     * Initializes the library
     *
     * @param path          individueller Pfad zum Pluginordner, in welchem die Konfigurationsdateien gespeichert werden
     *                      <p>
     *                      individual path to the plugin folder, in which the configs are stored
     * @param subFolderName individueller Unterordnername inmitten des Pluginordners
     *                      <p>
     *                      individual sub folder name inside the plugin folder
     * @author ItsLeMax
     */
    private static void set(JavaPlugin plugin, String path, String subFolderName) {
        ILMLib.plugin = plugin;

        ConfigLib.pluginFolderPath = path != null
                ? path
                : plugin.getDataFolder().toString();

        ConfigLib.subFolderName = subFolderName != null
                ? subFolderName
                : "generated";
    }
}