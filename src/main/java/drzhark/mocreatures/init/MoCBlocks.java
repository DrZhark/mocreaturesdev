package drzhark.mocreatures.init;

import com.google.common.base.Preconditions;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.block.MoCBlock;
import drzhark.mocreatures.block.MoCBlockDirt;
import drzhark.mocreatures.block.MoCBlockGrass;
import drzhark.mocreatures.block.MoCBlockLeaf;
import drzhark.mocreatures.block.MoCBlockLog;
import drzhark.mocreatures.block.MoCBlockPlanks;
import drzhark.mocreatures.block.MoCBlockRock;
import drzhark.mocreatures.block.MoCBlockTallGrass;
import drzhark.mocreatures.block.MultiItemBlock;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCBlocks {

    public static ArrayList<String> multiBlockNames = new ArrayList<String>();

    public static MoCBlock mocStone = (MoCBlock) new MoCBlockRock("MoCStone").setHardness(1.5F).setResistance(10.0F);
    public static MoCBlock mocGrass = (MoCBlock) new MoCBlockGrass("MoCGrass").setHardness(0.5F);
    public static MoCBlock mocDirt = (MoCBlock) new MoCBlockDirt("MoCDirt").setHardness(0.6F);
    //non terrain generator blocks
    public static MoCBlock mocLeaf = (MoCBlock) new MoCBlockLeaf("MoCLeaves").setHardness(0.2F).setLightOpacity(1);
    public static MoCBlock mocLog = (MoCBlock) new MoCBlockLog("MoCLog").setHardness(2.0F);
    public static MoCBlockTallGrass mocTallGrass = (MoCBlockTallGrass) new MoCBlockTallGrass("MoCTallGrass", true).setHardness(0.0F);
    public static MoCBlock mocPlank = (MoCBlock) new MoCBlockPlanks("MoCWoodPlank").setHardness(2.0F).setResistance(5.0F);

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {
        public static final Set<ItemBlock> ITEM_BLOCKS = new HashSet<>();

        /**
         * Register this mod's {@link Block}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {
                mocStone,
                mocGrass,
                mocDirt,
                mocLeaf,
                mocLog,
                mocTallGrass,
                mocPlank
            };

            //wyvern lair block harvest settings
            mocDirt.setHarvestLevel("shovel", 0, mocDirt.getDefaultState());
            mocGrass.setHarvestLevel("shovel", 0, mocGrass.getDefaultState());
            mocStone.setHarvestLevel("pickaxe", 1, mocStone.getDefaultState());
            multiBlockNames.add("WyvernLair");
            multiBlockNames.add("OgreLair");
            registry.registerAll(blocks);
        }


        /**
         * Register this mod's {@link ItemBlock}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final ItemBlock[] items = {
                    new MultiItemBlock(mocStone),
                    new MultiItemBlock(mocGrass),
                    new MultiItemBlock(mocDirt),
                    new MultiItemBlock(mocLeaf),
                    new MultiItemBlock(mocLog),
                    new MultiItemBlock(mocTallGrass),
                    new MultiItemBlock(mocPlank)
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final ItemBlock item : items) {
                final Block block = item.getBlock();
                final ResourceLocation registryName = Preconditions.checkNotNull(block.getRegistryName(), "Block %s has null registry name", block);
                registry.register(item.setRegistryName(registryName));
                ITEM_BLOCKS.add(item);
                if (!MoCreatures.isServer()) {
                    final String name = item.getUnlocalizedName().replace("tile.", "").replace("MoC", "").toLowerCase();
                    System.out.println("registering custom location " + name);
                    ModelBakery.registerItemVariants(item, new ResourceLocation("mocreatures:wyvern_" + name));
                    ModelBakery.registerItemVariants(item, new ResourceLocation("mocreatures:ogre_" + name));
                    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("mocreatures:wyvern_" + name, "inventory"));
                    ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation("mocreatures:ogre_" + name, "inventory"));
                    ModelLoader.setCustomModelResourceLocation(item, 2, new ModelResourceLocation("mocreatures:wyvern_" + name, "variant=wyvern_lair"));
                    ModelLoader.setCustomModelResourceLocation(item, 3, new ModelResourceLocation("mocreatures:ogre_" + name, "variant=ogre_lair"));
                }
            }
        }
    }
}
