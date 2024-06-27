package de.max.ilmlib.init;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;

@SuppressWarnings("all")
public class ConfigLib {
    public static HashMap<String, HashMap<String, Object>> configs = new HashMap<>();

    public static String pluginFolderPath = ILMLib.plugin.getDataFolder().toString();
    public static String pluginFolderName = ILMLib.plugin.getName();

    /**
     * Entnimmt die verlangte Configdatei der HashMap
     * <p>
     * Retrieves the config file from the HashMap
     *
     * @return Datei <p> File
     * @author ItsLeMax
     * @see #configs
     */
    public static File getFile(String configName) {
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
    public static FileConfiguration getConfig(String configName) {
        return (FileConfiguration) configs.get(configName).get("configuration");
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
    private static void initialize(String configName, Object data) {
        configs.get(configName).put(data instanceof File ? "file" : data instanceof FileConfiguration ? "configuration" : "initialize_error", data);
    }

    /**
     * Speichert eine Config
     * <p>
     * Saves a config
     *
     * @author ItsLeMax
     */
    public static void save(String configName) {
        try {
            getConfig(configName).save(getFile(configName));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
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
    public static String lang(String path) {
        String language = getConfig("config").getString("language");
        FileConfiguration config = getConfig(language);
        if (config == null) config = getConfig("en_US");

        return config.getString(path) == null ? "§c§lError" : config.getString(path);
    }


    /**
     * @see #create(String, String...)
     */
    public static void createDefaults(String... fileNames) {
        create(null, fileNames);
    }

    /**
     * @see #create(String, String...)
     */
    public static void createInsideDirectory(String folderName, String... fileNames) {
        create(folderName, fileNames);
    }

    /**
     * Erstellt die Konfigurationsdateien (mitsamt Ordner) sofern nicht vorhanden
     * <p>
     * Creates the configuration files (with folder) if they do not exist
     *
     * @param folderName Unterordner
     *                   <p>
     *                   Sub directory
     * @param fileNames  Dateien, welche erstellt werden sollen
     *                   <p>
     *                   Files, that should be created
     * @author ItsLeMax, Spigot
     * @link <a href="https://spigotmc.org/wiki/config-files/">Spigot Wiki</a>
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void create(String folderName, String... fileNames) {
        for (String fileName : fileNames) {
            String filePath = fileName + ".yml";
            if (folderName != null) filePath = folderName + "/" + filePath;

            File newlyCreatedConfig = new File(pluginFolderPath, filePath);

            configs.put(fileName, new HashMap<>());
            initialize(fileName, newlyCreatedConfig);
            initialize(fileName, YamlConfiguration.loadConfiguration(newlyCreatedConfig));

            if (newlyCreatedConfig.exists()) continue;
            if (!newlyCreatedConfig.getParentFile().exists()) {
                newlyCreatedConfig.getParentFile().mkdirs();
            }

            try {
                InputStream configFromResources = ILMLib.plugin.getResource(filePath);

                if (configFromResources != null) {
                    Files.copy(configFromResources, newlyCreatedConfig.toPath());
                    continue;
                }

                newlyCreatedConfig.createNewFile();
            } catch (IOException ioException) {
                throw new RuntimeException(ioException);
            }
        }
    }
}