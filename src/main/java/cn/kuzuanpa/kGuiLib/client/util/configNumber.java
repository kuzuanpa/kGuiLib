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

public class configNumber {
    private final float minValue;
    private final float maxValue;
    private float value;
    public boolean isInteger = false;
    public String desc;
    public String forgeConfigCategory = "main";
    public String name;

    public configNumber(int minValue, int value, int maxValue, String name) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.name = name;
        desc = "";
        isInteger = true;
    }

    public configNumber(int minValue, int value, int maxValue, String name, String desc) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.name = name;
        this.desc = desc;
        isInteger = true;
    }

    public configNumber(int minValue, int value, int maxValue, String name, String category, String desc) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.name = name;
        this.forgeConfigCategory = category;
        this.desc = desc;
        isInteger = true;
    }

    //float
    public configNumber(float minValue, float value, float maxValue, String name) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.name = name;
        this.desc = "";
    }

    public configNumber(float minValue, float value, float maxValue, String name, String desc) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.name = name;
        this.desc = desc;
    }

    public configNumber(float minValue, float value, float maxValue, String name, String category, String desc) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.name = name;
        this.forgeConfigCategory = category;
        this.desc = desc;
    }

    public configNumber setInteger(boolean isInteger) {
        this.isInteger = isInteger;
        return this;
    }

    public float get() {
        return isInteger ? getI() : value;
    }

    public int getI() {
        return (int) value;
    }

    public float min() {
        return isInteger ? minI() : minValue;
    }

    public int minI() {
        return (int) Math.ceil(minValue);
    }

    public float max() {
        return isInteger ? maxI() : maxValue;
    }

    public int maxI() {
        return (int) Math.floor(maxValue);
    }

    public void update(float newValue) {
        if (min() > newValue) newValue = min();
        else if (max() < newValue) newValue = max();
        this.value = newValue;
    }
}
