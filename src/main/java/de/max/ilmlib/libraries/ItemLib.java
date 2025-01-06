package de.max.ilmlib.libraries;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

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
    public ItemLib(@NotNull Material material, @NotNull int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = item.getItemMeta();
    }

    /**
     * Setzt den Namen des ItemStack
     * <p>
     * Sets the name of the ItemStack
     *
     * @param name Name des Items
     *             <p>
     *             name of the item
     */
    public ItemLib setName(@NotNull String name) {
        meta.setDisplayName(name);
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
    public ItemLib setLore(@NotNull String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemLib addEnchantment(@NotNull Enchantment enchantment) {
        // WiP
        return this;
    }

    /**
     * Gibt das Item wieder
     * <p>
     * Returns the item
     *
     * @return ItemStack item
     */
    public ItemStack create() {
        item.setItemMeta(meta);
        return item;
    }
}