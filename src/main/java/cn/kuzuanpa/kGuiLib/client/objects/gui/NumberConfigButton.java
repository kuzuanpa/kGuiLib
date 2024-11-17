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

import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableButton;
import cn.kuzuanpa.kGuiLib.client.util.configNumber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.text.NumberFormat;

public class NumberConfigButton extends ThinkerButtonBase {
    public NumberConfigButton(int id, int xPos, int yPos, int width, int height,String displayText, configNumber config) {
        super(id, xPos, yPos,width,height,displayText);
        this.config=config;
        this.originalY=yPos;
    }
    public configNumber config;
    public int originalY;
    public void drawButton2(Minecraft p_146112_1_, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if(!isAnimatedInFBO)IAnimatableButton.drawPre(this,timer);

            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean isMouseHovering=this.isMouseInButton(mouseX,mouseY);
            int k = this.getHoverState(isMouseHovering);

            if(!isAnimatedInFBO)IAnimatableButton.draw(this,timer);

            float progress=config.get()/(config.max()-config.min());
            drawRect(xPosition+2,yPosition+height/2,xPosition+width-2,yPosition+(height/2)+1,0xcccccccc);
            drawRect((int) (xPosition+2+(width-4)*progress)-1,yPosition+10, (int) (xPosition+2+(width-4)*progress)+1,yPosition+height-10,-1);
            if(k==2&& Mouse.isButtonDown(0)) config.update(mouseX<xPosition+2?config.min():mouseX>xPosition+width-2?config.max():(config.max()-config.min())*(mouseX-2-xPosition)/(width-4));
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(2);
            this.drawString(fontrenderer, this.displayString, this.xPosition, this.yPosition, -1);
            this.drawString(fontrenderer, nf.format(config.min()), this.xPosition, this.yPosition+height-10, -1);
            this.drawCenteredString(fontrenderer, nf.format(config.get()), this.xPosition+width/2, this.yPosition+height-10, -1);
            this.drawCenteredString(fontrenderer, nf.format(config.max()), this.xPosition+width, this.yPosition+height-10, -1);

            if(!isAnimatedInFBO) IAnimatableButton.drawAfter(this,timer);
        }
    }
}
