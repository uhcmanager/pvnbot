package usa.cactuspuppy.PVNBot.mainbot.mirror;

import lombok.NoArgsConstructor;
import usa.cactuspuppy.PVNBot.mainbot.mirror.entity.MirrorEntity;
import usa.cactuspuppy.PVNBot.utils.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MirrorManager {
    private static final int MAX_GEN_ATTEMPTS = 10000;
    private static Map<Long, MirrorEntity> entityMap = new HashMap<>();

    public static MirrorEntity getEntity(long id) {
        if (id == -1) return null;
        return entityMap.get(id);
    }

    public static void trackEntity(long id, MirrorEntity e) {
        if (id == -1) return;
        entityMap.put(id, e);
    }

    public static void untrackEntity(long id) {
        entityMap.remove(id);
    }

    public static void untrackAll() {
        entityMap.clear();
    }

    public static long getNewID() {
        return new IDGenerator().get();
    }

    public static long getNewID(IDGenerator generator) {
        return generator.get();
    }

    @NoArgsConstructor
    public static class IDGenerator {
        private Random rng = new Random();

        public IDGenerator(Random random) {
            rng = random;
        }

        /**
         * Gets a new unique ID
         * @return Long ID which is not currently being tracked.
         */
        public long get() {
            for (int i = 0; i < MAX_GEN_ATTEMPTS; i++) {
                long candidate = rng.nextLong();
                if (!entityMap.keySet().contains(candidate)) return candidate;
            }
            Logger.logWarning(this.getClass(), "Exceeded max generation attempts while generating new ID");
            return -1;
        }
    }
}
