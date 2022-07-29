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
package org.apache.flink.connector.redis.sink2;

import java.io.Serializable;

public class RedisAction implements Serializable {
    public final RedisCommand command;
    public final String key;
    public final String value;

    private RedisAction(RedisCommand command, String key, String value) {
        this.command = command;
        this.key = key;
        this.value = value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RedisCommand command;
        private String key;
        private String value;

        public Builder withCommand(RedisCommand command) {
            this.command = command;
            return this;
        }

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public RedisAction build() {
            return new RedisAction(command, key, value);
        }
    }


}
