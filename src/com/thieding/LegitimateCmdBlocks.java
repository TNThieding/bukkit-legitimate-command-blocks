package com.thieding;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class LegitimateCmdBlocks extends JavaPlugin implements Listener
{
	Logger log;
	
	public void onEnable()
	{
		log = this.getLogger();
		getServer().getPluginManager().registerEvents(this, this);
		log.info("LegitimateCmdBlocks is now enabled.");
		
		ShapedRecipe recipe_cmdblock = new ShapedRecipe(new ItemStack(Material.COMMAND)).shape(new String[] { "rcr", "cbc", "rcr" }).setIngredient('r', Material.REDSTONE).setIngredient('c', Material.REDSTONE_COMPARATOR).setIngredient('b', Material.HARD_CLAY);
		getServer().addRecipe(recipe_cmdblock);
	}
	
	public void onDisable()
	{
		log.info("LegitimateCmdBlocks is now disabled.");
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Block clicked = event.getClickedBlock();
		Player clicker = event.getPlayer();
		if (clicked.getType() == Material.COMMAND)
		{
			if (clicker.getItemInHand().getType() == Material.WRITTEN_BOOK)
			{
				if(clicker.hasPermission("LegitimateCmdBlocks.updatecmd"))
				{
					CommandBlock clickedcmd = (CommandBlock)clicked.getState();
					BookMeta cmdbook = (BookMeta) clicker.getItemInHand().getItemMeta();
					clickedcmd.setCommand(cmdbook.getPage(1));
					clickedcmd.update();
					clicker.sendMessage("Command updated to " + cmdbook.getPage(1));
				}
				else
				{
					clicker.sendMessage("You do not have permission to update command blocks!");
				}
			}
		}
	}
}
