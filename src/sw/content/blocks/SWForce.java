package sw.content.blocks;

import arc.math.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import sw.content.*;
import sw.world.blocks.force.*;
import sw.world.blocks.production.*;
import sw.world.consumers.*;
import sw.world.graph.*;
import sw.world.meta.*;

import static mindustry.type.ItemStack.*;

public class SWForce {
	public static Block
		excavator,

		beltNode, beltNodeLarge, omniBelt,
		torquePump,
		stirlingGenerator, electricSpinner, pressureSpinner, waterWheel,
		compoundMixer, freezingSpinner,

		mortar, incend;

	public static void load() {
		excavator = new ForceDrill("excavator") {{
			requirements(Category.production, with(
				SWItems.compound, 45,
				Items.graphite, 35,
				Items.silicon, 25,
				Items.titanium, 30
			));
			size = 3;
			health = 200;
			drillTime = 320f;
			rotatorOffset = 4f;
			tier = 4;
			updateEffect = Fx.pulverizeMedium;
			drillEffect = Fx.mineBig;
			consume(new ConsumeSpeed(1f, 8f));
		}};

		beltNode = new ForceNode("belt-node") {{
			requirements(Category.power, with(
				Items.silicon, 20,
				SWItems.compound, 20
			));
			health = 120;
			forceConfig = new ForceConfig() {{
				friction = 0.03f;
				maxForce = 1f;
				range = 60f;
			}};
		}};
		beltNodeLarge = new ForceNode("belt-node-large") {{
			requirements(Category.power, with(
				Items.silicon, 60,
				Items.titanium, 40,
				SWItems.compound, 80
			));
			size = 2;
			health = 120;
			forceConfig = new ForceConfig() {{
				friction = 0.06f;
				maxForce = 2f;
				range = 90f;
				beltSize = 4f;
			}};
		}};
		omniBelt = new OmniForceNode("omni-belt") {{
			requirements(Category.power, with(
				Items.silicon, 60,
				Items.titanium, 40,
				SWItems.neodymium, 80
			));
			size = 3;
			health = 120;
			forceConfig = new ForceConfig() {{
				friction = 0.06f;
				maxForce = 3f;
				range = 120f;
			}};
		}};

		torquePump = new ForcePump("torque-pump") {{
			requirements(Category.liquid, with(
				Items.metaglass, 40,
				Items.silicon, 50,
				Items.lead, 30,
				SWItems.compound, 40
			));
			size = 2;
			health = 160;
			pumpAmount = 0.375f;
			consume(new ConsumeSpeed(3, 9));
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawPumpLiquid(),
				new DrawDefault()
			);
			forceConfig = new ForceConfig() {{
				maxForce = 18f;
				beltSize = 4f;
				outputsForce = false;
			}};
		}};

