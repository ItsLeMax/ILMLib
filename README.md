# ILMLib

Adds useful methods for minecraft plugin developers to spare time and repetitive code

## Setup

1. Download the latest jars [here](https://github.com/ItsLeMax/ILMLib/releases/latest)
2. Put them into the plugins folder of your server
3. Open your IDE (IntelliJ in this example) and navigate to either
   `File > Project Structure > Global Libraries` or inside your project alone to `Libraries`\
   ![project_structure](https://github.com/ItsLeMax/ILMLib/assets/80857459/74ea10a3-f8ba-4af3-8d9b-03a89a0e31b5)
4. Add a new Library with the plus sign and choose `Java`\
   ![new_library](https://github.com/ItsLeMax/ILMLib/assets/80857459/9a90cc22-b008-42fb-9973-0f1c9a9a45d3)
5. Navigate to the library jar file, select it and press `OK`
6. Click on `Apply`

## Documentation for the latest version

> Initializing the library

```java
new ILMLib(JavaPlugin plugin) -> void
new ILMLib(JavaPlugin plugin, String pluginFolderPath) -> void
```

`plugin` is the plugin from `onEnable`.\
`pluginFolderPath` is a custom path you can choose for your plugin configs folder.\

### ConfigLib: Class

> Get the ConfigLib class with its Methods

```java
#getConfigLib() -> ConfigLib
```

### ConfigLib: Methods

> Get a config

```java
#getConfig(String configName) -> FileConfiguration
```

> Get a config file

```java
#getFile(String configName) -> File
```

> Save a config

```java
#save(String configName) -> void
```

`configName` is the name of the config as text

> Load a language key

```java
#lang(String path) -> String
```

`path` is the path of the config content, seperated by a dot

> Create config files

```java
#createDefaults(String... fileNames) -> ConfigLib
```

> Create config files inside a directory

```java
#createInsideDirectory(String directoryName, String... fileNames) -> ConfigLib
```

`directoryName` is the name of the directory
`fileNames` are the names of the configs, seperated by a comma

### ConfigLib: Practical usage

Lets start with the initialization.

```java
public static ConfigLib configLib;

@Override
public void onEnable() {
    configLib = new ILMLib(plugin).getConfigLib();
}
```

You can now use the methods listed above.

### ConfigLib: Creating empty *.yml config files to store data

```java
configLib
    .createDefaults("file_1", "file_2");
    .createInsideDirectory("languages", "file_3", "file_4");
```

### ConfigLib: Prefilled configs

The above methods will create empty configs except if they do already exist in the same path inside your projects `resources`.
If so, their content will be copied. Using `createDefaults` requires the configs to exist inside `resources` directly, while
`createInsideDirectory` requires an additional folder with the configs inside with the same name as mentioned in the method call.

### ConfigLib: Using the prefilled configs to load languages

You can fill the configs with language keys and values. A Method for loading language Strings is part of this library.

```java
configLib.lang("path.to.key");
```

If you want to use other language files besides the default `en_US.yml` (not provided), you need a custom `config.yml`.
Create it inside `resources` or any sub directory of your project if it does not exist yet.
Add a key called `language` and assign it the language you want to use (i.e. `en_US` without `.yml`).
Use `createDefaults` (or `createInsideDirectory` if the file is inside a sub directory) with parameter `fileNames`
being at least `config` to create it on server start.

### ConfigLib: Summarizing Example

Your folder structure could look like this:

```
src.main.java
├  de.max.plugin.main
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
    configLib = new ILMLib(this, plugin.getServer().getWorldContainer()).getConfigLib();
    configLib
        .createDefaults("config", "storage");
        .createInsideDirectory("language_data", "de_DE", "en_US");
}
```

...with this language call inside a different class:

```java
import static de.max.storageterminal.init.StorageTerminal.configLib;

Bukkit.getConsoleSender().sendMessage(configLib.lang("general.initial")); // sends "Hallo Welt!"
```

...getting a File and Config like this:

```java
File storageFile = configLib.getFile("storage");
FileConfiguration storageConfig = configLib.getConfig("storage");

if (storageConfig.getBoolean("badWordsEnabled")) {
    Bukkit.getConsoleSender().sendMessage("Filter up and running.")
}

storageConfig.set("myBelovedBoolean", true);

// Feel free to use the file however you want.
storageFile.*
```

...and last but not least saving it:

```java
configLib.save("storage");
```