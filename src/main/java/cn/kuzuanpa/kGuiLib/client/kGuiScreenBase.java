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

import cn.kuzuanpa.kGuiLib.client.objects.IMouseWheelAccepter;
import cn.kuzuanpa.kGuiLib.client.objects.gui.*;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Level;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author kuzuanpa
 */
@SideOnly(Side.CLIENT)
public abstract class kGuiScreenBase extends GuiScreen implements IkGui{
	HashMap<Integer,requestedTooltip> requestedTooltipMap = new HashMap<>();
	protected GuiButton selectedButton;

	protected static class requestedTooltip {
		protected requestedTooltip(List<String> strings, Predicate<Vector2f> posPredicate, boolean additionalCondition){
			this.strings=strings;
			this.posPredicate=posPredicate;
			this.additionalCondition=additionalCondition;
		}
		protected List<String> strings;
		protected Predicate<Vector2f> posPredicate;
		public boolean isPosCheckPassed = false;
		protected boolean additionalCondition;
	}

	public void addTooltip(int id, String[] strings, Predicate<Vector2f> MousePositionPredicate, boolean additionalCondition){
		requestedTooltipMap.put(id, new requestedTooltip(Arrays.asList(strings),MousePositionPredicate,additionalCondition));
	}

	public void updateTooltip(int id, boolean additionalCondition){
		if(requestedTooltipMap.get(id)!=null)requestedTooltipMap.get(id).additionalCondition=additionalCondition;
	}

	public void addOrUpdateTooltip(int id, String[] strings,Predicate<Vector2f> MousePositionPredicate){
		if(requestedTooltipMap.get(id)!=null)requestedTooltipMap.get(id).strings= Arrays.asList(strings);
		else addTooltip(id,strings,MousePositionPredicate,true);
	}
	public void addOrUpdateTooltip(int id, String[] strings,Predicate<Vector2f> MousePositionPredicate,boolean additionalCondition){
		if(requestedTooltipMap.get(id)!=null){
			requestedTooltipMap.get(id).strings= Arrays.asList(strings);
			requestedTooltipMap.get(id).additionalCondition=additionalCondition;
		}
		else addTooltip(id,strings,MousePositionPredicate,additionalCondition);
	}

	public void onNoButtonPressed() {};

	private int displayWidth,displayHeight;

	public boolean openByUser=false;
	public long initTime=0;

	private List<String> hoveringString=new ArrayList<>();
	public List<kGuiButtonBase> buttons= new ArrayList<>();

	public kGuiScreenBase() {
		openByUser=true;
		allowUserInput = false;

	}

	public void onOpenByUserAfter(){
		initTime=System.currentTimeMillis();
		openByUser=false;
	}

	public IkGui parentGui = null;
	public IkGui  childGui = null;

	@Override
	public void initGui() {
		initGui2();
	}

