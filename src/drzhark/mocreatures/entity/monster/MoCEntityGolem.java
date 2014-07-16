package drzhark.mocreatures.entity.monster;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageTwoBytes;

public class MoCEntityGolem extends MoCEntityMob implements IEntityAdditionalSpawnData {

    public int tcounter;
    public MoCEntityThrowableRock tempRock;
    private byte golemCubes[];
    private int dCounter = 0;
    private int sCounter;

    public MoCEntityGolem(World world)
    {
        super(world);
        texture = "golemt.png";
        setSize(1.5F, 4F);
    }

    protected void applyEntityAttributes()
    {
      super.applyEntityAttributes();
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        for (int i = 0; i < 23; i++)
        {
            data.writeByte(golemCubes[i]);
        }
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        for (int i = 0; i < 23; i++)
        {
            golemCubes[i] = data.readByte();
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        initGolemCubes();
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // gState - 0 spawned / 1 summoning rocks /2 has enemy /3 half life (harder) /4 dying
    }

    public int getGolemState()
    {
        return (int) (dataWatcher.getWatchableObjectByte(23));
    }

    public void setGolemState(int b)
    {
        dataWatcher.updateObject(23, Byte.valueOf((byte) b));
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {
            if (getGolemState() == 0) //just spawned
            {
                EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 8D);
                if (entityplayer1 != null)
                {
                    setGolemState(1); //activated
                }
            }

            if (getGolemState() == 1 && !isMissingCubes())//entityToAttack != null)
            {
                setGolemState(2); //is complete
            }

            if (getGolemState() > 2 && getGolemState() != 4 && entityToAttack == null)
            {
                setGolemState(1);
            }

            if (getGolemState() > 1 && entityToAttack != null && rand.nextInt(20) == 0)
            {
                if (getHealth() >= 30)
                {
                    setGolemState(2);
                }
                if (getHealth() < 30 && getHealth() >= 10)
                {
                    setGolemState(3); //more dangerous
                }
                if (getHealth() < 10)
                {
                    setGolemState(4); //dying
                }
            }

            if (getGolemState() != 0 && getGolemState() != 4 && isMissingCubes())
            {

                int freq = 21 - (getGolemState() * worldObj.difficultySetting.getDifficultyId());
                if (getGolemState() == 1)
                {
                    freq = 10;
                }
                if (rand.nextInt(freq) == 0)
                {
                    acquireRock(2);
                }
            }

            if (getGolemState() == 4)
            {
                setPathToEntity(null);
                dCounter++;

                if (dCounter < 80 && rand.nextInt(3) == 0)
                {
                    acquireRock(4);
                }

                if (dCounter == 120)
                {
                    MoCTools.playCustomSound(this, "golemdying", worldObj, 3F);
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
                }

                if (dCounter > 140)
                {
                    MoCTools.playCustomSound(this, "golemexplode", worldObj, 3F);
                    destroyGolem();
                }
            }
        }

        if (tcounter != 0)
        {
            if (tcounter++ == 50)
            {
                if (MoCreatures.isServer())
                {
                    shootBlock(entityToAttack);
                }

            }
            else if (tcounter > 70)
            {
                tcounter = 0;
            }

        }

        if (MoCreatures.proxy.getParticleFX() > 0 && getGolemState() == 4 && sCounter > 0)
        {
            for (int i = 0; i < 10; i++)
            {
                worldObj.spawnParticle("explode", posX, posY, posZ, rand.nextGaussian(), rand.nextGaussian(), rand.nextGaussian());
            }
        }
    }

