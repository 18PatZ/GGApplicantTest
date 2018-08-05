package com.goodgaming.applicanttest.command;

import com.goodgaming.applicanttest.ApplicantTest;
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
@CommandInfo(name = "eco", aliases = {"aeco"})
public class EcoCommand extends SparkCommand {

    @Override
    protected void onCommand(CommandSender sender, String[] args) {

        if(!sender.hasPermission("testproject.command.eco"))
            throw new CommandException("You don't have permission to do this!");

        checkArgs(args, 3, "/eco give <player> <amount>");

        Player target = getPlayer(args[1]);
        int amount;

        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            throw new CommandException("The amount must be a number!");
        }

        if(amount <= 0)
            throw new CommandException("The amount must be positive!");

        EcoManager.getInstance().addBal(target, amount);
        String formatted = ApplicantTest.format(amount);
        sender.sendMessage(CC.translate("&b&l(!) &7Sent &b" + target.getName() + " &7an additional &b$" + formatted + "&7."));
        target.sendMessage(CC.translate("&b&l(!) &7You received &b&l$" + formatted + "&7!"));

    }

}
