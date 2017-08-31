package interfaces;

import java.util.List;

import domain.Repository;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GitHubService {

    @GET("users/{user}/repos")
    Observable<List<Repository>> getRepositories(
            @Path("user") String userName,
            @Query("page") int page,
            @Query("per_page") int itemsPerPage);
}