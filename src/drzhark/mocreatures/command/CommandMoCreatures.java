package drzhark.mocreatures.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import drzhark.mocreatures.MoCBiomeGroupData;
import drzhark.mocreatures.MoCConfigCategory;
import drzhark.mocreatures.MoCConfiguration;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCProperty;
import drzhark.mocreatures.MoCProperty.Type;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

public class CommandMoCreatures extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();
    private static List<String> commandsCustomSpawner = new ArrayList<String>();
    private static Map<String, String> commmentMap = new TreeMap<String, String>();

    static {
        commands.add("/moc <entity> biomegroup add <group>");
        commands.add("/moc <entity> biomegroup remove <group>");
        commands.add("/moc <entity> frequency <int>");
        commands.add("/moc <entity> min <int>");
        commands.add("/moc <entity> max <int>");
        commands.add("/moc spawntickrate <int>");
        commands.add("/moc despawnvanilla <boolean>");
        commands.add("/moc despawntickrate <int>");
        commands.add("/moc modifyvanillaspawns <boolean>");
        commands.add("/moc worldgencreaturespawning <boolean>");
        commands.add("/moc maxambient <int>");
        commands.add("/moc maxmobs <int>");
        commands.add("/moc maxanimals <int>");
        commands.add("/moc maxwatermobs <int>");
        commands.add("/moc usecustomspawner <boolean>");
        commands.add("/moc caveogrestrength <float>");
        commands.add("/moc fireogrestrength <float>");
        commands.add("/moc ogreattackrage <int>");
        commands.add("/moc ogrestrength <float>");
        commands.add("/moc caveogrechance <float>");
        commands.add("/moc fireogrechance <int>");
        commands.add("/moc enableownership <boolean>");
        commands.add("/moc enableresetownerscroll <boolean>");
        commands.add("/moc maxtamedperop <int>");
        commands.add("/moc maxtamedperplayer <int>");
        commands.add("/moc attackdolphins <boolean>");
        commands.add("/moc attackhorses <boolean>");
        commands.add("/moc attackwolves <boolean>");
        commands.add("/moc destroydrops <boolean>");
        commands.add("/moc easybreeding <boolean>");
        commands.add("/moc elephantbulldozer <boolean>");
        commands.add("/moc zebrachance <int>");
        commands.add("/moc spawnpiranhas <boolean>");
        commands.add("/moc debuglogging <boolean>");
        commands.add("/moc list biomegroups");
        commands.add("/moc list <entity> biomegroups");
        aliases.add("moc");
       // commandsCustomSpawner.add("spawntickrate");
      //  commandsCustomSpawner.add("despawnvanilla");
       // commandsCustomSpawner.add(")
    }

    public String getCommandName()
    {
        return "mocreatures";
    }

    public List getCommandAliases()
    {
        return aliases;
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.translateString("commands.mocreatures.usage", new Object[0]);
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        String par1 = "";
        if (par2ArrayOfStr.length == 0)
            par1 = "help";
        else par1 = par2ArrayOfStr[0];
        String par2 = "";
        if (par2ArrayOfStr.length > 1)
            par2 = par2ArrayOfStr[1];
        MoCConfiguration config = MoCreatures.proxy.MoCconfig;

        boolean saved = false;
        if (par2ArrayOfStr.length >= 2)
        {

            OUTER: if (par2ArrayOfStr.length <= 4)
            {
                String par3 = "";
                if (par2ArrayOfStr.length >= 3)
                {
                    par3 = par2ArrayOfStr[2];
                }
                // START HELP COMMAND
                if (par1.equalsIgnoreCase("help"))
                {
                    List<String> list = this.getSortedPossibleCommands(par1ICommandSender);
                    byte b0 = 7;
                    int i = (list.size() - 1) / b0;
                    boolean flag = false;
                    ICommand icommand;
                    int j;

                    try
                    {
                        j = par2ArrayOfStr.length == 0 ? 0 : parseIntBounded(par1ICommandSender, par2ArrayOfStr[0], 1, i + 1) - 1;
                    }
                    catch (NumberInvalidException numberinvalidexception)
                    {
                        if (par2 != null)
                        {
                            throw new WrongUsageException(par2, new Object[0]);
                        }

                        throw new CommandNotFoundException();
                    }

                    int k = Math.min((j + 1) * b0, list.size());
                    par1ICommandSender.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + "--- Showing MoCreatures help page " + Integer.valueOf(j + 1) + " of " + Integer.valueOf(i + 1) + "(/moc help <page>)---");

                    for (int l = j * b0; l < k; ++l)
                    {
                        String command = list.get(l);
                        par1ICommandSender.sendChatToPlayer(command);
                    }

                    /*if (j == 0 && par1ICommandSender instanceof EntityPlayer)
                    {
                        par1ICommandSender.sendChatToPlayer(EnumChatFormatting.GREEN + par1ICommandSender.translateString("commands.help.footer", new Object[0]));
                    }*/
                }
                // END HELP COMMAND
                // START LIST COMMAND
                else if (par1.equalsIgnoreCase("list"))
                {
                    if (par2 != null)
                    {
                        if (par2.equalsIgnoreCase("biomegroups") || par2.equalsIgnoreCase("bg"))
                        {
                            for (Map.Entry<String, MoCBiomeGroupData> biomeGroupEntry: MoCreatures.proxy.biomeGroupMap.entrySet())
                            {
                                par1ICommandSender.sendChatToPlayer(biomeGroupEntry.getKey());
                            }
                        }
                    }
                }
                // END LIST COMMAND
                // START ENTITY FREQUENCY/BIOME SECTION
                else if (par2.equalsIgnoreCase("frequency") || par2.equalsIgnoreCase("min") || par2.equalsIgnoreCase("max") || par2.equalsIgnoreCase("biomegroup") || par2.equalsIgnoreCase("bg"))
                {
                    for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.proxy.entityMap.entrySet())
                    {
                        if (entityEntry.getKey().equalsIgnoreCase(par1))
                        {
                            // handle frequencies
                            if (par2.equalsIgnoreCase("frequency") || par2.equalsIgnoreCase("min") || par2.equalsIgnoreCase("max"))
                            {
                                String category = "";
                                if (entityEntry.getValue().getType() == EnumCreatureType.ambient)
                                {
                                    category = MoCreatures.proxy.CATEGORY_MOC_AMBIENT_FREQUENCIES;
                                }
                                else if (entityEntry.getValue().getType() == EnumCreatureType.creature)
                                {
                                    category = MoCreatures.proxy.CATEGORY_MOC_CREATURE_FREQUENCIES;
                                }
                                else if (entityEntry.getValue().getType() == EnumCreatureType.waterCreature)
                                {
                                    category = MoCreatures.proxy.CATEGORY_MOC_WATER_CREATURE_FREQUENCIES;
                                }
                                else if (entityEntry.getValue().getType() == EnumCreatureType.monster)
                                {
                                    category = MoCreatures.proxy.CATEGORY_MOC_MONSTER_FREQUENCIES;
                                }
                                if (par2.equalsIgnoreCase("frequency"))
                                {
                                    try 
                                    {
                                       // System.out.println("Saving frequency " + par3 + " to entity " + entityEntry.getKey());
                                        entityEntry.getValue().frequency = Integer.parseInt(par3);
                                        MoCProperty prop = config.get(category, entityEntry.getKey());
                                        prop.valueList.set(0, par3);
                                        saved = true;
                                        par1ICommandSender.sendChatToPlayer("Set " + entityEntry.getKey() + " frequency to " + par3 + ".");
                                    }
                                    catch(NumberFormatException ex)
                                    {
                                        this.sendCommandHelp(par1ICommandSender);
                                    }
                                }
                                else if (par2.equalsIgnoreCase("min"))
                                {
                                    try 
                                    {
                                       // System.out.println("Saving min " + par3 + " to entity " + entityEntry.getKey());
                                        entityEntry.getValue().minGroup = Integer.parseInt(par3);
                                        MoCProperty prop = config.get(category, entityEntry.getKey());
                                        prop.valueList.set(1, par3);
                                        saved = true;
                                        par1ICommandSender.sendChatToPlayer("Set " + entityEntry.getKey() + " minGroupSpawn to " + par3 + ".");
                                    }
                                    catch(NumberFormatException ex)
                                    {
                                        this.sendCommandHelp(par1ICommandSender);
                                    }
                                }
                                else if (par2.equalsIgnoreCase("max"))
                                {
                                    try 
                                    {
                                       // System.out.println("Saving max " + par3 + " to entity " + entityEntry.getKey());
                                        entityEntry.getValue().maxGroup = Integer.parseInt(par3);
                                        MoCProperty prop = config.get(category, entityEntry.getKey());
                                        prop.valueList.set(2, par3);
                                        saved = true;
                                        par1ICommandSender.sendChatToPlayer("Set " + entityEntry.getKey() + " maxGroupSpawn to " + par3 + ".");
                                    }
                                    catch(NumberFormatException ex)
                                    {
                                        this.sendCommandHelp(par1ICommandSender);
                                    }
                                }
                                break OUTER;
                            }
                            // handle biome groups
                            else if (par2.equalsIgnoreCase("biomegroup") || par2.equalsIgnoreCase("bg"))
                            {
                                //System.out.println("biomegroup test");
                                if (par2ArrayOfStr.length != 4)
                                    break OUTER;
                                String value = par2ArrayOfStr[3].toUpperCase();
                                String category = MoCreatures.proxy.CATEGORY_ENTITY_BIOME_SETTINGS;
                                try 
                                {
                                    ArrayList<String> biomeGroups = entityEntry.getValue().getBiomeGroups();
                                    if (par3.equalsIgnoreCase("add"))
                                    {
                                        if (!biomeGroups.contains(value))
                                        {
                                            if (!MoCreatures.proxy.biomeGroupMap.containsKey(value))
                                            {
                                                par1ICommandSender.sendChatToPlayer("Invalid Biome Group entered. Please choose a biome group from the following list : ");
                                                for (Map.Entry<String, MoCBiomeGroupData> biomeGroupEntry : MoCreatures.proxy.biomeGroupMap.entrySet())
                                                {
                                                    par1ICommandSender.sendChatToPlayer(biomeGroupEntry.getKey());
                                                }
                                                break OUTER;
                                            }
                                            biomeGroups.add(value);
                                            MoCProperty prop = config.get(category, entityEntry.getKey());
                                            prop.valueList.add(value);
                                            Collections.sort(prop.valueList);
                                            saved = true;
                                            par1ICommandSender.sendChatToPlayer("Added biome group " + value + " to entity " + entityEntry.getKey() + ".");
                                            break OUTER;
                                        }
                                        else 
                                        {
                                            par1ICommandSender.sendChatToPlayer("Biome Group " + value + " already exists!!, please choose another from the following list :");
                                            for (int i = 0; i < MoCreatures.proxy.biomeGroupMap.size(); i++)
                                            {
                                                par1ICommandSender.sendChatToPlayer(MoCreatures.proxy.biomeGroupMap.get(i).getBiomeGroupName());
                                            }
                                        }
                                    }
                                    else if (par3.equalsIgnoreCase("rem") || par3.equalsIgnoreCase("remove"))
                                    {
                                        for (int i = 0; i < biomeGroups.size(); i++)
                                        {
                                            if (value.equals(biomeGroups.get(i)))
                                            {
                                                biomeGroups.remove(i);
                                                MoCProperty prop = config.get(category, entityEntry.getKey());
                                                prop.valueList.remove(i);
                                                saved = true;
                                                par1ICommandSender.sendChatToPlayer("Removed biome group " + value.toUpperCase() + " from entity " + entityEntry.getKey() + ".");
                                                break OUTER;
                                            }
                                        }
                                        par1ICommandSender.sendChatToPlayer("Invalid biomegroup entered, please choose from the following list :");
                                        for (int i = 0; i < biomeGroups.size(); i++)
                                        {
                                            par1ICommandSender.sendChatToPlayer(biomeGroups.get(i));
                                        }
                                    }
                                }
                                catch(NumberFormatException ex)
                                {
                                    this.sendCommandHelp(par1ICommandSender);
                                }
                                break OUTER;
                            }
                        }
                    }
                }
                // END ENTITY FREQUENCY/BIOME SECTION
                // START OTHER SECTIONS
                else 
                {
                    for (Map.Entry<String, MoCConfigCategory> catEntry : config.categories.entrySet())
                    {
                        String catName = catEntry.getValue().getQualifiedName();

                        for (Map.Entry<String, MoCProperty> propEntry : catEntry.getValue().entrySet())
                        {
                            if (propEntry.getValue() == null || !propEntry.getKey().equalsIgnoreCase(par1))
                                continue;
                            MoCProperty property = propEntry.getValue();
                            List<String> propList = propEntry.getValue().valueList;
                            String propValue = propEntry.getValue().value;
                            if (propList == null && propValue == null)
                                continue;

                            if (propEntry.getValue().getType() == Type.BOOLEAN)
                            {
                                if (par2.equalsIgnoreCase("true") || par2.equalsIgnoreCase("false"))
                                {
                                    property.set(par2);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer("Set " + propEntry.getKey() + " to " + par2 + ".");
                                }
                            }
                            else if (propEntry.getValue().getType() == Type.INTEGER)
                            {
                                try {
                                    Integer.parseInt(par2);
                                    property.set(par2);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer("Set " + propEntry.getKey() + " to " + par2 + ".");
                                }
                                catch (NumberFormatException ex)
                                {
                                    //System.out.println("Saving int list in category " + catName);
                                    //property.set(valueList);
                                    //saved = true;
                                }
                                
                            }
                            else if (propEntry.getValue().getType() == Type.DOUBLE)
                            {
                                try {
                                    Double.parseDouble(par2);
                                    //System.out.println("Saving double " + par2 + " in category " + catName);
                                    property.set(par2);
                                    saved = true;
                                    par1ICommandSender.sendChatToPlayer("Set " + propEntry.getKey() + " to " + par2 + ".");
                                }
                                catch (NumberFormatException ex)
                                {
                                    //System.out.println("Saving int list in category " + catName);
                                    //property.set(valueList);
                                    //saved = true;
                                }
                            }
                            break OUTER; // exit since we found the property we need to save
                        }
                    }
                }
                // END OTHER SECTIONS
            }
        }
        if (saved)
        {
            config.save();
            MoCreatures.proxy.ConfigInit(MoCreatures.proxy.configPreEvent);
            //MoCreatures.updateSettings();
            MoCreatures.proxy.ConfigPostInit(MoCreatures.proxy.configPostEvent);
            MoCreatures.updateSettings();
        }
        else
        {
            this.sendCommandHelp(par1ICommandSender);
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given ICommandSender.
     */
    protected List getSortedPossibleCommands(ICommandSender par1ICommandSender)
    {
        Collections.sort(this.commands);
        return this.commands;
    }

    public void sendCommandHelp(ICommandSender sender)
    {
        sender.sendChatToPlayer("\u00a72Listing MoCreatures commands");
        for (int i = 0; i < commands.size(); i++)
        {
            sender.sendChatToPlayer(commands.get(i));
        }
    }
}
