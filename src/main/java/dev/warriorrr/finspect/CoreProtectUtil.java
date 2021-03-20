package dev.warriorrr.finspect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.coreprotect.CoreProtectAPI.ParseResult;
import net.md_5.bungee.api.ChatColor;

import static dev.warriorrr.finspect.FInspect.prefix;

public class CoreProtectUtil {
    public static boolean isCoreProtectEnabled() {
        Plugin coreProtect = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

        if (coreProtect == null || !(coreProtect instanceof CoreProtect)) {
            return false;
        }

        return ((CoreProtect) coreProtect).getAPI().isEnabled();
    }

    public static CoreProtectAPI getCoreProtectAPI() {
        return ((CoreProtect) Bukkit.getServer().getPluginManager().getPlugin("CoreProtect")).getAPI();
    }

    public static List<String> getParsedBlockHistory(Block block) {
        CoreProtectAPI coreProtectAPI = getCoreProtectAPI();
        List<String[]> results = coreProtectAPI.performLookup(604800, null, null, null, null, null, 1, block.getLocation());
        List<String> parsedResults = new ArrayList<String>();
        parsedResults.add(prefix + ChatColor.BLUE + " Results for (" + ChatColor.GRAY + block.getWorld().getName() + "/" + block.getX() + "/" + block.getY() + "/" + block.getZ() + ChatColor.BLUE + ") Radius: 1");
        for (int i = 0; i < results.size(); i++) {
            if (i >= 10)
                break;
            
            int a = i+1;
            String[] result = results.get(i);
            ParseResult parsedResult = coreProtectAPI.parseResult(result);
            String stringResult = ChatColor.GOLD + "(" + ChatColor.RED + a + ChatColor.GOLD + ") " + ChatColor.BLUE + parsedResult.getPlayer() + " " + ChatColor.GRAY + getActionString(parsedResult.getActionId()) + " " + ChatColor.BLUE + parsedResult.getType() + ChatColor.GRAY + " at " + ChatColor.BLUE + parsedResult.getX() + ChatColor.GRAY + "/" + ChatColor.BLUE + parsedResult.getY() + ChatColor.GRAY + "/" + ChatColor.BLUE + parsedResult.getZ();
            parsedResults.add(stringResult);
        }
        return parsedResults;
    }

    private static String getActionString(int actionID) {
        switch(actionID) {
            case 0:
                return "removed";
            case 1:
                return "placed";
            case 2:
                return "interacted with";
            default:
                return "UNKNOWN";
        }
    }
}
