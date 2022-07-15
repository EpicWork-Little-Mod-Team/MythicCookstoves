package cc.mycraft.mythic_cookstoves.block_entities

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class FurnitureBlockEntity(pWorldPosition: BlockPos, pBlockState: BlockState) :
    BlockEntity(ModBlockEntities.FURNITURE, pWorldPosition, pBlockState) {
    private var _model = ""
    var model: String
        get() = _model
        set(value) {
            _model = value
            setChanged()
        }

    override fun load(pTag: CompoundTag) {
        super.load(pTag)
        _model = pTag.getString("model")
    }

    override fun saveAdditional(pTag: CompoundTag) {
        super.saveAdditional(pTag)
        pTag.putString("model", _model)
    }

    override fun getUpdateTag(): CompoundTag {
        return CompoundTag().apply { putString("model", _model) }
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }
}
