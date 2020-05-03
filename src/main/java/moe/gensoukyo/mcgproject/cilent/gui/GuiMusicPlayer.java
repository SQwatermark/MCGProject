package moe.gensoukyo.mcgproject.cilent.gui;

import moe.gensoukyo.mcgproject.common.feature.musicplayer.EntityMusicPlayer;
import moe.gensoukyo.mcgproject.common.network.MusicPlayerPacket;
import moe.gensoukyo.mcgproject.common.network.NetworkWrapper;
import moe.gensoukyo.mcgproject.common.util.MathMCG;
import moe.gensoukyo.mcgproject.core.Information;
import moe.gensoukyo.mcgproject.core.MCGProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;

@Information(author = "EternalBlueFlame, SQwatermark", licence = "The MIT License", source = "https://github.com/EternalBlueFlame/Traincraft-5")
@SideOnly(Side.CLIENT)
public class GuiMusicPlayer extends GuiScreen {

	private GuiTCTextField streamTextBox;
	private EntityMusicPlayer musicPlayer;
	private EntityPlayer player;
	private int gui_width;
	private int gui_height;
	private int anim = 0;
	private String infoText;
	ArrayList<String> randomMusics = new ArrayList<>();
	public final String NETEASE_URL = "http://music.163.com/song/media/outer/url?id=";
	
	public GuiMusicPlayer(EntityPlayer player, EntityMusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		this.player = player;
		gui_width = 352;
		gui_height = 120;
		infoText = "将外链粘贴在下方（可使用网易云的音乐id，支持.m3u和.pls流媒体）";
		randomMusics.add("26124646");
		randomMusics.add("774882");
		randomMusics.add("450222722");
		randomMusics.add("33211208");
		randomMusics.add("26134231");
		randomMusics.add("407685151");
		randomMusics.add("30251976");
		randomMusics.add("1444956021");
	}

	@Override
	public void initGui() {
		buttonList.clear();
		buttonList.add(new GuiButton(0, this.width / 2 - 45, this.height / 2 + 30, 90, 20, "播放/暂停"));
		buttonList.add(new GuiButton(1, this.width / 2 - 45 - 120, this.height / 2 + 30, 90, 20, "粘贴"));
		buttonList.add(new GuiButton(2, this.width / 2 - 45 + 120, this.height / 2 + 30, 90, 20, "清空"));
		buttonList.add(new GuiButton(4, this.width / 2 - 70, this.height / 2 + 30, 20, 20, "+"));
		buttonList.add(new GuiButton(5, this.width / 2 + 50, this.height / 2 + 30, 20, 20, "-"));
		streamTextBox = new GuiTCTextField(this.fontRenderer, this.width / 2 - (gui_width) / 2 + 10, this.height / 2 - gui_height / 2 + 50, gui_width - 16, 16);
		streamTextBox.setMaxStringLength(1000);
		streamTextBox.setText(musicPlayer.streamURL);
		//Localizations
		Keyboard.enableRepeatEvents(true);
		int var1 = (this.width - gui_width) / 2;
		int var2 = (this.height - gui_height) / 2;
		if (musicPlayer.owner.isEmpty()) {
			this.buttonList.add(new GuiButton(3, var1 + gui_width - 350, var2 - 10, 51, 10, "未锁定"));
		}
		else {
			this.buttonList.add(new GuiButton(3, var1 + gui_width - 350, var2 - 10, 43, 10, "已锁定"));
		}
		buttonList.add(new GuiButton(6, var1 + gui_width - 296, var2 - 10, 43, 10, "随机"));
		buttonList.add(new GuiButton(7, var1 + gui_width - 242, var2 - 10, 43, 10, "沉浸"));
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		int var5 = (this.width) / 2 - gui_width / 2;
		int var6 = (this.height) / 2 - gui_height / 2;
		int var7 = (this.width) / 2 + gui_width / 2;
		int var8 = (this.height) / 2 + gui_height / 2;

		drawRect(var5 + 2, var6 + 2, var7 + 2, var8 + 2, 0xffc6c6c6);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(new ResourceLocation(MCGProject.ID, "textures/gui/" + "gui_jukebox.png"));

		drawSquareCorners(2, 2, var5, var6, var7, var8, 0, 0);
		drawSquareCorners(-6, -20, var5, var6 + 26, var7, var8 - gui_height / 2 + 26, 10, 0);
		drawSquareSides(gui_width, gui_height, 2, 2, var5, var6, var7, var8, 0, 0);
		drawSquareSides(gui_width - 16, 16, 2, 2, var5 + 8, var6 + 22 + 26, var7 - 8, var8 - gui_height / 2 - 32 + 36, 10, 0);

		drawTexturedModalRect(var5 + 6, var6 + 6, 20, 0, 7, 10);
		drawTexturedModalRect(var5 + 13, var6 + 6, anim / 100, 12, (28 * (gui_width / 28)) / 2, 10);
		drawTexturedModalRect(var5 + 13 + (28 * (gui_width / 28)) / 2, var6 + 6, anim / 100, 12, (28 * (gui_width / 28)) / 2, 10);
		if (musicPlayer.isPlaying) {
			anim += 10;
			if (anim == 2800) {
				anim = 0;
			}
		}

		//fontRenderer.drawString("Date: " + Calendar.getInstance().get(Calendar.MONTH) + " " + Calendar.getInstance().get(Calendar.DATE), var5 - gui_width / 2, var6 - 30, 0xffffffff);
		
		if((Minecraft.getMinecraft().player != null) && ((musicPlayer).musicCode != null)) {
			fontRenderer.drawString("音量: " + Math.round(musicPlayer.volume * 100), width / 2 - 26, height / 2 + 18, 0xff0e0e0e);
		}
		else {
			fontRenderer.drawString("音量: 0", width / 2 - 26, height / 2 + 18, 0xff0e0e0e);
		}

		fontRenderer.drawString(infoText, this.width / 2 - gui_width / 2 + 10, this.height / 2 - 30, -15856114);
		super.drawScreen(par1, par2, par3);
		streamTextBox.drawTextBox();
		if (intersectsWith(par1, par2)) {
			drawCreativeTabHoveringText(par1, par2);
		}
	}

