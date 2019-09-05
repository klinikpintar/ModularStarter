# ModularStarter

A starter for modular android project.

### Why Modular? 
Going modular gives you lots of benefits such as :
- Faster build time
- Less coupling / dependencies
- Flexibility to adopt different architecture for different module
- On demand feature delivery using Dynamic Module 

## Structure & Concept

### Main Modules
#### App
You can basically leave this one out. App Module is now as small as possible and only used as entry point.
#### Data
This is your module for your data source. Connect to your API, manage cache, creating entities happened here.
#### Features
Each feature must be contained in a separate gradle module.
#### Navigation
This is used for routing purposes, especially between modules.
#### Common
Contains reusable classes and resources that you can use in another modules. We have already added some useful common features that you will need to start your project.


### How to Add New Feature
1. Setup your data source, and update relevant build / android_commons gradle file. Add relevant functionality in data modules (model, local, remote, repository)
2. Add your relevant features. Create new module for each feature
4. Add your relevant features koin module and add them in App application.
3. Connect your features by updating navigation module nav graph


### THIS IS A WORK IN PROGRESS

## TODO
- [ ] Improve readme
- [ ] Screenshots
- [ ] Product flavors
- [ ] Write Unit test and Instrumental test
- [ ] Add recycler view example feature as Dynamic Module
- [ ] More utils
- [ ] Create project generator
- [ ] Define style standard

## Dependencies

### Minimum Version
* Android Lollipop (API 19) or above

### App
* [Jetpack](https://developer.android.com/jetpack/?gclid=CjwKCAjwtajrBRBVEiwA8w2Q8E7yXdD2mDo40oB3ZFEMv7CkG_5_yG8fogXFI6C2fYIIVHbK0KOiExoCiTIQAvD_BwE) - Jetpack is a suite of libraries, tools, and guidance to help developers write high-quality apps easier.
* [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java
* [RxJava](https://github.com/ReactiveX/RxJava) - a library for composing asynchronous and event-based programs by using observable sequences.
* [Koin](https://insert-koin.io/) - A pragmatic lightweight dependency injection framework for Kotlin developers. Written in pure Kotlin
* [OkHttp](https://github.com/square/okhttp/) - HTTP is the way modern applications network. It’s how we exchange data & media. Doing HTTP efficiently makes your stuff load faster and saves bandwidth.
* [Room](https://https://developer.android.com/training/data-storage/room) - Persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.

### Test
* [MockK](https://mockk.io/) - mocking library for Kotlin.
* [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) - A scriptable web server for testing HTTP clients
* [Espresso](https://developer.android.com/training/testing/espresso?authuser=2) - Concise, beautiful, and reliable Android UI tests.
