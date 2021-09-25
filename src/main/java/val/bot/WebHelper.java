package val.bot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class WebHelper {

    private static HashMap<String,String> stats; //list of all the data that the scraper will grab and their selectors
    private static ChromeOptions options;
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void initializeVars() {
        stats = new HashMap<String,String>();
        stats.put("Playtime", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.title > div > div > span.playtime");
        stats.put("Damage/rd", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.giant-stats > div:nth-child(1) > div > div.numbers > span.value");
        stats.put("KDR", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.giant-stats > div:nth-child(2) > div > div.numbers > span.value");
        stats.put("Headshot %", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.giant-stats > div:nth-child(3) > div > div.numbers > span.value");
        stats.put("Win %", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.giant-stats > div:nth-child(4) > div > div.numbers > span.value");
        stats.put("Wins", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.highlighted.highlighted--giants > div.highlighted__content > div > div.valorant-winloss > svg > g:nth-child(3) > text:nth-child(1)");
        stats.put("Matches", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.title > div > div > span.matches");
        //stats.put("Losses", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.highlighted.highlighted--giants > div.highlighted__content > div > div.valorant-winloss > svg > g:nth-child(3) > text:nth-child(2)");
        stats.put("Kills", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.main > div:nth-child(2) > div > div.numbers > span.value");
        stats.put("Headshots", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.main > div:nth-child(3) > div > div.numbers > span.value");
        stats.put("Deaths", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.main > div:nth-child(4) > div > div.numbers > span.value");
        stats.put("Assists", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.main > div:nth-child(5) > div > div.numbers > span.value");
        stats.put("Rank", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.highlighted.highlighted--giants > div.highlighted__content > div > div.valorant-highlighted-content__stats > div:nth-child(2) > span.valorant-highlighted-stat__value");
        //stats.put("Aces", ""); // aces all time not shown anymore
        stats.put("Kills/rd", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.main > div:nth-child(7) > div > div.numbers > span.value");
        stats.put("Most Kills", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.main > div:nth-child(10) > div > div.numbers > span.value");
        stats.put("Score/rd", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.main > div:nth-child(6) > div > div.numbers > span.value"); //score per round not shown anymore
        stats.put("season","#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.segment-stats.area-main-stats.card.bordered.header-bordered.responsive > div.title > div > h2");
        stats.put("top-agent", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.top-agents.area-top-agents > div > div > table > tbody > tr:nth-child(1) > td:nth-child(1) > div > span");
        stats.put("top-playtime", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.top-agents.area-top-agents > div > div > table > tbody > tr:nth-child(1) > td:nth-child(2) > div > span");
        stats.put("top-win%", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.top-agents.area-top-agents > div > div > table > tbody > tr:nth-child(1) > td:nth-child(2) > div > span");
        stats.put("top-K/D", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.top-agents.area-top-agents > div > div > table > tbody > tr:nth-child(1) > td:nth-child(5) > div > span.name");
        stats.put("top-matches", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.top-agents.area-top-agents > div > div > table > tbody > tr:nth-child(1) > td:nth-child(3)");
        stats.put("top-dmg/rd", "#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.site-container.trn-grid.trn-grid--vertical.trn-grid--small > div.trn-grid.container > div.top-agents.area-top-agents > div > div > table > tbody > tr:nth-child(1) > td:nth-child(6) > div > span.name");
        //total # of matches is calculated from wins + losses
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
        options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors",
                "--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
    }
    //inits webdriver if closed
    private static void initializeDriver() {
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, 30);
    }
    //return stats of user with given Riot ID
    public static HashMap<String, String> getStats(String id, HashMap<String,String> parameters) throws IOException, NoSuchElementException, AuthenticationException {
        initializeDriver();
        if (!isValidID(id)) return null;
        int tagIndex = id.indexOf("#");
        String formattedID = id.replace("#", "%23").replace(" ", "%20");
        HashMap<String, String> userStats = new HashMap<String, String>();
        for (String s: parameters.values()) {
            System.out.println("Parameter: " + s);
        }
        String URL = "https://tracker.gg/valorant/profile/riot/"+formattedID+"/overview"+parameters.get("gamemode")+"&"+parameters.get("season");
        System.out.println("URL: " + URL);
        driver.navigate().to(URL);
        WebElement testForValid;
//        try {
//            Thread.sleep(5000);
//        }
//        catch (Exception e) {
//            System.out.println("Thread exception");
//        }
        try {
            System.out.println("Attempting to grab stats for " + id); // checks if profile username is visible (if not, then profile not public or doesnt exist)
                    testForValid = driver.findElement(By.cssSelector("#app > div.trn-wrapper > div.trn-container > div > main > div.content.no-card-margin > div.ph > div.ph__container > div.ph-details > div.ph-details__identifier > span > span.trn-ign__username"));
        }
        // profile is invalid, so check if it's just private or if no profile exists
        catch (org.openqa.selenium.NoSuchElementException n) {
            try { //check if id does not exist
                System.out.println("Checking if profile exists...");
                testForValid = driver.findElement(By.cssSelector("#app > div.trn-wrapper > div.trn-container > div > main > div.content.content--error > h1"));

                //id does not exist, so throw error
                System.out.println("Profile does not exist!");
                throw new NoSuchElementException();
            }
            catch (org.openqa.selenium.NoSuchElementException e){ //this means profile is just private
                System.out.println("Profile is private.");
                throw new AuthenticationException();
            }
        }

        //profile is public and valid, so start grabbing competitive stats
        String lastKey = "nothing yet"; //for error checking
        try {
            System.out.println("Profile is public! Grabbing stats");
            WebElement stat;

            for (String key : stats.keySet()) {
                lastKey = key;
                try {
                    stat = driver.findElement(By.cssSelector(stats.get(key)));
                    //special cases
                    if (key.equals("Losses")) { //cuts off non-number characters
                        userStats.put(key, stat.getText().substring(0, stat.getText().length()-2));
                    }
                    else if (key.equals("Playtime")) {
                        userStats.put(key, stat.getText().substring(0, stat.getText().length()-10));
                    }
                    // exception for KAY/O to remove slash
                    else if (key.equals("top-agent") && stats.get("top-agent").equals("KAY/O")) {
                        stats.put("top-agent", "KAYO");
                    }
                    else {
                        userStats.put(key, stat.getText());
                    }
                    System.out.println(key + ": " + userStats.get(key));

                }
                catch (Exception e) {
                    System.out.println("[ERROR] " + key + ": " + e);
                }

            }

        }
        catch (Exception e) {
            System.out.println("Error while grabbing stats for "+lastKey+": " +e);
        }

        //add up wins and losses for total matches
        String matches = userStats.get("Matches");
        int losses = Integer.parseInt(matches.substring(0, matches.length()-8)) - Integer.parseInt(userStats.get("Wins"));
        userStats.put("Losses", ""+losses);
        driver.close();
        return userStats;
    }
    //simple filtering for valid Riot IDs. Not exhaustive whatsoever.
    public static boolean isValidID(String id) {
        if (id.indexOf("#") < 1) {
            System.out.println(id + " has no #");
            return false;
        }
        int tagLength = id.length() - id.indexOf("#") - 1;
        System.out.println("id: " + id + " ## tag: " + id.substring(id.indexOf("#")+1));
        return tagLength >= 3 && tagLength <= 5;
    }
}






//    Document statsPage;
//        try {
//            //connects to user's stats profile
//            System.out.println("Getting stats for " + id);
//            String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:43.0) Gecko/20100101 Firefox/43.0"; //Pretend to be a browser
//            statsPage = Jsoup.connect("https://tracker.gg/valorant/profile/riot/"
//                    +formattedID+"/overview?playlist=competitive")
//                    .header("User-Agent", userAgent)
//                    .referrer("http://www.google.com")
//                    .timeout(12000)
//                    //.ignoreHttpErrors(true)
//                    .get();
//            Elements unknown = statsPage.select(".lead:contains(WE COULD NOT FIND THE PLAYER)");
//            if (unknown == null) {
//                System.out.println("Error: player not found");
//                throw new NoSuchElementException();
//            }
//            //checks if profile is signed up for tracker.gg
//            Elements publicProfile = statsPage.select(".lead:contains(This profile is private.)");
//            if (publicProfile != null) {
//                return null;
//            }
//
//            //past this point, user profile is public and everything is fine and dandy so far
//
//
//            System.out.println("done!");
//        }
//        catch (IOException e) {
//            System.out.println(e);
//            throw new IOException(); //tracker.gg is down
//        }
//        return userStats;
