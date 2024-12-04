package de.max.ilmlib.libraries;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

@SuppressWarnings("all")
public class ConfigLib {
    private JavaPlugin plugin;
    private String pluginFolderPath;
    private HashMap<String, HashMap<String, Object>> configs = new HashMap<>();

    /**
     * Legt das Plugin und dessen Pfad fest
     * <p>
     * Sets the plugin and its path
     *
     * @author Kurty00
     */
    public ConfigLib(JavaPlugin javaPlugin) {
        plugin = javaPlugin;
        pluginFolderPath = plugin.getDataFolder().toString();
        // System hier hinzufügen, mit welchem ggf. neue Einträge der
        // yml-Dateien des Plugins in den Benutzergenerierten hinzugefügt werden
    }

    /**
     * Setzt den Pfad des Pluginordners
     * <p>
     * Sets the path of the plugin folder
     *
     * @param pluginFolderPath
     * @author Kurty00
     */
    public void setPluginFolderPath(String pluginFolderPath) {
        this.pluginFolderPath = pluginFolderPath;
    }

    /**
     * Entnimmt die verlangte Configdatei der HashMap
     * <p>
     * Retrieves the config file from the HashMap
     *
     * @return Datei <p> File
     * @author ItsLeMax
     * @see #configs
     */
    public File getFile(String configName) {
        return (File) configs.get(configName).get("file");
    }

    /**
     * Entnimmt die verlangte Config der HashMap
     * <p>
     * Retrieves the config from the HashMap
     *
     * @return FileConfiguration Config
     * @author ItsLeMax
     * @see #configs
     */
    public FileConfiguration getConfig(String configName) {
        return (FileConfiguration) configs.get(configName).get("configuration");
    }

    /**
     * Speichert eine Config
     * <p>
     * Saves a config
     *
     * @author ItsLeMax
     */
    public void save(String configName) {
        try {
            getConfig(configName).save(getFile(configName));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    /**
     * Initialisiert eine Variable der HashMap
     * <p>
     * Initializes a variable of the HashMap
     *
     * @param data Datei oder Config
     *             <p>
     *             File or Config
     * @author ItsLeMax
     * @see #configs
     */
    private void initialize(String configName, Object data) {
        configs.get(configName).put(data instanceof File ? "file" : data instanceof FileConfiguration ? "configuration" : "initialize_error", data);
    }

    /**
     * Lädt Text je nach gewählter Sprache in der Config
     * <p>
     * Loads text depending on the chosen language inside the config
     *
     * @param path Pfad zum Text in den Sprachconfigs
     *             <p>
     *             Path to the text in the language configs
     * @return String mit Text in der gewählten Sprache <p> String with text in the chosen language
     * @author ItsLeMax
     */
    public String lang(String path) {
        String language = getConfig("config").getString("language");
        FileConfiguration config = getConfig(language);

        if (config == null) config = getConfig("en_US");
        return config.getString(path) == null ? "§c§lError" : config.getString(path);
    }


    /**
     * @see #create(String, String...)
     */
    public ConfigLib createDefaults(String... fileNames) {
        create(null, fileNames);
        return this;
    }

    /**
     * @see #create(String, String...)
     */
    public ConfigLib createInsideDirectory(String folderName, String... fileNames) {
        create(folderName, fileNames);
        return this;
    }

    /**
     * Erstellt die Konfigurationsdateien (mitsamt Ordner) sofern nicht vorhanden
     * <p>
     * Creates the configuration files (with folder) if they do not exist
     *
     * @param directoryName Unterordnername
     *                      <p>
     *                      Sub directory name
     * @param fileNames     Dateien, welche erstellt werden sollen
     *                      <p>
     *                      Files, that should be created
     * @author ItsLeMax, Spigot
     * @link <a href="https://spigotmc.org/wiki/config-files/">Spigot Wiki</a>
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void create(String directoryName, String... fileNames) {
        for (String fileName : fileNames) {
            String filePath = fileName + ".yml";
            if (directoryName != null) filePath = directoryName + "/" + filePath;

            File newlyCreatedConfig = new File(pluginFolderPath, filePath);

            configs.put(fileName, new HashMap<>());
            initialize(fileName, newlyCreatedConfig);
            initialize(fileName, YamlConfiguration.loadConfiguration(newlyCreatedConfig));

            if (newlyCreatedConfig.exists()) continue;
            if (!newlyCreatedConfig.getParentFile().exists()) {
                newlyCreatedConfig.getParentFile().mkdirs();
            }

            try {
                InputStream configFromResources = plugin.getResource(filePath);

                if (configFromResources != null) {
                    Files.copy(configFromResources, newlyCreatedConfig.toPath());
                    initialize(fileName, YamlConfiguration.loadConfiguration(newlyCreatedConfig));
                    continue;
                }

                newlyCreatedConfig.createNewFile();
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    }
}