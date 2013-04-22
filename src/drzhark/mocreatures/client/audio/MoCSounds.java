package drzhark.mocreatures.client.audio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;

public class MoCSounds {

    public void onSound(SoundLoadEvent event)
    {
        loadSounds();
    }

    @ForgeSubscribe
    public void onSoundsLoaded(SoundLoadEvent event)
    {
        SoundManager manager = event.manager;

        for (String soundName : soundNames)
        {
            manager.soundPoolSounds.addSound(soundName, this.getClass().getResource("/drzhark/mocreatures/client/resources/newsound/" + soundName));
        }
        manager.soundPoolStreaming.addSound("shuffling.ogg", this.getClass().getResource("/drzhark/mocreatures/client/resources/streaming/shuffling.ogg"));
    }

    private static String soundZipPath = "/resources/";
    private static String[] soundNames = { 
        "beardeath.ogg", 
        "beargrunt1.ogg", 
        "beargrunt2.ogg", 
        "beargrunt3.ogg", 
        "beargrunt4.ogg", 
        "beargrunt5.ogg", 
        "bearhurt1.ogg", 
        "bearhurt2.ogg", 
        "birdblack1.ogg", 
        "birdblack2.ogg", 
        "birdblack3.ogg", 
        "birdblue1.ogg", 
        "birdblue2.ogg", 
        "birdblue3.ogg", 
        "birdblue4.ogg", 
        "birdgreen1.ogg", 
        "birdgreen2.ogg", 
        "birdgreen3.ogg", 
        "birdred1.ogg", 
        "birdred2.ogg", 
        "birdred3.ogg", 
        "birdred4.ogg", 
        "birdred5.ogg", 
        "birdwhite1.ogg", 
        "birdyellow1.ogg", 
        "birdyellow2.ogg", 
        "birdyellow3.ogg", 
        "birdyellow4.ogg", 
        "birdyellow5.ogg", 
        "crocdying1.ogg", 
        "crocgrunt1.ogg", 
        "crocgrunt2.ogg", 
        "crocgrunt3.ogg", "crocgrunt4.ogg", "crochurt1.ogg", "crochurt2.ogg", "crochurt3.ogg", 
        "crocjawsnap1.ogg", "crocjawsnap2.ogg", "crocresting1.ogg", "crocresting2.ogg", "crocroll.ogg", "cub1.ogg", "cubdying.ogg", 
        "cubgrunt5.ogg", "cubgrunt1.ogg", "cubgrunt2.ogg", "cubgrunt3.ogg", "cubgrunt4.ogg", "cubhurt3.ogg", "cubhurt1.ogg", "cubhurt2.ogg", 
        "deergrunt.ogg", "deerdying.ogg", "deerfgrunt.ogg", "deerhurt.ogg", "destroy.ogg", "dolphin1.ogg", "dolphin2.ogg", "dolphin3.ogg", 
        "dolphin4.ogg", "dolphin5.ogg", "dolphin6.ogg", "dolphindying1.ogg", "dolphindying2.ogg", "dolphindying3.ogg", "dolphinhurt1.ogg", 
        "dolphinhurt2.ogg", "dolphinupset.ogg", "duck1.ogg", "duck2.ogg", "duck3.ogg", "duckhurt1.ogg", "duckhurt2.ogg", "duckplop.ogg", 
        "eating.ogg", "foxcall1.ogg", "foxcall2.ogg", "foxcall3.ogg", "foxcall4.ogg", "foxcall5.ogg", "foxdying.ogg", "foxhurt1.ogg", 
        "foxhurt2.ogg", "goatdigg.ogg", "goatdying.ogg", "goateating.ogg", "goatfemale.ogg", "goatgrunt.ogg", "goatkid.ogg", "goatsmack.ogg", 
        "horsedying.ogg", "horsegrunt1.ogg", "horsegrunt2.ogg", "horsegrunt3.ogg", "horsehurt1.ogg", "horsehurt2.ogg", "horsemad.ogg", 
        "kittendying.ogg", "kittengrunt1.ogg", "kittenhurt1.ogg", "kittenhurt2.ogg", "kittydying1.ogg", "kittydying2.ogg", "kittyeatingf.ogg", 
        "kittyeatingm.ogg", "kittyfood1.ogg", "kittyfood2.ogg", "kittyfood3.ogg", "kittyfood4.ogg", "kittygrunt1.ogg", "kittygrunt2.ogg", 
        "kittyhurt1.ogg", "kittyhurt2.ogg", "kittyhurt3.ogg", "kittypurr1.ogg", "kittypurr2.ogg", "kittytrapped.ogg", "kittyupset1.ogg", 
        "kittyupset2.ogg", "kittyupset3.ogg", "liondeath.ogg", "liongrunt1.ogg", "liongrunt2.ogg", "liongrunt3.ogg", "liongrunt4.ogg",
        "lionhurt1.ogg", "lionhurt2.ogg", "lionhurt3.ogg", "lionhurt4.ogg", "micedying1.ogg", "micedying2.ogg", "micegrunt1.ogg", 
        "micegrunt2.ogg", "micegrunt3.ogg", "micehurt1.ogg", "micehurt2.ogg", "micehurt3.ogg", "ogre1.ogg", "ogre2.ogg", "ogre3.ogg", 
        "ogredying.ogg", "ogrehurt1.ogg", "pegasus.ogg", "pouringfood.ogg", "pouringmilk.ogg", "rabbitdeath.ogg", "rabbithurt1.ogg", 
        "rabbithurt2.ogg", "rabbitland.ogg", "rabbitlift.ogg", "ratdying1.ogg", "ratgrunt1.ogg", "ratgrunt2.ogg", "ratgrunt3.ogg", 
        "ratgrunt4.ogg", "rathurt1.ogg", "rathurt2.ogg", "roping.ogg", "scorpionclaw.ogg", "scorpiondying1.ogg", "scorpiongrunt1.ogg", 
        "scorpiongrunt2.ogg", "scorpiongrunt3.ogg", "scorpiongrunt4.ogg", "scorpionhurt1.ogg", "scorpionsting1.ogg", "scorpionsting2.ogg", 
        "snakedying.ogg", "snakehiss1.ogg", "snakehiss2.ogg", "snakehurt1.ogg", "snakerattle1.ogg", "snakerattle2.ogg", "snakesnap.ogg", 
        "snakeswim.ogg", "snakeupset1.ogg", "snakeupset2.ogg", "turtledying.ogg", "turtlehissing.ogg", "turtlehurt.ogg", "werehumandying1.ogg",
        "werehumandying2.ogg", "werehumanhurt1.ogg", "werehumanhurt2.ogg", "weretransform1.ogg", "weretransform2.ogg", "weretransform3.ogg", 
        "werewolfdying1.ogg", "werewolfdying2.ogg", "werewolfgrunt1.ogg", "werewolfgrunt2.ogg", "werewolfgrunt3.ogg", "werewolfgrunt4.ogg", 
        "werewolfhurt1.ogg", "werewolfhurt2.ogg", "whip.ogg", "wolfdeath.ogg", "wolfgrunt1.ogg", "wolfgrunt2.ogg", "wolfgrunt3.ogg", 
        "wolfhurt1.ogg", "wolfhurt2.ogg", "wraith1.ogg", "wraith2.ogg", "wraith3.ogg", "wraithdying1.ogg", "wraithdying2.ogg", 
        "wraithurt1.ogg", "wraithurt2.ogg", "ostrichchick.ogg", "ostrichdying.ogg", "ostrichgrunt1.ogg", "ostrichgrunt2.ogg", 
        "ostrichhurt1.ogg", "ostrichhurt2.ogg", "appear.ogg", "appearmagic.ogg", "armoroff.ogg", "armorput.ogg", "donkeygrunt1.ogg", 
        "donkeygrunt2.ogg", "donkeyhurt.ogg", "donkeydying.ogg", "drinking.ogg", "vanish.ogg", "wingflap.ogg", "zebragrunt1.ogg", 
        "zebrahurt.ogg", "zebragrunt2.ogg", "horsemadundead.ogg", "transform.ogg", "horsedyingghost.ogg", "horsedyingundead.ogg", 
        "horsegruntghost1.ogg", "horsegruntghost2.ogg", "horsegruntghost3.ogg", "horsegruntundead1.ogg", "horsegruntundead2.ogg", 
        "horsehurtundead1.ogg", "horsehurtghost1.ogg", "horsemadghost.ogg", "turkey1.ogg", "turkey2.ogg", "turkeyhurt.ogg", "bee1.ogg", 
        "bee2.ogg", "bee3.ogg", "beeupset.ogg", "cricket.ogg", "cricketfly.ogg", "dragonfly.ogg", "fly.ogg", "golemattach.ogg", 
        "golemdying.ogg", "golemgrunt1.ogg", "golemgrunt2.ogg", "golemhurt.ogg", "golemshoot1.ogg", "golemwalk.ogg", "golemexplode.ogg",
        "elephantcalf1.ogg", "elephantcalf2.ogg", "elephantdying.ogg", "elephantgrunt1.ogg", "elephantgrunt2.ogg", "elephantgrunt3.ogg",
        "elephantgrunt4.ogg", "elephantgrunt5.ogg", "elephantgrunt6.ogg", "elephantgrunt7.ogg", "elephantgrunt8.ogg",
        "elephanthurt1.ogg", "elephanthurt2.ogg",
        "wyverndying.ogg", "wyverngrunt1.ogg", "wyverngrunt2.ogg", "wyverngrunt3.ogg", "wyverngrunt4.ogg", "wyverngrunt5.ogg",
        "wyvernhurt1.ogg", "wyvernhurt2.ogg", "wyvernpoisoning.ogg", "wyvernwingflap.ogg"
    }; 

