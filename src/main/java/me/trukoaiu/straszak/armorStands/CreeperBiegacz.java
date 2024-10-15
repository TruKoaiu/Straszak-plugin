package me.trukoaiu.straszak.armorStands;

import me.trukoaiu.straszak.Straszak;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

public class CreeperBiegacz extends BaseArmorStand {
    private boolean wasSoundPlayed = false;
    private Location targetLocation;

    public ArmorStand spawnBiegacz(Location location, Player p) {
        //Base armor stand
        ArmorStand biegacz = getBaseArmorStand(location);
        equipArmor(biegacz);

        //calculate targeted location for the "biegacz" to run to
        Location playerLocation = p.getLocation();
        Location primaryBiegaczLocation = biegacz.getLocation();
        Vector normalizeVector = playerLocation.toVector().subtract(primaryBiegaczLocation.toVector()).normalize();
        targetLocation = playerLocation.clone().add(normalizeVector.multiply(2));

        //Szkielet bignie do gracza
        int runAfterPlayer = Bukkit.getScheduler().scheduleSyncRepeatingTask(Straszak.instance, () -> {
            Location biegaczLocation = biegacz.getLocation();
            if (!wasSoundPlayed){
                wasSoundPlayed = true;
                p.playSound(biegaczLocation, Sound.ENTITY_WITHER_DEATH, 0.9F, 1.5F);
            }
            double distance = biegaczLocation.distance(playerLocation);
            Vector normalizedDistance = targetLocation.toVector().subtract(primaryBiegaczLocation.toVector()).normalize();

            double predkosc = Straszak.getPlugin().getConfig().getDouble("szkielet-predkosc");
            predkosc /= 20;
            Vector teleportOffset = normalizedDistance.multiply(predkosc);

            Location teleportLocation = biegaczLocation.clone().add(teleportOffset);
            biegacz.teleport(teleportLocation);
        }, 0, 1L);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
                if (Bukkit.getScheduler().isQueued(runAfterPlayer)){
                    Bukkit.getScheduler().cancelTask(runAfterPlayer);
                }
                biegacz.remove();

        }, 17L);
        return biegacz;
    }

    @Override
    public ArmorStand equipArmor(ArmorStand armorStand) {
        //Hat
        armorStand.getEquipment().setHelmet(new ItemStack(Material.CREEPER_HEAD));
        //Chestplate
        ItemStack redChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta redChestplateMeta = (LeatherArmorMeta) redChestplate.getItemMeta();
        redChestplateMeta.setColor(Color.RED);
        redChestplate.setItemMeta(redChestplateMeta);
        armorStand.getEquipment().setChestplate(redChestplate);
        //Legs
        ItemStack whiteLegins = new ItemStack(Material.IRON_LEGGINGS);
        armorStand.getEquipment().setLeggings(whiteLegins);

        return armorStand;
    }
}
