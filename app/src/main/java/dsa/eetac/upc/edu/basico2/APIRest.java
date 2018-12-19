package dsa.eetac.upc.edu.basico2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRest {

    // URL donde se encuentra alojada la API
    String BASE_URL = "https://api.github.com/users/";

    // Llamamos a la función para obtener la foto, los followers & los repositoris a partir de su username
    @GET("{username}")
    Call<User> getProfile(@Path("username") String username);

    // Llamamos a la función para obtener la lista de followers (contiene la foto y username)
    @GET("{username}/followers")
    Call<List<User>> getFollowers(@Path("username") String username);

    // Creamos la conexión con la API
    static APIRest createAPIRest() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(APIRest.class);
    }
}
