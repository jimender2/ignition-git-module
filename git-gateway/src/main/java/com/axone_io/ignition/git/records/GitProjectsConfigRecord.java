package com.axone_io.ignition.git.records;

import com.axone_io.ignition.git.web.ProjectList.ProjectListEditorSource;
import com.inductiveautomation.ignition.gateway.localdb.persistence.*;
import com.inductiveautomation.ignition.gateway.web.components.editors.CheckboxEditorSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleorm.dataset.SFieldFlags;

public class GitProjectsConfigRecord extends PersistentRecord {
    private static final Logger logger = LoggerFactory.getLogger(GitProjectsConfigRecord.class);

    public static final RecordMeta<GitProjectsConfigRecord> META = new RecordMeta<>(
            GitProjectsConfigRecord.class, "GitProjectsConfigRecord");

    @Override
    public RecordMeta<?> getMeta() {
        return META;
    }

    public static final IdentityField Id = new IdentityField(META);
    public static final StringField ProjectName = new StringField(META, "ProjectName", SFieldFlags.SMANDATORY,
            SFieldFlags.SDESCRIPTIVE);
    public static final StringField URI = new StringField(META, "URI", SFieldFlags.SMANDATORY,
            SFieldFlags.SDESCRIPTIVE);

    public static final StringField BranchName = new StringField(META, "BranchName", SFieldFlags.SMANDATORY)
            .setDefault("master");

    public static final BooleanField ImportTags = new BooleanField(META, "ImportTags", SFieldFlags.SMANDATORY)
            .setDefault(true);

    public static final BooleanField ImportThemes = new BooleanField(META, "ImportThemes", SFieldFlags.SMANDATORY)
            .setDefault(true);

    public static final BooleanField ImportImages = new BooleanField(META, "ImportImages", SFieldFlags.SMANDATORY)
            .setDefault(true);

    static final Category ProjectConfiguration = new Category("GitProjectsConfigRecord.Category.ProjectConfiguration",
            1000).include(ProjectName, URI);

    public long getId() {
        return this.getLong(Id);
    }

    public String getProjectName() {
        return this.getString(ProjectName);
    }

    public void setProjectName(String projectName) {
        setString(ProjectName, projectName);
    }

    public String getURI() {
        return this.getString(URI);
    }

    public void setURI(String uri) {
        setString(URI, uri);
    }

    public boolean getImportTags() {
        return this.getBoolean(ImportTags);
    }

    public void setImportTags(boolean val) {
        setBoolean(ImportTags, val);
    }

    public boolean getImportThemes() {
        return this.getBoolean(ImportThemes);
    }

    public void setImportThemes(boolean val) {
        setBoolean(ImportThemes, val);
    }

    public boolean getImportImages() {
        return this.getBoolean(ImportImages);
    }

    public void setImportImages(boolean val) {
        setBoolean(ImportImages, val);
    }

    public boolean isSSHAuthentication() {
        return !this.getString(URI).toLowerCase().startsWith("http");
    }

    static {
        ProjectName.getFormMeta().setEditorSource(ProjectListEditorSource.getSharedInstance());

        URI.getFormMeta().setFieldDescriptionKey("GitProjectsConfigRecord.URI.Desc");
        URI.getFormMeta().setFieldDescriptionKeyAddMode("GitProjectsConfigRecord.URI.NewDesc");
        URI.getFormMeta().setFieldDescriptionKeyEditMode("GitProjectsConfigRecord.URI.EditDesc");
        URI.setWide();

        // TODO: Set name and description keys
        ImportTags.getFormMeta().setEditorSource(CheckboxEditorSource.getSharedInstance());
        ImportThemes.getFormMeta().setEditorSource(CheckboxEditorSource.getSharedInstance());
        ImportImages.getFormMeta().setEditorSource(CheckboxEditorSource.getSharedInstance());

    }
}
