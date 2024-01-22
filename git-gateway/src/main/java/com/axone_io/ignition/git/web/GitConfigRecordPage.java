package com.axone_io.ignition.git.web;

import org.apache.commons.lang3.tuple.Pair;

import com.axone_io.ignition.git.records.GitConfigRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;
import com.inductiveautomation.ignition.gateway.web.components.RecordActionTable;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.pages.IConfigPage;
import static com.axone_io.ignition.git.GatewayHook.CONFIG_CATEGORY;

public class GitConfigRecordPage extends RecordActionTable<GitConfigRecord> {

    public static final IConfigTab MENU_ENTRY = DefaultConfigTab.builder()
            .category(CONFIG_CATEGORY)
            .name("projects")
            .i18n("bundle_git.Config.Git.Projects.MenuTitle")
            .page(GitProjectsConfigPage.class)
            .terms(new String[] { "git", "projects", "users", "ssh" })
            .build();

    public GitConfigRecordPage(final IConfigPage configPage) {
        super(configPage);
    }

    @Override
    protected RecordMeta<GitConfigRecord> getRecordMeta() {
        return GitConfigRecord.META;
    }

    @Override
    public Pair<String, String> getMenuLocation() {
        return MENU_ENTRY.getMenuLocation();
    }
}
