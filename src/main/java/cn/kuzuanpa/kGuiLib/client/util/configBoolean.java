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

package cn.kuzuanpa.kGuiLib.client.util;

public class configBoolean {
    private boolean value;
    public String forgeConfigCategory = "main";
    public String forgeConfigComment = "";
    public String name;

    public configBoolean(boolean value, String name) {
        this.value = value;
        this.name = name;
    }

    public configBoolean(boolean value, String name, String forgeConfigCategory) {
        this.value = value;
        this.name = name;
        this.forgeConfigCategory = forgeConfigCategory;
    }

    public configBoolean(boolean value, String name, String forgeConfigCategory, String forgeConfigComment) {
        this.value = value;
        this.name = name;
        this.forgeConfigCategory = forgeConfigCategory;
        this.forgeConfigComment = forgeConfigComment;
    }

    public boolean get() {
        return value;
    }

    public void set(boolean value) {
        this.value = value;
    }
}
