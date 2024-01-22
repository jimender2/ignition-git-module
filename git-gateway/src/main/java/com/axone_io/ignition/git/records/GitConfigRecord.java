package com.axone_io.ignition.git.records;

import com.axone_io.ignition.git.web.ProjectList.ProjectListEditorSource;
import com.inductiveautomation.ignition.gateway.localdb.persistence.*;
import com.inductiveautomation.ignition.gateway.web.components.editors.CheckboxEditorSource;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleorm.dataset.SFieldFlags;

public class GitConfigRecord extends PersistentRecord {
    private static final Logger logger = LoggerFactory.getLogger(GitConfigRecord.class);

    public static final RecordMeta<GitConfigRecord> META = new RecordMeta<>(
            GitConfigRecord.class, "GitConfigRecord");

    @Override
    public RecordMeta<?> getMeta() {
        return META;
    }

    public static final IdentityField Id = new IdentityField(META);
    public static final StringField Key = new StringField(META, "Key", SFieldFlags.SMANDATORY,
            SFieldFlags.SDESCRIPTIVE);
    public static final StringField Value = new StringField(META, "Value", SFieldFlags.SMANDATORY,
            SFieldFlags.SDESCRIPTIVE);

    static final Category ProjectConfiguration = new Category("GitConfigRecord.Category.ProjectConfiguration",
            1000).include(Key, Value);

    public long getId() {
        return this.getLong(Id);
    }

    public String getKey() {
        return this.getString(Key);
    }

    public void setKey(String key) {
        setString(Key, key);
    }

    public String getValue() {
        return this.getString(Value);
    }

    public void setValue(String value) {
        setString(Value, value);
    }

    static {

    }
}
