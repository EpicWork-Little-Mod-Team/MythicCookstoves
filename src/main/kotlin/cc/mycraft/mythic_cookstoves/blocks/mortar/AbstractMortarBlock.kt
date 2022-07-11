package cc.mycraft.mythic_cookstoves.blocks.mortar

import cc.mycraft.mythic_cookstoves.block_entities.ModBlockEntities
import cc.mycraft.mythic_cookstoves.block_entities.MortarBlockEntity
import cc.mycraft.mythic_cookstoves.blocks.pestle.AbstractPestleBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

abstract class AbstractMortarBlock(properties: Properties) :
    Block(properties), EntityBlock {
    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH))
    }

    override fun createBlockStateDefinition(pBuilder: StateDefinition.Builder<Block, BlockState>) {
        super.createBlockStateDefinition(pBuilder)
        pBuilder.add(FACING)
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
        val blockentity = pLevel.getBlockEntity(pPos)
        return if (blockentity is MortarBlockEntity && blockentity.pestle != Blocks.AIR) shapeWithPestle else shapeEmpty
    }

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return MortarBlockEntity(pPos, pState)
    }

    override fun <T : BlockEntity> getTicker(
        pLevel: Level,
        pState: BlockState,
        pBlockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (pBlockEntityType == ModBlockEntities.MORTAR) BlockEntityTicker(MortarBlockEntity.Companion::tick) else null
    }

    @Deprecated("Deprecated in Java")
    override fun use(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pPlayer: Player,
        pHand: InteractionHand,
        pHit: BlockHitResult
    ): InteractionResult {
        val itemstack = pPlayer.getItemInHand(pHand)
        val item = itemstack.item
        val blockentity =
            pLevel.getBlockEntity(pPos).let { if (it is MortarBlockEntity) it else throw IllegalStateException() }
        val flagSneaking = pPlayer.isCrouching
        val flagHandEmpty = itemstack.isEmpty
        val flagMortarEmpty = blockentity.pestle == Blocks.AIR
        return if (!flagMortarEmpty) {
            if (flagSneaking) {
                val newstack = ItemStack(blockentity.pestle)
                blockentity.pestle = Blocks.AIR
                if (itemstack.isEmpty) {
                    pPlayer.setItemInHand(pHand, newstack)
                } else if (!pPlayer.addItem(newstack)) {
                    pPlayer.drop(newstack, false)
                }
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos)
            } else {
                blockentity.turn()
            }
            InteractionResult.sidedSuccess(pLevel.isClientSide)
        } else if (!flagHandEmpty && item is BlockItem) {
            val b = item.block
            if (b is AbstractPestleBlock) {
                blockentity.pestle = b
                if (!pPlayer.abilities.instabuild) {
                    itemstack.shrink(1)
                }
                pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos)
                InteractionResult.sidedSuccess(pLevel.isClientSide)
            } else InteractionResult.PASS
        } else InteractionResult.PASS
    }

    override fun playerDestroy(
        pLevel: Level,
        pPlayer: Player,
        pPos: BlockPos,
        pState: BlockState,
        pBlockEntity: BlockEntity?,
        pTool: ItemStack
    ) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool)
        if (pBlockEntity is MortarBlockEntity) {
            popResource(pLevel, pPos, ItemStack(pBlockEntity.pestle))
        }
    }

    companion object {
        val FACING = BlockStateProperties.HORIZONTAL_FACING
        private val shapeWithPestle = box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0)
        private val shapeEmpty =
            Shapes.join(shapeWithPestle, box(4.0, 1.0, 4.0, 12.0, 6.0, 12.0), BooleanOp.ONLY_FIRST)
    }
}