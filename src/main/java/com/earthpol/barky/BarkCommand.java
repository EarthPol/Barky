package com.earthpol.barky;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class BarkCommand implements CommandExecutor {
    private HashMap<UUID, Long> cooldowns = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerID = player.getUniqueId();
        long timeNow = System.currentTimeMillis();
        long cooldownTime = 20 * 1000; // 20 seconds in milliseconds

        if (cooldowns.containsKey(playerID)) {
            long lastUsed = cooldowns.get(playerID);
            if (timeNow - lastUsed < cooldownTime) {
                // Check if the player is an OP or has the bypass permission
                if (!player.isOp() && !player.hasPermission("earthpol.barky.bypass")) {
                    player.sendMessage("Are Fur real? Give it a minute before barking again!");
                    return true;
                }
            }
        }

        cooldowns.put(playerID, timeNow);

        Sound sound = Sound.ENTITY_WOLF_AMBIENT; // Default sound
        String action = "barked"; // Default action

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "bark":
                    sound = Sound.ENTITY_WOLF_AMBIENT;
                    break;
                case "howl":
                    sound = Sound.ENTITY_WOLF_HOWL;
                    action = "howled";
                    break;
                case "growl":
                    sound = Sound.ENTITY_WOLF_GROWL;
                    action = "growled";
                    break;
                case "hurt":
                    sound = Sound.ENTITY_WOLF_HURT;
                    action = "sounded hurt";
                    break;
                case "pant":
                    sound = Sound.ENTITY_WOLF_PANT;
                    action = "purred";
                    break;
                case "whine":
                    sound = Sound.ENTITY_WOLF_WHINE;
                    action = "whined";
                    break;
                default:
                    player.sendMessage("Unknown sound. Playing default bark");
            }
        }

        player.getWorld().playSound(player.getLocation(), sound, 1.0f, 1.0f);
        String message = player.getName() + " has " + action + ".";
        Bukkit.getOnlinePlayers().stream().filter(p -> p.getLocation().distance(player.getLocation()) <= 15).forEach(p -> p.sendMessage(message));


        return true;
    }
}
