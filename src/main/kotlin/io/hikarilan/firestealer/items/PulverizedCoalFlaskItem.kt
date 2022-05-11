package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level

class PulverizedCoalFlaskItem : Item(
    Properties()
        .tab(FireStealerMod.creativeModeTab)
        .rarity(Rarity.COMMON)
        .craftRemainder(Items.GLASS_BOTTLE)
        .food(
            FoodProperties.Builder()
                .nutrition(1)
                .saturationMod(0.1f)
                .build()
        )
) {

    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        val item = super.finishUsingItem(pStack, pLevel, pLivingEntity)
        pLivingEntity.hurt(DamageSource.MAGIC, 2.0f)
        return if (pLivingEntity is Player && pLivingEntity.abilities.instabuild) item else ItemStack(Items.GLASS_BOTTLE)
    }

}