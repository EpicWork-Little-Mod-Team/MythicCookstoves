package cc.mycraft.mythic_cookstoves.blocks

import cc.mycraft.mythic_cookstoves.Sounds
import cc.mycraft.mythic_cookstoves.block_entities.MythicFireBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.*

class MythicFireBlock :
    Block(Properties.of(Material.FIRE).noCollission().sound(SoundType.AMETHYST).lightLevel { 15 }), EntityBlock {
    @Deprecated("use BlockState's getShape instead")
    override fun getShape(
        pState: BlockState,
        pLevel: BlockGetter,
        pPos: BlockPos,
        pContext: CollisionContext
    ): VoxelShape {
        return shape
    }

    @Deprecated("Deprecated in Java")
    override fun entityInside(pState: BlockState, pLevel: Level, pPos: BlockPos, pEntity: Entity) {
        super.entityInside(pState, pLevel, pPos, pEntity)
        pEntity.setSecondsOnFire(120)
        pEntity.hurt(DamageSource.IN_FIRE, 10.0f)
    }

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return MythicFireBlockEntity(pPos, pState)
    }

    override fun animateTick(pState: BlockState, pLevel: Level, pPos: BlockPos, pRand: Random) {
        if (pRand.nextInt(12) == 0) {
            Sounds.MYTHIC_FIRE_AMBIENT.playAt(
                pLevel,
                pPos,
                1.2f + pRand.nextFloat(),
                pRand.nextFloat() * 0.7f + 0.3f,
                false
            )
        }
        if (pRand.nextInt(4) == 0) {
            val x = pPos.x + pRand.nextDouble()
            val y = pPos.y + pRand.nextDouble()
            val z = pPos.z + pRand.nextDouble()
            val speedFunc = { pRand.nextGaussian(0.0,0.01) }
            pLevel.addParticle(ParticleTypes.FLAME, x, y, z, speedFunc(), speedFunc(), speedFunc())
        }
    }

    companion object {
        private val shape = box(6.0, 2.0, 6.0, 10.0, 6.0, 10.0)
    }
}