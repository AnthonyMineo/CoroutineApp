package com.example.coroutineapp.injections;

import com.example.coroutineapp.utils.GithubService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    Gson provideGson() { return new GsonBuilder().create(); }

    @Provides
    Retrofit provideRetrofit(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/")
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    GithubService provideGithubService(Retrofit retrofit){
        return retrofit.create(GithubService.class);
    }
}
