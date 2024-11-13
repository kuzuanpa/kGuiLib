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
package cn.kuzuanpa.kGuiLib.client.anime;

import cn.kuzuanpa.kGuiLib.client.objects.gui.ThinkerButtonBase;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

public class animeScaleQuad implements IGuiAnime {
    public animeScaleQuad(int startTime, int endTime, float scaleRate, float scaleX, float scaleY, float strength){
        this.startTime=startTime;
        this.endTime=endTime;
        this.scaleRate=scaleRate;
        this.scaleX=scaleX;
        this.scaleY=scaleY;
        this.strength=strength;
    }
    public int startTime, endTime;
    public float scaleRate,scaleX,scaleY, strength;
    @Override
    public void animeDraw(long timer) {
        if(timer<startTime) return;
        float rate = 1- (float) Math.pow(1-((float)(timer - startTime)/(float)(endTime-startTime)),strength);
        if(timer<endTime) GL11.glScalef(rate*scaleX*scaleRate,rate*scaleY*scaleRate,1);
        else GL11.glScalef(scaleX*scaleRate,scaleY*scaleRate,1);
    }

    @Override
    public void animeDrawPre(long time) {}

    @Override
    public void animeDrawAfter(long time) {}
    @Override
    public void updateButton(long timer, ThinkerButtonBase button) {
    }

    @Override
    public boolean isActive(long time) {
        return time > startTime && time <= endTime;
    }
}
