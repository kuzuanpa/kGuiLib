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

package cn.kuzuanpa.kGuiLib.client.anime.shortcut;

import cn.kuzuanpa.kGuiLib.client.anime.IGuiAnime;
import cn.kuzuanpa.kGuiLib.client.anime.animeRGBA;

public class animeTransparency extends animeRGBA implements IGuiAnime {
    public animeTransparency(int startTime, int endTime, int startA, int dA){
        super(startTime,endTime,255,255,255,startA,0,0,0,dA);
    }
}
