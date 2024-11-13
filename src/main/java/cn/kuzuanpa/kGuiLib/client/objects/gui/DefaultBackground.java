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

import cn.kuzuanpa.kGuiLib.client.anime.IColorChangedAnime;
import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableThinkerObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.atomic.AtomicInteger;

public class DefaultBackground extends ThinkerButtonBase {
    int color;
    String text;
    public DefaultBackground(int id){
        super(id,0,0,Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight,"");
        this.color=-1;
        needUpdate=true;
    }
    public void drawButton2(Minecraft mc, int mouseX, int mouseY){
        if (this.visible) {
            IAnimatableThinkerObject.drawPre(this,timer);

            GL11.glTranslatef(xPosition + (height / 2F), yPosition + (width / 2F),0);
            GuiAnimeList.forEach(anime -> anime.animeDraw(timer));
            GL11.glTranslatef(-(xPosition + (height / 2F)), -(yPosition + (width / 2F)),0);

            //why (256F/height)? idk, i just rendered some picture and guessed this argument.
            float var7 = 0.00390625F*(256F/height)*(height*1F/width);
            float var8 = 0.00390625F*(256F/height);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0, 0 + height, zLevel, 0, height * var8);
            tessellator.addVertexWithUV(0 + width, 0 + height, zLevel,  width * var7, height * var8);
            tessellator.addVertexWithUV(0 + width, 0, zLevel, width * var7, 0);
            tessellator.addVertexWithUV(0, 0, zLevel, 0, 0);
            GL11.glTranslatef(xPosition, yPosition ,0);
            GL11.glTranslatef(-(xPosition ), -(yPosition ),0);
            GL11.glColor4f(0.5F,0.5F,1F,0.5F);
            tessellator.draw();
            GL11.glEnable(GL11.GL_BLEND);
            //mixinHandler.cancelForgeFontColorOverride=false;
            GuiAnimeList.forEach(anime -> anime.animeDrawAfter(timer));

            IAnimatableThinkerObject.drawAfter(this,timer);
        }
    }
}