		stirlingGenerator = new SWGenericCrafter("stirling-generator") {{
			requirements(Category.power, with(
				SWItems.denseAlloy, 120,
				SWItems.nickel, 110,
				Items.silicon, 140,
				Items.titanium, 100
			));
			size = 3;
			health = 160;
			consume(new ConsumeHeat(0.5f, 100f, false));
			craftTime = 1f;
			outputSpeed = 3f;
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawPistons() {{
					sides = 8;
					sideOffset = 10f;
					sinScl = 2.5f;
					sinMag = 2f;
				}},
				new DrawRegion("-top")
			);
			forceConfig = new ForceConfig() {{
				maxForce = 5f;
				friction = 0.06f;
				beltSize = 6f;
				acceptsForce = false;
			}};
		}};
		pressureSpinner = new ForceSwayCrafter("pressure-spinner") {{
			requirements(Category.power, with(
				Items.silicon, 50,
				Items.graphite, 80,
				SWItems.compound, 60
			));
			size = 3;
			health = 160;
			swayScl = 0.7f;
			craftTime = 1f;
			drawer = new DrawMulti(
				new DrawDefault(),
				new DrawRegion("-rotator") {{
					spinSprite = true;
					rotateSpeed = 16f;
				}}
			);
			consumeLiquid(SWLiquids.steam, 0.2f);
			forceConfig = new ForceConfig() {{
				maxForce = 5f;
				friction = 0.06f;
				beltSize = 6f;
				acceptsForce = false;
			}};
			outputSpeed = 2f;
		}};
		electricSpinner = new SWGenericCrafter("electric-spinner") {{
			requirements(Category.power, with(
				Items.silicon, 50,
				Items.graphite, 80,
				SWItems.compound, 60
			));
			size = 2;
			health = 160;
			consumePower(1f);
			hasHeat = false;
			craftTime = 1f;
			forceConfig = new ForceConfig() {{
				maxForce = 5f;
				friction = 0.06f;
				beltSize = 4f;
				acceptsForce = false;
			}};
			outputSpeed = 1f;
		}};
		waterWheel = new SWGenericCrafter("water-wheel") {{
			requirements(Category.power, with(
				Items.silicon, 80,
				Items.lead, 120,
				Items.metaglass, 70,
				SWItems.compound, 100
			));
			size = 3;
			health = 200;
			consumeLiquid(Liquids.water, 0.2f);
			drawer = new DrawMulti(
				new DrawRegion("-bottom"),
				new DrawLiquidTile(Liquids.water, 1f),
				new DrawRegion("-wheel") {{
					spinSprite = true;
					rotateSpeed = -1f;
				}},
				new DrawRegion("-top")
			);
			hasHeat = false;
			hasLiquids = true;
			craftTime = 1f;
			forceConfig = new ForceConfig() {{
				maxForce = 1f;
				friction = 0.09f;
				beltSize = 6f;
				acceptsForce = false;
			}};
			outputSpeed = 1f;
		}};

		compoundMixer = new SWGenericCrafter("compound-mixer") {{
			requirements(Category.crafting, with(
				Items.silicon, 150,
				Items.graphite, 160,
				Items.titanium, 80,
				SWItems.compound, 200
			));
			hasHeat = false;
			forceConfig = new ForceConfig() {{
				outputsForce = false;
				friction = 0.09f;
				maxForce = 14f;
				beltSize = 6f;
			}};
			size = 3;
			health = 200;
			craftTime = 45f;
			drawer = new DrawMulti(new DrawDefault(), new DrawRegion("-rotator"){{
				spinSprite = true;
				rotateSpeed = 2f;
			}});
			consume(new ConsumeSpeed(1.5f, 15f));
			consume(new ConsumeRatio(ForceGraph.ForceRatio.normal));
			consumeItems(with(
				Items.copper, 2,
				SWItems.nickel, 1
			));
			outputItem = new ItemStack(SWItems.compound, 2);
		}};
		freezingSpinner = new SWGenericCrafter("freezing-spinner") {{
			requirements(Category.production, with(
				Items.silicon, 150,
				Items.graphite, 160,
				Items.thorium, 80,
				SWItems.frozenMatter, 200
			));
			size = 2;
			health = 160;
			craftTime = 90f;
			forceConfig.outputsForce = hasHeat = false;
			consume(new ConsumeSpeed(2f, 4f));
			consume(new ConsumeRatio(ForceGraph.ForceRatio.normal));
			consumeItems(with(
				SWItems.denseAlloy, 3
			));
			consumeLiquid(Liquids.cryofluid, 0.6f);
			outputItem = new ItemStack(SWItems.frozenMatter, 2);
		}};

		mortar = new ForceTurret("mortar") {{
			requirements(Category.turret, with(
				Items.silicon, 120,
				Items.graphite, 100,
				SWItems.compound, 150
			));
			targetAir = false;
			consume(new ConsumeSpeed(1f, 6f));
			consumeItem(Items.graphite, 2);
			size = 3;
			health = 200 * 9;
			reload = 120f;
			range = 180f;
			recoil = -1f;
			shootY = 2f;
			shootSound = Sounds.mediumCannon;
			shoot = new ShootPattern() {{
				firstShotDelay = 30f;
			}};
			forceConfig = new ForceConfig() {{
				friction = 0.09f;
				maxForce = 10f;
				outputsForce = false;
			}};
			drawer = new DrawTurret() {{
				parts.addAll(
					new RegionPart("-launcher") {{
						x = y = 0f;
						moveY = -10;
						progress = PartProgress.charge.mul(1.5f).curve(Interp.pow2Out);
					}}
				);
			}};
			shootType = new ArtilleryBulletType(4f, 120) {{
				width = height = 20f;
				splashDamage = 120f;
				splashDamageRadius = 40f;
				lifetime = 50f;
			}};
		}};
		incend = new ForceTurret("incend") {{
			requirements(Category.turret, with(
				Items.silicon, 50,
				SWItems.compound, 40,
				Items.graphite, 50,
				Items.titanium, 80
			));
			size = 2;
			health = 890;
			reload = 6f;
			recoil = 0.5f;
			range = 92f;
			shootY = 6f;
			targetAir = false;
			shootSound = Sounds.flame;
			consume(new ConsumeSpeed(1f, 6f));
			consumeLiquid(SWLiquids.butane, 0.2f);

			forceConfig = new ForceConfig() {{
				friction = 0.18f;
				maxForce = 10f;
				outputsForce = false;
			}};

			drawer = new DrawTurret() {{
				parts.add(
					new RegionPart("-barrel") {{
						moveY = -1f;
						under = true;
					}}
				);
			}};

			shootType = new BulletType(4f, 25) {{
				hitSize = 8f;
				lifetime = 23f;
				status = StatusEffects.burning;
				despawnEffect = Fx.none;
				shootEffect = SWFx.longShootFlame;
				hitEffect = Fx.hitFlameSmall;
				statusDuration = 60 * 4f;
				pierce = true;
				collidesAir = false;
				keepVelocity = false;
				hittable = false;
			}};
		}};
	}
}