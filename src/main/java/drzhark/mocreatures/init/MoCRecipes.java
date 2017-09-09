package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCRecipes {

    private static final ResourceLocation MOCREATURES_RESOURCE_GROUP = new ResourceLocation("mocreatures");

    private static void addShapedRecipe(ResourceLocation resource, ItemStack output, Object... params) {
        GameRegistry.addShapedRecipe(resource, MOCREATURES_RESOURCE_GROUP, output, params);
    }

    private static void addShapedRecipe(ItemStack output, Object... params) {
        GameRegistry.addShapedRecipe(output.getItem().getRegistryName(), MOCREATURES_RESOURCE_GROUP, output, params);
    }

    private static void addShapelessRecipe(ResourceLocation resource, ItemStack output, Ingredient ingredient) {
        GameRegistry.addShapelessRecipe(resource, MOCREATURES_RESOURCE_GROUP, output, ingredient);
    }

    private static void addShapelessRecipe(ItemStack output, Ingredient ingredient) {
        GameRegistry.addShapelessRecipe(output.getItem().getRegistryName(), MOCREATURES_RESOURCE_GROUP, output, ingredient);
    }

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {

            GameRegistry.addSmelting(MoCItems.crabraw, new ItemStack(MoCItems.crabcooked, 1), 0F);

            GameRegistry.addSmelting(MoCItems.ratRaw, new ItemStack(MoCItems.ratCooked, 1), 0F);

            GameRegistry.addSmelting(MoCItems.ostrichraw, new ItemStack(MoCItems.ostrichcooked, 1), 0F);

            GameRegistry.addSmelting(MoCItems.rawTurkey, new ItemStack(MoCItems.cookedTurkey, 1), 0F);

            GameRegistry.addSmelting(MoCItems.mocegg, new ItemStack(MoCItems.omelet, 1), 0F);

            GameRegistry.addSmelting(Items.EGG, new ItemStack(MoCItems.omelet, 1), 0F);

            addShapelessRecipe(new ItemStack(MoCItems.scrollFreedom, 1), Ingredient.fromItems(Items.PAPER, Items.FEATHER, Items.REDSTONE));

            addShapelessRecipe(new ItemStack(MoCItems.scrollFreedom, 1), Ingredient.fromItems(MoCItems.scrollOfSale, Items.REDSTONE));

            addShapelessRecipe(new ItemStack(MoCItems.scrollOfSale, 1), Ingredient.fromItems(Items.PAPER, Items.FEATHER));

            addShapelessRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Items.LEATHER.getUnlocalizedName().replace("item.", "")), new ItemStack(Items.LEATHER, 1), Ingredient.fromItems(MoCItems.animalHide));

            addShapelessRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Blocks.WOOL.getUnlocalizedName().replace("item.", "")), new ItemStack(Blocks.WOOL, 1), Ingredient.fromItems(MoCItems.fur));

            addShapelessRecipe(new ItemStack(MoCItems.scorpSwordNether, 1), Ingredient.fromItems(Items.DIAMOND_SWORD, MoCItems.scorpStingNether, MoCItems.scorpStingNether,
                    MoCItems.scorpStingNether));

            addShapelessRecipe(new ItemStack(MoCItems.scorpSwordFrost, 1), Ingredient.fromItems(Items.DIAMOND_SWORD, MoCItems.scorpStingFrost, MoCItems.scorpStingFrost,
                    MoCItems.scorpStingFrost));

            addShapelessRecipe(new ItemStack(MoCItems.scorpSwordCave, 1), Ingredient.fromItems(Items.DIAMOND_SWORD, MoCItems.scorpStingCave, MoCItems.scorpStingCave,
                    MoCItems.scorpStingCave));

            addShapelessRecipe(new ItemStack(MoCItems.scorpSwordDirt, 1), Ingredient.fromItems(Items.DIAMOND_SWORD, MoCItems.scorpStingDirt, MoCItems.scorpStingDirt,
                    MoCItems.scorpStingDirt));

            addShapelessRecipe(new ItemStack(MoCItems.turtlesoup, 1), Ingredient.fromStacks(new ItemStack(MoCItems.turtleraw, 1), new ItemStack(Items.BOWL, 1)));

            addShapelessRecipe(new ItemStack(MoCItems.essencelight, 1), Ingredient.fromItems(MoCItems.essenceundead, MoCItems.essencefire, MoCItems.essencedarkness));

            addShapedRecipe(new ItemStack(MoCItems.fishnet, 1),
                    new Object[] {" # ", "S#S", "#S#", Character.valueOf('#'), Items.STRING, Character.valueOf('S'), MoCItems.sharkteeth});

            addShapedRecipe(new ItemStack(MoCItems.tusksWood, 1),
                    new Object[] {"X  ", "XR ", "XXX", Character.valueOf('X'), Blocks.PLANKS, Character.valueOf('R'), Items.LEAD});

            addShapedRecipe(new ItemStack(MoCItems.tusksIron, 1),
                    new Object[] {"X  ", "XR ", "XXX", Character.valueOf('X'), Items.IRON_INGOT, Character.valueOf('R'), Items.LEAD});

            addShapedRecipe(new ItemStack(MoCItems.tusksDiamond, 1),
                    new Object[] {"X  ", "XR ", "XXX", Character.valueOf('X'), Items.DIAMOND, Character.valueOf('R'), Items.LEAD});

            addShapedRecipe(new ItemStack(MoCItems.mammothPlatform, 1),
                    new Object[] {"WRW", "PPP", "WRW", Character.valueOf('W'), Blocks.LOG, Character.valueOf('R'), Items.LEAD, Character.valueOf('P'),
                            Blocks.PLANKS});

            addShapedRecipe(new ItemStack(MoCItems.elephantChest, 1),
                    new Object[] {" W ", "CHC", " W ", Character.valueOf('H'), MoCItems.animalHide, Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 0),
                            Character.valueOf('C'), Blocks.CHEST});

            addShapedRecipe(new ItemStack(MoCItems.elephantHarness, 1),
                    new Object[] {"HWH", "IWI", "HWH", Character.valueOf('H'), MoCItems.animalHide, Character.valueOf('W'), new ItemStack(Blocks.WOOL, 1, 0),
                            Character.valueOf('I'), Items.IRON_INGOT});

            addShapedRecipe(new ItemStack(MoCItems.elephantHowdah, 1),
                    new Object[] {"SRS", "RYR", "SRS", Character.valueOf('S'), Items.STICK, Character.valueOf('R'), new ItemStack(Blocks.WOOL, 1, 14),
                            Character.valueOf('Y'), new ItemStack(Blocks.WOOL, 1, 4)});

            addShapedRecipe(new ItemStack(MoCItems.elephantGarment, 1), new Object[] {"pyg", "RMR", "BYB", Character.valueOf('R'),
                    new ItemStack(Blocks.WOOL, 1, 14), Character.valueOf('Y'), new ItemStack(Blocks.WOOL, 1, 4), Character.valueOf('B'),
                    new ItemStack(Blocks.WOOL, 1, 11), Character.valueOf('M'), MoCItems.medallion, Character.valueOf('p'), new ItemStack(Items.DYE, 1, 9),
                    Character.valueOf('y'), new ItemStack(Items.DYE, 1, 11), Character.valueOf('g'), new ItemStack(Items.DYE, 1, 10)});

            //Items.dye.itemID
            addShapedRecipe(new ItemStack(MoCItems.ratBurger, 1),
                    new Object[] {"SB ", "GRG", " B ", Character.valueOf('R'), MoCItems.ratCooked, Character.valueOf('B'), Items.BREAD, Character.valueOf('S'),
                            Items.PUMPKIN_SEEDS, Character.valueOf('G'), Items.WHEAT_SEEDS});

            addShapedRecipe(new ItemStack(MoCItems.scorpPlateFrost, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.chitinFrost});

            addShapedRecipe(new ItemStack(MoCItems.scorpHelmetFrost, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.chitinFrost});

            addShapedRecipe(new ItemStack(MoCItems.scorpLegsFrost, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.chitinFrost});

            addShapedRecipe(new ItemStack(MoCItems.scorpBootsFrost, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.chitinFrost});

            addShapedRecipe(new ItemStack(MoCItems.scorpPlateNether, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.chitinNether});

            addShapedRecipe(new ItemStack(MoCItems.scorpHelmetNether, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.chitinNether});

            addShapedRecipe(new ItemStack(MoCItems.scorpLegsNether, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.chitinNether});

            addShapedRecipe(new ItemStack(MoCItems.scorpBootsNether, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.chitinNether});

            addShapedRecipe(new ItemStack(MoCItems.scorpPlateCave, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.chitinCave});

            addShapedRecipe(new ItemStack(MoCItems.scorpHelmetCave, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.chitinCave});

            addShapedRecipe(new ItemStack(MoCItems.scorpLegsCave, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.chitinCave});

            addShapedRecipe(new ItemStack(MoCItems.scorpBootsCave, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.chitinCave});

            addShapedRecipe(new ItemStack(MoCItems.scorpPlateDirt, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.chitin});

            addShapedRecipe(new ItemStack(MoCItems.scorpHelmetDirt, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.chitin});

            addShapedRecipe(new ItemStack(MoCItems.scorpLegsDirt, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.chitin});

            addShapedRecipe(new ItemStack(MoCItems.scorpBootsDirt, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.chitin});

            addShapedRecipe(new ItemStack(MoCItems.chestHide, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.animalHide});

            addShapedRecipe(new ItemStack(MoCItems.helmetHide, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.animalHide});

            addShapedRecipe(new ItemStack(MoCItems.legsHide, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.animalHide});

            addShapedRecipe(new ItemStack(MoCItems.bootsHide, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.animalHide});

            addShapedRecipe(new ItemStack(MoCItems.horsearmorcrystal, 1), new Object[] {"  D", "CDC", "DCD", Character.valueOf('D'), Items.DIAMOND,
                    Character.valueOf('C'), Blocks.GLASS});

            //addShapedRecipe(new ItemStack(horsearmormetal, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), new ItemStack(Blocks.WOOL, 1, 15) });

            //addShapedRecipe(new ItemStack(horsearmorgold, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'), new ItemStack(Blocks.WOOL, 1, 14) });

            //addShapedRecipe(new ItemStack(horsearmordiamond, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), new ItemStack(Blocks.WOOL, 1, 11) });

            addShapedRecipe(new ItemStack(MoCItems.essencelight, 1),
                    new Object[] {"X", "Y", "Z", Character.valueOf('X'), MoCItems.essenceundead, Character.valueOf('Y'), MoCItems.essencefire, Character.valueOf('Z'),
                            MoCItems.essencedarkness});

            addShapedRecipe(new ItemStack(MoCItems.essenceundead, 1), new Object[] {" X ", " Y ", " Z ", Character.valueOf('X'), Items.ROTTEN_FLESH,
                    Character.valueOf('Y'), MoCItems.heartundead, Character.valueOf('Z'), Items.GLASS_BOTTLE});

            addShapedRecipe(new ItemStack(MoCItems.essencefire, 1), new Object[] {" X ", " Y ", " Z ", Character.valueOf('X'), Items.BLAZE_POWDER,
                    Character.valueOf('Y'), MoCItems.heartfire, Character.valueOf('Z'), Items.GLASS_BOTTLE});

            addShapedRecipe(new ItemStack(MoCItems.essencefire, 1),
                    new Object[] {" X ", " Y ", " Z ", Character.valueOf('X'), Blocks.FIRE, Character.valueOf('Y'), MoCItems.heartfire, Character.valueOf('Z'),
                            Items.GLASS_BOTTLE});

            addShapedRecipe(new ItemStack(MoCItems.essencedarkness, 1), new Object[] {" X ", " Y ", " Z ", Character.valueOf('X'), Items.ENDER_PEARL,
                    Character.valueOf('Y'), MoCItems.heartdarkness, Character.valueOf('Z'), Items.GLASS_BOTTLE});

            addShapedRecipe(new ItemStack(MoCItems.chestFur, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.fur});

            addShapedRecipe(new ItemStack(MoCItems.helmetFur, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.fur});

            addShapedRecipe(new ItemStack(MoCItems.legsFur, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.fur});

            addShapedRecipe(new ItemStack(MoCItems.bootsFur, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.fur});

            //addShapedRecipe(new ItemStack(key, 1), new Object[] {"  #", " # ", "X  ", Character.valueOf('#'), Items.stick, Character.valueOf('X'),
            //        Items.iron_ingot,});

            addShapedRecipe(new ItemStack(MoCItems.petamulet, 1),
                    new Object[] {"X X", " Z ", "X X", Character.valueOf('X'), Items.GOLD_NUGGET, Character.valueOf('Z'), Items.DIAMOND});

            addShapedRecipe(
                    new ItemStack(MoCItems.amuletbone, 1),
                    new Object[] {"#X#", "XZX", "#X#", Character.valueOf('#'), Items.BONE, Character.valueOf('X'), Items.GOLD_NUGGET,
                            Character.valueOf('Z'), Items.ENDER_PEARL});

            addShapedRecipe(
                    new ItemStack(MoCItems.amuletghost, 1),
                    new Object[] {"#X#", "XZX", "#X#", Character.valueOf('#'), Items.BONE, Character.valueOf('X'), Items.GOLD_NUGGET,
                            Character.valueOf('Z'), Items.GHAST_TEAR});

            addShapedRecipe(
                    new ItemStack(MoCItems.amuletfairy, 1),
                    new Object[] {"#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.FIRE, Character.valueOf('X'), Items.GOLD_NUGGET,
                            Character.valueOf('Z'), MoCItems.unicornhorn});

            addShapedRecipe(
                    new ItemStack(MoCItems.amuletfairy, 1),
                    new Object[] {"#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.FIRE, Character.valueOf('X'), Items.GOLD_NUGGET,
                            Character.valueOf('Z'), MoCItems.essencelight});

            addShapedRecipe(
                    new ItemStack(MoCItems.amuletpegasus, 1),
                    new Object[] {"#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.FIRE, Character.valueOf('X'), Items.GOLD_NUGGET,
                            Character.valueOf('Z'), Items.DIAMOND});

            addShapedRecipe(new ItemStack(MoCItems.sharksword, 1),
                    new Object[] {"#X#", "#X#", " X ", Character.valueOf('#'), MoCItems.sharkteeth, Character.valueOf('X'), Items.STICK,});

            //addShapedRecipe(new ItemStack(fishbowl_e, 1), new Object[] {"# #", "# #", "###", Character.valueOf('#'), Blocks.glass,});

            //addShapedRecipe(new ItemStack(rope, 1), new Object[] { "# #", " # ", "# #", Character.valueOf('#'), Item.silk, });

            addShapelessRecipe(new ItemStack(MoCItems.petfood, 4), Ingredient.fromStacks(new ItemStack(Items.FISH, 1), new ItemStack(Items.PORKCHOP, 1)));

            addShapedRecipe(new ItemStack(MoCItems.woolball, 1), new Object[] {" # ", "# #", " # ", Character.valueOf('#'), Items.STRING,});

            addShapedRecipe(new ItemStack(MoCItems.litterbox, 1),
                    new Object[] {"###", "#X#", "###", Character.valueOf('#'), Blocks.PLANKS, Character.valueOf('X'), Blocks.SAND,});

            addShapedRecipe(
                    new ItemStack(MoCItems.medallion, 1),
                    new Object[] {"# #", "XZX", " X ", Character.valueOf('#'), Items.LEATHER, Character.valueOf('Z'), Items.DIAMOND,
                            Character.valueOf('X'), Items.GOLD_INGOT,});

            addShapedRecipe(new ItemStack(MoCItems.medallion, 1), new Object[] {"# #", " X ", Character.valueOf('#'), Items.LEATHER,
                    Character.valueOf('X'), Items.GOLD_INGOT,});

            addShapedRecipe(new ItemStack(MoCItems.whip, 1), new Object[] {"#X#", "X X", "# Z", Character.valueOf('#'), MoCItems.bigcatclaw, Character.valueOf('X'),
                    Items.LEATHER, Character.valueOf('Z'), Items.IRON_INGOT});

            addShapedRecipe(new ItemStack(MoCItems.horsesaddle, 1),
                    new Object[] {"XXX", "X#X", "# #", Character.valueOf('#'), Items.IRON_INGOT, Character.valueOf('X'), Items.LEATHER});

            addShapedRecipe(new ItemStack(MoCItems.haystack, 1), new Object[] {"XXX", "XXX", Character.valueOf('X'), Items.WHEAT});

            addShapedRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Items.WHEAT.getUnlocalizedName().replace("item.", "")), new ItemStack(Items.WHEAT, 6), new Object[] {"X", Character.valueOf('X'), MoCItems.haystack});

            addShapedRecipe(new ItemStack(MoCItems.sugarlump, 1), new Object[] {"XX", "##", Character.valueOf('X'), Items.SUGAR, Character.valueOf('#'),
                    Items.SUGAR});

            addShapedRecipe(new ItemStack(MoCItems.horsesaddle, 1), new Object[] {"X", "#", Character.valueOf('X'), Items.SADDLE, Character.valueOf('#'),
                    Items.IRON_INGOT});

            addShapedRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Items.CHAINMAIL_CHESTPLATE.getUnlocalizedName().replace("item.", "")), new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.sharkteeth});

            addShapedRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Items.CHAINMAIL_HELMET.getUnlocalizedName().replace("item.", "")), new ItemStack(Items.CHAINMAIL_HELMET, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.sharkteeth});

            addShapedRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Items.CHAINMAIL_LEGGINGS.getUnlocalizedName().replace("item.", "")), new ItemStack(Items.CHAINMAIL_LEGGINGS, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.sharkteeth});

            addShapedRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Items.CHAINMAIL_BOOTS.getUnlocalizedName().replace("item.", "")), new ItemStack(Items.CHAINMAIL_BOOTS, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.sharkteeth});

            addShapedRecipe(new ItemStack(MoCItems.plateCroc, 1), new Object[] {"X X", "XXX", "XXX", Character.valueOf('X'), MoCItems.hideCroc});

            addShapedRecipe(new ItemStack(MoCItems.helmetCroc, 1), new Object[] {"XXX", "X X", Character.valueOf('X'), MoCItems.hideCroc});

            addShapedRecipe(new ItemStack(MoCItems.legsCroc, 1), new Object[] {"XXX", "X X", "X X", Character.valueOf('X'), MoCItems.hideCroc});

            addShapedRecipe(new ItemStack(MoCItems.bootsCroc, 1), new Object[] {"X X", "X X", Character.valueOf('X'), MoCItems.hideCroc});

            for (int i = 0; i < 16; i++) {
                addShapelessRecipe(new ItemStack(MoCItems.kittybed[i], 1), Ingredient.fromStacks(new ItemStack(Items.DYE, 1, i),
                        new ItemStack(MoCItems.kittybed[0], 1)));

                addShapedRecipe(new ItemStack(MoCItems.kittybed[i], 1),
                        new Object[] {"###", "#X#", "Z  ", Character.valueOf('#'), Blocks.PLANKS, Character.valueOf('X'),
                                new ItemStack(Blocks.WOOL, 1, i), Character.valueOf('Z'), Items.IRON_INGOT,});
                String s = EnumDyeColor.byMetadata(i).getUnlocalizedName();
                s = s.substring(0, 1).toUpperCase() + s.substring(1);
                //LanguageRegistry.addName(new ItemStack(kittybed[i], 1), (s + " Kitty Bed"));
            }

            for (int i = 0; i < MoCBlocks.multiBlockNames.size(); i++) {
                addShapelessRecipe(new ItemStack(MoCBlocks.mocPlank, 4, i), Ingredient.fromStacks(new ItemStack(MoCBlocks.mocLog, 1, i)));
            }

            addShapedRecipe(
                    new ItemStack(MoCItems.staffPortal, 1),
                    new Object[] {"  E", " U ", "R  ", Character.valueOf('E'), Items.ENDER_EYE, Character.valueOf('U'), MoCItems.unicornhorn,
                            Character.valueOf('R'), Items.BLAZE_ROD});

            addShapedRecipe(
                    new ItemStack(MoCItems.staffPortal, 1),
                    new Object[] {"  E", " U ", "R  ", Character.valueOf('E'), Items.ENDER_EYE, Character.valueOf('U'), MoCItems.essencelight,
                            Character.valueOf('R'), Items.BLAZE_ROD});

            addShapelessRecipe(new ResourceLocation(MoCConstants.MOD_ID + ":" + Items.DYE.getUnlocalizedName().replace("item.", "")), new ItemStack(Items.DYE, 1, 15), Ingredient.fromItems(MoCItems.sharkteeth));
        }
    }
}
