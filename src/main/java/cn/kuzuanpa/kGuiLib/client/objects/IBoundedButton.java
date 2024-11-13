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

package cn.kuzuanpa.kGuiLib.client.objects;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public interface IBoundedButton extends IThinkerObject {
    int getPosX();
    int getPoxY();
    int getSizeX();
    int getSizeY();
    static void drawPre(IBoundedButton obj){
        float factor = Minecraft.getMinecraft().displayWidth*1F/Minecraft.getMinecraft().currentScreen.width;
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int) (obj.getPosX()*factor), (int) ((Minecraft.getMinecraft().currentScreen.height-obj.getPoxY()-obj.getSizeY())*factor), (int) (obj.getSizeX()*factor), (int) ((obj.getSizeY())*factor));
    }

    static void drawAfter(){
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
