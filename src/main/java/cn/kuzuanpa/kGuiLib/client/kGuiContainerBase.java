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

import cn.kuzuanpa.kGuiLib.client.objects.IMouseWheelAccepter;
import cn.kuzuanpa.kGuiLib.client.objects.gui.ThinkerButtonBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
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
public abstract class kGuiContainerBase extends GuiContainer implements IkGui{
	HashMap<Integer, kGuiScreenBase.requestedTooltip> requestedTooltipMap = new HashMap<>();
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
		requestedTooltipMap.put(id, new kGuiScreenBase.requestedTooltip(Arrays.asList(strings),MousePositionPredicate,additionalCondition));
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

		buttons.forEach(ThinkerButtonBase::destroy);

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
		GL11.glEnable(GL11.GL_BLEND);
		this.buttons.forEach(b -> b.updateTimer(getTimer()));

		//draw buttons
		super.drawScreen(p_73863_1_,p_73863_2_,p_73863_3_);

		if(childGui != null) childGui.drawScreen2(p_73863_1_,p_73863_2_,p_73863_3_);

		drawTooltips();
	}

	public void drawTooltips(){
		if(!Mouse.isInsideWindow()||childGui!=null) return;
		int x = Mouse.getX() * this.width / this.mc.displayWidth;
		int y = mc.currentScreen.height - Mouse.getY() * mc.currentScreen.height / this.mc.displayHeight - 1;

		GL11.glPushMatrix();
		//Draw button tooltips
		hoveringString=new ArrayList<>();
		buttons.forEach(button -> {
			if (!button.visible) return;
			if (button.isMouseInButton(x, y)) hoveringString = Collections.singletonList(button.displayString);
		});

		//Draw requested tooltips
		requestedTooltipMap.values().forEach(tooltip -> {
			if (tooltip != null && tooltip.additionalCondition && tooltip.isPosCheckPassed) drawHoveringText(tooltip.strings, x, y, fontRendererObj);
		});

		if (hoveringString != null && hoveringString.stream().noneMatch(String::isEmpty)) drawHoveringText(hoveringString, x, y + 5, fontRendererObj);
		GL11.glPopMatrix();
	}

	public boolean close() {
		if(parentGui !=null) {
			parentGui.setChildGui(null);
			releaseResources();
			return false;
		}
		//profileHandler.oldWheel=0;
		if(childGui !=null) childGui.close();
		releaseResources();
		this.mc.displayGuiScreen(null);
		this.mc.setIngameFocus();
		return true;
	}

	@Override
	public void releaseResources() {
		buttons.forEach(ThinkerButtonBase::destroy);
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
		else onMouseClicked(mouseX,mouseY,mouseButton);
	}

	@Override
	public void onMouseClicked(int mouseX, int mouseY, int mouseButton) {
		boolean hasButtonPressed = false;
		for (int l = this.buttonList.size() - 1; l >= 0 ;l--)
		{
			ThinkerButtonBase guibutton = (ThinkerButtonBase)this.buttonList.get(l);
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
		handleMouseInput2();
	}

	public void handleMouseInput2(){
		super.handleMouseInput();

		if(childGui!=null)childGui.handleMouseInput2();
		int mouseX = Mouse.getX() * mc.currentScreen.width / this.mc.displayWidth;
		int mouseY =mc.currentScreen.height - Mouse.getY() * mc.currentScreen.height / this.mc.displayHeight - 1;

		if(Mouse.isInsideWindow()&&Mouse.getEventDWheel()!=0)buttons.stream().filter(buttonBase -> buttonBase instanceof IMouseWheelAccepter).map(button-> ((IMouseWheelAccepter) button)).forEach(button->button.handleMouseWheel(mouseX,mouseY, Mouse.getEventDWheel()));

		checkRequestedTooltipPos(mouseX,mouseY);
	}

	public void checkRequestedTooltipPos(int mouseX, int mouseY){
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;

		requestedTooltipMap.values().forEach(tooltip -> tooltip.isPosCheckPassed=tooltip.posPredicate.test(new Vector2f(mouseX-ContainerX,mouseY-ContainerY)));
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
