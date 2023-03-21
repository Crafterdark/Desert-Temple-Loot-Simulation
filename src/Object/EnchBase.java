package Object;
import java.util.ArrayList;

import static Object.GameVersion.higherThan;

public class EnchBase {

    //Defining variables
    public static int maxEnchIdList;
    public int id, weight, type, value;

    //type: (Protection or Damage)
    //value: Protection or Damage values

    //Base
    public static final int[] p_b_e = new int[] {1, 10, 5, 5, 3};
    public static final int[] d_b_e = new int[] {1, 5, 5};

    //Level
    public static final int[] p_l_e = new int[] {11, 8, 6, 8, 6};
    public static final int[] d_l_e = new int[] {11, 8, 8};

    //Threshold
    public static final int[] p_t_e = new int[] {20, 12, 10, 12, 15};
    public static final int[] d_t_e = new int[] {20, 20, 20};

    //Enchantment Lists
    public static EnchBase[] e_list;
    public static EnchBase[] e_b_list;

    //type: 0 -> pt (protection)
    //type: 1 -> dt (damage)

    public static final EnchBase //Constant enchantments on all Releases
            Protection,
            FireProtection,
            FeatherFalling,
            BlastProtection,
            ProjectileProtection,
            Respiration,
            AquaAffinity,
            Thorns,Sharpness,
            Smite,
            BaneOfArthropods,
            Knockback,
            FireAspect,
            Looting,
            Efficiency,
            SilkTouch,
            Unbreaking,
            Fortune,
            Power,
            Punch,
            Flame,
            Infinity,
            LuckOfTheSea,
            Lure;
    public static EnchBase //Enchantments that change depending on the Release
            DepthStrider;


    static {

        selectMaxEnchList(GameVersion.getGameVersion());

        Protection = new EnchBase(0, 10, 0, 0);
        FireProtection = new EnchBase(1, 5,0, 1);
        FeatherFalling = new EnchBase(2, 5,0, 2);
        BlastProtection = new EnchBase(3, 2,0, 3);
        ProjectileProtection = new EnchBase(4, 5,0, 4);
        Respiration = new EnchBase(5, 2,1);
        AquaAffinity = new EnchBase(6, 2,2);
        Thorns = new EnchBase(7,1,3);
        if(higherThan(GameVersion.getGameVersion(),"1.8"))DepthStrider = new EnchBase(8,2,4);
        Sharpness = new EnchBase(16,  10,5,0);
        Smite = new EnchBase(17,  5,5, 1);
        BaneOfArthropods = new EnchBase(18,5,5, 2);
        Knockback = new EnchBase(19,5,6);
        FireAspect = new EnchBase(20,2,7);
        Looting = new EnchBase(21,2,8);
        Efficiency = new EnchBase(32, 10,9);
        SilkTouch = new EnchBase(33, 1,10);
        Unbreaking = new EnchBase(34,5,11);
        Fortune = new EnchBase(35, 2,8);
        Power = new EnchBase(48, 10,12);
        Punch = new EnchBase(49, 2,13);
        Flame = new EnchBase(50, 2,14);
        Infinity = new EnchBase(51, 1,15);
        LuckOfTheSea = new EnchBase(61, 2,8);
        Lure = new EnchBase(62,2,16);
    }
    public static ArrayList<EnchBase> allowedEnchList  = new ArrayList<>();

    public static void initBookList(){
        for (EnchBase ench : e_list) {
            if (ench!=null)allowedEnchList.add(ench);
        }
        e_b_list = allowedEnchList.toArray(new EnchBase[0]);
    }
    public void EnchInit(int id, int weight, int type){
        this.id = id;
        this.weight = weight;
        this.type = type;
        e_list[id] = this;
    }

    public EnchBase(int id, int weight, int type){
       EnchInit(id, weight,type);
       this.value = -1; //NO VALUE
    }
    public EnchBase(int id, int weight, int type, int value){
        EnchInit(id, weight,type);
        this.value = value;
    }

