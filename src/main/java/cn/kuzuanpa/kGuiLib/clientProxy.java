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
package cn.kuzuanpa.kGuiLib;

import cn.kuzuanpa.kGuiLib.client.handler.keyBindHandler;
import cn.kuzuanpa.kGuiLib.client.kGuiScreenDev;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class clientProxy extends commonProxy {

    public void init(FMLInitializationEvent event) {
        super.init(event);
        if(kGuiLib.enableDevGUI) {
            ClientRegistry.registerKeyBinding(keyBindHandler.keyThink);
            FMLCommonHandler.instance().bus().register(new keyBindHandler());
        }
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch(id) {
            case 0: return new kGuiScreenDev();
        }
        return null;
    }

}