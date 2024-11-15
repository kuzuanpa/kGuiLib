package cn.kuzuanpa.kGuiLib.client.objects.gui;

import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableThinkerObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.kGuiLib.kGuiLib.MOD_ID;

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
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        mc.getTextureManager().bindTexture(textureLocation);
        GL11.glColor4f(1,1,1, 1);
        drawTexturedModalRect(xPosition,yPosition,u,v,width,height);
    }

    @Override
    public void drawFBOToScreen(Minecraft mc, int mouseX, int mouseY) {
        IAnimatableThinkerObject.drawPre(this,timer);
        IAnimatableThinkerObject.draw(this,timer);
        super.drawFBOToScreen(mc, mouseX, mouseY);
        IAnimatableThinkerObject.drawAfter(this,timer);
    }
}
