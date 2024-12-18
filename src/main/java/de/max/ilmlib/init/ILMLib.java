package de.max.ilmlib.init;

import de.max.ilmlib.libraries.ConfigLib;
import de.max.ilmlib.libraries.MessageLib;

@SuppressWarnings("all")
public class ILMLib {
    private ConfigLib configLib;
    private MessageLib messageLib;

    /**
     * Macht die Bibliothek zugänglich für die Getter
     * <p>
     * Makes the libraries accessable for the getters
     *
     * @author Kurty00
     * @see <a href="https://github.com/itslemax/ilmlib">GitHub Repository</a>
     */
    public ILMLib() {
        configLib = new ConfigLib();
        messageLib = new MessageLib();
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
}