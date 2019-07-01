/*
 * Copyright (C) 2019 Benny Bottema (benny@bennybottema.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.simplejavamail.smtpconnectionpool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bbottema.clusteredobjectpool.core.ClusterConfig;
import org.bbottema.clusteredobjectpool.core.ClusterConfig.ClusterConfigBuilder;
import org.bbottema.genericobjectpool.expirypolicies.TimeoutSinceLastAllocationExpirationPolicy;

import javax.mail.Session;
import javax.mail.Transport;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Defines a preconfigured {@link ClusterConfig} builder with the following defaults:
 * <ul>
 *      <li>A specialized allocator factory for starting and stopping Transport connections ({@link TransportAllocatorFactory})</li>
 *      <li>Expiration policy of {@value EXPIRY_POLICY_SECONDS} seconds, connections don't last long by default</li>
 *      <li>Core pool size of {@value CORE_POOL_SIZE}, so objects are only created on-demand</li>
 *      <li>Max pool size of {@value MAX_POOL_SIZE}</li>
 * </ul>
 *
 * Configure further using {@code mySmtpClusterConfig.getConfigBuilder().x(a).y(b).z(c);}
 */
@Getter
@ToString
@NoArgsConstructor
@SuppressWarnings("WeakerAccess")
public final class SmtpClusterConfig {
    
    static final int CORE_POOL_SIZE = 0;
    static final int MAX_POOL_SIZE = 4;
    static final int EXPIRY_POLICY_SECONDS = 10;

    private final ClusterConfigBuilder<Session, Transport> configBuilder = ClusterConfig.<Session, Transport>builder()
            .allocatorFactory(new TransportAllocatorFactory())
            .defaultExpirationPolicy(new TimeoutSinceLastAllocationExpirationPolicy<Transport>(EXPIRY_POLICY_SECONDS, SECONDS))
            .defaultCorePoolSize(CORE_POOL_SIZE)
            .defaultMaxPoolSize(MAX_POOL_SIZE);
}