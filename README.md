# ILMLib

Adds functions for the plugins of @ItsLeMax

## Purpose

This library makes a few complex processes doable in an ease.

## Setup

1. Download the latest jar [here](https://github.com/ItsLeMax/ILMLib/releases/latest)
2. Put it into the plugins folder of your server
3. Open your IDE and navigate to `File > Project Structure > Artifacts`
4. Add a new Library with the plus sign and choose `Java`
5. Navigate to the library jar file, select it and press `OK`
6. Click on `Apply`

## Documentation
`configName` is the name of the config as text

> Initializing the library
```
ILMLib.init(JavaPlugin plugin)
ILMLib.init(JavaPlugin plugin, File path)
ILMLib.init(JavaPlugin plugin, String subFolderName)
ILMLib.init(JavaPlugin plugin, File path, String subFolderName)
```
`plugin` is the plugin from `onEnable`.\
`path` is a custom path you can choose for your plugin configs folder.\
`subFolderName` is the name of the folder inside the plugin configs folder.

> Get a config from a String
```
FileConfiguration getConfig(String configName)
```

> Get a config file from a String
```
File getFile(String configName)
```

> Save a config
```
void save(String configName)
```

> Load a language key
```
String lang(String path)
```
`path` is the path of the config content, i.e. `general.init` would retrieve:

> Create config files
```
create(String... names)
```
`names` are the names of the configs, seperated by a comma

## Practical usage

Lets start with its initialization.

```
@Override
public void onEnable() {
	ILMLib.init(this);
}
```

You can now use the methods listed above.

### Creating an empty *.yml config file to store data

```
ConfigLib.create("storage", "locations", "logs", "de_DE", "en_US");
```

### Prefilled configs

The above method will create empty configs except if they do already exist in a special folder. If so, their content will be copied.
To create configs that are filled you need to create a folder inside `resources` of your project.
It should be called `generated`. A custom name like `languages` is possible and requires an additional parameter in the `ILMLib.init` method call.
Create a yml file in the newly created folder and write data into it.

### Using the prefilled configs to load languages

You can fill the configs with language keys and values like this:
A Method for loading language Strings is part of this library.
If you want to use other language files besides the default `en_US.yml` (not provided), you need to have a config.yml.
Create it inside the `resources` folder of your project if it does not exist yet.
Add a key called `language` and assign it the language you want to use (i.e. `en_US` without `.yml`).
Use `saveDefaultConfig();`, optimally in your `onEnable` method to create it on server start.
Use the following example method call to load a language key value:
```
ConfigLib.lang("misc.notification");
```

### Summarizing Example

Your folder structure could look like this:
```
resources
├ languages
├ ├ de_DE.yml
├ └ en_US.yml
├ config.yml
└ plugin.yml
```
...with `de_DE.yml` content:
```
general:
 initial: Hallo!
 error: Ein Fehler ist aufgetreten.
 debug: sout erreicht.
```
...and `config.yml`:
```
language: de_DE
```
...while having a method call like this:
```
@Override
public void onEnable() {
	saveDefaultConfig();

	ILMLib.init(this, new File("../"), "languages");
	ConfigLib.create("de_DE", "en_US");
}
```

You can now load a language String as such:
```
ConfigLib.lang("general.initial"); // returns "Hallo!"
```
...get a File and Config like this:
```
File storageFile = getFile("storage");
FileConfiguration storageConfig = getConfig("storage");

Player player = event.getPlayer();
storageConfig.put(player.getUniqueId() + ".name", player.getName());

// Feel free to use the file however you want.
storageFile.*
```
...and last but not least save it:
```
save("storage");
```