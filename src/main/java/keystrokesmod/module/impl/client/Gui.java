package keystrokesmod.module.impl.client;

import keystrokesmod.Raven;
import keystrokesmod.clickgui.ClickGui;
import keystrokesmod.module.Module;
import keystrokesmod.module.setting.impl.ButtonSetting;
import keystrokesmod.module.setting.impl.ModeSetting;
import keystrokesmod.utility.Theme;
import keystrokesmod.utility.Utils;

public class Gui extends Module {
    public static ButtonSetting removePlayerModel, resetPosition, translucentBackground, removeWatermark, rainBowOutlines, toolTip;
    public static ModeSetting font;
    public static ModeSetting theme;

    public Gui() {
        super("Gui", Module.category.client, 54);
        this.registerSetting(rainBowOutlines = new ButtonSetting("Rainbow outlines", true));
        this.registerSetting(removePlayerModel = new ButtonSetting("Remove player model", false));
        this.registerSetting(removeWatermark = new ButtonSetting("Remove watermark", false));
        this.registerSetting(translucentBackground = new ButtonSetting("Translucent background", true));
        this.registerSetting(toolTip = new ButtonSetting("Tool tip", true));
        this.registerSetting(resetPosition = new ButtonSetting("Reset position", ClickGui::resetPosition));
        this.registerSetting(font = new ModeSetting("Font", new String[]{"Minecraft", "Product Sans", "Tenacity"}, 0));
        this.registerSetting(theme = new ModeSetting("Theme", Theme.themes, 0));
    }

    public static int getColor() {
        return Theme.getGradient((int) theme.getInput(), 12);
    }

    public void onEnable() {
        if (Utils.nullCheck() && mc.currentScreen != Raven.clickGui) {
            mc.displayGuiScreen(Raven.clickGui);
            Raven.clickGui.initMain();
        }
        this.disable();
    }
}
