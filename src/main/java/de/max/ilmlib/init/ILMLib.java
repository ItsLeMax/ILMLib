package de.max.ilmlib.init;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("all")
public class ILMLib {
    private JavaPlugin plugin;
    private ConfigLib configLib;

    /**
     * Gibt eine Instanz der Klasse ConfigLib wieder
     * <p>
     * returns an instance of the class ConfigLib
     *
     * @return ConfigLib
     * @author Kurty00
     */
    public ConfigLib getConfigLib() {
        return this.configLib;
    }

    /**
     * @see #ILMLib(JavaPlugin, String)
     */
    public ILMLib(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        configLib = new ConfigLib(plugin);
    }

    /**
     * Initialisiert die Bibliothek und Variablen
     * <p>
     * Initializes the library and variables
     * <pre>
     *     ILMLib(this);
     *     ILMLib(this, plugin.getServer().getWorldContainer());
     * </pre>
     *
     * @param pluginFolderPath individueller Pfad zum Pluginordner, in welchem die Konfigurationsdateien gespeichert werden
     *                         <p>
     *                         individual path to the plugin folder, in which the configs are stored
     * @author Kurty00, ItsLeMax
     * @see <a href="https://github.com/itslemax/ilmlib">GitHub Repository</a>
     */
    public ILMLib(JavaPlugin javaPlugin, String pluginFolderPath) {
        this(javaPlugin);
        configLib.setPluginFolderPath(pluginFolderPath);
    }
}