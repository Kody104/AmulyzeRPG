# Classes 
Got to rework roll chances for all classes. Levels and exp and stats all need tweaking. Got to cleanup a lot of messy code and oversights. Lightning (Mage) strikes should be deleted. Hook (Warrior) needs a buff. PlayerDeath should be worked better than it is to include exp and level changes and hp resets. Roll Items in inventory when moved should update to show what they do. Leap (Beserker) needs a buff. Roll items algorithim needs completely redone. OnPLayerJoin needs to be based on GamePlayer and not Minecraft Player.

# Skills
Skills are special traits that a player can utilize to earn special perks, items, and tools. Skills can be increased by completing
activities related to that attribute or a randomized goal. After reaching a certain level (not sure on this yet), a player will have the chance to /rankup. This will open the "chest-based" menu with various perks for reaching that level. Skills can also depreciate - that is lose value - over a longer period of time. However, they will not decay fully, and the max a skill will decay is (n - 3).

There are 3 Skills Currently. (Open to suggestions)
<ol>
  <li> Alchemy: better variety of potions, potions with multiple effects, transmutations, and mob conjuring (limited).
  <li> Farming: uncertain perks at the moment; higher crop yields - but then, minecraft already has HUGE crop yields. TBD
  <li> Mining: quick mining, higher ore yields, and smelting grants more than 1 ignot.
