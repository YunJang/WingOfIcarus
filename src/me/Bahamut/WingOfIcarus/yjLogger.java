package me.Bahamut.WingOfIcarus;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Yun on 3/28/2015.
 */
public class yjLogger
{
    public static final Logger logger = Logger.getLogger("Minecraft");
    private final JavaPlugin plugin;

    public yjLogger (JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    public void info (String s)
    {
        logger.log(Level.INFO, "[" + plugin.getDescription().getName() + "] " + s);
    }

    public void warning (String s)
    {
        logger.log(Level.WARNING, "[" + plugin.getDescription().getName() + "] " + s);
    }
}
