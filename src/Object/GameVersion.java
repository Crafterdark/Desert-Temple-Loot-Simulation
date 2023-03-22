package Object;

import java.util.ArrayList;
import java.util.List;

public class GameVersion {
    public static String releaseVersion;
    public static String mostRecentAndAvailableVersion = "1.8";
    public static ArrayList<String> versionList = new ArrayList<>(List.of("1.3","1.4.5","1.4.6","1.5","1.6","1.7","1.8"));

    public static String getGameVersion(){
        return releaseVersion;
    }

    public static boolean higherOrEqualThan(String gameVersion, String comparedVersion){

        int index1 = versionList.indexOf(comparedVersion);
        int index2 = versionList.indexOf(gameVersion);

        return index2 >= index1; //if gameVersion >= comparedVersion
    }

    public static boolean betweenVersionsOrEqualThan(String gameVersion, String rangeVersionMin, String rangeVersionMax){

        int indexMin = versionList.indexOf(rangeVersionMin);
        int indexMax = versionList.indexOf(rangeVersionMax);
        int indexGameVer = versionList.indexOf(gameVersion);

        return (indexMax >= indexGameVer) && (indexGameVer >= indexMin); // indexMax >= indexGameVer >= indexMin
    }

    public static void setGameVersionAndInitObjects(String gameVersion){
        releaseVersion = gameVersion;
        EnchBase.initBookList();
        LootTable.setCurrentDesertTempleLootTable();
    }

}
