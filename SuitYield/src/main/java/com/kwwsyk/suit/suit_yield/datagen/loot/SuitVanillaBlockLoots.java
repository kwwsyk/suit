package com.kwwsyk.suit.suit_yield.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class SuitVanillaBlockLoots extends BlockLootSubProvider {

    protected final Set<Block> knownBlocks = new LinkedHashSet<>();

    public SuitVanillaBlockLoots(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        addStrictSilkTouchOnlyNoOtherDrops();
        addSpecialUnobtainableNoDrop();

        knownBlocks.forEach(this::dropSelf);
    }

    private void addStrictSilkTouchOnlyNoOtherDrops() {
        strictSilkTouchOnly(
                // Glass
                Blocks.GLASS,
                Blocks.WHITE_STAINED_GLASS,
                Blocks.LIGHT_GRAY_STAINED_GLASS,
                Blocks.GRAY_STAINED_GLASS,
                Blocks.BLACK_STAINED_GLASS,
                Blocks.BROWN_STAINED_GLASS,
                Blocks.RED_STAINED_GLASS,
                Blocks.ORANGE_STAINED_GLASS,
                Blocks.YELLOW_STAINED_GLASS,
                Blocks.LIME_STAINED_GLASS,
                Blocks.GREEN_STAINED_GLASS,
                Blocks.CYAN_STAINED_GLASS,
                Blocks.LIGHT_BLUE_STAINED_GLASS,
                Blocks.BLUE_STAINED_GLASS,
                Blocks.PURPLE_STAINED_GLASS,
                Blocks.MAGENTA_STAINED_GLASS,
                Blocks.PINK_STAINED_GLASS,

                // Glass panes
                Blocks.GLASS_PANE,
                Blocks.WHITE_STAINED_GLASS_PANE,
                Blocks.LIGHT_GRAY_STAINED_GLASS_PANE,
                Blocks.GRAY_STAINED_GLASS_PANE,
                Blocks.BLACK_STAINED_GLASS_PANE,
                Blocks.BROWN_STAINED_GLASS_PANE,
                Blocks.RED_STAINED_GLASS_PANE,
                Blocks.ORANGE_STAINED_GLASS_PANE,
                Blocks.YELLOW_STAINED_GLASS_PANE,
                Blocks.LIME_STAINED_GLASS_PANE,
                Blocks.GREEN_STAINED_GLASS_PANE,
                Blocks.CYAN_STAINED_GLASS_PANE,
                Blocks.LIGHT_BLUE_STAINED_GLASS_PANE,
                Blocks.BLUE_STAINED_GLASS_PANE,
                Blocks.PURPLE_STAINED_GLASS_PANE,
                Blocks.MAGENTA_STAINED_GLASS_PANE,
                Blocks.PINK_STAINED_GLASS_PANE,

                // Ice
                Blocks.ICE,
                Blocks.PACKED_ICE,
                Blocks.BLUE_ICE,

                // Turtle egg
                Blocks.TURTLE_EGG,

                // Amethyst buds
                Blocks.SMALL_AMETHYST_BUD,
                Blocks.MEDIUM_AMETHYST_BUD,
                Blocks.LARGE_AMETHYST_BUD,

                // Bee nest
                Blocks.BEE_NEST,

                // Live coral
                Blocks.TUBE_CORAL,
                Blocks.BRAIN_CORAL,
                Blocks.BUBBLE_CORAL,
                Blocks.FIRE_CORAL,
                Blocks.HORN_CORAL,

                // Live coral fans
                Blocks.TUBE_CORAL_FAN,
                Blocks.BRAIN_CORAL_FAN,
                Blocks.BUBBLE_CORAL_FAN,
                Blocks.FIRE_CORAL_FAN,
                Blocks.HORN_CORAL_FAN,

                Blocks.CHISELED_BOOKSHELF
        );
    }

    private void addSpecialUnobtainableNoDrop() {
        noDropOverride(
                Blocks.BUDDING_AMETHYST,
                Blocks.REINFORCED_DEEPSLATE
        );
    }

    private void strictSilkTouchOnly(Block... blocks) {
        knownBlocks.addAll(Arrays.asList(blocks));
    }

    private void noDropOverride(Block... blocks) {
        knownBlocks.addAll(Arrays.asList(blocks));
    }
}
