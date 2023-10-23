package ro.sk.game.commands.pfc;

import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;
import ro.sk.game.Pfc;
import ro.skayizen.api.inventories.ClickableItem;
import ro.skayizen.api.inventories.SkInventory;
import ro.skayizen.api.inventories.content.InventoryContents;
import ro.skayizen.api.inventories.content.InventoryProvider;
import ro.skayizen.api.itemsbuilder.itemstack.ItemBuilder;


import java.util.HashMap;

import static ro.sk.game.commands.pfc.PfcCmd.*;

public class PfcGui implements InventoryProvider {

    private static HashMap<Player, String> pfcChoice = new HashMap<Player, String>();

    public static final SkInventory INVENTORY = SkInventory.builder()
            .id("RankMenu")
            .title("§3§lPfc §d» §fPfc Jeux")
            .provider(new PfcGui())
            .size(5, 9)
            .closeable(false)
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {


        hasClickedMessage.put(player, true);

        inventoryContents.fillBorders(ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                .withDisplayName(" ")
                .withDurability((short) 9)
                .build()));


        inventoryContents.set(2, 2, ClickableItem.of(new ItemBuilder(Material.COBBLESTONE)
                .withDisplayName("§7Pierre")
                .build(), e -> {

            isPfcRegister(player, "Pierre");

        }));

        inventoryContents.set(2, 4, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .withDisplayName("§fFeuille")
                .build(), e -> {

            isPfcRegister(player, "Feuille");

        }));

        inventoryContents.set(2, 6, ClickableItem.of(new ItemBuilder(Material.SHEARS)
                .withDisplayName("§6Ciseau")
                .build(), e -> {

            isPfcRegister(player, "Ciseau");


        }));
    }
    @Override
    public void update (Player player, InventoryContents contents){


    }

    private static void isPfcRegister(Player pfcPlayer, String choice){
        if(pfcChoice.containsKey(pfcPlayer)){
            pfcChoice.remove(pfcPlayer);
        }
        pfcChoice.put(pfcPlayer, choice);
        INVENTORY.close(pfcPlayer);

        Player target = pfcListGameByPlayer.get(pfcPlayer);
        if(pfcChoice.containsKey(target)){
            String targetChoice = pfcChoice.get(target);
            String pfcPlayerChoice = pfcChoice.get(pfcPlayer);
            messageWin(pfcPlayer, target, pfcPlayerChoice, targetChoice);

        } else {
            pfcPlayer.sendMessage(pfcPrefix + "§eEn attente du choix de l'adversaire !");
        }
    }

    private static void messageWin(Player player, Player target, String playerChoice, String targetChoice) {
        String message = pfcPrefix + "§eChoix de l'adversaire : §6" + targetChoice;

        if (targetChoice.equals(playerChoice)) {
            message += " §ec'est donc une §6Égalité §e!";
        } else if ((targetChoice.equals("Pierre") && playerChoice.equals("Feuille")) ||
                (targetChoice.equals("Feuille") && playerChoice.equals("Ciseau")) ||
                (targetChoice.equals("Ciseau") && playerChoice.equals("Pierre"))) {
            message += " §etu as donc §aGagné §e!";
        } else {
            message += " §etu as donc §cPerdu §e!";
        }

        player.sendMessage(message);
        target.sendMessage(message);

        removePlayersFromHashMaps(player, target);
    }

    private static void removePlayersFromHashMaps(Player player, Player target) {
        pfcListGameByPlayer.remove(player);
        pfcListGameByPlayer.remove(target);
        pfcChoice.remove(player);
        pfcChoice.remove(target);
    }



}
