package com.kwwsyk.suit.common.ench.conflict_solution;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Per-createResult conflict session.
 */
public class AnvilEnchantConflictSession {

    private static final int PROTECTION_SQUARE_SUM_CAP = 16;

    private final Object2IntOpenHashMap<Holder<Enchantment>> provisional = new Object2IntOpenHashMap<>();
    private int xpDelta;
    private boolean needsNormalization;

    public void begin() {
        this.provisional.clear();
        this.xpDelta = 0;
        this.needsNormalization = false;
    }

    public boolean allowVanillaConflict(Holder<Enchantment> incoming, int incomingLevel,
                                        Holder<Enchantment> existing, int existingLevel,
                                        boolean enchantedBook) {
        this.provisional.put(incoming, incomingLevel);
        this.provisional.put(existing, existingLevel);

        if (isInfinityMending(incoming, existing)) {
            this.xpDelta += enchantedBook ? 2 : 4;
            return true;
        }

        EnchConflictionSolution.MergeResult mergeResult = EnchUtilBridge.resolvePair(incoming, incomingLevel, existing, existingLevel);
        if (mergeResult.resolved()) {
            this.needsNormalization = true;
            this.xpDelta += mergeResult.extraXpCost();
            for (Map.Entry<Holder<Enchantment>, Integer> entry : mergeResult.enchantments().entrySet()) {
                this.provisional.put(entry.getKey(), entry.getValue());
            }
            return true;
        }

        if (incoming.is(EnchantmentTags.ARMOR_EXCLUSIVE) && existing.is(EnchantmentTags.ARMOR_EXCLUSIVE)) {
            this.needsNormalization = true;
            return true;
        }

        return false;
    }

    public FinalizationResult finalizeEnchantments(ItemEnchantments computed) {
        Object2IntOpenHashMap<Holder<Enchantment>> result = new Object2IntOpenHashMap<>();
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : computed.entrySet()) {
            result.put(entry.getKey(), entry.getIntValue());
        }
        if (needsNormalization) {
            normalizeArmorGroup(result);
        }
        return new FinalizationResult(result, xpDelta);
    }

    private void normalizeArmorGroup(Object2IntOpenHashMap<Holder<Enchantment>> result) {
        Set<Holder<Enchantment>> armorExclusive = new HashSet<>();
        for (Holder<Enchantment> enchantment : result.keySet()) {
            if (enchantment.is(EnchantmentTags.ARMOR_EXCLUSIVE)) {
                armorExclusive.add(enchantment);
            }
        }
        if (armorExclusive.isEmpty()) {
            return;
        }

        int squareSum = 0;
        for (Holder<Enchantment> enchantment : armorExclusive) {
            int level = result.getInt(enchantment);
            squareSum += level * level;
        }

        if (squareSum <= PROTECTION_SQUARE_SUM_CAP) {
            return;
        }

        Holder<Enchantment> reductionTarget = null;
        int maxLevel = -1;
        for (Holder<Enchantment> enchantment : armorExclusive) {
            if (enchantment.is(Enchantments.PROTECTION)) {
                continue;
            }
            int level = result.getInt(enchantment);
            if (level > maxLevel) {
                maxLevel = level;
                reductionTarget = enchantment;
            }
        }

        if (reductionTarget == null) {
            return;
        }

        int currentLevel = result.getInt(reductionTarget);
        while (currentLevel > 0 && squareSum > PROTECTION_SQUARE_SUM_CAP) {
            squareSum -= currentLevel * currentLevel;
            currentLevel -= 1;
            squareSum += currentLevel * currentLevel;
            this.xpDelta -= 1;
        }

        if (currentLevel <= 0) {
            result.removeInt(reductionTarget);
        } else {
            result.put(reductionTarget, currentLevel);
        }
    }

    private static boolean isInfinityMending(Holder<Enchantment> a, Holder<Enchantment> b) {
        return (a.is(Enchantments.INFINITY) && b.is(Enchantments.MENDING))
                || (a.is(Enchantments.MENDING) && b.is(Enchantments.INFINITY));
    }

    public record FinalizationResult(Object2IntOpenHashMap<Holder<Enchantment>> enchantments, int xpDelta) {
    }
}
