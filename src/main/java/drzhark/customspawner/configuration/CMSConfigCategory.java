package drzhark.customspawner.configuration;

import static drzhark.customspawner.configuration.CMSConfiguration.NEW_LINE;
import static drzhark.customspawner.configuration.CMSConfiguration.allowedProperties;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CMSConfigCategory implements Map<String, CMSProperty> {

    private String name;
    private String comment;
    private ArrayList<CMSConfigCategory> children = new ArrayList<CMSConfigCategory>();
    private Map<String, CMSProperty> properties = new TreeMap<String, CMSProperty>();
    public final CMSConfigCategory parent;
    private boolean changed = false;

    public CMSConfigCategory(String name) {
        this(name, null, true);
    }

    public CMSConfigCategory(String name, CMSConfigCategory parent) {
        this(name, parent, true);
    }

    public CMSConfigCategory(String name, boolean newline) {
        this(name, null, newline);
    }

    public CMSConfigCategory(String name, CMSConfigCategory parent, boolean newline) {
        this.name = name;
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CMSConfigCategory) {
            CMSConfigCategory cat = (CMSConfigCategory) obj;
            return this.name.equals(cat.name) && this.children.equals(cat.children);
        }

        return false;
    }

    public String getQualifiedName() {
        return getQualifiedName(this.name, this.parent);
    }

    public static String getQualifiedName(String name, CMSConfigCategory parent) {
        return (parent == null ? name : parent.getQualifiedName() + CMSConfiguration.CATEGORY_SPLITTER + name);
    }

    public CMSConfigCategory getFirstParent() {
        return (this.parent == null ? this : this.parent.getFirstParent());
    }

    public boolean isChild() {
        return this.parent != null;
    }

    public Map<String, CMSProperty> getValues() {
        return this.properties;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean containsKey(String key) {
        return this.properties.containsKey(key);
    }

    public CMSProperty get(String key) {
        return this.properties.get(key);
    }

    public void set(String key, CMSProperty value) {
        this.properties.put(key, value);
    }

    private void write(BufferedWriter out, String... data) throws IOException {
        write(out, true, data);
    }

    private void write(BufferedWriter out, boolean new_line, String... data) throws IOException {
        for (int x = 0; x < data.length; x++) {
            if (data[x] != null) {
                out.write(data[x]);
            }
        }
        if (new_line) {
            out.write(NEW_LINE);
        }
    }

    public void write(BufferedWriter out, int indent) throws IOException {
        String pad0 = getIndent(indent);
        String pad1 = getIndent(indent + 1);

        write(out, pad0, "####################");
        write(out, pad0, "# ", this.name);

        if (this.comment != null) {
            write(out, pad0, "#===================");
            Splitter splitter = Splitter.onPattern("\r?\n");

            for (String line : splitter.split(this.comment)) {
                write(out, pad0, "# ", line);
            }
        }

        write(out, pad0, "####################", NEW_LINE);

        if (!allowedProperties.matchesAllOf(this.name) && this.name.charAt(0) != '"') // don't append quotes more than once
        {
            this.name = '"' + this.name + '"';
        }

        write(out, pad0, this.name, " {");

        CMSProperty[] props = this.properties.values().toArray(new CMSProperty[this.properties.size()]);

        for (int x = 0; x < props.length; x++) {
            CMSProperty prop = props[x];

            if (prop.comment != null) {
                if (x != 0) {
                    out.newLine();
                }

                Splitter splitter = Splitter.onPattern("\r?\n");
                for (String commentLine : splitter.split(prop.comment)) {
                    write(out, pad1, "# ", commentLine);
                }
            }

            String propName = prop.getName();

            if (!allowedProperties.matchesAllOf(propName)) {
                propName = '"' + propName + '"';
            }

            if (prop.isList()) {
                char type = prop.getType().getID();
                write(out, false, pad1 + String.valueOf(type), ":", propName, " <");
                for (int i = 0; i < prop.valueList.size(); i++) {
                    String line = prop.valueList.get(i);
                    if (prop.valueList.size() == i + 1) // if last line, don't write delimiter
                    {
                        write(out, false, line);
                    } else {
                        write(out, false, line + ":");
                    }
                }
                write(out, false, ">", NEW_LINE);
            } else if (prop.getType() == null) {
                write(out, false, propName, "=", prop.getString());
            } else {
                char type = prop.getType().getID();
                write(out, pad1, String.valueOf(type), ":", propName, "=", prop.getString());
            }
        }

        for (CMSConfigCategory child : this.children) {
            child.write(out, indent + 1);
        }

        write(out, pad0, "}", NEW_LINE);
    }

    private String getIndent(int indent) {
        StringBuilder buf = new StringBuilder("");
        for (int x = 0; x < indent; x++) {
            buf.append("    ");
        }
        return buf.toString();
    }

    public boolean hasChanged() {
        if (this.changed) {
            return true;
        }
        for (CMSProperty prop : this.properties.values()) {
            if (prop.hasChanged()) {
                return true;
            }
        }
        return false;
    }

    void resetChangedState() {
        this.changed = false;
        for (CMSProperty prop : this.properties.values()) {
            prop.resetChangedState();
        }
    }

    //Map bouncer functions for compatibility with older mods, to be removed once all mods stop using it.
    @Override
    public int size() {
        return this.properties.size();
    }

    @Override
    public boolean isEmpty() {
        return this.properties.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.properties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.properties.containsValue(value);
    }

    @Override
    public CMSProperty get(Object key) {
        return this.properties.get(key);
    }

    @Override
    public CMSProperty put(String key, CMSProperty value) {
        this.changed = true;
        return this.properties.put(key, value);
    }

    @Override
    public CMSProperty remove(Object key) {
        this.changed = true;
        return this.properties.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends CMSProperty> m) {
        this.changed = true;
        this.properties.putAll(m);
    }

    @Override
    public void clear() {
        this.changed = true;
        this.properties.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.properties.keySet();
    }

    @Override
    public Collection<CMSProperty> values() {
        return this.properties.values();
    }

    @Override
    //Immutable copy, changes will NOT be reflected in this category
            public
            Set<java.util.Map.Entry<String, CMSProperty>> entrySet() {
        return ImmutableSet.copyOf(this.properties.entrySet());
    }

    public Set<CMSConfigCategory> getChildren() {
        return ImmutableSet.copyOf(this.children);
    }

    public void removeChild(CMSConfigCategory child) {
        if (this.children.contains(child)) {
            this.children.remove(child);
            this.changed = true;
        }
    }

}
