package com.jhipsterpress2.web.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.jhipsterpress2.web.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Profile.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Profile.class.getName() + ".interests", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Profile.class.getName() + ".activities", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Profile.class.getName() + ".celebs", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".blogs", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".messages", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".followeds", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".followings", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".blockingusers", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".blockedusers", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".interests", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".activities", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Party.class.getName() + ".celebs", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Follow.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Blockeduser.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Blog.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Blog.class.getName() + ".posts", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Post.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Post.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Post.class.getName() + ".tags", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Post.class.getName() + ".topics", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Message.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Album.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Album.class.getName() + ".photos", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Photo.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Topic.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Topic.class.getName() + ".posts", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Tag.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Tag.class.getName() + ".posts", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Interest.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Interest.class.getName() + ".parties", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Interest.class.getName() + ".profiles", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Activity.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Activity.class.getName() + ".parties", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Activity.class.getName() + ".profiles", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Celeb.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Celeb.class.getName() + ".parties", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Celeb.class.getName() + ".profiles", jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Urllink.class.getName(), jcacheConfiguration);
            cm.createCache(com.jhipsterpress2.web.domain.Frontpageconfig.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
