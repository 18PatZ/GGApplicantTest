package com.goodgaming.applicanttest.command;

import com.goodgaming.applicanttest.entity.CustomSheep;
import com.patrickzhong.spark.command.CommandInfo;
import com.patrickzhong.spark.command.SparkCommand;
import com.patrickzhong.spark.util.CC;
import org.bukkit.entity.Player;

/**
 * Created by patrickzhong on 8/4/18.
 */
@CommandInfo(name = "at", aliases = {"applicanttest", "gg"})
public class ATCommand extends SparkCommand {

    @Override
    protected void onCommand(Player player, String[] args) {
        if(player.isOp()){
            CustomSheep.spawn(player.getLocation());
            player.sendMessage(CC.Good + "Summoned custom sheep.");
        }
    }

}
