package drzhark.mocreatures.entity.ambient;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;

public class MoCEntityFly extends MoCEntityInsect
{
    public MoCEntityFly(World world)
    {
        super(world);
        texture = "fly.png";
    }

    private int soundCount;// = 50;

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {

            if (getIsFlying() && rand.nextInt(200) == 0)
            {
                setIsFlying(false);
            }

            EntityPlayer ep = worldObj.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --soundCount == -1)
            {
                MoCTools.playCustomSound(this, "fly", this.worldObj);
                soundCount = 55;
            }

            //TODO
            //not working this causes entities to attack it and creepers to blow!
            /*if (entityToAttack == null && worldObj.rand.nextInt(100) == 0)
            {

                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12D, 8D, 12D));
                for (int i = 0; i < list.size(); i++)
                {
                    Entity entity = (Entity) list.get(i);
                    if (!(entity instanceof EntityMob))
                    {
                        continue;
                    }
                    EntityMob entitymob = (EntityMob) entity;
                    entitymob.setAttackTarget(this);
                    if (entitymob instanceof EntityZombie)
                    {
                        this.entityToAttack = entitymob;
                    }
                    if (entitymob instanceof MoCEntityHorseMob)
                    {
                        if (((MoCEntityHorseMob) entitymob).getType() == 23)
                        {
                            ;
                        }
                        {
                            this.entityToAttack = entitymob;
                        }
                        //((MoCEntityOgre) entitymob).attackTime = 20;
                    }
                }

            }*/

        }
    }

    @Override
    protected float getFlyingSpeed()
    {
        return 0.7F;
    }

    @Override
    protected float getWalkingSpeed()
    {
        return 0.3F;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.rotten_flesh;
    }
}