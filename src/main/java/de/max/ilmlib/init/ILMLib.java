package de.max.ilmlib.init;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class ILMLib extends JavaPlugin {
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