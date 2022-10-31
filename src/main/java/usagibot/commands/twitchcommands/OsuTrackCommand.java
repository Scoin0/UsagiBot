package usagibot.commands.twitchcommands;

import usagibot.commands.Command;
import usagibot.commands.CommandEvent;
import usagibot.osu.api.GameMode;
import usagibot.osu.osutrack.OsuTracker;

import java.util.Locale;

public class OsuTrackCommand extends Command {

    public OsuTrackCommand() {
        name = "osutrack";
        description = "osu!Track Commands";
        aliases = new String[]{"ot"};
        usage.add("ot");                                                // Default command that leads back to commands list
        usage.add("ot u");                                              // Updates self with the default Gamemode (Osu)
        usage.add("ot u {user}");                                       // Updates another user with the default Gamemode (Osu)
        usage.add("ot u {user} {mode}");                                // Updates another user with a Gamemode
        usage.add("ot update");                                         // Updates self with the default Gamemode (Osu)
        usage.add("ot update {user}");                                  // Updates another user with the default Gamemode (Osu)
        usage.add("ot update {user} {mode}");                           // Updates another user with a Gamemode

        usage.add("ot sh");                                             // Gets all stats updates for self with default Gamemode (Osu)
        usage.add("ot sh {user}");                                      // Gets all stats updates a user with default Gamemode (Osu)
        usage.add("ot sh {user} {mode}");                               // Gets all stats updates a user with a Gamemode
        usage.add("ot sh {user} {mode} {from} {to}");                   // Gets all stats updates a user with a Gamemode within a date range (Optional)
        usage.add("ot statshistory");                                   // Gets all stats updates for self with default Gamemode (Osu)
        usage.add("ot statshistory {user}");                            // Gets all stats updates a user with default Gamemode (Osu)
        usage.add("ot statshistory {user} {mode}");                     // Gets all stats updates a user with a Gamemode
        usage.add("ot statshistory {user} {mode} {from} {to}");         // Gets all stats updates a user with a Gamemode within a date range(Optional)

        usage.add("ot hi");                                             // Gets all recorded hiscores for self with default Gamemode (Osu)
        usage.add("ot hi {user}");                                      // Gets all recorded hiscores a user with default Gamemode (Osu)
        usage.add("ot hi {user} {mode}");                               // Gets all recorded hiscores a user with a Gamemode
        usage.add("ot hi {user} {mode} {from} {to}");                   // Gets all recorded hiscores a user with a Gamemode within a date range (Optional)
        usage.add("ot hiscores");                                       // Gets all recorded hiscores for self with default Gamemode (Osu)
        usage.add("ot hiscores {user}");                                // Gets all recorded hiscores a user with default Gamemode (Osu)
        usage.add("ot hiscores {user} {mode}");                         // Gets all recorded hiscores a user with a Gamemode
        usage.add("ot hiscores {user} {mode} {from} {to}");             // Gets all recorded hiscores a user with a Gamemode within a date range (Optional)
        usage.add("ot highscores");                                     // Gets all recorded hiscores for self with default Gamemode (Osu)
        usage.add("ot highscores {user}");                              // Gets all recorded hiscores a user with default Gamemode (Osu)
        usage.add("ot highscores {user} {mode}");                       // Gets all recorded hiscores a user with a Gamemode
        usage.add("ot highscores {user} {mode} {from} {to}");           // Gets all recorded hiscores a user with a Gamemode within a date range (Optional)

        usage.add("ot p");                                              // Gets peak rank + accuracy for self with the default Gamemode (Osu)
        usage.add("ot p {user}");                                       // Gets peak rank + accuracy for another user with the default Gamemode (Osu)
        usage.add("ot p {user} {mode}");                                // Gets peak rank + accuracy for another user with a Gamemode
        usage.add("ot peak");                                           // Gets peak rank + accuracy for self with the default Gamemode (Osu)
        usage.add("ot peak {user}");                                    // Gets peak rank + accuracy for another user with the default Gamemode (Osu)
        usage.add("ot peak {user} {mode}");                             // Gets peak rank + accuracy for another user with a Gamemode

        usage.add("ot bp");                                             // Gets all best plays for pp for all users with default Gamemode (Osu | limit of 5)
        usage.add("ot bp {mode}");                                      // Gets all best plays for pp for all users with a Gamemode (Osu | limit of 5)
        usage.add("ot bp {mode} {from} {to}");                          // Gets all best plays for pp for all users with a Gamemode within a date range (Optional | limit of 5)
        usage.add("ot bp {mode} {from} {to} {limit}");                  // Gets all best plays for pp for all users with a Gamemode within a date range and a limit (Optional | limit of 5)
        usage.add("ot bestplays");                                      // Gets all best plays for pp for all users with default Gamemode (Osu | limit of 5)
        usage.add("ot bestplays {mode}");                               // Gets all best plays for pp for all users with a Gamemode (Osu | limit of 5)
        usage.add("ot bestplays {mode} {from} {to}");                   // Gets all best plays for pp for all users with a Gamemode within a date range (Optional | limit of 5)
        usage.add("ot bestplays {mode} {from} {to} {limit}");           // Gets all best plays for pp for all users with a Gamemode within a date range and a limit (Optional | limit of 5)



    }

    @Override
    public void onCommand(CommandEvent event) {

        OsuTracker osuTracker = new OsuTracker();

        if (event.getArgs().length == 0) {
            event.getClient().sendMessage("Please refer to the command list to see how this command is used: https://github.com/Scoin0/UsagiBot/wiki/Commands");
        }

        if (event.getArgs()[0].equalsIgnoreCase("u") || event.getArgs()[0].equalsIgnoreCase("update")) {
            System.out.println(osuTracker.postOsuStats("11493596", GameMode.OSU).getNewHighscores());
        }

    }

    public void updateUser() {

    }
}
