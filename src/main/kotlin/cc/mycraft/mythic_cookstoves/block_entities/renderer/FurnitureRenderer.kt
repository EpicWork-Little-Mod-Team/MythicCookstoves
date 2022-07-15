package cc.mycraft.mythic_cookstoves.block_entities.renderer

import cc.mycraft.mythic_cookstoves.MythicCookstoves
import cc.mycraft.mythic_cookstoves.block_entities.FurnitureBlockEntity
import cc.mycraft.mythic_cookstoves.blocks.BonfireBlock
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.renderable.BakedRenderable

class FurnitureRenderer(pContext: BlockEntityRendererProvider.Context) : BlockEntityRenderer<FurnitureBlockEntity> {
    private val modelMap = mutableMapOf<String, BakedRenderable>()

    private fun getFurnitureModel(model: String): BakedRenderable {
        return modelMap.getOrPut(model) {
            BakedRenderable.of(
                ResourceLocation(
                    MythicCookstoves.MOD_ID,
                    "block/$model"
                )
            )
        }
    }

    override fun render(
        pBlockEntity: FurnitureBlockEntity,
        pPartialTick: Float,
        pPoseStack: PoseStack,
        pBufferSource: MultiBufferSource,
        pPackedLight: Int,
        pPackedOverlay: Int
    ) {
        if (pBlockEntity.model == "") return
        val facingYRot = pBlockEntity.blockState.getValue(BonfireBlock.FACING).toYRot() / 360 + 0.5
        pPoseStack.pushPose()
        pPoseStack.translate(0.5, 0.5, 0.5)
        pPoseStack.mulPose(Vector3f.YP.rotation(-(facingYRot * 2 * Math.PI).toFloat()))
        pPoseStack.translate(-0.5, -0.5, -0.5)
        getFurnitureModel(pBlockEntity.model).render(
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