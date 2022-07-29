package org.apache.flink.connector.redis.sink2;

import org.apache.flink.connector.redis.shared.config.JedisPoolConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedisSinkTest extends BaseITCase {

    private JedisPoolConfig jedisConfig;
    private Jedis jedis;

    @BeforeEach
    public void setUp() {
        jedisConfig = new JedisPoolConfig.Builder()
                .setHost(redisHost())
                .setPort(redisPort())
                .build();
        jedis = new Jedis(redisHost(), redisPort());
    }

    @AfterEach
    public void cleanUp() {
        jedis.close();
    }

    @Test
    public void testSetCommand() throws Exception {
        RedisSerializer<String> serializer = new TestRedisSerializer(RedisCommand.SET);
        RedisSinkConfig redisSinkConfig = RedisSinkConfig.builder().build();
        RedisSink<String> underTest = new RedisSink<>(jedisConfig, redisSinkConfig, serializer);

        List<String> source = Arrays.asList("one", "two", "three");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        env.fromCollection(source).sinkTo(underTest);
        env.execute();

        // verify results
        source.forEach(key -> assertEquals(serializer.getValueFromData(key), jedis.get(key)));

    }

    private static class TestRedisSerializer implements RedisSerializer<String> {

        private final RedisCommand command;

        public TestRedisSerializer(RedisCommand command) {
            this.command = command;
        }
        @Override
        public RedisCommand getCommand(String data) {
            return command;
        }

        @Override
        public String getKeyFromData(String data) {
            return data;
        }

        @Override
        public String getValueFromData(String data) {
            return "value#" + data;
        }
    }
}