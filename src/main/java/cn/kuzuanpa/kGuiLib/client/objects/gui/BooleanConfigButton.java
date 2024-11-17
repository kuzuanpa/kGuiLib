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
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the Thinker Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/Thinker
 *
 * Thinker is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.kGuiLib.client.objects.gui;

import cn.kuzuanpa.kGuiLib.client.IkGui;
import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableButton;
import cn.kuzuanpa.kGuiLib.client.util.configBoolean;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

public class BooleanConfigButton extends ThinkerButtonBase {
    public BooleanConfigButton(int id, int xPos, int yPos, int width, int height, String displayText, configBoolean config) {
        super(id, xPos, yPos,width,height,displayText);
        this.config=config;
        this.originalY=yPos;
    }
    public configBoolean config;
    public int originalY;
    public void drawButton2(Minecraft p_146112_1_, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            if(!isAnimatedInFBO)IAnimatableButton.drawPre(this,timer);

            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if(!isAnimatedInFBO) IAnimatableButton.draw(this, timer);

            drawRect(xPosition+2,yPosition+height/2,xPosition+width-2,yPosition+(height/2)+1,config.get()?0xff99ffcc:0x99ff99cc);

            this.drawString(fontrenderer, this.displayString, this.xPosition, this.yPosition, -1);

            if(!isAnimatedInFBO) IAnimatableButton.drawAfter(this, timer);
        }
    }

    @Override
    public byte onPressed(IkGui gui, int posX, int posY) {
        config.set(!config.get());
        return 2;
    }
}
