package cn.kuzuanpa.kGuiLib.client.objects.gui;

import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class CommonTexturedButton extends ThinkerButtonBase {
    public CommonTexturedButton(int id, int xPos, int yPos,int u,int v, int width, int height, String path) {
        super(id, xPos, yPos, width, height, "");
        this.u=u;
        this.v=v;
        textureLocation =  new ResourceLocation(MOD_ID,path);
    }
    public int u,v;
    final ResourceLocation textureLocation;

    @Override
    public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
        if(!visible)return;
        if(!isAnimatedInFBO)IAnimatableButton.drawPre(this, this.timer);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        mc.getTextureManager().bindTexture(textureLocation);

        GL11.glColor4f(1,1,1,1);
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(xPosition         , yPosition + height , this.zLevel, (u * f)        , (v+height) * f1);
        tessellator.addVertexWithUV(xPosition + width , yPosition + height , this.zLevel, (u + width) * f, (v+height) * f1);
        tessellator.addVertexWithUV(xPosition + width , yPosition          , this.zLevel, (u + width) * f,  v*f1);
        tessellator.addVertexWithUV(xPosition         , yPosition          , this.zLevel, (u * f)        ,  v*f1);

        GL11.glTranslatef(xPosition, yPosition ,0);
        if(!isAnimatedInFBO)IAnimatableButton.draw(this, this.timer);
        GL11.glTranslatef(-(xPosition ), -(yPosition ),0);

        tessellator.draw();

        if(!isAnimatedInFBO)IAnimatableButton.drawAfter(this, this.timer);
    }

}
