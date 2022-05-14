package io.hikarilan.firestealer.items

import io.hikarilan.firestealer.FireStealerMod
import io.hikarilan.firestealer.blocks.FireStealerBlocks
import io.hikarilan.firestealer.materials.FireStealerMaterials
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.*
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object FireStealerItems {

    val REGISTRY: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, FireStealerMod.MOD_ID)

    // 燃素瓶
    val PHLOGISTON_BOTTLE: Item by REGISTRY.registerObject("phlogiston_bottle") {
        Item(Item.Properties().tab(FireStealerMod.creativeModeTab).fireResistant().rarity(Rarity.COMMON))
    }

    // 研钵
    val MORTAR by REGISTRY.registerObject("mortar") {
        MortarItem()
    }

    // 煤粉
    val PULVERIZED_COAL by REGISTRY.registerObject("pulverized_coal") {
        PulverizedCoalItem()
    }

    // 富燃素沙子（BlockItem）
    val PHLOGISTON_RICH_SAND_ITEM by REGISTRY.registerObject("phlogiston_rich_sand") {
        PhlogistonRichSandItem()
    }

    // 富燃素粗矿
    val PHLOGISTON_RICH_RAW_ORE by REGISTRY.registerObject("phlogiston_rich_raw_ore") {
        PhlogistonRichRawOreItem()
    }

    // 煤粉烧瓶
    val PULVERIZED_COAL_FLASK by REGISTRY.registerObject("pulverized_coal_flask") {
        PulverizedCoalFlaskItem()
    }

    // 荧光炬（BlockItem）
    val GLOWSTONE_TORCH_ITEM by REGISTRY.registerObject("glowstone_torch") {
        StandingAndWallBlockItem(
            FireStealerBlocks.GLOWSTONE_TORCH, FireStealerBlocks.GLOWSTONE_WALL_TORCH,
            Item.Properties().tab(FireStealerMod.creativeModeTab)
        )
    }

    // 轻质元素
    val LIGHT_WEIGHTED_ELEMENT by REGISTRY.registerObject("light_weighted_element") {
        Item(Item.Properties().tab(FireStealerMod.creativeModeTab).rarity(Rarity.COMMON))
    }

    // 轻质元素锭
    val LIGHT_WEIGHTED_ELEMENT_INGOT by REGISTRY.registerObject("light_weighted_element_ingot") {
        Item(Item.Properties().tab(FireStealerMod.creativeModeTab).rarity(Rarity.COMMON))
    }

    // 轻质元素块（BlockItem）
    val LIGHT_WEIGHTED_ELEMENT_BLOCK by REGISTRY.registerObject("light_weighted_element_block") {
        BlockItem(
            FireStealerBlocks.LIGHT_WEIGHTED_ELEMENT_BLOCK,
            Item.Properties().tab(FireStealerMod.creativeModeTab).rarity(Rarity.COMMON)
        )
    }

    val LIGHT_WEIGHTED_ELEMENT_HELMET by REGISTRY.registerObject("light_weighted_element_helmet") {
        ArmorItem(
            FireStealerMaterials.LIGHT_WEIGHTED_ELEMENT_ARMOR_MATERIAL, EquipmentSlot.HEAD, Item.Properties().tab(FireStealerMod.creativeModeTab)
        )
    }
    val LIGHT_WEIGHTED_ELEMENT_CHESTPLATE by REGISTRY.registerObject("light_weighted_element_chestplate") {
        ArmorItem(
            FireStealerMaterials.LIGHT_WEIGHTED_ELEMENT_ARMOR_MATERIAL, EquipmentSlot.CHEST, Item.Properties().tab(FireStealerMod.creativeModeTab)
        )
    }
    val LIGHT_WEIGHTED_ELEMENT_LEGGINGS by REGISTRY.registerObject("light_weighted_element_leggings") {
        ArmorItem(
            FireStealerMaterials.LIGHT_WEIGHTED_ELEMENT_ARMOR_MATERIAL, EquipmentSlot.LEGS, Item.Properties().tab(FireStealerMod.creativeModeTab)
        )
    }
    val LIGHT_WEIGHTED_ELEMENT_BOOTS by REGISTRY.registerObject("light_weighted_element_boots") {
        ArmorItem(
            FireStealerMaterials.LIGHT_WEIGHTED_ELEMENT_ARMOR_MATERIAL, EquipmentSlot.FEET, Item.Properties().tab(FireStealerMod.creativeModeTab)
        )
    }


}