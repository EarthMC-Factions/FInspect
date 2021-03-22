package dev.warriorrr.finspect;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

import net.md_5.bungee.api.ChatColor;
import net.prosavage.factionsx.core.Faction;
import net.prosavage.factionsx.manager.GridManager;

import static dev.warriorrr.finspect.FInspect.inspectingPlayers;
import static dev.warriorrr.finspect.FInspect.prefix;
import static dev.warriorrr.finspect.FInspect.cooldowns;

import java.util.List;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerDisconnect(PlayerQuitEvent event) {
        if (inspectingPlayers.contains(event.getPlayer().getUniqueId()))
            inspectingPlayers.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND)
            return;
        
        if (!inspectingPlayers.contains(event.getPlayer().getUniqueId()) || !canInspect(event.getPlayer(), event.getClickedBlock()))
            return;

        int cooldown = 5;
        if (cooldowns.containsKey(event.getPlayer().getUniqueId()) && cooldowns.get(event.getPlayer().getUniqueId()) > System.currentTimeMillis()) {
            long seconds = (cooldowns.get(event.getPlayer().getUniqueId()) - System.currentTimeMillis()) / 1000 % cooldown;
            event.getPlayer().sendMessage(prefix + ChatColor.RED + " Inspect is on cooldown! Time remaining: " + seconds + " seconds.");
            return;
        }


        List<String> results = CoreProtectUtil.getParsedBlockHistory(event.getClickedBlock());
        if (results.size() == 1)
            event.getPlayer().sendMessage(prefix + ChatColor.BLUE + " No block data found for this location.");
        else
            for (String result : results)
                event.getPlayer().sendMessage(result);
        if (cooldowns.containsKey(event.getPlayer().getUniqueId()))
            cooldowns.replace(event.getPlayer().getUniqueId(), System.currentTimeMillis() + cooldown * 1000);
        else
            cooldowns.put(event.getPlayer().getUniqueId(), System.currentTimeMillis() + cooldown * 1000);
    }

    private boolean canInspect(Player player, Block block) {
        Faction faction = GridManager.INSTANCE.getFactionAt(block.getChunk());
        if (faction.isWilderness())
            return false;
        if (faction.getLeader().getPlayer().getUniqueId() != player.getUniqueId())
            return false;
        return true;
    }
}
