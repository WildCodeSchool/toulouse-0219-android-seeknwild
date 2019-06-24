package fr.wildcodeschool.seeknwild.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.StartAdventureActivity;
import fr.wildcodeschool.seeknwild.model.Adventure;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.IdviewHolder> {

    private static Context context;
    private List<Adventure> listAdventure;

    public HomeAdapter(List<Adventure> listAdventure, Context context) {
        this.listAdventure = listAdventure;
        HomeAdapter.context = context;
    }

    @Override
    public HomeAdapter.IdviewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        IdviewHolder idviewHolder = new IdviewHolder(view);
        return idviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IdviewHolder idviewHolder, int i) {
        idviewHolder.title.setText(listAdventure.get(i).getTitle());
        idviewHolder.distance.setText(String.valueOf(listAdventure.get(i).getDistance()));
        idviewHolder.done.setText(listAdventure.get(i).getAlreadyDone() ? context.getString(R.string.already_done) : "");
        //Glide.with(context).load(listAdventure.get(i).getAdventurePicture()).into(idviewHolder.adventureImage);
        //Glide.with(context).load(listAdventure.get(i).getRate()).into(idviewHolder.starRate);
    }

    @Override
    public int getItemCount() {
        return listAdventure.size();
    }

    public static class IdviewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView distance;
        public TextView done;
        public ImageView adventureImage;
        public ImageView starRate;

        public IdviewHolder(View favoritesView) {
            super(favoritesView);
            title = favoritesView.findViewById(R.id.tvAdventureName);
            distance = favoritesView.findViewById(R.id.tvAdventureDistance);
            done = favoritesView.findViewById(R.id.tvAlreadyDone);
            adventureImage = favoritesView.findViewById(R.id.ivAdventurePhoto);
            starRate = favoritesView.findViewById(R.id.ivStarRate);
            itemView.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, StartAdventureActivity.class);
                    v.getContext().startActivity(i);
                }
            }));
        }
    }
}