package com.goodgaming.applicanttest.entity;

import com.goodgaming.applicanttest.ApplicantTest;
import com.patrickzhong.spark.util.CC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by patrickzhong on 8/4/18.
 */
public class CustomSheep extends EntitySheep {

    /**
     * A custom sheep that stands still and stares at you.
     */

    public CustomSheep(WorldServer world) {
        super(world);

        try {

            wipe("b");
            wipe("c");

            ApplicantTest.log("Setting goals...");

            Method nav = getNavigation().getClass().getDeclaredMethod("b", boolean.class);
            nav.setAccessible(true);
            nav.invoke(getNavigation(), true);
            this.goalSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));

            setCustomNameVisible(true);
            setCustomName(CC.AquaB + "SHEEP");

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void wipe(String name) throws NoSuchFieldException, IllegalAccessException {
        Field c = PathfinderGoalSelector.class.getDeclaredField(name);
        c.setAccessible(true);
        c.set(goalSelector, new ArrayList<>());
        c.set(targetSelector, new ArrayList<>());
    }

    private static boolean registered = false;

    public static void register(){

        ApplicantTest.log("Custom sheep not registered. Registering now...");

        try {
            getMap("c").remove("Sheep");
            getMap("e").remove(91);

            Method register = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
            register.setAccessible(true);
            register.invoke(null, CustomSheep.class, "Sheep", 91);

            registered = true;

        } catch (NoSuchMethodException | NoSuchFieldException | IllegalAccessException | InvocationTargetException e1) {
            e1.printStackTrace();
        }
    }

    private static Map getMap(String name) throws NoSuchFieldException, IllegalAccessException {
        Field map = EntityTypes.class.getDeclaredField(name);
        map.setAccessible(true);
        return (Map) map.get(null);
    }

    public static void spawn(Location loc){

        ApplicantTest.log("Spawning new instance...");

        if(!registered)
            register();

        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        CustomSheep sheep = new CustomSheep(world);
        sheep.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        world.addEntity(sheep);

        ApplicantTest.log("DONE.");

    }



}
