package hk.eduhk.typeduck.util

import android.net.Uri
import androidx.preference.Preference
import hk.eduhk.typeduck.BuildConfig
import hk.eduhk.typeduck.data.AppPrefs

object AppVersionUtils {
    /**
     * Compare the application's version name
     * to check if this version is a newer/older version.
     *
     * @param prefs the [AppPrefs] instance.
     */
    fun isDifferentVersion(prefs: AppPrefs): Boolean {
        val currentVersionName = BuildConfig.VERSION_NAME
        val lastVersionName = prefs.internal.lastVersionName
        return !currentVersionName.contentEquals(lastVersionName).also {
            prefs.internal.lastVersionName = currentVersionName
        }
    }

    fun Preference.writeLibraryVersionToSummary(versionCode: String) {
        val commitHash = if (versionCode.contains("-g")) {
            versionCode.replace("^(.*-g)([0-9a-f]+)(.*)$".toRegex(), "$2")
        } else {
            versionCode.replace("^([^-]*)(-.*)$".toRegex(), "$1")
        }
        this.summary = versionCode
        val intent = this.intent
        if (intent != null) {
            intent.data = Uri.withAppendedPath(intent.data, "commits/$commitHash")
            this.intent = intent
        }
    }
}
