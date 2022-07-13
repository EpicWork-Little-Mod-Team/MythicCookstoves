package cc.mycraft.mythic_cookstoves

import net.minecraft.core.Vec3i
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn


@OnlyIn(Dist.CLIENT)
object Sounds {
    val mythic_tinder_AMBIENT = WrappedSound(
        SoundSource.BLOCKS,
        SoundData(SoundEvents.FIRE_AMBIENT, 1.0f, 1.2f),
        SoundData(SoundEvents.FIRE_AMBIENT, 1.0f, 0.8f),
    )
    val MORTAR_GRINDING = WrappedSound(
        SoundSource.BLOCKS,
        SoundData(SoundEvents.GRINDSTONE_USE, 1.0f, 0.8f),
    )
    val BONFIRE_CRACKLE = WrappedSound(
        SoundSource.BLOCKS,
        SoundData(SoundEvents.CAMPFIRE_CRACKLE, 1.0f, 1.0f)
    )

    data class SoundData(val sound: SoundEvent, val volume: Float, val pitch: Float)

    class WrappedSound(private val category: SoundSource, private vararg val sounds: SoundData) {
        fun play(level: Level, player: Player, x: Double, y: Double, z: Double, volume: Float, pitch: Float) {
            for (sound in sounds) {
                level.playSound(
                    player, x, y, z, sound.sound, category, sound.volume * volume, sound.pitch * pitch
                )
            }
        }

        fun playAt(level: Level, pos: Vec3i, volume: Float, pitch: Float, distanceDelay: Boolean) {
            playAt(level, pos.x + 0.5, pos.y + 0.5, pos.z + 0.5, volume, pitch, distanceDelay)
        }

        fun playAt(level: Level, x: Double, y: Double, z: Double, volume: Float, pitch: Float, distanceDelay: Boolean) {
            for (sound in sounds) {
                level.playLocalSound(
                    x, y, z, sound.sound, category, sound.volume * volume, sound.pitch * pitch, distanceDelay
                )
            }
        }
    }
}