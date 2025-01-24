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

import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.objects.gui.kGuiButtonBase;

public class animeMoveFadeIn extends animeFadeIn {
    static final int dX = 20;
    public animeMoveFadeIn(int length){
        super(length);
        subAnimeMove = new animeMoveSlowIn(0,length,-dX,0, 2);
    }

    final animeMoveSlowIn subAnimeMove;
    final animeMoveLinear subAnimePreMove = new animeMoveLinear(-1,0,dX,0);

    @Override
    public void animeDraw(long time) {
        super.animeDraw(time);
        subAnimePreMove.animeDraw(time);
        subAnimeMove.animeDraw(time);
    }

    @Override
    public void animeDrawPre(long time) {
        super.animeDrawPre(time);
        subAnimePreMove.animeDrawPre(time);
        subAnimeMove.animeDrawPre(time);
    }

    @Override
    public void animeDrawAfter(long time) {
        super.animeDrawAfter(time);
        subAnimePreMove.animeDrawAfter(time);
        subAnimeMove.animeDrawAfter(time);

    }

    @Override
    public void updateButton(long time, kGuiButtonBase button) {
        super.updateButton(time, button);
        subAnimePreMove.updateButton(time,button);
        subAnimeMove.updateButton(time, button);
    }
}
