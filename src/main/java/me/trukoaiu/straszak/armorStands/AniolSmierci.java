package me.trukoaiu.straszak.armorStands;

import me.trukoaiu.straszak.Straszak;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class AniolSmierci extends BaseArmorStand {

//    private final Straszak plugin;
//
//    public AniolSmierci(Straszak plugin) {
//        this.plugin = plugin;
//    }

    private boolean shouldMove = true;
    private boolean haveKilled = false;


    public ArmorStand spawnAniolSmierci(Location location, Player p) {
        ArmorStand aniol = getBaseArmorStand(location);

        aniol.setRightArmPose(new EulerAngle(
                Math.toRadians(330.),
                Math.toRadians(-10.),
                Math.toRadians(168.)
        ));

        equipArmor(aniol);


        int AproachPlayer = Bukkit.getScheduler().scheduleSyncRepeatingTask(Straszak.instance, () -> {
            if (!isFacingArmorStand(p, aniol) && shouldMove){
                Location playerLocation = p.getLocation();
                Location aniolLocation = aniol.getLocation();

                float aniolNewYaw = (float) Math.toDegrees(
                        Math.atan2(p.getLocation().getZ() - aniolLocation.getZ(), p.getLocation().getX() - aniolLocation.getX()));
                aniolNewYaw = (float) adderRadian180Equalizer(aniolNewYaw, -90.);

                aniol.teleport(new Location(
                        aniol.getWorld(),
                        aniolLocation.getX(),
                        aniolLocation.getY(),
                        aniolLocation.getZ(),
                        aniolNewYaw,
                        aniolLocation.getPitch()
                ));

                aniolLocation = aniol.getLocation();
                double distance = aniolLocation.distance(playerLocation);
                Vector normalizedVector = playerLocation.toVector().subtract(aniolLocation.toVector()).normalize();

                double predkosc = Straszak.getPlugin().getConfig().getDouble("aniol-predkosc");
                predkosc /= 20;
                if (predkosc == 0.) {
                    predkosc = 0.15;
                }
                Vector teleportOffset = normalizedVector.multiply(predkosc);

                Location teleportLocation = aniolLocation.clone().add(teleportOffset);
                aniol.teleport(teleportLocation);
                if (distance < 0.3 && !haveKilled && !aniol.isDead()) {
                    haveKilled = true;
                    shouldMove = false;
                    p.playSound(playerLocation, Sound.ENTITY_GHAST_SCREAM, 0.9F, 0.5F);
                    p.setHealth(0.);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
                        aniol.remove();
                    }, 14L);
                }
            };
        }, 0, 1L);

        double czasTrwania = Straszak.getPlugin().getConfig().getDouble("aniol-trwanie");

        if (czasTrwania == 0.) {
             czasTrwania = 12.0;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
            if (Bukkit.getScheduler().isQueued(AproachPlayer)){
                Bukkit.getScheduler().cancelTask(AproachPlayer);
            }
            if (!aniol.isDead()){
                aniol.remove();
            }
        }, 20L * (long) czasTrwania);


        return aniol;
    }

    @Override
    public ArmorStand equipArmor(ArmorStand armorStand) {
        //Hat
        armorStand.getEquipment().setHelmet(new ItemStack(Material.SKELETON_SKULL));
        //Chestplate
        ItemStack Wings = new ItemStack(Material.ELYTRA);
        armorStand.getEquipment().setChestplate(Wings);
        //Legs
        ItemStack whiteLegins = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta whiteLeginsMeta = (LeatherArmorMeta) whiteLegins.getItemMeta();
        whiteLeginsMeta.setColor(Color.WHITE);
        whiteLegins.setItemMeta(whiteLeginsMeta);
        armorStand.getEquipment().setLeggings(whiteLegins);
        //Hand
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        armorStand.getEquipment().setItemInMainHand(sword);

        return armorStand;
    }

}
