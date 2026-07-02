package com.phairplay.cast

import android.content.Context
import com.phairplay.BuildConfig
import com.phairplay.service.ProtocolState
import com.phairplay.util.Logger

/**
 * Phone / tablet Cast receiver implementation.
 *
 * The phone flavor targets AirPlay mirroring to a handset and deliberately does
 * not bundle the Google Cast TV receiver SDK (that SDK is a TV-oriented dependency
 * and requires a registered Cast app ID). The receiver reports DISABLED instead of
 * advertising a protocol it cannot serve — mirroring on the phone is provided by the
 * AirPlay stack, not by Cast.
 */
class CastReceiver(
    @Suppress("UNUSED_PARAMETER") context: Context,
    private val onStateChanged: (ProtocolState) -> Unit
) {
    fun start() {
        Logger.w("Google Cast disabled on phone flavor")
        onStateChanged(ProtocolState.DISABLED)
    }

    fun stop() {
        onStateChanged(ProtocolState.DISABLED)
    }

    companion object {
        fun isAvailable(@Suppress("UNUSED_PARAMETER") context: Context): Boolean = false

        fun isConfigured(appId: String = CAST_APP_ID): Boolean {
            val normalized = appId.trim()
            return normalized.isNotEmpty() &&
                normalized != "TODO_REGISTER_YOUR_CAST_APP_ID" &&
                normalized != "00000000"
        }

        const val CAST_APP_ID = BuildConfig.CAST_APP_ID
    }
}
