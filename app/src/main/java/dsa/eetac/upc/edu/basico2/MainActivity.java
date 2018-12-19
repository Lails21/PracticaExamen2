package dsa.eetac.upc.edu.basico2;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    // Para recoger el texto del textview al clickarle al button
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Definimos el nombre del layout que debe abrirse con esta clase
        setContentView(R.layout.activity_main);

        // Creamos un botón y lo identificamos con el nombre que tenga el botón en el xml
        final Button button = (Button)findViewById(R.id.button);

        // Le asignamos al botón el evento click y definimos que hay que hacer cuando clickemos
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intentamos abrir la nueva activity (ProfileActivity)
                Intent intent =  new Intent(view.getContext(), ProfileActivity.class);
                // Creamos una variable y la identificamos con el nombre que tenga el textview en el xml
                EditText editText = (EditText) findViewById(R.id.nameText);
                // Guardamos el valor insertado en el textview en una variable tipo String
                String message = editText.getText().toString();
                //Le pasamos al ProfileActivity el string del textview (con la variable message)
                intent.putExtra(EXTRA_MESSAGE, message);
                // Abrimos la nueva activity
                startActivity(intent);
            }
        });

    }
}
