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

package cn.kuzuanpa.kGuiLib.client.objects.gui;

import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableThinkerObject;
import cn.kuzuanpa.kGuiLib.client.anime.IGuiAnime;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static cn.kuzuanpa.kGuiLib.kGuiLib.getInt;

public class Image extends ThinkerButtonBase implements IAnimatableThinkerObject {

    public Image(int id, String texturePath, int posX, int posY, int width, int height){
        super(id,posX,posY,width,height,"");
        this.texturePath=texturePath;
        this.posX=posX;
        this.posY=posY;
        this.width=width;
        this.height=height;
        loadTexture();
    }
    public Image(int id, String texturePath, int posX, int posY, int width, int height, IGuiAnime... animes){
        super(id,posX,posY,width,height);
        this.texturePath=texturePath;
        this.posX=posX;
        this.posY=posY;
        this.width=width;
        this.height=height;
        loadTexture();
        for (IGuiAnime anime : animes) this.addAnime(anime);
    }

    public void destroy(){
        super.destroy();
        TextureUtil.deleteTexture(this.glTextureId);
    }
    String texturePath;
    int posX,posY,width,height,glTextureId=-1;
    public void loadTexture(){
        try (InputStream inputstream = Files.newInputStream(Paths.get(texturePath)))
        {
            if (this.glTextureId != -1) {
                TextureUtil.deleteTexture(this.glTextureId);
                this.glTextureId = -1;
            }
            glTextureId=TextureUtil.uploadTextureImage(GL11.glGenTextures(), ImageIO.read(inputstream));
        }catch (IOException ioexception)
        {
            FMLLog.log(Level.WARN,"Failed to load texture: " + texturePath);
            ioexception.printStackTrace();
        }
    }

    public void drawButton2(Minecraft mc, int mouseX, int mouseY){
        if (this.visible) {

            IAnimatableThinkerObject.drawPre(this,timer);

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, glTextureId);

            //why (256F/height)? idk, i just rendered some picture and guessed this argument.
            float var7 = 0.00390625F*(256F/height)*(height*1F/width);
            float var8 = 0.00390625F*(256F/height);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(posX, posY + height, zLevel, 0, height * var8);
            tessellator.addVertexWithUV(posX + width, posY + height, zLevel,  width * var7, height * var8);
            tessellator.addVertexWithUV(posX + width, posY, zLevel, width * var7, 0);
            tessellator.addVertexWithUV(posX, posY, zLevel, 0, 0);
            GL11.glTranslatef(xPosition, yPosition ,0);
            GuiAnimeList.forEach(anime -> anime.animeDraw(timer));
            GL11.glTranslatef(-(xPosition ), -(yPosition ),0);
            tessellator.draw();

            IAnimatableThinkerObject.drawAfter(this,timer);
        }
    }

}
