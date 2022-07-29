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
package org.apache.flink.connector.redis.shared.config;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JedisSentinelConfigTest {

    public static final String MASTER_NAME = "test-master";

    @Test
    public void shouldThrowNullPointExceptionIfMasterValueIsNull() {
        assertThrows(NullPointerException.class, () -> {
            JedisSentinelConfig.Builder builder = new JedisSentinelConfig.Builder();
            Set<String> sentinels = new HashSet<>();
            sentinels.add("127.0.0.1");
            builder.setSentinels(sentinels).build();
        });
    }

    @Test
    public void shouldThrowNullPointExceptionIfSentinelsValueIsNull() {
        assertThrows(NullPointerException.class, () -> {
            JedisSentinelConfig.Builder builder = new JedisSentinelConfig.Builder();
            builder.setMasterName(MASTER_NAME).build();
        });
    }

    @Test
    public void shouldThrowNullPointExceptionIfSentinelsValueIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            JedisSentinelConfig.Builder builder = new JedisSentinelConfig.Builder();
            Set<String> sentinels = new HashSet<>();
            builder.setMasterName(MASTER_NAME).setSentinels(sentinels).build();
        });
    }
}
