package de.max.ilmlib.init;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@SuppressWarnings("unused")
public class ILMLib extends JavaPlugin {
    public static JavaPlugin plugin;

    /**
     * @see #init(JavaPlugin, File, String)
     */
    public static void init(final JavaPlugin plugin) {
        set(plugin);
    }

    /**
     * @see #init(JavaPlugin, File, String)
     */
    public static void init(final JavaPlugin plugin, File path) {
        set(plugin, path);
    }

    /**
     * @see #init(JavaPlugin, File, String)
     */
    public static void init(final JavaPlugin plugin, String subFolderName) {
        set(plugin, subFolderName);
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
    public static void init(final JavaPlugin plugin, File path, String subFolderName) {
        set(plugin, path, subFolderName);
    }

    /**
     * Legt angegebene Werte beim Initialisieren fest
     * <p>
     * Sets given values while initializing
     *
     * @param data JavaPlugin, File, String
     *             <p>
     *             JavaPlugin, Datei, Text
     * @author ItsLeMax
     */
    private static void set(Object... data) {
        for (Object value : data) {
            if (value instanceof JavaPlugin) {
                plugin = (JavaPlugin) value;
            }

            if (value instanceof File) {
                ConfigLib.path = (File) value;
            }

            if (value instanceof String) {
                ConfigLib.folderName = (String) value;
            }
        }

        if (ConfigLib.path == null) {
            ConfigLib.path = plugin.getDataFolder();
        }

        if (ConfigLib.folderName == null) {
            ConfigLib.folderName = "generated";
        }
    }
}