	public void drawSquareCorners(int x, int y, int x0y0, int x1y0, int x0y1, int x1y1, int u, int v) {
		mc.renderEngine.bindTexture(new ResourceLocation(MCGProject.ID, "textures/gui/" + "gui_jukebox.png"));
		drawTexturedModalRect(x0y0 - x, x1y0 - y, u, v, 4, 4);
		drawTexturedModalRect(x0y1 + x, x1y0 - y, u + 5, v, 4, 4);
		drawTexturedModalRect(x0y0 - x, x1y1 + y, u, v + 5, 4, 4);
		drawTexturedModalRect(x0y1 + x, x1y1 + y, u + 5, v + 5, 4, 4);
	}

	public void drawSquareSides(int width, int height, int x, int y, int x0y0, int x1y0, int x0y1, int x1y1, int u, int v) {
		mc.renderEngine.bindTexture(new ResourceLocation(MCGProject.ID, "textures/gui/" + "gui_jukebox.png"));
		int sides = Math.max(width, height);
		for (int i = 0; i < sides; i++) {
			if (width > height) {
				if (i < height) {
					drawTexturedModalRect(x0y0 - x, x1y0 + y + i, u, v + 4, 4, 1);
					drawTexturedModalRect(x0y1 + x, x1y0 + y + i, u + 5, v + 4, 4, 1);
				}
				drawTexturedModalRect(x0y0 + x + i, x1y0 - y, u + 4, v, 1, 4);
				drawTexturedModalRect(x0y0 + x + i, x1y1 + y, u + 4, v + 5, 1, 4);
			}
			else {
				if (i < width) {
					drawTexturedModalRect(x0y0 + x + i, x1y0 - y, u + 4, v, 1, 4);
					drawTexturedModalRect(x0y0 + x + i, x1y1 + y, u + 4, v + 5, 1, 4);
				}
				drawTexturedModalRect(x0y0 - x, x1y0 + y + i, u, v + 4, 4, 1);
				drawTexturedModalRect(x0y1 + x, x1y0 + y + i, u + 5, v + 4, 4, 1);
			}
		}
	}

	@Override
	public void updateScreen() {
		streamTextBox.updateCursorCounter();
		super.updateScreen();
	}

	@Override
	protected void keyTyped(char par1, int par2) throws IOException {
		streamTextBox.textboxKeyTyped(par1, par2);
		if (par1 == 28) {
			actionPerformed(buttonList.get(1));
		}
		if (par2 == 1 || par2 == mc.gameSettings.keyBindInventory.getKeyCode()) {
			if (!streamTextBox.isFocused()) {
				mc.player.closeScreen();
			}
		}
		super.keyTyped(par1, par2);
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) throws IOException {
		streamTextBox.mouseClicked(par1, par2, par3);
		super.mouseClicked(par1, par2, par3);
	}

