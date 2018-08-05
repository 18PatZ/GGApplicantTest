package com.goodgaming.applicanttest;

import com.goodgaming.applicanttest.command.ATCommand;
import com.goodgaming.applicanttest.command.BalanceCommand;
import com.goodgaming.applicanttest.command.EcoCommand;
import com.goodgaming.applicanttest.data.DataManager;
import com.goodgaming.applicanttest.data.EcoManager;
import com.goodgaming.applicanttest.listener.EventListener;
import com.patrickzhong.spark.SparkPlugin;
import com.patrickzhong.spark.util.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

/**
 * Created by patrickzhong on 8/4/18.
 */
public class ApplicantTest extends SparkPlugin {

    @Getter private static ApplicantTest instance;
    @Setter public static boolean logging = true;

    @Override
    public void onEnable() {

        instance = this;

        new ATCommand().register(this);
        new EcoCommand().register(this);
        new BalanceCommand().register(this);
        new DataManager();
        new EcoManager();

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        super.onEnable();

    }

    @Override
    public void onDisable() {

        EcoManager.getInstance().saveAll();

        super.onDisable();
    }

    public static void log(String s){
        if(logging)
            Bukkit.getConsoleSender().sendMessage(CC.translate("&e&l[AT] &7" + s));
    }

    public static String format(int i){
        return format(i+"");
    }

    public static String format(String s){
        String r = "";
        for(int j = s.length()-1; j >= 0; j--){
            r = s.charAt(j) + r;
            if((s.length() - j) % 3 == 0)
                r = "," + r;
        }

        if(r.startsWith(","))
            return r.substring(1);

        return r;
    }

}
