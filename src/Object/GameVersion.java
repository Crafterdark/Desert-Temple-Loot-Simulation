package Object;

public class GameVersion {
    public static String releaseVersion;

    public static String getGameVersion(){
        return releaseVersion;
    }

    public static boolean higherThan(String highestVersion, String lowestVersion){
        if (lowestVersion.equals("1.3")){
            return !highestVersion.equals("1.3");
        }

        //TODO: Add the other cases

        else if(lowestVersion.equals("1.8")){
            return highestVersion.equals("1.8");
        }

        return false; //ERROR
    }

    public static void setGameVersionAndInitObjects(String gameVersion){
        releaseVersion = gameVersion;
        EnchBase.initBookList();
        LootTable.setCurrentDesertTempleLootTable();
    }

}
