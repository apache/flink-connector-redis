package org.apache.flink.connector.redis.sink2;

import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class BaseITCase {

    @Container
    private GenericContainer redis = new GenericContainer(DockerImageName
            .parse("redis:7.0.4-alpine"))
            .withExposedPorts(6379);

    public static MiniClusterWithClientResource cluster = new MiniClusterWithClientResource(
            new MiniClusterResourceConfiguration.Builder()
                    .setNumberSlotsPerTaskManager(2)
                    .setNumberTaskManagers(1)
                    .build());

    @BeforeAll
    public static void beforeAll() throws Exception {
        cluster.before();
    }

    @AfterAll
    public static void afterAll() {
        cluster.after();
    }

    public String redisHost() {
        return redis.getHost();
    }

    public Integer redisPort() {
        return redis.getFirstMappedPort();
    }

}
