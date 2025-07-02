package me.thatsbloo.fleeTheFacility.Commands;

import me.thatsbloo.fleeTheFacility.Players.RagdollEntity;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)  {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            RagdollEntity.execute(p);
            p.setGameMode(GameMode.SPECTATOR);
            return true;
        }
        return false;
    }
}
