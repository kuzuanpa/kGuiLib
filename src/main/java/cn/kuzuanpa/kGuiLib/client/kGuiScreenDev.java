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

		buttons.add(new Image(0,"ideas/resources/tutorial/machineTooltip.png",80,0,240,40).addAnime(new animeMoveSlowIn(000,500,-40,0,2)));
		buttons.add(new Image(1,"ideas/resources/tutorial/machineTooltip.png",120,40,240,40).addAnime(new animeMoveSlowIn(000,1000,-80,0,2)));
		buttons.add(new Image(2,"ideas/resources/tutorial/machineTooltip.png",160,80,240,40).addAnime(new animeMoveSlowIn(000,1500,-120,0,2)));
		buttons.add(new Image(3,"ideas/resources/tutorial/machineTooltip.png",200,120,240,40).addAnime(new animeMoveSlowIn(000,2000,-160,0,2)));

		addOrUpdateTooltip(0,new String[]{"Hello World!"}, v->v.x>0&&v.x<20);
	}
}
