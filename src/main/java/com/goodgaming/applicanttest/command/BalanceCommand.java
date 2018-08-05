package com.goodgaming.applicanttest.command;

import com.goodgaming.applicanttest.data.EcoManager;
import com.patrickzhong.spark.command.CommandException;
import com.patrickzhong.spark.command.CommandInfo;
import com.patrickzhong.spark.command.SparkCommand;
import com.patrickzhong.spark.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by patrickzhong on 8/5/18.
 */
@CommandInfo(name = "bal", aliases = {"balance", "abal"})
public class BalanceCommand extends SparkCommand {

    @Override
    protected void onCommand(CommandSender sender, String[] args) {

        if(args.length > 0) {
            Player target = getPlayer(args[0]);
            sender.sendMessage(CC.translate("&b&l(!) &b" + target.getName() + " &7has a balance of &b&l$" + EcoManager.getInstance().getBal(target) + "&7."));
        }
        else {
            if(!(sender instanceof Player))
                throw new CommandException("You must be a player to check your own balance!");

            sender.sendMessage(CC.translate("&b&l(!) &7You have a balance of &b&l$" + EcoManager.getInstance().getBal((Player) sender) + "&7."));
        }

    }

}
