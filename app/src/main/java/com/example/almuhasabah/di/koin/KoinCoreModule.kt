package com.example.almuhasabah.di.koin

//class KoinCoreModule {
//    val koinCoreModule = module {
//        single<ISharedPreferenceService>(definition = { SharedPreferenceImp(androidApplication()) })
//        single<Interceptor> { AuthTokenInterceptor(get()) }
//        single<IMuhasabahClientBuilder> { MuhasabahClientBuilder(get(), get()) }
//        single<IMuhasabahClient> { MuhasabahClient(get()) }
//        single<IUserControllerRepository> { UserControllerRepository(get<IMuhasabahClient>().getUserControllerRepository()) }
//        single<ISelfControllerRepository> { SelfControllerRepository(get<IMuhasabahClient>().getSelfControllerRepository())}
//        single<IProfileControllerRepository> { ProfileControllerRepository(get<IMuhasabahClient>().getProfileControllerRepository())}
//    }
//}