package me.c4llm3p3t3r.stonemaker.Files;

import me.c4llm3p3t3r.stonemaker.StoneMaker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DebugCmd implements CommandExecutor {
    private StoneMaker stoneMaker = StoneMaker.getPlugin(StoneMaker.class);
    private StoneMakerItem stoneMakerItem = new StoneMakerItem();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("stone.save") || sender.hasPermission("stone.*")){
            if(command.getName().equals("stone")){

                if(args.length == 1){
                    if(args[0].equals("save")) {
                        try{


                            stoneMaker.reloadConfig();
                            stoneMakerItem.createItem();
                            Bukkit.resetRecipes();
                            stoneMakerItem.createRecipe();


                        }catch (Exception e){
                            if (sender instanceof Player){
                                sender.sendMessage("Error while trying to reload the config file. See console to get more details.");

                            }else{
                                Bukkit.getConsoleSender().sendMessage("Error while trying to reload the config file.");

                            }
                            Bukkit.getConsoleSender().sendMessage("[StoneMakerError]" + String.valueOf(e));
                        }


                    }
                }
            }
        }






        return true;
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
