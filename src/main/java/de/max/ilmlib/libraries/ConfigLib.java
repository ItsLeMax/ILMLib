package de.max.ilmlib.libraries;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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
     * @see #set(JavaPlugin, String)
     */
    public ConfigLib setPlugin(@NotNull JavaPlugin plugin) {
        set(plugin, null);
        return this;
    }

    /**
     * @see #set(JavaPlugin, String)
     */
    public ConfigLib setPlugin(@NotNull JavaPlugin plugin, String pluginFolderPath) {
        set(plugin, pluginFolderPath);
        return this;
    }

    /**
     * Legt das Plugin und dessen Standardpfad fest
     * <p>
     * Sets the plugin and its default path
     *
     * @param pluginFolderPath Dateipfad zum vorgesehenen Ordner des Plugins
     *                         <p>
     *                         File path to the planned folder of the plugin
     * @author Kurty00, ItsLeMax
     */
    private void set(JavaPlugin plugin, String pluginFolderPath) {
        this.plugin = plugin;

        if (pluginFolderPath != null) {
            this.pluginFolderPath = pluginFolderPath;
        }
    }

    /**
     * Entnimmt die verlangte Configdatei der HashMap
     * <p>
     * Retrieves the config file from the HashMap
     *
     * @return File Datei <p> File file
     * @author ItsLeMax
     * @see #configs
     */
    public File getFile(@NotNull String configName) {
        return (File) configs.get(configName).get("file");
    }

    /**
     * Entnimmt die verlangte Config der HashMap
     * <p>
     * Retrieves the config from the HashMap
     *
     * @return FileConfiguration Config <p> FileConfiguration Konfiguration
     * @author ItsLeMax
     * @see #configs
     */
    public FileConfiguration getConfig(@NotNull String configName) {
        return (FileConfiguration) configs.get(configName).get("configuration");
    }

    /**
     * Speichert eine Config
     * <p>
     * Saves a config
     *
     * @author ItsLeMax
     */
    public void save(@NotNull String configName) {
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
    public String lang(@NotNull String path) {
        String language = getConfig("config").getString("language");
        FileConfiguration config = getConfig(language);

        if (config == null) {
            config = getConfig("en_US");
        }

        return config.getString(path) == null ? "§rtext_not_found" : config.getString(path);
    }


    /**
     * @see #create(String, String...)
     */
    public ConfigLib createDefaults(@NotNull String... configNames) {
        create(null, configNames);
        return this;
    }

    /**
     * @see #create(String, String...)
     */
    public ConfigLib createInsideDirectory(@NotNull String folderName, @NotNull String... configNames) {
        create(folderName, configNames);
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
    private void create(String directoryName, String... fileNames) {
        if (plugin == null) {
            throw new NullPointerException("The file creation methods of ConfigLib require parameter 'plugin' to not be null. Use method #setPlugin accordingly.");
        }

        if (pluginFolderPath == null) {
            pluginFolderPath = plugin.getDataFolder().toString();
        }

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