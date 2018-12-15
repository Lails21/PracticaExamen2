package dsa.eetac.upc.edu.basico2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Recycler extends RecyclerView.Adapter<Recycler.ViewHolder> {
    private List<User> data;

    //Asign the text TextView to the text1 in the layout
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(android.R.id.text1);
        }
    }

    //Constructor
    public Recycler(List<User> data) {
        this.data = data;
    }

    @Override
    public Recycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_selectable_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Recycler.ViewHolder holder, int position) {
        User userdata = ((User) data.get(position));
        holder.text.setText(userdata.toString());
        holder.itemView.setTag(userdata.id);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

