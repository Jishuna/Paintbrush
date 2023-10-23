package me.jishuna.paintbrush.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.jishuna.paintbrush.ColorUtils;
import me.jishuna.paintbrush.Settings;
import me.jishuna.paintbrush.brushdata.PaintbrushData;

public class PaintbrushListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || !player.hasPermission("paintbrush.use")) {
            return;
        }

        ItemStack brush = event.getItem();
        if (brush == null || !brush.hasItemMeta()) {
            return;
        }

        ItemMeta meta = brush.getItemMeta();
        PaintbrushData brushData = PaintbrushData.getData(meta);
        if (brushData == null || !brushData.hasDye()) {
            return;
        }

        Block block = event.getClickedBlock();
        Material type = block.getType();
        if (type == Material.WATER_CAULDRON) {
            cleanBrush(block, player, brushData);
        } else {
            paintBlock(block, type, brushData, event);
        }

        brushData.apply(meta);
        brush.setItemMeta(meta);
    }

    private void cleanBrush(Block block, Player player, PaintbrushData brushData) {
        if (!Settings.allowCleaning) {
            return;
        }

        brushData.setDye(null);

        Levelled cauldron = (Levelled) block.getBlockData();
        if (cauldron.getLevel() > 1) {
            cauldron.setLevel(cauldron.getLevel() - 1);
            block.setBlockData(cauldron);
        } else {
            block.setType(Material.CAULDRON);
        }
        player.playSound(block.getLocation(), Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1f, 1f);
    }

    private boolean paintBlock(Block block, Material type, PaintbrushData brushData, PlayerInteractEvent event) {
        DyeColor color = brushData.getDye().getDyeColor();
        Material to = ColorUtils.getDyedMaterial(type, color);
        if (to == null || to == type) {
            return false;
        }

        event.setCancelled(true);
        recolorBlock(block, to);
        event.getPlayer().playSound(block.getLocation(), Sound.ITEM_BRUSH_BRUSHING_GENERIC, SoundCategory.BLOCKS, 0.5f, 0.6f);

        brushData.reduceUses(1);
        if (brushData.getUses() <= 0) {
            brushData.setDye(null);
        }
        return true;
    }

    private void recolorBlock(Block block, Material to) {
        BlockState state = block.getState();
        if (state instanceof Container container) { // Handle shulker contents
            copyAndChange(container, to);
        } else if (state instanceof Banner banner) { // Handle banners
            copyAndChange(banner, to);
        } else {
            state.setBlockData(changeType(state.getBlockData(), to));
        }

        state.update(true);
    }

    private void copyAndChange(Banner banner, Material to) {
        List<Pattern> patterns = banner.getPatterns();
        banner.setBlockData(changeType(banner.getBlockData(), to));
        banner.setPatterns(patterns);
    }

    private void copyAndChange(Container container, Material to) {
        ItemStack[] contents = container.getSnapshotInventory().getContents();
        container.setBlockData(changeType(container.getBlockData(), to));
        container.getSnapshotInventory().setContents(contents);
    }

    // Janky hack mate
    private BlockData changeType(BlockData from, Material to) {
        String data = from.getAsString(true);
        data = data.replace(from.getMaterial().getKey().toString(), to.getKey().toString());

        return Bukkit.createBlockData(data);
    }
}
