package net.minecraft.client.gui;

import java.io.IOException;
import manticore.Manticore;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiManticoreXraySettings extends GuiScreen
{
    private final GuiScreen parentGuiScreen;
    private final GameSettings guiGameSettings;
    protected String screenTitle = "X-ray Settings";
    private GuiButton diamondOreBtn;
    private GuiButton coalOreBtn;
    private GuiButton bedrockBtn;
    private GuiButton goldOreBtn;
    private GuiButton ironOreBtn;

    public GuiManticoreXraySettings(GuiScreen parentScreenIn, GameSettings gameSettingsIn)
    {
        this.parentGuiScreen = parentScreenIn;
        this.guiGameSettings = gameSettingsIn;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();
        diamondOreBtn = new GuiButton(300, this.width / 2 - 100, 20, "Diamond ore " +  (Manticore.xrayDiamondOre ? "ON" : "OFF"));
        coalOreBtn = new GuiButton(301, this.width / 2 - 100, 44, "Coal ore " +  (Manticore.xrayCoalOre ? "ON" : "OFF"));
        bedrockBtn = new GuiButton(302, this.width / 2 - 100, 68, "Bedrock " +  (Manticore.xrayBedrock ? "ON" : "OFF"));
        goldOreBtn = new GuiButton(303, this.width / 2 - 100, 92, "Gold ore " +  (Manticore.xrayGoldOre ? "ON" : "OFF"));
        ironOreBtn = new GuiButton(304, this.width / 2 - 100, 116, "Iron ore " +  (Manticore.xrayIronOre ? "ON" : "OFF"));
        this.buttonList.add(diamondOreBtn);
        this.buttonList.add(coalOreBtn);
        this.buttonList.add(bedrockBtn);
        this.buttonList.add(goldOreBtn);
        this.buttonList.add(ironOreBtn);
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 27, I18n.format("gui.done")));
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == 1)
        {
            this.mc.gameSettings.saveOptions();
        }

        super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 200)
            {
                this.mc.renderGlobal.loadRenderers();
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (button.id == 300) {
                Manticore.xrayDiamondOre = !Manticore.xrayDiamondOre;
                diamondOreBtn.displayString = "Diamond ore " + (Manticore.xrayDiamondOre ? "ON" : "OFF");
            }
            if (button.id == 301) {
                Manticore.xrayCoalOre = !Manticore.xrayCoalOre;
                coalOreBtn.displayString = "Coal ore " + (Manticore.xrayCoalOre ? "ON" : "OFF");
            }
            if (button.id == 302) {
                Manticore.xrayBedrock = !Manticore.xrayBedrock;
                bedrockBtn.displayString = "Bedrock " + (Manticore.xrayBedrock ? "ON" : "OFF");
            }
            if (button.id == 303) {
                Manticore.xrayGoldOre = !Manticore.xrayGoldOre;
                goldOreBtn.displayString = "Gold ore " + (Manticore.xrayGoldOre ? "ON" : "OFF");
            }
            if (button.id == 304) {
                Manticore.xrayIronOre = !Manticore.xrayIronOre;
                ironOreBtn.displayString = "Iron ore " + (Manticore.xrayIronOre ? "ON" : "OFF");
            }
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        int i = this.guiGameSettings.guiScale;
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.guiGameSettings.guiScale != i)
        {
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int j = scaledresolution.getScaledWidth();
            int k = scaledresolution.getScaledHeight();
            this.setWorldAndResolution(this.mc, j, k);
        }
    }

    /**
     * Called when a mouse button is released.
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        int i = this.guiGameSettings.guiScale;
        super.mouseReleased(mouseX, mouseY, state);

        if (this.guiGameSettings.guiScale != i)
        {
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int j = scaledresolution.getScaledWidth();
            int k = scaledresolution.getScaledHeight();
            this.setWorldAndResolution(this.mc, j, k);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 5, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
