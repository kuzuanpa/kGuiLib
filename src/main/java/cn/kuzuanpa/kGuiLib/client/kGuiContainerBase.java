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

package cn.kuzuanpa.kGuiLib.client;

import cn.kuzuanpa.kGuiLib.client.objects.gui.ThinkerButtonBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author kuzuanpa
 */
@SideOnly(Side.CLIENT)
public abstract class kGuiContainerBase extends GuiContainer {

	private int displayWidth,displayHeight;

	public boolean openByUser=false;
	public long initTime=0;

	private List<String> hoveringString=new ArrayList<>();
	public List<ThinkerButtonBase> buttons= new ArrayList<>();

	public kGuiContainerBase(Container container) {
        super(container);

        openByUser=true;
		allowUserInput = false;
	}

	public void onOpenByUserAfter(){
		initTime=System.currentTimeMillis();
		openByUser=false;
	}

	public void initGui() {
		super.initGui();

		displayWidth= FMLClientHandler.instance().getClient().currentScreen.width;
		displayHeight= FMLClientHandler.instance().getClient().currentScreen.height;
		buttons.clear();

		addButtons();
		if(!buttonList.isEmpty()) FMLLog.log(Level.ERROR, this.toString()+": raw buttonList should not be used!");

		if(openByUser) onOpenByUserAfter();
	}

	public abstract void addButtons();

	public String l10n(String key){String text1= LanguageRegistry.instance().getStringLocalization(key);return text1.isEmpty() ? key: text1;}

	protected void keyTyped(char p_73869_1_, int p_73869_2_)
	{
		if (p_73869_2_ == Keyboard.KEY_ESCAPE) close();
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int mouseButton)
	{
        for (int l = this.buttonList.size() - 1; l >= 0 ;l--)
        {
        	ThinkerButtonBase guibutton = (ThinkerButtonBase)this.buttonList.get(l);
        	if(guibutton.isMouseInButton(p_73864_1_, p_73864_2_))
        	{
        		GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
        		if (MinecraftForge.EVENT_BUS.post(event))
        			break;
        		if(event.button.id!=0)event.button.func_146113_a(this.mc.getSoundHandler());
        		if (this.onButtonPressed(event.button)) break;
        		if (this.equals(this.mc.currentScreen))
        			MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
        	}
        }
	}

	protected boolean onButtonPressed(GuiButton button) {

		return true;
	}
	public void handleMouseInput(){
		super.handleMouseInput();
		int x = Mouse.getX() * this.width / this.mc.displayWidth;
		int y =this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;
		//if(((ThinkerButtonBase)buttonList.get(3)).visible&&Mouse.isInsideWindow()&&Mouse.getEventDWheel()!=0&& x< profileHandler.profileLayer*32+32&& x>0) profileHandler.handleMouseWheel();
	}
	public long getTimer(){
		return System.currentTimeMillis()-initTime;
	}
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_){
		this.buttons.forEach(b -> b.updateTimer(getTimer()));

		super.drawScreen(p_73863_1_,p_73863_2_,p_73863_3_);

		//profileHandler.tick();

		if(!Mouse.isInsideWindow())return;
		int x = Mouse.getX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getY() * this.height / this.mc.displayHeight - 1;
		buttons.forEach(button -> {
			if(!button.visible)return;
			if(button.isMouseInButton(x,y))hoveringString= Collections.singletonList(button.displayString);
		});
		if (hoveringString == null||hoveringString.stream().allMatch(String::isEmpty)) return;
		drawHoveringText(hoveringString, x, y+5, fontRendererObj);
	}

	public boolean close() {
		//profileHandler.oldWheel=0;
		this.mc.displayGuiScreen(null);
		this.mc.setIngameFocus();
		return true;
	}
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
