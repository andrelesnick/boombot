package val.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.sasl.AuthenticationException;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Messenger extends ListenerAdapter {
    HashMap<String, String> help;
    WebHelper wh;
    static String[] colors; //used to select embed colors
    HashMap<String,String> resources;
    public Messenger() {
        colors = new String[]{"#4A90E2","#D0021B","#BD10E0","#5ECA14","#F5A623","#FF0AD5"};
        help = ConfigHelper.initHelp();
        wh = new WebHelper();
        resources = new HashMap<String,String>();
        resources.put("iron 1","https://cdn.discordapp.com/attachments/798400435018661930/798400473547276288/iron1.png");
        resources.put("iron 2","https://cdn.discordapp.com/attachments/798400435018661930/798400476739141662/iron2.png");
        resources.put("iron 3","https://cdn.discordapp.net/attachments/798400435018661930/798400479360843866/iron3.png");
        resources.put("bronze 1","https://cdn.discordapp.net/attachments/798400435018661930/798400986694811678/bronze1.png");
        resources.put("bronze 2","https://cdn.discordapp.net/attachments/798400435018661930/798400980307148800/bronze2.png");
        resources.put("bronze 3","https://cdn.discordapp.com/attachments/798400435018661930/798400983779770388/bronze3.png");
        resources.put("silver 1","https://cdn.discordapp.com/attachments/798400435018661930/798401594877542470/silver1.png");
        resources.put("silver 2","https://cdn.discordapp.com/attachments/798400435018661930/798401591233216532/silver2.png");
        resources.put("silver 3","https://cdn.discordapp.com/attachments/798400435018661930/798401593467863070/silver3.png");
        resources.put("gold 1","https://cdn.discordapp.com/attachments/798400435018661930/798408044467585094/gold1.png");
        resources.put("gold 2","https://cdn.discordapp.com/attachments/798400435018661930/798408046971584522/gold2.png");
        resources.put("gold 3","https://cdn.discordapp.com/attachments/798400435018661930/798408049022468096/gold3.png");
        resources.put("platinum 1","https://cdn.discordapp.com/attachments/798400435018661930/798408137614819338/platinum1.png");
        resources.put("platinum 2","https://cdn.discordapp.com/attachments/798400435018661930/798408134888390686/platinum2.png");
        resources.put("platinum 3","https://cdn.discordapp.com/attachments/798400435018661930/798408136255471626/platinum3.png");
        resources.put("diamond 1","https://cdn.discordapp.com/attachments/798400435018661930/798408319835570196/diamond1.png");
        resources.put("diamond 2","https://cdn.discordapp.com/attachments/798400435018661930/798408316686565427/diamond2.png");
        resources.put("diamond 3","https://cdn.discordapp.com/attachments/798400435018661930/798408318354980864/diamond3.png");
        resources.put("immortal 1","https://cdn.discordapp.com/attachments/798400435018661930/798408398865170452/immortal1.png");
        resources.put("immortal 2","https://cdn.discordapp.com/attachments/798400435018661930/798408395941740554/immortal2.png");
        resources.put("immortal 3","https://cdn.discordapp.com/attachments/798400435018661930/798408397400703026/immortal3.png");
        resources.put("immortal","https://cdn.discordapp.com/attachments/798400435018661930/798408397400703026/immortal3.png");
        resources.put("radiant","https://cdn.discordapp.com/attachments/798400435018661930/798408564548567090/radiant.png");
        resources.put("unranked","https://cdn.discordapp.com/attachments/798400435018661930/798408989029302302/chicken.png");
        resources.put("boombot","https://cdn.discordapp.com/attachments/798400435018661930/798419122043093003/boombot.png");

        resources.put("boombotcircle", "<:boombotcircle:799139345621188628>");
        resources.put("Omen","<:omen:798399553635745793>");
        resources.put("Breach","<:breach:798399553635745833>");
        resources.put("Killjoy","<:killjoy:798399553837334608>");
        resources.put("Brimstone","<:brimstone:798399553798799370>");
        resources.put("Phoenix","<:phoenix:798399554277343253>");
        resources.put("Jett","<:jett:798399553790410772>");
        resources.put("Viper","<:viper:798399553945731112>");
        resources.put("Reyna","<:reyna:798399553794867231>");
        resources.put("Cypher","<:cypher:798399553711636521>");
        resources.put("Skye","<:skye:798399554243395625>");
        resources.put("Sage","<:sage:798399553941274676>");
        resources.put("Raze","<:raze:798399553463779349>");
        resources.put("Sova","<:sova:798399553950449684>");
        resources.put("Yoru", "<:yoru:799127818122297354>");

    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();     //This is the MessageChannel that the message was sent to.
        String msg = message.getContentRaw();
        Guild guild = null;
        boolean isGuild = false;
        boolean bot = author.isBot();                    //This boolean is useful to determine if the User that
        // sent the Message is a BOT or not!
        if (channel.getType().equals(ChannelType.TEXT)) {
            isGuild = true;
            guild = event.getGuild();
        }

        if (isGuild && !bot) {
            //simple output for all messages
            System.out.println("[" + guild.getName() + "] " + "Name: " + author.getName() + " | " + channel.getName() + " (" + channel.getId() + ")\nID: " + author.getId() + "\n   " + message.getContentDisplay() + "\n");
        }
        //sets pref to default value unless server has its own
        String pref = ConfigHelper.getPref(null);
        if (isGuild) {
            pref = ConfigHelper.getPref(guild.getId());
        }

        if (msg.startsWith(pref+"link")) {
            String[] split = msg.split(" ");
            if (split.length != 2 || !WebHelper.isValidID(split[1])) {
                channel.sendMessage("Error linking profiles.\nUsage: link [Riot ID]").queue();
            } else {
                ConfigHelper.editUser(author.getId(), split[1]);
                ConfigHelper.editItem("discord,riot", author.getId(), split[1]);//add user, add to config
                channel.sendMessage("Successfully linked your Discord profile to **" + split[1]+"**!").queue();
            }
        }
        if (msg.startsWith(pref + "pref")) {
            String[] split = msg.split(" ");
            if (split.length != 2) {
                channel.sendMessage("Error: prefix not specified or it has a space").queue();
            }
            else if (split[1].equals(pref)) {
                channel.sendMessage("Error: you already have this prefix").queue();
            }
            else if (!isGuild) {
                channel.sendMessage("Sorry, you can't change your prefix in DMs :(").queue();
            }
            else {
                ConfigHelper.editItem("server,pref", guild.getId(), split[1]);
                ConfigHelper.initializeVars();
                pref = ConfigHelper.getPref(guild.getId());
                channel.sendMessage("Prefix successfully changed to '"+pref+"'").queue();
            }
        }

        if (msg.startsWith(pref+"help")) {
            if (msg.length()<=6) {
                channel.sendMessage("Here is a list of commands.\nType \""+pref+"help [command] for more details.").queue();
                String mssage = "```";
                for (String s: help.keySet()) {
                    mssage += "\n"+s;
                }
                mssage += "\n```";
                channel.sendMessage(mssage).queue();
            }
            else if (msg.length() > 6) {
                String [] split = msg.split(" ");
                boolean valid = false; //valid parameter
                for (String s: help.keySet()) {
                    if (split[1].contains(s)) {
                        channel.sendMessage(help.get(split[1])).queue();
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    channel.sendMessage("Error: command not found.").queue();
                }
            }
        }
        if (msg.startsWith(pref+"profile")) {
            String user = ConfigHelper.getUser(author.getId());
            if (user == null) {
                user = "Error: Riot ID not found. Please link one by typing 'link [Riot ID]'";
            }
            channel.sendMessage(user).queue();
        }
        if (msg.startsWith(pref+"stats")) {
            int idStart = msg.indexOf(" ") + 1;
            String profile = null;
            if (idStart == 0) {
                if (ConfigHelper.getUser(author.getId()) == null) {
                    channel.sendMessage("Error: Riot ID not found. Please link one by typing 'link [Riot ID]'").queue();
                }
                else {
                    profile = ConfigHelper.getUser(author.getId());
                }
            }
            else if (idStart > 0 && WebHelper.isValidID(msg.substring(idStart))) {
                profile = msg.substring(idStart);
            }
            else {
                channel.sendMessage("Error: Incorrect usage. Perhaps the ID is wrong?").queue();
            }
            if (profile != null) {
                try {
                    channel.sendMessage("Fetching stats for `" + profile + "`...").queue();
                    HashMap<String,String> stats = WebHelper.getStats(profile);
//                    String str = "```\n";
//                    for (String s: stats.keySet()) {
//                        str += s + ": " + stats.get(s) + "\n";
//                    }
//                    str += "```";
                    channel.sendMessage(statsMenu(stats, profile).build()).queue();
                }
                catch (AuthenticationException e) {
                    String formattedID = profile.replace("#", "%23").replace(" ", "%20");
                    channel.sendMessage("<:cyphercam:799155266184151110>").queue();
                    channel.sendMessage("Error: Profile is private. Please visit https://tracker.gg/valorant/profile/riot/" +formattedID + " and sign in.").queue();
                }
                catch (IOException e) {
                    channel.sendMessage("<:networkproblem:799156231730233395>").queue();
                    channel.sendMessage("Error connecting to tracker service :(\nPlease try again later.").queue();
                }
                catch (NoSuchElementException e) {
                    channel.sendMessage("<:chiken_zoomed:799154829569556500>").queue();
                    channel.sendMessage("Error: Valorant profile for `" + profile + "` does not exist").queue();
                }

            }
        }

    }
    //generates an embedded menu with stats
    public MessageBuilder statsMenu(HashMap<String,String> stats, String id) {
        String rank = stats.get("Rank").toLowerCase();
        String rankImg = "unranked";
        String formattedID = id.replace("#", "%23").replace(" ", "%20");
        if (resources.containsKey(rank)) {
            rankImg = rank;
        }
        return new MessageBuilder()
                //.append("Here are [User]'s competitive stats!'")
                .setEmbed(new EmbedBuilder()
                        .setTitle("**"+id+"'s Ranked Profile**", resources.get("chicken"))
                        .setDescription(
                        "\n\n**"+stats.get("Playtime")+" | "+stats.get("Wins")+"W "+stats.get("Losses")+"L ("+stats.get("Win %")+")**")
                        .setColor(pickColor())
                        .setThumbnail(resources.get(rankImg))
                        //.setTimestamp()
                        //.setImage("https://cdn.discordapp.com/embed/avatars/0.png")
                        //.setAuthor("Viewing "+id+"'s competitive stats", resources.get("chicken"), resources.get("chicken"))
//                        .addField("", "**K/D: **" + stats.get("KDR"), true)
//                        .addField("", "**ADR: **"+stats.get("Damage/rd"), true)
//                        .addField("", "**Kills: **"+stats.get("Kills"), true)
//                        .addField("", "**Deaths: **"+stats.get("Deaths"), true)
                        .addField("K/D:", stats.get("KDR"),true)
                        .addField("Score/rd:",stats.get("Score/rd"),true)
                        .addField("Damage/rd:", stats.get("Damage/rd"),true)
                        .addField("Kills:",stats.get("Kills"),true)
                        .addField("Deaths:",stats.get("Deaths"),true)
                        .addField("Assists:",stats.get("Assists"),true)
                        .addField("Headshots:", stats.get("Headshots")+ " **("+stats.get("Headshot %")+"%)**",true)
                        .addField("Aces:",stats.get("Aces"),true)
                        .addField("Most Kills:", stats.get("Most Kills"),true)

                        .addField("", "**Top Agent:  "+ " "+resources.get(stats.get("top-agent"))+" | ("+stats.get("top-playtime").toUpperCase()
                                + ")**", false)
                        //.addField("", , true)
                        .addField("K/D:", stats.get("top-K/D"), true)
                        .addField("Damage/rd:", stats.get("top-dmg/rd"), true)
                        .addField("","[View full profile here](https://tracker.gg/valorant/profile/riot/" +formattedID+"/overview?playlist=competitive)",false)
                        .addField("", "\n<:boombotcircle:799139345621188628> Boom Bot | [GitHub](https://i.imgur.com/RpTWqF0.gif)", false)
                        .build());
    }
    //chooses random color for the menu
    public static Color pickColor() {
        Color c = Color.decode(colors[(int)(Math.random()*colors.length)]);
        System.out.println("Color chosen:" + c.toString());
        return c;
    }

}