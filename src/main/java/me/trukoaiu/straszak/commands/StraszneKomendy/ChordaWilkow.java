package me.trukoaiu.straszak.commands.StraszneKomendy;

import me.trukoaiu.straszak.Straszak;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class ChordaWilkow {

    public void summonWilk(Player p){
        Wolf wolf = (Wolf) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
        wolf.setAngry(true);
        wolf.setTarget(p);

        double czasTrwania = Straszak.getPlugin().getConfig().getDouble("wilki-trwanie");
        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
            wolf.remove();
        }, 20L * (long) czasTrwania);
    }

}
