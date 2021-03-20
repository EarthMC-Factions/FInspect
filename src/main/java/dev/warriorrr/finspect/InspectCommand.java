package dev.warriorrr.finspect;

import net.md_5.bungee.api.ChatColor;
import net.prosavage.factionsx.command.engine.CommandInfo;
import net.prosavage.factionsx.command.engine.CommandRequirementsBuilder;
import net.prosavage.factionsx.command.engine.FCommand;

import static dev.warriorrr.finspect.FInspect.prefix;

import java.util.UUID;

import static dev.warriorrr.finspect.FInspect.inspectingPlayers;

public class InspectCommand extends FCommand {
    public InspectCommand() {
        getAliases().add("inspect");
        getAliases().add("i");

        this.commandRequirements = new CommandRequirementsBuilder()
            .asLeader(true)
            .asPlayer(true)
            .build();
    }

    public boolean execute(CommandInfo commandInfo) {
        UUID uuid = commandInfo.getPlayer().getUniqueId();
        if (inspectingPlayers.contains(uuid)) {
            inspectingPlayers.remove(uuid);
            commandInfo.getPlayer().sendMessage(prefix + ChatColor.AQUA + " Inspector disabled.");
        } else {
            inspectingPlayers.add(uuid);
            commandInfo.getPlayer().sendMessage(prefix + ChatColor.AQUA + " Inspector enabled.");
        }
        return true;
    }

    public String getHelpInfo() {
        return "Toggles inspect mode.";
    }
}
