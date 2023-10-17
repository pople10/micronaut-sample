/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories.entities.listeners;

import com.sample.repositories.AuditRepository;
import com.sample.repositories.entities.audit.Audit;
import com.sample.security.CustomSecurityService;
import com.sample.utils.consts.SecurityConst;
import com.sample.utils.enumerations.AuditActions;
import io.micronaut.context.annotation.Factory;
import io.micronaut.data.event.listeners.PostPersistEventListener;
import io.micronaut.data.event.listeners.PostRemoveEventListener;
import io.micronaut.data.event.listeners.PostUpdateEventListener;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import com.sample.repositories.entities.audit.TimeStampEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Factory
public class AuditListener {
    private static final Logger logger = LoggerFactory.getLogger(AuditListener.class);

    @Inject private AuditRepository auditRepository;

    @Singleton
    PostPersistEventListener<TimeStampEntity> afterPersist() {
        return (obj) -> {
            try {
                saveAudit(AuditBuilder.builder().action(AuditActions.CREATE).object(obj).build());
            } catch (Exception exception) {
                logger.warn(String.format("Unable to save audit for %s", obj), exception);
            }
        };
    }

    @Singleton
    PostUpdateEventListener<TimeStampEntity> afterUpdate() {
        return (obj) -> {
            try {
                saveAudit(AuditBuilder.builder().action(AuditActions.UPDATE).object(obj).build());
            } catch (Exception exception) {
                logger.warn(String.format("Unable to save audit for %s", obj), exception);
            }
        };
    }

    @Singleton
    PostRemoveEventListener<TimeStampEntity> afterDelete() {
        return (obj) -> {
            try {
                saveAudit(AuditBuilder.builder().action(AuditActions.DELETE).object(obj).build());
            } catch (Exception exception) {
                logger.warn(String.format("Unable to save audit for %s", obj), exception);
            }
        };
    }

    @Async
    public void saveAudit(Audit audit) {
        logger.info(
                "{} is trying to {} on table {}",
                audit.getActor(),
                audit.getAction(),
                audit.getTableName());

        logger.debug("Saving the audit : {}", audit);
        auditRepository.save(audit);
        logger.debug("Executor has been done working with {}", audit);
    }

    public static class AuditBuilder {
        private CustomSecurityService securityService = new CustomSecurityService();
        private Object object;
        private AuditActions action;

        private AuditBuilder() {}

        public AuditBuilder object(Object obj) {
            this.object = obj;
            return this;
        }

        public AuditBuilder action(AuditActions action) {
            this.action = action;
            return this;
        }

        public AuditBuilder action(String action) {
            try {
                this.action = AuditActions.valueOf(action);
            } catch (Exception exception) {
                logger.debug(String.format("Action %s is not listed", action), exception);
            }
            return this;
        }

        public static AuditBuilder builder() {
            return new AuditBuilder();
        }

        public Audit build() {
            String username = SecurityConst.ANONYMOUS_USERNAME;
            if (securityService.username().isPresent()) {
                username = securityService.username().get();
            }
            Audit audit = new Audit();
            audit.setAction(this.action);
            audit.setTableName(this.object.getClass().getSimpleName().toLowerCase());
            audit.setActor(username);
            return audit;
        }
    }
}
