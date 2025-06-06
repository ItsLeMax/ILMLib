# ILMLib

Adds useful methods for Minecraft plugin developers to spare time and repetitive code
![1 0 0-methods](https://github.com/user-attachments/assets/91bdd179-2eb4-431b-9e66-58059b80999f)

## Purpose of this library

I am aware that there do indeed exist a ton of other libraries but I had the urge to create one myself for the
experience and individual preferences I was able to implement.
I decided to make it public if other people are interested.
> One goal kept in mind while developing this library was to make it <b>compatible with most of the Minecraft
> versions</b>, including future releases.

## Sub-libraries and their supported Minecraft versions

| Library    | Description                                                                                              | Theoretical Compatibilty | Tested                           |
|------------|----------------------------------------------------------------------------------------------------------|--------------------------|----------------------------------|
| ConfigLib  | Allows the easy creation and management of `yml` config/storage files, both prefilled and empty          | `1.5`-`1.21+`            | `1.12.2`<br>`1.20.*`<br>`1.21.4` |
| MessageLib | Lets you send messages with a pre-determined unified design and templates for human errors, success etc. | `1.8`-`1.21+`            | `1.12.2`<br>`1.20.*`<br>`1.21.4` |
| ItemLib    | Creates items easily without the need of having to extract the item meta                                 | `1.8`-`1.21+`            | `1.12.2`<br>`1.20.*`<br>`1.21.4` |

## Setup

### JAR file:

1. Download the latest jar file [here](https://github.com/ItsLeMax/ILMLib/releases/latest).\
   ![1 0 0-download](https://github.com/user-attachments/assets/67d8dd0e-159c-4439-a517-07217bb8c2a6)
2. Open your IDE (IntelliJ in this example) and navigate to either `File > Project Structure > Global Libraries` or
   inside your project alone to `Libraries`.\
   ![1 0 0-project_structure](https://github.com/user-attachments/assets/2e0169b9-df05-4503-a7ec-4ab9185cfa03)
3. Add a new Library with the plus sign and choose `Java`.\
   ![1 0 0-library](https://github.com/user-attachments/assets/8fc6fd00-9873-472a-8bea-a7c482c1b19f)
4. Navigate to the library jar file, select it and press `OK`.
   ![1 0 0-select](https://github.com/user-attachments/assets/17b1b5c5-a327-4eba-abab-495e4517e88a)
5. Click on `Apply`.\
   ![1 0 0-apply](https://github.com/user-attachments/assets/21bcba00-332d-479c-9290-b4cc5d1cc956)

### Maven:

1. Open your IDE (IntelliJ in this example) and open the `pom.xml` file inside your project\
   ![1 2 1-pom](https://github.com/user-attachments/assets/4cdae496-53b5-4c6c-85a6-7c221412f704)
2. Add the following to the `repositories` section:

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

3. Add this to the `dependencies` section:

```xml
<dependency>
    <groupId>com.github.itslemax</groupId>
    <artifactId>ilmlib</artifactId>
    <version>1.2.2</version> <!-- change as desired. -->
</dependency>
```

## Providing the library

You can do one of the following to get your plugin to work:

- put the jar file into the plugins folder of your server\
  ![plugins](https://github.com/user-attachments/assets/f220cbd4-c510-4441-803a-7ff7abd101b3)
- shade the plugin by setting this section into your `pom.xml` and build the plugin using `mvn package` (and optionally
  `clean`; with the result being inside your projects `/target/` by default):

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.4.1</version> <!-- change as desired. -->
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <shadedArtifactAttached>false</shadedArtifactAttached>
                        <minimizeJar>true</minimizeJar>
                        <relocations>
                            <relocation>
                                <pattern>de.fpm_studio.ilmlib</pattern>
                                <shadedPattern>de.fpm_studio.shaded.ilmlib</shadedPattern>
                            </relocation>
                        </relocations>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

# Documentation for the latest version

## ConfigLib

### Main class

> Gets the ConfigLib class with its methods

```java
new ConfigLib(JavaPlugin: plugin) -> ConfigLib
```

`plugin` is one from `onEnable` required for config creation.

### Methods

> [!WARNING]
> Call this method before creating configs or it will not take effect.

> Sets the path of the plugin config folder (allows path traversal)

```java
#setPluginFolderPath(String: pluginFolderPath) -> ConfigLib
```

`pluginFolderPath` is one of the used operating system (relative paths are allowed, i.e. `../../PluginData`).

> Creates configs

```java
#createDefaultConfigs(String...: configNames) -> ConfigLib
```

`configNames` are those of the configurations, strings seperated by a comma.

> Creates configs inside a directory

```java
#createConfigsInsideDirectory(String: directoryName, String...: configNames) -> ConfigLib
```

`directoryName` is one of the folder.

> Gets a config

```java
#getConfig(String: configName) -> FileConfiguration
```

`configName` is one for the configuration.

> Gets a config file

```java
#getFile(String: configName) -> File
```

> Saves a config

```java
#saveConfig(String: configName) -> void
```

> Loads a language key

```java
#text(String: path) -> String
```

`path` is one of the config content, seperated by dots.

### Prefilled configs

The above methods will create empty configs except if they do already exist in the same path inside your projects
`resources`. If so, their content will be copied. Using `createDefaultConfigs` requires the configs to exist inside
`resources` directly, while `createConfigsInsideDirectory` requires an additional folder with the configs inside with
the same name as mentioned in the method call.

### Using the prefilled configs to load languages

You can fill the configs with language keys and values. A Method for loading language Strings is part of this library.
If you want to use other language files besides the default `en_US.yml` (not provided), you need a custom `config.yml`.
Create it inside `resources` or any sub directory of your project if it does not exist yet.
Add a key called `language` and assign it the language you want to use (i.e. `de_DE` without `.yml`).
Use `createDefaultConfigs` (or `createConfigsInsideDirectory` if the file is inside a sub directory) with parameter
`fileNames` being at least `config` to create it on server start.

### Summarizing example

Your folder structure could look like this:

```as
src.main.java
├  de.max.plugin.init.Main
├  ...
resources
├  languages
├  ├  de_DE.yml
├  └  en_US.yml
├  config.yml
├  plugin.yml
└  storage.yml
```

...with `de_DE.yml`:

```yaml
general:
  initial: Hallo Welt!
  error: Ein Fehler ist aufgetreten.
```

...`storage.yml`:

```yaml
badWordsEnabled: true
badWords: [ damn, darn it ]
```

...and `config.yml`:

```yaml
language: de_DE
```

...while having an initialization like this:

```java
public ConfigLib configLib;

@Override
public void onEnable() {

    configLib = new ConfigLib(this)
            // plugin folder created one directory above / inside the server folder
            .setPluginFolderPath(this.getServer().getWorldContainer())
            // basic config files
            .createDefaultConfigs("config", "storage")
            // language config files
            .createConfigsInsideDirectory("languages", "de_DE", "en_US");

    // Passing configLib to another class (alternative would be using static imports)
    new UtilClass(configLib);

}
```

...with this language call inside a different class:

```java
private final ConfigLib configLib;

public UtilClass(@NotNull final ConfigLib configLib) {
    this.configLib = configLib;
}

final String initialMessage = configLib.text("general.initial");

// sends "Hallo Welt!"
Bukkit.getConsoleSender().sendMessage(initialMessage);
```

...getting a file and config like this:

```java
final File storageFile = configLib.getFile("storage");
final FileConfiguration storageConfig = configLib.getConfig("storage");

if (storageConfig.getBoolean("badWordsEnabled")) {
    Bukkit.getConsoleSender().sendMessage("Filter up and running.");
}

storageConfig.set("myBelovedBoolean", true);

// Feel free to use the file however you want
storageFile.*;
```

...and last but not least saving it:

```java
configLib.saveConfig("storage");
```

## MessageLib

### Main class

> Gets the MessageLib class with its methods

```java
new MessageLib() -> MessageLib
```

### Methods

> Creates an empty line before and after any message

```java
#addSpacing() -> MessageLib
```

> Sets a prefix message that gets shown right before any message

```java
#setPrefix(String: prefix, boolean?: seperateLine) -> MessageLib
```

`prefix` is a message you want as prefix.\
`seperateLine` creates a new one for the message prefix alone when set to `true`.

> Creates default values for templates (format/color, sound and suffix, as seen later)

```java
#createTemplateDefaults() -> MessageLib
```

> [!WARNING]
> If you choose to set the default values manually, you need to call these methods after `#createTemplateDefaults()`.
> It will overwrite your settings otherwise.

> Allows to overwrite the default format code for the messages

```java
#setFormattingCode(Template: template, char: formattingCode) -> MessageLib
```

```java
#setFormattingCode(HashMap: formattingCodes) -> MessageLib
```

`template` is an enum one you can use.\
`formattingCode` describes a single character one from Minecraft.\
`formattingCodes` are multiple inside a map with content `<Template, Character>`.

> Allows to overwrite the default sound played to players when sending a message

```java
#setSound(Template: template, Sound: sound, Float?: volume) -> MessageLib
```

```java
#setSound(HashMap: sounds) -> MessageLib
```

`sound` is one played to a player when a message gets send.\
`volume` is the playback loudness.\
`sounds` are multiple inside a map with content `<Template, Sound>`.

> Allows to overwrite the default suffix shown right after the prefix

```java
#setSuffix(Template: template, String: suffix) -> MessageLib
```

```java
#setSuffix(HashMap: suffixes) -> MessageLib
```

`suffix` is an additional text.\
`suffixes` are multiple inside a map with content `<Template, String>`.

> Generates a message using the specifications from earlier and template or custom values

```java
#sendInfo(CommandSender: sender, char?: formattingCode | Template?: template, String: message, HoverText?: hoverText) -> void
```

`sender` is either a console or player.\
`message` is one that the player is supposed to see.\
`hoverText` is one showing when the mouse cursor is above the message using a special class.

### Message variety

```java
new MessageLib().setPrefix("Plugin Info >");
```

Using this method will create a message like this:
| Plugin Info > This is an information. |
| ------------------------------------- |

A space will be added after the prefix automatically.

```java
new MessageLib().setPrefix("Plugin Info:", true);
```

The method with `seperateLine` set to `true` creates one like this:
| Plugin Info:            |
| :---------------------- |
| This is an information. |

If `addSpacing()` was called before, the message would look like this:
| ⠀                       |
| ----------------------- |
| **Plugin Info:**        |
| This is an information. |
| ⠀                       |

### Formatting codes

Color codes can be seen here:

| Color       | Code | Hex aquivalent |
|-------------|------|----------------|
| Black       | 0    | `#000000`      |
| Dark Blue   | 1    | `#0000AA`      |
| Dark Green  | 2    | `#00AA00`      |
| Dark Aqua   | 3    | `#00AAAA`      |
| Dark Red    | 4    | `#AA0000`      |
| Dark Purple | 5    | `#AA00AA`      |
| Gold        | 6    | `#FFAA00`      |
| Gray        | 7    | `#AAAAAA`      |
| Dark Gray   | 8    | `#555555`      |
| Blue        | 9    | `#5555FF`      |
| Green       | a    | `#55FF55`      |
| Aqua        | b    | `#55FFFF`      |
| Red         | c    | `#FF5555`      |
| Purple      | d    | `#FF55FF`      |
| Yellow      | e    | `#FFFF55`      |
| White       | f    | `#FFFFFF`      |

You may also use non color formatting:

| Description   | Formatting code |
|---------------|-----------------|
| Obfuscated    | k               |
| Bold          | l               |
| Strikethrough | m               |
| Underline     | n               |
| Italic        | o               |
| Reset         | r               |

### Templates default values

| Template class (`MessageLib.Template.*`) | Default format | Default sound                | Default suffix |
|------------------------------------------|----------------|------------------------------|----------------|
| SUCCESS                                  | a (green)      | ENTITY_EXPERIENCE_ORB_PICKUP | `Success! §7»` |
| WARNING                                  | e (yellow)     | UI_BUTTON_CLICK              | `Warning! §7»` |
| ERROR                                    | c (red)        | BLOCK_ANVIL_PLACE            | `Error! §7»`   |
| INFO                                     | 9 (blue)       | *None*                       | `Info! §7»`    |
| *Neither*                                | 7 (gray)       | *None*                       | *None*         |

### Summarizing example

The initialization may look like this:

```java
private MessageLib messageLib;

@Override
public void onEnable() {

    messageLib = new MessageLib()
            .addSpacing()
            .setPrefix("§e§lFPM §7§l>", true)
            .createTemplateDefaults()
            // setting (or in this case overwriting due to #createTemplateDefaults) the color of the info template alone
            .setFormattingCode(MessageLib.Template.INFO, '3')
            // same with the sound for the info template, additionally half as loud
            .setSound(MessageLib.Template.INFO, Sound.ENTITY_ENDERMAN_TELEPORT, .5f)
            // overwriting the suffix of multiple templates using a HashMap
            .setSuffix(new HashMap<>() {{
                put(MessageLib.Template.SUCCESS, "[✔]");
                put(MessageLib.Template.WARNING, "[⚠]");
                put(MessageLib.Template.ERROR, "[!]");
            }});

    // Passing messageLib to another class (alternative would be using static imports)
    getCommand("customcommand").setExecutor(new CustomCommand(messageLib));

}
```

...and creating messages inside other classes is as easy as this:

```java
private final MessageLib messageLib;

public CustomCommand(@NotNull final MessageLib messageLib) {
    this.messageLib = messageLib;
}

@Override
public boolean onCommand(@NotNull final CommandSender sender /* and so on */) {

    // Sending a simple red message without template to the console
    messageLib.sendInfo(Bukkit.getConsoleSender(), 'c', "Command executed.");

    // Sending a message using a template
    if (COMMAND_DISABLED) {
        // MessageLib.Template.INFO from the custom value above uses '3' as color and causes ENTITY_ENDERMAN_TELEPORT to play
        messageLib.sendInfo(sender, MessageLib.Template.INFO, "This command was disabled by the author.", new HoverText("§7Contact an administrator for more details."));
        return true;
    }

    if (sender instanceof Player) {
        // MessageLib.Template.SUCCESS from *.createTemplateDefaults uses 'a' as color and causes ENTITY_EXPERIENCE_ORB_PICKUP to play
        messageLib.sendInfo((Player) sender, MessageLib.Template.SUCCESS, "Client created successfully.");
    }

    return true;

}
```

## ItemLib

### Main class

> Gets the ItemLib class with its methods

```java
new ItemLib() -> ItemLib
```

### Methods

> Sets material and amount of the item

```java
#setItem(Material: material, int?: amount) -> ItemLib
```

```java
#setItem(ItemStack: item) -> ItemLib
```

`material` is one you want the item to have.\
`amount` is one of a stack.\
`item` is an existing one you can use to edit

> Sets the title of the item

```java
#setName(String: name) -> ItemLib
```

`name` is the text of the item

> Sets the subtext of the item

```java
#setLore(String...: lore | List: lore) -> ItemLib
```

`lore` is the sub text below the name with content `<String>`, visible inside any inventory

> Gets the currently stored item meta

```java
#getItemMeta() -> ItemMeta
```

> Returns the item to use

```java
#create() -> ItemStack
```

### Flexibility

You can imagine the ItemLib class as an item builder:
One can create items using the methods until it is finished using `create()`, meaning it is possible
to call the editor later again and continue where you stopped before without using variables to store.
This way you have the option to use if statements instead of using unpleasent ternary operators.

```java
@EventHandler
public static void inventoryClick(InventoryClickEvent event) {

    final ItemLib repairAnvilBuilder = new ItemLib();

    // creating a basic item
    repairAnvilBuilder.setItem(Material.ANVIL).setName("§cRepair Anvil");

    if (event.getCurrentItem() == null) return;
    if (event.getCurrentItem().getType().equals(Material.ANVIL) && event.getClick().isRightClick()) {
        // adding an enchantment under certain circumstances
        repairAnvilBuilder.addEnchantment(Enchantment.ARROW_INFINITE, true);
    }

    // finalized item with #setItem, #setName and, if true, #addEnchantment
    final ItemStack repairAnvil = repairAnvilBuilder.create();

}
```

### Summarizing example

You can create an item like this:

```java
final ItemStack compass = new ItemLib()
        // four cookies
        .setItem(Material.COOKIE, 4)
        .setName("§dGrandmas Cookie")
        .setLore("§7Made with love. §c❤")
        // Sharpness IV, not visible as usual below lore
        .addEnchantment(Enchantment.SHARPNESS, 4, true)
        .create();
```