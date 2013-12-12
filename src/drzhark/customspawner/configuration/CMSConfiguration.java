/**
 * This software is provided under the terms of the Minecraft Forge Public
 * License v1.0.
 */

package drzhark.customspawner.configuration;

import static drzhark.customspawner.configuration.CMSProperty.Type.BOOLEAN;
import static drzhark.customspawner.configuration.CMSProperty.Type.DOUBLE;
import static drzhark.customspawner.configuration.CMSProperty.Type.INTEGER;
import static drzhark.customspawner.configuration.CMSProperty.Type.STRING;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.FMLInjectionData;

/**
 * This class offers advanced configurations capabilities, allowing to provide
 * various categories for CMSConfiguration variables.
 */
public class CMSConfiguration
{
    private static boolean[] configMarkers = new boolean[Item.itemsList.length];
    private static final int ITEM_SHIFT = 256;
    private static final int MAX_BLOCKS = 4096;

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_BLOCK   = "block";
    public static final String CATEGORY_ITEM    = "item";
    public static final String ALLOWED_CHARS = "._-";
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String CATEGORY_SPLITTER = ".";
    public static final String NEW_LINE;
    private static final Pattern CONFIG_START = Pattern.compile("START: \"([^\\\"]+)\"");
    private static final Pattern CONFIG_END = Pattern.compile("END: \"([^\\\"]+)\"");
    public static final CharMatcher allowedProperties = CharMatcher.JAVA_LETTER_OR_DIGIT.or(CharMatcher.anyOf(ALLOWED_CHARS));
    private static CMSConfiguration PARENT = null;

    public File file;

    public Map<String, CMSConfigCategory> categories = new TreeMap<String, CMSConfigCategory>();
    private Map<String, CMSConfiguration> children = new TreeMap<String, CMSConfiguration>();

    private boolean caseSensitiveCustomCategories;
    public String defaultEncoding = DEFAULT_ENCODING;
    private String fileName = null;
    public boolean isChild = false;
    private boolean changed = false;

    static
    {
        Arrays.fill(configMarkers, false);
        NEW_LINE = System.getProperty("line.separator");
    }

    public CMSConfiguration(){}

