package me.c4llm3p3t3r.stonemaker.Files;

import me.c4llm3p3t3r.stonemaker.StoneMaker;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class StoneMakerItem implements Listener {

    private Plugin stoneMaker = StoneMaker.getPlugin(StoneMaker.class);


    public ItemStack createItem(){
        ItemStack itemStack = new ItemStack(Material.ENDER_STONE);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(stoneMaker.getConfig().getBoolean("Options.Enchanted")){
            itemMeta.addEnchant(Enchantment.LURE, stoneMaker.getConfig().getInt("Options.EnchantLevel"), true);

        }

        itemMeta.setDisplayName(stoneMaker.getConfig().getString("Options.Name").replace('&', '§'));

        ArrayList<String> lore = new ArrayList<String>();
        for (String line: stoneMaker.getConfig().getStringList("Options.Lore")) {
            lore.add(line.replace('&', '§'));
        }

        itemMeta.setLore(lore);

        itemMeta.setUnbreakable(true);

        itemStack.setItemMeta(itemMeta);





        return itemStack;
    }


    public void createRecipe(){



        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(stoneMaker, "new"), createItem());

        shapedRecipe.shape("!!!", "!@!", "!!!");

        shapedRecipe.setIngredient('!', Material.OBSIDIAN);
        shapedRecipe.setIngredient('@', Material.DIAMOND);

        stoneMaker.getServer().addRecipe(shapedRecipe);

    }
    @EventHandler
    public void blockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        ItemStack hand = player.getItemInHand();
        if(hand.getItemMeta().equals(createItem().getItemMeta())){

            Block stack = event.getBlockPlaced();
            ArrayList<String> locationsOfStoneMakers = new ArrayList<String>(stoneMaker.getConfig().getStringList("Locations.StoneMakers"));
            Location location = event.getBlockPlaced().getLocation();
            locationsOfStoneMakers.add(location.toString());

            stoneMaker.getConfig().set("Locations.StoneMakers", locationsOfStoneMakers);
            stoneMaker.saveConfig();
            location.setY(location.getY() + 1);
            if(stack.getType().equals(createItem().getType())){

                location.getWorld().getBlockAt(location).setType(Material.STONE);

            }
        }


    }
    @EventHandler
    public void blockDestroy(BlockBreakEvent event){

        Player player = event.getPlayer();

        Block bloc = event.getBlock();

        Location location = bloc.getLocation();
        if(bloc.getType().equals(Material.ENDER_STONE) && stoneMaker.getConfig().getStringList("Locations.StoneMakers").contains(location.toString())){
            ArrayList<String> locationsOfStoneMakers = new ArrayList<String>(stoneMaker.getConfig().getStringList("Locations.StoneMakers"));
            locationsOfStoneMakers.remove(location.toString());
            stoneMaker.getConfig().set("Locations.StoneMakers", locationsOfStoneMakers);
            stoneMaker.saveConfig();
            bloc.setType(Material.AIR);
            location.getWorld().dropItemNaturally(location, createItem());
        }else{
            location.setY(location.getY() - 1);

            if (stoneMaker.getConfig().getStringList("Locations.StoneMakers").contains(location.toString())){

                location.setY(location.getY() + 1);

                Location stone = location;

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(stone.getBlock().getType().equals(Material.AIR)){
                            stone.getWorld().getBlockAt(stone).setType(Material.STONE);
                            this.cancel();
                        }else{

                            this.cancel();
                        }

                    }
                }.runTaskTimer(stoneMaker, stoneMaker.getConfig().getInt("Options.Time")*20, 0);


            }
        }


    }


}
