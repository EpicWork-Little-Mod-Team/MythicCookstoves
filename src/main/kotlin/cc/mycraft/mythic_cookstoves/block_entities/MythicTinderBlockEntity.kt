package cc.mycraft.mythic_cookstoves.block_entities

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import thedarkcolour.kotlinforforge.forge.callWhenOn

class MythicTinderBlockEntity(pWorldPosition: BlockPos, pBlockState: BlockState) :
    BlockEntity(ModBlockEntities.MYTHIC_TINDER, pWorldPosition, pBlockState) {
    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket? {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    companion object {
        fun tick(pLevel: Level, pPos: BlockPos, pState: BlockState, pBlockEntity: BlockEntity) {
            if (pLevel.canSeeSky(pPos) && pLevel.random.nextInt(400) == 0) {
                val lightning = EntityType.LIGHTNING_BOLT.create(pLevel)
                lightning?.moveTo(Vec3.atBottomCenterOf(pPos))
                lightning?.let(pLevel::addFreshEntity)
            }
        }
    }
}
