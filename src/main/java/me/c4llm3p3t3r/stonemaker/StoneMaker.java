package me.c4llm3p3t3r.stonemaker;

import me.c4llm3p3t3r.stonemaker.Files.DebugCmd;
import me.c4llm3p3t3r.stonemaker.Files.StoneMakerItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StoneMaker extends JavaPlugin{





    @Override
    public void onEnable() {


            getConfig().addDefault("Locations.StoneMakers", "first launch");
            getConfig().addDefault("Options.Enchanted", true);
            getConfig().addDefault("Options.EnchantLevel", 10);
            getConfig().addDefault("Options.Name", "&4STONEMAKER");
            getConfig().addDefault("Options.Time", 3);
            getConfig().addDefault("Options.Lore", new String[]{"&6STONEMAKER generates", "&6stone every 3 seconds!"});




        getConfig().options().copyDefaults(true);
        saveConfig();
        StoneMakerItem stoneMakerItem = new StoneMakerItem();
        setupItem();

        getServer().getPluginManager().registerEvents(new StoneMakerItem(), this);
        getCommand("stone").setExecutor(new DebugCmd());

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setupItem(){
        StoneMakerItem stoneMakerItem = new StoneMakerItem();

        reloadConfig();
        stoneMakerItem.createItem();
        Bukkit.resetRecipes();
        stoneMakerItem.createRecipe();
    }
}
