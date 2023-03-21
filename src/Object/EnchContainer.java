package Object;
import java.util.*;

import static Object.EnchBase.getEnchBaseWithId;
import static Object.GameVersion.getGameVersion;

public class EnchContainer {
    public final EnchBase enchBase;
    public final int enchLevel, weight;
    public static ArrayList<Integer> enchIds = new ArrayList<>();
    public static ArrayList<Integer> enchIdsToRemove = new ArrayList<>();
    public static ArrayList<EnchContainer> enchContainers = new ArrayList<>();

    public EnchContainer(EnchBase enchBase, int enchLevel) {
        this.enchBase = enchBase;
        this.weight = enchBase.weight;
        this.enchLevel = enchLevel;
    }

    public static EnchContainer getRandomEnchContainer(Random rand, Collection<EnchContainer> enchContainers) {
        int totalWeight = 0;

        for (EnchContainer enchContainer : enchContainers) {
            totalWeight += enchContainer.weight;
        }

        int randWeight = rand.nextInt(totalWeight);

        for (EnchContainer enchContainer : enchContainers) {
            randWeight -= enchContainer.weight;

            if (randWeight < 0) {
                return enchContainer;

            }
        }

        return null;
    }

    public static void removeInvalidEnchFromList(){
        for (Integer removeId : enchIdsToRemove) {
            enchContainers.remove(enchIds.indexOf(removeId));
            enchIds.remove(removeId);
        }
        enchIdsToRemove = new ArrayList<>();
    }

    public static void removeEnchIncompatibleWith(List<EnchContainer> enchList){
        for (Integer currentId : enchIds) {
            for (EnchContainer enchContainer : enchList) {

                if (!enchContainer.enchBase.canApplyWith(getEnchBaseWithId(currentId))) {
                    enchIdsToRemove.add(currentId);
                    break;
                }
            }
        }
        removeInvalidEnchFromList();
    }

    public static List<EnchContainer> createEnchContainersForBook(Random rand, int enchLevel) {

        int enchValue = (int) ((float) (1 + rand.nextInt(1) + rand.nextInt(1) + enchLevel) * (1.0F + ((rand.nextFloat() + rand.nextFloat() - 1.0F) * 0.15F)) + 0.5F);
        if (enchValue < 1) enchValue = 1;

        List<EnchContainer> enchList = new ArrayList<>();

        setEnchContainers(enchValue);

        if (!enchIds.isEmpty()) {

            enchList.add(getRandomEnchContainer(rand, enchContainers)); //Add at least one enchantment to the book

            for (int currentEnchValue = enchValue; rand.nextInt(50) <= currentEnchValue; currentEnchValue /= 2) {

                removeEnchIncompatibleWith(enchList);

                if (!enchIds.isEmpty()) {
                    enchList.add(getRandomEnchContainer(rand, enchContainers)); //Add a random extra enchantment that is valid
                }
            }

        }

        return enchList;

    }

    public static void setEnchContainers(int enchValue) {

        HashMap chaoticMap = null;

        for (EnchBase ench : EnchBase.e_list) {
            if (ench == null) continue;

            for (int enchLevel = ench.getMinEnchLevel(); enchLevel <= ench.getMaxEnchLevel(); ++enchLevel) {
                if (enchValue >= ench.getMinExpLevelEnch(enchLevel) && enchValue <= ench.getMaxExpLevelEnch(enchLevel)) {

                    if (getGameVersion().equals("1.8")) {

                        //The game uses the correct order for enchantments in 1.8+

                        if (enchIds.contains(ench.id)) {
                            enchContainers.set(enchIds.indexOf(ench.id), new EnchContainer(ench, enchLevel));
                        } else {
                            enchIds.add(ench.id);
                            enchContainers.add(new EnchContainer(ench, enchLevel));
                        }

                    } else if (getGameVersion().equals("1.7")) {

                        //The game accidentally used a simple HashMap in 1.7, which caused the list to be rehashed and reordered, instead of the intended ascending order.
                        //This code simulates that part to retrieve the correct reordered list

                        if (chaoticMap == null) chaoticMap = new HashMap<>();
                        chaoticMap.put(ench.id, new EnchContainer(ench, enchLevel));

                    }

                }
            }
        }

        if (getGameVersion().equals("1.7")) {
            enchIds.addAll(chaoticMap.keySet());
            enchContainers.addAll(chaoticMap.values());
        }

    }
}
