package usa.cactuspuppy.PVNBot.constants.main;

/**
 * Saves all data file name constants
 */
public enum MainData {
    DIR("/mainBot"),
    CMD_PREFIX( "cmdPrefix.dat"),
    GUILD_ID("mainGuild.dat"),
    PVN_INSTANCE("pvnInstanceID.dat");

    String value;
    MainData(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
