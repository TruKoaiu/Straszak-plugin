package me.trukoaiu.straszak.armorStands;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BaseArmorStand {

    //Set base
    public ArmorStand getBaseArmorStand(Location location) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setArms(true);
        armorStand.setInvulnerable(true);
        armorStand.setBasePlate(false);
        armorStand.setGravity(false);
        armorStand.setCollidable(false);

        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING);
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING);
        armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING);
        armorStand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING);

        return armorStand;
    }
    public ArmorStand equipArmor(ArmorStand armorStand) {
        //Armor
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

    public static boolean isFacingArmorStand(Player p, ArmorStand armorStand) {
        Location playerLocation = p.getLocation();
        Location armorStandLocation = armorStand.getLocation();
        float playerStabilizedYaw = p.getLocation().getYaw() - 90;
        if (playerStabilizedYaw <= -180) {
            playerStabilizedYaw += 360;
        }

        double angleToArmorStand = Math.atan2(armorStandLocation.getZ() - playerLocation.getZ(), armorStandLocation.getX() - playerLocation.getX());
        double degreesToArmorStand = Math.toDegrees(angleToArmorStand);
        double difference = Math.abs(adderRadian180Equalizer(playerStabilizedYaw, -180.) - degreesToArmorStand);

        double threshold = 65.0;

        if (difference > (360-threshold) || difference < (threshold)) {
            return true;
        }
        return false;
    }

    public static double adderRadian180Equalizer(double base, double addValue){
        double result = base + addValue;
        if (result > 180) {
            result -= 360;
        } else if (result < -180) {
            result += 360;
        }

        return result;
    }
}
