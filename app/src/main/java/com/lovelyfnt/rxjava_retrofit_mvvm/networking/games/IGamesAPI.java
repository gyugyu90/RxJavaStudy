package com.lovelyfnt.rxjava_retrofit_mvvm.networking.games;

import retrofit.http.GET;
import retrofit.http.Header;
import rx.Observable;

/**
 * Created by kh2000park on 2017. 10. 20..
 */
//completed
public interface IGamesAPI {

    @GET("/games")
    Observable<GamesResponse> getGamesInformation(@Header("nickname") String nickname);

}
