import java.io.*;
import java.util.*;
import Object.*;

import static Object.GameVersion.mostRecentAndAvailableVersion;

public class DesertTempleLootSimulation {
    public static int d,i,g,e,b,rf,s,ih,gh,dh,bo,old_bo;

    //Item ID:
    //D [0] 64
    //I [1] 64
    //G [2] 64
    //E [3] 64
    //B [4] 64
    //RF [5] 64
    //S [6] 1
    //IH [7] 1
    //GH [8] 1
    //DH [9] 1
    //BO [10] 1

    public static String releaseVersion = mostRecentAndAvailableVersion;
    public static int total_e, chest;
    public static boolean debug_input = false;
    public static boolean debug_ench_show = false;
    public static int tradeEmeraldsMax, tradeEmeraldsMin;
    public static List<EnchContainer> enchList = new ArrayList<>();
    public static RandomUtility globalRand = new RandomUtility();

    public static void checkEnchBook(){
        if (debug_ench_show && (old_bo!=bo)){
            old_bo++;
            System.out.println("Enchanted Book enchantments on Chest "+chest+" are:");
            for (EnchContainer enchContainer : enchList){
                System.out.println("["+Translation.enchantName(enchContainer.enchBase.id)+",Level "+enchContainer.enchLevel+"]");
            }
        }
    }
    public static void updateLootCount(){
        if (Item.currentIdItem == 0)d+= Item.currentStack;
        if (Item.currentIdItem == 1)i+= Item.currentStack;
        if (Item.currentIdItem == 2)g+= Item.currentStack;
        if (Item.currentIdItem == 3)e+= Item.currentStack;
        if (Item.currentIdItem == 4)b+= Item.currentStack;
        if (Item.currentIdItem == 5)rf+= Item.currentStack;
        if (Item.currentIdItem == 6)s+= Item.currentStack;
        if (Item.currentIdItem == 7)ih+= Item.currentStack;
        if (Item.currentIdItem == 8)gh+= Item.currentStack;
        if (Item.currentIdItem == 9)dh+= Item.currentStack;
        if (Item.currentIdItem == 10)bo+= Item.currentStack;
    }
    public static void addItemsToLoot(){
        int length = Item.storedSlotArray.size();
        while (length>0){
            length--;
            Item.currentIdItem = Item.storedIdItemArray.get(length);
            Item.currentStack = Item.storedStackArray.get(length);
            Item.currentSlot = Item.storedSlotArray.get(length);
            updateLootCount();
        }
    }
    public static void resetLootCount(){
        d=0;
        i=0;
        g=0;
        e=0;
        b=0;
        rf=0;
        s=0;
        ih=0;
        gh=0;
        dh=0;
        bo=0;
    }
    public static void resetArrays(){
         Item.storedIdItemArray = new ArrayList<>();
         Item.storedStackArray = new ArrayList<>();
         Item.storedSlotArray = new ArrayList<>();
         EnchContainer.enchIds = new ArrayList<>();
         EnchContainer.enchContainers= new ArrayList<>();
         EnchContainer.enchIdsToRemove= new ArrayList<>();
    }
    public static void simulateRandomEnchOnBook(Random rand, int enchLevel) {
        enchList = EnchContainer.createEnchContainersForBook(rand, enchLevel);
    }

    public static void simulateRandomEnchOnBook(Random rand) {
       EnchBase uniqueEnch = EnchBase.e_b_list[rand.nextInt(EnchBase.e_b_list.length)]; //In these releases, only one enchantment can apply to book
       int minLevel = uniqueEnch.getMinEnchLevel();
       int maxLevel = uniqueEnch.getMaxEnchLevel();
       int uniqueEnchLevel = -1;
       if (minLevel == maxLevel)uniqueEnchLevel = 1;
       else if (minLevel < maxLevel )uniqueEnchLevel = rand.nextInt(maxLevel - minLevel + 1);
       enchList = new ArrayList<>(List.of(new EnchContainer(uniqueEnch,uniqueEnchLevel)));
    }