    private void destroyGolem()
    {
        List<Integer> usedBlocks = usedCubes();
        if ((!usedBlocks.isEmpty()) && (MoCTools.mobGriefing(this.worldObj)) && (MoCreatures.proxy.golemDestroyBlocks))
        {
            for (int i = 0; i < usedBlocks.size(); i++)
            {
                Block block = Block.getBlockById(generateBlock(golemCubes[usedBlocks.get(i)]));
                EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(block, 1, 0));
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
            }
        }
        this.setDead();
    }

    @Override
    protected boolean isMovementCeased()
    {
        return getGolemState() == 4;
    }

    protected void acquireRock(int type)
    {
        //finds a missing rock spot in its body
        //looks for a random rock around it
        int[] myRockCoords = new int[] { -9999, -1, -1 };
        myRockCoords = MoCTools.getRandomBlockCoords(this, 24D);
        if (myRockCoords[0] == -9999) { return; }

        boolean canDestroyBlocks = MoCTools.mobGriefing(worldObj) && MoCreatures.proxy.golemDestroyBlocks;
        
            Block block = worldObj.getBlock(myRockCoords[0], myRockCoords[1], myRockCoords[2]);
        
        int tRockID = Block.getIdFromBlock(worldObj.getBlock(MathHelper.floor_double(myRockCoords[0]), MathHelper.floor_double(myRockCoords[1]), MathHelper.floor_double(myRockCoords[2])));
        if (tRockID == 0) {return;} //air blocks
        
        int tRockMetadata = worldObj.getBlockMetadata(MathHelper.floor_double(myRockCoords[0]), MathHelper.floor_double(myRockCoords[1]), MathHelper.floor_double(myRockCoords[2]));
        
            BlockEvent.BreakEvent event = null;
            if (!this.worldObj.isRemote)
            {
            event = new BlockEvent.BreakEvent(myRockCoords[0], myRockCoords[1], myRockCoords[2], worldObj, block, tRockMetadata, FakePlayerFactory.get((WorldServer)this.worldObj, MoCreatures.MOCFAKEPLAYER));
            }
        if (canDestroyBlocks && event != null && !event.isCanceled())
            {
                //destroys the original rock
                worldObj.setBlock(myRockCoords[0], myRockCoords[1], myRockCoords[2], Blocks.air, 0, 3);
            }
        
        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.worldObj, this, myRockCoords[0], myRockCoords[1]+1, myRockCoords[2]);//, false, true);
        
        if (!canDestroyBlocks) //make cheap rocks
        {
            tRockID = returnRandomCheapBlock();
            tRockMetadata = 0;
        }

        trock.setType(tRockID);
        trock.setMetadata(tRockMetadata);
        trock.setBehavior(type);//so the rock: 2 follows the EntityGolem  or 3 - gets around the golem
        
        //spawns the new TRock
        this.worldObj.spawnEntityInWorld(trock);
    }

    /**
     * returns a random block when the golem is unable to break blocks
     * @return
     */
    private int returnRandomCheapBlock()
    {
        int i = rand.nextInt(4);
        switch (i)
        {
        case 0:
            return 3; //dirt
        case 1:
            return 4; //cobblestone
        case 2:
            return 5; //wood
        case 3:
            return 79; //ice
        }
        return 3;
    }

    /**
     * When the golem receives the rock, called from within EntityTRock
     * 
     * @param ID
     *            = block id
     * @param Metadata
     *            = block Metadata
     */
    public void receiveRock(int ID, int Metadata)
    {
        if (MoCreatures.isServer())
        {
            byte myBlock = translateOre(ID);
            byte slot = (byte) getRandomCubeAdj();
            if ((slot != -1) && (slot < 23) && (myBlock != -1) && getGolemState() != 4)
            {
                MoCTools.playCustomSound(this, "golemattach", worldObj, 3F);
                int h = worldObj.difficultySetting.getDifficultyId();
                this.setHealth(getHealth() + h);
                if (getHealth() > getMaxHealth())
                {
                    this.setHealth(getMaxHealth());
                }
                saveGolemCube(slot, myBlock);
            }
            else
            {
                MoCTools.playCustomSound(this, "turtlehurt", worldObj, 2F);
                if ((MoCTools.mobGriefing(this.worldObj)) && (MoCreatures.proxy.golemDestroyBlocks))
                {
                    EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Block.getBlockById(ID), 1, Metadata));
                    entityitem.delayBeforeCanPickup = 10;
                    entityitem.age = 4000;
                }
            }
        }
    }

    /**
     * Not used!
     */
    protected void attackWithTRock()
    {
        //TODO add metadata!!
        this.tcounter++;// += 1;
        if (this.tcounter == 5)
        {
            //creates a dummy Trock on top of it
            MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.worldObj, this, this.posX, this.posY + 2.0D, this.posZ);//, true, false);
            this.worldObj.spawnEntityInWorld(trock);
            //removes a block from the environment and uses its type for the Trock
            trock.setType(MoCTools.destroyRandomBlock(this, 5D));
            trock.setBehavior(1);
            this.tempRock = trock;
        }

        if ((this.tcounter >= 5) && (this.tcounter < 200))
        {
            //maintains position of Trock above head
            this.tempRock.posX = this.posX;
            this.tempRock.posY = (this.posY + 3.0D);
            this.tempRock.posZ = this.posZ;
        }

        if (this.tcounter >= 200)
        {
            //throws a newly spawned Trock and destroys the held Trock
            if (entityToAttack != null)
            {
                ThrowStone(entityToAttack, this.tempRock.getType(), 0);
            }

            this.tempRock.setDead();
            this.tcounter = 0;
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {

        if ((f > 5.0F) && entity != null && tcounter == 0 && canShoot()) //attackTime <= 0 &&
        {
            tcounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
            return;
        }

        if (this.attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10);
        }
    }

    @Override
    public void performAnimation(int animationType)
    {
        if (animationType == 0) //rock throwing animation
        {
            tcounter = 1;
        }
        if (animationType == 1) //smoking animation
        {
            sCounter = 1;
        }
    }

    private void shootBlock(Entity entity)
    {
        if (entity == null) { return; }
        List<Integer> armBlocks = new ArrayList<Integer>();

        for (int i = 9; i < 15; i++)
        {
            if (golemCubes[i] != 30)
            {
                armBlocks.add(i);
            }
        }
        if (armBlocks.isEmpty()) { return; }

        int j = rand.nextInt(armBlocks.size());
        int i = armBlocks.get(j);
        int x = i;

        if (i == 9 || i == 12)
        {
            if (golemCubes[i + 2] != 30)
            {
                x = i + 2;
            }
            else if (golemCubes[i + 1] != 30)
            {
                x = i + 1;
            }
        }

        if (i == 10 || i == 13)
        {
            if (golemCubes[i + 1] != 30)
            {
                x = i + 1;
            }
        }
        MoCTools.playCustomSound(this, "golemshoot", worldObj, 3F);
        ThrowStone(entity, generateBlock(golemCubes[x]), 0);
        saveGolemCube((byte) x, (byte) 30);
        tcounter = 0;
    }

    private boolean canShoot()
    {
        int x = 0;
        for (byte i = 9; i < 15; i++)
        {
            if (golemCubes[i] != 30)
            {
                x++;
            }
        }
        return (x != 0) && getGolemState() != 4 && getGolemState() != 1;
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (getGolemState() == 4) { return false; }

        List missingChestBlocks = missingChestCubes();
        boolean uncoveredChest = (missingChestBlocks.size() == 4);
        if (!openChest() && !uncoveredChest && getGolemState() != 1)
        {
            int j = worldObj.difficultySetting.getDifficultyId();
            if (MoCreatures.isServer() && rand.nextInt(j) == 0)
            {
                destroyRandomGolemCube();
            }
            else
            {
                MoCTools.playCustomSound(this, "turtlehurt", worldObj, 2F);
            }

            Entity entity = damagesource.getEntity();
            if ((entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0))
            {
                entityToAttack = entity;
            }
            return true;
        }
        if (i > 5)
        {
            i = 5; //so you can't hit a Golem too hard
        }
        if (getGolemState() != 1 && super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0))
            {
                entityToAttack = entity;
            }
            return true;
        }
        if (getGolemState() == 1)
        {
            Entity entity = damagesource.getEntity();
            if ((entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0))
            {
                entityToAttack = entity;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Destroys a random cube, with the proper check for extremities and spawns
     * a block in world
     */
    private void destroyRandomGolemCube()
    {
        int i = getRandomUsedCube();
        if (i == 4) { return;
        //do not destroy the valueable back cube
        }

        int x = i;
        if (i == 10 || i == 13 || i == 16 || i == 19)
        {
            if (golemCubes[i + 1] != 30)
            {
                x = i + 1;
            }

        }

        if (i == 9 || i == 12 || i == 15 || i == 18)
        {
            if (golemCubes[i + 2] != 30)
            {
                x = i + 2;
            }
            else if (golemCubes[i + 1] != 30)
            {
                x = i + 1;
            }
        }

        if (x != -1 && golemCubes[x] != 30)
        {
            Block block = Block.getBlockById(generateBlock(golemCubes[x]));
            saveGolemCube((byte) x, (byte) 30);
            MoCTools.playCustomSound(this, "golemhurt", worldObj, 3F);
            if ((MoCTools.mobGriefing(this.worldObj)) && (MoCreatures.proxy.golemDestroyBlocks))
            {
                EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(block, 1, 0));
                entityitem.delayBeforeCanPickup = 10;
                this.worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }

    @Override
    public float getAdjustedYOffset()
    {
        if (golemCubes[17] != 30 || golemCubes[20] != 30)
        {
            //has feet
            return 0F;
        }
        if (golemCubes[16] != 30 || golemCubes[19] != 30)
        {
            //has knees but not feet
            return 0.4F;
        }
        if (golemCubes[15] != 30 || golemCubes[18] != 30)
        {
            //has thighs but not knees or feet
            return 0.7F;
        }

        if (golemCubes[1] != 30 || golemCubes[3] != 30)
        {
            //has lower chest
            return 0.8F;
        }
        //missing everything
        return 1.45F;
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor()
    {
        return 1.8F;
    }

    /**
     * Throws stone at entity
     * 
     * @param targetEntity
     * @param rocktype
     * @param metadata
     */
    protected void ThrowStone(Entity targetEntity, int rocktype, int metadata)
    {
        ThrowStone((int) targetEntity.posX, (int) targetEntity.posY, (int) targetEntity.posZ, rocktype, metadata);
    }

    /**
     * Throws stone at X,Y,Z coordinates
     * 
     * @param X
     * @param Y
     * @param Z
     * @param rocktype
     * @param metadata
     */
    protected void ThrowStone(int X, int Y, int Z, int rocktype, int metadata)
    {
        MoCEntityThrowableRock etrock = new MoCEntityThrowableRock(this.worldObj, this, this.posX, this.posY + 3.0D, this.posZ);//, false, false);
        this.worldObj.spawnEntityInWorld(etrock);
        etrock.setType(rocktype);
        etrock.setMetadata(metadata);
        etrock.setBehavior(0);
        etrock.motionX = ((X - this.posX) / 20.0D);
        etrock.motionY = ((Y - this.posY) / 20.0D + 0.5D);
        etrock.motionZ = ((Z - this.posZ) / 20.0D);
    }

    /**
     * @param i
     *            = slot
     * @return the block type stored in that slot. 30 = empty
     */
    public byte getBlockText(int i)
    {
        return golemCubes[i];
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("golemState", getGolemState());
        NBTTagList cubeLists = new NBTTagList();

        for (int i = 0; i < 23; i++)
        {
            NBTTagCompound nbttag = new NBTTagCompound();
            nbttag.setByte("Slot", this.golemCubes[i]);
            cubeLists.appendTag(nbttag);
        }
        nbttagcompound.setTag("GolemBlocks", cubeLists);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setGolemState(nbttagcompound.getInteger("golemState"));
        NBTTagList nbttaglist = nbttagcompound.getTagList("GolemBlocks", 10);
        for (int i = 0; i < 23; i++)
        {
            NBTTagCompound var4 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
            this.golemCubes[i] = var4.getByte("Slot");
        }
    }

    /**
     * Initializes the goleCubes array
     */
    private void initGolemCubes()
    {
        golemCubes = new byte[23];

        for (int i = 0; i < 23; i++)
        {
            golemCubes[i] = 30;
        }

        int j = rand.nextInt(4);
        switch (j)
        {
            case 0:
                j = 7;
                break;
            case 1:
                j = 11;
                break;
            case 2:
                j = 15;
                break;
            case 3:
                j = 21;
                break;
        }
        saveGolemCube((byte) 4, (byte) j);
    }

    /**
     * Saves the type of Cube(value) on the given 'slot' if server, then sends a
     * packet to the clients
     * 
     * @param slot
     * @param value
     */
    public void saveGolemCube(byte slot, byte value)
    {
        golemCubes[slot] = value;
        if (MoCreatures.isServer() && MoCreatures.proxy.worldInitDone) // Fixes CMS initialization during world load
        {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageTwoBytes(this.getEntityId(), slot, value), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
        }
    }

    /**
     * returns a list of the empty blocks
     * 
     * @return
     */
    private List missingCubes()
    {
        List<Integer> emptyBlocks = new ArrayList<Integer>();

        for (int i = 0; i < 23; i++)
        {
            if (golemCubes[i] == 30)
            {
                emptyBlocks.add(i);
            }
        }
        return emptyBlocks;
    }

    /**
     * Returns true if is 'missing' any cube, false if it's full
     * 
     * @return
     */
    public boolean isMissingCubes()
    {
        for (int i = 0; i < 23; i++)
        {
            if (golemCubes[i] == 30) { return true; }
        }
        return false;
    }

    private List missingChestCubes()
    {
        List<Integer> emptyChestBlocks = new ArrayList<Integer>();

        for (int i = 0; i < 4; i++)
        {
            if (golemCubes[i] == 30)
            {
                emptyChestBlocks.add(i);

            }

        }
        return emptyChestBlocks;
    }

    /**
     * returns a list of the used block spots
     * 
     * @return
     */
    private List usedCubes()
    {
        List<Integer> usedBlocks = new ArrayList<Integer>();

        for (int i = 0; i < 23; i++)
        {
            if (golemCubes[i] != 30)
            {
                usedBlocks.add(i);
            }
        }
        return usedBlocks;
    }

    /**
     * Returns a random used cube position if the golem is empty, returns -1
     * 
     * @return
     */
    private int getRandomUsedCube()
    {
        List<Integer> usedBlocks = usedCubes();
        if (usedBlocks.isEmpty()) { return -1; }
        int randomEmptyBlock = rand.nextInt(usedBlocks.size());
        return usedBlocks.get(randomEmptyBlock);
    }

    /**
     * Returns a random empty cube position if the golem is full, returns -1
     * 
     * @return
     */
    private int getRandomMissingCube()
    {
        //first it makes sure it has the four chest cubes
        List<Integer> emptyChestBlocks = missingChestCubes();
        if (!emptyChestBlocks.isEmpty())
        {
            int randomEmptyBlock = rand.nextInt(emptyChestBlocks.size());
            return emptyChestBlocks.get(randomEmptyBlock);
        }

        //otherwise returns any other cube
        List<Integer> emptyBlocks = missingCubes();
        if (emptyBlocks.isEmpty())
        {
            return -1;
        }
        int randomEmptyBlock = rand.nextInt(emptyBlocks.size());
        return emptyBlocks.get(randomEmptyBlock);
    }

    /**
     * returns the position of the cube to be added, contains logic for the
     * extremities
     * 
     * @return
     */
    private int getRandomCubeAdj()
    {
        int i = getRandomMissingCube();

        if (i == 10 || i == 13 || i == 16 || i == 19)
        {
            if (golemCubes[i - 1] == 30)
            {
                return i - 1;
            }
            else
            {
                saveGolemCube((byte) i, golemCubes[i - 1]);
                return i - 1;
            }
        }

        if (i == 11 || i == 14 || i == 17 || i == 20)
        {
            if (golemCubes[i - 2] == 30 && golemCubes[i - 1] == 30) { return i - 2; }
            if (golemCubes[i - 1] == 30)
            {
                saveGolemCube((byte) (i - 1), golemCubes[i - 2]);
                return i - 2;
            }
            else
            {
                saveGolemCube((byte) i, golemCubes[i - 1]);
                saveGolemCube((byte) (i - 1), golemCubes[i - 2]);
                return i - 2;
            }
        }
        return i;
    }

    public int rollRotationOffset()
    {
        int leftLeg = 0;
        int rightLeg = 0;
        if (golemCubes[15] != 30)
        {
            leftLeg++;
        }
        if (golemCubes[16] != 30)
        {
            leftLeg++;
        }
        if (golemCubes[17] != 30)
        {
            leftLeg++;
        }
        if (golemCubes[18] != 30)
        {
            rightLeg++;
        }
        if (golemCubes[19] != 30)
        {
            rightLeg++;
        }
        if (golemCubes[20] != 30)
        {
            rightLeg++;
        }
        return (leftLeg - rightLeg) * 10;
    }

    /**
     * The chest opens when the Golem is missing cubes and the summoned blocks
     * are close
     * 
     * @return
     */
    public boolean openChest()
    {
        if (isMissingCubes())
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(2D, 2D, 2D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity1 = (Entity) list.get(i);
                if (entity1 instanceof MoCEntityThrowableRock)
                {
                    if (MoCreatures.proxy.getParticleFX() > 0)
                    {
                        MoCreatures.proxy.VacuumFX(this);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Converts the world block into the golem block texture if not found,
     * returns -1
     * 
     * @param blockType
     * @return
     */
    private byte translateOre(int blockType)
    {
        switch (blockType)
        {
        case 0:
            return 0;
        case 1:
            return 0;
        case 18:
            return 10; //leaves
        case 2:
        case 3:
            return 1; //dirt, grass
        case 4:
        case 48:
            return 2; //cobblestones
        case 5:
            return 3;
        case 12:
            return 4;
        case 13:
            return 5;
        case 16:
        case 21:
        case 56:
        case 74:
        case 73:
            return 24; //all ores are transformed into diamond ore
        case 14:
        case 41:
            return 7; //ore gold and block gold = block gold
        case 15:
        case 42:
            return 11;//iron ore and blocks = block iron
        case 57:
            return 15; //block diamond
        case 17:
            return 6; //wood
        case 20:
            return 8;
        case 22:
        case 35: //lapis and cloths
            return 9;
        case 45:
            return 12; //brick
        case 49:
            return 14; //obsidian
        case 58:
            return 16; //workbench
        case 61:
        case 62:
            return 17; //stonebench
        case 78:
        case 79:
            return 18; //ice
        case 81:
            return 19; //cactus
        case 82:
            return 20; //clay
        case 86:
        case 91:
        case 103:
            return 22; //pumpkin pumpkin lantern melon
        case 87:
            return 23; //netherrack
        case 89:
            return 25; //glowstone
        case 98:
            return 26; //stonebrick
        case 112:
            return 27; //netherbrick
        case 129:
        case 133:
            return 21; //emeralds
        default:
            return -1;
        }
    }

    /**
     * Provides the blockID originated from the golem's block
     * 
     * @param golemBlock
     * @return
     */
    private int generateBlock(int golemBlock)
    {
        switch (golemBlock)
        {
        case 0:
            return 1;
        case 1:
            return 3;
        case 2:
            return 4;
        case 3:
            return 5;
        case 4:
            return 12;
        case 5:
            return 13;
        case 6:
            return 17;
        case 7:
            return 41;
        case 8:
            return 20;
        case 9:
            return 35;
        case 10:
            return 18;
        case 11:
            return 42;
        case 12:
            return 45;
        case 13: //unused
            return 2;
        case 14:
            return 49;
        case 15:
            return 57;
        case 16:
            return 58;
        case 17:
            return 51;
        case 18:
            return 79;
        case 19:
            return 81;
        case 20:
            return 82;
        case 21:
            return 133;
        case 22:
            return 86;
        case 23:
            return 87;
        case 24:
            return 56;
        case 25:
            return 89;
        case 26:
            return 98;
        case 27:
            return 112;
        default:
            return 2;
        }
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.4F * (countLegBlocks() / 6F);
    }

    private int countLegBlocks()
    {
        int x = 0;
        for (byte i = 15; i < 21; i++)
        {
            if (golemCubes[i] != 30)
            {
                x++;
            }
        }
        return x;
    }

    /**
     * Used for the power texture used on the golem
     * 
     * @return
     */
    public ResourceLocation getEffectTexture()
    {
        switch (getGolemState())
        {
        case 1:
            return MoCreatures.proxy.getTexture("golemeffect1.png");
        case 2:
            return MoCreatures.proxy.getTexture("golemeffect2.png");
        case 3:
            return MoCreatures.proxy.getTexture("golemeffect3.png");
        case 4:
            return MoCreatures.proxy.getTexture("golemeffect4.png");
        default:
            return null;
        }
    }

    /**
     * Used for the particle FX
     * 
     * @param i
     * @return
     */
    public float colorFX(int i)
    {
        switch (getGolemState())
        {
        case 1:
            if (i == 1) { return 65F / 255F; }
            if (i == 2) { return 157F / 255F; }
            if (i == 3) { return 254F / 255F; }
        case 2:
            if (i == 1) { return 244F / 255F; }
            if (i == 2) { return 248F / 255F; }
            if (i == 3) { return 36F / 255F; }
        case 3:
            if (i == 1) { return 255F / 255F; }
            if (i == 2) { return 154F / 255F; }
            if (i == 3) { return 21F / 255F; }
        case 4:
            if (i == 1) { return 248F / 255F; }
            if (i == 2) { return 10F / 255F; }
            if (i == 3) { return 10F / 255F; }
        }
        return 0;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void func_145780_a(int par1, int par2, int par3, Block par4)
    {
        this.playSound("mocreatures:golemwalk", 1.0F, 1.0F);
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:golemgrunt";
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (super.getCanSpawnHere() && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && (posY > 50D));
    }
}