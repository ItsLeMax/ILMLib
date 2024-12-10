# ILMLib

Adds useful methods for Minecraft plugin developers to spare time and repetitive code
![1 0 0-methods](https://github.com/user-attachments/assets/5ba9accf-274b-4c66-881f-fccdba0842bd)

## Setup

1. Download the latest jar file [here](https://github.com/ItsLeMax/ILMLib/releases/latest).\
![1 0 0-download](https://github.com/user-attachments/assets/67d8dd0e-159c-4439-a517-07217bb8c2a6)
2. Put it into the plugins folder of your server.\
![plugins](https://github.com/user-attachments/assets/f220cbd4-c510-4441-803a-7ff7abd101b3)
3. Open your IDE (IntelliJ in this example) and navigate to either `File > Project Structure > Global Libraries` or inside your project alone to `Libraries`.\
![1 0 0-project_structure](https://github.com/user-attachments/assets/2e0169b9-df05-4503-a7ec-4ab9185cfa03)
4. Add a new Library with the plus sign and choose `Java`.\
![1 0 0-library](https://github.com/user-attachments/assets/8fc6fd00-9873-472a-8bea-a7c482c1b19f)
5. Navigate to the library jar file, select it and press `OK`.
![1 0 0-select](https://github.com/user-attachments/assets/17b1b5c5-a327-4eba-abab-495e4517e88a)
6. Click on `Apply`.\
![1 0 0-apply](https://github.com/user-attachments/assets/21bcba00-332d-479c-9290-b4cc5d1cc956)

## Libraries and their supported Minecraft versions

| Library    | Description                                                                      | Version     |
| ---------- | -------------------------------------------------------------------------------- | ----------- |
| ConfigLib  | Allows the easy creation and management of `yml` files, both prefilled and empty | `1.5-1.20+` |
| MessageLib | lets you send messages with a pre-determined unified design                      | `1.8-1.20+` |
| ItemLib    | Creates items easily without the need of having to extract the item meta         | `???`       |

## Documentation for the latest version

> Initializing the library

```java
new ILMLib(JavaPlugin plugin) -> ILMLib
```

`plugin` is one from `onEnable`.

## ConfigLib

### Class

> Gets the ConfigLib class with its methods

```java
#getConfigLib() -> ConfigLib
```

### Methods

> Sets the path of the plugin config folder (allows path traversal)

```java
#setPluginFolderPath(String pluginFolderPath) -> void
```

`pluginFolderPath` is mentioned path.

> [!WARNING]
> Call this method first or it will not take effect.

> Creates configs

```java
#createDefaults(String... configNames) -> ConfigLib
```

> Creates configs inside a directory

```java
#createInsideDirectory(String directoryName, String... configNames) -> ConfigLib
```

`directoryName` is one of the folder.\
`configNames` are those of the configurations, strings seperated by a comma.

### Prefilled configs

The above methods will create empty configs except if they do already exist in the same path inside your projects `resources`.
If so, their content will be copied. Using `createDefaults` requires the configs to exist inside `resources` directly, while
`createInsideDirectory` requires an additional folder with the configs inside with the same name as mentioned in the method call.

### Using the prefilled configs to load languages

You can fill the configs with language keys and values. A Method for loading language Strings is part of this library.
If you want to use other language files besides the default `en_US.yml` (not provided), you need a custom `config.yml`.
Create it inside `resources` or any sub directory of your project if it does not exist yet.
Add a key called `language` and assign it the language you want to use (i.e. `en_US` without `.yml`).
Use `createDefaults` (or `createInsideDirectory` if the file is inside a sub directory) with parameter `fileNames`
being at least `config` to create it on server start.

> Gets a config

```java
#getConfig(String configName) -> FileConfiguration
```

> Gets a config file

```java
#getFile(String configName) -> File
```

> Saves a config

```java
#save(String configName) -> void
```

`configName` is one for the configuration.

> Loads a language key

```java
#lang(String path) -> String
```

`path` is one of the config content, seperated by dots.

### Summarizing example

Your folder structure could look like this:

```as
src.main.java
├  de.max.plugin.init.main
├  ...
resources
├  language_data
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
 debug: sout erreicht.
```

...`storage.yml`:

```yaml
badWordsEnabled: true
badWords: [damn, darn it]
boop: false
```

...and `config.yml`:

```yaml
language: de_DE
```

...while having an initialization like this:

```java
public static ConfigLib configLib;

@Override
public void onEnable() {
    configLib = new ILMLib(this).getConfigLib();
    configLib
        // plugin folder created one directory above inside server folder
        .setPluginFolderPath(this.getServer().getWorldContainer())
        // ...
        .createDefaults("config", "storage")
        // language yml files
        .createInsideDirectory("language_data", "de_DE", "en_US");
}
```

...with this language call inside a different class:

```java
import static de.max.plugin.init.Main.configLib;

// sends "Hallo Welt!"
Bukkit.getConsoleSender().sendMessage(configLib.lang("general.initial"));
```

...getting a file and config like this:

```java
File storageFile = configLib.getFile("storage");
FileConfiguration storageConfig = configLib.getConfig("storage");

if (storageConfig.getBoolean("badWordsEnabled")) {
    Bukkit.getConsoleSender().sendMessage("Filter up and running.");
}

storageConfig.set("myBelovedBoolean", true);

// Feel free to use the file however you want.
storageFile.*;
```

...and last but not least saving it:

```java
configLib.save("storage");
```

## MessageLib

### Class

> Gets the MessageLib class with its methods

```java
#getMessageLib() -> MessageLib
```

### Methods

> Creates an empty line before and after the message, thus creating space

```java
#addSpacing() -> MessageLib
```

> Sets a prefix message that gets shown right before the information

```java
#setPrefix(String prefix) -> MessageLib
```
```java
#setPrefix(String prefix, boolean seperateLine) -> MessageLib
```

`prefix` is a message you want as prefix.
`seperateLine` creates a new one for the message prefix alone when set to `true`.

### Different types of messages

```java
messageLib.setPrefix("Plugin Info >");
```

Using this method will create a message like this:
| Plugin Info > This is an information. |
| ------------------------------------- |

A space will be added after the prefix automatically.

```java
messageLib.setPrefix("Plugin Info:", true);
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

### Templates

There are a few templates to choose from:

| template     | default color  | default sound                |
| ------------ | -------------- | ---------------------------- |
| Success      | a (green)      | ENTITY_EXPERIENCE_ORB_PICKUP |
| Warning/Info | e (yellow)     | UI_BUTTON_CLICK              |
| Danger       | c (red)        | BLOCK_ANVIL_PLACE            |

Using one of the colors from above will determine the sound played automatically.

> Allows to overwrite default colors and sound values for specified templates

| Method                                         | Parameters                                | Return value  |
| ---------------------------------------------- | ----------------------------------------- | ------------- |
| setSuccess() <br> setWarning() <br> setError() | char colorCode <br> Sound sound <br> both | -> MessageLib |

`colorCode` describes the single character one from Minecraft.\
`sound` is one played to a player when the message gets send.

Color codes can be seen here:

| Color        | Code | Hex aquivalent |
| ------------ | -    | -------------- |
| Black        | 0    | `#000000`      |
| Dark Blue    | 1    | `#0000AA`      |
| Dark Green   | 2    | `#00AA00`      |
| Dark Aqua    | 3    | `#00AAAA`      |
| Dark Red     | 4    | `#AA0000`      |
| Dark Purple  | 5    | `#AA00AA`      |
| Gold         | 6    | `#FFAA00`      |
| Gray         | 7    | `#AAAAAA`      |
| Dark Gray    | 8    | `#555555`      |
| Blue         | 9    | `#5555FF`      |
| Green        | a    | `#55FF55`      |
| Aqua         | b    | `#55FFFF`      |
| Red          | c    | `#FF5555`      |
| Purple       | d    | `#FF55FF`      |
| Yellow       | e    | `#FFFF55`      |
| White        | f    | `#FFFFFF`      |

You may also use formatting:

| Description   | Formatting Code |
| ------------- | --------------- |
| Obfuscated    | k               |
| Bold          | l               |
| Strikethrough | m               |
| Underline     | n               |
| Italic        | o               |
| Reset         | r               |

> Creates defaults for mentioned templates

```java
#createDefaultSounds() -> MessageLib
```

> [!NOTE]
> If you choose to set the defaults manually using the template classes, you need to call this method first.
> It will overwrite your settings otherwise.

> Generates a message using the specifications from earlier or default values

```java
#sendInfo(CommandSender sender, char colorCode, String message) -> void
```
```java
#sendInfo(CommandSender sender, String message) -> void
```

`sender` is the `commandSender` (console) or casted player `((Player) commandSender)`.\
`colorCode`, as defined above, is '7' (gray) if skipped.\
`message` is one that the player is supposed to see.

### Summarizing example

The initialization may look like this:

```java
public static MessageLib messageLib;

@Override
public void onEnable() {
    messageLib = new ILMLib(this).getMessageLib();
    messageLib
        .addSpacing()
        .setPrefix("§e§lFPM §7§l>", true)
        .setSuccess('2', Sound.BLOCK_NOTE_BLOCK_BELL)
        .setWarning('6', Sound.ITEM_TRIDENT_RIPTIDE_1)
        .setError('4', Sound.ENTITY_SKELETON_HORSE_HURT);
}
```

...and creating messages inside other classes is as easy as this:

```java
import static de.max.plugin.init.Main.messageLib;

@Override
public boolean onCommand(@NotNull CommandSender sender /* and so on */) {
    // COLOR_SUCCESS causes ENTITY_EXPERIENCE_ORB_PICKUP to play as seen above
    messageLib.sendInfo((Player) sender, messageLib.COLOR_SUCCESS, "Client created successfully.");
}
```