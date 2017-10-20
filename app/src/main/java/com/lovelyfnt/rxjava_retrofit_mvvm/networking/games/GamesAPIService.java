package com.lovelyfnt.rxjava_retrofit_mvvm.networking.games;

import com.lovelyfnt.rxjava_retrofit_mvvm.networking.games.exception.GamesTechFailureException;

import retrofit.RestAdapter;
import rx.Observable;

/**
 * Created by kh2000park on 2017. 10. 20..
 */

public class GamesAPIService {

    private IGamesAPI gamesAPI;
    private boolean isRequestingGames;

    public GamesAPIService(RestAdapter restAdapter){
        this.gamesAPI = restAdapter.create(IGamesAPI.class);
    }

    public boolean isRequestingGames() {
        return isRequestingGames;
    }

    public Observable<GamesResponse> getGames(GamesRequest gamesRequest){
        return gamesAPI.getGamesInformation(gamesRequest.getNickname())
                .doOnSubscribe(() -> isRequestingGames = true)
                .doOnTerminate(() -> isRequestingGames = false)
                .doOnError(this::handleGamesError);
    }


    private void handleGamesError(Throwable throwable){
        throw new GamesTechFailureException();
    }
}
