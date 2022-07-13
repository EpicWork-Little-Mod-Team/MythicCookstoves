package cc.mycraft.mythic_cookstoves.block_entities

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.util.Mth
import net.minecraft.world.entity.EntitySelector
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.phys.Vec3
import java.lang.Math.pow
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class MythicTinderBlockEntity(pWorldPosition: BlockPos, pBlockState: BlockState) :
    BlockEntity(ModBlockEntities.MYTHIC_TINDER, pWorldPosition, pBlockState) {
    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket? {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    companion object {
        fun tick(pLevel: Level, pPos: BlockPos, pState: BlockState, pBlockEntity: BlockEntity) {
            if (pPos.y == pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, pPos.x, pPos.z) - 1) {
                val blockPos = Vec3.atBottomCenterOf(pPos)
                val target = pLevel.getNearestPlayer(
                    pPos.x + 0.5,
                    pPos.y + 0.5,
                    pPos.z + 0.5,
                    16.0,
                    true
                )
                val targetPos = if (target != null) Vec3(target.x, target.y, target.z) else blockPos
                val distance = if (target != null) min(16.0, targetPos.distanceTo(blockPos)) else 16.0
                val threshold = 2.0.pow((16 - distance) / 2) / 400
                if (pLevel.random.nextDouble() < threshold) {
                    val lightning = EntityType.LIGHTNING_BOLT.create(pLevel)
                    lightning?.moveTo(targetPos)
                    lightning?.let(pLevel::addFreshEntity)
                }
            }
        }
    }
}
