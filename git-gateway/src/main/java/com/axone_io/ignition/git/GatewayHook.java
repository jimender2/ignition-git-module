package com.axone_io.ignition.git;

import com.axone_io.ignition.git.commissioning.utils.GitCommissioningUtils;
import com.axone_io.ignition.git.records.GitConfigRecord;
import com.axone_io.ignition.git.records.GitProjectsConfigRecord;
import com.axone_io.ignition.git.records.GitReposUsersRecord;
import com.axone_io.ignition.git.web.GitConfigRecordPage;
import com.axone_io.ignition.git.web.GitProjectsConfigPage;
import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.gateway.clientcomm.ClientReqSession;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IRecordListener;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.models.ConfigCategory;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.KeyValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class GatewayHook extends AbstractGatewayModuleHook {
    public static String MODULE_NAME = "Git";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GatewayScriptModule scriptModule;
    public static GatewayContext context;

    public static final ConfigCategory CONFIG_CATEGORY = new ConfigCategory(MODULE_NAME,
            "bundle_git.Config.Git.MenuTitle", 700);

    public static final IConfigTab GitModuleIntegration_CONFIG_ENTRY = DefaultConfigTab.builder()
            .category(CONFIG_CATEGORY)
            .name("Git Config")
            .i18n("GitConfig.integrationapi.nav.status.header")
            .page(GitConfigRecordPage.class)
            .terms("git", "module")
            .build();

    @Override
    public List<? extends IConfigTab> getConfigPanels() {
        return List.of(GitProjectsConfigPage.MENU_ENTRY, GitModuleIntegration_CONFIG_ENTRY);
    }

    @Override
    public List<ConfigCategory> getConfigCategories() {
        return Collections.singletonList(CONFIG_CATEGORY);
    }

    @Override
    public void setup(GatewayContext gatewayContext) {
        context = gatewayContext;
        scriptModule = new GatewayScriptModule(context);
        BundleUtil.get().addBundle("bundle_git", getClass(), "bundle_git");
        verifySchema(gatewayContext);

        logger.info("setup()");

        GitConfigRecord.META.addRecordListener(new IRecordListener<GitConfigRecord>() {
            @Override
            public void recordUpdated(GitConfigRecord localGitConfigRecord) {
                logger.info("recordUpdated");
                GitCommissioningUtils.loadConfiguration();
            }

            @Override
            public void recordAdded(GitConfigRecord localGitConfigRecord) {
                logger.info("recordAdded");
                GitCommissioningUtils.loadConfiguration();
            }

            @Override
            public void recordDeleted(KeyValue arg0) {
                GitCommissioningUtils.loadConfiguration();
            }
        });
    }

    private void verifySchema(GatewayContext context) {
        try {
            context.getSchemaUpdater().updatePersistentRecords(GitProjectsConfigRecord.META, GitReposUsersRecord.META,
                    GitConfigRecord.META);
        } catch (SQLException e) {
            logger.error("Error verifying persistent record schemas for Git Config records.", e);
        }
    }

    @Override
    public void startup(LicenseState licenseState) {
        GitCommissioningUtils.loadConfiguration();

        logger.info("startup()");
    }

    @Override
    public void shutdown() {
        logger.info("shutdown()");
    }

    @Override
    public boolean isFreeModule() {
        return true;
    }

    @Override
    public boolean isMakerEditionCompatible() {
        return true;
    }

    @Override
    public Object getRPCHandler(ClientReqSession session, String projectName) {
        return scriptModule;
    }
}
