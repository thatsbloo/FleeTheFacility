package me.thatsbloo.fleeTheFacility;

import java.util.UUID;
import org.bukkit.plugin.java.JavaPlugin;
import me.thatsbloo.fleeTheFacility.Hacking.*;

public final class FleeTheFacility extends JavaPlugin {

    public static UUID beastUUID;
    public HackManager hackManager;

    @Override
    public void onEnable() {

        this.hackManager = new HackManager(this);
        getServer().getPluginManager().registerEvents(new HackListener(), this);
        
        
        
        // Plugin startup logic//
        //make computer
        //make beast exist
        //beast hit player -> player get deaded (unalived)
        //beast grab deaded player to thing
        //NO PROTOCLLIB!!!!
        //deaded player in like the tube is a fake npc type thing
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}