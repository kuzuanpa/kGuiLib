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
package cn.kuzuanpa.kGuiLib.client.objects;

import cn.kuzuanpa.kGuiLib.kGuiLib;
import cn.kuzuanpa.kGuiLib.client.anime.IGuiAnime;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public interface IAnimatableButton extends IkGuiButton {
    ArrayList<IGuiAnime> getGuiAnimeList();

    default IAnimatableButton addAnimes(List<IGuiAnime> guiAnimes){
        if(this.getGuiAnimeList()==null&&!guiAnimes.isEmpty()) kGuiLib.err("Object "+this.toString()+ "don't support GUIAnime!");
        else this.getGuiAnimeList().addAll(guiAnimes);
        return this;
    }
    static void drawPre(IAnimatableButton obj, long timer){
        GL11.glPushMatrix();
        obj.getGuiAnimeList().forEach(anime -> anime.animeDrawPre(timer));
    }
    static void draw(IAnimatableButton obj, long timer){
        obj.getGuiAnimeList().forEach(anime -> anime.animeDraw(timer));
    }
    static void drawAfter(IAnimatableButton obj, long timer){
        obj.getGuiAnimeList().forEach(anime -> anime.animeDrawPre(timer));
        GL11.glPopMatrix();
    }
    static boolean isAnimeActive(IAnimatableButton obj, long timer){
        return obj.getGuiAnimeList().stream().anyMatch(a->a.isActive(timer));
    }
}
