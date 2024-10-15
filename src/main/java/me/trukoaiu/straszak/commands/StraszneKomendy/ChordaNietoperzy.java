package me.trukoaiu.straszak.commands.StraszneKomendy;

import me.trukoaiu.straszak.Straszak;
import org.bukkit.Bukkit;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class ChordaNietoperzy {

    public void summonNietoperz(Player p){
        Bat bat = (Bat) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.BAT);

        double czasTrwania = Straszak.getPlugin().getConfig().getDouble("nietoperze-trwanie");
        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
            bat.remove();
        }, 20L * (long) czasTrwania);
    }
}
