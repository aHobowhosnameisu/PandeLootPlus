To see how to write flags, check the _syntax_ page!

# Explanation
Flags are used to change what happens when a drop is dropped or how it acts. The are multiple kinds of flags, the main ones are:

* General Flag: Will always be executed
* Player Flag: Will only execute if there is a player attached to the drops
* Item Flag: Will only executed if the drop is an item
* Entity Flag: Will only execute if the drop is an entity
* Condition Flag: Drops won't be selected if any of their condition flags return false
  * Condition checks may be inverted by using the attribute ``<invert=true>``

Additionally, all flags are bound to a trigger, by default the trigger is on spawn, the trigger list is:

* onPreSpawn: Runs before the loot is dropped, won't execute item/entity flags
* onSpawn: Runs when the item is spawned
* onPickUp: Runs when the item is picked up
* onLand: Runs when the item touches the ground
* onOpen: Runs when a lootbag is opened, won't do anything if the conditions aren't meet
* onRoll: Runs every time a lootbag is rolled by the _roll_ flag


# Flag Compendium

# General Flags

## Broadcast
Sends a message to everyone in the server
```yml
# Example use: Tell the whole server the player got a rare drop
- diamond{broadcast=%player.name% looted a rare diamond from a field boss!}
```

## Command
Runs a command as console
```yml
# Example use: Make the player progress in a quest
- mythicmobs:MoonTotem{onpickup=[command=questplugin stage:moonBoss setProgress:3 for:%player%]}
# Made up command
```

## Delay
Causes this and all following drops to have X ticks of delay
```yml
# Example use: First drop the common items then the rare ones
- coal
- copper
- iron_ingot{delay=10}
- gold_ingot
- diamond{delay=20}
- emerald
```

