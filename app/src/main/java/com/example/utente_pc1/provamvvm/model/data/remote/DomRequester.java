package com.example.utente_pc1.provamvvm.model.data.remote;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Base64;


class DomRequester {
    private static final String AUTH = "Authorization";
    private static final String COOKIE = "Cookie";
    private static final String SET_COOKIE = "Set-Cookie";
    private static final String BASIC = "Basic ";

    private String base64Auth;
    private String cookie = "";
    private URL url = null;
    private HttpURLConnection connection;
    private Exception error = new Exception("Internet connection missing!");

    public DomRequester() {

    }

    private DomRequester(String name, String password) {
        this.setAuthentication(name, password);
    }

    public DomRequester(String name, String password, String url) {
        this(name, password);
        this.setUrl(url);
    }

    public void setAuthentication(String name, String password) {
        String auth = name + ":" + password;
        this.base64Auth = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
        this.cookie = "";
    }

    public void resetAuthentication() {
        this.base64Auth = "";
        this.cookie = "";
    }

    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (IOException e) {
            this.url = null;
        }
    }

    private void retCookie() throws Exception {
        if (this.url == null) {
            throw error;
        }
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setRequestProperty(AUTH, BASIC + base64Auth);
            String tmpcookie = connection.getHeaderField(SET_COOKIE);

            this.cookie = (tmpcookie != null) ? tmpcookie : this.cookie;

        } catch (IOException e) {
            throw error;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    public String retriveDom() throws Exception {
        if (this.cookie.isEmpty()) {
            this.retCookie();
        }
        StringBuilder dom = new StringBuilder();
        String line;
        try {
            connection = (HttpURLConnection) this.url.openConnection();
            connection.setRequestProperty(AUTH, BASIC + base64Auth);
            connection.setRequestProperty(COOKIE, this.cookie);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null) {
                dom.append(line).append("\n");
            }
            return dom.toString();

        } catch(ConnectException e0) {
            throw error;
        } catch (IOException e) {
            throw new Exception("Wrong account name or password");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }


    public void doSingleReq() throws Exception {
        this.retCookie();
    }


}