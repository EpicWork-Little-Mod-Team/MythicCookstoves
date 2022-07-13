package cc.mycraft.mythic_cookstoves.datagen

import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import com.mojang.datafixers.util.Pair
import net.minecraft.data.DataGenerator
import net.minecraft.data.loot.BlockLoot
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.LootTables
import net.minecraft.world.level.storage.loot.ValidationContext
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.function.BiConsumer
import java.util.function.Consumer
import java.util.function.Supplier

class LootTablesGen(pGenerator: DataGenerator) : LootTableProvider(pGenerator) {
    override fun getTables(): MutableList<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> {
        return mutableListOf(
            Pair.of(Supplier(::BlockLoots), LootContextParamSets.BLOCK)
        )
    }

    override fun validate(map: Map<ResourceLocation, LootTable>, validationtracker: ValidationContext) {
        map.forEach { (key, value) -> LootTables.validate(validationtracker, key, value) }
    }

    private class BlockLoots : BlockLoot() {
        override fun addTables() {
            dropWhenSilkTouch(ModBlocks.MYTHIC_TINDER)
            dropSelf(ModBlocks.COOKSTOVE)
            add(ModBlocks.BONFIRE, ::createDoubleBlockTable)
            dropSelf(ModBlocks.SHALLOW_PAN)
            dropSelf(ModBlocks.SAUCEPAN)
            dropSelf(ModBlocks.STONE_MORTAR)
            dropSelf(ModBlocks.OAK_PESTLE)
            add(ModBlocks.STRAWBERRY_BUSH, noDrop())
            add(ModBlocks.CHILI, noDrop())
        }

        override fun getKnownBlocks(): MutableIterable<Block> {
            return ModBlocks.REGISTRY.entries.map { it.get() }.toMutableList()
        }

        private fun createDoubleBlockTable(block: Block): LootTable.Builder {
            return createSinglePropConditionTable(block, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)
        }
    }
}
