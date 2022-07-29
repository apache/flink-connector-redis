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

public class RedisSinkConfig implements Serializable {

    public final int maxBatchSize;
    public final int maxInFlightRequests;
    public final int maxBufferedRequests;
    public final long maxBatchSizeInBytes;
    public final long maxTimeInBufferMS;
    public final long maxRecordSizeInBytes;

    public RedisSinkConfig(int maxBatchSize, int maxInFlightRequests, int maxBufferedRequests,
                           long maxBatchSizeInBytes, long maxTimeInBufferMS, long maxRecordSizeInBytes) {
        this.maxBatchSize = maxBatchSize;
        this.maxInFlightRequests = maxInFlightRequests;
        this.maxBufferedRequests = maxBufferedRequests;
        this.maxBatchSizeInBytes = maxBatchSizeInBytes;
        this.maxTimeInBufferMS = maxTimeInBufferMS;
        this.maxRecordSizeInBytes = maxRecordSizeInBytes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int maxBatchSize = 10;
        private int maxInFlightRequests = 1;
        private int maxBufferedRequests = 100;
        private long maxBatchSizeInBytes = 110;
        private long maxTimeInBufferMS = 1_000;
        private long maxRecordSizeInBytes = maxBatchSizeInBytes;

        public Builder withMaxBatchSize(int maxBatchSize) {
            this.maxBatchSize = maxBatchSize;
            return this;
        }

        public Builder withMaxInFlightRequests(int maxInFlightRequests) {
            this.maxInFlightRequests = maxInFlightRequests;
            return this;
        }

        public Builder withMaxBufferedRequests(int maxBufferedRequests) {
            this.maxBufferedRequests = maxBufferedRequests;
            return this;
        }

        public Builder withMaxBatchSizeInBytes(long maxBatchSizeInBytes) {
            this.maxBatchSizeInBytes = maxBatchSizeInBytes;
            return this;
        }

        public Builder withMaxTimeInBufferMS(long maxTimeInBufferMS) {
            this.maxTimeInBufferMS = maxTimeInBufferMS;
            return this;
        }

        public Builder withMaxRecordSizeInBytes(long maxRecordSizeInBytes) {
            this.maxRecordSizeInBytes = maxRecordSizeInBytes;
            return this;
        }

        public RedisSinkConfig build() {
            return new RedisSinkConfig(
                    maxBatchSize, maxInFlightRequests, maxBufferedRequests,
                    maxBatchSizeInBytes, maxTimeInBufferMS, maxRecordSizeInBytes);
        }
    }

}
