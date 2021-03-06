package io.hikarilan.firestealer

import io.hikarilan.firestealer.blocks.FireStealerBlocks
import io.hikarilan.firestealer.capability.IPlayerCapability
import io.hikarilan.firestealer.crafting.PhlogistonBottleBrewingRecipe
import io.hikarilan.firestealer.crafting.PhlogistonRichRawOreRecipe
import io.hikarilan.firestealer.data.LootModifiers
import io.hikarilan.firestealer.items.FireStealerItems
import io.hikarilan.firestealer.loot.FireStealerLoots
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.brewing.BrewingRecipeRegistry
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
@Mod(FireStealerMod.MOD_ID)
object FireStealerMod {

    const val MOD_ID = "firestealer"
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    val creativeModeTab = object : CreativeModeTab(MOD_ID) {

        override fun makeIcon(): ItemStack = ItemStack(FireStealerItems.PHLOGISTON_BOTTLE)

    }

    @SubscribeEvent
    fun onRegisterCapabilities(e: RegisterCapabilitiesEvent) {
        e.register(IPlayerCapability::class.java)
    }

    @SubscribeEvent
    fun onGatherData(e: GatherDataEvent) {
        //e.generator.addProvider(LootGenerator(e.generator))
    }

    @SubscribeEvent
    fun onFMLCommonSetup(e: FMLCommonSetupEvent) {
        e.enqueueWork {
            FireStealerLoots.init()

            BrewingRecipeRegistry.addRecipe(PhlogistonBottleBrewingRecipe())
        }
    }

    init {
        FireStealerBlocks.REGISTRY.register(MOD_BUS)
        FireStealerItems.REGISTRY.register(MOD_BUS)
        LootModifiers.REGISTRY.register(MOD_BUS)
        PhlogistonRichRawOreRecipe.REGISTRY.register(MOD_BUS)

        val (first, second)
                = ForgeConfigSpec.Builder().configure { Configuration.Common(it) }
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, second)
        Configuration.COMMON_CONFIG = first
    }

}