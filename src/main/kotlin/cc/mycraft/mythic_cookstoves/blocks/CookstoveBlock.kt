package cc.mycraft.mythic_cookstoves.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class CookstoveBlock : AbstractOpaqueBlock(Properties.of(Material.STONE).requiresCorrectToolForDrops()) {
    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false))
    }

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(pBuilder)
        pBuilder.add(FACING)
        pBuilder.add(LIT)
    }

    override fun getStateForPlacement(pContext: BlockPlaceContext): BlockState? {
        return defaultBlockState().setValue(FACING, pContext.horizontalDirection.opposite)
    }

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
        val FACING = BlockStateProperties.HORIZONTAL_FACING
        val LIT = BlockStateProperties.LIT
        private val shape: VoxelShape

        init {
            val base = box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)
            val top = box(1.0, 15.0, 1.0, 15.0, 16.0, 15.0)
            shape = Shapes.or(base, top)
        }
    }
}