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

public class animeMoveCustom implements IGuiAnime {
    public animeMoveCustom(int startTime, int endTime, String XFormula,String YFormula){
        this.startTime=startTime;
        this.endTime=endTime;
        this.XFormula=XFormula;
        this.YFormula=YFormula;
    }
    public int startTime, endTime;
    public String XFormula,YFormula;
    @Override
    public void animeDraw(long timer) {
        if(timer<startTime) return;
        if (timer < endTime) GL11.glTranslatef(Integer.getInteger(XFormula, (int) timer),Integer.getInteger(YFormula, (int) timer),0);
        else GL11.glTranslatef(Integer.getInteger(XFormula,endTime),Integer.getInteger(YFormula,  endTime),0);
    }

    @Override
    public void animeDrawPre(long time){}

    @Override
    public void animeDrawAfter(long time) {}
    @Override
    public void updateButton(long time, ThinkerButtonBase button) {
    }
    @Override
    public boolean isActive(long time) {
        return time > startTime && time <= endTime;
    }
}