	public void initGui2() {
		super.initGui();
		displayWidth= FMLClientHandler.instance().getClient().currentScreen.width;
		displayHeight= FMLClientHandler.instance().getClient().currentScreen.height;

		buttons.forEach(kGuiButtonBase::destroy);

		buttonList.clear();
		buttons.clear();

		addButtons();
		if(!buttonList.isEmpty()) FMLLog.log(Level.WARN, this.toString()+": raw buttonList should not be used!");

		buttonList.addAll(buttons);

		if(childGui != null) childGui.initGui2();

		if(openByUser) onOpenByUserAfter();
	}
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		drawScreen2(p_73863_1_, p_73863_2_, p_73863_3_);
	}
	public void drawScreen2(int p_73863_1_, int p_73863_2_, float p_73863_3_){
		this.buttons.forEach(b -> b.updateTimer(getTimer()));

		//draw buttons
		super.drawScreen(p_73863_1_,p_73863_2_,p_73863_3_);

		if(childGui != null) childGui.drawScreen2(p_73863_1_,p_73863_2_,p_73863_3_);

		if(!Mouse.isInsideWindow()) return;

		int x = Mouse.getX() * mc.currentScreen.width / this.mc.displayWidth;
		int y = mc.currentScreen.height - Mouse.getY() * mc.currentScreen.height / this.mc.displayHeight - 1;

		drawButtonTooltips(x,y);
		drawRequestedTooltips(x,y);
	}
	public void drawButtonTooltips(int mouseX, int mouseY){
		GL11.glPushMatrix();
		//Draw button tooltips
		buttons.forEach(button -> {
			if (!button.visible) return;
			if (button.isMouseInButton(mouseX, mouseY)) hoveringString = Collections.singletonList(button.displayString);
		});

		if (hoveringString != null && hoveringString.stream().noneMatch(String::isEmpty)) drawHoveringText(hoveringString, mouseX, mouseY + 5, fontRendererObj);


		GL11.glPopMatrix();
	}

	public void drawRequestedTooltips(int mouseX, int mouseY){
		requestedTooltipMap.values().forEach(tooltip -> {
			if (tooltip != null && tooltip.additionalCondition && tooltip.isPosCheckPassed) drawHoveringText(tooltip.strings, mouseX, mouseY, fontRendererObj);
		});
	}

	public boolean close() {
		//profileHandler.oldWheel=0;
		if(parentGui !=null) {
			parentGui.setChildGui(null);
			releaseResources();
			return false;
		}
		if(childGui !=null) childGui.close();
		releaseResources();
		this.mc.displayGuiScreen(null);
		this.mc.setIngameFocus();
		return true;
	}
	@Override
	public void releaseResources() {
		buttons.forEach(kGuiButtonBase::destroy);
		if(childGui!=null)childGui.releaseResources();
	}
	public String l10n(String key){String text1= LanguageRegistry.instance().getStringLocalization(key);return text1.isEmpty() ? key: text1;}

	protected void keyTyped(char p_73869_1_, int p_73869_2_)
	{
		if(childGui!=null)childGui.onKeyTyped(p_73869_1_,p_73869_2_);
		else onKeyTyped(p_73869_1_,p_73869_2_);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		if(childGui!=null)childGui.onMouseClicked(mouseX,mouseY,mouseButton);
		onMouseClicked( mouseX,  mouseY,  mouseButton);
	}

	@Override
	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		boolean hasButtonPressed = false;
		for (int l = this.buttonList.size() - 1; l >= 0 ;l--)
		{
			kGuiButtonBase guibutton = (kGuiButtonBase)this.buttonList.get(l);
			if(!guibutton.isMouseInButton(mouseX, mouseY))continue;
			GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);

			if (MinecraftForge.EVENT_BUS.post(event)) break;
			guibutton.func_146113_a(this.mc.getSoundHandler());

			byte buttonResult = guibutton.onPressed(this,mouseX,mouseY);
			if(buttonResult > 0)hasButtonPressed = true;
			if ((buttonResult == 0 || buttonResult == 2) && this.onButtonPressed(guibutton, mouseX,mouseY)) break;
			if(buttonResult > 1/*==2 or ==3*/)break;

			if (this.equals(this.mc.currentScreen)) MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(this, guibutton, this.buttonList));
		}
		if(!hasButtonPressed)onNoButtonPressed();
	}

	/**@return will break buttons search, useful in multi button in one pos**/
	@Override
	public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {
		return false;
	}

	@Override
	public void handleMouseInput() {
		int mouseX = Mouse.getX() * mc.currentScreen.width / this.mc.displayWidth;
		int mouseY = mc.currentScreen.height - Mouse.getY() * mc.currentScreen.height / this.mc.displayHeight - 1;
		handleMouseInput2(mouseX, mouseY);
	}

	public void handleMouseInput2(int mouseX, int mouseY){
		super.handleMouseInput();
		if(childGui!=null)childGui.handleMouseInput2(mouseX, mouseY);

		checkRequestedTooltipPos(mouseX,mouseY);
		if(Mouse.isInsideWindow()&&Mouse.getEventDWheel()!=0)buttons.stream().filter(buttonBase -> buttonBase instanceof IMouseWheelAccepter).map(button-> ((IMouseWheelAccepter) button)).forEach(button->button.handleMouseWheel(mouseX,mouseY, Mouse.getEventDWheel()));

	}
	public void checkRequestedTooltipPos(int mouseX, int mouseY){
		requestedTooltipMap.values().forEach(tooltip -> tooltip.isPosCheckPassed=tooltip.posPredicate.test(new Vector2f(mouseX,mouseY)));
	}
	public long getTimer(){
		return System.currentTimeMillis()-initTime;
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public IkGui setMC(Minecraft mc) {
		this.mc=mc;
		return this;
	}

	@Override
	public IkGui setFontRenderer(FontRenderer fontRenderer) {
		this.fontRendererObj=fontRenderer;
		return this;
	}
	@Override
	public IkGui setParentGui(IkGui parentGui) {
		this.parentGui = parentGui;
		return this;
	}
	@Override
	public IkGui setChildGui(IkGui childGui) {
		this.childGui = childGui;
		return this;
	}

	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		releaseResources();
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
	}
}
