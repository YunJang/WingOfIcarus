package me.Bahamut.WingOfIcarus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

/**
 * Created by Yun on 3/28/2015.
 */
public class WingOfIcarusListener implements Listener
{
    public WingOfIcarusPlugin plugin;
    private static HashMap<String, Integer> playerTaskMap = new HashMap<String, Integer>();

    public WingOfIcarusListener(WingOfIcarusPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFeatherClick(PlayerInteractEvent e)
    {
        if (e.getAction() == Action.RIGHT_CLICK_AIR)
        {
            final Player player = e.getPlayer();

            if (e.getItem().getType() == Material.FEATHER && !player.getAllowFlight() && !player.isFlying())
            {
                // Store the player into a flying list.
                player.sendMessage(plugin.c.pluginName + "I'm flying!");
                player.setAllowFlight(true);
                player.setFlying(true);

                final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                playerTaskMap.put(player.getName(), scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        final Player thePlayer = player;
                        int itemIndex = player.getInventory().first(Material.FEATHER);
                        if (itemIndex >= 0)
                        {
                            ItemStack is = player.getInventory().getItem(itemIndex);
                            int itemLeft = is.getAmount();
                            if (itemLeft <= 6) player.sendMessage(plugin.c.pluginName + yjColor.gold(Integer.toString(itemLeft - 1)) + " Feathers remaining!");
                            if (itemLeft - 1 <= 0) player.getInventory().clear(itemIndex);
                            else is.setAmount(itemLeft - 1);
                            player.updateInventory();
                            return;
                        }

                        // Out of feathers. Remove the task.
                        plugin.getServer().getScheduler().cancelTask(playerTaskMap.get(player.getName()));
                        player.sendMessage(plugin.c.pluginName + "I'm falling!");
                        player.setFlying(false);
                        player.setAllowFlight(false);
                        playerTaskMap.remove(player.getName());
                    }
                }, 0L, 40L));
            }
            else if (e.getItem().getType() == Material.FEATHER && player.getAllowFlight())
            {
                player.sendMessage(plugin.c.pluginName + "I'm going down!");
                player.setFlying(false);
                player.setAllowFlight(false);

                BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                scheduler.cancelTask(playerTaskMap.get(player.getName()));
                playerTaskMap.remove(player.getName());
            }
        }
    }
}
