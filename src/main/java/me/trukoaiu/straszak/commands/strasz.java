package me.trukoaiu.straszak.commands;

import me.trukoaiu.straszak.Straszak;
import me.trukoaiu.straszak.armorStands.AniolSmierci;
import me.trukoaiu.straszak.armorStands.CreeperBiegacz;
import me.trukoaiu.straszak.armorStands.CzarnySzkielet;
import me.trukoaiu.straszak.commands.StraszneKomendy.ChordaCreeperow;
import me.trukoaiu.straszak.commands.StraszneKomendy.ChordaNietoperzy;
import me.trukoaiu.straszak.commands.StraszneKomendy.ChordaWilkow;
import me.trukoaiu.straszak.commands.StraszneKomendy.Opentanie;
import me.trukoaiu.straszak.utility.RadianFunctionality;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.util.StringUtil;

import java.util.*;

public class strasz implements CommandExecutor {

    public String[] strings1 = {"Demon", "Jumpscare", "Wilki", "Nietoperze", "Aniol", "Muzyka", "Creeper", "Kapelusz", "Opetanie"};

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player p){
            if (isPlayerOnline(strings[0])){
                Player targetPlayer = Bukkit.getPlayer(strings[0]);
                if (strings[1].equalsIgnoreCase("demon")) {
                    NieOdwracajSie(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("jumpscare")) {
                    BiegCreepera(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("wilki")) {
                    PrzyzwijWilki(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("nietoperze")) {
                    PrzyzwijNietoperze(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("aniol")) {
                    AniolSmierci(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("muzyka")) {
                    PlayMusic(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("creeper")) {
                    SpawnCreeper(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("kapelusz")) {
                    spawnBatOnPlayerHead(targetPlayer);
                } else if (strings[1].equalsIgnoreCase("opetanie")) {
                    utrataKontroli(targetPlayer);
                } else {
                    p.sendMessage(ChatColor.RED + "Nie ma takiej metody.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "Nie ma takiego gracza.");
            }
        }
        return true;
    }

    public boolean isPlayerOnline(String playerName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }

    public void NieOdwracajSie(Player p){
        Location playerLocation = p.getLocation();

        float yaw = playerLocation.getYaw();
        double radians = Math.toRadians(yaw);
        double xVector = -Math.sin(radians);
        double zVector = Math.cos(radians);

        Location spawnLocation = playerLocation.clone().add(xVector * -5, 0, zVector * -5);

        ArmorStand spawnCzarnySzkielet = new CzarnySzkielet().spawnCzarnySzkielet(spawnLocation, p);

        String wiadomosc = Straszak.getPlugin().getConfig().getString("szkielet-wiadomosc");
        p.sendTitle(ChatColor.RED + "", ChatColor.RED + wiadomosc, 5, 15, 15);
    }

    public void AniolSmierci(Player p){
        Location playerLocation = p.getLocation();

        float yaw = playerLocation.getYaw();
        double radians = Math.toRadians(yaw);
        double xVector = -Math.sin(radians);
        double zVector = Math.cos(radians);

        Location spawnLocation = playerLocation.clone().add(xVector * -5.5, 0, zVector * -5.5);

        ArmorStand spawnAniolSmierci = new AniolSmierci().spawnAniolSmierci(spawnLocation, p);

        String wiadomosc = Straszak.getPlugin().getConfig().getString("aniol-wiadomosc");
        if (wiadomosc == null) {
            wiadomosc = "Hello World";
        }
        p.sendTitle(ChatColor.RED + "", ChatColor.GREEN + wiadomosc, 5, 15, 15);
    }

    public void BiegCreepera(Player p) {
        Location playerLocation = p.getLocation();

        float yaw = playerLocation.getYaw();
        double radians = Math.toRadians(yaw);
        double xVector = -Math.sin(radians);
        double zVector = Math.cos(radians);

        Location spawnLocation = playerLocation.clone().add(xVector * 3, -0.1, zVector * 3);
        spawnLocation.setYaw((float) RadianFunctionality.adderRadian180Equalizer(spawnLocation.getYaw(), -180.0));

        ArmorStand spawnCreeperBiegacz = new CreeperBiegacz().spawnBiegacz(spawnLocation, p);
    }

    public void PrzyzwijWilki(Player p){
        int liczba = Straszak.getPlugin().getConfig().getInt("wilki-ilosc");
        for (int i = 0; i < liczba; i++) {
            new ChordaWilkow().summonWilk(p);
        }
    }

    public void PrzyzwijNietoperze(Player p){
        int liczba = Straszak.getPlugin().getConfig().getInt("nietoperze-ilosc");
        for (int i = 0; i < liczba; i++) {
            new ChordaNietoperzy().summonNietoperz(p);
        }
    }

    public void PlayMusic(Player p) {
        p.playSound(p.getLocation(), Sound.MUSIC_DISC_13, 0.6f, 0.7f);
        p.playSound(p.getLocation(), Sound.MUSIC_DISC_5, 0.8f, 1.0f);

        int time = Straszak.getPlugin().getConfig().getInt("muzyka-trwanie");

        Bukkit.getScheduler().runTaskLater(Straszak.getPlugin(), () -> {
            p.stopSound(Sound.MUSIC_DISC_13);
            p.stopSound(Sound.MUSIC_DISC_5);
        }, 20L * time);
    }

    public void SpawnCreeper(Player p) {
        for (int i = 0; i < Math.random() * 3; i++) {
            new ChordaCreeperow().summonCreeper(p);
        }
    }

    public void spawnBatOnPlayerHead(Player p) {
        Bat bat = (Bat) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
        p.addPassenger(bat);
        p.playSound(p.getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 0.8F, 1.0F);
    }

    public void utrataKontroli(Player p) {
        new Opentanie().OpentanieGracza(p);
    }
}
