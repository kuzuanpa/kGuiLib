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

package cn.kuzuanpa.kGuiLib.client;

import cn.kuzuanpa.kGuiLib.client.objects.IMouseWheelAccepter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

/**
 * @author kuzuanpa
 */
@SideOnly(Side.CLIENT)
public abstract class kGuiScreenContainerLayerBase extends kGuiScreenBase implements IkGuiContainerLayer{
	protected int ContainerX=0,ContainerY=0;

	@Override
	public void checkRequestedTooltipPos(int mouseX, int mouseY){
		requestedTooltipMap.values().forEach(tooltip -> tooltip.isPosCheckPassed=tooltip.posPredicate.test(new Vector2f(mouseX-ContainerX,mouseY-ContainerY)));
	}

	@Override
	public IkGuiContainerLayer setup(int width, int height, int containerStartPosX, int containerStartPosY) {
		this.width=width;
		this.height=height;
		this.ContainerX=containerStartPosX;
		this.ContainerY=containerStartPosY;
		return this;
	}

	@Override
	public void onKeyTyped(char c, int i) {
		close();
	}
}
