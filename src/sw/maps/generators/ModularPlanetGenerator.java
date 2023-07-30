package sw.maps.generators;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.content.*;
import mindustry.maps.generators.*;
import mindustry.type.*;
import mindustry.world.*;

public class ModularPlanetGenerator extends PlanetGenerator {
	public float minHeight = 0f;
	public Block defaultBlock = Blocks.grass;
	public Seq<Noise3DSettings> heights = new Seq<>();
	public Seq<ColorPatch> colors = new Seq<>();

	public class Noise3DSettings {
		public Vec3 offset = Vec3.Zero.cpy();
		public float min = 0f, max = 1f, mag = 1f, heightOffset = 0;
		public double oct = 7, per = 0.5f, scl = 1f/3f;

		public float noise(Vec3 pos) {
			Tmp.v31.set(pos).add(offset);
			return Mathf.clamp(Simplex.noise3d(seed, oct, per, scl, Tmp.v31.x, Tmp.v31.y, Tmp.v31.z) * mag + heightOffset, min, max);
		}
	}
	public class ColorPatch {
		public Block block = Blocks.grass;
		public Noise3DSettings noise;
		public float minT = 0f, maxT = 1f;

		public boolean canColor(Vec3 pos, float height) {
			float noise1 = noise.noise(pos);
			return noise1 >= minT && noise1 <= maxT && noise1 >= height;
		}
	}

	float height(Vec3 pos) {
		return heights.sumf(s -> s.noise(pos))/heights.size;
	}
	@Override public float getHeight(Vec3 p) {
		return Math.max(minHeight, height(p));
	}

	@Override
	public Color getColor(Vec3 p) {
		return getBlock(p).mapColor;
	}
	
	public Block getBlock(Vec3 p) {
		Block out = defaultBlock;
		for (ColorPatch color : colors) {
			if (color.canColor(p, getHeight(p))) out = color.block;
		}
		return out;
	}

	@Override
	public void generateSector(Sector sector) {
		sector.generateEnemyBase = false;
	}
}
