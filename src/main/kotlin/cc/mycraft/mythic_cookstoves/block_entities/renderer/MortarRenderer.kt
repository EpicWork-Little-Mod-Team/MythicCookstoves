package cc.mycraft.mythic_cookstoves.block_entities.renderer

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.block_entities.MortarBlockEntity
import cc.mycraft.mythic_cookstoves.blocks.mortar.AbstractMortarBlock
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.renderable.BakedRenderable

class MortarRenderer(pContext: BlockEntityRendererProvider.Context) : BlockEntityRenderer<MortarBlockEntity> {
    private val modelMap = mutableMapOf<Block, BakedRenderable>()

    private fun getPestleModel(pestle: Block): BakedRenderable {
        return modelMap.getOrPut(pestle) {
            BakedRenderable.of(
                ResourceLocation(
                    MythicCookstoves.MOD_ID,
                    "block/${checkNotNull(pestle.registryName).path}_inside"
                )
            )
        }
    }

    override fun render(
        pBlockEntity: MortarBlockEntity,
        pPartialTick: Float,
        pPoseStack: PoseStack,
        pBufferSource: MultiBufferSource,
        pPackedLight: Int,
        pPackedOverlay: Int
    ) {
        if (pBlockEntity.pestle == Blocks.AIR) return
        val facingYRot = pBlockEntity.blockState.getValue(AbstractMortarBlock.FACING).toYRot() / 360 + 0.5
        pPoseStack.pushPose()
        pPoseStack.translate(0.5, 0.5, 0.5)
        pPoseStack.mulPose(
            Vector3f.YP.rotation(
                -((facingYRot + pBlockEntity.angle + pPartialTick * pBlockEntity.velocity) * 2 * Math.PI).toFloat()
            )
        )
        pPoseStack.translate(-0.5, -0.5, -0.5)
        getPestleModel(pBlockEntity.pestle).render(
            pPoseStack,
            pBufferSource,
            { RenderType.solid() },
            pPackedLight,
            pPackedOverlay,
            pPartialTick,
            EmptyModelData.INSTANCE
        )
        pPoseStack.popPose()
    }
}