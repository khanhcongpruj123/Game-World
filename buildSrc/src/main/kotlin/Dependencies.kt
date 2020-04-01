object LibraryVersion {
    const val RETROFIT_COROUTINES_ADAPTER = "0.9.2"
    const val GSON = "2.8.6"
    const val ROOM = "2.2.4"
    const val CIRCLE_PROGRESSBAR = "3.0.3"
    const val KTX = "2.2.0"
    const val LEAK_CANARY = "2.2"
    const val GLIDE = "4.11.0"
    const val MATERIAL_DESIGN = "1.2.0-alpha05"
    const val KOIN = "2.0.1"
    const val NAVIGATION = "2.2.0"
    const val COUROUTINES = "1.3.3"
    const val WORKER = "2.1.0"
    const val RETROFIT = "2.8.1"
}

object LibrariesDependency {

    //kotlin coroutines
    const val COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${LibraryVersion.COUROUTINES}"
    const val ANDROID_COROUTINES =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${LibraryVersion.COUROUTINES}"

    //androidx worker ktx
    const val WORKER_KTX = "androidx.work:work-runtime-ktx:${LibraryVersion.WORKER}"

    //navigation ktx
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${LibraryVersion.NAVIGATION}"
    const val NAVIGATION_UI_KTX =
        "androidx.navigation:navigation-ui-ktx:${LibraryVersion.NAVIGATION}"

    //koin
    const val KOIN_ANDROID = "org.koin:koin-android:${LibraryVersion.KOIN}"
    const val KOIN_ANDROID_SCROPE = "org.koin:koin-android-scope:${LibraryVersion.KOIN}"
    const val KOIN_ANDROID_VIEWMODEL = "org.koin:koin-android-viewmodel:${LibraryVersion.KOIN}"
    const val KOIN_ANDROID_EXT = "org.koin:koin-android-ext:${LibraryVersion.KOIN}"

    //material design
    const val MATERIAL_DESIGN =
        "com.google.android.material:material:${LibraryVersion.MATERIAL_DESIGN}"

    //glide
    const val GLIDE = "com.github.bumptech.glide:glide:${LibraryVersion.GLIDE}"

    const val LIFECYCLE_LIVEDATA_KTX =
        "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.KTX}"

    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.KTX}"


    const val CIRCLE_PROGRESSBAR =
        "com.mikhaellopez:circularprogressbar:${LibraryVersion.CIRCLE_PROGRESSBAR}"

    //ROOM
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${LibraryVersion.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${LibraryVersion.ROOM}"

    //RETROFIT
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${LibraryVersion.RETROFIT}"
    const val RETROFIT_COROUTINES_ADAPTER = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${LibraryVersion.RETROFIT_COROUTINES_ADAPTER}"

    //GSON
    const val GSON = "com.google.code.gson:gson:${LibraryVersion.GSON}"

}

object KAPT {
    const val ROOM_KAPT = "androidx.room:room-compiler:${LibraryVersion.ROOM}"
}

object AnnotationDependency {
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${LibraryVersion.GLIDE}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${LibraryVersion.ROOM}"
}

object DebugDependency {
    const val LEAK_CANARY =
        "com.squareup.leakcanary:leakcanary-android:${LibraryVersion.LEAK_CANARY}"
}

object ModulesDependency {

}
