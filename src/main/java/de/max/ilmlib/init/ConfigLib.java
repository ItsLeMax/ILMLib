package de.max.ilmlib.init;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Objects;

@SuppressWarnings("all")
public class ConfigLib {
    private static HashMap<String, HashMap<String, Object>> configs = new HashMap<>();
    public static File path;
    public static String folderName;

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
        return (FileConfiguration) configs.get(configName).get("config");
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
        String type = data instanceof File ? "file" : data instanceof FileConfiguration ? "config" : null;

        configs.put(configName, new HashMap<>());
        configs.get(configName).put(type, data);
    }

    /**
     * Speichert eine Config
     * <p>
     * Saves a config
     *
     * @author ItsLeMax
     */
    public static void save(String config) {
        try {
            getConfig(config).save(getFile(config));
        } catch (IOException error) {
            throw new RuntimeException(error);
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
        FileConfiguration config = getConfig(ILMLib.plugin.getConfig().getString("language"));

        if (config == null) {
            config = getConfig("en_US");
        }

        return config.getString(path) == null ? "§c§lError in language file" : config.getString(path);
    }

    /**
     * Erstellt die Konfigurationsdateien (mitsamt Ordner) sofern nicht vorhanden
     * <p>
     * Creates the configuration files (with folder) if they do not exist
     * <p>
     * createConfigs("storage", "de_DE", "en_US", "custom_lang");
     *
     * @param params Dateien, welche erstellt werden sollen
     *               <p>
     *               Files, that should be created
     * @author ItsLeMax, Spigot
     * @link <a href="https://spigotmc.org/wiki/config-files/">Spigot Wiki</a>
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void create(String... params) {
        for (String file : params) {
            initialize(file, new HashMap<>() {{
                put("file", null);
                put("config", null);
            }});
        }

        File customConfigsFolder = new File(path, folderName);
        if (!customConfigsFolder.exists()) customConfigsFolder.mkdirs();

        for (String file : configs.keySet()) {
            String customConfig = folderName + "/" + file + ".yml";
            String fileName = customConfig.split("/")[1];

            boolean isFileToCopy = ILMLib.plugin.getResource(customConfig) != null;
            String filePath = isFileToCopy ? customConfig : fileName;

            File configFile = new File(path, filePath);

            String dataFolder = path.toString();
            if (isFileToCopy) dataFolder += "/" + fileName;
            initialize(file, new File(dataFolder));

            try {
                if (!configFile.exists()) {
                    if (isFileToCopy) {
                        Files.copy(Objects.requireNonNull(ILMLib.plugin.getResource(filePath)), configFile.toPath());
                    } else {
                        configFile.createNewFile();
                    }
                }
            } catch (IOException error) {
                throw new RuntimeException(error);
            }

            initialize(file, configFile);
            initialize(file, YamlConfiguration.loadConfiguration(configFile));
        }
    }
}