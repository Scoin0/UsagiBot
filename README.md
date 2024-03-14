[![CodeFactor](https://www.codefactor.io/repository/github/scoin0/usagibot/badge)](https://www.codefactor.io/repository/github/scoin0/usagibot)
# UsagiBot 
---
UsagiBot is a Twitch & Bancho IRC bot for handling song requests in Osu!

### Instruction

---
This version of the bot is targeting Java 8. Please have Java 8 or higher installed.

1. Download the latest version of [UsagiBot](https://github.com/Scoin0/UsagiBot/releases)
2. Extract `UsagiBot.jar` to it's own folder
3. Open notepad and create a `start.bat` script. You can copy and paste the code from below.
```
java -jar Usagibot.jar
pause
```
4. Run the newly created script once. It will create a configuration file for you. Fill out those fields and run the bot again.
    * Head over to the [configuration](https://github.com/Scoin0/UsagiBot/wiki/Configuration) wiki to learn how to configure the bot properly.
5. Everything should hopefully be working properly.

### Twitch Usage

---
#### Here's a list of avaliable [Commands](https://github.com/Scoin0/UsagiBot/wiki/Commands)

#### Requesting songs
Anyone can request a song to be added. To do this just simply post the beatmapset link inside the chat.    
As an example, `https://osu.ppy.sh/beatmapsets/53176#osu/162237`.     
If you would like to tell the streamer to play certain mods, use `+<mods>`.    
As an example, `https://osu.ppy.sh/beatmapsets/53176#osu/162237 +HDHR`.

### Requirements

---

* [Gosumemory](https://github.com/l3lackShark/gosumemory) - This is how the bot knows what song is playing.
* [Twitch OAuth2 Token](https://twitchapps.com/tmi/) - It is recommended to create a new account for this.
* [Bancho IRC Token](https://osu.ppy.sh/p/irc) - Do not create a new account to send you the beatmaps you can be banned for multiaccounting. Use your own account, it will be like you're DM'ing yourself.
* [Osu API Token](https://osu.ppy.sh/home/account/edit) - Scroll down to the bottom to obtain your token. More information inside the [wiki](https://github.com/Scoin0/UsagiBot/wiki/Configuration).

If there are any issues please let me know either in the issue tracker or DM me on Discord at `Scoin0#0002`.
