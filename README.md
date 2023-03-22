# Desert Temple Loot Simulation

- The main objective of this program is filtering and predicting loot in the Desert Temple structure that is found in Minecraft. (Faster than manual check)
- It can be used to find new seeds for any% Set Seed Glitchless, used for Minecraft speedrunning.
- It can be also used for Category Extensions or other categories.

Important:

- Full credit to Mojang for the "enchanting" and "looting" systems.
These systems were reworked, reduced and rewritten from the original game code only to be reused for the purpose explained above.

- This is NOT an official Minecraft product or program [!]

Feature:

- The program can predict items & enchants of enchanting books in a given Desert Temple chunk, for a given World Seed.

Current Limitation(s):

- If Mineshaft (1) and/or Village (2) and/or Stronghold (3) pieces will collide with the Desert Temple chunk, then the loot will be different due to extra RNG calls and the prediction will fail.
- The future solution to predict the loot in this edge case will be to pregenerate all Mineshafts, Villages and Strongholds. This might be made later.
- This specific limitation should affect Minecraft Release 1.3 to 1.12 only.

(There's another issue with loot in Release 1.13 to be addressed, while Release 1.14+ should be free from inconsistencies)

Currently, this simulation supports the release:

    1.8
    1.7
    1.6
-------------------------
* Usage of the Tool

Generic Command Line:

    java -jar Desert-Temple-Loot-Simulation_v1.jar input.txt output.txt releaseVersion minTradeEmeralds maxTradeEmeralds isDebug

Example Command Line:

    java -jar Desert-Temple-Loot-Simulation_v1.jar input.txt output.txt 1.8 7 17 false

Example Input:

    Seed, TempleX, TempleZ, VillageX, VillageZ, PortalX, PortalZ, TotalDistance
- (Note: All in BlockPos, not ChunkPos)

Generic Input in "input.txt":

    225874918561344128, -400, -432, -496, -448, -589, -405, 317

Generic Output in "output.txt":

    Generated for Minecraft Release 1.8
    Seed, TempleX, TempleZ, VillageX, VillageZ, PortalX, PortalZ, TotalDistance, D, I, G, E, B, RF, S, IH, GH, DH, BO, TradeEmeralds
    225874918561344128, -400, -432, -496, -448, -589, -405, 317, 0, 0, 18, 7, 14, 18, 2, 0, 0, 1, 0, 9
