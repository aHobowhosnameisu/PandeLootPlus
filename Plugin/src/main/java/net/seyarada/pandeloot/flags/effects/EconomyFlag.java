package net.seyarada.pandeloot.flags.effects;

import net.milkbowl.vault.economy.Economy;
import net.seyarada.pandeloot.compatibility.VaultCompatibility;
import net.seyarada.pandeloot.drops.IDrop;
import net.seyarada.pandeloot.drops.LootDrop;
import net.seyarada.pandeloot.flags.FlagEffect;
import net.seyarada.pandeloot.flags.FlagPack;
import net.seyarada.pandeloot.flags.enums.FlagTrigger;
import net.seyarada.pandeloot.flags.types.ICondition;
import net.seyarada.pandeloot.flags.types.IPlayerEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@FlagEffect(id="economy", description="Broadcast a message")
public class EconomyFlag implements IPlayerEvent, ICondition {

	@Override
	public void onCallPlayer(Player player, FlagPack.FlagModifiers values, @Nullable LootDrop lootDrop, @Nullable IDrop iDrop, FlagTrigger trigger) {
		Economy economy = VaultCompatibility.getEconomy();

		if(values.getIntOrDefault("give", 0)!=0) {
			economy.depositPlayer(player, values.getInt("give"));
		}

		if(values.getIntOrDefault("take", 0)!=0) {
			economy.withdrawPlayer(player, values.getInt("take"));
		}

	}

	@Override
	public boolean onCheck(FlagPack.FlagModifiers values, LootDrop lootDrop, IDrop itemDrop) {
		Economy economy = VaultCompatibility.getEconomy();
		if(lootDrop.p!=null && values.getIntOrDefault("balance", 0)!=0) {
			return economy.getBalance(lootDrop.p)>=values.getInt("balance");
		}
		return true;
	}

	@Override
	public boolean onCheckNoLootDrop(FlagPack.FlagModifiers values, Entity entity, Player player) {
		Economy economy = VaultCompatibility.getEconomy();
		if(values.getIntOrDefault("balance", 0)!=0) {
			return economy.getBalance(player)>=values.getInt("balance");
		}
		return true;
	}
}