## Discord
Uses [DiscordSRV](https://www.spigotmc.org/resources/discordsrv.18494/) to send messages/embeds to a discord server

| Attribute | Default | Description |
| ------- | ----- | ------------- |
| Embed | true | If the message should be embed |
| Title | - | The title of the embed |
| Color | - | The color of the embed, accepts RGB |
| Thumbnail | - | The thumbnail of the embed, has to be an image link |
| Author | - | The author of the embed |
| Footer | - | The footer of the embed |
| Image | - | The image of the embed, has to be an image link |
| Avatar | - | If it should show the player head as the thumbnail |
| Channel | - | The channel where the message should be send |

```yml
# Example usage: Send an embed when a player is dropped a rare item
- diamond{discord=%player% got an ultra rare sword! <channel=710138582853091371;color=#bdca24;thumbnail=https://cdn.discordapp.com/attachments/710138582853091371/878539180219723786/unknown.png;footer=Chance of drop: 0.1%;author=%player%>}
```

## MythicMobs Skill
Runs a skill from MythicMobs, with the base mob as target, player as caster and mob location as origin
```yml
- diamond_block{skill=TestSkill}

TestSkill:
  Skills:
  - SetBlockType{material=DIAMOND_BLOCK} @target # Places diamond block at mob location
  - SetBlockType{material=GOLD_BLOCK} # Places gold block at player location
  - message{m=hello} # Sends message to player
```

## Stop
Once a drop with this flag is dropped, it stops all the other drops intended for this player
```yml
# Example Usage: When a rare item drops, don't drop more items
- coal
- diamond{stop=true} 0.5
- iron_ingot # 50% Chance for this and the drops below to not be dropped
- copper_ingot
- emerald{stop=true}
- gold_ingot # Will never be dropped
```

# Player Flags
## Action Bar
Sends an action bar message to the player
```yml
# Example use: Show an action bar to the playing saying they got a rare drop
- diamond{actionbar=You found a rare drop!}
```

## Consume Item
Takes the specified amount of the item in the player's hand
```yml
# Example use: Drop a lootbag which requires a specific mythicmobs item to open it
- lb:LockedChest{preventpickup=true;onopen=[consumeitem=1;holding=mm:chestKey]}
```

## Economy
Uses Vault to give/take money, may also act as a condition for balance checking
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| Give | - | Gives money to the player |
| Take | - | Takes money from the player |
| Balance | - | Checks how much money the player has |
```yml
# Example use: Give money to a player on item pick up
- gold_block{economy=<give=50>}
# Example use: Only drop the item if the player has $100 and take that money
- emerald_block{economy=<balance=100;take=100>}
```

## Experience
Gives experience points to the playaer
```yml
# Example use: Gives experience to a player which got a rare drop
- diamond{experience=10}
```

## Message
Sends a message to the player
```yml
# Example use: Show a message to the playing saying they got a rare drop
- diamond{message=You found a rare drop!}
```

## Pity
Using this causes the internal placeholder <player.pity>|<player.pity.id> to increase by the amount determined, with this you can make a drop which has a granted chance to drop after a certain number of tries
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| id | Global | Specifies which pity ID to increase |
```yml
# Example use: Increase chances of certain drops
- mm:commonDrop{pity=0.01}
- mm:rareDrop 0.01+<player.pity>

- coal{pity=0.01 <id=ores>}
- diamond 0.01+<player.pity.ores>
```

## Sound
Sends a sound to the player
```yml
# Example use: Sebd a sound to the playing when they got a rare drop
- diamond{sound=ui.toast.challenge_complete}
```

## Title
Sends a title|subtitle to the player
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| subtitle | - | The title subtitle |
| fade | - | The title fade in/out duration |
| duration | - | The title duration after fade |
```yml
# Example use: Send a rare drop titel
- diamond{title=RARE DROP! <subtitle=&bPure Diamond>}
```

## Toast
Sends a toast (the small achievements you get at the top right) to the player
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| frame | - | The frame type (TASK/GOAL/CHALLENGE) |
| icon | - | The material for the toast (Stone,Dirt,...) |
```yml
# Example use: Tell information to the player
- mm:questItem{toast=You found the last piece! <frame=GOAL>}
```

# Item Flags

## Amount
Sets the amount of the item
```yml
# Both formats are valid. Also accepts ranges.
- diamond{a=5}
- diamond 5
- diamond 3to5
```

## Prevent Pick Up
Prevents the item from being picked up by players
```yml
# Example use: Create a lootbag that can only be opened while on the ground
- lootbag:specialBag{preventpickup=true}
```

## Roll Bag
Will make the dropped lootbag item loop trough the contents of the table, on right click, it will stop and the present item will be the pickupable one
```yml
# Example use: Create a lootbag that can only be opened while on the ground
- lootbag:specialBag{preventpickup=true}
```

## Stackable
Makes the drop unstackable by adding an unique identifier to the item NBT tag
```yml
# Example use: Make a diamond that won't stack with others
- diamond{stackable=false}
```

## To Inventory
Gives the item directly to the player instead of dropping it to the ground
```yml
# Example use: Give low tier items to the player
- stone{toinventory=true}
```

# Entity Flags
## Beam Flag
Creates a beam of particles above the item, like borderlands drops
```yml
# Example use: Make rare drops more noticiable
# The value determines how high the beam is
- diamond{beam=1;color=BLUE}
```

## Color Flag
Sets the color of the item
| Special Values | Description |
| ------- | ------------- |
| RANDOM | Gives the item a random color |
| RAINBOW | Gives the item rainbow colors loop |
```yml
# Example use: Make items more ✨fancy✨
- diamond{glow=true;color=BLUE}
```

## Explode
Applies an explosion-like velocity to the item
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| type | Spread | Chances how the explosion looks |
| offset <spread only> | 0.2 | How strong the item is thrown |
| height <spread only> | 0.6 | The height of the throw |
| radius <circular|radial only> | 3 | Max radius for the item to land in |

Type Options:
* Spread ( Throws drops in a square shape )
* Circular ( Throws the drops randomly within radius )
* Radial ( Throws the drops evenly around the max radius )
```yml
# Example use: Make the drop explode out of the item
- stone{explode=true}
- stone{explode=true <offset=0.4;height=1.2>}
- stone{explode=true <type=radial;radius=2>}
- stone{explode=true <type=circular>}
```

## Firework
Spawns a firework at the entity
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| type | BALL | How the explosion looks |
| power | - | The firework power |
| flicker | - | If the firework should flicker |
| trail | - | If the firework should have a trail |
| instant | - | If the firework explode instantly |

Type Options:
* BALL
* BALL_LARGE
* BURST
* CREEPER
* STAR
```yml
# Example use: Spawn firework on rare drop
- diamond{firework=GREEN;power=2;flicker=true;trail=true;instant=false}
```

## Glow
If the entity should glow, you can specify the color with the Color option
```yml
# Example use: Make items for visible
- diamond{glow=true;color=BLUE}
```

## Hologram
Creates a hologram over the entity, in the case of it being a single line it will change the entity name so it won't affect perfomance

The following special values only apply to item entities
| Special Values | Description |
| ------- | ------------- |
| DISPLAYCUSTOM | Only creates the hologram if the item has a custom name |
| DISPLAY | Makes the hologram be the item display |
| LORE | Makes the hologram be the item lore |
| FULL | Makes the hologram be the item display and lore |

A comma (``,``) can be used to split the hologram in multiple lines in case special values weren't used
```yml
# Example use: Show item information as an hologram
- mm:skeletonSword{hologram=display}
- mm:amazingBow{hologram=full}
- diamond{hologram=Some custom lore, wow!}
```

## Remove
Removes the entity when the flag is called
```yml
# Example use: Make an item which as on pick up effects but it's removed and won't go to the player inventory
- mm:coin{onpickup=[economy=<give=5>;remove=true;message=You picked up a coin worth $5!]}
```

## Visibility
Determines which players can see and pick up the item
| Special Values | Description |
| ------- | ------------- |
| PARTY | Everyone that participated in the fight |
| DEFAULT | The player |
```yml
# Example use: Make the player be the only one able to see this item
- diamond{visibility=PLAYER}
```

# Condition Flags
## Chance
The probability to drop this item, can accept math operation and placeholders
```yml
# Example use: 50% to drop an item
- diamond 0.5
# Example use: Calculate drop chance based the value of a player placeholder
- diamond 0.1+0.05*%player.level%
```

## Damage
When in a fight, the damage the player has to do to recieve the item, accepts percentages (``%``) and ranged values (``1to2``)
```yml
# Example use: Player needs to do 50 damage to receive the item
- diamond{damage=50}
# Example use: Player needs to do 25% of the damage to receive the item
- diamond{damage=25%}
# Example use: Player needs to do between 25 and 50 damage to receive the item
- diamond{damage=25to50}
```

## First Hit
Checks if the player was the one that did the first hit to the mob
```yml
# Example use: Only drop item to the one that started the combat
- diamond{firsthit=true}
```

## Holding
Checks the item and amount of the item the player is holding
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| Amount | - | The min. amount required |
```yml
# Example use: Make a player only able to open a lootbag if they have 5 coins
- lb:PurchaseableChest{preventpickup=true;onopen=[consumeitem=5;holding=mm:coin <amount=5>]}
```

## Last Hit
Checks if the player did the killing hit to the mob
```yml
# Example use: Only drop item to the one that killed the mob
- diamond{lasthit=true}
```

## MythicMobs Condition
Checks a condition from mythicmobs
| Attribute | Default | Description |
| ------- | ----- | ------------- |
| Eval | Player | If the Mob or Player should be the one evaluated |
```yml
# Example use: Check the experience level of the player
- diamond{mmcondition=enchantinglevel{a=>5}}
```

## Permission
Checks if the player has a certain permission
```yml
# Example use: Only drop the item to VIP players
- diamond{permission=player.rank.vip}
```

## Top
Checks if the player is in this position of the damage leaderboard, accepts ranged values (``1to2``)
```yml
# Example use: Only drop the item to player that did the most damage
- diamond{top=1}
```