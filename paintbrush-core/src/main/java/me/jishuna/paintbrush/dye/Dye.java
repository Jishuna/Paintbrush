package me.jishuna.paintbrush.dye;

import java.awt.Color;

import org.bukkit.DyeColor;
import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;

public class Dye {
    private final Material material;
    private final DyeColor dyeColor;
    private final ChatColor chatColor;

    public Dye(Material material, DyeColor dyeColor) {
        this.material = material;
        this.dyeColor = dyeColor;
        this.chatColor = createChatColor(dyeColor);
    }

    private ChatColor createChatColor(DyeColor dyeColor) {
        return ChatColor.of(new Color(dyeColor.getColor().asRGB()));
    }

    public Material getMaterial() {
        return material;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public String getName() {
        return this.dyeColor.name();
    }

    @Override
    public int hashCode() {
        return dyeColor.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Dye other)) {
            return false;
        }
        return dyeColor == other.dyeColor;
    }
}
