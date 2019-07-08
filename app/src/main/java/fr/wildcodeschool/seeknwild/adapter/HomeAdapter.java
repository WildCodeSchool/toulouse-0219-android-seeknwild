package fr.wildcodeschool.seeknwild.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.util.Consumer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.StartAdventureDescription;
import fr.wildcodeschool.seeknwild.model.Adventure;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.IdviewHolder> {

    private static Context context;
    private List<Adventure> listAdventure;
    private Consumer<Adventure> listener;

    public HomeAdapter(List<Adventure> listAdventure, Context context, Consumer<Adventure> listener) {
        this.listAdventure = listAdventure;
        HomeAdapter.context = context;
        this.listener = listener;
    }

    @Override
    public HomeAdapter.IdviewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        IdviewHolder idviewHolder = new IdviewHolder(view);
        return idviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IdviewHolder idviewHolder, final int i) {
        idviewHolder.title.setText(listAdventure.get(i).getTitle());
        idviewHolder.distance.setText(String.valueOf(listAdventure.get(i).getDistance()));
        Glide.with(context).load(listAdventure.get(i).getAdventurePicture()).into(idviewHolder.adventureImage);
        if (listAdventure.get(i).getRate() != null) {
            idviewHolder.starRate.setRating(listAdventure.get(i).getRate());
        }
        //TODO: set already done if user got userAdv on this adv + distance.
        idviewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == idviewHolder.container.getId()) {
                    listener.accept(listAdventure.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAdventure.size();
    }

    public static class IdviewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView title;
        public TextView distance;
        public TextView done;
        public ImageView adventureImage;
        public RatingBar starRate;

        public IdviewHolder(View favoritesView) {
            super(favoritesView);
            container = favoritesView;
            title = favoritesView.findViewById(R.id.tvAdventureName);
            distance = favoritesView.findViewById(R.id.tvAdventureDistance);
            done = favoritesView.findViewById(R.id.tvAlreadyDone);
            adventureImage = favoritesView.findViewById(R.id.ivAdventurePhoto);
            starRate = favoritesView.findViewById(R.id.ivStarRate);
        }
    }
}