package val.bot;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ConfigHelper {
    private static String token; //bot token
    private static HashMap<String, String> prefs; //command prefix <server id, prefix>
    private static File cfg; //the config file, settings.txt
    private static String sep; //line separator
    private static final String PREF = ";;"; //default command prefix
    //private static HashMap<String, String> help = initHelp(); //list of commands
    private static HashMap<String, String> users; //Discord IDs : Riot IDs
    private static HashMap<String, String> help = initHelp(); //list of commands

    public static void initializeVars() {
        sep = System.getProperty("line.separator");
        prefs = new HashMap<String, String>();
        cfg = new File("settings.txt");
        users = new HashMap<String, String>();
        token = null;
        try {
            if (cfg.createNewFile()) {
                FileWriter writer = new FileWriter(cfg, true);
                writer.write("#Insert your bot's authentication token below https://i.imgur.com/DoJn80b.png" + sep);
                writer.write("token=" + sep);
                writer.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Scanner myReader = new Scanner(cfg);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                int start = data.indexOf("=") + 1; //start of data item to insert
                int start2 = data.length();
                if (data.indexOf(",") != -1) {
                    start2 = data.indexOf(":")+1; //start of 2nd data item
                }
                if (data.startsWith("token=")) {
                    token = data.substring(start);
                    System.out.println("token=" + token);
                } else if (data.startsWith("server,pref=")) {
                    prefs.put(data.substring(start,start2-1), data.substring(start2));
                }
                else if (data.startsWith("discord,riot=")) {
                    users.put(data.substring(start,start2-1), data.substring(start2));
                }
                //System.out.println(data);
            }
            myReader.close();
        } catch (Exception e) {
            System.out.println("Error reading\n" + e);
            e.printStackTrace();
        }
    }

    public static String getToken() {
        return token;
    }

    public static String getPref(String serverID) {
        if (prefs.containsKey(serverID)) {
            return prefs.get(serverID);
        }
        return PREF;
    }
    //addItem does not check if the item is existing, since that shouldn't happen (unless someone edits the file manually)
    public static void addItem(String type, String id) {
        try {
            FileWriter writer = new FileWriter(cfg, true);
            writer.write(type + "=" + id + sep);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error adding " + type + " | " + id);
            System.out.println(e);
        }
    }

    public static boolean removeItem(String type, String id) {
        String data;
        boolean removed = false;
        try {
            File temp = new File("temp.txt");
            temp.createNewFile();
            Scanner reader = new Scanner(cfg);
            FileWriter writer = new FileWriter(temp, false); //false -> should override the original file

            while (reader.hasNextLine()) {
                data = reader.nextLine();
                if (data.contains(type + "=" + id)) {
                    removed = true;
                    System.out.println("if: " + data);
                } else {
                    writer.write(data + sep);
                    System.out.println("else: " + data);
                }
            }
            writer.close();
            reader.close();
            cfg.delete();
            System.out.println(temp.renameTo(cfg));


        } catch (Exception e) {
            System.out.println("Error writing | " + type + "|" + id + "\n" + e);
        }
        return removed;
    }
    //if value does not exist, adds item; type2 optional
    public static boolean editItem(String type, String id1, String id2) { //currently only works with variables with only one instance
        String data;
        boolean edited = false;
        try {
            File temp = new File("temp.txt");
            temp.createNewFile();
            Scanner reader = new Scanner(cfg);
            FileWriter writer = new FileWriter(temp, false); //false -> should override the original file

            while (reader.hasNextLine()) {
                data = reader.nextLine();
                if (id2 == null && data.startsWith(type)) {
                    writer.write(type + "=" + id1 + sep);
                    edited = true;
                }
                else if (id2 != null && data.startsWith(type + "=" + id1)){
                    writer.write(type + "=" + id1 + ":" + id2 + sep);
                    edited = true;
                } else {
                    writer.write(data + sep);
                    System.out.println("else: " + data);
                }
            }
            if (!edited) { //item not found, so adding to end
                if (id2 != null) {
                    writer.write(type + "=" +id1+":"+id2);
                }
                else {
                    writer.write(type+"="+id1);
                }
            }
            writer.close();
            reader.close();
            cfg.delete();
            System.out.println(temp.renameTo(cfg));
        } catch (Exception e) {
            System.out.println("Error writing | " + type + "|" + id1 + "\n" + e);
        }
        return edited;
    }
    public static void editUser(String disc, String riot) {
    users.put(disc, riot);
    }

    public static String getUser(String disc) {
        if (users.containsKey(disc)) {
            return users.get(disc);
        }
        return null;
    }

    public static HashMap<String,String> initHelp() { //order these such that there are no conflicts with 'contains', so 'remove' goes before 'removeall'
        HashMap<String, String> commmands = new HashMap<String, String>();
        commmands.put("help", "it helps you lol\nUsage: `help [command]`");
        commmands.put("link", "Links your Discord profile to a Riot ID\nUsage: `link [Riot ID]`");
        commmands.put("profile", "Returns your Riot ID, if you have one set.");
        commmands.put("stats", "Displays your Valorant Competitive stats.\nUsage: `stats [Riot ID]`\nLeave ID blank to display your own stats if you've used `link`\nGet unrated stats with \"-u\", all-time stats with \"-all\", or stats from a particular episode/act with \"-e#a#\"\nExample: `;;stats BoomBot#NA1 -u -e2a3`");
        commmands.put("prefix", "change the command prefix\nDefault is \";;\"\nUsage: `prefix [prefix]`");
        commmands.put("emojis", "Send an emoji of an agent with [prefix][agent\nExample: `;;raze` ");
        return commmands;
    }
}
