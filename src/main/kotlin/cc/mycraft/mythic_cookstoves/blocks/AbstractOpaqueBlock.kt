package cc.mycraft.mythic_cookstoves.blocks

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

abstract class AbstractOpaqueBlock(properties: Properties) : Block(properties) {
    override fun propagatesSkylightDown(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos): Boolean {
        return false
    }

    @Deprecated("Deprecated in Java")
    override fun getLightBlock(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos): Int {
        return pLevel.maxLightLevel
    }
}