	protected String parseURL(String url) {
		if (url.toLowerCase().contains(".m3u"))
			return takeFirstEntryFromM3U(url);
		if (url.toLowerCase().contains(".pls"))
			return parsePls(url);
		if (url.toLowerCase().contains("music.163.com"))
			return parseNetease(url);
		if (MathMCG.isNumeric(url))
			return NETEASE_URL + url + ".mp3";
		return url;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			if (streamTextBox.getText() != null && streamTextBox.getText().length() > 0) {
				if (!musicPlayer.isPlaying) {
					musicPlayer.streamURL = parseURL(this.streamTextBox.getText());
					musicPlayer.startStream();
				}
				else {
					musicPlayer.stopStream();
				}
				NetworkWrapper.INSTANCE.sendToServer(new MusicPlayerPacket(musicPlayer));
			}
			else if (musicPlayer.isPlaying){
				musicPlayer.stopStream();
				NetworkWrapper.INSTANCE.sendToServer(new MusicPlayerPacket(musicPlayer));
			}
		}
		else if (button.id == 1) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			try {
				String result = (String) clipboard.getData(DataFlavor.stringFlavor);
				streamTextBox.setText(result);
			} catch (Exception ignored) {}
		}
		else if (button.id == 2) {
			streamTextBox.setText("");
			streamTextBox.setFocused(true);
		}
		else if (button.id == 4) {
			if(musicPlayer.volume<1.0f) {
				musicPlayer.volume += 0.1f;
			}
			NetworkWrapper.INSTANCE.sendToServer(new MusicPlayerPacket(musicPlayer));
		}
		else if (button.id == 5) {
			if(musicPlayer.volume>0.0f) {
				musicPlayer.volume -= 0.1f;
			}
			NetworkWrapper.INSTANCE.sendToServer(new MusicPlayerPacket(musicPlayer));
		}
		else if (button.id == 3) {
			if (musicPlayer.owner.isEmpty()) {
				musicPlayer.owner = player.getName();
				button.displayString = "已锁定";
			} else {
				musicPlayer.owner = "";
				button.displayString = "未锁定";
			}
			NetworkWrapper.INSTANCE.sendToServer(new MusicPlayerPacket(musicPlayer));
		}
		else if (button.id == 6) {
			streamTextBox.setText(NETEASE_URL + randomMusics.get(new Random().nextInt(randomMusics.size())) + ".mp3");
		}
		else if (button.id == 7) {
			musicPlayer.immersive = !musicPlayer.immersive;
			NetworkWrapper.INSTANCE.sendToServer(new MusicPlayerPacket(musicPlayer));
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean getState() {
		return musicPlayer.isPlaying;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	protected void drawCreativeTabHoveringText(int t, int g) {
		String state;
		if (!musicPlayer.owner.isEmpty())
			state = "已锁定";
		else
			state = "未锁定";

		int textWidth = fontRenderer.getStringWidth("When a jukebox is unlocked,") + 2;

		int i4 = 0xf0100010;
		drawGradientRect(t + 15 - 3, g - 40 - 4, t + textWidth + 3, g + 8 + 4, i4, i4);
		drawGradientRect(t + 15 - 4, g - 40 - 3, t + textWidth + 4, g + 8 + 3, i4, i4);
		int colour1 = 0x505000ff;
		int colour2 = (colour1 & 0xfefefe) >> 1 | colour1 & 0xff000000;
		drawGradientRect(t + 15 - 3, g - 40 - 3, t + textWidth + 3, g + 8 + 3, colour1, colour2);
		drawGradientRect(t + 15 - 2, g - 40 - 2, t + textWidth + 2, g + 8 + 2, i4, i4);
		fontRenderer.drawStringWithShadow("在被锁定时，只有", t + 15, g - 40, -1);
		fontRenderer.drawStringWithShadow("主人和管理员可以", t + 15, g + 10 - 40, -1);
		fontRenderer.drawStringWithShadow("打开GUI和破坏它", t + 15, g + 20 - 40, -1);
		fontRenderer.drawStringWithShadow("当前状态: " + state, t + 15, g + 30 - 40, -1);
		fontRenderer.drawStringWithShadow("主人: " + musicPlayer.owner, t + 15, g + 40 - 40, -1);
	}

	public boolean intersectsWith(int mouseX, int mouseY) {
		int j = (width - gui_width) / 2;
		int k = (height - gui_height) / 2;
		return mouseX >= j + gui_width - 350 && mouseX <= j + gui_width - 300 && mouseY >= k - 10 && mouseY <= k;
	}

	public String takeFirstEntryFromM3U(String m3uurl) {
		String out = "";
		try {
			URL m3u = new URL(m3uurl);
			URLConnection con = m3u.openConnection();
			BufferedReader i = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String mp3;
			while ((mp3 = i.readLine()) != null) {
				if (!mp3.startsWith("#")) {
					break;
				}
			}
			out = mp3;
		} catch (IOException e) {
			infoText = "Not a valid stream, only .m3u and .pls";
		}
		return out;
	}

	public String parsePls(String plsurl) {
		String out = "";
		try {
			URL pls = new URL(plsurl);
			URLConnection con = pls.openConnection();
			BufferedReader i = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String mp3;
			while ((mp3 = i.readLine()) != null) {
				String f = mp3.trim();
				if (f.contains("http://")) {
					out = f.substring(f.indexOf("http://"));
					break;
				}
			}
		} catch (IOException e) {
			infoText = "Not a valid stream, only .m3u and .pls";
		}
		return out;
	}

	public static String getArg(String args, String name) {
		if (args.contains(name)) {
			int pos = args.indexOf(name);
			String sub = args.substring(pos + name.length() + 1);
			if (sub.contains("&")) {
				return sub.split("&")[0];
			}
			return sub;
		}
		return args;
	}

	public String parseNetease(String input) {
		if (input.contains("music.163.com") && input.contains("?")) {
			String[] args = input.split("\\?");
			if (args.length > 1) {
				String id = getArg(args[1], "id");
				return NETEASE_URL + id + ".mp3";
			}
		}
		return input;
	}

}