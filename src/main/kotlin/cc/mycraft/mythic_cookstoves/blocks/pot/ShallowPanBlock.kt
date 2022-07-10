package cc.mycraft.mythic_cookstoves.blocks.pot

import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class ShallowPanBlock : AbstractPotBlock(Properties.of(Material.HEAVY_METAL, MaterialColor.METAL).noOcclusion()) {
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
        private val shape: VoxelShape

        init {
            val bottom = box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0)
            val edge1N = box(3.0, 1.0, 2.0, 13.0, 2.0, 3.0)
            val edge1S = box(3.0, 1.0, 13.0, 13.0, 2.0, 14.0)
            val edge1W = box(2.0, 1.0, 3.0, 3.0, 2.0, 13.0)
            val edge1E = box(13.0, 1.0, 3.0, 14.0, 2.0, 13.0)
            val edge2N = box(3.0, 2.0, 1.0, 13.0, 3.0, 2.0)
            val edge2S = box(3.0, 2.0, 14.0, 13.0, 3.0, 15.0)
            val edge2W = box(1.0, 2.0, 3.0, 2.0, 3.0, 13.0)
            val edge2E = box(14.0, 2.0, 3.0, 15.0, 3.0, 13.0)
            val corner2NW = box(2.0, 2.0, 2.0, 3.0, 3.0, 3.0)
            val corner2NE = box(13.0, 2.0, 2.0, 14.0, 3.0, 3.0)
            val corner2SW = box(2.0, 2.0, 13.0, 3.0, 3.0, 14.0)
            val corner2SE = box(13.0, 2.0, 13.0, 14.0, 3.0, 14.0)
            shape = Shapes.or(
                bottom, edge1N, edge1S, edge1W, edge1E,
                edge2N, edge2S, edge2W, edge2E, corner2NW, corner2NE, corner2SW, corner2SE
            )
        }
    }
}