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

import java.util.ArrayList;
import java.util.Collections;
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
        final Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if (e.getItem().getType() == Material.FEATHER && !player.getAllowFlight() && !player.isFlying())
            {
                // Store the player into a flying list.
                player.sendMessage(plugin.c.pluginName + "You feel lighter...");
                player.setAllowFlight(true);
                player.setFlying(true);

                final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
                playerTaskMap.put(player.getName(), scheduler.scheduleSyncRepeatingTask(plugin, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        final Player thePlayer = player;
                        int totalFeathersLeft = 0;

                        HashMap<Integer, ? extends ItemStack> allFeathers = player.getInventory().all(Material.FEATHER);
                        ArrayList<Integer> itemIndexList = new ArrayList();
                        for (Integer key : allFeathers.keySet()) { itemIndexList.add(key); totalFeathersLeft += allFeathers.get(key).getAmount(); }
                        Collections.sort(itemIndexList);

                        if (allFeathers.size() > 0)
                        {
                            int itemIndex = itemIndexList.get(itemIndexList.size() - 1);
                            ItemStack is = player.getInventory().getItem(itemIndex);
                            int itemLeft = is.getAmount();
                            if (totalFeathersLeft <= 6) player.sendMessage(plugin.c.pluginName + yjColor.gold(Integer.toString(totalFeathersLeft - 5)) + " Feathers remaining!");
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
                }, 0L, 80L));
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
        else if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
        {
            if (e.getItem().getType() == Material.FEATHER)
            {
                int totalFeathersLeft = 0;
                HashMap<Integer, ? extends ItemStack> allFeathers = player.getInventory().all(Material.FEATHER);
                for (Integer key : allFeathers.keySet()) totalFeathersLeft += allFeathers.get(key).getAmount();
                player.sendMessage(plugin.c.pluginName + " You have " + yjColor.gold(Integer.toString(totalFeathersLeft)) + " Feathers (" + yjColor.gold(Integer.toString(totalFeathersLeft * 4)) + " seconds) left.");
            }
        }
    }
}
