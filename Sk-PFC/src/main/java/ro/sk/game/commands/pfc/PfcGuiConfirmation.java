package ro.sk.game.commands.pfc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ro.skayizen.api.inventories.ClickableItem;
import ro.skayizen.api.inventories.SkInventory;
import ro.skayizen.api.inventories.content.InventoryContents;
import ro.skayizen.api.inventories.content.InventoryProvider;
import ro.skayizen.api.itemsbuilder.itemstack.ItemBuilder;

import static ro.sk.game.commands.pfc.PfcCmd.pfcListGameByPlayer;
import static ro.sk.game.commands.pfc.PfcCmd.pfcPrefix;

public class PfcGuiConfirmation implements InventoryProvider {

    public static final SkInventory INVENTORY = SkInventory.builder()
            .id("PfcGuiConfirm")
            .title("§3§lPfc §d» §fPfc Jeux")
            .provider(new PfcGuiConfirmation())
            .size(5, 9)
            .closeable(false)
            .build();

    @Override
    public void init(Player player, InventoryContents inventoryContents) {
        Player target = pfcListGameByPlayer.get(player);

        inventoryContents.fillBorders(ClickableItem.empty(new ItemBuilder(Material.STAINED_GLASS_PANE)
                .withDisplayName(" ")
                .withDurability((short) 4)
                .build()));


        inventoryContents.set(2, 3, ClickableItem.of(new ItemBuilder(Material.WOOL)
                .withDisplayName("§aAccepter")
                        .withDurability((short) 5)
                .build(), e -> {
                    if(pfcListGameByPlayer.containsKey(target)){
                        pfcListGameByPlayer.remove(player);
                        INVENTORY.close(player);
                        player.sendMessage(pfcPrefix + "§eCe joueur est déjà dans une partie.");
                    } else {
                        pfcListGameByPlayer.put(target, player);
                        PfcGui.INVENTORY.open(target);
                        PfcGui.INVENTORY.open(player);
                        player.sendMessage(pfcPrefix + "§eVeuillez attendre la fin de votre partie !");
                    }


        }));

        inventoryContents.set(2, 5, ClickableItem.of(new ItemBuilder(Material.WOOL)
                .withDisplayName("§cRefuser")
                        .withDurability((short) 14)
                .build(), e -> {
                pfcListGameByPlayer.remove(player);
                INVENTORY.close(player);
                target.sendMessage(pfcPrefix + "§eCe joueur a refusé votre demande !");

        }));

    }

    @Override
    public void update(Player player, InventoryContents contents) {
        InventoryProvider.super.update(player, contents);
    }
}
