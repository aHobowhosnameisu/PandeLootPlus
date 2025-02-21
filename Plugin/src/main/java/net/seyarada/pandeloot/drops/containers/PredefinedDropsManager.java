package net.seyarada.pandeloot.drops.containers;

import net.seyarada.pandeloot.Logger;
import net.seyarada.pandeloot.config.Config;
import net.seyarada.pandeloot.config.Storable;
import net.seyarada.pandeloot.drops.IDrop;
import net.seyarada.pandeloot.drops.ItemDrop;
import net.seyarada.pandeloot.flags.FlagPack;
import net.seyarada.pandeloot.utils.ItemUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.logging.Level;

public class PredefinedDropsManager implements Storable {

    static final HashMap<String, IDrop> dropsGeneric = new HashMap<>();

    @Override
    public void load() {
        int loadedContainers = 0;
        dropsGeneric.clear();
        FlagPack.predefinedPacks.clear();

        for(YamlConfiguration config : Config.drops) {
            for(String key : config.getKeys(false)) {
                if(dropsGeneric.containsKey(key)) {
                    Logger.log(Level.WARNING, "Duplicate drop key: %s", key);
                    continue;
                }
                loadedContainers++;
                ConfigurationSection section = config.getConfigurationSection(key);
                ItemDrop iDrop = new ItemDrop(ItemUtils.getItemFromSection(section), FlagPack.fromExtended(section));
                dropsGeneric.put(key, iDrop);
            }
        }
        Logger.log("Loaded %o predefined drops", loadedContainers);
    }

    public static IDrop get(String str) {
        return dropsGeneric.get(str);
    }

    @Override
    public void save() {}

}
