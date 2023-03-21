package Object;

import java.util.ArrayList;
import java.util.List;

import static Object.GameVersion.getGameVersion;

public class LootTable {

    public static ArrayList<Item> currentDesertTempleLootTable;
    public static final ArrayList<Item> desertTempleLootTable_1_7_to_1_8 = new ArrayList<>(List.of(
            new Item(0,1, 3, 3,64),
            new Item(1,1, 5, 10,64),
            new Item(2,2, 7, 15,64),
            new Item(3,1, 3, 2,64),
            new Item(4,4, 6, 20,64),
            new Item(5,3, 7, 16,64),
            new Item(6,1, 1, 3,1),
            new Item(7,1, 1, 1,1),
            new Item(8,1, 1, 1,1),
            new Item(9,1, 1, 1,1),
            new Item(10,1, 1, 1,1))
    );

    //TODO: Add the pre1.7 loot tables
    public static final ArrayList<Item> desertTempleLootTable_pre1_7 = new ArrayList<>(List.of(
            new Item(0,1, 3, 3,64),
            new Item(1,1, 5, 10,64),
            new Item(2,2, 7, 15,64),
            new Item(3,1, 3, 2,64),
            new Item(4,4, 6, 20,64),
            new Item(5,3, 7, 16,64),
            new Item(6,1, 1, 3,1),
            new Item(7,1, 1, 1,1),
            new Item(8,1, 1, 1,1),
            new Item(9,1, 1, 1,1),
            new Item(10,1, 1, 1,1))
    );

    public static void setCurrentDesertTempleLootTable(){
        switch (getGameVersion()) {
            case "1.8", "1.7" -> currentDesertTempleLootTable = desertTempleLootTable_1_7_to_1_8;
            case "1.6" -> currentDesertTempleLootTable = desertTempleLootTable_pre1_7;
        }
    }

}
