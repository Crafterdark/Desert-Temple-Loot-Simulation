# Desert Temple Loot Simulator

- The main objective of this program is filtering loot in Desert Temple. (Which is faster than manually checking it ingame)
- It can be used to find new any% Set Seed Glitchless seeds, used for Minecraft speedrunning.
- It can be also used for Category Extensions or other categories.
- Credit to Mojang for the "enchanting & looting" systems.
These systems were reworked from the game code and reused for the purpose explained above.  

Feature:

- The program can predict items & enchants of enchanting books in a given Desert Temple chunk, for a given World Seed.

Current Limitation(s):

- If Mineshaft (1) and/or Village (2) and/or Stronghold (3) pieces will collide with the chunk with loot, then this one will be different due to RNG calls.
- The solution to predict this behaviour is to pre-generate all Mineshafts, Villages and Strongholds, but this will be made later.
- The problem only affects Release 1.0-1.12.

(There's another similar problem to solve in Release 1.13, while Release 1.14+ should be always fine)

Currently, the simulation supports:

    1.8
    1.7

Generic Command Line:

    java -jar DesertTempleLoot.jar input.txt output.txt releaseVersion minTradeEmeralds maxTradeEmeralds isDebug

Example Command Line:

    java -jar DesertTempleLoot.jar input.txt output.txt 1.8 7 17 false

Example Input:

    Seed, TempleX, TempleZ, VillageX, VillageZ, PortalX, PortalZ, TotalDistance
- (Note: All in BlockPos, not ChunkPos)

Generic Input in "input.txt":

    225874918561344128, -400, -432, -496, -448, -589, -405, 317

Generic Output in "output.txt":

    Generated for Minecraft Release 1.8
    Seed, TempleX, TempleZ, VillageX, VillageZ, PortalX, PortalZ, TotalDistance, D, I, G, E, B, RF, S, IH, GH, DH, BO, TradeEmeralds
    225874918561344128, -400, -432, -496, -448, -589, -405, 317, 0, 0, 18, 7, 14, 18, 2, 0, 0, 1, 0, 9