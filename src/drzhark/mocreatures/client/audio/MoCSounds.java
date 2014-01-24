package drzhark.mocreatures.client.audio;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoCSounds {

    @SubscribeEvent
    public void onSound(SoundLoadEvent event) {
        String[] sounds = null;

        try {
            sounds = getResourceListing(this.getClass(), "assets/mocreatures/sound/");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if (sounds != null)
        {
            for (int i = 0; i < sounds.length; i++)
            { 
                // register our sounds
                //if (sounds[i].contains(".ogg"))
                 //   event.manager.addSound("mocreatures:" + sounds[i]);
            }
        }
      //  event.manager.addStreaming("mocreatures:shuffling.ogg");
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onPlayStreaming(PlayStreamingEvent event) 
    {
        if (event.name == "shuffling")
        {
            //FMLClientHandler.instance().getClient().sndManager.playStreaming("mocreatures:shuffling", (float) event.x + 0.5F, (float) event.y + 0.5F, (float) event.z + 0.5F);
        }
    }

    /**
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     * 
     * @author Greg Briggs
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException 
     * @throws IOException 
     */
    String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
          /* A file path: easy enough */
          return new File(dirURL.toURI()).list();
        } 

        if (dirURL == null) {
          /* 
           * In case of a jar file, we can't actually find a directory.
           * Have to assume the same jar as clazz.
           */
          String me = clazz.getName().replace(".", "/")+".class";
          dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
          /* A JAR path */
          String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
          JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
          Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
          Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
          while(entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            if (name.startsWith(path)) { //filter according to the path
              String entry = name.substring(path.length());
              int checkSubdir = entry.indexOf("/");
              if (checkSubdir >= 0) {
                // if it is a subdirectory, we just return the directory name
                entry = entry.substring(0, checkSubdir);
              }
              result.add(entry);
            }
          }
          jar.close();
          return result.toArray(new String[result.size()]);
        } 

        throw new UnsupportedOperationException("Cannot list files for URL "+dirURL);
    }
}