    public static void selectMaxEnchList(String releaseVersion){
        if (releaseVersion.equals("1.8")){
            maxEnchIdList = 63;
        }
        else if (releaseVersion.equals("1.7")){
            maxEnchIdList = 63;
        }
        else{
            maxEnchIdList = 63; //Assume newest
        }
        e_b_list = new EnchBase[maxEnchIdList];
        e_list = new EnchBase[256];
    }

    public static EnchBase getEnchBaseWithId(int id)
    {
        return e_list[id];
    }
    public int getMinEnchLevel()
    {
        return 1;
    }
    public int getMaxEnchLevel() {

        int value = getMaxEnchLevel_VALUE();
        if(value!=-1)return value;

        return 1;
    }
    public int getMaxEnchLevel_VALUE(){
        return switch (this.type) {
            case 0 -> 4;
            case 5, 9, 12 -> 5;
            case 1, 3, 8, 11, 16, 4 -> 3;
            case 6, 7, 13 -> 2;
            default -> -1; //ERROR
        };
    }
    public int getMinExpLevelEnch(int enchLevel) {

        int value = getMinExpLevelEnch_VALUE(enchLevel);
        if(value!=-1)return value;

        return 1 + enchLevel * 10; //DEFAULT
    }
    public int getMinExpLevelEnch_VALUE(int enchLevel){
        return switch (this.type) {
            case 0 -> p_b_e[this.value] + (enchLevel - 1) * p_l_e[this.value];
            case 5 -> d_b_e[this.value] + (enchLevel - 1) * d_l_e[this.value];
            case 1, 4 -> 10 * enchLevel;
            case 2 -> 1;
            case 3, 7 -> 10 + 20 * (enchLevel - 1);
            case 6 -> 5 + 20 * (enchLevel - 1);
            case 8, 16 -> 15 + (enchLevel - 1) * 9;
            case 9, 12 -> 1 + (enchLevel - 1) * 10;
            case 10 -> 15;
            case 11 -> 5 + (enchLevel - 1) * 8;
            case 13 -> 12 + (enchLevel - 1) * 20;
            case 14, 15 -> 20;
            default -> -1; //ERROR
        };

    }
    public int getMaxExpLevelEnch(int enchLevel) {

        int value = getMaxExpLevelEnch_VALUE(enchLevel);
        if(value!=-1)return value;

        return this.getMinExpLevelEnch(enchLevel) + 5; //DEFAULT

    }
    public int getMaxExpLevelEnch_VALUE(int enchLevel){
        return switch (this.type) {
            case 0 -> p_t_e[this.value] + this.getMinExpLevelEnch(enchLevel);
            case 5 -> d_t_e[this.value] + this.getMinExpLevelEnch(enchLevel);
            case 1 -> 30 + this.getMinExpLevelEnch(enchLevel);
            case 2 -> 40 + this.getMinExpLevelEnch(enchLevel);
            case 3, 6, 7, 8, 9, 10, 11, 16 -> 50 + this.getMinExpLevelEnch(enchLevel);
            case 4, 12 -> 15 + this.getMinExpLevelEnch(enchLevel);
            case 13 -> 25 + this.getMinExpLevelEnch(enchLevel);
            case 14, 15 -> 50;
            default -> -1; //ERROR
        };
    }
    public boolean canApplyWith(EnchBase ench) {

        boolean areDifferentEnchants = this != ench;

        if (this.type==0){
            if(ench.type==0){
                return ench.value != this.value && (this.value == 2 || ench.value == 2);
            }
        }

        else if (this.type==5){
            return !(ench.type==5);
        }

        else if (this.type == 8) {
            return (areDifferentEnchants) && (ench.id != SilkTouch.id);
        }

        else if (this.type == 10) {

            return (areDifferentEnchants) && (ench.id != Fortune.id);
        }

        return areDifferentEnchants;
    }
}