    public static boolean simulateDesertTempleLoot(long worldSeed, int templeX, int templeZ) {

        resetLootCount();

        globalRand.setSeedBeforeStructurePopulation(globalRand,worldSeed,templeX,templeZ);

        resetArrays(); //Safety, could be removed, but... better keep it.

        old_bo = 0;
        chest = 0;

        while (chest<4){
            chest++;
            if(GameVersion.higherOrEqualThan(releaseVersion,"1.7"))simulateRandomEnchOnBook(globalRand, 30);
            else if(GameVersion.betweenVersionsOrEqualThan(releaseVersion,"1.3","1.6"))simulateRandomEnchOnBook(globalRand);
            Chest chestObject = new Chest(chest);
            chestObject.simulateChestContent(globalRand, LootTable.currentDesertTempleLootTable, 2 + globalRand.nextInt(5));
            addItemsToLoot();
            checkEnchBook();
            resetArrays();
        }

        return checkTradingConditions(g,rf,e);

    }
    public static boolean checkTradingConditions(int g_item, int rf_item, int e_item){
        int bothTrades = (g_item/8) + (rf_item/36);
        total_e = e_item;

        if (bothTrades>0){
            if (bothTrades!=1) total_e+=bothTrades;
            return (total_e >= tradeEmeraldsMin) && (total_e <= tradeEmeraldsMax);
        }

        else return tradeEmeraldsMin == 0; //Allows to check the output with any trade if this is set to 0
    }
    public static void main(String[] argv) throws IOException {

        long worldSeed;
        int templeX, templeZ, villageX, villageZ, PRX, PRZ, Dist;
        File reader, writer;
        BufferedReader buffer_r;
        BufferedWriter buffer_w;

        //Only useful for testing in IDE
        //debug_input = true;
        //debug_ench_show = true;

        if (!debug_input) {
            debug_input = Boolean.parseBoolean(argv[5]);
            debug_ench_show = Boolean.parseBoolean(argv[5]);
        }

        if(!debug_input) {

            reader = new File(argv[0]);
            buffer_r = new BufferedReader(new FileReader(reader));
            writer = new File(argv[1]);
            buffer_w = new BufferedWriter(new FileWriter(writer));

            String input_string;

            releaseVersion = argv[2];

            GameVersion.setGameVersionAndInitObjects(releaseVersion);

            tradeEmeraldsMin = Integer.parseInt(argv[3]);
            tradeEmeraldsMax = Integer.parseInt(argv[4]);

            //Used to define all the output values in the resulting file
            buffer_w.write("Generated for Minecraft Release "+releaseVersion+ "\n");
            buffer_w.write("Seed, TempleX, TempleZ, VillageX, VillageZ, PortalX, PortalZ, TotalDistance, D, I, G, E, B, RF, S, IH, GH, DH, BO, TradeEmeralds" + "\n");

            while ((input_string = buffer_r.readLine()) != null) {
                worldSeed = Long.parseLong(input_string.split(", ")[0]);
                templeX = Integer.parseInt(input_string.split(", ")[1]) / 16;
                templeZ = Integer.parseInt(input_string.split(", ")[2]) / 16;
                villageX = Integer.parseInt(input_string.split(", ")[3]);
                villageZ = Integer.parseInt(input_string.split(", ")[4]);
                PRX = Integer.parseInt(input_string.split(", ")[5]);
                PRZ = Integer.parseInt(input_string.split(", ")[6]);
                Dist = Integer.parseInt((input_string.split(", ")[7]).split("\n")[0]);

                if (simulateDesertTempleLoot(worldSeed, templeX, templeZ)) {
                    buffer_w.write(worldSeed + ", " + (templeX * 16) + ", " + (templeZ * 16) + ", " + villageX + ", " + villageZ + ", " + PRX + ", " + PRZ + ", " + Dist + ", " + d + ", " + i + ", " + g + ", " + e + ", " + b + ", " + rf + ", " + s + ", " + ih + ", " + gh + ", " + dh + ", " + bo + ", " + total_e + "\n");
                    buffer_w.flush();
                }


            }
        }

        else
        {
            //Testing worldSeed, it is used for checking the Desert Temple simulation on a single case.

            worldSeed = -1225265174872649318L;
            templeX = -928/16;
            templeZ = 864/16;

            releaseVersion = "1.6";

            GameVersion.setGameVersionAndInitObjects(releaseVersion);

            tradeEmeraldsMax = 999;
            tradeEmeraldsMin = 0;

            //[Debug Seeds used for testing in Release 1.7-1.8]
            //Expected Output 1:
            //Seed:225874918561344128 TempleX:-25 TempleZ:-27
            //d:0 i:0 g:18 e:7 b:14 rf:18 s:2 ih:0 gh:0 dh:1 bo:0 total_e:9
            //Expected Output 2:
            //Seed:364115059209886541, 720, -144, 720, -224, 623, -273, 916, 0, 2, 26, 3, 10, 4, 1, 0, 0, 0, 2, 6
            //Temple: 45 -9
            //Expected Output 3:
            //-1225265174872649318, -928, 864, -912, 688, -941, 655, 1779, 2, 0, 7, 3, 33, 21, 0, 0, 2, 0, 0, 3 (1.6)



            if (simulateDesertTempleLoot(worldSeed, templeX, templeZ)) {
                //Actual Output:
                System.out.println("Seed:"+worldSeed+" TempleX:"+templeX+" TempleZ:"+templeZ);
                System.out.println("d:" + d + " i:" + i + " g:" + g + " e:" + e + " b:" + b + " rf:" + rf + " s:" + s + " ih:" + ih + " gh:" + gh + " dh:" + dh + " bo:" + bo + " total_e:" + total_e);
                System.out.println("Minecraft Release "+releaseVersion);
            }

        }


    }

}
