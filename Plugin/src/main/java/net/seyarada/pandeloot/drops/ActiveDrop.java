package net.seyarada.pandeloot.drops;

import net.md_5.bungee.api.ChatColor;
import net.seyarada.pandeloot.Constants;
import net.seyarada.pandeloot.PandeLoot;
import net.seyarada.pandeloot.drops.containers.IContainer;
import net.seyarada.pandeloot.flags.FlagManager;
import net.seyarada.pandeloot.flags.FlagPack;
import net.seyarada.pandeloot.flags.effects.GlowFlag;
import net.seyarada.pandeloot.flags.enums.FlagTrigger;
import net.seyarada.pandeloot.nms.NMSManager;
import net.seyarada.pandeloot.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ActiveDrop {

    public static HashMap<Entity, ActiveDrop> activeDropItem = new HashMap<>();

    int rainbowRunnableID = -1;
    int beamRunnableID = -1;
    int lootbagRollerID = -1;
    int hologramRunnableID = -1;
    int flyingParticleRunnable = -1;
    List<Entity> holograms;
    List<Player> hologramsPlayers;
    float rainbowDegrees = 0;

    public boolean canBePickedUp = true;


    public int amountOpened = 0;

    private ChatColor color = Constants.ACCENT;

    Entity e;
    Player p;
    FlagPack flags;
    LootDrop lootDrop;
    IDrop iDrop;

    public ActiveDrop(IDrop drop, Entity e, Player p, FlagPack pack, LootDrop lootDrop) {
        this.e = e;
        this.p = p;
        this.flags = drop.getFlagPack();
        this.lootDrop = lootDrop;
        this.iDrop = drop;

        activeDropItem.put(e, this);
        pack.trigger(FlagTrigger.onspawn, e, lootDrop, drop);

        if(pack.flags.containsKey(FlagTrigger.onland))
            new ActiveDropListener().checkForLandings(e, pack);
    }

    public void startRainbowRunnable() {
        rainbowRunnableID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PandeLoot.inst, () -> {
            if(!e.isValid()) cancel();

            rainbowDegrees += 0.05;
            color = ChatColor.of(Color.getHSBColor(rainbowDegrees, 0.5f, 1));

            updateColors();

        }, 0, 3);
    }

    public void startBeamRunnable(double height) {
        beamRunnableID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PandeLoot.inst, () -> {
            if(!e.isValid() || lootDrop.p==null ) cancel();

            if(e.isOnGround()) {
                double modHeight = height;
                while(modHeight>0) {
                    Particle.DustOptions dustOptions = new Particle.DustOptions(org.bukkit.Color.fromRGB(color.getColor().getRed(), color.getColor().getGreen(), color.getColor().getBlue()), 1);
                    lootDrop.p.spawnParticle(Particle.REDSTONE, e.getLocation().add(0, 0.15+modHeight, 0), 1, dustOptions);
                    modHeight = modHeight - 0.1;
                }
            }

        }, 0, 3);
    }

    public void startFlyingParticleRunnable() {
        flyingParticleRunnable = Bukkit.getScheduler().scheduleSyncRepeatingTask(PandeLoot.inst, () -> {
            if(!e.isValid() || lootDrop.p==null ) cancel();

            if(!e.isOnGround()) {
                Particle.DustOptions dustOptions = new Particle.DustOptions(org.bukkit.Color.fromRGB(color.getColor().getRed(), color.getColor().getGreen(), color.getColor().getBlue()), 1);
                lootDrop.p.spawnParticle(Particle.REDSTONE, e.getLocation(), 1, dustOptions);
            }

        }, 0, 2);
    }

    public void startLootBagRollRunnable(IContainer bag, LootDrop drop, FlagPack flags) {
        Item i = (Item)e;
        FlagPack droppedFlags = new FlagPack();
        droppedFlags.merge(flags);
        droppedFlags.flags.remove(FlagTrigger.onspawn);
        List<IDrop> drops = bag.getDropList(drop);
        lootbagRollerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PandeLoot.inst, () -> {
            if(!e.isValid()) cancel();

            IDrop iDrop = drops.get((int) (Math.random() * drops.size()));
            ItemStack iS = iDrop.getItemStack();
            ItemUtils.writeData(iS, Constants.LOCK_LOOTBAG, "true");
            ItemUtils.writeData(iS, Constants.LOOTBAG_KEY, bag.getConfig().getName());
            FlagPack combinedPack = new FlagPack();
            combinedPack.merge(iDrop.getFlagPack());
            combinedPack.merge(droppedFlags);
            ItemUtils.setFlags(iS, combinedPack);

            combinedPack.trigger(FlagTrigger.onroll, i, drop.p);

            i.setItemStack(iS);

        }, 0, 10);
    }

    public void stopLootBagRunnable() {
        Bukkit.getScheduler().cancelTask(lootbagRollerID);
        lootbagRollerID = -1;
    }

    public void startHologramRunnable(Entity e, List<Entity> holograms, List<Player> players) {
        this.holograms = holograms;
        this.hologramsPlayers = players;
        final Location[] oldLoc = {e.getLocation()};
        hologramRunnableID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PandeLoot.inst, () -> {
            if(!e.isValid()) cancel();
            if(oldLoc[0].equals(e.getLocation())) return;
            oldLoc[0] = e.getLocation();

            Location tempLoc = oldLoc[0].clone();

            tempLoc.add(0,0.25,0);
            for (Entity i : holograms) {
                tempLoc.add(0,0.22,0);
                if (i == null) continue;
                for (Player player : players) {
                    NMSManager.get().updateHologramPosition(tempLoc.getX(), tempLoc.getY(), tempLoc.getZ(), i, player);
                }
            }

        }, 0, 3);
    }

    public void setColor(ChatColor color) {
        this.color = color;
        updateColors();
    }
    public ChatColor getColor() {
        return color;
    }

    public void updateColors() {
        if(e.isGlowing()) {
            if(flyingParticleRunnable==-1) startFlyingParticleRunnable();
            ((GlowFlag) FlagManager.getFromID("glow")).updateColor(e, color, p);
        }
    }

    void cancel() {
        e.remove();
        if(rainbowRunnableID>0) Bukkit.getScheduler().cancelTask(rainbowRunnableID);
        if(beamRunnableID>0) Bukkit.getScheduler().cancelTask(beamRunnableID);
        if(flyingParticleRunnable>0) Bukkit.getScheduler().cancelTask(flyingParticleRunnable);
        if(lootbagRollerID>0) Bukkit.getScheduler().cancelTask(lootbagRollerID);
        if(hologramRunnableID>0) {
            Bukkit.getScheduler().cancelTask(hologramRunnableID);
            for (Player player : hologramsPlayers) {
                if (!player.isOnline()) continue;
                holograms.stream().filter(Objects::nonNull).forEach(e -> NMSManager.get().destroyEntity(e.getEntityId(), player));
            }
        }
    }

    public static ActiveDrop get(Entity i) {
        return activeDropItem.get(i);
    }

}
