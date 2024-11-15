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

package cn.kuzuanpa.kGuiLib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;

public interface IkGui {
    void initGui();
    void addButtons();
    void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_);
    void releaseResources();
    boolean close();

    void onKeyTyped(char key, int keyCode);
    void onMouseClicked(int mouseX, int mouseY, int mouseButton);
    boolean onButtonPressed(GuiButton button, int mouseX, int mouseY);
    void handleMouseInput();

    IkGui setMC(Minecraft mc);
    IkGui setFontRenderer(FontRenderer fontRenderer);
    IkGui setParentGui(IkGui gui);
    IkGui setChildGui(IkGui gui);
}
