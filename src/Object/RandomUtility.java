package Object;

import java.util.Random;

public class RandomUtility extends Random {

    public void setSeedBeforeStructurePopulation(RandomUtility rand, long worldSeed, int chunkX, int chunkZ){

        rand.setSeed(worldSeed);
        long long1 = rand.nextLong() / 2L * 2L + 1L;
        long long2 = rand.nextLong() / 2L * 2L + 1L;
        rand.setSeed((long)chunkX * long1 + (long)chunkZ * long2 ^ worldSeed);

    }

}
