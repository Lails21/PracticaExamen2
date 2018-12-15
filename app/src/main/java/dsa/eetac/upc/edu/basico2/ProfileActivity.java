package dsa.eetac.upc.edu.basico2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends MainActivity {

    private APIRest myAPIRest;
    private Retrofit retrofit;
    public String message;
    private static final String URL_INTERNET="https://avatars2.githubusercontent.com/u/43338918?v=4";
    private static final String URL_INTERNET_PICASO="https://avatars3.githubusercontent.com/u/43338979?v=4";

    private ImageView activityProfileIVInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView2);
        textView.setText(message);

        setUpView();
        loadImagenByInternetUrlWithPicasso();

        myAPIRest = APIRest.createAPIRest();
            getData();
    }

    private void setUpView(){
        activityProfileIVInternet=findViewById(R.id.activityProfileIVInternet);
    }

    private void loadImagenByInternetUrlWithPicasso(){
        Picasso.with(getApplicationContext()).load(URL_INTERNET).into(activityProfileIVInternet);
    }

    public void getData(){
        Call<User> userCall = myAPIRest.getProfile(message);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    Log.i("Login:" + user.login, response.message());
                }
                else{
                    Log.e("No api connection", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("No api connection", t.getMessage());
            }
        });

    }
}
