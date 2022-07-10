package cc.mycraft.mythic_cookstoves.blocks.pestle

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

abstract class AbstractPestleBlock(properties: Properties) : Block(properties) {
    @Deprecated("use BlockState's getShape instead")
    override fun getShape(
        pState: BlockState,
        pLevel: BlockGetter,
        pPos: BlockPos,
        pContext: CollisionContext
    ): VoxelShape {
        return shape
    }

    companion object {
        private val shape = box(6.0, 0.0, 6.0, 10.0, 10.25, 10.0)
    }
}