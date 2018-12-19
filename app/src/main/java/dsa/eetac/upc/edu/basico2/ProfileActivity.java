package dsa.eetac.upc.edu.basico2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends MainActivity {

    private APIRest myAPIRest;
    private Retrofit retrofit;
    private User user;
    public String message;
    public TextView numrepos;
    public TextView numfollowers;
    ImageView activityProfileIVInternet;
    private RecyclerView recyclerView;
    private Recycler recycler;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recycler = new Recycler(this);
        recyclerView.setAdapter(recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        numrepos=(TextView)findViewById(R.id.numReposTXT);
        numfollowers =(TextView)findViewById(R.id.numfollowingtxt);

        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        activityProfileIVInternet = (ImageView)findViewById(R.id.activityProfileIVInternet);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        myAPIRest = APIRest.createAPIRest();

        getData();
        getFollowers();
    }


    public void getData(){
        Call<User> userCall = myAPIRest.getProfile(message);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("GITHUB", "onRESPONSE"+response.code());
                if (response.isSuccessful()){
                    User user = response.body();
                    Log.i("GITHUB",  "login "+user.login +" "+ response.message());
                    Picasso.with(getApplicationContext()).load(user.avatar_url).into(activityProfileIVInternet);
                    Log.i("GITHUB",  "repos "+user.public_repos +" " +response.message());
                    numrepos.setText(""+user.public_repos);
                    Log.i("GITHUB" , "followers: "+user.followers+ " "+response.message());
                    numfollowers.setText(""+user.followers);
                    progressDialog.hide();
                }
                else{
                    Log.e("GITHUB", String.valueOf(response.errorBody()));
                    progressDialog.hide();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);

                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage(response.message())
                            .setCancelable(false)
                            .setPositiveButton("OK", ((dialog, which) -> finish()));
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("GITHUB", t.getMessage());
                progressDialog.hide();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProfileActivity.this);

                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(t.getMessage())
                        .setCancelable(false)
                        .setPositiveButton("OK", ((dialog, which) -> finish()));
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
    }

    public void getFollowers(){
        Call<List<User>> followerCall = myAPIRest.getFollowers(message);
        followerCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    List<User> newlist = response.body();
                    if (newlist.size() != 0){
                        recycler.addFollowers(newlist);
                    }
                    progressDialog.hide();
                    for(int i = 0; i < newlist.size(); i++) {
                        Log.i("Login: " + newlist.get(i).login, response.message());
                        Log.i("Size of the list: " + newlist.size(), response.message());
                    }
                }
                else{
                    Log.e("Response failure", response.message());
                    progressDialog.hide();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("No api connection", t.getMessage());
                progressDialog.hide();

            }
        });
    }
}
