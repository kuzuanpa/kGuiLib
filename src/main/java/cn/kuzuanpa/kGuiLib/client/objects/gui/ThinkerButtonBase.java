/*
 * This class was created by <kuzuanpa>. It is a part of kGuiLib.
 * Get the Source Code in github:
 * https://github.com/kuzuanpa/kGuiLib
 *
 * kGuiLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kGuiLib is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */
package cn.kuzuanpa.kGuiLib.client.objects.gui;

import cn.kuzuanpa.kGuiLib.client.IkGui;
import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableButton;
import cn.kuzuanpa.kGuiLib.client.anime.IGuiAnime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static cn.kuzuanpa.kGuiLib.kGuiLib.MOD_ID;

public class ThinkerButtonBase extends GuiButton implements IAnimatableButton {
    public final ArrayList<IGuiAnime> GuiAnimeList = new ArrayList<>();
    public long timer =0;
    public int animeXModify=0,animeYModify=0,animeWidthModify=0,animeHeightModify=0,joinTime=0,leaveTime=Integer.MAX_VALUE;
    public boolean needUpdate = true, firstFrameRendered = false;
    public ThinkerButtonBase(int id, int xPos, int yPos, int width, int height, String displayText) {
        super(id, xPos, yPos,width,height,displayText);
        this.fbo = new Framebuffer(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight,false);
    }

    public ThinkerButtonBase(int id, int xPos, int yPos, int width, int height) {
        this(id, xPos, yPos,width,height,"");
    }

    protected final Framebuffer fbo ;
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        needUpdate = updateRenderState(timer);
        if(needUpdate && (timer<joinTime || timer>leaveTime))return;
        int screenWidth = Minecraft.getMinecraft().displayWidth;
        int screenHeight = Minecraft.getMinecraft().displayHeight;
        GL11.glPushMatrix();

        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        int w = scaledresolution.getScaledWidth();
        int h = scaledresolution.getScaledHeight();

        //These Matrix and ortho settings are both applied in drawToFBO & drawFBOToScreen
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, w, h, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);

//todo: just update when necessary
        needUpdate = true;

        if(needUpdate)drawToFBO(mc,mouseX,mouseY,screenWidth,screenHeight);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glViewport(0,0,screenWidth,screenHeight);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);

        drawFBOToScreen(mc,mouseX,mouseY);

        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    public void drawToFBO(Minecraft mc, int mouseX, int mouseY, int screenWidth, int screenHeight) {
        firstFrameRendered = false;
        GL11.glPushMatrix();
        fbo.bindFramebuffer(false);
        GL11.glViewport(0,0,screenWidth,screenHeight);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        drawButton2(mc, mouseX, mouseY);

        fbo.unbindFramebuffer();
        GL11.glPopMatrix();

        Minecraft.getMinecraft().getFramebuffer().bindFramebuffer(true);
    }

    public void drawFBOToScreen(Minecraft mc, int mouseX, int mouseY){
        if(isAnimatedInFBO)IAnimatableButton.drawPre(this,timer);

        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        int w = scaledresolution.getScaledWidth();
        int h = scaledresolution.getScaledHeight();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.fbo.framebufferTexture);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D,  h, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV( w,  h, 0.0D, 1, 0.0D);
        tessellator.addVertexWithUV( w, 0.0D, 0.0D, 1, 1);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 1);

        if(FBOOffsetX!=0||FBOOffsetY!=0)GL11.glTranslatef(FBOOffsetX,FBOOffsetY,0);

        if(isAnimatedInFBO)IAnimatableButton.draw(this,timer);
        tessellator.draw();
        if(isAnimatedInFBO)IAnimatableButton.drawAfter(this,timer);
    }

    public void drawButton2(Minecraft mc, int mouseX, int mouseY)
    {
        //A default draw that very like to Minecraft button
        if (!this.visible|| timer<joinTime || timer > leaveTime)return;

        IAnimatableButton.drawPre(this,timer);

        FontRenderer fontrenderer = mc.fontRenderer;
        mc.getTextureManager().bindTexture(buttonTextures);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        boolean isMouseHovering=this.isMouseInButton(mouseX,mouseY);
        int k = this.getHoverState(isMouseHovering);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int l = 14737632;
        if (packedFGColour != 0)l = packedFGColour;
        else if (!this.enabled)l = 10526880;
        else if (isMouseHovering)l = 16777120;
        GuiAnimeList.forEach(anime -> anime.animeDraw(timer));
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
        this.mouseDragged(mc, mouseX, mouseY);
        this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);

        IAnimatableButton.drawAfter(this,timer);
    }

    public ThinkerButtonBase addAnime(IGuiAnime anime){
        GuiAnimeList.add(anime);
        return this;
    }

    public ThinkerButtonBase addToList(List<ThinkerButtonBase> list){
        list.add(this);
        return this;
    }

    @Override
    public void destroy() {
        fbo.deleteFramebuffer();
    }

    public boolean isMouseInButton(int mouseX, int mouseY)
    {
        animeXModify=animeYModify=animeHeightModify=animeWidthModify=0;
        GuiAnimeList.forEach(anime -> anime.updateButton(timer,this));
        return this.enabled && this.visible && mouseX >= this.xPosition+animeXModify && mouseY >= this.yPosition+animeYModify && mouseX < this.xPosition+animeXModify + this.width+animeWidthModify && mouseY < this.yPosition+animeYModify + this.height+animeHeightModify;
    }
    public void updateTimer(long timer){this.timer=timer;}
    /**@return 0: notify GUI to process this click event, 1: continue search without notify GUI, 2: stop search and notify GUI, 3: stop search without notify GUI**/
    public byte onPressed(IkGui gui, int posX, int posY){return 0;}
    public ArrayList<IGuiAnime> getGuiAnimeList() {return GuiAnimeList;}
    public boolean updateRenderState(long timer){
        return !firstFrameRendered || IAnimatableButton.isAnimeActive(this, this.timer);
    }
    public int FBOOffsetX=0,FBOOffsetY=0;
    public ThinkerButtonBase setFBOOffset(int x,int y){
        FBOOffsetX=x;
        FBOOffsetY=y;
        return this;
    }
    public boolean isAnimatedInFBO = false;

    public ThinkerButtonBase setAnimatedInFBO(boolean animatedInFBO) {
        isAnimatedInFBO = animatedInFBO;
        return this;
    }

    public ThinkerButtonBase setJoinLeaveTime(int joinTime, int leaveTime) {
        this.joinTime = joinTime;
        this.leaveTime=leaveTime;
        return this;
    }
}
