package tehnut.soulshards.enumeration;

import lombok.Getter;
import net.minecraft.world.World;
import tehnut.soulshards.util.Bound;

import java.util.Locale;

@Getter
public enum EnumTier {

    ZERO(Bound.of(0, 0)),
    ONE(Bound.of(64, 127)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    TWO(Bound.of(128, 255)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    THREE(Bound.of(256, 511)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    FOUR(Bound.of(512, 1023)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    FIVE(Bound.of(1024, 1024)) {
        @Override
        void trySpawn(World world, int x, int y, int z) {

        }
    },
    DEFAULT(Bound.of(0, 0));

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
