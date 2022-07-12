package cc.mycraft.mythic_cookstoves.blocks

import cc.mycraft.mythic_cookstoves.Sounds
import cc.mycraft.mythic_cookstoves.block_entities.BonfireBlockEntity
import cc.mycraft.mythic_cookstoves.litDoubleBlockEmission
import cc.mycraft.mythic_cookstoves.preventCreativeDropFromBottomPart
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.*

class BonfireBlock :
    Block(Properties.of(Material.WOOD).noOcclusion().sound(SoundType.WOOD).lightLevel(litDoubleBlockEmission(15))),
    EntityBlock {
    init {
        registerDefaultState(
            stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false)
                .setValue(HALF, DoubleBlockHalf.LOWER)
        )
    }

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(pBuilder)
        pBuilder.add(FACING)
        pBuilder.add(LIT)
        pBuilder.add(HALF)
    }

    override fun getStateForPlacement(pContext: BlockPlaceContext): BlockState? {
        val blockpos = pContext.clickedPos
        val level = pContext.level
        return if (blockpos.y < level.maxBuildHeight - 1 &&
            level.getBlockState(blockpos.above()).canBeReplaced(pContext)
        ) {
            defaultBlockState().setValue(FACING, pContext.horizontalDirection.opposite)
        } else null
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
        return BonfireBlockEntity(pPos, pState)
    }

    override fun setPlacedBy(
        pLevel: Level,
        pPos: BlockPos,
        pState: BlockState,
        pPlacer: LivingEntity?,
        pStack: ItemStack
    ) {
        val blockpos = pPos.above()
        pLevel.setBlock(blockpos, defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER), 3)
    }

    @Deprecated("Deprecated in Java")
    override fun updateShape(
        pState: BlockState,
        pDirection: Direction,
        pNeighborState: BlockState,
        pLevel: LevelAccessor,
        pCurrentPos: BlockPos,
        pNeighborPos: BlockPos
    ): BlockState {
        val half = pState.getValue(DoorBlock.HALF)
        return if (pDirection.axis === Direction.Axis.Y && (half == DoubleBlockHalf.LOWER) == (pDirection == Direction.UP)) {
            if (pNeighborState.`is`(this) && pNeighborState.getValue(DoorBlock.HALF) != half)
                pState.setValue(FACING, pNeighborState.getValue(FACING)).setValue(LIT, pNeighborState.getValue(LIT))
            else Blocks.AIR.defaultBlockState()
        } else super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos)
    }

    override fun playerWillDestroy(pLevel: Level, pPos: BlockPos, pState: BlockState, pPlayer: Player) {
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer)
        if (!pLevel.isClientSide && pPlayer.isCreative) {
            preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer)
        }
    }

    override fun animateTick(pState: BlockState, pLevel: Level, pPos: BlockPos, pRand: Random) {
        if (pState.getValue(HALF) == DoubleBlockHalf.LOWER && pState.getValue(LIT)) {
            if (pRand.nextInt(10) == 0) {
                Sounds.BONFIRE_CRACKLE.playAt(
                    pLevel,
                    pPos,
                    0.5f + pRand.nextFloat(),
                    pRand.nextFloat() * 0.7f + 0.6f,
                    false
                )
            }
        }
    }

    companion object {
        val FACING = BlockStateProperties.HORIZONTAL_FACING
        val LIT = BlockStateProperties.LIT
        val HALF = BlockStateProperties.DOUBLE_BLOCK_HALF
        private val shape = Shapes.block()
    }
}