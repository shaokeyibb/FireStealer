package io.hikarilan.firestealer.blocks

import io.hikarilan.firestealer.FireStealerMod
import net.minecraft.world.level.block.SandBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object FireStealerBlocks {

    val REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, FireStealerMod.MOD_ID)

    // 富燃素沙子
    val PHLOGISTON_RICH_SAND by REGISTRY.registerObject("phlogiston_rich_sand") {
        SandBlock(14406560, BlockBehaviour.Properties.of(Material.SAND).strength(0.5F).sound(SoundType.SAND))
    }

}