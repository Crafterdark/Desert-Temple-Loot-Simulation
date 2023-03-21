package Object;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Item
{
    public final int id, weight, minStack, maxStack, maxStackLimit;
    public static int currentIdItem, currentStack, currentSlot;
    public Item(int id, int minChance, int maxChance, int weight, int maxStackLimit) {
        this.id = id;
        this.minStack = minChance;
        this.maxStack = maxChance;
        this.weight = weight;
        this.maxStackLimit = maxStackLimit;
    }

    public static void currentItem(int currId, int currStack, int currSlot){
        currentIdItem = currId;
        currentStack = currStack;
        currentSlot = currSlot;
    }

    public static ArrayList<Integer> storedIdItemArray, storedStackArray, storedSlotArray = new ArrayList<>();


    public static Item getRandomItemFromList(Random rand, Collection<Item> randItems) {
        int totalWeight = 0;

        for (Item randItem: randItems)
        {
            totalWeight += randItem.weight;
        }

        int randWeight = rand.nextInt(totalWeight);

        for (Item randItem : randItems)
        {
            randWeight -= randItem.weight;

            if (randWeight < 0)
            {
                return randItem;
            }
        }

        return null;
    }
}
