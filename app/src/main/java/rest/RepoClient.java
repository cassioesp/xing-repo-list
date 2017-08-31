package rest;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import domain.Repository;
import interfaces.GitHubService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Singleton class for REST Client.
 */
public class RepoClient {

    private static final String BASE_URL = "https://api.github.com/";

    private static RepoClient instance;

    private GitHubService gitHubService;

    public static RepoClient getInstance() {
        if (instance == null) {
            instance = new RepoClient();
        }
        return instance;
    }

    private RepoClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.
                        LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gitHubService = retrofit.create(GitHubService.class);
    }

    public Observable<List<Repository>> getRepositories(@NonNull String userName,
                                                        @NonNull int page,
                                                        @NonNull int itemsPerPage) {
        return gitHubService.getRepositories(userName, page, itemsPerPage);
    }
}