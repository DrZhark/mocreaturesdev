package drzhark.mocreatures.client.renderer.texture;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import drzhark.mocreatures.MoCreatures;

public class MoCTextures {

    private static final Map<String, ResourceLocation> RESOURCE_CACHE = Maps.newHashMap();
    private static final Map<String, String[]> TEXTURE_RESOURCES = Maps.newHashMap();

    public void loadTextures() {
        try {
            TEXTURE_RESOURCES.put(MoCreatures.proxy.ARMOR_TEXTURE, getResourceListing(this.getClass(), "assets/mocreatures/textures/armor/"));
            TEXTURE_RESOURCES.put(MoCreatures.proxy.BLOCK_TEXTURE, getResourceListing(this.getClass(), "assets/mocreatures/textures/blocks/"));
            TEXTURE_RESOURCES.put(MoCreatures.proxy.GUI_TEXTURE, getResourceListing(this.getClass(), "assets/mocreatures/textures/gui/"));
            TEXTURE_RESOURCES.put(MoCreatures.proxy.ITEM_TEXTURE, getResourceListing(this.getClass(), "assets/mocreatures/textures/items/"));
            TEXTURE_RESOURCES.put(MoCreatures.proxy.MISC_TEXTURE, getResourceListing(this.getClass(), "assets/mocreatures/textures/misc/"));
            TEXTURE_RESOURCES.put(MoCreatures.proxy.MODEL_TEXTURE, getResourceListing(this.getClass(), "assets/mocreatures/textures/models/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String[]> textureEntry : TEXTURE_RESOURCES.entrySet())
        {
            String[] resources = textureEntry.getValue();
            if (resources != null && resources.length > 0)
            {
                for (int i = 0; i < resources.length; i++)
                {
                    if (resources[i].contains(".png"))
                        RESOURCE_CACHE.put(resources[i], new ResourceLocation("mocreatures", textureEntry.getKey() + resources[i]));
                }
            }
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

    public ResourceLocation getTexture(String texture)
    {
        return RESOURCE_CACHE.get(texture);
    }
}