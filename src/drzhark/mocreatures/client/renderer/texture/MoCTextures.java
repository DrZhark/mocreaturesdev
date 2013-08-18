package drzhark.mocreatures.client.renderer.texture;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import com.google.common.collect.Maps;

import drzhark.mocreatures.MoCreatures;

import net.minecraft.util.ResourceLocation;

public class MoCTextures {

    private static final Map<String, ResourceLocation> RESOURCE_CACHE = Maps.newHashMap();
    private static final Map<String, Enumeration<URL>> ENUMERATION_URLS = Maps.newHashMap();

    public void loadTextures() {

        try {
            ENUMERATION_URLS.put(MoCreatures.proxy.ARMOR_TEXTURE, this.getClass().getClassLoader().getResources("assets/mocreatures/textures/armor"));
            ENUMERATION_URLS.put(MoCreatures.proxy.BLOCK_TEXTURE, this.getClass().getClassLoader().getResources("assets/mocreatures/textures/blocks"));
            ENUMERATION_URLS.put(MoCreatures.proxy.GUI_TEXTURE, this.getClass().getClassLoader().getResources("assets/mocreatures/textures/gui"));
            ENUMERATION_URLS.put(MoCreatures.proxy.ITEM_TEXTURE, this.getClass().getClassLoader().getResources("assets/mocreatures/textures/items"));
            ENUMERATION_URLS.put(MoCreatures.proxy.MISC_TEXTURE, this.getClass().getClassLoader().getResources("assets/mocreatures/textures/misc"));
            ENUMERATION_URLS.put(MoCreatures.proxy.MODEL_TEXTURE, this.getClass().getClassLoader().getResources("assets/mocreatures/textures/models"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Enumeration<URL>> enumEntry : ENUMERATION_URLS.entrySet())
        {
            Enumeration<URL> enumeration = enumEntry.getValue();
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
    
                    String[] mocTextures = files.list();
                    for (int i = 0; i < mocTextures.length; i++)
                    {
                        // register our textures
                       // System.out.println("Registering texture " + mocTextures[i]);
                        RESOURCE_CACHE.put(mocTextures[i], new ResourceLocation("mocreatures", enumEntry.getKey() + mocTextures[i]));
                    }
                }
            }
        }
    }

    public ResourceLocation getTexture(String texture)
    {
        return RESOURCE_CACHE.get(texture);
    }
}