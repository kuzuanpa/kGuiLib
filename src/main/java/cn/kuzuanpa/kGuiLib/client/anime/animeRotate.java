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

public class animeRotate implements IGuiAnime {
    public animeRotate(int startTime,int endTime,int rotateAngle){
        this.startTime=startTime;
        this.rotateAngle=rotateAngle;
        this.endTime=endTime;
    }
    public int startTime, endTime, rotateAngle;
    @Override
    public void animeDraw(long timer) {
        if(timer<startTime) return;
        if(timer<endTime)GL11.glRotatef(((float)(timer - startTime)/(float)(endTime-startTime))*rotateAngle,0,0,1);
        else GL11.glRotatef(rotateAngle,0,0,1);
    }

    @Override
    public void animeDrawPre(long time) {

    }

    @Override
    public void animeDrawAfter(long time) {

    }
    @Override
    public void updateButton(long time, kGuiButtonBase button) {

    }
    @Override
    public boolean isActive(long time) {
        return time > startTime && time <= endTime;
    }
}
