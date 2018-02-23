package net.minecraft.client.gui;

import manticore.Manticore;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiGPS extends GuiScreen
{

    private GuiTextField inputX;
    private GuiTextField inputZ;

    public GuiGPS()
    {
    }

    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.inputX = new GuiTextField(0, this.fontRendererObj, 10, this.height / 2, 100, 12);
        this.inputX.setFocused(true);
        this.inputX.setEnableBackgroundDrawing(false);
        this.inputX.setMaxStringLength(256);
        this.inputX.setText(String.valueOf(Manticore.gpsX));

        this.inputZ = new GuiTextField(0, this.fontRendererObj, 10 + 100 + 20, this.height / 2, 100, 12);
        this.inputZ.setFocused(false);
        this.inputZ.setEnableBackgroundDrawing(false);
        this.inputZ.setMaxStringLength(256);
        this.inputZ.setText(String.valueOf(Manticore.gpsZ));
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    public void updateScreen()
    {
        if (this.inputX.isFocused())
            this.inputX.updateCursorCounter();
        else if (this.inputZ.isFocused())
            this.inputZ.updateCursorCounter();
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (keyCode == Keyboard.KEY_ESCAPE)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
            Manticore.gpsActive = false;
        } else if (keyCode == Keyboard.KEY_RETURN || keyCode == Keyboard.KEY_NUMPADENTER)
        {
            try {
                Manticore.gpsX = Integer.parseInt(this.inputX.getText());
            } catch (NumberFormatException e) {
                Manticore.gpsX = 0;
                Manticore.gpsActive = false;
                this.mc.displayGuiScreen((GuiScreen)null);
                return;
            }
            try {
                Manticore.gpsZ = Integer.parseInt(this.inputZ.getText());
            } catch (NumberFormatException e) {
                Manticore.gpsZ = 0;
                Manticore.gpsActive = false;
                this.mc.displayGuiScreen((GuiScreen)null);
                return;
            }

            Manticore.gpsActive = true;
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (keyCode == Keyboard.KEY_TAB)
        {
            if (this.inputX.isFocused()) {
                this.inputX.setFocused(false);
                this.inputZ.setFocused(true);
            } else {
                this.inputX.setFocused(true);
                this.inputZ.setFocused(false);
            }
        }
        else if ((keyCode >= Keyboard.KEY_1 && keyCode <= Keyboard.KEY_0) || (keyCode == Keyboard.KEY_MINUS) ||
                (keyCode == Keyboard.KEY_NUMPAD0 || keyCode == Keyboard.KEY_NUMPAD1 || keyCode == Keyboard.KEY_NUMPAD2 ||
                 keyCode == Keyboard.KEY_NUMPAD3 || keyCode == Keyboard.KEY_NUMPAD4 || keyCode == Keyboard.KEY_NUMPAD5 ||
                 keyCode == Keyboard.KEY_NUMPAD6 || keyCode == Keyboard.KEY_NUMPAD7 || keyCode == Keyboard.KEY_NUMPAD8 ||
                 keyCode == Keyboard.KEY_NUMPAD9) || (keyCode == Keyboard.KEY_BACK))
        {
            this.inputX.textboxKeyTyped(typedChar, keyCode);
            this.inputZ.textboxKeyTyped(typedChar, keyCode);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (mouseButton == 0)
        {
        }

        this.inputX.mouseClicked(mouseX, mouseY, mouseButton);
        this.inputZ.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawRect(10, (this.height / 2) - 4, 110, (this.height / 2) + 10, Integer.MIN_VALUE);
        drawRect(130, (this.height / 2) - 4 , 230, (this.height / 2) + 10, Integer.MIN_VALUE);
        this.inputX.drawTextBox();
        this.inputZ.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return true;
    }
}
