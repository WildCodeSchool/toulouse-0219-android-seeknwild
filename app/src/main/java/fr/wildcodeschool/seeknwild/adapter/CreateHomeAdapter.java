package fr.wildcodeschool.seeknwild.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.CreateAdventureActivity;
import fr.wildcodeschool.seeknwild.model.Adventure;

public class CreateHomeAdapter extends RecyclerView.Adapter<CreateHomeAdapter.IdviewHolder> {

    private static Context context;
    private List<Adventure> listAdventure;

    public CreateHomeAdapter(List<Adventure> listAdventure, Context context) {
        this.listAdventure = listAdventure;
        CreateHomeAdapter.context = context;
    }

    @Override
    public CreateHomeAdapter.IdviewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_adventure_item, parent, false);
        CreateHomeAdapter.IdviewHolder idviewHolder = new CreateHomeAdapter.IdviewHolder(view);
        return idviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final IdviewHolder idviewHolder, final int i) {
        idviewHolder.title.setText(listAdventure.get(i).getTitle());
        Glide.with(context).load(listAdventure.get(i).getAdventurePicture()).into(idviewHolder.adventureImage);
        idviewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == idviewHolder.edit.getId()) {
                    Intent intentCreateAdv = new Intent(v.getContext(), CreateAdventureActivity.class);
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
        public TextView inProcess;
        public TextView publish;
        public ImageButton delete;
        public ImageButton edit;
        public ImageView adventureImage;

        public IdviewHolder(View favoritesView) {
            super(favoritesView);
            container = favoritesView;
            title = favoritesView.findViewById(R.id.tvAdventureName);
            inProcess = favoritesView.findViewById(R.id.tvInProcess);
            publish = favoritesView.findViewById(R.id.tvPublish);
            delete = favoritesView.findViewById(R.id.btDelete);
            edit = favoritesView.findViewById(R.id.btEdit);
            adventureImage = favoritesView.findViewById(R.id.ivAdventurePhoto);
        }
    }
}