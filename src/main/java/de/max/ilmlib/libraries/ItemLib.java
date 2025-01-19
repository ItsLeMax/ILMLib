package de.max.ilmlib.libraries;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class ItemLib {
    private ItemStack lastItem;
    private ItemMeta lastMeta;

    /**
     * @see #item(Material, int)
     */
    public ItemLib setItem(@NotNull Material material) {
        item(material, 1);
        return this;
    }

    /**
     * @see #item(Material, int)
     */
    public ItemLib setItem(@NotNull Material material, @NotNull int amount) {
        item(material, amount);
        return this;
    }

    /**
     * Bildet die Grundlage zum Bearbeiten eines bereits existierenden Items
     * <p>
     * Creates the fundamentals of editing an already existing item
     *
     * @param item Item zum Bearbeiten
     *             <p>
     *             Item to edit
     * @author ItsLeMax
     */
    public ItemLib setItem(@NotNull ItemStack item) {
        this.lastItem = item;
        this.lastMeta = item.getItemMeta();
        return this;
    }

    /**
     * Erstellt ein ItemStack mitsamt ItemMeta, bereit zur Bearbeitung
     * <p>
     * Creates an ItemStack with ItemMeta, ready to edit
     *
     * @param material Material, welches das Item haben soll
     *                 <p>
     *                 Material that the item is supposed to have
     * @param amount   Anzahl des Items
     *                 <p>
     *                 Amount of the item
     */
    private void item(Material material, int amount) {
        this.lastItem = new ItemStack(material, amount);
        this.lastMeta = lastItem.getItemMeta();
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
        lastMeta.setDisplayName(name);
        return this;
    }

    /**
     * @see #setLore(String...)
     */
    public ItemLib setLore(@NotNull List<String> lore) {
        lastMeta.setLore(lore);
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
        lastMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * @see #add(Enchantment, int)
     */
    public ItemLib addEnchantment(@NotNull Enchantment enchantment) {
        add(enchantment, 1, false);
        return this;
    }

    /**
     * @see #add(Enchantment, int)
     */
    public ItemLib addEnchantment(@NotNull Enchantment enchantment, int level) {
        add(enchantment, level, false);
        return this;
    }

    /**
     * @see #add(Enchantment, int)
     */
    public ItemLib addEnchantment(@NotNull Enchantment enchantment, boolean hideNBT) {
        add(enchantment, 1, hideNBT);
        return this;
    }

    /**
     * @see #add(Enchantment, int)
     */
    public ItemLib addEnchantment(@NotNull Enchantment enchantment, int level, boolean hideNBT) {
        add(enchantment, level, hideNBT);
        return this;
    }

    /**
     * Fügt eine Verzauberung zu einem Item hinzu
     * <p>
     * Adds an enchantment to an item
     *
     * @param enchantment Verzauberung zum Anwenden
     *                    <p>
     *                    Enchantment to apply
     * @param level       Level of the enchantment
     *                    <p>
     *                    Stufe der Verzauberung
     * @param hideNBT     Soll die Verzauberung beim Hovern über das Item versteckt sein?
     *                    <p>
     *                    Should the enchantment be hidden while hovering over the item?
     * @author ItsLeMax
     */
    private void add(Enchantment enchantment, int level, boolean hideNBT) {
        lastMeta.addEnchant(enchantment, level, true);
        if (hideNBT) {
            lastMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
    }

    /**
     * Gibt das Item wieder
     * <p>
     * Returns the item
     *
     * @return Erstelltes Item <p> Created item
     */
    public ItemStack create() {
        lastItem.setItemMeta(lastMeta);
        return lastItem;
    }

    /**
     * Entnimmt die zwischenzeitlich erstellte Meta
     * <p>
     * Returns the in the meantime created item meta
     *
     * @return Temporäre Meta <p> Temporary meta
     * @author ItsLeMax
     */
    public ItemMeta getItemMeta() {
        return lastMeta;
    }
}