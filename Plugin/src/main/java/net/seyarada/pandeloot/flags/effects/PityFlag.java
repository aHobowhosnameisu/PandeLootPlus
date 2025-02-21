package net.seyarada.pandeloot.flags.effects;

import net.seyarada.pandeloot.config.Pity;
import net.seyarada.pandeloot.drops.IDrop;
import net.seyarada.pandeloot.drops.LootDrop;
import net.seyarada.pandeloot.flags.FlagEffect;
import net.seyarada.pandeloot.flags.FlagPack;
import net.seyarada.pandeloot.flags.enums.FlagTrigger;
import net.seyarada.pandeloot.flags.types.IPlayerEvent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

@FlagEffect(id="pity", description="Broadcast a message")
public class PityFlag implements IPlayerEvent {

	@Override
	public void onCallPlayer(Player player, FlagPack.FlagModifiers values, @Nullable LootDrop lootDrop, @Nullable IDrop iDrop, FlagTrigger trigger) {
		HashMap<String, Double> pity = Pity.pity.getOrDefault(player.getUniqueId().toString(), new HashMap<>());
		String id = values.getOrDefault("id", "Global");
		double newPity = pity.getOrDefault(id, 0d) + values.getDouble();
		pity.put(id, newPity);
		Pity.pity.put(player.getUniqueId().toString(), pity);
	}

}
