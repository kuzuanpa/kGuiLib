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

import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import org.lwjgl.opengl.GL11;

public class animeMoveSlowOut implements IGuiAnime {
    public animeMoveSlowOut(int startTime, int endTime, int dX, int dY, float strength){
        this.startTime=startTime;
        this.dX =dX;
        this.dY =dY;
        this.endTime=endTime;
        this.strength=strength;
    }
    public int startTime, endTime, dX, dY;
    public float strength;
    @Override
    public void animeDraw(long timer) {
        if(timer<startTime) return;
        if (timer < endTime) {
            float rate =  (float) Math.pow(((float)(timer - startTime)/(float)(endTime-startTime)),strength);
            GL11.glTranslatef(rate* dX,rate* dY, 0);
        }
        else GL11.glTranslatef(dX, dY, 0);
    }

    @Override
    public void animeDrawPre(long time) {

    }

    @Override
    public void animeDrawAfter(long time) {

    }

    @Override
    public void updateButton(long time, kGuiButtonBase button) {
        long timer = System.currentTimeMillis()- time;
        if(timer<startTime) return;
        if (timer < endTime) {
            float f1=((float)(timer - startTime)/(float)(endTime-startTime));
            button.animeXModify+= (int) (dX*f1);
            button.animeYModify+= (int) (dY*f1);
        }
        else {
            button.animeXModify +=dX ;
            button.animeYModify +=dY ;
        }
    }
    @Override
    public boolean isActive(long time) {
        return time > startTime && time <= endTime;
    }
}
