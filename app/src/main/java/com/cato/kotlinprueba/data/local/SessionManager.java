package com.cato.kotlinprueba.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.samer.samerclient.data.models.UserEntity;
import com.samer.samerclient.data.models.UserEntityInvitado;

/**
 * Created by kath on 09/04/18.
 */

public class SessionManager {
    public static final String PREFERENCE_NAME = "SymbiosisClient";
    public static int PRIVATE_MODE = 0;

    /**
     USUARIO DATA SESSION - JSON
     */
    public static final String USER_TOKEN = "user_code";
    public static final String USER_JSON = "user_json";
    public static final String USER_TOKEN_INVITADO = "user_code";
    public static final String USER_JSON_INVITADO = "user_json";
    public static final String IS_LOGIN = "user_login";
    public static final String USER_MAIL = "user_mail";

    public static final String IS_INVITED = "is_invited";
    public static final String USER_INVITED = "user_invited";



    public static final String EXPLANATION_SCHEDULE = "schedule_explanation";



    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        preferences = this.context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public boolean isLogin()  {
        return preferences.getBoolean(IS_LOGIN, false);
    }


    public void openSession(String token) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }
    public void openSessionInvitado(String token) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_TOKEN_INVITADO, token);
        editor.commit();
    }


    public boolean isExplanationSchedule()  {
        return preferences.getBoolean(EXPLANATION_SCHEDULE, false);
    }

    public void setExplanationSchedle(boolean active){
        editor.putBoolean(EXPLANATION_SCHEDULE, active);
        editor.commit();
    }


    public void closeSession() {
        if (isUserInvited()){
            editor.putBoolean(IS_LOGIN, false);
            editor.putString(USER_TOKEN, null);
            editor.putString(USER_JSON, null);
            editor.commit();
        }else{
            editor.putBoolean(IS_LOGIN, false);
            editor.putString(USER_TOKEN_INVITADO, null);
            editor.putString(USER_JSON_INVITADO, null);
            editor.commit();
        }

    }


    public String getUserToken() {
        if (isLogin()) {
            return preferences.getString(USER_TOKEN, "");
        } else {
            return "";
        }
    }

    public String getUserTokenInvitado() {
        if (isLogin()) {
            return preferences.getString(USER_TOKEN_INVITADO, "");
        } else {
            return "";
        }
    }

    public void setUser(UserEntity userEntity) {
        if (userEntity != null) {
            Gson gson = new Gson();
            String user = gson.toJson(userEntity);
            editor.putString(USER_JSON, user);
        }
        editor.commit();
    }
    public void setUserInvitado(UserEntityInvitado userEntity) {
        if (userEntity != null) {
            Gson gson = new Gson();
            String user = gson.toJson(userEntity);
            editor.putString(USER_JSON_INVITADO, user);
        }
        editor.commit();
    }

    public UserEntity getUserEntityInvitado() {
        String userData = preferences.getString(USER_JSON_INVITADO, null);
        return new Gson().fromJson(userData, UserEntity.class);
    }
    public UserEntity getUserEntity() {
        String userData = preferences.getString(USER_JSON, null);
        return new Gson().fromJson(userData, UserEntity.class);
    }

    public void setEmailUser(String email) {
        editor.putString(USER_MAIL, email);
        editor.commit();
    }

    public String getUserEmail() {
        return preferences.getString(USER_MAIL, "");
    }

    public boolean isUserInvited()  {
        return preferences.getBoolean(IS_INVITED, false);
    }

    public void setIsUserInvited(boolean active){
        editor.putBoolean(IS_INVITED, active);
        editor.commit();
    }


    public String setUserInvited()  {
        return preferences.getString(USER_INVITED, " ");
    }

    public void getUserInvited(String active){
        editor.putString(USER_INVITED, active);
        editor.commit();
    }
}
