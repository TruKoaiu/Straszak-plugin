package me.trukoaiu.straszak.commands.StraszneKomendy;

import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ChordaCreeperow {

    public void summonCreeper(Player p){
        Creeper creeper = (Creeper) p.getLocation().getWorld().spawnEntity(getRandomLocation(p), EntityType.CREEPER);
        creeper.setExplosionRadius(0);
        creeper.ignite();
        creeper.setGravity(false);
        creeper.setAI(false);
    }

    public static Location getRandomLocation(Player p) {
        Location playerLocation = p.getLocation();

        double playerX = playerLocation.getX();
        double playerZ = playerLocation.getZ();

        boolean addX = Math.random() < 0.5;
        boolean addZ = Math.random() < 0.5;

        double newX = addX ? playerX + 1 : playerX - 1;
        double newZ = addZ ? playerZ + 1 : playerZ - 1;

        Location randomLocation = new Location(p.getWorld(), newX, playerLocation.getY(), newZ);

        return randomLocation;
    }
}
