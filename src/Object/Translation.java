package Object;

public class Translation {

    public static String enchantName(int id) {
        return switch (id) {
            case 0 -> "Protection";
            case 1 -> "Fire Protection";
            case 2 -> "Feather Falling";
            case 3 -> "Blast Protection";
            case 4 -> "Projectile Protection";
            case 5 -> "Respiration";
            case 6 -> "Aqua Affinity";
            case 7 -> "Thorns";
            case 8 -> "Depth Strider";
            case 16 -> "Sharpness";
            case 17 -> "Smite";
            case 18 -> "Bane Of Arthropods";
            case 19 -> "Knockback";
            case 20 -> "Fire Aspect";
            case 21 -> "Looting";
            case 32 -> "Efficiency";
            case 33 -> "Silk Touch";
            case 34 -> "Unbreaking";
            case 35 -> "Fortune";
            case 48 -> "Power";
            case 49 -> "Punch";
            case 50 -> "Flame";
            case 51 -> "Infinity";
            case 61 -> "Luck Of The Sea";
            case 62 -> "Lure";
            default -> "Unknown Enchantment";  //EXCEPTION
        };


    }

}
