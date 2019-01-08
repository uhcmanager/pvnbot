package usa.cactuspuppy.PVNBot.mainbot.mirror.entity;

import lombok.Getter;
import usa.cactuspuppy.PVNBot.mainbot.mirror.MirrorManager;

public abstract class MirrorEntity {
    @Getter private long internalID;

    public MirrorEntity() {
        internalID = MirrorManager.getNewID();
    }

    public static void delete(MirrorEntity e) {
        MirrorManager.untrackEntity(e.internalID);
    }
}
