package usa.cactuspuppy.PVNBot.utils;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import usa.cactuspuppy.PVNBot.Main;
import usa.cactuspuppy.PVNBot.utils.discord.MainGuild;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public final class Bridge {
    /**
     * Returns the Discord ID of a given Minecraft username.
     * @param mc_name name to find
     * @return ID of the Discord user
     */
    public static long mcToDiscord(String mc_name) {
        JDA jda = Main.getMainJDA();
        Guild main =  jda.getGuildById(Main.getMainGuildID());
        if (main == null) {
            Main.getLogger().warning("Could not find guild with ID: " + Main.getMainGuildID());
            return -1;
        }
        List<Member> candidates = main.getMembersByEffectiveName(mc_name, false);
        if (candidates.isEmpty()) {
            Main.getLogger().info("Could not find user with effective name: " + mc_name);
            return -1;
        }
        return candidates.get(0).getUser().getIdLong();
    }

    /**
     * Returns the Discord ID of a given Minecraft player.
     * @param player Minecraft player
     * @return ID of the Discord user
     */
    public static long mcToDiscord(OfflinePlayer player) {
        return mcToDiscord(player.getName());
    }

    /**
     * Returns the Discord ID of a given Minecraft UUID.
     * @param uuid UUID of Minecraft player
     * @return ID of the Discord user
     */
    public static long mcToDiscord(UUID uuid) {
        return mcToDiscord(getNameFromUUID(uuid));
    }

    /**
     * Gets the Minecraft UUID of a Discord user.
     * @param id Discord user ID
     * @return Minecraft UUID
     */
    public static UUID discordToMC(long id) {
        return getMCUUID(MainGuild.get().getMemberById(id).getEffectiveName());
    }

    /**
     * Gets the Minecraft UUID of a Discord user.
     * @param member Discord member to get MC UUID of
     * @return Minecraft UUID
     */
    public static UUID discordToMC(Member member) {
        return discordToMC(member.getUser().getIdLong());
    }

    /**
     * Gets the Minecraft UUID of a Discord user.
     * @param effectiveName Effective name of the user
     * @return Minecraft UUID
     */
    public static UUID discordToMC(String effectiveName) { return getMCUUID(effectiveName); }

    public static UUID getMCUUID(String username) {
        OfflinePlayer p = Bukkit.getPlayer(username);
        if (p != null) {
            return p.getUniqueId();
        } else {
            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
                StringBuilder responseBuilder = new StringBuilder();

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                int responseCode = connection.getResponseCode();
                String responseMessage = connection.getResponseMessage();
                if (responseCode != 200) {
                    Main.getLogger().warning("Bad response code while querying Mojang API.\n" +
                            "Code: " + responseCode + "\n" +
                            "Message: " + responseMessage + "\n" +
                            "Queried Name: " + username);
                    return null;
                }
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line = reader.readLine();
                    while (line != null) {
                        responseBuilder.append(line);
                        line = reader.readLine();
                    }
                }
                JSONObject response = (JSONObject) JSONValue.parseWithException(responseBuilder.toString());
                String uuidString = (String) response.get("id");
                uuidString = uuidString.replaceAll("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5");
                return UUID.fromString(uuidString);
            } catch (MalformedURLException e) {
                Main.getLogger().warning("Mojang API URL invalid!");
            } catch (IOException e) {
                Main.getLogger().warning("Issue retrieving JSON payload from Mojang API");
            } catch (ParseException e) {
                Main.getLogger().warning("Issue parsing JSON payload");
            }
            return null;
        }
    }

    public static String getNameFromUUID(UUID u) {
        OfflinePlayer p = Bukkit.getOfflinePlayer(u);
        if (p != null) return p.getName();
        //TODO: Query Mojang API for name
        return null;
    }
}
