package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MoCBlock extends Block {

    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", MoCBlock.EnumType.class);

    public MoCBlock(String name, Material material) {
        super(material);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(name);
        GameRegistry.registerBlock(this, MultiItemBlock.class, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, MoCBlock.EnumType.WYVERN_LAIR));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return ((MoCBlock.EnumType) state.getValue(VARIANT)).getMetadata();
    }

    @Override
    public boolean isLeaves(IBlockAccess world, BlockPos pos) {
        return this.getUnlocalizedName().contains("leaves");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        MoCBlock.EnumType[] aenumtype = MoCBlock.EnumType.values();
        int i = aenumtype.length;

        for (int j = 0; j < i; ++j) {
            MoCBlock.EnumType enumtype = aenumtype[j];
            list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, MoCBlock.EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((MoCBlock.EnumType) state.getValue(VARIANT)).getMetadata();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {VARIANT});
    }

    public static enum EnumType implements IStringSerializable {
        WYVERN_LAIR(0, "wyvern_lair"), OGRE_LAIR(1, "ogre_lair");

        private static final MoCBlock.EnumType[] META_LOOKUP = new MoCBlock.EnumType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;

        private static final String __OBFID = "CL_00002081";

        private EnumType(int meta, String name) {
            this(meta, name, name);
        }

        private EnumType(int meta, String name, String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static MoCBlock.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        static {
            MoCBlock.EnumType[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                MoCBlock.EnumType var3 = var0[var2];
                META_LOOKUP[var3.getMetadata()] = var3;
            }
        }
    }
}
