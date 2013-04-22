package drzhark.mocreatures;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.world.World;

public class MoCEggColour {

    private static String textureZipPath = "/mocreatures/";

    /**
     * To create eggs with texture if no eggs have been created before.
     * 
     * @param entityClass
     * @param entityId
     * @return
     */
    public static EntityEggInfo searchEggColor(Class entityClass, int entityId)
    {
        int principalColor = 0x000000;
        int secondColor = 0xffffff;
        try
        {
            //JarFile jarFile = new JarFile(Minecraft.getAppDir("Minecraft")+"/bin/minecraft.jar");
            //JarEntry entry = jarFile.getJarEntry(((Entity)entityClass.getConstructors()[0].newInstance((World)null)).getTexture().substring(1));
            //String baseTexture2 = (((Entity)entityClass.getConstructors()[0].newInstance((World)null)).getTexture().substring(1));
            String baseTexture = (((Entity) entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { null })).getTexture()); //.substring(1));

            //Entity entity = (Entity)entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {null});

            BufferedInputStream fileIn = new BufferedInputStream(MoCreatures.class.getResourceAsStream(baseTexture));
            BufferedImage imageInput = ImageIO.read(fileIn);
            //if (entry==null)
            if (imageInput == null) { return new EntityEggInfo(entityId, secondColor, principalColor); }

            //String srcPath = soundZipPath + filepath + filename;
            //BufferedInputStream fileIn = new BufferedInputStream(MoCreatures.class.getResourceAsStream(textureZipPath));

            //InputStream input = jarFile.getInputStream(entry);
            //InputStream textureInput = null;
            //BufferedImage imageInput = ImageIO.read(input);
            //BufferedImage imageInput = ImageIO.read(baseTexture);
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int i = 0; i < imageInput.getWidth(); i++)
            {
                for (int j = 0; j < imageInput.getHeight(); j++)
                {
                    int rgb = imageInput.getRGB(i, j);
                    if (map.containsKey(rgb))
                    {
                        map.put(rgb, map.get(rgb) + 1);
                    }
                    else
                    {
                        map.put(rgb, 1);
                    }
                }
            }
            Collection<Integer> values = map.values();
            int principalIndex = 0;
            int secondIndex = 0;

            for (Integer value : values)
            {
                if (value > principalIndex)
                {
                    principalIndex = value;
                }
                if (value >= secondIndex && value != principalIndex)
                {
                    secondIndex = value;
                }
            }

            for (int i = 0; i < imageInput.getWidth(); i++)
            {
                for (int j = 0; j < imageInput.getHeight(); j++)
                {
                    int rgb = imageInput.getRGB(i, j);
                    if (map.containsKey(rgb))
                    {
                        int number = map.get(rgb);
                        if (number == principalIndex)
                        {
                            principalColor = rgb;
                        }
                        else if (number == secondIndex)
                        {
                            secondColor = rgb;
                        }
                        map.remove(rgb);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new EntityEggInfo(entityId, secondColor, principalColor);
    }

}