package de.max.ilmlib.libraries;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@SuppressWarnings("all")
public class ItemLib {
    private ItemStack item;
    private ItemMeta meta;

    /**
     * Erstellt ein ItemStack mitsamt ItemMeta, bereit zur Bearbeitung
     * <p>
     * Creates an ItemStack with ItemMeta, ready to edit
     *
     * @param amount Anzahl der Items im Stack
     *               <p>
     *               Amount of items of the stack
     */
    public ItemLib create(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
        return this;
    }

    /**
     * Setzt den Namen des ItemStack
     * <p>
     * Sets the name of the ItemStack
     *
     * @param displayName Display-Name des Items
     *                    <p>
     *                    Display name of the item
     */
    public ItemLib setName(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    /**
     * Setzt die Loren des ItemStack
     * <p>
     * Sets the lores of the ItemStack
     *
     * @param lore Loren bzw. Untertitel
     *             <p>
     *             Lores or subtitles
     */
    public ItemLib setLore(String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Gibt das Item wieder
     * <p>
     * Returns the item
     *
     * @return ItemStack item
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}