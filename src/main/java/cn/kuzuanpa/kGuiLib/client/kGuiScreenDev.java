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

import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeFadeIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeMoveFadeIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeScaleIn;
import cn.kuzuanpa.kGuiLib.client.objects.gui.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


/**
 * @author kuzuanpa
 */
@SideOnly(Side.CLIENT)
public class kGuiScreenDev extends kGuiScreenBase {

	@Override
	public void addButtons() {
		buttons.add(new Text(1,"ideas/resources/tutorial/machineTooltip.png",10,130).addAnime(new animeScaleIn(1000,0.5F,0.5F)));

		buttons.add(new Image(3,"ideas/resources/tutorial/machineTooltip.png",10,30,240,40).addAnime(new animeScaleIn(1000,0.5F,0.5F)));

		addOrUpdateTooltip(0,new String[]{"Hello World!"}, v->v.x>0&&v.x<20);
	}

	@Override
	public void onKeyTyped(char key, int keyCode) {
		close();
	}
}
