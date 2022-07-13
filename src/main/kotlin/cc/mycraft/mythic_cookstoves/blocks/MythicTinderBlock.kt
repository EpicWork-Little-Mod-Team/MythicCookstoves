package cc.mycraft.mythic_cookstoves.blocks

import cc.mycraft.mythic_cookstoves.Sounds
import cc.mycraft.mythic_cookstoves.block_entities.ModBlockEntities
import cc.mycraft.mythic_cookstoves.block_entities.MortarBlockEntity
import cc.mycraft.mythic_cookstoves.block_entities.MythicTinderBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import java.util.*

class MythicTinderBlock :
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
        if (pEntity.hurt(DamageSource.IN_FIRE, 10.0f)) {
            pEntity.playSound(SoundEvents.GENERIC_BURN, 1.2f, 2.0f + pLevel.random.nextFloat() * 0.4f)
        }
    }

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return MythicTinderBlockEntity(pPos, pState)
    }

    override fun <T : BlockEntity> getTicker(
        pLevel: Level,
        pState: BlockState,
        pBlockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if (pBlockEntityType == ModBlockEntities.MYTHIC_TINDER) BlockEntityTicker(MythicTinderBlockEntity.Companion::tick) else null
    }

    override fun animateTick(pState: BlockState, pLevel: Level, pPos: BlockPos, pRand: Random) {
        if (pRand.nextInt(12) == 0) {
            Sounds.mythic_tinder_AMBIENT.playAt(
                pLevel,
                pPos,
                1.2f + pRand.nextFloat(),
                pRand.nextFloat() * 0.7f + 0.3f,
                false
            )
        }
        if (pRand.nextBoolean()) {
            val x = pPos.x + pRand.nextDouble()
            val y = pPos.y + pRand.nextDouble()
            val z = pPos.z + pRand.nextDouble()
            val speedFunc = { pRand.nextGaussian(0.0,0.01) }
            pLevel.addParticle(ParticleTypes.FLAME, x, y, z, speedFunc(), speedFunc(), speedFunc())
        }
        if (pRand.nextInt(4) == 0) {
            val x = pPos.x + pRand.nextDouble()
            val y = pPos.y + pRand.nextDouble()
            val z = pPos.z + pRand.nextDouble()
            val speedFunc = { pRand.nextGaussian(0.0,0.01) }
            pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, speedFunc(), speedFunc(), speedFunc())
        }
    }

    companion object {
        private val shape = box(6.0, 2.0, 6.0, 10.0, 6.0, 10.0)
    }
}