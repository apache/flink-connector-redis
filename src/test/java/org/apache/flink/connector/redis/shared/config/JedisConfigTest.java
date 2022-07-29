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

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JedisConfigTest {

    @Test
    public void shouldThrowIllegalArgumentExceptionIfTimeOutIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestConfig(-1, 0, 0, 0, false, false, false)
        );
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfMaxTotalIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestConfig(1, -1, 0, 0, false, false, false)
        );
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfMaxIdleIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestConfig(0, 0, -1, 0, false, false, false)
        );
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfMinIdleIsNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                new TestConfig(0, 0, 0, -1, false, false, false)
        );
    }

    private static class TestConfig extends JedisConfig {
        protected TestConfig(int connectionTimeout, int maxTotal, int maxIdle, int minIdle,
                             boolean testOnBorrow, boolean testOnReturn, boolean testWhileIdle) {
            super(connectionTimeout, maxTotal, maxIdle, minIdle, "dummy", testOnBorrow, testOnReturn, testWhileIdle);
        }
    }
}
