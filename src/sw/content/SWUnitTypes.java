package sw.content;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import ent.anno.Annotations.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import sw.ai.*;
import sw.gen.*;
import sw.type.*;

public class SWUnitTypes {
  @EntityDef({Revealc.class, WaterMovec.class, Unitc.class}) public static UnitType recluse, retreat, evade;
  @EntityDef({Tankc.class, Unitc.class}) public static UnitType sentry, tower, castle;
  @EntityDef({TetherUnitc.class, Unitc.class}) public static UnitType protMask, mesoShield, paleoShield, cenoShield;
  @EntityDef({Legsc.class, Unitc.class}) public static UnitType existence, remembered, presence;
  @EntityDef({Shieldedc.class, Legsc.class, Unitc.class}) public static UnitType prot, meso, paleo, ceno;
  @EntityDef({Copterc.class, Unitc.class}) public static UnitType fly, spin, gyro;
  //@EntityDef({Unitc.class}) public static UnitType focus, precision, target;
  @EntityDef({Intangiblec.class, Legsc.class, Unitc.class}) public static UnitType lambda;

  public static UnitType focus, precision, target;

  public static void load() {

    //region specialist
    recluse = new SWUnitType("recluse") {{
      speed = 1;
      health = 250;
      hitSize = 8f;
      accel = 0.4f;
      drag = 0.14f;
      rotateSpeed = 4f;
      range = maxRange = 40f;
      submerges = true;

      constructor = UnitWaterMoveReveal::create;

      weapons.add(new Weapon("sw-recluse-weapon") {{
        x = 3.75f;
        y = -1.25f;
        reload = 20f;
        shootSound = Sounds.sap;
        rotate = true;
        bullet = new SapBulletType() {{
          damage = 20f;
          length = 40f;
          color = Pal.sapBullet;
        }};
      }});
    }};
    retreat = new SWUnitType("retreat") {{
      health = 580;
      speed = 0.9f;
      hitSize = 11f;
      accel = 0.4f;
      drag = 0.14f;
      rotateSpeed = 3.5f;
      range = maxRange = 80f;
      targetAir = false;
      submerges = true;
      vulnerabilityTime = 150f;

      constructor = UnitWaterMoveReveal::create;

      weapons.add(new Weapon("sw-retreat-weapon") {{
        x = y = 0f;
        reload = 60f;
        mirror = false;
        rotate = true;
        shootSound = Sounds.artillery;
        bullet = new ArtilleryBulletType(3f, 40) {{
          width = height = 10f;
          lifetime = 26.75f;
          collidesGround = true;
          splashDamage = 40f;
          splashDamageRadius = 24f;
          hitEffect = despawnEffect = Fx.hitBulletColor;
          frontColor = hitColor = trailColor = Pal.sapBullet;
          backColor = Pal.sapBulletBack;
          trailLength = 40;
          status = StatusEffects.sapped;
          statusDuration = 120f;
        }};
      }});
    }};
    evade = new SWUnitType("evade") {{
      health = 1200;
      speed = 0.8f;
      accel = 0.4f;
      drag = 0.14f;
      hitSize = 18f;
      rotateSpeed = 3f;
      range = maxRange = 160f;
      submerges = true;
      vulnerabilityTime = 240f;

      constructor = UnitWaterMoveReveal::create;

      weapons.add(
        new Weapon() {{
          x = y = 0f;
          reload = 240f;
          rotateSpeed = 360f;
          mirror = false;
          rotate = true;
          shootSound = Sounds.missileLaunch;
          bullet = new BasicBulletType(4f, 200f) {{
            width = height = 15f;
            lifetime = 40f;
            drag = -0.01f;
            collideFloor = true;
            splashDamage = 120f;
            splashDamageRadius = 24f;
            frontColor = trailColor = hitColor = Pal.sapBullet;
            backColor = Pal.sapBulletBack;
            status = StatusEffects.sapped;
            statusDuration = 300f;
            trailLength = 80;
          }};
        }},
        new Weapon("sw-evade-cannon") {{
          x = 0f;
          y = -7.25f;
          reload = 120f;
          targetAir = false;
          mirror = false;
          rotate = true;
          shootSound = Sounds.artillery;
          bullet = new ArtilleryBulletType(4f, 120) {{
            width = height = 12f;
            lifetime = 25f;
            range = 100f;
            collidesGround = true;
            splashDamage = 120f;
            splashDamageRadius = 24f;
            hitEffect = despawnEffect = Fx.hitBulletColor;
            frontColor = hitColor = trailColor = Pal.sapBullet;
            backColor = Pal.sapBulletBack;
            status = StatusEffects.sapped;
            statusDuration = 180f;
            trailLength = 40;
          }};
        }},
        new Weapon("sw-evade-mount") {{
          x = 8.75f;
          y = 4f;
          reload = 60f;
          rotate = true;
          shootSound = Sounds.shotgun;
          bullet = new ShrapnelBulletType() {{
            damage = 80f;
            length = 12f;
            range = length;
            toColor = Pal.sapBullet;
            serrations = 4;
          }};
        }}
      );
    }};

    //endregion
    //region tanks
    sentry = new SWUnitType("sentry") {{
      health = 320;
      speed = 0.25f;
      range = maxRange = 800f;
      hitSize = 8f;
      rotateSpeed = 1;
      outlines = faceTarget = false;
      targetAir = false;
      squareShape = true;
      omniMovement = false;
      rotateMoveFirst = true;
      aiController = MortarAI::new;

      treadFrames = 16;
      treadRects = new Rect[]{
				new Rect(-24f, -29f, 14, 56)
			};

      constructor = TankUnit::create;

      weapons.add(
        new Weapon("sw-long-mortar") {{
          x = y = 0f;
          reload = 300f;
          recoil = 0f;
          recoilTime = 60f;
          cooldownTime = 150f;
          rotateSpeed = 0.5f;
          shootY = 16f;
          shootSound = Sounds.largeCannon;
          rotate = true;
          mirror = false;

          parts.add(
            new RegionPart("-cannon") {{
              moveY = -2f;
              outlineLayerOffset = 0f;
              under = true;
              progress = PartProgress.recoil.curve(Interp.bounceIn);
            }}
          );

          bullet = new ArtilleryBulletType(2f, 1, "missile-large") {{
            frontColor = Pal.missileYellow;
            backColor = trailColor = hitColor = Pal.missileYellowBack;
            splashDamage = 100f;
            splashDamageRadius = 16f;
            width = height = 16f;
            lifetime = 400;
						shrinkX = shrinkY = 0.5f;
            trailWidth = 3f;
            trailLength = 50;
            collides = collidesTiles = collidesGround = true;
            shootEffect = Fx.shootTitan;
            smokeEffect = Fx.shootSmokeTitan;
            trailEffect = Fx.none;
            hitEffect = despawnEffect = new MultiEffect(Fx.titanSmoke, Fx.titanExplosion);
            bullet.hitSound = bullet.despawnSound = Sounds.largeExplosion;
          }};
        }}
      );
    }};
    tower = new SWUnitType("tower") {{
      health = 650;
      speed = 0.25f;
      range = maxRange = 1000f;
      hitSize = 10f;
      rotateSpeed = 0.8f;
	    outlines = faceTarget = false;
	    targetAir = false;
	    squareShape = true;
	    omniMovement = false;
	    rotateMoveFirst = true;
	    aiController = MortarAI::new;

      treadFrames = 16;
      treadRects = new Rect[]{
        new Rect(-31, -21, 14, 56),
        new Rect(-13, -37, 13, 72)
      };

      constructor = TankUnit::create;

			weapons.add(
        new Weapon("sw-longer-mortar") {{
				  x = y = 0f;
				  reload = 450f;
				  recoil = 0f;
	  			recoilTime = 60f;
		  		cooldownTime = 225f;
			  	rotateSpeed = 0.4f;
				  shootY = 26f;
  				shootSound = Sounds.largeCannon;
	  			rotate = true;
		  		mirror = false;

			  	parts.add(
				  	new RegionPart("-cannon") {{
				  		moveY = -4f;
				  		outlineLayerOffset = 0f;
				  		under = true;
				  		progress = PartProgress.recoil.curve(Interp.bounceIn);
				  	}}
				  );

				  bullet = new ArtilleryBulletType(2f, 1, "missile-large") {{
				  	frontColor = Pal.missileYellow;
				  	backColor = trailColor = hitColor = Pal.missileYellowBack;
				  	splashDamage = 300f;
				  	splashDamageRadius = 16f;
				  	width = height = 18f;
				  	lifetime = 500;
				  	shrinkX = shrinkY = 0.5f;
				  	trailWidth = 3f;
				  	trailLength = 50;
				  	collides = collidesTiles = collidesGround = true;
				  	shootEffect = Fx.shootTitan;
				  	smokeEffect = Fx.shootSmokeTitan;
				  	trailEffect = Fx.none;
				  	hitEffect = despawnEffect = new MultiEffect(Fx.titanSmoke, Fx.titanExplosion);
				  	bullet.hitSound = bullet.despawnSound = Sounds.explosionbig;

            bulletInterval = 15f;
				  	intervalBullets = 2;
            intervalSpread = 20f;
            intervalRandomSpread = 0f;
				  	intervalBullet = new BasicBulletType(1f, 10) {{
              frontColor = Pal.missileYellow;
              backColor = trailColor = hitColor = Pal.missileYellowBack;
              trailWidth = 1f;
              trailLength = 10;
              homingPower = 0.4f;
              homingRange = 40f;
              lifetime = 20f;
              hitSound = despawnSound = Sounds.explosion;
				  	}};
				  }};
        }}
      );
    }};
    castle = new SWUnitType("castle") {{
      health = 1400;
      speed = 0.25f;
      range = maxRange = 1200f;
      hitSize = 14f;
      rotateSpeed = 0.7f;
	    outlines = faceTarget = false;
	    targetAir = false;
	    squareShape = true;
	    omniMovement = false;
	    rotateMoveFirst = true;
	    aiController = MortarAI::new;

      treadFrames = 16;
      treadRects = new Rect[]{
        new Rect(-34f, -20f, 14, 64),
        new Rect(-12f, -47f, 12, 95)
      };

      constructor = TankUnit::create;

      weapons.add(new Weapon("sw-longest-mortar") {{
        x = y = 0f;
        reload = 600f;
        recoil = 0f;
        recoilTime = 60f;
        cooldownTime = 225f;
        rotateSpeed = 0.3f;
        shootY = 34f;
        shootSound = Sounds.largeCannon;
        rotate = true;
        mirror = false;

        parts.add(
          new RegionPart("-cannon") {{
            moveY = -8.5f;
            outlineLayerOffset = 0f;
            under = true;
            progress = PartProgress.recoil.curve(Interp.bounceIn);
          }}
        );

        bullet = new ArtilleryBulletType(2f, 1, "missile-large") {{
          frontColor = Pal.missileYellow;
          backColor = trailColor = hitColor = Pal.missileYellowBack;
          splashDamage = 600f;
          splashDamageRadius = 16f;
          width = height = 18f;
          lifetime = 600;
          shrinkX = shrinkY = 0.5f;
          trailWidth = 3f;
          trailLength = 50;
          collides = collidesTiles = collidesGround = true;
          shootEffect = Fx.shootTitan;
          smokeEffect = Fx.shootSmokeTitan;
          trailEffect = Fx.none;
          hitEffect = despawnEffect = new MultiEffect(Fx.titanSmoke, Fx.titanExplosion);
          hitSound = despawnSound = Sounds.explosionbig;

          bulletInterval = 15f;
          intervalBullets = 2;
          intervalSpread = 20f;
          intervalRandomSpread = 0f;
          intervalBullet = new BasicBulletType(1f, 10) {{
            frontColor = Pal.missileYellow;
            backColor = trailColor = hitColor = Pal.missileYellowBack;
            trailWidth = 1f;
            trailLength = 10;
            homingPower = 0.4f;
            homingRange = 40f;
            lifetime = 20f;
            hitSound = despawnSound = Sounds.explosion;
          }};
        }};
      }});
    }};
    //endregion
    //region legs
    existence = new SWUnitType("existence") {{
      health = 250;
  		speed = 1.2f;
	  	rotateSpeed = 3f;
		  range = maxRange = 24f;

	  	constructor = MechUnit::create;

      weapons.add(new Weapon() {{
        bullet = new ExplosionBulletType(150, 80);
      }});
    }};
    remembered = new SWUnitType("remembered") {{
      health = 600;
	  	hitSize = 9f;
	  	speed = 0.7f;
	  	rotateSpeed = 3f;
	  	range = maxRange = 80f;

	  	constructor = LegsUnit::create;

			weapons.add(
				new Weapon("sw-bottom-gun") {{
          x = 0f;
          y = 5.75f;
          reload = 120f;
          mirror = false;
          layerOffset = -0.01f;

          bullet = new LaserBulletType(25) {{
            length = 100f;
          }};
        }},
				new Weapon("sw-top-gun") {{
          x = 4.75f;
          y = 2.25f;
          reload = 60f;

          bullet = new MissileBulletType(2, 14) {{
            frontColor = Color.white;
            backColor = hitColor = trailColor = Pal.lancerLaser;
            lifetime = 40f;
            width = height = 10f;
          }};
        }}
			);
    }};
    presence = new SWUnitType("presence") {{
      health = 1230;
	  	hitSize = 11f;
	  	speed = 0.7f;
	  	rotateSpeed = 3f;
	  	range = maxRange = 120f;

			legCount = 6;
			legLength = 16f;
			legExtension = 2f;

	  	constructor = LegsUnit::create;

      weapons.add(
	      new Weapon("sw-presence-cannon") {{
		      x = 7.25f;
		      y = 4f;
		      range = 100f;
		      reload = 60f;
		      rotate = false;
		      layerOffset = -0.01f;

		      shootSound = Sounds.artillery;
          shoot = new ShootBarrel() {{
						shots = 2;
            barrels = new float[] {
              -1.5f, 1.5f, 0f,
              2.5f, -2.5f, 0f
            };
          }};
		      bullet = new BasicBulletType(4, 20) {{
			      lifetime = 25f;
			      width = height = 12f;
		      }};
	      }},
        new Weapon("sw-laser-gun") {{
          x = 7.5f;
          y = -4.25f;
          reload = 120f;
          rotate = true;
          alternate = false;

          shootSound = Sounds.laser;
          bullet = new LaserBulletType(20) {{
            length = 120f;
          }};
        }}
      );
    }};
    protMask = new SWUnitType("prot-mask") {{
      health = 2300;
      speed = 2f;
      rotateSpeed = 3f;
      range = maxRange = 0f;

      engineOffset = 2f;

      flying = lowAltitude = hidden = true;
      playerControllable = targetable = false;

      controller = u -> new ShieldAI();
      constructor = UnitTetherUnit::create;
    }};
    prot = new SWUnitType("prot") {{
      health = 2300;
      hitSize = 16f;
      speed = 1f;
      rotateSpeed = 1f;
      range = maxRange = 160f;

      shieldSeparateRadius = 24f;
      shieldStartAng = -54f;
      shieldEndAng = 288f;
      shieldShootingStartAng = 40f;
      shieldShootingEndAng = 100f;

      shieldUnit = protMask;

      legCount = 6;
      legGroupSize = 3;
      legLength = 30f;
      legBaseOffset = 10f;
			legExtension = 15f;

      constructor = UnitLegsShielded::create;

      weapons.add(new Weapon("sw-prot-weapon") {{
				x = 12.75f;
				y = 0;
				shootY = 11f;
				reload = 60f;
				recoil = 2f;

				top = false;

				shootSound = Sounds.artillery;

				bullet = new LaserBulletType(250) {{
					length = 160f;
					shootEffect = Fx.shockwave;
					colors = new Color[]{Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
				}};
			}});
    }};
    //endregion
	  //region copter
    fly = new SWUnitType("fly") {{
      health = 250;
      speed = 2f;
      fallSpeed = 0.005f;
      rotateSpeed = 8f;
      range = maxRange = 120f;

      engineSize = 0f;

      flying = lowAltitude = true;

      loopSound = Sounds.cutter;
      constructor = UnitCopter::create;

      rotors.add(
        new UnitRotor("-rotor", true) {{
          speed = 10f;
        }}
      );

      weapons.add(
        new Weapon("sw-small-launcher") {{
          x = 3.75f;
          y = 3f;
          reload = 60;
          layerOffset = -0.01f;
          shootSound = Sounds.missileLarge;

          shoot = new ShootSpread(3, 5f) {{
            shotDelay = 5f;
          }};

          bullet = new BasicBulletType(2f, 5, "missile") {{
            frontColor = Pal.missileYellow;
            backColor = Pal.missileYellowBack;
            trailLength = 5;
            width = 7f;
            height = 9f;
            lifetime = 60f;
          }};
        }}
      );
    }};
	  spin = new SWUnitType("spin") {{
		  health = 560;
		  hitSize = 10f;
		  speed = 2f;
      fallSpeed = 0.005f;
		  rotateSpeed = 4f;
		  range = maxRange = 96f;

		  engineSize = 0f;

		  flying = lowAltitude = true;

      loopSound = Sounds.cutter;
		  constructor = UnitCopter::create;

      rotors.add(
        new UnitRotor("sw-bottom-rotor", false) {{
          speed = 10f;
          layerOffset = -0.02f;
          drawTop = false;
        }}
      );

		  weapons.add(
        new Weapon("sw-small-artillery") {{
          x = 0f;
          y = -3f;
          reload = 45;
          mirror = false;
          rotate = true;
          shootSound = Sounds.malignShoot;

          bullet = new BasicBulletType(3f, 20, "mine-bullet") {{
            frontColor = Pal.missileYellow;
            backColor = Pal.missileYellowBack;
            trailLength = 10;
            width = 8f;
            height = 12f;
            recoil = 1f;
            lifetime = 32f;
          }};
        }},
        new Weapon("sw-small-launcher") {{
          x = 6.5f;
          y = 0.75f;
          reload = 30;
          layerOffset = -0.01f;
          shootSound = Sounds.shootAlt;
          alternate = false;

          bullet = new BasicBulletType(2f, 12f, "missile-large") {{
            frontColor = Pal.missileYellow;
            backColor = Pal.missileYellowBack;
            trailLength = 5;
            width = 7f;
            height = 9f;
            homingPower = 0.012f;
            lifetime = 48f;
          }};
        }}
      );
	  }};
    gyro = new SWUnitType("gyro") {{
      health = 1300;
      hitSize = 14f;
      speed = 1.5f;
      fallSpeed = 0.005f;
      rotateSpeed = 4f;
      range = maxRange = 200f;

      engineSize = 0f;

      flying = lowAltitude = true;

      loopSound = Sounds.cutter;
      constructor = UnitCopter::create;

      rotors.add(
        new UnitRotor("-rotor", true) {{
          speed = 10f;
        }},
        new UnitRotor("sw-bottom-rotor", false) {{
          x = 10f;
          speed = 10f;
          layerOffset = -0.02f;
          mirrored = true;
          drawTop = false;
        }}
      );

      weapons.add(
        new Weapon("sw-small-artillery") {{
          x = 4.5f;
          y = -2.75f;
          reload = 60;
          rotate = true;
          shootSound = Sounds.missileSmall;

          bullet = new BasicBulletType(2f, 20, "missile-large") {{
            frontColor = Pal.missileYellow;
            backColor = Pal.missileYellowBack;
            width = height = 8f;
            shrinkY = 0f;
            trailLength  = 15;
            lifetime = 100f;
            homingPower = 0.02f;
          }};
        }},
        new Weapon("sw-medium-launcher") {{
          x = 7.75f;
          y = 5.5f;
          reload = 30;
          layerOffset = -0.01f;
          shootSound = Sounds.shootBig;

          bullet = new BasicBulletType(4f, 25) {{
            frontColor = Pal.missileYellow;
            backColor = Pal.missileYellowBack;
            width = height = 12f;
            lifetime = 25f;
          }};
        }}
      );
    }};
		//endregion
	  //region shields
	  mesoShield = new SWUnitType("meso-shield") {{
		  health = 100;
		  speed = 2f;
		  rotateSpeed = 3f;
		  range = maxRange = 0f;
			lightRadius = 0;
			lightColor = engineColor = Pal.heal;

		  engineOffset = 2f;

		  flying = lowAltitude = hidden = true;
		  playerControllable = drawCell = targetable = isEnemy = useUnitCap = false;

		  controller = u -> new ShieldAI();
		  constructor = UnitTetherUnit::create;
	  }};
	  paleoShield = new SWUnitType("paleo-shield") {{
		  health = 100;
		  speed = 2f;
		  rotateSpeed = 3f;
		  range = maxRange = 0f;
		  engineSize = 3.5f;
		  engineOffset = 4f;
		  lightRadius = 0;
		  lightColor = engineColor = Pal.heal;

		  flying = lowAltitude = hidden = true;
		  playerControllable = drawCell = targetable = isEnemy = useUnitCap = false;

		  controller = u -> new ShieldAI();
		  constructor = UnitTetherUnit::create;
	  }};
	  cenoShield = new SWUnitType("ceno-shield") {{
		  health = 100;
		  speed = 2f;
		  rotateSpeed = 3f;
		  range = maxRange = 0f;
		  engineSize = 4f;
		  engineOffset = 0f;
		  lightRadius = 0;
		  lightColor = engineColor = Pal.heal;

		  flying = lowAltitude = hidden = true;
		  playerControllable = drawCell = targetable = isEnemy = useUnitCap = false;

		  controller = u -> new ShieldAI();
		  constructor = UnitTetherUnit::create;
	  }};

	  meso = new SWUnitType("meso") {{
		  health = 200f;
		  speed = 0.7f;
		  rotateSpeed = 2f;
		  range = maxRange = 80f;
		  lightOpacity = 0.3f;
		  lightRadius = 40;
		  lightColor = Pal.heal;
		  outlines = false;

		  shields = 5;

		  shieldUnit = mesoShield;
		  shieldSeparateRadius = 16f;
		  shieldStartAng = -144;
		  shieldEndAng = 144;

		  legBaseOffset = 2f;
		  legCount = 6;
		  legExtension = 3f;
		  legGroupSize = 3;
		  legLength = 8f;
		  lockLegBase = true;
		  legContinuousMove = true;

		  constructor = UnitLegsShielded::create;

		  parts.add(new RegionPart("-cannon") {{
			  moveY = -0.5f;
			  heatColor = Pal.heal;
			  progress = PartProgress.recoil;
		  }});

		  weapons.add(new Weapon() {{
			  x = y = 0f;
			  reload = 60f;
			  recoilTime = 10f;
			  mirror = false;
			  shootSound = Sounds.lasershoot;

			  shoot = new ShootAlternate() {{
				  shots = 2;
			  }};

			  bullet = new BasicBulletType(2f, 9) {{
				  lifetime = 40;
				  trailLength = 10;
				  trailWidth = 1f;
				  frontColor = Color.white;
				  backColor = trailColor = lightColor = Pal.heal;
				  hitEffect = despawnEffect = Fx.hitLaser;
			  }};
		  }});
	  }};
	  paleo = new SWUnitType("paleo") {{
		  health = 600f;
		  hitSize = 8f;
		  speed = 0.6f;
		  rotateSpeed = 2f;
		  range = maxRange = 120f;
		  lightOpacity = 0.3f;
		  lightRadius = 60;
		  lightColor = Pal.heal;
		  outlineLayerOffset = -0.002f;
		  outlines = false;

		  shields = 3;

		  shieldUnit = paleoShield;
		  shieldSeparateRadius = 16f;
		  shieldStartAng = -120;
		  shieldEndAng = 120;

		  legBaseOffset = 2f;
		  legCount = 4;
		  legExtension = 4;
		  legGroupSize = 2;
		  legLength = 16f;
		  legStraightness = 0.3f;
		  legContinuousMove = true;
		  lockLegBase = true;

		  constructor = UnitLegsShielded::create;

		  parts.add(
			  new RegionPart("-cannon-back") {{
				  x = -4.5f;
				  y = -4.25f;
				  moveX = moveY = 1f;
				  layerOffset = -0.001f;
				  progress = PartProgress.warmup.mul(PartProgress.recoil.inv());
				  mirror = under = true;
			  }},
			  new RegionPart("-cannon-top") {{
				  x = -3.5f;
				  y = 4.5f;
				  moveX = 2f;
				  moveY = 0.5f;
				  layerOffset = -0.001f;
				  progress = PartProgress.warmup.mul(PartProgress.recoil.inv().curve(Interp.circleIn));
				  mirror = under = true;
			  }}
		  );

		  weapons.add(new Weapon() {{
			  x = y = 0;
			  reload = 40;
			  recoilTime = 10;
			  minWarmup = 0.9f;
			  shootY = 8f;
			  mirror = false;
			  shootSound = Sounds.release;

			  shoot = new ShootHelix() {{
				  shots = 2;
			  }};

			  bullet = new BasicBulletType(4f, 15) {{
				  lifetime = 30;
				  width = 8f;
				  height = 10f;
				  trailLength = 10;
				  trailWidth = 1f;
				  frontColor = Color.white;
				  backColor = trailColor = lightColor = Pal.heal;
				  hitEffect = despawnEffect = smokeEffect = Fx.hitLaser;
				  shootEffect = Fx.none;
			  }};
		  }});
	  }};
	  ceno = new SWUnitType("ceno") {{
		  health = 1200f;
		  hitSize = 10f;
		  speed = 0.5f;
		  rotateSpeed = 2f;
		  range = maxRange = 200f;
		  lightOpacity = 0.3f;
		  lightRadius = 80;
		  lightColor = Pal.heal;
		  outlineLayerOffset = -0.002f;
		  outlines = false;

		  shields = 3;

		  shieldUnit = cenoShield;
		  shieldSeparateRadius = 26f;
		  shieldStartAng = -120;
		  shieldEndAng = 120;

		  legBaseOffset = 2f;
		  legCount = 8;
		  legExtension = 4;
		  legGroupSize = 4;
		  legLength = 16f;
		  legStraightness = 0.2f;
		  legContinuousMove = true;
		  lockLegBase = true;

		  constructor = UnitLegsShielded::create;

		  parts.add(
			  new RegionPart("-big-cannon") {{
				  y = 3.5f;
					moveY = -2f;
				  layerOffset = -0.001f;
					progress = PartProgress.recoil;
				  under = true;
			  }},
			  new RegionPart("-side-cannon") {{
				  x = -4.75f;
				  y = 2.75f;
				  moveX = 3.25f;
				  moveY = 2.5f;
				  layerOffset = -0.001f;
					moves.add(new PartMove(PartProgress.reload, -1.5f, -1, 0, 0, 0));
				  mirror = under = true;
			  }}
		  );

		  weapons.add(
			  new Weapon() {{
				  x = y = 0;
				  reload = 80;
				  minWarmup = 0.9f;
				  shootY = 8f;
				  mirror = false;
				  shootSound = Sounds.release;

					shoot = new ShootPattern() {{
						shots = 2;
						shotDelay = 40;
					}};

				  bullet = new LaserBulletType(50) {{
					  width = 16f;
					  length = 180f;
						colors = new Color[]{Pal.heal, Color.white};
					  hitEffect = despawnEffect = smokeEffect = Fx.hitLaser;
					  shootEffect = Fx.none;
				  }};
			  }},
			  new Weapon() {{
				  x = y = 0;
				  reload = 90;
				  recoilTime = 10;
				  minWarmup = 0.9f;
				  shootY = 0f;
				  mirror = false;
				  shootSound = Sounds.plasmaboom;

					shoot = new ShootAlternate(12) {{
						shots = 2;
					}};

				  bullet = new BasicBulletType(2f, 15) {{
					  lifetime = 100;
					  width = 8f;
					  height = 10f;
					  trailLength = 10;
					  trailWidth = 1f;
					  frontColor = Color.white;
					  backColor = trailColor = lightColor = Pal.heal;
					  hitEffect = despawnEffect = smokeEffect = Fx.hitLaser;
					  shootEffect = Fx.none;
				  }};
			  }}
		  );
	  }};
	  //endregion

    lambda = new UnitType("lambda") {{
			health = 300;
			speed = 1;
			hitSize = 8f;
			range = maxRange = 100;
			engineSize = engineOffset = shadowElevationScl = 0f;
			fallSpeed = buildSpeed = 1f;
			mineTier = 1;
			mineSpeed = 5;
	    coreUnitDock = lowAltitude = true;
			//shocking
			flying = true;

			legCount = 8;
			legGroupSize = 2;
			legForwardScl = 0f;
	    legBaseOffset = 4f;
	    legExtension = -2f;
	    legLength = 14f;
			legContinuousMove = true;

			constructor = UnitLegsIntangible::create;

			weapons.add(
				new Weapon("sw-lambda-weapon") {{
					x = 4.75f;
					y = 2.5f;
					reload = 60;
					layerOffset = -0.001f;
					shootSound = Sounds.shootAlt;
					shoot = new ShootPattern() {{
						shots = 3;
						shotDelay = 5;
					}};

					bullet = new BasicBulletType(2f, 7) {{
						lifetime = 50f;
						trailLength = 10;
						trailWidth = 1f;
					}};
				}}
			);
    }};
  }
}
