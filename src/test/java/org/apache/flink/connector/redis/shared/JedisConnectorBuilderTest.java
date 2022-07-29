/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flink.connector.redis.shared;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.flink.connector.redis.shared.config.JedisPoolConfig;
import org.apache.flink.test.util.AbstractTestBase;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;



public class JedisConnectorBuilderTest extends AbstractTestBase {

    @Test
    public void testNotTestWhileIdle() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig.Builder().setHost("host").setPort(0).setDatabase(0).build();
        GenericObjectPoolConfig<?> genericObjectPoolConfig = JedisConnectorBuilder.getGenericObjectPoolConfig(jedisPoolConfig);
        assertFalse(genericObjectPoolConfig.getTestWhileIdle());
        assertEqualConfig(jedisPoolConfig, genericObjectPoolConfig);
    }

    @Test
    public void testTestWhileIdle() {
        JedisPoolConfig flinkJedisPoolConfig = new JedisPoolConfig.Builder().setHost("host").setPort(0).setDatabase(0).setTestWhileIdle(true).build();
        GenericObjectPoolConfig<?> genericObjectPoolConfig = JedisConnectorBuilder.getGenericObjectPoolConfig(flinkJedisPoolConfig);
        assertTrue(genericObjectPoolConfig.getTestWhileIdle());
        assertEqualConfig(flinkJedisPoolConfig, genericObjectPoolConfig);

        redis.clients.jedis.JedisPoolConfig redisJedisPoolConfig = new redis.clients.jedis.JedisPoolConfig();
        assertEquals(genericObjectPoolConfig.getMinEvictableIdleTimeMillis(), redisJedisPoolConfig.getMinEvictableIdleTimeMillis());
        assertEquals(genericObjectPoolConfig.getTimeBetweenEvictionRunsMillis(), redisJedisPoolConfig.getTimeBetweenEvictionRunsMillis());
        assertEquals(genericObjectPoolConfig.getNumTestsPerEvictionRun(), redisJedisPoolConfig.getNumTestsPerEvictionRun());
    }

    private void assertEqualConfig(JedisPoolConfig flinkJedisPoolConfig, GenericObjectPoolConfig<?> genericObjectPoolConfig) {
        assertEquals(genericObjectPoolConfig.getMaxIdle(), flinkJedisPoolConfig.getMaxIdle());
        assertEquals(genericObjectPoolConfig.getMinIdle(), flinkJedisPoolConfig.getMinIdle());
        assertEquals(genericObjectPoolConfig.getMaxTotal(), flinkJedisPoolConfig.getMaxTotal());
        assertEquals(genericObjectPoolConfig.getTestWhileIdle(), flinkJedisPoolConfig.getTestWhileIdle());
        assertEquals(genericObjectPoolConfig.getTestOnBorrow(), flinkJedisPoolConfig.getTestOnBorrow());
        assertEquals(genericObjectPoolConfig.getTestOnReturn(), flinkJedisPoolConfig.getTestOnReturn());
    }
}
