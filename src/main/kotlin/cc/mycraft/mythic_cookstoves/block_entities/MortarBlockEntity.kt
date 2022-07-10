package cc.mycraft.mythic_cookstoves.block_entities

import cc.mycraft.mythic_cookstoves.Sounds
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.callWhenOn
import kotlin.math.floor
import kotlin.math.max


class MortarBlockEntity(pWorldPosition: BlockPos, pBlockState: BlockState) :
    BlockEntity(ModBlockEntities.MORTAR, pWorldPosition, pBlockState) {
    private var _pestle = Blocks.AIR
    private var _angle = 0.0
    var pestle: Block
        get() = _pestle
        set(value) {
            _pestle = value
            if (value == Blocks.AIR) _angle = 0.0
            setChanged()
        }
    var angle: Double
        get() = _angle
        set(value) {
            _angle = value
            _angle -= floor(_angle)
            setChanged()
        }
    var velocity = 0.0
    var audioTick = 0
        set(value) {
            field = value % 5
        }

    override fun load(pTag: CompoundTag) {
        super.load(pTag)
        _pestle = ForgeRegistries.BLOCKS.getValue(ResourceLocation(pTag.getString("pestle"))) ?: Blocks.AIR
        _angle = if (pTag.contains("angle", 6)) pTag.getDouble("angle") else 0.0
    }

    override fun saveAdditional(pTag: CompoundTag) {
        super.saveAdditional(pTag)
        pTag.putString("pestle", _pestle.registryName.toString())
        pTag.putDouble("angle", _angle)
    }

    override fun getUpdateTag(): CompoundTag {
        return CompoundTag().apply { putString("pestle", _pestle.registryName.toString()) }
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    private fun convertUnit(perMinute: Double) = perMinute / 1200

    private fun tick(pLevel: Level, pPos: BlockPos, pState: BlockState) {
        if (pestle == Blocks.AIR) return
        if (velocity > 0) {
            angle += velocity
            velocity = max(velocity - convertUnit(friction), 0.0)
        }
    }

    @OnlyIn(Dist.CLIENT)
    private fun tickAudio(pLevel: Level, pPos: BlockPos, pState: BlockState) {
        if (pestle == Blocks.AIR) return
        if (velocity > 0) {
            if (audioTick == 0) {
                Sounds.MORTAR_GRINDING.playAt(
                    pLevel, pPos, (velocity / convertUnit(volumeRate)).toFloat(), (1.2 - velocity * 0.4).toFloat(), true
                )
            }
            ++audioTick
        } else audioTick = 0
    }

    fun turn() {
        velocity = convertUnit(rpm)
    }

    companion object {
        private const val rpm = 30.0
        private const val friction = 3.0
        private const val volumeRate = 10.0

        fun tick(pLevel: Level, pPos: BlockPos, pState: BlockState, pBlockEntity: BlockEntity) {
            if (pBlockEntity is MortarBlockEntity) {
                pBlockEntity.tick(pLevel, pPos, pState)
                callWhenOn(Dist.CLIENT) { pBlockEntity.tickAudio(pLevel, pPos, pState) }
            }
        }
    }
}