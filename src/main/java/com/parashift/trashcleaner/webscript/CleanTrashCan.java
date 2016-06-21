package com.parashift.trashcleaner.webscript;

import com.github.dynamicextensionsalfresco.webscripts.annotations.HttpMethod;
import com.github.dynamicextensionsalfresco.webscripts.annotations.Uri;
import com.github.dynamicextensionsalfresco.webscripts.annotations.WebScript;
import org.alfresco.repo.node.archive.NodeArchiveService;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.security.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cetra on 21/06/2016.
 */
@Component
@WebScript
public class CleanTrashCan {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    CountDownLatch latch;

    ExecutorService executorService;

    @Autowired
    NodeArchiveService nodeArchiveService;

    @PostConstruct
    public void init() {

        logger.debug("CleanTrashcan Initilialised, creating new countdown latch");

        executorService = Executors.newSingleThreadExecutor();

        latch = new CountDownLatch(0);
    }

    @Uri(value = "/parashift/cleantrashcan", method = HttpMethod.DELETE)
    public void handleDelete(WebScriptRequest request, WebScriptResponse response) throws IOException {

        String user = AuthenticationUtil.getRunAsUser();

        if(latch.getCount() == 0) {

            latch = new CountDownLatch(1);

            logger.debug("Countdown latch set to 1, executing background thread.");

            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Void>() {
                        @Override
                        public Void doWork() throws Exception {

                            logger.debug("Purging All Archive Nodes for Workspace");

                            nodeArchiveService.purgeAllArchivedNodes(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE);

                            latch.countDown();

                            logger.debug("Purging Completed");
                            return null;
                        }
                    }, user);

                }
            });

            response.getWriter().write("Submitting background deletion\n");

        } else {
            response.getWriter().write("Awaiting existing deletion process\n");
        }

    }
}
