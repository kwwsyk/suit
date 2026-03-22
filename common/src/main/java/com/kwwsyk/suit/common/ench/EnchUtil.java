package com.kwwsyk.suit.common.ench;

public class EnchUtil {

    public static int transformLevelToXpCost(int level){
        if (level < 0) return -transformLevelToXpCost(-level);

        if (level < 15) {
            return level * level + 6 * level;
        } else if (level < 30) {
            return (5 * level * level - 43 * level) / 2 + 1080;
        } else {
            return (9 * level * level - 325 * level) / 2 + 5510;
        }
    }

}
