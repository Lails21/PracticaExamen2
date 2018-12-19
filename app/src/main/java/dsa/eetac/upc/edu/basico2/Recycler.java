package dsa.eetac.upc.edu.basico2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {
    // Creamos una lista con la estructura de User para recoger todos los followers de un usuario (llamarlo siempre data)
    private List<User> data;
    // Necesario para el constructor del Recycler
    private Context context;

    // Gestionamos el RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Creamos un ImageView, un TextView y un LinearLayout
        private ImageView followerImageView;
        private TextView followerNameView;
        public LinearLayout linearLayout;

        public ViewHolder(View v) {
            super(v);
            // Los identificamos con el nombre que tengan en el xml
            followerNameView=v.findViewById(R.id.usernametxt);
            followerImageView=v.findViewById(R.id.imageView);
            linearLayout = v.findViewById(R.id.linearLayout);
        }
    }

    // Función para añadir
    public void addFollowers (List<User> followerslist){
        data.addAll(followerslist);
        notifyDataSetChanged();
    }

    //Constructor (utilizar context)
    public Recycler(Context context) {
        this.data = new ArrayList<>();
        this.context=context;
    }

    // Inflamos el RecyclerView con las filas del itemfollower
    @Override
    public Recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfollower, parent, false);
        return new ViewHolder(v);
    }

    // Rellenamos el RecyclerView con toda la información que tenemos en la lista de Data
    @Override
    public void onBindViewHolder(Recycler.ViewHolder holder, int position) {
        // Creamos una variable User y vamos guardando la información de cada posición de la lista Data
        User userdata = ((User) data.get(position));
        // Cogemos solo el nombre del usuario y se lo pasamos al TextView de cada linea del RecyclerView
        holder.followerNameView.setText(userdata.login);
        // Cogemos solo la foto del usuario y se lo pasamos al ImageView de cada linea del RecyclerView
        Picasso.with(context).load(userdata.avatar_url).into(holder.followerImageView);
    }

    // Devuelve el tamaño de la lista de datos
    @Override
    public int getItemCount() {
        return data.size();
    }
}

