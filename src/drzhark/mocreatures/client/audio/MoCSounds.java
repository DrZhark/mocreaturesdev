package drzhark.mocreatures.client.audio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;

public class MoCSounds {

    @ForgeSubscribe
    public void onSound(SoundLoadEvent event) {
        Enumeration<URL> enumeration = null;
        try {
            enumeration = this.getClass().getClassLoader().getResources("assets/mocreatures/sound");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (enumeration != null)
        {
            while (enumeration.hasMoreElements())
            {
                URL url=enumeration.nextElement();
                File files = null;
                try {
                    files = new File(url.toURI());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                String[] mocSounds = files.list();
                for (int i = 0; i < mocSounds.length; i++)
                {
                    // register our sounds
                    event.manager.addSound("mocreatures:" + mocSounds[i]);
                }
            }
        }
    }
}