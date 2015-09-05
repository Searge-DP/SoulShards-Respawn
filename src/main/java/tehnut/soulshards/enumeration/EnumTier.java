package tehnut.soulshards.enumeration;

import lombok.Getter;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import tehnut.soulshards.ConfigHandler;
import tehnut.soulshards.util.Bound;

import java.util.Locale;

@Getter
public enum EnumTier {

    ZERO(Bound.of(0, ConfigHandler.killRequirement[0] - 1)),
    ONE(Bound.of(ConfigHandler.killRequirement[0], ConfigHandler.killRequirement[1] - 1)) {
        @Override
        public void doSpawn(World world, EntityLiving entity, int x, int y, int z) {
            for (int i = 0; i < ConfigHandler.maxSpawns[this.ordinal()]; i++) {
                double xCoord = x + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0D;
                double yCoord = y + world.rand.nextInt(3) - 1;
                double zCoord = z + (world.rand.nextDouble() - world.rand.nextDouble()) * 4.0D;

                entity.setLocationAndAngles(xCoord, yCoord, zCoord, world.rand.nextFloat() * 360.0F, 0.0F);
                world.spawnEntityInWorld(entity);
            }
        }
    },
    TWO(Bound.of(ConfigHandler.killRequirement[1], ConfigHandler.killRequirement[2] - 1)) {
        @Override
        public void doSpawn(World world, EntityLiving entity, int x, int y, int z) {

        }
    },
    THREE(Bound.of(ConfigHandler.killRequirement[2], ConfigHandler.killRequirement[3] - 1)) {
        @Override
        public void doSpawn(World world, EntityLiving entity, int x, int y, int z) {

        }
    },
    FOUR(Bound.of(ConfigHandler.killRequirement[3], ConfigHandler.killRequirement[4] - 1)) {
        @Override
        public void doSpawn(World world, EntityLiving entity, int x, int y, int z) {

        }
    },
    FIVE(Bound.of(ConfigHandler.killRequirement[4], ConfigHandler.killRequirement[4])) {
        @Override
        public void doSpawn(World world, EntityLiving entity, int x, int y, int z) {

        }
    },
    UNBOUND(Bound.of(-1, -1));

    Bound<Integer> bound;

    EnumTier(Bound<Integer> bound) {
        this.bound = bound;
    }

    public void doSpawn(World world, EntityLiving entity, int x, int y, int z) {

    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    public int getNumber() {
        return ordinal() - 1;
    }

    public String toCasedString() {
        return name().charAt(0) + name().substring(1).toLowerCase(Locale.ENGLISH);
    }

    public EnumTier prev() {
        EnumTier ret = ZERO;

        if (ordinal() > ZERO.ordinal())
            ret = EnumTier.values()[ordinal() - 1];

        return ret;
    }

    public EnumTier next() {
        EnumTier ret = FIVE;

        if (ordinal() < FIVE.ordinal())
            ret = EnumTier.values()[ordinal() + 1];

        return ret;
    }
}
