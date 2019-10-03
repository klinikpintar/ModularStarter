
object ApplicationId {
    const val id = "id.medigo.modularstarter"
}

object Releases {
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val navigation = ":navigation"
    const val model = ":data:model"
    const val local = ":data:local"
    const val remote = ":data:remote"
    const val repository = ":data:repository"
    const val home = ":features:home"
    const val auth = ":features:auth"
    const val common = ":common"
    const val commonTest = ":common_test"
}

object Versions {
    const val gradle = "3.5.0"
    const val safeArgs = "2.1.0-alpha01"
    const val multidex = "2.0.1"

    const val compileSdk = 28
    const val minSdk = 19
    const val targetSdk = 28

    const val kotlin = "1.3.50"
    const val coroutines = "1.1.1"

    const val playService = "17.0.0"

    const val core = "1.0.1"
    const val activity = "1.0.0"
    const val fragment = "1.1.0"
    const val coreKtx = "1.0.1"
    const val constraintLayout = "1.1.3"
    const val recyclerview = "1.0.0"
    const val cardView = "1.0.0"

    const val koin = "2.0.1"

    const val appCompat = "1.1.0"
    const val nav = "2.0.0"

    const val room = "2.1.0"

    const val gson = "2.8.5"
    const val okHttp = "3.12.1"
    const val retrofit = "2.6.1"
    const val json = "20190722"

    const val lifecycle = "2.1.0-alpha04"

    const val androidTestRunner = "1.2.0"
    const val espressoCore = "3.2.0"

    const val archCoreTest = "2.0.0"
    const val androidJunit = "1.1.0"
    const val mockk = "1.9.2"
    const val fragmentTest = "1.1.0-alpha06"
    const val databinding = "3.3.2"

    const val material = "3.1.1"
    const val sosoito  = "v1.0.3"
    const val easyPermission = "3.0.0"
    const val glide = "4.9.0"
    const val glideProcessor = "4.8.0"
}

object Libraries {
    // KOIN
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinCore = "org.koin:koin-core:${Versions.koin}\""
    const val koinViewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    // ROOM
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    // RETROFIT
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    // MULTIDEX
    const val multidex = "androidx.multidex:multidex:${Versions.multidex}"
    // EASY PERMISSION
    const val easyPermission = "pub.devrel:easypermissions:${Versions.easyPermission}"
    // MATERIAL
    const val material = "com.afollestad.material-dialogs:core:${Versions.material}"
    // SOSITO
    const val sosoito = "com.github.flipboxstudio:sosoito:${Versions.sosoito}"
    // Image Loader
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideProcessor = "com.github.bumptech.glide:compiler:${Versions.glideProcessor}"
    // JSON
    const val json = "org.json:json:${Versions.json}"
}

object AndroidLibraries {
    // KOTLIN
    const val kotlinCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    // ANDROID
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val core = "androidx.core:core:${Versions.core}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val activity = "androidx.activity:activity:${Versions.activity}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"
    const val fragment = "androidx.fragment:fragment:${Versions.fragment}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.nav}"
    const val navigationFrag = "androidx.navigation:navigation-fragment-ktx:${Versions.nav}"

    const val gcm = "com.google.android.gms:play-services-gcm:${Versions.playService}"
    const val maps = "com.google.android.gms:play-services-maps:${Versions.playService}"
    const val location = "com.google.android.gms:play-services-location:${Versions.playService}"
}

object KotlinLibraries {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlinCoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
}

object TestLibraries {
    // ANDROID TEST
    const val androidTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    const val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    const val junit = "androidx.test.ext:junit:${Versions.androidJunit}"
    const val fragmentNav = "androidx.fragment:fragment-testing:${Versions.fragmentTest}"
    // KOIN
    const val koin = "org.koin:koin-test:${Versions.koin}"
    // MOCK WEBSERVER
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
    // MOCK
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    // COROUTINE
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    // DATA BINDING
    const val databinding = "androidx.databinding:databinding-compiler:${Versions.databinding}"
}