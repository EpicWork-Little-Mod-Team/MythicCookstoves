package cc.mycraft.mythic_cookstoves.block_entities

import net.minecraft.core.BlockPos
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BonfireBlockEntity(pWorldPosition: BlockPos, pBlockState: BlockState) :
    BlockEntity(ModBlockEntities.BONFIRE, pWorldPosition, pBlockState) {
    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket? {
        return ClientboundBlockEntityDataPacket.create(this)
    }
}
