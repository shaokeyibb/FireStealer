package io.hikarilan.firestealer.data

import net.minecraft.data.loot.EntityLoot
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.storage.loot.LootTable


class EntityLootTables : EntityLoot() {

    private val knownEntities: MutableSet<EntityType<*>> = mutableSetOf()

    override fun add(entity: EntityType<*>, builder: LootTable.Builder) {
        super.add(entity, builder)
        knownEntities.add(entity)
    }

    override fun add(id: ResourceLocation, table: LootTable.Builder) {
        super.add(id, table)
    }

    override fun getKnownEntities(): MutableIterable<EntityType<*>> = knownEntities

    override fun addTables() {
        // ignored
    }

}