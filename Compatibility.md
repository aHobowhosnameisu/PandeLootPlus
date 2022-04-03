One of PandeLoot's main goal is to be able to have as much compatibility as possible! So if you don't see a plugin you use here feel free to make a suggestion for it!

# MythicMobs
### Using MythicMobs Mobs
To use MythicMobs mobs, add the ``Reward`` key to the mythicmobs configuration. You may also use the ``ScoreHologram`` and ``ScoreMessage`` options
```yml
MyMythicMobsZombie:
  Health: 25
  Type: Zombie
  Options:
    ScoreHologram: true
    ScoreMessage: true
  Rewards:
  - stone
```

### Using MythicMobs Items
To call a MythicMobs item from PandeLoot you have to use ``mythicmobs``|``mm`` as reward origin and MM's internal item name as the reward name
```yml
# List of examples:
- mythicmobs:SkeletonKingSword
- mythicmobs:KingsCrown
- mythicmobs:BanditTunic
```

### Using PandeLoot in MythicMobs Skills
For users that are savy with MythicMobs and want to do complex uses of PandeLoot rewards, we also provide a way of using rewards has a skill or a drop with the syntax of ``- pandeloot{<reward line here>}``|``ploot{...}``
```yml
# List of examples:
Skills:
- pandeloot{stone} @PIR{r=15} ~onDeath
- pandeloot{diamond{color=RAINBOW;message=Hello!}} @Target ~onTimer:15
```

### Using MythicMobs Skills in PandeLoot
See [the flag page](https://github.com/Seyarada/PandeLootPlus/wiki/Flags#mythicmobs-skill)

### Using MythicMobs Conditions in PandeLoot
See [the flag page](https://github.com/Seyarada/PandeLootPlus/wiki/Flags#mythicmobs-condition)

### Using MythicMobs DropTables
To call a MythicMobs DropTable from PandeLoot you have to use ``droptable``|``dt`` as reward origin, and internal DropTable name as reward name
```yml
- droptable:KingRewards
```

# MMOItems
### Using MMOItems Items
To call a MMOItems Mobs item from PandeLoot you have to use ``mmoitems``|``mi`` as reward origin, the MMOItem name as the reward name, and the ``type`` option to specify the type of the item.
```yml
# List of examples:
- mmoitems:PLATEMAIL_CHESTPIECE{type=ARMOR}
- mmoitems:CRACKED_SKULL{type=MATERIAL}
- mmoitems:WEATHERED_IRON_SWORD{type=SWORD}
```

# Citizens
You are able to set if Citizens' damage towards mobs should be counted or not in the config, this is true by default
```yml
Settings:
  IgnoreCitizensDamage: true
```

# DiscordSRV
See [the flag page](https://github.com/Seyarada/PandeLootPlus/wiki/Flags#discord)

# Economy
See [the flag page](https://github.com/Seyarada/PandeLootPlus/wiki/Flags#economy)