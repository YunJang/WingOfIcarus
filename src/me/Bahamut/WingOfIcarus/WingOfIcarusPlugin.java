package me.Bahamut.WingOfIcarus;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by Yun on 3/28/2015.
 */
public class WingOfIcarusPlugin extends JavaPlugin
{
    private static yjLogger logger;
    public WingOfIcarusConstants c;

    public void onDisable ()
    {
        System.out.println(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " disabled.");
    }

    public void onEnable ()
    {
        logger = new yjLogger(this);
        getServer().getPluginManager().registerEvents(new WingOfIcarusListener(this), this);
        logger.info(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " enabled!");
    }
}
