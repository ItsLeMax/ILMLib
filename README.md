# ILMLib

Adds useful methods for minecraft plugin developers to spare time and repetitive code

## Setup

1. Download the latest jar [here](https://github.com/ItsLeMax/ILMLib/releases/latest)
2. Put it into the plugins folder of your server
3. Open your IDE (IntelliJ in this example) and navigate to `File > Project Structure > Libraries`\
   ![project_structure](https://github.com/ItsLeMax/ILMLib/assets/80857459/74ea10a3-f8ba-4af3-8d9b-03a89a0e31b5)
4. Add a new Library with the plus sign and choose `Java`\
   ![new_library](https://github.com/ItsLeMax/ILMLib/assets/80857459/9a90cc22-b008-42fb-9973-0f1c9a9a45d3)
5. Navigate to the library jar file, select it and press `OK`
6. Click on `Apply`

## Documentation

`configName` is the name of the config as text

> Initializing the library

```java
ILMLib.init(JavaPlugin plugin) -> void
ILMLib.init(JavaPlugin plugin, String path, String subFolderName) -> void
```

`plugin` is the plugin from `onEnable`.\
`path` is a custom path you can choose for your plugin configs folder.\
`subFolderName` is the name of the folder inside the plugin configs folder.

> Get a config

```java
ConfigLib.getConfig(String configName) -> FileConfiguration
```

> Get a config file

```java
ConfigLib.getFile(String configName) -> File
```

> Save a config

```java
ConfigLib.save(String configName) -> void
```

> Load a language key

```java
ConfigLib.lang(String path) -> String
```

`path` is the path of the config content, seperated by a dot

> Create config files

```java
ConfigLib.create(String... names) -> void
```

`names` are the names of the configs, seperated by a comma

## Practical usage

Lets start with the initialization.

```java
@Override
public void onEnable() {
    ILMLib.init(this);
}
```

You can now use the methods listed above.

### Creating empty *.yml config files to store data

```java
ConfigLib.create("file_1", "file_2", "file_3");
```

### Prefilled configs

The above method will create empty configs except if they do already exist in a special folder. If so, their content
will be copied. To create configs that are filled you need to create a folder inside `resources` of your project.
It should be called `generated`. A custom name like `languages` is possible and requires an additional parameter in
the `ILMLib.init` method call. Create a yml file in the newly created folder and write data into it.

### Using the prefilled configs to load languages

You can fill the configs with language keys and values.
A Method for loading language Strings is part of this library.
If you want to use other language files besides the default `en_US.yml` (not provided), you need to have a config.yml.
Create it inside the `resources` folder of your project if it does not exist yet.
Add a key called `language` and assign it the language you want to use (i.e. `en_US` without `.yml`).
Use `saveDefaultConfig();` – optimally in your `onEnable` method – to create it on server start.
Use the following method call to load a language key value:

```java
ConfigLib.lang("path.to.key");
```

### Summarizing Example

Your folder structure could look like this:

```
src.main.java
├   de.max.plugin.main
├   ...
resources
├   LANGUAGE_FILES
├   ├   storage.yml
├   ├   de_DE.yml
├   └   en_US.yml
├   config.yml
└   plugin.yml
```

...with `de_DE.yml` content:

```yaml
general:
 initial: Hallo!
 error: Ein Fehler ist aufgetreten.
 debug: sout erreicht.
```

...`storage.yml`:

```yaml
admin:
 ...
default:
  prefix: "§4Admin §8| §7"
  permissions: []
  users: []
```

...and `config.yml`:

```yaml
language: de_DE
```

...while having a method call like this:

```java
@Override
public void onEnable() {
    saveDefaultConfig();

    ILMLib.init(this, getDataFolder() + "../", "LANGUAGE_FILES");
    ConfigLib.create("storage", "de_DE", "en_US");
}
```

You can now load a language String as such:

```java
ConfigLib.lang("general.initial"); // returns "Hallo!"
```

...get a File and Config like this:

```java
File storageFile = getFile("storage");
FileConfiguration storageConfig = getConfig("storage");

Player player = event.getPlayer();
storageConfig.put("default.users", player.getUniqueId());

// Feel free to use the file however you want.
storageFile.*
```

...and last but not least save it:

```java
save("storage");
```