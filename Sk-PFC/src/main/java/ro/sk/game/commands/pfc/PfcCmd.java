package ro.sk.game.commands.pfc;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ro.sk.game.Pfc;
import ro.skayizen.api.commands.framwork.Command;
import ro.skayizen.api.commands.framwork.CommandArgs;

import java.util.HashMap;

public class PfcCmd {


    public static Pfc instance;
    public PfcCmd(Plugin instance) {
        this.instance = (Pfc) instance;
    }

    public static String pfcPrefix = "§6[§ePfc§6] §d» ";
    public static HashMap<Player, Player> pfcListGameByPlayer = new HashMap<>();
    public static HashMap<Player, Boolean> hasClickedMessage = new HashMap<>();
    @Command(name = "pfc")
    public boolean onStart(CommandArgs args) {
        CommandSender sender = args.getSender();
        if ((sender instanceof Player)) {


            Player player = (Player) sender;
            if (args.length() == 1) {
                Player target = Bukkit.getPlayer(args.getArgs(0));
                if (target != null) {
                    if(target == player){
                        player.sendMessage(pfcPrefix + "§eVous ne pouvez pas jouer tout seul.");
                        return false;
                    }
                    if (pfcListGameByPlayer.containsKey(player)) {
                        player.sendMessage(pfcPrefix + "§eVeuillez attendre la fin de votre partie !");
                        return false;
                    }
                    if (pfcListGameByPlayer.containsKey(target)) {
                        player.sendMessage(pfcPrefix + "§eCe joueur est déjà dans une partie.");
                        return false;
                    }
                    player.sendMessage(pfcPrefix + "§eTu as défier §6" + target.getName() + " §e !");
                    TextComponent oppenentMessage = new TextComponent(pfcPrefix + "§6" + player.getName() + "§e vient de te défier ! §e[§6Click sur §6le message !§e]");
                    oppenentMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6Click pour accepter le duel !").create()));
                    oppenentMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pfc accept " + player.getName()));
                    target.spigot().sendMessage(oppenentMessage);

                } else {
                    player.sendMessage(pfcPrefix + "§6" + args.getArgs(0) + "§e n'est pas en ligne");
                    return false;
                }
            } else if(args.length() == 2){
                if(args.getArgs(0).equalsIgnoreCase( "accept")){
                    Player target = Bukkit.getPlayer(args.getArgs(1));
                    if (target != null) {
                        if (pfcListGameByPlayer.containsKey(player)) {
                            player.sendMessage(pfcPrefix + "§eVeuillez attendre la fin de votre partie !");
                            return false;
                        } else {
                            pfcListGameByPlayer.put(player, target);
                            PfcGuiConfirmation.INVENTORY.open(player);
                        }

                        /*
                        if (!hasClickedMessage.containsKey(player) || !hasClickedMessage.get(player)) {
                          // CHALLENGER
                           if (pfcListGameByPlayer.containsKey(target)) {
                             player.sendMessage(pfcPrefix + "§eCe joueur est déjà dans une partie");
                              return false;
                          }

                            pfcListGameByPlayer.put(player, target);
                            PfcGui.INVENTORY.open(player);

                            // OPPONENT
                            if (pfcListGameByPlayer.containsKey(target)) {
                                pfcListGameByPlayer.remove(target);
                            }
                            pfcListGameByPlayer.put(target, player);
                            PfcGui.INVENTORY.open(target);
                        }  else {
                            player.sendMessage(pfcPrefix + "§6Tu dois lui renvoyer une invitation !");
                       }

                         */

                    } else {
                        player.sendMessage(pfcPrefix + "§6" +  args.getArgs(1) + "§e n'est pas en ligne");
                        return false;
                    }
                }
            } else {
                player.sendMessage(pfcPrefix + "§6Il faut préciser un joueur !");
            }
            return true;

        }
        return false;
    }


}
