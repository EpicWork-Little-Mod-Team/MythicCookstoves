package cc.mycraft.mythic_cookstoves

import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DoublePlantBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf

fun preventCreativeDropFromBottomPart(
    pLevel: Level,
    pPos: BlockPos,
    pState: BlockState,
    pPlayer: Player?
) {
    val half = pState.getValue(DoublePlantBlock.HALF)
    if (half == DoubleBlockHalf.UPPER) {
        val blockpos = pPos.below()
        val blockstate = pLevel.getBlockState(blockpos)
        if (blockstate.`is`(pState.block) && blockstate.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
            val waterlogged =
                if (blockstate.hasProperty(BlockStateProperties.WATERLOGGED) &&
                    blockstate.getValue(BlockStateProperties.WATERLOGGED)
                )
                    Blocks.WATER.defaultBlockState() else Blocks.AIR.defaultBlockState()
            pLevel.setBlock(blockpos, waterlogged, 35)
            pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate))
        }
    }
}
