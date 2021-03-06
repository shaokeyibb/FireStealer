package io.hikarilan.firestealer

import io.hikarilan.firestealer.capability.IPlayerCapability
import io.hikarilan.firestealer.capability.PlayerCapabilityProvider
import io.hikarilan.firestealer.items.IFireReplaceable
import io.hikarilan.firestealer.materials.FireStealerMaterials
import net.minecraft.ChatFormatting
import net.minecraft.Util
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.TagKey
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.level.Explosion
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.event.TickEvent.WorldTickEvent
import net.minecraftforge.event.entity.EntityLeaveWorldEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.server.command.TextComponentHelper

@EventBusSubscriber(modid = FireStealerMod.MOD_ID)
object FireStealerEventListener {

    @SubscribeEvent
    fun onAttachPlayerCapability(e: AttachCapabilitiesEvent<Entity>) {
        if (e.`object` !is ServerPlayer) return
        e.addCapability(ResourceLocation("${FireStealerMod.MOD_ID}:player_capability"), PlayerCapabilityProvider())
    }

    @SubscribeEvent
    fun onInteractFurnaceBlock(e: PlayerInteractEvent.RightClickBlock) {
        val player = e.player
        val level = player.level

        if (level.isClientSide()) return
        if (Configuration.COMMON_CONFIG.disableFurnaceExplode.get()) return


        val state = level.getBlockState(e.pos)

        if (!state.`is`(TagKey.create(Registry.BLOCK_REGISTRY, ResourceLocation("forge:furnaces")))) return

        val capability = player.getCapability(CapabilityManager.get(object : CapabilityToken<IPlayerCapability>() {}))

        if (!capability.isPresent) return
        if (capability.resolve().get().hasUnlockedFire) return

        e.isCanceled = true

        val hitLoc = e.hitVec.location

        player.level.setBlockAndUpdate(e.pos, Blocks.AIR.defaultBlockState())

        level.explode(
            null,
            DamageSource.IN_FIRE,
            null,
            hitLoc.x,
            hitLoc.y,
            hitLoc.z,
            4.0f,
            true,
            Explosion.BlockInteraction.DESTROY
        )

        player.sendMessage(
            TextComponentHelper.createComponentTranslation(
                player, "firestealer.text.interact_furnace_blocks_when_not_unlocked_fire"
            ).withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD), Util.NIL_UUID
        )
    }

    @SubscribeEvent
    fun onTimeChange(e: WorldTickEvent) {
        if (!e.side.isServer || e.world !is ServerLevel) return
        if (e.phase != TickEvent.Phase.END) return
        if (Configuration.COMMON_CONFIG.disableTimeChange.get()) return
        val playersOnline = e.world.server!!.playerCount

        val playersUnlockTheFire = e.world.server!!.playerList.players.count { player ->
            player.getCapability(CapabilityManager.get(object : CapabilityToken<IPlayerCapability>() {})).let {
                it.isPresent && it.resolve().get().hasUnlockedFire
            }
        }

        if (playersUnlockTheFire == playersOnline) return

        // 18000+(?????????????????????????????????????????????/???????????????)*6000
        (e.world as ServerLevel).dayTime = (18000 + playersUnlockTheFire.toDouble() / playersOnline * 6000).toLong()
    }

    @SubscribeEvent
    fun onFireReplace(e: EntityLeaveWorldEvent) {
        val entity = e.entity
        val world = e.world

        if (entity !is ItemEntity) return

        val item = entity.item.item

        if (item !is IFireReplaceable) return

        if (!entity.isOnFire) return

        val replace = (item as IFireReplaceable).onFireReplace(world, entity) ?: return

        val pos = entity.position()

        if (!world.isClientSide && world is ServerLevel) {
            world.addFreshEntity(
                ItemEntity(
                    world,
                    pos.x,
                    pos.y,
                    pos.z,
                    replace
                )
            )
        }
    }

    @SubscribeEvent
    fun onPlayerWearFullLightWeightedElementArmor(e: PlayerTickEvent) {
        if (!e.side.isServer || e.player !is ServerPlayer) return
        if (e.phase != TickEvent.Phase.END) return

        if (e.player.inventory.armor.any { it.item !is ArmorItem || (it.item as ArmorItem).material != FireStealerMaterials.LIGHT_WEIGHTED_ELEMENT_ARMOR_MATERIAL }) return

        e.player.addEffect(MobEffectInstance(MobEffects.JUMP, 200, 1, false, false, true))
        e.player.addEffect(MobEffectInstance(MobEffects.SLOW_FALLING, 200, 0, false, false, true))
    }

}