package de.fpm_studio.ilmlib.libraries;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Contains methods to create simple configurations
 * <br>
 * Birgt Methoden zum Erstellen von einfachen Konfigurationen
 *
 * @author ItsLeMax, Kurty00
 */
@SuppressWarnings("unused")
public final class ConfigLib {
    private final HashMap<String, HashMap<String, Object>> configs = new HashMap<>();

    private final JavaPlugin instance;
    private final Logger logger;

    private String pluginFolderPath;

    /**
     * Sets the plugin
     * <br>
     * Legt das Plugin fest
     *
     * @author Kurty00
     */
    public ConfigLib(@NotNull final JavaPlugin instance) {
        this.instance = instance;
        this.logger = instance.getLogger();
    }

    /**
     * Sets the path of the plugin folder
     * <br>
     * Setzt den Pfad des Pluginordners
     *
     * @param pluginFolderPath Plugin folder path to the plugin folder destiny
     *                         <br>
     *                         Pluginordnerpfad zum Pluginordnerziel
     * @author Kurty00
     */
    public void setPluginFolderPath(@NotNull final String pluginFolderPath) {
        this.pluginFolderPath = pluginFolderPath;
    }

    /**
     * Retrieves the config file from the HashMap
     * <br>
     * Entnimmt die verlangte Konfigurationsdatei der HashMap
     *
     * @return File <br> File-Datei
     * @author ItsLeMax
     * @see #configs
     */
    public File getFile(@NotNull final String configName) {
        return (File) configs.get(configName).get("file");
    }

    /**
     * Retrieves the config from the HashMap
     * <br>
     * Entnimmt die verlangte Config der HashMap
     *
     * @return FileConfiguration Config <br> FileConfiguration-Konfiguration
     * @author ItsLeMax
     * @see #configs
     */
    public FileConfiguration getConfig(@NotNull final String configName) {
        return (FileConfiguration) configs.get(configName).get("configuration");
    }

    /**
     * Saves a config
     * <br>
     * Speichert eine Config
     *
     * @author ItsLeMax
     */
    public void save(@NotNull final String configName) {
        final FileConfiguration config = getConfig(configName);
        final File file = getFile(configName);

        try {
            config.save(file);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Initializes a variable of the HashMap
     * <br>
     * Initialisiert eine Variable der HashMap
     *
     * @param data Data as File or Config
     *             <br>
     *             Daten als Datei oder Config
     * @author ItsLeMax
     * @see #configs
     */
    private void initialize(@NotNull final String configName, @NotNull final Object data) {
        final String key = data instanceof File ? "file" : data instanceof FileConfiguration ? "configuration" : null;

        if (key == null) {
            throw new NullPointerException("Config " + configName + " couldn't be initialized!");
        }

        configs.get(configName).put(key, data);
    }

    /**
     * Loads text depending on the chosen language inside the config
     * <br>
     * Lädt Text je nach gewählter Sprache in der Config
     *
     * @param path Path to the text in the language configs
     *             <br>
     *             Pfad zum Text in den Sprachconfigs
     * @return String with text in the chosen language <br> String mit Text in der gewählten Sprache
     * @author ItsLeMax
     */
    public String text(@NotNull final String path) {
        final String language = getConfig("config").getString("language");
        assert language != null;

        FileConfiguration config = getConfig(language);

        if (config == null) {
            config = getConfig("en_US");
        }

        if (config.getString(path) == null) {
            logger.warning("Missing string returned while looking for one in the language files!" + "\n" +
                    Arrays.toString(Thread.currentThread().getStackTrace())
            );
        }

        return config.getString(path);
    }


    /**
     * @see #create(String, String...)
     */
    public ConfigLib createDefaults(@NotNull final String... configNames) {
        create(null, configNames);
        return this;
    }

    /**
     * @see #create(String, String...)
     */
    public ConfigLib createInsideDirectory(@NotNull final String folderName, @NotNull final String... configNames) {
        create(folderName, configNames);
        return this;
    }

    /**
     * Creates the configuration files (with folder) if they do not exist
     * <br>
     * Erstellt die Konfigurationsdateien (mitsamt Ordner) sofern nicht vorhanden
     *
     * @param subDirectoryName Sub directory name if present
     *                         <br>
     *                         Unterordnername, gegebenenfalls
     * @param fileNames        File names of files that should be created
     *                         <br>
     *                         Dateinamen für Dateien, welche erstellt werden sollen
     * @author ItsLeMax, Spigot
     * @link <a href="https://spigotmc.org/wiki/config-files/">Spigot Wiki</a>
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void create(final String subDirectoryName, @NotNull final String... fileNames) {
        if (pluginFolderPath == null) {
            pluginFolderPath = instance.getDataFolder().toString();
        }

        for (final String fileName : fileNames) {
            String filePath = fileName + ".yml";

            if (subDirectoryName != null) {
                filePath = subDirectoryName + "/" + filePath;
            }

            final File newlyCreatedConfig = new File(pluginFolderPath, filePath);

            configs.put(fileName, new HashMap<>());
            initialize(fileName, newlyCreatedConfig);
            initialize(fileName, YamlConfiguration.loadConfiguration(newlyCreatedConfig));

            if (newlyCreatedConfig.exists()) {
                continue;
            }

            if (!newlyCreatedConfig.getParentFile().exists()) {
                newlyCreatedConfig.getParentFile().mkdirs();
            }

            try {
                final InputStream configFromResources = instance.getResource(filePath);

                if (configFromResources != null) {
                    Files.copy(configFromResources, newlyCreatedConfig.toPath());
                    initialize(fileName, YamlConfiguration.loadConfiguration(newlyCreatedConfig));
                    continue;
                }

                newlyCreatedConfig.createNewFile();
            } catch (final IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    }
}