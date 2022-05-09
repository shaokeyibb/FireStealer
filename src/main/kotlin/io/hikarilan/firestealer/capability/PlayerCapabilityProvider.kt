package io.hikarilan.firestealer.capability

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class PlayerCapabilityProvider : ICapabilitySerializable<CompoundTag> {

    private val playerCapability: IPlayerCapability = PlayerCapabilityImpl()

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return if (cap == CapabilityManager.get(object : CapabilityToken<IPlayerCapability>() {}))
            LazyOptional.of { playerCapability as T }
        else LazyOptional.empty()
    }

    override fun serializeNBT(): CompoundTag {
        return CompoundTag().apply {
            putBoolean("has_unlocked_fire", playerCapability.hasUnlockedFire)
        }
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        playerCapability.hasUnlockedFire = nbt.getBoolean("has_unlocked_fire")
    }
}