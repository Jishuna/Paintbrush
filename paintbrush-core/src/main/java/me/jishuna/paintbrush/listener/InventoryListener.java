package me.jishuna.paintbrush.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.jishuna.paintbrush.Settings;
import me.jishuna.paintbrush.brushdata.PaintbrushData;
import me.jishuna.paintbrush.dye.Dye;
import me.jishuna.paintbrush.dye.DyeRegistry;

public class InventoryListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCursor() == null) {
            return;
        }

        ItemStack brush = event.getCurrentItem();
        ItemStack dyeItem = event.getCursor();
        Dye dye = DyeRegistry.getDye(dyeItem.getType());

        if (dye == null || !brush.hasItemMeta()) {
            return;
        }

        ItemMeta meta = brush.getItemMeta();
        PaintbrushData brushData = PaintbrushData.getData(meta);
        if (brushData == null || (brushData.hasDye() && brushData.getDye() != dye)) {
            return;
        }

        event.setCancelled(true);
        applyDye(brushData, dyeItem, dye, event.isShiftClick());

        brushData.apply(meta);
        brush.setItemMeta(meta);
    }

    private void applyDye(PaintbrushData brushData, ItemStack dyeItem, Dye dye, boolean shift) {
        if (!brushData.hasDye()) {
            brushData.setDye(dye);
        }
        if (brushData.getUses() < Settings.maxDyeApplied) {
            do {
                brushData.addUses(Settings.dyePerItem);
                dyeItem.setAmount(dyeItem.getAmount() - 1);
            } while (brushData.getUses() < Settings.maxDyeApplied && shift);
        }
    }
}
