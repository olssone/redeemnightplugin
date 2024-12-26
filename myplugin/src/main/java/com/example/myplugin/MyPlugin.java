package com.example.myplugin;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin implements CommandExecutor {

    private static final int REQUIRED_XP_LEVELS = 5;
    private static final long NIGHT_TIME_TICKS = 13000L; // Minecraft night starts at tick 13000

    @Override
    public void onEnable() {
        // Register the "redeemnight" command and associate it with this class
        if (getCommand("redeemnight") != null) {
            getCommand("redeemnight").setExecutor(this);
        }
        getLogger().info("MyPlugin has been enabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the executed command is "redeemnight"
        if (label.equalsIgnoreCase("redeemnight")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String pName = player.getName();
                getLogger().info("Command: redeemnight executed by " + pName + ".");
                if (player.hasPermission("myplugin.redeemnight")) {
                    if (player.getLevel() >= REQUIRED_XP_LEVELS) {
                        // Deduct 5 XP levels
                        getLogger().info("Deducting " + REQUIRED_XP_LEVELS + " levels from " + pName);
                        player.setLevel(player.getLevel() - REQUIRED_XP_LEVELS);

                        // Set the time to night in the player's current world
                        World world = player.getWorld();
                        getLogger().info("Changing world time. Initial time is " + world.getTime());
                        world.setTime(NIGHT_TIME_TICKS);
                        getLogger().info("World time has been changed. Updated time is " + world.getTime());

                        // Notify the player
                        player.sendMessage("§aYou have redeemed night! 5 XP levels have been deducted.");
                    } else {
                        // Insufficient XP levels
                        player.sendMessage("§cYou need at least 5 XP levels to redeem night.");
                    }
                } else {
                    // No permission
                    player.sendMessage("§cYou do not have permission to use this command.");
                }
            } else {
                // Command was not run by a player
                sender.sendMessage("This command can only be run by a player.");
            }
            return true;
        }
        return false;
    }
}
