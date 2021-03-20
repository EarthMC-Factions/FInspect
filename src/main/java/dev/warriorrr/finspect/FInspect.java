package dev.warriorrr.finspect;

import net.md_5.bungee.api.ChatColor;
import net.prosavage.factionsx.FactionsX;
import net.prosavage.factionsx.addonframework.Addon;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

public class FInspect extends Addon {

    public static String prefix = ChatColor.GOLD + "[" + gradient("EMCF", Color.GREEN, new Color(0, 102, 0)) + ChatColor.GOLD + "]";
    public static List<UUID> inspectingPlayers = new ArrayList<UUID>();
    private static InspectCommand inspectCommand = new InspectCommand();

    @Override
    public void onEnable() {
        FactionsX.baseCommand.addSubCommand(inspectCommand);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this.getFactionsXInstance());
    }

    @Override
    public void onDisable() {
        FactionsX.baseCommand.removeSubCommand(inspectCommand);
    }

    public static String gradient(String string, Color from, Color to) {
        int l = string.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < l; i++) {
            sb.append(ChatColor.of(new Color(
                    (from.getRed() + (i * (1.0F / l) * (to.getRed() - from.getRed()))) / 255,
                    (from.getGreen() + (i * (1.0F / l) * (to.getGreen() - from.getGreen()))) / 255,
                    (from.getBlue() + (i * (1.0F / l) * (to.getBlue() - from.getBlue()))) / 255
            )));
            sb.append(string.charAt(i));
        }
        return sb.toString();
    }
}
