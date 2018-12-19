package dsa.eetac.upc.edu.basico2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    // Declaramos todas las variables que queremos coger del JSON (poner exactamente el mismo nombre que sale en la API)
    @SerializedName("login")
    @Expose
    String login;
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("avatar_url")
    @Expose
    String avatar_url;
    @SerializedName("followers")
    @Expose
    int followers;
    @SerializedName("following")
    @Expose
    int following;
    @SerializedName("public_repos")
    @Expose
    int public_repos;

    // Constructor de todas las variables que tiene User
    public User(String login, int id, String avatar_url, int followers, int following, int public_repos) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.followers = followers;
        this.following = following;
        this.public_repos = public_repos;
    }

    // Setters & Getters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }
}
