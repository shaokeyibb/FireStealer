package io.hikarilan.firestealer.materials

import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor

object FireStealerMaterials {

    val LIGHT_WEIGHTED_ELEMENT_MATERIAL = Material.Builder(MaterialColor.ICE).build()

    val LIGHT_WEIGHTED_ELEMENT_ARMOR_MATERIAL = LightWeightedElementArmorMaterial()

}