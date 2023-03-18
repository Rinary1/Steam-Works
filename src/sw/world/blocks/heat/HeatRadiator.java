package sw.world.blocks.heat;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import sw.world.heat.HasHeat;
import sw.world.heat.HeatBlockI;
import sw.world.heat.HeatConfig;
import sw.world.modules.HeatModule;

import static sw.util.SWMath.heatMap;

public class HeatRadiator extends Block implements HeatBlockI {
  HeatConfig heatConfig = new HeatConfig(-200f, 2000f, 0.4f, 0.2f, true, true);
  public TextureRegion heatRegion, topRegion;

  public HeatRadiator(String name) {
    super(name);
    solid = destructible = true;
    update = sync = true;
    rotate = true;
  }

  @Override public HeatConfig heatConfig() {return heatConfig;}

  @Override
  public void setBars() {
    super.setBars();
    addBar("heat", (HeatRadiatorBuild entity) -> new Bar(Core.bundle.get("bar.heat"), Pal.accent, () -> heatMap(entity.module().heat, 0f, heatConfig().maxHeat)));
  }
  @Override
  public void setStats() {
    super.setStats();
    heatStats(stats);
  }

  @Override
  public void load() {
    super.load();
    heatRegion = Core.atlas.find(name + "-heat");
    topRegion = Core.atlas.find(name + "-top");
  }

  public class HeatRadiatorBuild extends Building implements HasHeat {
    HeatModule module = new HeatModule();

    @Override public HeatModule module() {
      return module;
    }
    @Override public HeatBlockI type() {return (HeatBlockI) block;}

    @Override public void updateTile() {
      updateHeat(this);
    }
    @Override
    public void draw() {
      super.draw();
      drawHeat(heatRegion, this);
      Draw.rect(topRegion, x, y, 0);
    }

    @Override
    public void read(Reads read, byte revision) {
      super.read(read, revision);
      module().read(read);
    }
    @Override
    public void write(Writes write) {
      super.write(write);
      module().write(write);
    }
  }
}