    /**
     * Create a configuration file for the file given in parameter.
     */
    public CMSConfiguration(File file)
    {
        this.file = file;
        String basePath = ((File)(FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");
        String path = file.getAbsolutePath().replace(File.separatorChar, '/').replace("/./", "/").replace(basePath, "");
        if (PARENT != null)
        {
            PARENT.setChild(path, this);
            isChild = true;
        }
        else
        {
            fileName = path;
            load();
        }
    }

    public CMSConfiguration(File file, boolean caseSensitiveCustomCategories)
    {
        this(file);
        this.caseSensitiveCustomCategories = caseSensitiveCustomCategories;
    }

    public CMSConfiguration(File file, boolean caseSensitiveCustomCategories, boolean useNewLine)
    {
        this(file);
    }

    /**
     * Gets or create a block id property. If the block id property key is
     * already in the configuration, then it will be used. Otherwise,
     * defaultId will be used, except if already taken, in which case this
     * will try to determine a free default id.
     */
    public CMSProperty getBlock(String key, int defaultID) { return getBlock(CATEGORY_BLOCK, key, defaultID, null); }
    public CMSProperty getBlock(String key, int defaultID, String comment) { return getBlock(CATEGORY_BLOCK, key, defaultID, comment); }
    public CMSProperty getBlock(String category, String key, int defaultID) { return getBlockInternal(category, key, defaultID, null, 256, Block.blocksList.length); }
    public CMSProperty getBlock(String category, String key, int defaultID, String comment) { return getBlockInternal(category, key, defaultID, comment, 256, Block.blocksList.length); }

    /**
     * Special version of getBlock to be used when you want to garentee the ID you get is below 256
     * This should ONLY be used by mods who do low level terrain generation, or ones that add new
     * biomes.
     * EXA: ExtraBiomesXL
     * 
     * Specifically, if your block is used BEFORE the Chunk is created, and placed in the terrain byte array directly.
     * If you add a new biome and you set the top/filler block, they need to be <256, nothing else.
     * 
     * If you're adding a new ore, DON'T call this function.
     * 
     * Normal mods such as '50 new ores' do not need to be below 256 so should use the normal getBlock
     */
    public CMSProperty getTerrainBlock(String category, String key, int defaultID, String comment)
    {
        return getBlockInternal(category, key, defaultID, comment, 0, 256); 
    }

    private CMSProperty getBlockInternal(String category, String key, int defaultID, String comment, int lower, int upper)
    {
        CMSProperty prop = get(category, key, -1, comment);

        if (prop.getInt() != -1)
        {
            configMarkers[prop.getInt()] = true;
            return prop;
        }
        else
        {
            if (defaultID < lower)
            {
                FMLLog.warning(
                    "Mod attempted to get a block ID with a default in the Terrain Generation section, " +
                    "mod authors should make sure there defaults are above 256 unless explicitly needed " +
                    "for terrain generation. Most ores do not need to be below 256.");
                FMLLog.warning("Config \"%s\" Category: \"%s\" Key: \"%s\" Default: %d", fileName, category, key, defaultID);
                defaultID = upper - 1;
            }

            if (Block.blocksList[defaultID] == null && !configMarkers[defaultID])
            {
                prop.value = Integer.toString(defaultID);
                configMarkers[defaultID] = true;
                return prop;
            }
            else
            {
                for (int j = upper - 1; j > 0; j--)
                {
                    if (Block.blocksList[j] == null && !configMarkers[j])
                    {
                        prop.value = Integer.toString(j);
                        configMarkers[j] = true;
                        return prop;
                    }
                }

                throw new RuntimeException("No more block ids available for " + key);
            }
        }
    }

    public CMSProperty getItem(String key, int defaultID) { return getItem(CATEGORY_ITEM, key, defaultID, null); }
    public CMSProperty getItem(String key, int defaultID, String comment) { return getItem(CATEGORY_ITEM, key, defaultID, comment); }
    public CMSProperty getItem(String category, String key, int defaultID) { return getItem(category, key, defaultID, null); }

    public CMSProperty getItem(String category, String key, int defaultID, String comment)
    {
        CMSProperty prop = get(category, key, -1, comment);
        int defaultShift = defaultID + ITEM_SHIFT;

        if (prop.getInt() != -1)
        {
            configMarkers[prop.getInt() + ITEM_SHIFT] = true;
            return prop;
        }
        else
        {
            if (defaultID < MAX_BLOCKS - ITEM_SHIFT)
            {
                FMLLog.warning(
                    "Mod attempted to get a item ID with a default value in the block ID section, " +
                    "mod authors should make sure there defaults are above %d unless explicitly needed " +
                    "so that all block ids are free to store blocks.", MAX_BLOCKS - ITEM_SHIFT);
                FMLLog.warning("Config \"%s\" Category: \"%s\" Key: \"%s\" Default: %d", fileName, category, key, defaultID);
            }

            if (Item.itemsList[defaultShift] == null && !configMarkers[defaultShift] && defaultShift >= Block.blocksList.length)
            {
                prop.set(defaultID);
                configMarkers[defaultShift] = true;
                return prop;
            }
            else
            {
                for (int x = Item.itemsList.length - 1; x >= ITEM_SHIFT; x--)
                {
                    if (Item.itemsList[x] == null && !configMarkers[x])
                    {
                        prop.set(x - ITEM_SHIFT);
                        configMarkers[x] = true;
                        return prop;
                    }
                }

                throw new RuntimeException("No more item ids available for " + key);
            }
        }
    }

    public CMSProperty get(String category, String key)
    {
        CMSConfigCategory cat = getCategory(category);
        if (cat.containsKey(key))
        {
            CMSProperty prop = cat.get(key);
            return prop;
        }
        return null;
    }

    public CMSProperty get(String category, String key, int defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, int defaultValue, String comment)
    {
        CMSProperty prop = get(category, key, Integer.toString(defaultValue), comment, INTEGER);
        if (!prop.isIntValue())
        {
            prop.set(defaultValue);
        }
        return prop;
    }

    public CMSProperty get(String category, String key, boolean defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, boolean defaultValue, String comment)
    {
        CMSProperty prop = get(category, key, Boolean.toString(defaultValue), comment, BOOLEAN);
        if (!prop.isBooleanValue())
        {
            prop.set(defaultValue);
        }
        return prop;
    }

    public CMSProperty get(String category, String key, double defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, double defaultValue, String comment)
    {
        CMSProperty prop = get(category, key, Double.toString(defaultValue), comment, DOUBLE);
        if (!prop.isDoubleValue())
        {
            prop.set(defaultValue);
        }
        return prop;
    }

    public CMSProperty get(String category, String key, String defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, String defaultValue, String comment)
    {
        return get(category, key, defaultValue, comment, STRING);
    }

    public CMSProperty get(String category, String key, List<String> defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, List<String> defaultValue, String comment)
    {
        return get(category, key, defaultValue, comment, STRING);
    }

    public CMSProperty get(String category, String key, int[] defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, int[] defaultValue, String comment)
    {
        List<String> values = new ArrayList();
        for (int i = 0; i < defaultValue.length; i++)
        {
            values.add(Integer.toString(defaultValue[i]));
        }

        CMSProperty prop =  get(category, key, values, comment, INTEGER);
        if (!prop.isIntList())
        {
            prop.valueList = values;
        }

        return prop;
    }

    public CMSProperty get(String category, String key, double[] defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, double[] defaultValue, String comment)
    {
        List<String> values = new ArrayList();
        for (int i = 0; i < defaultValue.length; i++)
        {
            values.add(Double.toString(defaultValue[i]));
        }

        CMSProperty prop =  get(category, key, values, comment, DOUBLE);
        
        if (!prop.isDoubleList())
        {
            prop.valueList = values;
        }

        return prop;
    }

    public CMSProperty get(String category, String key, boolean[] defaultValue)
    {
        return get(category, key, defaultValue, null);
    }

    public CMSProperty get(String category, String key, boolean[] defaultValue, String comment)
    {
        List<String> values = new ArrayList();
        for (int i = 0; i < defaultValue.length; i++)
        {
            values.add(Boolean.toString(defaultValue[i]));
        }

        CMSProperty prop =  get(category, key, values, comment, BOOLEAN);
        
        if (!prop.isBooleanList())
        {
            prop.valueList = values;
        }

        return prop;
    }

    public CMSProperty get(String category, String key, String defaultValue, String comment, CMSProperty.Type type)
    {
        if (!caseSensitiveCustomCategories)
        {
            category = category.toLowerCase(Locale.ENGLISH);
        }

        CMSConfigCategory cat = getCategory(category);

        if (cat.containsKey(key))
        {
            CMSProperty prop = cat.get(key);

            if (prop.getType() == null)
            {
                prop = new CMSProperty(prop.getName(), prop.value, type);
                cat.set(key, prop);
            }

            prop.comment = comment;
            return prop;
        }
        else if (defaultValue != null)
        {
            CMSProperty prop = new CMSProperty(key, defaultValue, type);
            prop.set(defaultValue); //Set and mark as dirty to signify it should save 
            cat.put(key, prop);
            prop.comment = comment;
            return prop;
        }
        else
        {
            return null;
        }
    }

    public CMSProperty get(String category, String key, List<String> defaultValue, String comment, CMSProperty.Type type)
    {
        if (!caseSensitiveCustomCategories)
        {
            category = category.toLowerCase(Locale.ENGLISH);
        }

        CMSConfigCategory cat = getCategory(category);

        if (cat.containsKey(key))
        {
            CMSProperty prop = cat.get(key);

            if (prop.getType() == null)
            {
                prop = new CMSProperty(prop.getName(), prop.getString(), type);
                cat.put(key, prop);
            }

            prop.comment = comment;

            return prop;
        }
        else if (defaultValue != null)
        {
            CMSProperty prop = new CMSProperty(key, defaultValue, type);
            prop.comment = comment;
            cat.put(key, prop);
            return prop;
        }
        else
        {
            return null;
        }
    }

    public boolean hasCategory(String category)
    {
        return categories.get(category) != null;
    }

    public boolean hasKey(String category, String key)
    {
        CMSConfigCategory cat = categories.get(category);
        return cat != null && cat.containsKey(key);
    }

    public void load()
    {
        if (PARENT != null && PARENT != this)
        {
            return;
        }

        BufferedReader buffer = null;
        UnicodeInputStreamReader input = null;
        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() && !file.createNewFile())
            {
                return;
            }

            if (file.canRead())
            {
                input = new UnicodeInputStreamReader(new FileInputStream(file), defaultEncoding);
                defaultEncoding = input.getEncoding();
                buffer = new BufferedReader(input);

                String line;
                CMSConfigCategory currentCat = null;
                CMSProperty.Type type = null;
                ArrayList<String> tmpList = null;
                int lineNum = 0;
                String name = null;

                while (true)
                {
                    lineNum++;
                    line = buffer.readLine();
                    if (line == null)
                    {
                        break;
                    }

                    Matcher start = CONFIG_START.matcher(line);
                    Matcher end = CONFIG_END.matcher(line);

                    if (start.matches())
                    {
                        fileName = start.group(1);

                        categories = new TreeMap<String, CMSConfigCategory>();
                        continue;
                    }
                    else if (end.matches())
                    {
                        fileName = end.group(1);
                        CMSConfiguration child = new CMSConfiguration();
                        child.categories = categories;
                        this.children.put(fileName, child);
                        continue;
                    }

                    int nameStart = -1, nameEnd = -1;
                    boolean skip = false;
                    boolean quoted = false;
                    boolean newline = true;

                    for (int i = 0; i < line.length() && !skip; ++i)
                    {
                        if (Character.isLetterOrDigit(line.charAt(i)) || ALLOWED_CHARS.indexOf(line.charAt(i)) != -1 || (quoted && line.charAt(i) != '"'))
                        {
                            if (nameStart == -1)
                            {
                                nameStart = i;
                            }

                            nameEnd = i;
                        }
                        else if (Character.isWhitespace(line.charAt(i)))
                        {
                            // ignore space charaters
                        }
                        else
                        {
                            switch (line.charAt(i))
                            {
                                case '#':
                                    skip = true;
                                    continue;

                                case '"':
                                    if (quoted)
                                    {
                                        quoted = false;
                                    }
                                    if (!quoted && nameStart == -1)
                                    {
                                        quoted = true;
                                    }
                                    break;

                                case '{':
                                    name = line.substring(nameStart, nameEnd + 1);
                                    String qualifiedName = CMSConfigCategory.getQualifiedName(name, currentCat);

                                    CMSConfigCategory cat = categories.get(qualifiedName);
                                    if (cat == null)
                                    {
                                        currentCat = new CMSConfigCategory(name, currentCat);
                                        categories.put(qualifiedName, currentCat);
                                    }
                                    else
                                    {
                                        currentCat = cat;
                                    }
                                    name = null;

                                    break;

                                case '}':
                                    if (currentCat == null)
                                    {
                                        throw new RuntimeException(String.format("Config file corrupt, attepted to close to many categories '%s:%d'", fileName, lineNum));
                                    }
                                    currentCat = currentCat.parent;

                                    break;

                                case '=':
                                    name = line.substring(nameStart, nameEnd + 1);

                                    if (currentCat == null)
                                    {
                                        throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", name, fileName, lineNum));
                                    }

                                    CMSProperty prop = new CMSProperty(name, line.substring(i + 1), type, true);
                                    i = line.length();

                                    currentCat.set(name, prop);

                                    break;

                                case ':':
                                    type = CMSProperty.Type.tryParse(line.substring(nameStart, nameEnd + 1).charAt(0));
                                    nameStart = nameEnd = -1;
                                    break;

                                case '<':

                                    if (tmpList != null)
                                    {
                                        throw new RuntimeException(String.format("Malformed list CMSProperty \"%s:%d\"", fileName, lineNum));
                                    }

                                    name = line.substring(nameStart, nameEnd + 1);

                                    if (currentCat == null)
                                    {
                                        throw new RuntimeException(String.format("'%s' has no scope in '%s:%d'", name, fileName, lineNum));
                                    }

                                    tmpList = new ArrayList<String>();

                                    if ((line.length() > i+1))
                                    {
                                        if (line.charAt(i+1) == '>')
                                        {
                                            i++;
                                        }
                                        else 
                                        {
                                            line = line.substring(i+1, line.length());
                                            String[] values = line.split(":|\\>");
                                            for (int j = 0; j < values.length; j++)
                                            {
                                                tmpList.add(values[j]);
                                            }
                                            i = line.length() - 1;
                                        }
                                    }
                                    else
                                    {
                                        skip = true;
                                        break;
                                    }

                                case '>':
                                    if (tmpList == null)
                                    {
                                        throw new RuntimeException(String.format("Malformed list CMSProperty \"%s:%d\"", fileName, lineNum));
                                    }
                                    currentCat.set(name, new CMSProperty(name, tmpList, type));
                                    name = null;
                                    tmpList = null;
                                    type = null;
                                    break;

                                default:
                                    throw new RuntimeException(String.format("Unknown character '%s' in '%s:%d'", line.charAt(i), fileName, lineNum));
                            }
                        }
                    }

                    if (quoted)
                    {
                        throw new RuntimeException(String.format("Unmatched quote in '%s:%d'", fileName, lineNum));
                    }
                    else if (tmpList != null && !skip)
                    {
                        tmpList.add(line.trim());
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (buffer != null)
            {
                try
                {
                    buffer.close();
                } catch (IOException e){}
            }
            if (input != null)
            {
                try
                {
                    input.close();
                } catch (IOException e){}
            }
        }

        resetChangedState();
    }

    public void save()
    {
        if (PARENT != null && PARENT != this)
        {
            PARENT.save();
            return;
        }

        try
        {
            if (file.getParentFile() != null)
            {
                file.getParentFile().mkdirs();
            }

            if (!file.exists() && !file.createNewFile())
            {
                return;
            }
            if (file.canWrite())
            {
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter buffer = new BufferedWriter(new OutputStreamWriter(fos, defaultEncoding));

                buffer.write("# Configuration file" + NEW_LINE + NEW_LINE);

                if (children.isEmpty())
                {
                    save(buffer);
                }
                else
                {
                    for (Map.Entry<String, CMSConfiguration> entry : children.entrySet())
                    {
                        buffer.write("START: \"" + entry.getKey() + "\"" + NEW_LINE);
                        entry.getValue().save(buffer);
                        buffer.write("END: \"" + entry.getKey() + "\"" + NEW_LINE + NEW_LINE);
                    }
                }

                buffer.close();
                fos.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void save(BufferedWriter out) throws IOException
    {
        for (CMSConfigCategory cat : categories.values())
        {
            if (!cat.isChild()) // if category has no parent categories
            {
                cat.write(out, 0);
                out.newLine();
            }
        }
    }

    public CMSConfigCategory getCategory(String category)
    {
        CMSConfigCategory ret = categories.get(category.toLowerCase());

        if (ret == null)
        {
            if (category.contains(CATEGORY_SPLITTER))
            {
                String[] hierarchy = category.split("\\"+CATEGORY_SPLITTER);
                CMSConfigCategory parent = categories.get(hierarchy[0]);

                if (parent == null)
                {
                    parent = new CMSConfigCategory(hierarchy[0]);
                    categories.put(parent.getQualifiedName(), parent);
                    changed = true;
                }

                for (int i = 1; i < hierarchy.length; i++)
                {
                    String name = CMSConfigCategory.getQualifiedName(hierarchy[i], parent);
                    CMSConfigCategory child = categories.get(name);

                    if (child == null)
                    {
                        child = new CMSConfigCategory(hierarchy[i], parent);
                        categories.put(name, child);
                        changed = true;
                    }

                    ret = child;
                    parent = child;
                }
            }
            else
            {
                ret = new CMSConfigCategory(category);
                categories.put(category, ret);
                changed = true;
            }
        }

        return ret;
    }

    public void removeCategory(CMSConfigCategory category)
    {
        for (CMSConfigCategory child : category.getChildren())
        {
            removeCategory(child);
        }

        if (categories.containsKey(category.getQualifiedName()))
        {
            categories.remove(category.getQualifiedName());
            if (category.parent != null)
            {
                category.parent.removeChild(category);
            }
            changed = true;
        }
    }

    public void addCustomCategoryComment(String category, String comment)
    {
        if (!caseSensitiveCustomCategories)
            category = category.toLowerCase(Locale.ENGLISH);
        getCategory(category).setComment(comment);
    }

    private void setChild(String name, CMSConfiguration child)
    {
        if (!children.containsKey(name))
        {
            children.put(name, child);
            changed = true;
        }
        else
        {
            CMSConfiguration old = children.get(name);
            child.categories = old.categories;
            child.fileName = old.fileName;
            old.changed = true;
        }
    }

    public static void enableGlobalConfig()
    {
        PARENT = new CMSConfiguration(new File(Loader.instance().getConfigDir(), "global.cfg"));
        PARENT.load();
    }

    public static class UnicodeInputStreamReader extends Reader
    {
        private final InputStreamReader input;
        private final String defaultEnc;

        public UnicodeInputStreamReader(InputStream source, String encoding) throws IOException
        {
            defaultEnc = encoding;
            String enc = encoding;
            byte[] data = new byte[4];

            PushbackInputStream pbStream = new PushbackInputStream(source, data.length);
            int read = pbStream.read(data, 0, data.length);
            int size = 0;

            int bom16 = (data[0] & 0xFF) << 8 | (data[1] & 0xFF);
            int bom24 = bom16 << 8 | (data[2] & 0xFF);
            int bom32 = bom24 << 8 | (data[3] & 0xFF);

            if (bom24 == 0xEFBBBF)
            {
                enc = "UTF-8";
                size = 3;
            }
            else if (bom16 == 0xFEFF)
            {
                enc = "UTF-16BE";
                size = 2;
            }
            else if (bom16 == 0xFFFE)
            {
                enc = "UTF-16LE";
                size = 2;
            }
            else if (bom32 == 0x0000FEFF)
            {
                enc = "UTF-32BE";
                size = 4;
            }
            else if (bom32 == 0xFFFE0000) //This will never happen as it'll be caught by UTF-16LE,
            {                             //but if anyone ever runs across a 32LE file, i'd like to disect it.
                enc = "UTF-32LE";
                size = 4;
            }

            if (size < read)
            {
                pbStream.unread(data, size, read - size);
            }

            this.input = new InputStreamReader(pbStream, enc);
        }

        public String getEncoding()
        {
            return input.getEncoding();
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException
        {
            return input.read(cbuf, off, len);
        }

        @Override
        public void close() throws IOException
        {
            input.close();
        }
    }

    public boolean hasChanged()
    {
        if (changed) return true;
        
        for (CMSConfigCategory cat : categories.values())
        {
            if (cat.hasChanged()) return true;
        }

        for (CMSConfiguration child : children.values())
        {
            if (child.hasChanged()) return true;
        }

        return false;
    }

    private void resetChangedState()
    {
        changed = false;
        for (CMSConfigCategory cat : categories.values())
        {
            cat.resetChangedState();
        }

        for (CMSConfiguration child : children.values())
        {
            child.resetChangedState();
        }
    }

    public Set<String> getCategoryNames()
    {
        return ImmutableSet.copyOf(categories.keySet());
    }

    public String getFileName()
    {
        if (this.file != null)
        {
            String fullName = this.file.getName();
            return fullName.substring(0, fullName.indexOf('.'));
        }
        return "undefined";
    }

    public File getFile()
    {
        return this.file;
    }
}