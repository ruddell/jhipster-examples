package com.mycompany.myapp.repository.search;
import com.mycompany.myapp.domain.Foo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Foo} entity.
 */
public interface FooSearchRepository extends ElasticsearchRepository<Foo, Long> {
}
