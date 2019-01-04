package usa.cactuspuppy.PVNBot.utils;

public enum MainData {
    DIR("/mainBot"),
    CMD_PREFIX( "cmdPrefix.dat"),
    GUILD_ID("mainGuild.dat");

    String value;
    MainData(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
