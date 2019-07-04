package fr.wildcodeschool.seeknwild.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.StartAdventureDescription;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.IdviewHolder> {

    private static Context context;
    private List<Adventure> listAdventure;
    private User user;
    private Long idUser;
    private UserAdventure userAdventure;

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
    public void onBindViewHolder(@NonNull final IdviewHolder idviewHolder, final int i) {
        idviewHolder.title.setText(listAdventure.get(i).getTitle());
        idviewHolder.distance.setText(String.valueOf(listAdventure.get(i).getDistance()));
        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();
        if (listAdventure.get(i).getRate() != null) {
            idviewHolder.starRate.setRating(listAdventure.get(i).getRate());
        }
        //TODO: set already done if user got userAdv on this adv + distance.

       /* if () {
            idviewHolder.done.setText(listAdventure.get(i).getAlreadyDone() ? context.getString(R.string.already_done) : "");
        } */

        idviewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == idviewHolder.container.getId()) {
                    Intent intentCreateAdv = new Intent(v.getContext(), StartAdventureDescription.class);
                    intentCreateAdv.putExtra("idAdventure", listAdventure.get(i).getIdAdventure());
                    v.getContext().startActivity(intentCreateAdv);
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