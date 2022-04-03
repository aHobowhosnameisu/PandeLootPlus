# Config
* ``Settings``

  * ``Debug``: Change if the plugin should output information about the drops to console or not

* ``Announcements``

  * ``ScoreHologram``: The hologram to display if the "ScoreHologram" option is specified as true in the mob
  * ``ScoreMessage``: The message to send if the "ScoreMessage" option is specified as true in the mob

* ``DefaultFlag``
  * This is the flag that will apply to *all* the flags dropped, useful to prevent repeating code

# Events
In the events file you are able to specify whether flags apply to certain item related events

  * ``onBlockBreak``: Flags to apply when a player breaks a block
  * ``onPlayerItemDrop``: Flags to apply when a player drops an item

# Flags
Use a set of flags too often? Inside this file you can create a set of predefined flags which you can apply to drops with
```yml
- stone{pack=somePredefinedFlagName}
```

# Drops Folder
Use a set of drops too often? Inside this folder you can create a set of predefined drops
```yml
- i:somePredefinedDropName
```

# Mobs Folder
This is a useful folder in the case you want to make a certain mob drop items but it doesn't has direct PandeLoot compatibility, so simply add the mob type and the mob's display name between other options so PandeLoot knows which mob to track and make it drop rewards!

# Containers Folder
The folder where you write your containers/loottables/lootbags/... , all files inside it will be read, to see how to write a Container consult the syntax page

# Boosters & Pity
Internal storage files