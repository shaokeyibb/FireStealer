package io.hikarilan.firestealer

import io.hikarilan.firestealer.capability.IPlayerCapability
import io.hikarilan.firestealer.capability.PlayerCapabilityProvider
import net.minecraft.ChatFormatting
import net.minecraft.Util
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.TagKey
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Explosion
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.WorldTickEvent
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

        val state = level.getBlockState(e.pos)

        if (!state.`is`(TagKey.create(Registry.BLOCK_REGISTRY, ResourceLocation("forge:furnaces")))) return

        val capability = player.getCapability(CapabilityManager.get(object : CapabilityToken<IPlayerCapability>() {}))

        if (!capability.isPresent) return
        if (capability.resolve().get().hasUnlockedFire) return

        e.isCanceled = true

        val hitLoc = e.hitVec.location

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
        val playersOnline = e.world.server!!.playerCount

        val playersUnlockTheFire = e.world.server!!.playerList.players.count { player ->
            player.getCapability(CapabilityManager.get(object : CapabilityToken<IPlayerCapability>() {})).let {
                it.isPresent && it.resolve().get().hasUnlockedFire
            }
        }

        if (playersUnlockTheFire == playersOnline) return

        // 18000+(已经获得火种的（在线）玩家人数/总玩家人数)*6000
        (e.world as ServerLevel).dayTime = (18000 + playersUnlockTheFire.toDouble() / playersOnline * 6000).toLong()
    }

}