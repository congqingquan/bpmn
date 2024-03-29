package priv.cqq.activity.config;

import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

// Activiti7 引入了 Spring security，需要配置一下权限信息才能正常启动
@Configuration
public class ActivitiSpringIdentityAutoConfiguration {

    @Bean
    public UserGroupManager userGroupManager() {
        return new UserGroupManager() {
            @Override
            public List<String> getUserGroups(String s) {
                return new ArrayList<>();
            }

            @Override
            public List<String> getUserRoles(String s) {
                return null;
            }

            @Override
            public List<String> getGroups() {
                return null;
            }

            @Override
            public List<String> getUsers() {
                return null;
            }
        };
    }
}
