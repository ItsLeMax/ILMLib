package de.max.ilmlib.init;

import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.ItemLib;
import de.max.ilmlib.libraries.MessageLib;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("all")
public class ILMLib {
    private JavaPlugin plugin;

    private ConfigLib configLib;
    private MessageLib messageLib;
    private ItemLib itemLib;

    /**
     * Initialisiert die Bibliothek und Variablen
     * <p>
     * Initializes the library and variables
     *
     * @author Kurty00
     * @see <a href="https://github.com/itslemax/ilmlib">GitHub Repository</a>
     */
    public ILMLib(JavaPlugin plugin) {
        this.plugin = plugin;

        configLib = new ConfigLib(plugin);
        messageLib = new MessageLib();
        itemLib = new ItemLib();
    }

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
     * Gibt eine Instanz der Klasse MessageLib wieder
     * <p>
     * returns an instance of the class MessageLib
     *
     * @return MessageLib
     * @author ItsLeMax
     */
    public MessageLib getMessageLib() {
        return this.messageLib;
    }

    /**
     * Gibt eine Instanz der Klasse ItemLib wieder
     * <p>
     * returns an instance of the class ItemLib
     *
     * @return ItemLib
     * @author ItsLeMax
     */
    public ItemLib getItemLib() {
        return this.itemLib;
    }
}