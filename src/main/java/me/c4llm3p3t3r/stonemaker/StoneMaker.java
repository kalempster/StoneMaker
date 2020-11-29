package me.c4llm3p3t3r.stonemaker;

import me.c4llm3p3t3r.stonemaker.Files.DebugCmd;
import me.c4llm3p3t3r.stonemaker.Files.StoneMakerItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class StoneMaker extends JavaPlugin{





    @Override
    public void onEnable() {

        try{
            getConfig().addDefault("Locations.StoneMakers", "first start of plugin");
            getConfig().addDefault("Options.Enchanted", true);
            getConfig().addDefault("Options.EnchantLevel", 10);
            getConfig().addDefault("Options.Name", "&4STONE MAKER");
            getConfig().addDefault("Options.Time", 3);
            getConfig().addDefault("Options.Lore", new String[]{"&6Stone Maker that generates", "&6stone every 3 seconds!"});
            getConfig().addDefault("Options.Crafting", new String[]{"OBSIDIAN", "OBSIDIAN", "OBSIDIAN", "OBSIDIAN", "DIAMOND", "OBSIDIAN", "OBSIDIAN", "OBSIDIAN", "OBSIDIAN"});




            getConfig().options().copyDefaults(true);
            saveConfig();
            StoneMakerItem stoneMakerItem = new StoneMakerItem();
            setupItem();

            getServer().getPluginManager().registerEvents(new StoneMakerItem(), this);
            getServer().getPluginManager().registerEvents(new DebugCmd(), this);
            getCommand("stone").setExecutor(new DebugCmd());

        }catch (Exception e){}

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setupItem(){
        try {
            StoneMakerItem stoneMakerItem = new StoneMakerItem();

            reloadConfig();
            stoneMakerItem.createItem();
            Bukkit.resetRecipes();

            stoneMakerItem.createRecipe();
            getServer().getConsoleSender().sendMessage("[StoneMaker]"+ ChatColor.DARK_GREEN + " Recipe created!");
        }catch (Exception e){
            getServer().getConsoleSender().sendMessage("\n\n\t\t [StoneMaker]" + ChatColor.DARK_RED + " Config error! Check you recipe and reload the server!\n\n");

            getServer().getPluginManager().disablePlugin(this);
        }

    }
}
