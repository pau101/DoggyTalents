package doggytalents.client.renderer.entity.layer;

import com.mojang.blaze3d.platform.GlStateManager;

import doggytalents.api.inferface.IThrowableItem;
import doggytalents.client.model.entity.ModelDog;
import doggytalents.client.renderer.entity.RenderDog;
import doggytalents.entity.EntityDog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerBone extends LayerRenderer<EntityDog, ModelDog> {
    
    public LayerBone(RenderDog dogRendererIn) {
        super(dogRendererIn);
    }

    @Override
    public void render(EntityDog dog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if(dog.hasBone()) {
            
            GlStateManager.pushMatrix();
    
            if(this.getEntityModel().isChild) { //isChild
                float f = 0.5F;
                GlStateManager.translatef(0.0F, 0.75F, -0.25F);
                GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            }

            if(dog.isSneaking())
                GlStateManager.translatef(0.0F, 0.2F, 0.0F);

            this.getEntityModel().head.postRender(0.0625F);
            GlStateManager.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(45.0F, 0.0F, 0.0F, 1.0F);

            GlStateManager.translated(0.20, -0.10, -0.10);
            IThrowableItem throwableItem = dog.getThrowableItem();
            Minecraft.getInstance().getItemRenderer().renderItem(throwableItem != null ? throwableItem.getRenderStack(dog.getBoneVariant()) : dog.getBoneVariant(), ItemCameraTransforms.TransformType.NONE);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}