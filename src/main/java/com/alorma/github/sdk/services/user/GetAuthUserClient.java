package com.alorma.github.sdk.services.user;

import android.content.Context;

import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.github.sdk.utils.GitskariosSettings;
import com.google.gson.Gson;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Bernat on 03/10/2014.
 */
public class GetAuthUserClient extends BaseUsersClient<User> {
    private String accessToken;

    public GetAuthUserClient(Context context, String accessToken) {
        super(context);
        this.accessToken = accessToken;
    }

    @Override
    protected void executeService(UsersService usersService) {
        if (getOnResultCallback() != null) {
            final GitskariosSettings gitskariosSettings = new GitskariosSettings(getContext());

            String userJson = gitskariosSettings.getAuthUserJson();

            final Gson gson = new Gson();
            if (userJson != null) {
                User user = gson.fromJson(userJson, User.class);
                getOnResultCallback().onResponseOk(user, null);
            }

            usersService.me(new Callback<User>() {
                @Override
                public void success(User me, Response response) {
                    getOnResultCallback().onResponseOk(me, null);
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        } else {
            usersService.me(this);
        }
    }

    @Override
    protected String getToken() {
        return accessToken;
    }
}
