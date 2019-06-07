package  com.mycompany.myapp.config.lazy;

import io.github.jhipster.config.JHipsterConstants;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Lazy initialize all Spring Beans, which boosts up start-up performance.
 *
 * This class originally comes from https://github.com/dsyer/spring-boot-allocations where it is written
 * it can be copied on a project.
 *
 * As this is an experimental feature, we only recommend to use it in "dev" mode for the moment.
 */
@Component
@Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
public class LazyInitBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private Class<?>[] exclusionList;

    public LazyInitBeanFactoryPostProcessor() {
    }

    public LazyInitBeanFactoryPostProcessor(Class<?>[] exclusionList) {
        this.exclusionList = exclusionList;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
        throws BeansException {

        // Iterate over all beans, mark them as lazy if they are not in the exclusion list.
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            if (isLazy(beanName, beanFactory)) {
                BeanDefinition definition = beanFactory.getBeanDefinition(beanName);
                definition.setLazyInit(true);
            }
        }
    }

    private boolean isLazy(String beanName, ConfigurableListableBeanFactory beanFactory) {
        if (exclusionList == null || exclusionList.length == 0) {
            return true;
        }
        for (Class<?> clazz : exclusionList) {
            if (beanFactory.isTypeMatch(beanName, clazz)) {
                return false;
            }
        }
        return true;
    }
}
