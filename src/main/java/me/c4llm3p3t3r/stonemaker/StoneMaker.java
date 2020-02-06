package me.c4llm3p3t3r.stonemaker;

import me.c4llm3p3t3r.stonemaker.Files.DebugCmd;
import me.c4llm3p3t3r.stonemaker.Files.StoneMakerItem;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StoneMaker extends JavaPlugin{





    @Override
    public void onEnable() {


            getConfig().addDefault("Locations.StoneMakers", "pierwsze uruchomienie pluginu, lub restart pliku konfiguracyjnego");
            getConfig().addDefault("Options.Enchanted", true);
            getConfig().addDefault("Options.EnchantLevel", 10);
            getConfig().addDefault("Options.Name", "&4STONIARKA");
            getConfig().addDefault("Options.Time", 3);
            getConfig().addDefault("Options.Lore", new String[]{"&6Stoniarka ktora generuje", "&6kamien co 3 sekundy!"});




        getConfig().options().copyDefaults(true);
        saveConfig();
        System.out.println("[StoneMaker] C4LLM3P3T3R All rights reserved");
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
