package drzhark.mocreatures.configuration;

import static drzhark.mocreatures.configuration.MoCConfiguration.NEW_LINE;
import static drzhark.mocreatures.configuration.MoCConfiguration.allowedProperties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

public class MoCConfigCategory implements Map<String, MoCProperty>
{
    private String name;
    private String comment;
    private ArrayList<MoCConfigCategory> children = new ArrayList<MoCConfigCategory>();
    private Map<String, MoCProperty> properties = new TreeMap<String, MoCProperty>();
    public final MoCConfigCategory parent;
private boolean changed = false;

    public MoCConfigCategory(String name)
    {
        this(name, null, true);
    }

    public MoCConfigCategory(String name, MoCConfigCategory parent)
    {
        this(name, parent, true);
    }

    public MoCConfigCategory(String name, boolean newline)
    {
        this(name, null, newline);
    }

    public MoCConfigCategory(String name, MoCConfigCategory parent, boolean newline)
    {
        this.name = name;
        this.parent = parent;
        if (parent != null)
        {
            parent.children.add(this);
        }
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof MoCConfigCategory)
        {
            MoCConfigCategory cat = (MoCConfigCategory)obj;
            return name.equals(cat.name) && children.equals(cat.children);  
        }
        
        return false;
    }

    public String getQualifiedName()
    {
        return getQualifiedName(name, parent);
    }

    public static String getQualifiedName(String name, MoCConfigCategory parent)
    {
        return (parent == null ? name : parent.getQualifiedName() + MoCConfiguration.CATEGORY_SPLITTER + name);
    }

    public MoCConfigCategory getFirstParent()
    {
        return (parent == null ? this : parent.getFirstParent());
    }

    public boolean isChild()
    {
        return parent != null;
    }

    public Map<String, MoCProperty> getValues()
    {
        return properties;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public boolean containsKey(String key)
    {
        return properties.containsKey(key);
    }

    public MoCProperty get(String key)
    {
        return properties.get(key);
    }

    public void set(String key, MoCProperty value)
    {
        properties.put(key, value);
    }

    private void write(BufferedWriter out, String... data) throws IOException
    {
        write(out, true, data);
    }

    private void write(BufferedWriter out, boolean new_line, String... data) throws IOException
    {
        for (int x = 0; x < data.length; x++)
        {
            if (data[x] != null)
                out.write(data[x]);
        }
        if (new_line) out.write(NEW_LINE);
    }

    public void write(BufferedWriter out, int indent) throws IOException
    {
        String pad0 = getIndent(indent);
        String pad1 = getIndent(indent + 1);
        String pad2 = getIndent(indent + 2);

        write(out, pad0, "####################");
        write(out, pad0, "# ", name);

        if (comment != null)
        {
            write(out, pad0, "#===================");
            Splitter splitter = Splitter.onPattern("\r?\n");

            for (String line : splitter.split(comment))
            {
                write(out, pad0, "# ", line);
            }
        }

        write(out, pad0, "####################", NEW_LINE);

        if (!allowedProperties.matchesAllOf(name))
        {
            name = '"' + name + '"';
        }

        write(out, pad0, name, " {");

        MoCProperty[] props = properties.values().toArray(new MoCProperty[properties.size()]);

        for (int x = 0; x < props.length; x++)
        {
            MoCProperty prop = props[x];

            if (prop.comment != null)
            {
                if (x != 0)
                {
                    out.newLine();
                }

                Splitter splitter = Splitter.onPattern("\r?\n");
                for (String commentLine : splitter.split(prop.comment))
                {
                    write(out, pad1, "# ", commentLine);
                }
            }

            String propName = prop.getName();

            if (!allowedProperties.matchesAllOf(propName))
            {
                propName = '"' + propName + '"';
            }

            if (prop.isList())
            {
                char type = prop.getType().getID();
                write(out, false, pad1 + String.valueOf(type), ":", propName, " <");
                for (int i = 0; i < prop.valueList.size(); i++)
                {
                    String line = prop.valueList.get(i);
                    if (prop.valueList.size() == i+1) // if last line, don't write delimiter
                    {
                        write(out, false, line);
                    }
                    else 
                    {
                        write(out, false, line + ":");
                    }
                }
                write(out, false, ">", NEW_LINE);
            }
            else if (prop.getType() == null)
            {
                write(out, false,  propName, "=", prop.getString());
            }
            else
            {
                char type = prop.getType().getID();
                write(out, pad1, String.valueOf(type), ":", propName, "=", prop.getString());
            }
        }

        for (MoCConfigCategory child : children)
        {
            child.write(out, indent + 1);
        }

        write(out, pad0, "}", NEW_LINE);
    }

    private String getIndent(int indent)
    {
        StringBuilder buf = new StringBuilder("");
        for (int x = 0; x < indent; x++)
        {
            buf.append("    ");
        }
        return buf.toString();
    }

    public boolean hasChanged()
    {
        if (changed) return true;
        for (MoCProperty prop : properties.values())
        {
            if (prop.hasChanged()) return true;
        }
        return false;
    }

    void resetChangedState()
    {
        changed = false;
        for (MoCProperty prop : properties.values())
        {
            prop.resetChangedState();
        }
    }


    //Map bouncer functions for compatibility with older mods, to be removed once all mods stop using it.
    @Override public int size(){ return properties.size(); }
    @Override public boolean isEmpty() { return properties.isEmpty(); }
    @Override public boolean containsKey(Object key) { return properties.containsKey(key); }
    @Override public boolean containsValue(Object value){ return properties.containsValue(value); }
    @Override public MoCProperty get(Object key) { return properties.get(key); }
    @Override public MoCProperty put(String key, MoCProperty value)
    {
        changed = true;
        return properties.put(key, value);
    }
    @Override public MoCProperty remove(Object key)
    {
        changed = true;
        return properties.remove(key);
    }
    @Override public void putAll(Map<? extends String, ? extends MoCProperty> m)
    {
        changed = true;
        properties.putAll(m);
    }
    @Override public void clear()
    {
        changed = true;
        properties.clear();
    }
    @Override public Set<String> keySet() { return properties.keySet(); }
    @Override public Collection<MoCProperty> values() { return properties.values(); }

    @Override //Immutable copy, changes will NOT be reflected in this category
    public Set<java.util.Map.Entry<String, MoCProperty>> entrySet()
    {
        return ImmutableSet.copyOf(properties.entrySet());
    }

    public Set<MoCConfigCategory> getChildren(){ return ImmutableSet.copyOf(children); }

    public void removeChild(MoCConfigCategory child)
    {
        if (children.contains(child))
        {
            children.remove(child);
            changed = true;
        }
    }

}