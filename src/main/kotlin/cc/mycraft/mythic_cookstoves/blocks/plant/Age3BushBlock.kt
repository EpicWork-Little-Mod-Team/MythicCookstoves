package cc.mycraft.mythic_cookstoves.blocks.plant

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties

abstract class Age3BushBlock(properties: Properties) : BushBlock(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(AGE, 0))
    }

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        pBuilder.add(AGE)
        super.createBlockStateDefinition(pBuilder)
    }

    companion object {
        val AGE = BlockStateProperties.AGE_3
    }
}