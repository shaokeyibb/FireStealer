package io.hikarilan.firestealer.materials

import io.hikarilan.firestealer.items.FireStealerItems
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient

class LightWeightedElementArmorMaterial : ArmorMaterial {

    private val slotProtections = intArrayOf(1, 2, 3, 1)

    private val healthPerSlot = intArrayOf(13, 15, 16, 11)

    private val durabilityMultiplier = 5

    private val enchantmentValue = 15

    override fun getDurabilityForSlot(pSlot: EquipmentSlot): Int =
        healthPerSlot[pSlot.index] * durabilityMultiplier

    override fun getDefenseForSlot(pSlot: EquipmentSlot): Int = slotProtections[pSlot.index]

    override fun getEnchantmentValue(): Int = enchantmentValue

    override fun getEquipSound(): SoundEvent = SoundEvents.ARMOR_EQUIP_LEATHER

    override fun getRepairIngredient(): Ingredient = Ingredient.of(FireStealerItems.LIGHT_WEIGHTED_ELEMENT_INGOT)

    override fun getName(): String = "light_weighted_element"

    override fun getToughness(): Float = 0.0f

    override fun getKnockbackResistance(): Float = 0.0f
}