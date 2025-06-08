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
 *
 * @author ItsLeMax, Kurty00
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class ConfigLib {

    private final HashMap<String, HashMap<String, Object>> configs = new HashMap<>();

    private final JavaPlugin instance;
    private final Logger logger;

    private String pluginFolderPath;

    public ConfigLib(@NotNull final JavaPlugin instance) {
        this.instance = instance;
        this.logger = instance.getLogger();
    }

    /**
     * Sets the path of the plugin folder
     *
     * @param pluginFolderPath Plugin folder path to the plugin folder destiny
     * @author Kurty00
     * @since 1.1.0
     */
    public void setPluginFolderPath(@NotNull final String pluginFolderPath) {
        this.pluginFolderPath = pluginFolderPath;
    }

    /**
     * Retrieves the config file from the HashMap
     *
     * @return File
     * @author ItsLeMax
     * @see #configs
     * @since 1.0.0
     */
    public File getFile(@NotNull final String configName) {
        return (File) configs.get(configName).get("file");
    }

    /**
     * Retrieves the config from the HashMap
     *
     * @return FileConfiguration Config
     * @author ItsLeMax
     * @see #configs
     * @since 1.0.0
     */
    public FileConfiguration getConfig(@NotNull final String configName) {
        return (FileConfiguration) configs.get(configName).get("configuration");
    }

    /**
     * Saves a config
     *
     * @author ItsLeMax
     * @since 1.0.0
     */
    public void saveConfig(@NotNull final String configName) {

        final FileConfiguration config = getConfig(configName);
        final File file = getFile(configName);

        try {
            config.save(file);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    /**
     * Initializes a variable of the HashMap
     *
     * @param data Data as File or Config
     * @author ItsLeMax
     * @see #configs
     * @since 1.0.0
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
     *
     * @param path Path to the text in the language configs
     * @return String with text in the chosen language
     * @author ItsLeMax
     * @since 1.0.0
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
    public ConfigLib createDefaultConfigs(@NotNull final String... configNames) {
        create(null, configNames);
        return this;
    }

    /**
     * @see #create(String, String...)
     */
    public ConfigLib createConfigsInsideDirectory(@NotNull final String folderName, @NotNull final String... configNames) {
        create(folderName, configNames);
        return this;
    }

    /**
     * Creates the configuration files (with folder) if they do not exist
     *
     * @param subDirectoryName Sub directory name if present
     * @param fileNames        File names of files that should be created
     * @author ItsLeMax, Spigot
     * @link <a href="https://spigotmc.org/wiki/config-files/">Spigot Wiki</a>
     * @since 1.0.0
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