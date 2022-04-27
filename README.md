# UsagiBot

---

UsagiBot is a Twitch & Bancho IRC bot for handling song requests in Osu!

### Running The Bot

---

1. Open notepad and create a `start.bat` script. You can copy and paste the code from below.
```
java -Xms4G -Xmx4G -jar Usagibot.jar
pause
```
2. Run Once. It will generate the configuration file and should close back out. Fill in the fields marked `required`.
3. Start bot again. Everything should be working properly.

### Twitch Usage

---
#### Commands

`np` - Now Playing. This command will output the current playing song as a download link in chat.

#### Requesting songs
Anyone can request a song to be added. To do this just simply post the beatmapset link inside the chat.
As of right now, the bot only accepts urls in the `beatmapsets` format. As an example,
`https://osu.ppy.sh/beatmapsets/53176#osu/162237`. This will be changed later to accept all forms of urls.
Like `/b/`, `/s/`, and `/beatmapsets/`.

### Requirements

---

* [Gosumemory](https://github.com/l3lackShark/gosumemory) - This is how the bot knows what song is playing.
* [Twitch OAuth2 Token](https://twitchapps.com/tmi/) - It is recommended to create a new account for this.
* [Bancho IRC Token](https://osu.ppy.sh/p/irc) - Do not create a new account to send you the beatmaps you can be banned for multiaccounting. Use your own account, it will be like you're DM'ing yourself.
* [Osu API Token](https://osu.ppy.sh/home/account/edit) - Scroll down to the bottom to obtain your token. More information inside the [wiki](https://github.com/Scoin0/UsagiBot/wiki/Configuration).


### Todo 

---

* PP Calculation
* Support osu mods
* Finish Implementing the Ratelimiter
* Support other osu urls. (`/b/` and `/s/`)
* Add more commands (`!stats` and such)
* Have 2 versions, a standalone and an always running version.

---
If there are any issues please let me know either in the issue tracker or DM me at `Scoin0#0002`.