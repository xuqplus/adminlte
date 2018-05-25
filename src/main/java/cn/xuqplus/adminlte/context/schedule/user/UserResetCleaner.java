package cn.xuqplus.adminlte.context.schedule.user;

import cn.xuqplus.adminlte.domain.user.UserReset;
import cn.xuqplus.adminlte.repository.user.UserResetRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResetCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResetCleaner.class);

    @Autowired
    UserResetRepository userResetRepository;

    @Scheduled(initialDelay = 1000L * 60, fixedDelay = 1000L * 60 * 5)
    public void clean() {
        try {
            List<UserReset> userResets = userResetRepository.findByExpiresAtLessThan(System.currentTimeMillis());
            if (CollectionUtils.isEmpty(userResets)) {
                return;
            }
            userResetRepository.deleteAll(userResets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}