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
import org.lwjgl.opengl.GL11;

import java.util.concurrent.atomic.AtomicInteger;

public class Text extends ThinkerButtonBase {
    int color;
    String text;
    public Text(int id, String text, int posX, int posY){
        super(id,posX,posY,text.length(),12,"");
        this.text=text;
        this.color=-1;        needUpdate=true;
    }
    public Text(int id, String text, int posX, int posY, int color){
        super(id,posX,posY,text.length()*5,12,"");
        this.text=text;
        this.color=color;        needUpdate=true;
    }
    public void drawButton2(Minecraft mc, int mouseX, int mouseY){
        if (this.visible) {
            IAnimatableThinkerObject.drawPre(this,timer);

            GL11.glTranslatef(xPosition + (height / 2F), yPosition + (width / 2F),0);
            GuiAnimeList.forEach(anime -> anime.animeDraw(timer));
            GL11.glTranslatef(-(xPosition + (height / 2F)), -(yPosition + (width / 2F)),0);

            AtomicInteger color = new AtomicInteger(0xffffffff);
            GuiAnimeList.stream().filter(anime->anime instanceof IColorChangedAnime).map(anime-> ((IColorChangedAnime) anime)).forEach(anime-> color.set(((Math.max(4,anime.getA(timer)) & 0xFF) << 24) | //when alpha < 4, font will render as no alpha, f**k you mojang
                    ((anime.getR(timer) & 0xFF) << 16) |
                    ((anime.getG(timer) & 0xFF) << 8)  |
                    ((anime.getB(timer) & 0xFF) << 0)));

            Minecraft.getMinecraft().fontRenderer.drawString(text, xPosition, yPosition, color.get());

            //mixinHandler.cancelForgeFontColorOverride=false;
            GuiAnimeList.forEach(anime -> anime.animeDrawAfter(timer));

            IAnimatableThinkerObject.drawAfter(this,timer);
        }
    }
}
