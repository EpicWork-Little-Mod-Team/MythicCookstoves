package cc.mycraft.mythic_cookstoves

import cc.mycraft.mythic_cookstoves.block_entities.ModBlockEntities
import cc.mycraft.mythic_cookstoves.block_entities.renderer.BonfireRenderer
import cc.mycraft.mythic_cookstoves.block_entities.renderer.MortarRenderer
import cc.mycraft.mythic_cookstoves.block_entities.renderer.MythicFireRenderer
import cc.mycraft.mythic_cookstoves.blocks.BonfireBlock
import cc.mycraft.mythic_cookstoves.blocks.ICutoutRender
import cc.mycraft.mythic_cookstoves.blocks.ModBlocks
import cc.mycraft.mythic_cookstoves.blocks.MythicFireBlock
import cc.mycraft.mythic_cookstoves.blocks.pestle.AbstractPestleBlock
import cc.mycraft.mythic_cookstoves.items.ModItems
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ForgeModelBakery
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

@Mod(MythicCookstoves.MOD_ID)
object MythicCookstoves {
    const val MOD_ID = "mythic_cookstoves"
    val LOGGER = LogManager.getLogger(MOD_ID);

    init {
        ModBlocks.REGISTRY.register(MOD_BUS)
        ModItems.REGISTRY.register(MOD_BUS)
        ModBlockEntities.REGISTRY.register(MOD_BUS)

        runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onModelRegistry)
                MOD_BUS.addListener(::onRendererRegister)
                MOD_BUS.addListener(::onClientSetup)
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
            }
        )
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        ForgeRegistries.BLOCKS.values.filter {
            it.registryName?.namespace == MOD_ID && it is ICutoutRender
        }.forEach { ItemBlockRenderTypes.setRenderLayer(it, RenderType.cutout()) }
        LOGGER.info("Mode: Client")
    }

    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.info("Mode: Dedicated server")
    }

    private fun onModelRegistry(event: ModelRegistryEvent) {
        ForgeRegistries.BLOCKS.values.filter { it.registryName?.namespace == MOD_ID }.forEach {
            val registryName = it.registryName
            checkNotNull(registryName)
            if (it is AbstractPestleBlock) {
                ForgeModelBakery.addSpecialModel(ResourceLocation(MOD_ID, "block/${registryName.path}_inside"))
            } else if (it is MythicFireBlock) {
                ForgeModelBakery.addSpecialModel(ResourceLocation(MOD_ID, "block/${registryName.path}"))
            } else if (it is BonfireBlock) {
                ForgeModelBakery.addSpecialModel(ResourceLocation(MOD_ID, "block/${registryName.path}"))
                ForgeModelBakery.addSpecialModel(ResourceLocation(MOD_ID, "block/${registryName.path}_lit"))
            }
        }
    }

    private fun onRendererRegister(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(ModBlockEntities.MYTHIC_FIRE, ::MythicFireRenderer)
        event.registerBlockEntityRenderer(ModBlockEntities.MORTAR, ::MortarRenderer)
        event.registerBlockEntityRenderer(ModBlockEntities.BONFIRE, ::BonfireRenderer)
    }
}