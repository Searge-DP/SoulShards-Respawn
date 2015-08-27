package tehnut.soulshards.enumeration;

import lombok.Getter;
import net.minecraft.world.World;
import tehnut.soulshards.ConfigHandler;
import tehnut.soulshards.util.Bound;

import java.util.Locale;

@Getter
public enum EnumTier {

    ZERO(Bound.of(0, 0)),
    ONE(Bound.of(ConfigHandler.killRequirement[0], ConfigHandler.killRequirement[1] - 1)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    TWO(Bound.of(ConfigHandler.killRequirement[1], ConfigHandler.killRequirement[2] - 1)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    THREE(Bound.of(ConfigHandler.killRequirement[2], ConfigHandler.killRequirement[3] - 1)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    FOUR(Bound.of(ConfigHandler.killRequirement[3], ConfigHandler.killRequirement[4] - 1)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    FIVE(Bound.of(ConfigHandler.killRequirement[4], ConfigHandler.killRequirement[4])) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    DEFAULT(Bound.of(-1, -1));

    Bound<Integer> bound;

    EnumTier(Bound<Integer> bound) {
        this.bound = bound;
    }

    void trySpawn(World world, int x, int y, int z) {

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

    public static EnumTier prev(EnumTier oldTier) {
        EnumTier ret = ZERO;

        if (oldTier.ordinal() > 0)
            ret = EnumTier.values()[oldTier.ordinal() - 1];

        return ret;
    }

    public static EnumTier next(EnumTier oldTier) {
        EnumTier ret = FIVE;

        if (oldTier.ordinal() < FIVE.ordinal())
            ret = EnumTier.values()[oldTier.ordinal() + 1];

        return ret;
    }
}
