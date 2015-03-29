package me.Bahamut.WingOfIcarus;

import org.bukkit.ChatColor;

/**
 * Created by yunjang on 3/14/15.
 */
public class yjColor
{
    public static String green (String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GREEN);
        sb.append(str);
        sb.append(ChatColor.RESET);
        return sb.toString();
    }

    public static String gold (String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GOLD);
        sb.append(str);
        sb.append(ChatColor.RESET);
        return sb.toString();
    }

    public static String aqua (String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.AQUA);
        sb.append(str);
        sb.append(ChatColor.RESET);
        return sb.toString();
    }

    public static String light_purple (String str)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.LIGHT_PURPLE);
        sb.append(str);
        sb.append(ChatColor.RESET);
        return sb.toString();
    }
}
