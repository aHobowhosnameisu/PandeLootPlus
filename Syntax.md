TODO: Split this in their respective "Drops" and "Containers" sections
## Drop Syntax
A drop is composed of an item, to which you may add flags or amount/chance

A fully formed syntax would look like this:

``- <origin>:<item>{flag1=value1;flag2=value2 <attribute1=otherValue>;ontrigger=[flag3=value3;...]} <amount>/<chance>``

* ``origin``: The origin of the item, this may be ``mythicmobs``, ``mmoitems`` or any other dependency, by default the origin is ``minecraft``
* ``item``: The material/id of the item
* ``flag``: All flags must be between brackets ``{...}``, and they are separated by the ``;`` character
* ``value``: A flag value is assigned by simply adding a ``=`` character then the value
* ``attribute``: Attributes are written like flags inside flags, but they must be between angle brackets ``<...>``
* ``amount``: It will take any value after the item/brackets bigger or equal to 1 as the amount
* ``chance``: It will take any value after the item/brackets smaller than 1 as the chance

### Building a complex drop from the ground up
The goal is going to create a glowing gold coin which will give money and dissapear when you walk over it

First we fill the origin and item, this drop is going to be from mythicmobs so I'll use that as my origin
```yml
- mythicmobs:GoldCoin
```
Now to make it glow yellow
```yml
- mythicmobs:GoldCoin{glow=true;color=YELLOW}
```
To add some spice to it I'm going to make it create particles when it lands using a mythicmobs skill
```yml
- mythicmobs:GoldCoin{glow=true;color=YELLOW;onland=[skill=GoldCoin_Land]}
```
For the final part of giving money and dissapearing that's going to use the economy and remove flag on the pickup trigger
```yml
- mythicmobs:GoldCoin{glow=true;color=YELLOW;onland=[skill=GoldCoin_Land];onpickup=[economy=<give=5>;remove=true]}
```
And that's it! Hope it isn't too hard 📦

## Container Syntax
Container is the technical name for things like a LootTable or LootBag, this will list the options you can add to them

LootTable:
* ``Rewards``: A list of the rewards you want to make it drop
* ``Guaranteed``: Makes the amount of dropped items be not less than the specified number
* ``TotalItems``: Sets the number of drops
* ``MaxItems``: Sets the number of max drops
* ``MinItems``: Sets the number of min drops

LootBag:
* Includes everything from the LootTable
* ``Material``: The item material
* ``Display``: The item display
* ``Lore``: The item lore
* ``Model``: The item model
* ``Skull``: If the item is a player skull, the texture ID