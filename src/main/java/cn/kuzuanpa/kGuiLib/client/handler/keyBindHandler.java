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

import cn.kuzuanpa.kGuiLib.client.kGuiScreenDev;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import static cn.kuzuanpa.kGuiLib.kGuiLib.MOD_NAME;

public class keyBindHandler {
    public static KeyBinding keyThink = new KeyBinding("key.Think", Keyboard.KEY_H, MOD_NAME);
    private boolean ThinkKeyPressed;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (keyThink.isPressed()) {
            FMLClientHandler.instance().getClient().displayGuiScreen(new kGuiScreenDev());
        }
    }
}

