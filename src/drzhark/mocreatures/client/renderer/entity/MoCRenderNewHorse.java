package drzhark.mocreatures.client.renderer.entity;

import java.util.Map;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelNewHorse;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

@SideOnly(Side.CLIENT)
public class MoCRenderNewHorse extends MoCRenderMoC {

    private static final Map<String, ResourceLocation> RESOURCE_CACHE = Maps.newHashMap();
    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsebug.png");
    private static final ResourceLocation TEXTURE_WHITE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsewhite.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseblack.png");
    private static final ResourceLocation TEXTURE_BROWN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsebrown.png");
    private static final ResourceLocation TEXTURE_DARKBROWN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsedarkbrown.png");
    private static final ResourceLocation TEXTURE_CREAMY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsecreamy.png");
    private static final ResourceLocation TEXTURE_BRIGHTCREAMY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsebrightcreamy.png");
    private static final ResourceLocation TEXTURE_PECKLED = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsepeckled.png");
    private static final ResourceLocation TEXTURE_PALEBROWN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsepalebrown.png");
    private static final ResourceLocation TEXTURE_GREY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsegrey.png");
    private static final ResourceLocation TEXTURE_PINTO = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsepinto.png");
    private static final ResourceLocation TEXTURE_BRIGHTPINTO = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsebrightpinto.png");
    private static final ResourceLocation TEXTURE_PALESPECKLES = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsepalespeckles.png");
    private static final ResourceLocation TEXTURE_SPOTTED = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsespotted.png");
    private static final ResourceLocation TEXTURE_COW = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsecow.png");
    private static final ResourceLocation TEXTURE_GHOST = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseghost.png");
    private static final ResourceLocation TEXTURE_GHOST2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseghostb.png");
    private static final ResourceLocation TEXTURE_UNDEAD = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseundead.png");
    private static final ResourceLocation TEXTURE_UNDEADUNICORN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseundeadunicorn.png");
    private static final ResourceLocation TEXTURE_UNDEADPEGASUS = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseundeadpegasus.png");
    private static final ResourceLocation TEXTURE_SKELETON = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseskeleton.png");
    private static final ResourceLocation TEXTURE_UNICORNSKELETON = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseunicornskeleton.png");
    private static final ResourceLocation TEXTURE_PEGASUSSKELETON = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsepegasusskeleton.png");
    private static final ResourceLocation TEXTURE_BAT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsebat.png");
    private static final ResourceLocation TEXTURE_UNICORN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseunicorn.png");
    private static final ResourceLocation TEXTURE_NIGHTMARE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare.png");
    private static final ResourceLocation TEXTURE_NIGHTMARE1 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare1.png");
    private static final ResourceLocation TEXTURE_PEGASUS = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsepegasus.png");
    private static final ResourceLocation TEXTURE_BLACKPEGASUS = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horseblackpegasus.png");
    private static final ResourceLocation TEXTURE_DARKPEGASUS = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsedarkpegasus.png");
    private static final ResourceLocation TEXTURE_FAIRYYELLOW = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairyyellow.png");
    private static final ResourceLocation TEXTURE_FAIRYPURPLE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairypurple.png");
    private static final ResourceLocation TEXTURE_FAIRYWHITE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairywhite.png");
    private static final ResourceLocation TEXTURE_FAIRYBLUE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairyblue.png");
    private static final ResourceLocation TEXTURE_FAIRYPINK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairypink.png");
    private static final ResourceLocation TEXTURE_FAIRYLIGHTGREEN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairylightgreen.png");
    private static final ResourceLocation TEXTURE_FAIRYBLACK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairyblack.png");
    private static final ResourceLocation TEXTURE_FAIRYRED = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairyred.png");
    private static final ResourceLocation TEXTURE_FAIRYDARKBLUE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png");
    private static final ResourceLocation TEXTURE_FAIRYCYAN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairycyan.png");
    private static final ResourceLocation TEXTURE_FAIRYGREEN = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairygreen.png");
    private static final ResourceLocation TEXTURE_FAIRYORANGE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsefairyorange.png");
    private static final ResourceLocation TEXTURE_ZEBRA = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsezebra.png");
    private static final ResourceLocation TEXTURE_ZORSE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsezorse.png");
    private static final ResourceLocation TEXTURE_DONKEY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsedonkey.png");
    private static final ResourceLocation TEXTURE_MULE = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsemule.png");
    private static final ResourceLocation TEXTURE_ZONKEY = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "horsezonkey.png");
    private Random rand = new Random();
    private int textCounter;

    public MoCRenderNewHorse(MoCModelNewHorse modelbase)
    {
        super(modelbase, 0.5F);

    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityHorse)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityHorse horse)
    {
        ResourceLocation tempTexture;

        switch (horse.getType())
        {
        case 1:
            tempTexture = TEXTURE_WHITE;
            break;
        case 2:
            tempTexture = TEXTURE_CREAMY;
            break;
        case 3:
            tempTexture = TEXTURE_BROWN;
            break;
        case 4:
            tempTexture = TEXTURE_DARKBROWN;
            break;
        case 5:
            tempTexture = TEXTURE_BLACK;
            break;
        case 6:
            tempTexture = TEXTURE_BRIGHTCREAMY;
            break;
        case 7:
            tempTexture = TEXTURE_PECKLED;
            break;
        case 8:
            tempTexture = TEXTURE_PALEBROWN;
            break;
        case 9:
            tempTexture = TEXTURE_GREY;
            break;
        case 11:
            tempTexture = TEXTURE_PINTO;
            break;
        case 12:
            tempTexture = TEXTURE_BRIGHTPINTO;
            break;
        case 13:
            tempTexture = TEXTURE_PALESPECKLES;
            break;
        case 16:
            tempTexture = TEXTURE_SPOTTED;
            break;
        case 17:
            tempTexture = TEXTURE_COW;
            break;

        case 21:
            tempTexture = TEXTURE_GHOST;
            break;
        case 22:
            tempTexture = TEXTURE_GHOST2;
            break;
        case 23:
            tempTexture = TEXTURE_UNDEAD;
            break;
        case 24:
            tempTexture = TEXTURE_UNDEADUNICORN;
            break;
        case 25:
            tempTexture = TEXTURE_UNDEADPEGASUS;
            break;
        case 26:
            tempTexture = TEXTURE_SKELETON;
            break;
        case 27:
            tempTexture = TEXTURE_UNICORNSKELETON;
            break;
        case 28:
            tempTexture = TEXTURE_PEGASUSSKELETON;
            break;
        case 30:
            tempTexture = TEXTURE_DEFAULT;
            break;
        case 32:
            tempTexture = TEXTURE_BAT;
            break;
        case 36:
            tempTexture = TEXTURE_UNICORN;
            break;
        case 38:
            horse.setImmuneToFire(true);
            tempTexture = TEXTURE_NIGHTMARE;
            break;
        case 39:
            tempTexture = TEXTURE_PEGASUS;
            break;
        case 40:
            horse.setImmuneToFire(true);
            tempTexture = TEXTURE_DARKPEGASUS;
            break;
            /*
        case 44:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;
        case 45:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;
        case 46:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;
            
        case 47:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;*/
        case 48:
            tempTexture = TEXTURE_FAIRYYELLOW;
            break;
        case 49:
            tempTexture = TEXTURE_FAIRYPURPLE;
            break;
        case 50:
            tempTexture = TEXTURE_FAIRYWHITE;
            break;
        case 51:
            tempTexture = TEXTURE_FAIRYBLUE;
            break;
        case 52:
            tempTexture = TEXTURE_FAIRYPINK;
            break;
        case 53:
            tempTexture = TEXTURE_FAIRYLIGHTGREEN;
            break;
        case 54:
            tempTexture = TEXTURE_FAIRYBLACK;
            break;
        case 55:
            tempTexture = TEXTURE_FAIRYRED;
            break;
        case 56:
            tempTexture = TEXTURE_FAIRYDARKBLUE;
            break;
        case 57:
            tempTexture = TEXTURE_FAIRYCYAN;
            break;
        case 58:
            tempTexture = TEXTURE_FAIRYGREEN;
            break;
        case 59:
            tempTexture = TEXTURE_FAIRYORANGE;
            break;
        
        case 60:
            tempTexture = TEXTURE_ZEBRA;
            break;
        case 61:
            tempTexture = TEXTURE_ZORSE;
            break;
        case 65:
            tempTexture = TEXTURE_DONKEY;
            break;
        case 66:
            tempTexture = TEXTURE_MULE;
            break;
        case 67:
            tempTexture = TEXTURE_ZONKEY;
            break;

        default:
            tempTexture = TEXTURE_DEFAULT;
        }

        if ((horse.isArmored() || horse.isMagicHorse()) && horse.getArmorType() > 0)
        {
            String armorTex = "";
            if (horse.getArmorType() == 1)
            {
                armorTex = "metal.png";
            }
            if (horse.getArmorType() == 2)
            {
                armorTex = "gold.png";
            }
            if (horse.getArmorType() == 3)
            {
                armorTex = "diamond.png";
            }
            if (horse.getArmorType() == 4)
            {
                armorTex = "crystaline.png";
            }
            if (!RESOURCE_CACHE.containsKey(tempTexture.func_110624_b().replace(".png", armorTex)))
                RESOURCE_CACHE.put(tempTexture.func_110624_b().replace(".png", armorTex), new ResourceLocation(tempTexture.func_110624_b().replace(".png", armorTex)));
            return RESOURCE_CACHE.get(tempTexture.func_110624_b().replace(".png", armorTex));
        }

        
        if (horse.isUndead() && horse.getType() < 26)
        {
            ResourceLocation baseTex = TEXTURE_UNDEAD;
            int max = 79;
            if (horse.getType() == 25) // undead pegasus
            {
                baseTex = TEXTURE_UNDEADPEGASUS;
                // max = 79; //undead pegasus have an extra animation

            }
            if (horse.getType() == 24)// undead unicorn
            {
                baseTex = TEXTURE_UNDEADUNICORN;
                max = 69; // undead unicorn have an animation less
            }
            
            String iteratorTex = "1";
            if (MoCreatures.proxy.getAnimateTextures())
            {
                if (rand.nextInt(3) == 0)
                {
                    textCounter++;
                }
                if (textCounter < 10)
                {
                    textCounter = 10;
                }
                if (textCounter > max)
                {
                    textCounter = 10;
                }
                iteratorTex = "" + textCounter;
                iteratorTex = iteratorTex.substring(0, 1);
            }
           
            String decayTex = "" + (horse.getEdad() / 100);
            decayTex = decayTex.substring(0, 1);
            if (!RESOURCE_CACHE.containsKey(baseTex.func_110624_b() + decayTex + iteratorTex + ".png"))
                RESOURCE_CACHE.put(baseTex.func_110624_b() + decayTex + iteratorTex + ".png", new ResourceLocation(baseTex.func_110624_b() + decayTex + iteratorTex + ".png"));
            return RESOURCE_CACHE.get(baseTex.func_110624_b() + decayTex + iteratorTex + ".png");
        }
        
        // if animate textures is off, return plain textures
        if (!MoCreatures.proxy.getAnimateTextures()) { return tempTexture; }

        
        if (horse.isNightmare())
        {
            if (rand.nextInt(1) == 0)
            {
                textCounter++;
            }
            if (textCounter < 10)
            {
                textCounter = 10;
            }
            if (textCounter > 59)
            {
                textCounter = 10;
            }
            String NTA = MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare";
            String NTB = "" + textCounter;
            NTB = NTB.substring(0, 1);
            String NTC = ".png";
            if (!RESOURCE_CACHE.containsKey(NTA + NTB + NTC))
                RESOURCE_CACHE.put(NTA + NTB + NTC, new ResourceLocation(NTA + NTB + NTC));
            return RESOURCE_CACHE.get(NTA + NTB + NTC);
        }

        

        if (horse.transformCounter != 0 && horse.transformType != 0)
        {
            ResourceLocation newText = TEXTURE_UNDEAD;
            if (horse.transformType == 23)
            {
                newText = TEXTURE_UNDEAD;
            }
            if (horse.transformType == 24)
            {
                newText = TEXTURE_UNDEADUNICORN;
            }
            if (horse.transformType == 25)
            {
                newText = TEXTURE_UNDEADPEGASUS;
            }
            if (horse.transformType == 36)
            {
                newText = TEXTURE_UNICORN;
            }
            if (horse.transformType == 39)
            {
                newText = TEXTURE_PEGASUS;
            }
            if (horse.transformType == 40)
            {
                newText = TEXTURE_BLACKPEGASUS;
            }
            
            if (horse.transformType == 48)
            {
                newText = TEXTURE_FAIRYYELLOW;
            }
            if (horse.transformType == 49)
            {
                newText = TEXTURE_FAIRYPURPLE;
            }
            if (horse.transformType == 50)
            {
                newText = TEXTURE_FAIRYWHITE;
            }
            if (horse.transformType == 51)
            {
                newText = TEXTURE_FAIRYBLUE;
            }
            if (horse.transformType == 52)
            {
                newText = TEXTURE_FAIRYPINK;
            }
            if (horse.transformType == 53)
            {
                newText = TEXTURE_FAIRYLIGHTGREEN;
            }
            if (horse.transformType == 54)
            {
                newText = TEXTURE_FAIRYBLACK;
            }
            if (horse.transformType == 55)
            {
                newText = TEXTURE_FAIRYRED;
            }
            if (horse.transformType == 56)
            {
                newText = TEXTURE_FAIRYDARKBLUE;
            }
            
            if (horse.transformType == 57)
            {
                newText = TEXTURE_FAIRYCYAN;
            }
            
            if (horse.transformType == 58)
            {
                newText = TEXTURE_FAIRYGREEN;
            }
            
            if (horse.transformType == 59)
            {
                newText = TEXTURE_FAIRYORANGE;
            }
            
            if (horse.transformType == 32)
            {
                newText = TEXTURE_BAT;
            }
            if (horse.transformType == 38)
            {
                newText = TEXTURE_NIGHTMARE1;
            }
            if ((horse.transformCounter % 5) == 0) { return newText; }
            if (horse.transformCounter > 50 && (horse.transformCounter % 3) == 0) { return newText; }

            if (horse.transformCounter > 75 && (horse.transformCounter % 4) == 0) { return newText; }
        }

        return tempTexture;
    }

    protected void adjustHeight(EntityLiving entityliving, float FHeight)
    {
        GL11.glTranslatef(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityHorse entityhorse = (MoCEntityHorse) entityliving;
        if (!entityhorse.getIsAdult() || entityhorse.getType() > 64)
        {
            stretch(entityhorse);
        }
        if (entityhorse.isGhost())
        {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 5F));
        }
        super.preRenderCallback(entityliving, f);

    }

    protected void stretch(MoCEntityHorse entityhorse)
    {
        float sizeFactor = entityhorse.getEdad() * 0.01F;
        if (entityhorse.getIsAdult())
        {
            sizeFactor = 1.0F;
        }
        if (entityhorse.getType() > 64) //donkey
        {
            sizeFactor *= 0.9F;
        }
        GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
    }

}
