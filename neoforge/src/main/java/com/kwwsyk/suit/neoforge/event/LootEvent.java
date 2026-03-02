package com.kwwsyk.suit.neoforge.event;

import com.kwwsyk.suit.common.options.ServerConfigs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.Collection;
import java.util.List;

@EventBusSubscriber
public class LootEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onBlockDrops(BlockDropsEvent event){
        List<ItemEntity> drops = event.getDrops();
        if(ServerConfigs.PICKUP_HELPER.ITEM_DROPS.PROTECT_DROPS.get()){
            drops.forEach(
                    item -> {
                        item.invulnerableTime = 6000;
                    }
            );
        }
        if(!(event.getBreaker() instanceof Player player)) return;
        if(ServerConfigs.PICKUP_HELPER.ITEM_DROPS.SEND_TO_INVENTORY.get()){
            drops.forEach(
                    itemEntity -> {
                        itemEntity.setNoPickUpDelay();
                        itemEntity.playerTouch(player);
                    }
            );
        }
        int v;
        if((v = ServerConfigs.PICKUP_HELPER.ITEM_DROPS.DIRECTED_DISTRIBUTE.get()) != 0){
            drops.forEach(
                    itemEntity -> {
                        if(itemEntity.isRemoved()) return;
                        if(v < 0) tpEntityToPlayer(itemEntity, player);
                        else directEntityToPlayer(itemEntity, player, 0.05 * v);
                    }
            );
        }

        if(ServerConfigs.PICKUP_HELPER.EXP_DROPS.GIVE_DIRECTLY.get()){
            int xp = event.getDroppedExperience();
            if(xp > 0){
                player.giveExperiencePoints(xp);
                event.setDroppedExperience(0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingDrops(LivingDropsEvent event){
        // Only handle player-caused kills
        if(!(event.getSource().getEntity() instanceof Player player)) return;
        Collection<ItemEntity> drops = event.getDrops();

        if(ServerConfigs.PICKUP_HELPER.ITEM_DROPS.PROTECT_DROPS.get()){
            drops.forEach(item -> item.invulnerableTime = 6000);
        }

        if(ServerConfigs.PICKUP_HELPER.ITEM_DROPS.SEND_TO_INVENTORY.get()){
            drops.forEach(itemEntity -> {
                itemEntity.setNoPickUpDelay();
                itemEntity.playerTouch(player);
            });
        }

        int v;
        if((v = ServerConfigs.PICKUP_HELPER.ITEM_DROPS.DIRECTED_DISTRIBUTE.get()) != 0){
            drops.forEach(itemEntity -> {
                if(itemEntity.isRemoved()) return;
                if(v < 0) tpEntityToPlayer(itemEntity, player);
                else directEntityToPlayer(itemEntity, player, 0.05 * v);
            });
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event){
        Player player = event.getAttackingPlayer();
        if(player == null) return;

        // Deliver experience directly via event when configured
        if(ServerConfigs.PICKUP_HELPER.EXP_DROPS.GIVE_DIRECTLY.get()){
            int xp = event.getDroppedExperience();
            if(xp > 0){
                player.giveExperiencePoints(xp);
                event.setDroppedExperience(0);
            }
        }

        // Note: Protection and directed distribution for XP orbs likely require
        // altering ExperienceOrb behavior (e.g., via mixin). Left unimplemented here.
    }

    private static void directEntityToPlayer(Entity entity,
                                             Player player,
                                             double speed) {
        var from = entity.position();
        var to = player.position().add(0.0, player.getBbHeight() * 0.75, 0.0);
        var dir = to.subtract(from);
        if (dir.lengthSqr() < 1.0e-6) return;

        dir = dir.scale(speed);
        entity.setDeltaMovement(dir);
    }

    private static void tpEntityToPlayer(Entity entity,
                                             Player player
                                             ) {
        var to = player.position();
        entity.setPos(to);
    }
}
