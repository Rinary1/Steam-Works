package sw.content;

import arc.struct.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;

import static arc.struct.Seq.*;
import static mindustry.content.TechTree.*;
import static sw.content.SWBlocks.*;
import static sw.content.SWItems.*;
import static sw.content.SWLiquids.*;
import static sw.content.SWUnitTypes.*;

public class SWTechTree {
  public static TechNode root;

  public static TechNode nodeObj(UnlockableContent content, Seq<Objective> objectives) {
    return node(content, objectives, () -> {});
  }
  public static TechNode nodeProduceObj(UnlockableContent content) {
    return node(content, () -> {});
  }

  public static void load() {
    root = nodeRoot("Steam Works", coreScaffold, () -> {
      // items
      nodeProduce(nickel, () -> {
        nodeProduceObj(steam);
        nodeProduceObj(compound);
        nodeProduceObj(denseAlloy);
      });

      // crafting
      node(nickelForge, () -> {
        node(rebuilder, () -> node(burner));
        node(boiler, with(new Research(burner)), () -> node(thermalBoiler));
        nodeObj(batchPress, with(new Research(Blocks.multiPress)));
      });

      // distribution
      node(heatPipe, with(new Produce(nickel), new Research(burner)), () -> {
        node(heatBridge);
        node(heatRadiator);
      });

      // defense
      node(compoundWall, with(new Produce(compound)), () -> {
        node(compoundWallLarge);
        node(denseWall, with(new Produce(denseAlloy)), () -> node(denseWallLarge));
      });

      // turrets
      node(bolt, () -> node(light));

      // units
      node(subFactory, () -> {
        node(recluse, () -> {
          node(retreat, with(new Research(Blocks.additiveReconstructor)), () -> {
            nodeObj(evade, with(new Research(Blocks.multiplicativeReconstructor)));
          });
        });
      });
      node(crafterFactory, with(new Research(Blocks.siliconCrucible)), () -> node(bakler));
      node(swarm, with(new Produce(compound), new Research(Blocks.airFactory)), () -> {
        node(ambush, with(new Research(Blocks.additiveReconstructor)), () -> {
          nodeObj(trap, with(new Research(Blocks.multiplicativeReconstructor)));
        });
      });
    });
  }
}
