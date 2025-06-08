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
 *
 * @author ItsLeMax
 * @since 1.2.0
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
     *
     * @param item Item to edit
     * @author ItsLeMax
     * @since 1.2.0
     */
    public ItemLib setItem(@NotNull final ItemStack item) {

        this.lastItem = item;
        this.lastMeta = item.getItemMeta();

        return this;

    }

    /**
     * Creates an ItemStack with ItemMeta, ready to edit
     *
     * @param material Material that the item is supposed to have
     * @param amount   Amount of the item
     * @author ItsLeMax
     * @since 1.2.0
     */
    private void item(@NotNull final Material material, final int amount) {
        this.lastItem = new ItemStack(material, amount);
        this.lastMeta = lastItem.getItemMeta();
    }

    /**
     * Sets the name of the ItemStack
     *
     * @param name name of the item
     * @author ItsLeMax
     * @since 1.2.0
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
     *
     * @param lores Lores (subtitles)
     * @author ItsLeMax
     * @since 1.2.0
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
     * @param level       Level of the enchantment
     * @param hideNBT     Should the enchantment be hidden while hovering over the item?
     * @author ItsLeMax
     * @since 1.2.0
     */
    private void enchantment(@NotNull final Enchantment enchantment, final int level, final boolean hideNBT) {

        lastMeta.addEnchant(enchantment, level, true);

        if (hideNBT) {
            lastMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

    }

    /**
     * Returns the item
     *
     * @return ItemStack with created item
     * @author ItsLeMax
     * @since 1.2.0
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
     *
     * @return Item Meta
     * @author ItsLeMax
     * @since 1.2.0
     */
    public ItemMeta getItemMeta() {
        return lastMeta;
    }

}