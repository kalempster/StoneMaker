package me.c4llm3p3t3r.stonemaker.Files;

import me.c4llm3p3t3r.stonemaker.StoneMaker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DebugCmd implements CommandExecutor, Listener {
    private StoneMaker stoneMaker = StoneMaker.getPlugin(StoneMaker.class);
    private StoneMakerItem stoneMakerItem = new StoneMakerItem();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("stone.reload") || sender.hasPermission("stone.*")){
            if(command.getName().equals("stone")){

                if(args.length == 1){
                    if(args[0].equals("reload")) {

                        try{

                            sender.sendMessage("Do you really want to relaod the config? It'll make every stone maker in every player's inventory useless! Y/N");
                            stoneMaker.getConfig().set("Config.YN", true);



                        }catch (Exception e){
                            if (sender instanceof Player){
                                sender.sendMessage("Error while trying to reload config. Look at the console to see more details.");

                            }else{
                                Bukkit.getConsoleSender().sendMessage("Error while trying to reload config");

                            }
                            Bukkit.getConsoleSender().sendMessage("[StoneMakerError]" + String.valueOf(e));
                        }


                    }
                    if(args[0].equals("give")){
                        if(sender instanceof Player){
                            Player p = (Player) sender;
                            if(p.hasPermission("stone.give") || p.hasPermission("stone.*")){
                                p.getInventory().addItem(stoneMakerItem.createItem());

                            }else{
                                p.sendMessage("You don't have permission to do that!");
                            }
                        }
                    }
                }
            }
        }






        return true;
    }
@EventHandler
    public void Chat(AsyncPlayerChatEvent event){
        Player p = event.getPlayer();
    if(stoneMaker.getConfig().getBoolean("Config.YN")){
        if(p.hasPermission("stone.config" )|| p.hasPermission("stone.*")){

                if(event.getMessage().equals("Y")){
                    event.setCancelled(true);
                    stoneMakerItem.createItem();
                    Bukkit.resetRecipes();
                    stoneMakerItem.createRecipe();
                    stoneMaker.getConfig().set("Config.YN", false);
                    p.sendMessage("Config reloaded");
                    stoneMaker.reloadConfig();

                    stoneMaker.saveConfig();


                }else{
                    stoneMaker.getConfig().set("Config.YN", false);
                    stoneMaker.saveConfig();
                    event.setCancelled(true);
                }

        }
    }


    }

    public int getAmountOfStoneMaker(Player p){

        int amount = 0;
        for (ItemStack stack: p.getInventory().getContents()) {
            if(stack.equals(stoneMakerItem.createItem())){
                amount++;
            }
        }
        return amount;
    }
}
