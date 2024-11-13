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

package cn.kuzuanpa.kGuiLib.client.handler;

public class mixinHandler {
    public static int color=0xbbffffff;
    public static boolean isColorOverwrite=false;
    public static void setColor(int color){
        mixinHandler.color=color;
    }
    public static void setColor(int color,boolean overwrite){
        mixinHandler.color=color;
        isColorOverwrite=overwrite;
    }

    public static boolean cancelForgeFontColorOverride=false;
}
