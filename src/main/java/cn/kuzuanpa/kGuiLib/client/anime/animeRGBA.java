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

import cn.kuzuanpa.kGuiLib.kGuiLib;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;
import org.lwjgl.opengl.GL11;

public class animeRGBA implements IGuiAnime, IColorChangedAnime{
    public animeRGBA(int startTime, int endTime,int startR,int startG,int startB,int startA,int dR,int dG,int dB,int dA){
        this.startTime=startTime;
        this.endTime=endTime;
        this.startR=startR;
        this.startG=startG;
        this.startB=startB;
        this.startA=startA;
        this.dR=dR;
        this.dG=dG;
        this.dB=dB;
        this.dA=dA;
    }
    public int startTime, endTime,startR, startG, startB, startA,dR,dG,dB,dA;
    @Override
    public void animeDraw(long timer) {
        if((startR+dR)>255||(startG+dG)>255||(startB+dB)>255||(startA+dA)>255) kGuiLib.err(new IllegalArgumentException("RGBA value is too big: dR:"+dR+",dG:"+dG+",dB:"+dB+",dA:"+dA));
        if(timer<startTime) return;
        if(timer<endTime){
            float f1=((float)(timer - startTime)/(float)(endTime-startTime));
            GL11.glColor4ub((byte) (startR+(f1*dR)), (byte) (startG+(f1*dG)), (byte) (startB+(f1*dB)), (byte) (startA+(f1*dA)));
        }if(timer>=endTime){
            GL11.glColor4ub((byte) (startR+dR), (byte) (startG+dG), (byte) (startB+dB), (byte) (startA+dA));
        }
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
    public int getR(long timer) {
        if(timer<startTime) return startR;
        if(timer<endTime)return (int) (startR + ((float)(timer - startTime)/(float)(endTime-startTime))*dR);
        if(timer>=endTime)return (startR+dR);
        return startR;
    }

    @Override
    public int getG(long timer) {
        if(timer<startTime) return startG;
        if(timer<endTime)return (int) (startG + ((float)(timer - startTime)/(float)(endTime-startTime))*dG);
        if(timer>=endTime)return (startG+dG);
        return startG;
    }

    @Override
    public int getB(long timer) {
        if(timer<startTime) return startB;
        if(timer<endTime)return (int) (startB + ((float)(timer - startTime)/(float)(endTime-startTime))*dB);
        if(timer>=endTime)return (startB+dB);
        return startB;
    }

    @Override
    public int getA(long timer) {
        if(timer<startTime) return startA;
        if(timer<endTime)return (int) (startA + ((float)(timer - startTime)/(float)(endTime-startTime))*dA);
        if(timer>=endTime)return (startA+dA);
        return startA;
    }
    @Override
    public boolean isActive(long time) {
        return time > startTime && time <= endTime;
    }
}
