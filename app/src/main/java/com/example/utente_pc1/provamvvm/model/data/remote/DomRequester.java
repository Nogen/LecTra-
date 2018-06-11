package com.example.utente_pc1.provamvvm.model.data.remote;

import com.example.utente_pc1.provamvvm.util.exceptions.ConnectionException;
import com.example.utente_pc1.provamvvm.util.exceptions.LoginException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Base64;
import android.util.Log;


public class DomRequester {
    private static final String AUTH = "Authorization";
    private static final String COOKIE = "Cookie";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String BASIC = "Basic ";

    private String base64Auth;
    private String cookie = new String("");
    private URL url = null;
    private HttpURLConnection connection;
    private ConnectionException error = new ConnectionException("Internet connection missing!");

    public DomRequester() {

    }

    public DomRequester(String name, String password) {
        this.setAuthentication(name, password);
    }

    public DomRequester(String name, String password, String url) {
        this(name, password);
        this.setUrl(url);
    }

    public void setAuthentication(String name, String password) {
        String auth = name + ":" + password;
        this.base64Auth = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
        this.cookie = new String();
    }

    public void resetAuthentication() {
        this.base64Auth = new String();
        this.cookie = new String();
    }

    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (IOException e) {
            this.url = null;
        }
    }

    private void retCookie() throws ConnectionException {
        if (this.url == null) {
            throw error;
        }
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setRequestProperty(AUTH, BASIC + base64Auth);
            String tmpcookie = connection.getHeaderField(SET_COOKIE);

            this.cookie = (tmpcookie != null) ? tmpcookie : this.cookie;

        } catch (IOException e) {
            e.printStackTrace();
            throw error;
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    public String retriveDom() throws ConnectionException, LoginException {
        if (this.cookie.isEmpty()) {
            this.retCookie();
        }
        String dom = new String();
        String line;
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setRequestProperty(AUTH, BASIC + base64Auth);
            connection.setRequestProperty(COOKIE, this.cookie);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                dom += line + "\n";
            }
            return dom;

        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof ConnectException) {
                throw error;
            } else {
                throw new LoginException("Wrong account name or password");
            }

        } catch (Exception e1) {
            e1.printStackTrace();
            throw error;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }


    public void doSingleReq() throws ConnectionException {
        this.retCookie();
    }


}