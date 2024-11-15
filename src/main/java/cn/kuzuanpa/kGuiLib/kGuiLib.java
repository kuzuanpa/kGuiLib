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

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;

@Mod(modid = kGuiLib.MOD_ID, version = kGuiLib.VERSION, dependencies = "required-after:CodeChickenCore@[1.0.7,)")
public class kGuiLib
{
    public static final String MOD_ID = "kguilib";
    public static final String MOD_NAME = "kGuiLib";
    public static final String VERSION = "0.0.1";

    public static boolean isServerSide=false,enableDevGUI=true;
    @SidedProxy(clientSide = "cn.kuzuanpa.kGuiLib.clientProxy",
            serverSide = "cn.kuzuanpa.kGuiLib.commonProxy")
    public static commonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        isServerSide=MinecraftServer.getServer() instanceof DedicatedServer;
        if(isServerSide){
            FMLLog.log(Level.ERROR,"kGuiLib is an CLIENT only mod, and will disable entirely in Server!");
            return;
        }
        if(!enableDevGUI)return;//Runtime kGuiLib actually have nothing to register, This just for me to open a GUI quickly to develop kGuiLib

        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if(isServerSide)return;
        PROXY.init(event);
    }

    public static void err(Throwable err){
        err.printStackTrace();
    }
    public static void err(String err){
        System.err.println(err);
    }
    public static void log(String log){
        System.out.println(log);
    }
    public static int getInt(Object str){
        return Integer.parseInt((String) str);
    }
    public static long getLong(Object str){
        return Long.parseLong((String) str);
    }
    public static double getDouble(Object str){
        return Double.parseDouble((String) str);
    }
    public static float getFloat(Object str){
        return Float.parseFloat((String) str);
    }
    public static boolean getBoolean(Object str){return Boolean.parseBoolean((String) str);}
}
