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
import org.lwjgl.opengl.GL11;

import java.util.Map;

import static cn.kuzuanpa.kGuiLib.kGuiLib.getInt;

public class animeMoveLinear implements IGuiAnime {
    public animeMoveLinear(int startTime, int endTime, int dX, int dY){
        this.startTime=startTime;
        this.dX =dX;
        this.dY =dY;
        this.endTime=endTime;
    }
    public int startTime, endTime, dX, dY;
    @Override
    public void animeDraw(long timer) {
        if(timer<startTime) return;
        if (timer < endTime) GL11.glTranslatef(((float)(timer - startTime)/(float)(endTime-startTime))* dX,((float)(timer - startTime)/(float)(endTime-startTime))* dY, 0);
        else GL11.glTranslatef(dX, dY, 0);
    }

    @Override
    public void animeDrawPre(long time) {

    }

    @Override
    public void animeDrawAfter(long time) {

    }

    @Override
    public void updateButton(long time, ThinkerButtonBase button) {
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
