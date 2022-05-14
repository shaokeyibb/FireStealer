package io.hikarilan.firestealer.blocks

import io.hikarilan.firestealer.FireStealerMod
import io.hikarilan.firestealer.materials.FireStealerMaterials
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

@Mod.EventBusSubscriber(modid = FireStealerMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object FireStealerBlocks {

    val REGISTRY: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, FireStealerMod.MOD_ID)

    // 富燃素沙子
    val PHLOGISTON_RICH_SAND by REGISTRY.registerObject("phlogiston_rich_sand") {
        SandBlock(14406560, BlockBehaviour.Properties.of(Material.SAND).strength(0.5F).sound(SoundType.SAND).lightLevel { 7 })
    }

    // 荧光炬
    val GLOWSTONE_TORCH by REGISTRY.registerObject("glowstone_torch") {
        TorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel { 14 }
            .sound(SoundType.WOOD), ParticleTypes.GLOW)
    }

    // 墙上的荧光炬
    val GLOWSTONE_WALL_TORCH by REGISTRY.registerObject("glowstone_wall_torch") {
        WallTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().instabreak().lightLevel { 14 }
            .sound(SoundType.WOOD).lootFrom { GLOWSTONE_TORCH }, ParticleTypes.GLOW)
    }

    // 轻质元素块
    val LIGHT_WEIGHTED_ELEMENT_BLOCK by REGISTRY.registerObject("light_weighted_element_block"){
        Block(BlockBehaviour.Properties.of(FireStealerMaterials.LIGHT_WEIGHTED_ELEMENT_MATERIAL).strength(0.3F).sound(SoundType.GLASS))
    }

    @SubscribeEvent
    fun onClientSetup(e: FMLClientSetupEvent) {
        e.enqueueWork {
            ItemBlockRenderTypes.setRenderLayer(GLOWSTONE_TORCH, RenderType.cutout())
            ItemBlockRenderTypes.setRenderLayer(GLOWSTONE_WALL_TORCH, RenderType.cutout())
        }
    }

}