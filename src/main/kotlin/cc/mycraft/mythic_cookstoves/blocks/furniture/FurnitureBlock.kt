package cc.mycraft.mythic_cookstoves.blocks.furniture

import cc.mycraft.mythic_cookstoves.block_entities.FurnitureBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class FurnitureBlock :
    Block(
        Properties.of(Material.BARRIER).strength(-1.0f, 3600000.8f).noDrops().noOcclusion()
            .isValidSpawn { _, _, _, _ -> false }), EntityBlock {
    init {
        registerDefaultState(
            stateDefinition.any().setValue(FACING, Direction.NORTH)
        )
    }

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(pBuilder)
        pBuilder.add(FACING)
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

    @Deprecated("Deprecated in Java")
    override fun getShadeBrightness(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos): Float {
        return 1.0f
    }

    override fun propagatesSkylightDown(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos): Boolean {
        return false
    }

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return FurnitureBlockEntity(pPos, pState)
    }

    companion object {
        val FACING = BlockStateProperties.HORIZONTAL_FACING
        private val shape = Shapes.block()
    }
}