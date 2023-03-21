package Object;

import java.util.List;
import java.util.Random;

import static Object.Item.currentItem;

public class Chest {
    public int chestId;
    public Chest(int chest){
        this.chestId = chest;
    }
    public static boolean debug = false;
    public static void storeCurrentItemFromSimulatedChest(){
        if(!Item.storedSlotArray.contains(Item.currentSlot)){
            Item.storedIdItemArray.add(Item.currentIdItem);
            Item.storedStackArray.add(Item.currentStack);
            Item.storedSlotArray.add(Item.currentSlot);
        }
        else {
            int index = Item.storedSlotArray.indexOf(Item.currentSlot);
            Item.storedIdItemArray.set(index, Item.currentIdItem);
            Item.storedStackArray.set(index, Item.currentStack);
            Item.storedSlotArray.set(index, Item.currentSlot);
        }

    }
    public void simulateChestContent(Random rand, List<Item> list, int maxRandomItems) {

        for (int currentItem = 0; currentItem < maxRandomItems; ++currentItem)
        {
            Item itemObject = Item.getRandomItemFromList(rand, list);

            assert itemObject != null;

            int stack = itemObject.minStack + rand.nextInt(itemObject.maxStack - itemObject.minStack + 1);


            if (itemObject.maxStackLimit >= stack)
            {

                currentItem(itemObject.id,stack,rand.nextInt(27));

                storeCurrentItemFromSimulatedChest();

                if(debug)System.out.println("idItem:"+ Item.currentIdItem +" stack:"+ Item.currentStack +" slot:"+ Item.currentSlot +" chest:"+ this.chestId);


            }
            else
            {
                for (int currentStack = 0; currentStack < stack; ++currentStack)
                {

                    currentItem(itemObject.id,stack,rand.nextInt(27));

                    if(debug)System.out.println("idItem:"+ Item.currentIdItem +" stack:"+ Item.currentStack +" slot:"+ Item.currentSlot +" chest:"+ this.chestId);

                    storeCurrentItemFromSimulatedChest();

                }
            }
        }
    }

}
