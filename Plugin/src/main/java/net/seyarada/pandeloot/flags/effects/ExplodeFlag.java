package net.seyarada.pandeloot.flags.effects;

import net.seyarada.pandeloot.drops.IDrop;
import net.seyarada.pandeloot.drops.LootDrop;
import net.seyarada.pandeloot.flags.FlagEffect;
import net.seyarada.pandeloot.flags.FlagPack;
import net.seyarada.pandeloot.flags.enums.FlagTrigger;
import net.seyarada.pandeloot.flags.types.IEntityEvent;
import net.seyarada.pandeloot.utils.MathUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

@FlagEffect(id="explode", description="Applies an explosion-like velocity to the item")
public class ExplodeFlag implements IEntityEvent {

	@Override
	public void onCallEntity(Entity item, FlagPack.FlagModifiers values, @Nullable LootDrop lootDrop, @Nullable IDrop iDrop, FlagTrigger trigger) {
		if(!values.getBoolean()) return;

		String explosionType = values.getOrDefault("type", "spread");

		switch (explosionType) {
			case "radial" -> doRadialDrop(item, values, lootDrop);
			case "circular" -> doCircularDrop(item, values);
			default -> doSpreadDrop(item, values);
		}

	}


	void doSpreadDrop(Entity item, FlagPack.FlagModifiers values) {
		final double offset = Double.parseDouble(values.getOrDefault("offset", "0.2"));
		final double height = Double.parseDouble(values.getOrDefault("height", "0.6"));
		Vector velocity = MathUtils.getVelocity(offset, height);

		item.setVelocity(velocity);
	}

	void doCircularDrop(Entity item, FlagPack.FlagModifiers values) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		double radius = Double.parseDouble(values.getOrDefault("radius", "3"));

		Vector destination = new Vector(Math.cos(rand.nextDouble() * 360) * radius, 0, Math.sin(rand.nextDouble() * 360) * radius);
		item.setVelocity(MathUtils.calculateVelocity(item.getLocation().toVector(), destination, 0.115, 3));
	}

	void doRadialDrop(Entity item, FlagPack.FlagModifiers values, LootDrop drop) {
		double radius = Double.parseDouble(values.getOrDefault("radius", "3"));

		int numberOfTotalDrops = 1;
		int numberOfThisDrop = 1;

		if(drop!=null) {
			numberOfTotalDrops = drop.size();
			numberOfThisDrop = Integer.parseInt(drop.data.getOrDefault("radialNumber", "1"));
			drop.data.put("radialNumber", String.valueOf(numberOfThisDrop+1));
		}

		if(numberOfTotalDrops==1) {
			doSpreadDrop(item, values);
			return;
		}

		double angle = 2 * Math.PI / numberOfTotalDrops;
		double cos = Math.cos(angle * numberOfThisDrop);
		double sin = Math.sin(angle * numberOfThisDrop);
		double iX = item.getLocation().getX() + radius * cos;
		double iZ = item.getLocation().getZ() + radius * sin;

		Location loc = item.getLocation().clone();
		loc.setX(iX);
		loc.setZ(iZ);

		item.setVelocity(MathUtils.calculateVelocity(item.getLocation().toVector(), loc.toVector(), 0.115, 3));
	}

}
