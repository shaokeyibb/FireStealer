package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import net.minecraft.world.item.Item
import net.minecraft.world.item.Rarity
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object FireStealerItems {

    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, FireStealerMod.MOD_ID)

    // 燃素瓶
    val PHLOGISTON_BOTTLE: Item by REGISTRY.registerObject("phlogiston_bottle") {
        Item(Item.Properties().tab(FireStealerMod.creativeModeTab).fireResistant().rarity(Rarity.COMMON))
    }

    // 研钵
    val MORTAR by REGISTRY.registerObject("mortar"){
        MortarItem()
    }

    // 煤粉
    val PULVERIZED_COAL by REGISTRY.registerObject("pulverized_coal"){
        Item(Item.Properties().tab(FireStealerMod.creativeModeTab).rarity(Rarity.COMMON))
    }

    // 富燃素沙子（BlockItem）
    val PHLOGISTON_RICH_SAND_ITEM by REGISTRY.registerObject("phlogiston_rich_sand"){
        PhlogistonRichSandItem()
    }

}