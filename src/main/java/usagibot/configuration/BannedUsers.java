package usagibot.configuration;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

@Slf4j
public class BannedUsers {

    private static File file = new File("bannedusers.txt");
    public Set<String> bannedUsers = new HashSet<>();

    public BannedUsers() {
        try {
            if (!file.exists()) {
                log.info("Creating bannedusers.txt");
                file.createNewFile();
            } else {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        bannedUsers.add(line);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void addBannedUser(String user) {
        String username = user.toLowerCase();
        if (bannedUsers.add(username)) {
            log.info("Added {} to the banned users file.", username);
            saveBannedUsersFile();
        } else {
            log.info("User: {} is already banned.", username);
        }
    }

    public void removeBannedUser(String user) {
        String username = user.toLowerCase();
        if (bannedUsers.remove(username)) {
            log.info("Removed {} from the banned users file.", username);
            saveBannedUsersFile();
        }
    }

    public void saveBannedUsersFile() {
        try (FileWriter writer = new FileWriter(file)) {
            for (String user : bannedUsers) {
                writer.write(user + System.lineSeparator());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}