    public void loadSounds()
    {
        if (MoCClientProxy.mc.sndManager == null)
        {
            //System.out.println("No sound manager?");
            return;
        }
        //soundsLoaded = true;

        // Record music
        installSound("shuffling.ogg", "streaming/");

        // Entity sounds
        String[] arr = this.soundNames;
        for (String fname : arr)
        {
            installSound(fname, "newsound/");
        }

    }

    //private void installSound(String filename) 
    private void installSound(String filename, String filepath)
    {
        //String filepath = "newsound/";
        File soundFile = new File(MoCClientProxy.mc.mcDataDir, "resources/" + filepath + filename);

        if (!soundFile.exists())
        {
            // Copy sound file from zip file to proper path
            try
            {
                if (!soundFile.getParentFile().exists())
                {
                    soundFile.getParentFile().mkdirs();
                }
                String srcPath = soundZipPath + filepath + filename;
                BufferedInputStream fileIn = new BufferedInputStream(MoCreatures.class.getResourceAsStream(srcPath));
                BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(soundFile));
                byte[] buffer = new byte[1024];
                int n = 0;
                while (-1 != (n = fileIn.read(buffer)))
                {
                    fileOut.write(buffer, 0, n);
                }
                fileIn.close();
                fileOut.close();

            }
            catch (IOException ex)
            {
                System.err.println("Could not extract and copy file: " + soundFile);
            }

        }

        if (soundFile.canRead() && soundFile.isFile())
        {
            MoCClientProxy.mc.installResource(filepath + filename, soundFile);
        }
        else
        {
            System.err.println("Could not load file: " + soundFile);
        }
    }
}
