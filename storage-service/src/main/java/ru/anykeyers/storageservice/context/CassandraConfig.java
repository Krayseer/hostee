package ru.anykeyers.storageservice.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

@Configuration
@PropertySource(value = {"classpath:application.yml"})
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.cassandra.keyspace-name}")
    private String cassandraKeyspace;

    @Override
    @Nonnull protected String getKeyspaceName() {
        return cassandraKeyspace;
    }

    @Override
    @Nonnull protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return Collections.singletonList(CreateKeyspaceSpecification.createKeyspace(cassandraKeyspace)
                .ifNotExists()
                .with(KeyspaceOption.DURABLE_WRITES, true)
                .withSimpleReplication());
    }

    @Override
    @Nonnull protected List<String> getStartupScripts() {
        return Collections.singletonList(generateVideosTableScript());
    }

    private String generateVideosTableScript() {
        return String.format("""
                CREATE TABLE IF NOT EXISTS %s.videos(uuid TEXT PRIMARY KEY, bytebuffer BLOB)
                """, cassandraKeyspace);
    }

}
