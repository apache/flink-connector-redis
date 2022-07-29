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

package org.apache.flink.connector.redis.sink2.async;

import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.connector.base.sink.writer.AsyncSinkWriter;
import org.apache.flink.connector.base.sink.writer.BufferedRequestState;
import org.apache.flink.connector.base.sink.writer.ElementConverter;
import org.apache.flink.connector.redis.shared.JedisConnector;
import org.apache.flink.connector.redis.sink2.RedisAction;
import org.apache.flink.connector.redis.sink2.RedisSinkConfig;
import redis.clients.jedis.Protocol.Command;
import redis.clients.jedis.commands.JedisCommands;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static redis.clients.jedis.Protocol.Command.SET;

public class RedisAsyncWriter<T> extends AsyncSinkWriter<T, RedisAction> {

    private final JedisConnector jedisConnector;

    public RedisAsyncWriter(JedisConnector jedisConnector,
                            RedisConverter<T> converter,
                            RedisSinkConfig sinkConfig,
                            Sink.InitContext context) {
        super(converter, context,
                sinkConfig.maxBatchSize, sinkConfig.maxInFlightRequests, sinkConfig.maxBufferedRequests,
                sinkConfig.maxBatchSizeInBytes, sinkConfig.maxTimeInBufferMS, sinkConfig.maxRecordSizeInBytes);
        this.jedisConnector = jedisConnector;
    }

    @Override
    protected void submitRequestEntries(List<RedisAction> actions, Consumer<List<RedisAction>> toRetry) {
        List<RedisAction> errors = new ArrayList<>();
        for(RedisAction action : actions) {
            try {
                this.jedisConnector.execute(action);
            } catch (Exception e) {
                errors.add(action);
            }
        }
        toRetry.accept(errors);
    }

    @Override
    protected long getSizeInBytes(RedisAction redisAction) {
        return redisAction.value.length();
    }

    public void close() {
        jedisConnector.close();
    }
}
