package usagibot.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BannedUsers {

    private static File file = new File("bannedusers.txt");
    private static final Logger log = LoggerFactory.getLogger(BannedUsers.class);

    public List<String> bannedUsers = new ArrayList<>();

    public BannedUsers() {
        try {
            if (!file.exists()) {
                log.info("Creating bannedusers.txt");
                file.createNewFile();
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    bannedUsers.add(line);
                }
                reader.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void addBannedUser(String user) {
        String username = user.toLowerCase(Locale.ROOT);
        if (!bannedUsers.contains(username)) {
            bannedUsers.add(username);
            log.info("Added " + username + " to the banned users file.");
            saveBannedUsersFile();
        } else {
            log.info("User: " + username + " is already banned.");
        }
    }

    public void removeBannedUser(String user) {
        String username = user.toLowerCase(Locale.ROOT);
        if (bannedUsers.contains(username)) {
            bannedUsers.remove(username);
            log.info("Removed " + username + " from the banned users file.");
            saveBannedUsersFile();
        }
    }

    public void saveBannedUsersFile() {
        try {
            FileWriter writer = new FileWriter(file);
            for (String users : bannedUsers) {
                writer.write(users + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}