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

    // Declaramos la API
    private APIRest myAPIRest;
    //Declaramos el retrofit
    private Retrofit retrofit;
    // Declarar el EXTRAMESSAGE
    public String message;
    // Declaramos los TextViews y el ImageView que aparecen en el layout (solo a los que debemos pasarle un valor)
    private TextView numrepos;
    private TextView numfollowers;
    ImageView activityProfileIVInternet;
    //Declaramos/Creamos el RecyclerView (en la clase donde lo tengamos que utilizar
    private RecyclerView recyclerView;
    private Recycler recycler;
    //Declaramos el spinner de cargando en el Activity donde estamos esperando los datos
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Definimos el nombre del layout que debe abrirse con esta clase
        setContentView(R.layout.activity_profile);

        // Identificamos con el nombre que tenga el RecyclerView en el xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // Definimos ciertos valores de estructura del RecyclerView
        recycler = new Recycler(this);
        recyclerView.setAdapter(recycler);
        recyclerView.setHasFixedSize(true);
        // Le asignamos a cada linea del RecyclerView el LinearLayout de itemfollower
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Identificamos con el nombre que tengan los TextView/ImageView del xml (activity_profile)
        numrepos=(TextView)findViewById(R.id.numReposTXT);
        numfollowers =(TextView)findViewById(R.id.numfollowingtxt);
        activityProfileIVInternet = (ImageView)findViewById(R.id.activityProfileIVInternet);

        // Recogemos el intento de abrir el ProfileActivity
        Intent intent = getIntent();
        // Recogemos el mensaje que nos pasa el MainActivity
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Justo al abrir esta actividad ponemos el spinner de cargando
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Waiting for the server");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        // Abrimos la conexión con la API (siempre antes de cualquier función que necesite su información
        myAPIRest = APIRest.createAPIRest();

        // Llamamos a las funciones que recogeran información de la API
        getData();
        getFollowers();
    }

    // Función para recoger los datos del usuario
    public void getData(){
        // Desarrollamos la función declarada en la interficie (APIRest)
        Call<User> userCall = myAPIRest.getProfile(message);
        userCall.enqueue(new Callback<User>() {
            // Recogemos la información que nos da la API (ON RESPONSE: Conexión la API OK)
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Si recogemos correctamente la información
                if (response.isSuccessful()){
                    // Metemos los campos que nos vengan de la API en una estructura de User (body porque tiene más de un campo)
                    User user = response.body();
                    Log.i("GITHUB",  "login "+user.login +" "+ response.message());
                    //Cogemos la foto del user y se la asignamos al ImageView donde irá colocada
                    Picasso.with(getApplicationContext()).load(user.avatar_url).into(activityProfileIVInternet);
                    Log.i("GITHUB",  "repos "+user.public_repos +" " +response.message());
                    //Cogemos el número de repositorios y se la asignamos al TextView donde irá colocado
                    numrepos.setText(""+user.public_repos);
                    //Cogemos el número de repositorios y se la asignamos al TextView donde irá colocado
                    Log.i("GITHUB" , "followers: "+user.followers+ " "+response.message());
                    numfollowers.setText(""+user.followers);
                    // Como ya hemos obtenido la información podemos cerrar el cargando...
                    progressDialog.hide();
                }
                // Si no recogemos correctamente la información
                else{
                    Log.e("GITHUB", String.valueOf(response.errorBody()));
                    // Al no obtener la información podemos cerrar el cargando...
                    progressDialog.hide();
                    // Le mostramos un mensaje de error al usuario para que no pete la aplicación
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

            // ON FAILURE: Conexión con la API: KO
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("GITHUB", t.getMessage());
                // Al no conectarse con la API podemos cerrar el cargando...
                progressDialog.hide();

                // Le mostramos un mensaje de error al usuario para que no pete la aplicación
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
    // Función para recoger la lista de datos de los followers del usuario
    public void getFollowers(){
        // Desarrollamos la función declarada en la interficie (APIRest)
        Call<List<User>> followerCall = myAPIRest.getFollowers(message);
        followerCall.enqueue(new Callback<List<User>>() {
            // Recogemos la información que nos da la API (ON RESPONSE: Conexión la API OK)
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                // Si recogemos correctamente la información
                if (response.isSuccessful()){
                    // Metemos los campos que nos vengan de la API en una lista de User (body porque tiene más de un campo)
                    List<User> newlist = response.body();
                    // Metemos en el recycler la lista
                    if (newlist.size() != 0){
                        recycler.addFollowers(newlist);
                    }
                    // Como ya hemos obtenido la información podemos cerrar el cargando...
                    progressDialog.hide();
                    for(int i = 0; i < newlist.size(); i++) {
                        Log.i("Login: " + newlist.get(i).login, response.message());
                        Log.i("Size of the list: " + newlist.size(), response.message());
                    }
                }
                // Si no recogemos correctamente la información
                else{
                    Log.e("GITHUB", String.valueOf(response.errorBody()));
                    // Al no obtener la información podemos cerrar el cargando...
                    progressDialog.hide();
                    // Le mostramos un mensaje de error al usuario para que no pete la aplicación
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
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("GITHUB", t.getMessage());
                // Al no conectarse con la API podemos cerrar el cargando...
                progressDialog.hide();

                // Le mostramos un mensaje de error al usuario para que no pete la aplicación
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
}
