/*
 * Copyright @ 2018 - present 8x8, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jitsi.videobridge.ice

import org.ice4j.ice.KeepAliveStrategy
import org.ice4j.ice.NominationStrategy
import org.jitsi.config.JitsiConfig
import org.jitsi.metaconfig.config
import org.jitsi.metaconfig.from
import org.jitsi.metaconfig.optionalconfig

class IceConfig private constructor() {
    /**
     * The ICE UDP port.
     */
    val port: Int by config {
        "org.jitsi.videobridge.SINGLE_PORT_HARVESTER_PORT".from(JitsiConfig.legacyConfig)
        "videobridge.ice.udp.port".from(JitsiConfig.newConfig)
    }

    /**
     * The prefix to STUN username fragments we generate.
     */
    val ufragPrefix: String? by optionalconfig {
        "org.jitsi.videobridge.ICE_UFRAG_PREFIX".from(JitsiConfig.legacyConfig)
        "videobridge.ice.ufrag-prefix".from(JitsiConfig.newConfig)
    }

    val keepAliveStrategy: KeepAliveStrategy by config {
        "org.jitsi.videobridge.KEEP_ALIVE_STRATEGY"
            .from(JitsiConfig.legacyConfig)
            .convertFrom<String> { KeepAliveStrategy.fromString(it) }
        "videobridge.ice.keep-alive-strategy"
            .from(JitsiConfig.newConfig)
            .convertFrom<String> { KeepAliveStrategy.fromString(it) }
    }

    val resolveRemoteCandidates: Boolean by config(
        "videobridge.ice.resolve-remote-candidates".from(JitsiConfig.newConfig)
    )

    /**
     * The ice4j nomination strategy policy.
     */
    val nominationStrategy: NominationStrategy by config {
        "videobridge.ice.nomination-strategy"
            .from(JitsiConfig.newConfig)
            .convertFrom<String> { NominationStrategy.fromString(it) }
    }

    /**
     * Whether to advertise ICE candidates with private IP addresses (RFC1918 IPv4 addresses and
     * fec0::/10 or fc00::/7 IPv6 addresses) even to endpoints that have not signaled support for private addresses.
     */
    val advertisePrivateCandidates: Boolean by config(
        "videobridge.ice.advertise-private-candidates".from(JitsiConfig.newConfig)
    )

    companion object {
        @JvmField
        val config = IceConfig()
    }
}
