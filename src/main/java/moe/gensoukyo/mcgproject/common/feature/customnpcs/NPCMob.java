package moe.gensoukyo.mcgproject.common.feature.customnpcs;

public class NPCMob {

    public int tab;
    public String name;
    public int max;
    public double weight;
    public int spawnDelay = -1;

    public NPCMob(int tab, String name, int max, double weight) {
        this.tab = tab;
        this.name = name;
        this.max = max;
        this.weight = weight;
    }

    public NPCMob(int tab, String name, int max, double weight, int spawnDelay) {
        this.tab = tab;
        this.name = name;
        this.max = max;
        this.weight = weight;
        this.spawnDelay = spawnDelay;
    }

}
