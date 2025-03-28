package de.fpm_studio.ilmlib.libraries;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Contains methods to create items
 * <br>
 * Beinhaltet Methoden zum Erstellen von Items
 *
 * @author ItsLeMax
 */
@SuppressWarnings("unused")
public final class ItemLib {
    private ItemStack lastItem;
    private ItemMeta lastMeta;

    /**
     * @see #item(Material, int)
     */
    public ItemLib setItem(@NotNull final Material material) {
        item(material, 1);
        return this;
    }

    /**
     * @see #item(Material, int)
     */
    public ItemLib setItem(@NotNull final Material material, final int amount) {
        item(material, amount);
        return this;
    }

    /**
     * Creates the fundamentals of editing an already existing item
     * <br>
     * Bildet die Grundlage zum Bearbeiten eines bereits existierenden Items
     *
     * @param item Item to edit
     *             <br>
     *             Item zum Bearbeiten
     * @author ItsLeMax
     */
    public ItemLib setItem(@NotNull final ItemStack item) {
        this.lastItem = item;
        this.lastMeta = item.getItemMeta();
        return this;
    }

    /**
     * Creates an ItemStack with ItemMeta, ready to edit
     * <br>
     * Erstellt ein ItemStack mitsamt ItemMeta, bereit zur Bearbeitung
     *
     * @param material Material that the item is supposed to have
     *                 <br>
     *                 Material, welches das Item haben soll
     * @param amount   Amount of the item
     *                 <br>
     *                 Anzahl der Items
     */
    private void item(@NotNull final Material material, final int amount) {
        this.lastItem = new ItemStack(material, amount);
        this.lastMeta = lastItem.getItemMeta();
    }

    /**
     * Sets the name of the ItemStack
     * <br>
     * Setzt den Namen des ItemStack
     *
     * @param name name of the item
     *             <br>
     *             Name des Items
     */
    public ItemLib setName(@NotNull final String name) {
        lastMeta.setDisplayName(name);
        return this;
    }

    /**
     * @see #setLore(String...)
     */
    public ItemLib setLore(@NotNull final List<String> lore) {
        lastMeta.setLore(lore);
        return this;
    }

    /**
     * Sets the lores of the ItemStack
     * <br>
     * Setzt die Loren des ItemStack
     *
     * @param lores Lores (subtitles)
     *              <br>
     *              Loren (Untertitel)
     */
    public ItemLib setLore(@NotNull final String... lores) {
        lastMeta.setLore(Arrays.asList(lores));
        return this;
    }

    /**
     * @see #enchantment(Enchantment, int, boolean)
     */
    public ItemLib addEnchantment(@NotNull final Enchantment enchantment) {
        enchantment(enchantment, 1, false);
        return this;
    }

    /**
     * @see #enchantment(Enchantment, int, boolean)
     */
    public ItemLib addEnchantment(@NotNull final Enchantment enchantment, final int level) {
        enchantment(enchantment, level, false);
        return this;
    }

    /**
     * @see #enchantment(Enchantment, int, boolean)
     */
    public ItemLib addEnchantment(@NotNull final Enchantment enchantment, final boolean hideNBT) {
        enchantment(enchantment, 1, hideNBT);
        return this;
    }

    /**
     * @see #enchantment(Enchantment, int, boolean)
     */
    public ItemLib addEnchantment(@NotNull final Enchantment enchantment, final int level, final boolean hideNBT) {
        enchantment(enchantment, level, hideNBT);
        return this;
    }

    /**
     * Adds an enchantment to an item
     * <br>
     * Fügt eine Verzauberung zu einem Item hinzu
     *
     * @param enchantment Enchantment to apply
     *                    <br>
     *                    Verzauberung zum Anwenden
     * @param level       Level of the enchantment
     *                    <br>
     *                    Stufe der Verzauberung
     * @param hideNBT     Should the enchantment be hidden while hovering over the item?
     *                    <br>
     *                    Soll die Verzauberung beim Hovern über das Item versteckt sein?
     * @author ItsLeMax
     */
    private void enchantment(@NotNull final Enchantment enchantment, final int level, final boolean hideNBT) {
        lastMeta.addEnchant(enchantment, level, true);

        if (hideNBT) {
            lastMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
    }

    /**
     * Returns the item
     * <br>
     * Gibt das Item wieder
     *
     * @return ItemStack with created item <br> Item-Stack mit erstelltem Item
     */
    public ItemStack create() {
        lastItem.setItemMeta(lastMeta);
        final ItemStack newItem = lastItem;

        lastItem = null;
        lastMeta = null;

        return newItem;
    }

    /**
     * Returns the in the meantime created item meta
     * <br>
     * Entnimmt die zwischenzeitlich erstellte Meta
     *
     * @return Item Meta
     * @author ItsLeMax
     */
    public ItemMeta getItemMeta() {
        return lastMeta;
    }
}