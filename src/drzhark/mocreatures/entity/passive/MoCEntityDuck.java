package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

public class MoCEntityDuck extends MoCEntityAnimal//EntityChicken
{
    public boolean field_70885_d = false;
    public float field_70886_e = 0.0F;
    public float destPos = 0.0F;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

    public MoCEntityDuck(World world)
    {
        super(world);
        texture = "duck.png";
        setSize(0.3F, 0.4F);
        //health = 4;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:duckhurt";
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:duckhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "mocreatures:duck";
    }

    @Override
    protected boolean canDespawn()
    {
        if (MoCreatures.proxy.forceDespawns)
            return !getIsTamed();
        else return false;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F)
        {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F)
        {
            this.destPos = 1.0F;
        }

        if (!this.onGround && this.field_70889_i < 1.0F)
        {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float) ((double) this.field_70889_i * 0.9D);

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        this.field_70886_e += this.field_70889_i * 2.0F;
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    protected Item getDropItem()
    {
        return Items.feather;
    }
}