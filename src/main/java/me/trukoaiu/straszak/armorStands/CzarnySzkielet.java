package me.trukoaiu.straszak.armorStands;

import me.trukoaiu.straszak.Straszak;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import static org.bukkit.Bukkit.getServer;

public class CzarnySzkielet extends BaseArmorStand {
    private boolean shouldContinue = false;
    private boolean isDeleteSet = false;
    private boolean wasSoundPlayed = false;
    private boolean shouldChase = false;
    private boolean chasingBool = true;

    public ArmorStand spawnCzarnySzkielet(Location location, Player p){
        ArmorStand szkielet = getBaseArmorStand(location);

        equipArmor(szkielet);

        int spootThePlayer = Bukkit.getScheduler().scheduleSyncRepeatingTask(Straszak.instance, () -> {
            if (isFacingArmorStand(p, szkielet)){
                shouldContinue = true;
                Location szkieletLocation = szkielet.getLocation();
                float szkieletNewYaw = (float) Math.toDegrees(
                        Math.atan2(p.getLocation().getZ() - szkieletLocation.getZ(), p.getLocation().getX() - szkieletLocation.getX()));
                szkieletNewYaw = (float) adderRadian180Equalizer(szkieletNewYaw, -90.);

                szkielet.teleport(new Location(
                        szkielet.getWorld(),
                        szkieletLocation.getX(),
                        szkieletLocation.getY(),
                        szkieletLocation.getZ(),
                        szkieletNewYaw,
                        szkieletLocation.getPitch()
                ));
            }
            szkielet.setHeadPose(new EulerAngle(
                    Math.toRadians(Math.random()*80-40),
                    Math.toRadians(Math.random()*80-40),
                    0.));

            szkielet.setRightArmPose(new EulerAngle(
                    Math.toRadians(Math.random()*360-180),
                    Math.toRadians(Math.random()*360-180),
                    Math.toRadians(0.)
            ));

            szkielet.setLeftArmPose(new EulerAngle(
                    Math.toRadians(Math.random()*360-180),
                    Math.toRadians(Math.random()*360-180),
                    Math.toRadians(0.)
            ));

        }, 0, 1L);

        //Szkielet bignie do gracza
        int catchThePlayer = Bukkit.getScheduler().scheduleSyncRepeatingTask(Straszak.instance, () -> {
            if (shouldContinue){
                Location playerLocation = p.getLocation();
                Location szkieletLocation = szkielet.getLocation();

                if (!wasSoundPlayed){
                    wasSoundPlayed = true;
                    p.playSound(szkieletLocation, Sound.ENTITY_ENDERMAN_DEATH, 0.9F, 0.8F);
                }

                if (shouldChase){
                    double distance = szkieletLocation.distance(playerLocation);
                    Vector normalizedDistance = playerLocation.toVector().subtract(szkieletLocation.toVector()).normalize();

                    double predkosc = Straszak.getPlugin().getConfig().getDouble("szkielet-predkosc");
                    predkosc /= 20;
                    if (distance < 0.6) {
                        predkosc /= 2;
                    } else if (distance < 0.2) {
                        predkosc = 0.2;
                    }
                    Vector teleportOffset = normalizedDistance.multiply(predkosc);

                    Location teleportLocation = szkieletLocation.clone().add(teleportOffset);
                    szkielet.teleport(teleportLocation);
                    if (distance < 0.15 && !isDeleteSet) {
                        isDeleteSet = true;
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
                            szkielet.remove();
                        }, 4L);
                    }
                } else {
                    if (chasingBool) {
                        chasingBool = false;
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
                            shouldChase = true;
                        }, 10L);
                    }
                }
            }
        }, 0, 1L);

        double czasTrwania = Straszak.getPlugin().getConfig().getDouble("czas-trwanie");
        if (czasTrwania == 0) {
            czasTrwania = 7.;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
            if (!shouldContinue) {
                if (Bukkit.getScheduler().isQueued(spootThePlayer)){
                    Bukkit.getScheduler().cancelTask(spootThePlayer);
                }
                if (Bukkit.getScheduler().isQueued(catchThePlayer)){
                    Bukkit.getScheduler().cancelTask(catchThePlayer);
                }
                szkielet.remove();
            }
        }, 20L * (long) czasTrwania);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
            if (Bukkit.getScheduler().isQueued(spootThePlayer)){
                Bukkit.getScheduler().cancelTask(spootThePlayer);
            }
            if (Bukkit.getScheduler().isQueued(catchThePlayer)){
                Bukkit.getScheduler().cancelTask(catchThePlayer);
            }
            if (!szkielet.isDead()){
                szkielet.remove();
            }
        }, 100L + 20L * (long) czasTrwania);

        return szkielet;
    }

    @Override
    public ArmorStand equipArmor(ArmorStand armorStand) {
        //Hat
        armorStand.getEquipment().setHelmet(new ItemStack(Material.WITHER_SKELETON_SKULL));
        //Chestplate
        ItemStack blackChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta blackChestplateMeta = (LeatherArmorMeta) blackChestplate.getItemMeta();
        blackChestplateMeta.setColor(Color.BLACK);
        blackChestplate.setItemMeta(blackChestplateMeta);
        armorStand.getEquipment().setChestplate(blackChestplate);
        //Legs
        ItemStack blackLegins = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta blackLeginsMeta = (LeatherArmorMeta) blackLegins.getItemMeta();
        blackLeginsMeta.setColor(Color.BLACK);
        blackLegins.setItemMeta(blackLeginsMeta);
        armorStand.getEquipment().setLeggings(blackLegins);

        return armorStand;
    }
}
