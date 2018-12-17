package dsa.eetac.upc.edu.basico2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {
    private List<User> data;
    private Context context;

    //Asign the text TextView to the text1 in the layout
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        private ImageView followerImageView;
        private TextView followerNameView;
        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(android.R.id.text1);
        }
    }

    public void addFollowers (List<User> followerslist){
        data.addAll(followerslist);
        notifyDataSetChanged();
    }

    //Constructor
    public Recycler(Context context) {
        this.data = new ArrayList<>();
        this.context=context;
    }

    @Override
    public Recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfollower, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Recycler.ViewHolder holder, int position) {
        User userdata = ((User) data.get(position));
        holder.followerNameView.setText(userdata.login);
        Picasso.with(context).load(userdata.avatar_url).into(holder.followerImageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

