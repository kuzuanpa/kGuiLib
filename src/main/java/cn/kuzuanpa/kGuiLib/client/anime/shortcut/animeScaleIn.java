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
package cn.kuzuanpa.kGuiLib.client.anime.shortcut;

import cn.kuzuanpa.kGuiLib.client.anime.IGuiAnime;
import cn.kuzuanpa.kGuiLib.client.anime.animeScaleQuad;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class animeScaleIn extends animeScaleQuad implements IGuiAnime {
    /**@param anchorX Screen width relative, usually 0.0F~1.0F
     * @param anchorY Screen height relative, usually 0.0F~1.0F**/
    public animeScaleIn(int length,float anchorX, float anchorY) {
        super(0, length, 10F, 3, 1, 1);
        this.anchorX=anchorX;
        this.anchorY=anchorY;
    }
    float anchorX,anchorY;

    @Override
    public void animeDraw(long timer) {
        GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
        GL11.glTranslatef(currentScreen.width * anchorX,-currentScreen.height * -anchorY,0);

        GL11.glScalef(0.1F,0.1F,1);
        super.animeDraw(timer);

        GL11.glTranslatef(currentScreen.width * -anchorX,currentScreen.height * -anchorY,0);
    }
}
