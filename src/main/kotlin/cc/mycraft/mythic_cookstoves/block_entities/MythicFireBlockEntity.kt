package cc.mycraft.mythic_cookstoves.block_entities

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class MythicFireBlockEntity(pWorldPosition: BlockPos, pBlockState: BlockState) :
    BlockEntity(ModBlockEntities.MYTHIC_FIRE, pWorldPosition, pBlockState) {
    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket? {
        return ClientboundBlockEntityDataPacket.create(this)
    }
}
