package fr.wildcodeschool.seeknwild;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AccueilAdapter extends RecyclerView.Adapter<AccueilAdapter.IdviewHolder> {

    private List<AdventureModel> listAdventure = new ArrayList<>();
    private Context context;

    public AccueilAdapter(List<AdventureModel> listAdventure, Context context) {
        this.listAdventure = listAdventure;
        this.context = context;
    }

    @Override
    public AccueilAdapter.IdviewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accueil_recycle_item, parent, false);
        IdviewHolder idviewHolder = new IdviewHolder(view);
        return idviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IdviewHolder idviewHolder, int i) {
        idviewHolder.title.setText(listAdventure.get(i).getTitle());
        idviewHolder.distance.setText(listAdventure.get(i).getDistance());
        idviewHolder.done.setText(listAdventure.get(i).getAlreadyDone());

        Glide.with(context).load(listAdventure.get(i).getImageAdventureUrl()).into(idviewHolder.adventureImage);
        Glide.with(context).load(listAdventure.get(i).getImageStarRate()).into(idviewHolder.starRate);
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
        }
    }
}
