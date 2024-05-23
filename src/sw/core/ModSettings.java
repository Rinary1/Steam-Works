package sw.core;

import arc.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.SettingsMenuDialog.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*;
import sw.*;
import sw.graphics.*;

public class ModSettings {
  public static void load() {
    Vars.ui.settings.addCategory(Core.bundle.get("sw-settings"), "sw-setting-category", table -> {
      table.pref(new ButtonSetting(Core.bundle.get("sw-erase-mod-progress"), () -> Vars.ui.showConfirm(Core.bundle.get("sw-erase-confirm"), SWVars::clearUnlockModContent)));
      table.pref(new MenuSetting((Core.bundle.get("sw-current-menu"))));
    });
  }

  static class ButtonSetting extends Setting {
    String name;
    Runnable listener;

    public ButtonSetting(String name, Runnable listener){
      super(name);
      this.name = name;
      this.listener = listener;
    }

    @Override
    public void add(SettingsTable table) {
      table.button(name, listener).margin(14).width(240f).pad(6f);
      table.row();
    }
  }
  static class MenuSetting extends Setting {
    public MenuSetting(String name) {
      super(name);
    }

    @Override
    public void add(SettingsTable table) {
      Table current = new Table(Styles.black5);
      current.add(SWMenuRenderer.find(SWVars.getMenuRenderer().currentMenuBackground, SWMenuRenderer.menus[0]).name);
      table.table(((TextureRegionDrawable)Tex.whiteui).tint(Pal.darkerGray), menu -> {
        menu.add((Core.bundle.get("sw-menu-selector"))).padBottom(10).color(Pal.accent).row();
        menu.pane(Styles.smallPane, menus -> {
          for (SWMenuRenderer.MenuProv prov : SWMenuRenderer.menus) {
            menus.button(prov.name, () -> {
              try{
                SWVars.getMenuRenderer().reload(prov.menuBackground);
                current.clear();
                current.add(prov.name);
                Core.settings.put(name, prov.name);
              }catch(Exception ex){
                Log.err("Failed to replace renderer", ex);
              }
            }).size(240, 50).row();
          }
        }).height(200f).row();
        menu.image(Styles.black3).growX().padTop(10).padBottom(10).row();
        menu.add(current).height(50).growX();
      }).margin(10f).row();
    }
  }
}
