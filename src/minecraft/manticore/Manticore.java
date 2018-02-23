package manticore;

public class Manticore {
    public static boolean displayCoords = true;
    public static boolean lightActive = false;
    public static boolean chestESPActive = false;
    public static boolean xrayActive = false;
    public static int[] xrayBlocks = new int[]{54, 57};
    public static boolean xrayDiamondOre = true;
    public static boolean xrayCoalOre = true;
    public static boolean xrayIronOre = true;
    public static boolean xrayGoldOre = true;
    public static boolean xrayBedrock = true;

    public static float getPct(float min, float max, float val) {
        float pct = (val - min) / max;
        if (pct < 0F) pct = 0F;
        if (pct > 1F) pct = 1F;
        return pct;
    }

    public static float interpolate(float from, float to, float pct) {
        return from + (to - from) * pct;
    }
}