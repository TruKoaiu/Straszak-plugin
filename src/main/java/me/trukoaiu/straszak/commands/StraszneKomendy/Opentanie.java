package me.trukoaiu.straszak.commands.StraszneKomendy;

import me.trukoaiu.straszak.Straszak;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Opentanie {

    public void OpentanieGracza(Player p){
        double okres = Straszak.getPlugin().getConfig().getDouble("opetanie-co-ile-sek");
        int proces = Bukkit.getScheduler().scheduleSyncRepeatingTask(Straszak.instance, () -> {
            Location pLocation = p.getLocation();

            float newYaw = (float) Math.random() * 360 - 180;
            float newPitch = (float) Math.random() * 120 - 60;
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20,1));

            p.teleport(new Location(
                    p.getWorld(),
                    pLocation.getX(),
                    pLocation.getY(),
                    pLocation.getZ(),
                    newYaw,
                    newPitch
            ));
        }, 0, 20L * (long) okres);

        int czasTrwania = Straszak.getPlugin().getConfig().getInt("opetanie-trwanie");

        Bukkit.getScheduler().scheduleSyncDelayedTask(Straszak.instance, () -> {
            if (Bukkit.getScheduler().isQueued(proces)){
                Bukkit.getScheduler().cancelTask(proces);
            }
        }, 20L * czasTrwania);
    }
}
