package org.eclipse.aether.spi.connector.checksum;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Component performing selection of {@link ChecksumAlgorithmFactory} based on known factory names.
 * Note: this component is NOT meant to be implemented or extended by client, is exposed ONLY to make clients
 * able to get {@link ChecksumAlgorithmFactory} instances.
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 * @since 1.8.0
 */
public interface ChecksumAlgorithmFactorySelector
{
    /**
     * Returns factory for given algorithm name, or throws if algorithm not supported.
     *
     * @throws IllegalArgumentException if asked algorithm name is not supported.
     */
    ChecksumAlgorithmFactory select( String algorithmName );

    /**
     * Returns a list of factories for given algorithm names in order as collection is ordered, or throws if any of the
     * algorithm name is not supported. The returned list has equal count of elements as passed in collection of names,
     * and if names contains duplicated elements, the returned list of algorithms will have duplicates as well.
     *
     * @throws IllegalArgumentException if any asked algorithm name is not supported.
     * @throws NullPointerException if passed in list of names is {@code null}.
     * @since 1.9.0
     */
    default List<ChecksumAlgorithmFactory> selectList( Collection<String> algorithmNames )
    {
        return algorithmNames.stream()
                .map( this::select )
                .collect( toList() );
    }

    /**
     * Returns a collection of supported algorithms. This set represents ALL the algorithms supported by Resolver,
     * and is NOT in any relation to given repository layout used checksums, returned by method {@link
     * org.eclipse.aether.spi.connector.layout.RepositoryLayout#getChecksumAlgorithmFactories()} (in fact, is super set
     * of it).
     */
    Collection<ChecksumAlgorithmFactory> getChecksumAlgorithmFactories();
}
