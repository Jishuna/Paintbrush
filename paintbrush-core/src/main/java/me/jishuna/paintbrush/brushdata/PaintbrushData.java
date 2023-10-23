package me.jishuna.paintbrush.brushdata;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.util.StringUtils;
import me.jishuna.paintbrush.Settings;
import me.jishuna.paintbrush.dye.Dye;

public class PaintbrushData {
    public static final NamespacedKey DATA_KEY = NamespacedKey.fromString("paintbrush:brush-data");

    private Dye dye;
    private int uses;

    public PaintbrushData(Dye dye, int uses) {
        this.dye = dye;
        this.uses = uses;
    }

    public Dye getDye() {
        return dye;
    }

    public boolean hasDye() {
        return dye != null;
    }

    public void setDye(Dye color) {
        this.dye = color;
    }

    public int getUses() {
        return uses;
    }

    public void reduceUses(int amount) {
        this.uses -= amount;
    }

    public void addUses(int amount) {
        this.uses = Math.min(this.uses + amount, Settings.maxDyeApplied);
    }

    public void apply(ItemStack item) {
        if (item.getType().isAir()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        apply(meta);
        item.setItemMeta(meta);
    }

    public void apply(ItemMeta meta) {
        meta.getPersistentDataContainer().set(DATA_KEY, PaintbrushDataType.INSTANCE, this);
        if (hasDye()) {
            String name = this.dye.getChatColor() + StringUtils.capitalizeAll(this.dye.getName().replace('_', ' '));

            meta.setDisplayName(MessageHandler.get("paintbrush.name", name, uses));
            meta.setLore(MessageHandler.getList("paintbrush.lore", name, uses));
        } else {
            meta.setDisplayName(MessageHandler.get("paintbrush.empty-name"));
            meta.setLore(MessageHandler.getList("paintbrush.empty-lore"));
        }
    }

    public static PaintbrushData getData(ItemStack item) {
        if (!item.hasItemMeta()) {
            return null;
        }
        return getData(item.getItemMeta());
    }

    public static PaintbrushData getData(ItemMeta meta) {
        return meta.getPersistentDataContainer().get(DATA_KEY, PaintbrushDataType.INSTANCE);
    }
}
