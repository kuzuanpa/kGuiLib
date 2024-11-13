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
import cn.kuzuanpa.kGuiLib.client.anime.IColorChangedAnime;
import cn.kuzuanpa.kGuiLib.client.anime.IGuiAnime;
import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableThinkerObject;
import cn.kuzuanpa.kGuiLib.client.objects.IBoundedButton;
import cn.kuzuanpa.kGuiLib.client.objects.IMouseWheelAccepter;
import cn.kuzuanpa.kGuiLib.client.objects.IThinkerObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ButtonList extends ThinkerButtonBase implements IAnimatableThinkerObject, IBoundedButton, IMouseWheelAccepter {
    int color;
    public ButtonList(int id, int posX, int posY, int width, int height){
        super(id,posX,posY,width,height,"");
        this.color=-1;
    }
    public float oldWheel=0F,YOffset=0,Inertia = 1.0F,scrollSpeed = 1.0F;
    public void tickWheel(){
        oldWheel+=oldWheel>0?-Inertia*10F : Inertia*10F;
        if(Math.abs(oldWheel)<= Inertia*10F)oldWheel=0;
        YOffset+=oldWheel/300*(scrollSpeed);
        if((YOffset)>0){YOffset=0;oldWheel=0;return;}
        if((YOffset)<-this.height){YOffset=-height;oldWheel=0;}
    }
    List<ThinkerButtonBase> buttons= new ArrayList<>();

    public ButtonList addSubButton(ThinkerButtonBase button){
        buttons.add(button);
        return this;
    }

    @Override
    public ThinkerButtonBase addAnime(IGuiAnime anime) {
        if(anime instanceof IColorChangedAnime) buttons.forEach(b->b.addAnime(anime));
        else super.addAnime(anime);
        return this;
    }

    public void updateTimer(long timer){ this.timer=timer; this.buttons.forEach(b->b.updateTimer(timer));}

    @Override
    public byte onPressed(IkGui gui, int mouseX, int mouseY) {
        for (int l = this.buttons.size() - 1; l >= 0 ;l--)
        {
            ThinkerButtonBase guibutton = this.buttons.get(l);
            mouseY-= (int) YOffset*Minecraft.getMinecraft().currentScreen.height/Minecraft.getMinecraft().displayHeight;
            if(!guibutton.isMouseInButton(mouseX, mouseY))continue;

            guibutton.func_146113_a(Minecraft.getMinecraft().getSoundHandler());

            byte buttonResult = guibutton.onPressed(gui,mouseX,mouseY);
            if ((buttonResult == 0 || buttonResult == 2) && gui.onButtonClicked(guibutton, mouseX,mouseY)) break;
            if(buttonResult > 1/*==2 or ==3*/)break;
        }
        return 0;
    }

    @Override
    public void drawFBOToScreen(Minecraft mc, int mouseX, int mouseY) {
        IAnimatableThinkerObject.drawPre(this,timer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.fbo.framebufferTexture);


        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D,  Minecraft.getMinecraft().displayHeight, 0.0D, 0.0D, 0.0D);
        tessellator.addVertexWithUV( Minecraft.getMinecraft().displayWidth,  Minecraft.getMinecraft().displayHeight, 0.0D, 1, 0.0D);
        tessellator.addVertexWithUV( Minecraft.getMinecraft().displayWidth, 0.0D, 0.0D, 1, 1);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 1);

        IAnimatableThinkerObject.draw(this,timer);
        tessellator.draw();


        IAnimatableThinkerObject.drawAfter(this,timer);
    }

    @Override
    public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
        if(!this.visible)return;
        IBoundedButton.drawPre(this);
        tickWheel();
        GL11.glTranslatef(0,YOffset,0);
        buttons.forEach(b->b.drawButton2(mc, mouseX, mouseY));
        IBoundedButton.drawAfter();
    }

    @Override
    public int getPosX() {
        return xPosition;
    }

    @Override
    public int getPoxY() {
        return yPosition;
    }

    @Override
    public int getSizeX() {
        return width;
    }

    @Override
    public int getSizeY() {
        return height;
    }
    @Override
    public void destroy() {
        buttons.forEach(IThinkerObject::destroy);
    }

    @Override
    public void handleMouseWheel(int mouseX, int mouseY, int dWheel) {
        if(!isMouseInButton(mouseX, mouseY))return;
        oldWheel+=dWheel;
    }

    @Override
    public boolean updateRenderState(long timer) {
        if(buttons.stream().anyMatch(buttonBase -> buttonBase.updateRenderState(timer)))return true;
        return super.updateRenderState(timer);
    }
}
