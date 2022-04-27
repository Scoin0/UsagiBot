# UsagiBot

---

UsagiBot is a Twitch & Bancho IRC bot for handling song requests in Osu!


### Running The Bot

---

*As of right now this isn't fully working*

1. Create a start.bat script
```
java -Xms4G -Xmx4G -jar usagibot.jar
pause
```
2. Run Once. It will generate the configuration file.
3. Start bot again. Everything should be working properly.

***A wiki will be made later with all the configuration values and their uses.***

### Twitch Usage

---

As of right now, the bot only accepts urls in the `beatmapsets` format. As an example,
`https://osu.ppy.sh/beatmapsets/53176#osu/162237`. This will be changed later to accept all forms of urls.
Like `/b/`, `/s/`, and `/beatmapsets/`. 

### Todo

---

* PP Calculation
* Support older osu urls. (`/b/` and `/s/`)
