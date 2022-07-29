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

import org.apache.flink.api.common.functions.Function;

import java.io.Serializable;
import java.util.Optional;

/**
 * Function that creates the description how the input data should be mapped to redis type.
 *
 * @param <T> The type of the element handled by this {@code RedisSerializer}
 */
public interface RedisSerializer<T> extends Function, Serializable {

    /**
     * Returns a redis command.
     *
     * @return RedisCommand
     */
    RedisCommand getCommand(T data);

    /**
     * Extracts key from data.
     *
     * @param data source data
     * @return key
     */
    String getKeyFromData(T data);

    /**
     * Extracts value from data.
     *
     * @param data source data
     * @return value
     */
    String getValueFromData(T data);

    /**
     * Extracts the additional key from data as an {@link Optional<String>}.
     * The default implementation returns an empty Optional.
     *
     * @param data
     * @return Optional
     */
    default Optional<String> getAdditionalKey(T data) {
        return Optional.empty();
    }

    /**
     * Extracts the additional time to live (TTL) for data as an {@link Optional<Integer>}.
     * The default implementation returns an empty Optional.
     *
     * @param data
     * @return Optional
     */
    default Optional<Integer> getAdditionalTTL(T data) {
        return Optional.empty();
    }
}
