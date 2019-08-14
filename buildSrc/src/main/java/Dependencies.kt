
object ApplicationId {
    val id = "com.medigo.mvvmstarter"
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Versions {
    val gradle = "3.3.2"
    val safeArgs = "2.1.0-alpha01"

    val compileSdk = 28
    val minSdk = 23
    val targetSdk = 28

    val kotlin = "1.3.21"

    val koin = "1.0.2"

    val appCompat = "1.1.0-alpha04"
    val nav = "2.0.0"

}

object Libraries {
    // KOIN
    val koin = "org.koin:koin-android:${Versions.koin}"
    val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
}

object AndroidLibraries {
    // ANDROID
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"
}

object KotlinLibraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}