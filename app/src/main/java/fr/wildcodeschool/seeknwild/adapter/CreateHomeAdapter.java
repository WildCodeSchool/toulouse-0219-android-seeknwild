package fr.wildcodeschool.seeknwild.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Consumer;
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
import fr.wildcodeschool.seeknwild.model.Adventure;

public class CreateHomeAdapter extends RecyclerView.Adapter<CreateHomeAdapter.IdviewHolder> {

    private static Context context;
    private List<Adventure> listAdventure;
    private Consumer<Adventure> listener;

    public CreateHomeAdapter(List<Adventure> listAdventure, Context context, Consumer<Adventure> listener) {
        this.listAdventure = listAdventure;
        CreateHomeAdapter.context = context;
        this.listener = listener;
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
                    listener.accept(listAdventure.get(i));
                }
            }
        });
        if (listAdventure.get(i).isPublished()) {
            idviewHolder.edit.setEnabled(false);
            idviewHolder.edit.setVisibility(View.INVISIBLE);
            idviewHolder.publish.setText(context.getString(R.string.adv_published));
        } else {
            idviewHolder.publish.setText(context.getString(R.string.in_process));
        }

    }

    @Override
    public int getItemCount() {
        return listAdventure.size();
    }

    public static class IdviewHolder extends RecyclerView.ViewHolder {
        public View container;
        public TextView title;
        public TextView publish;
        public ImageButton edit;
        public ImageView adventureImage;

        public IdviewHolder(View favoritesView) {
            super(favoritesView);
            container = favoritesView;
            title = favoritesView.findViewById(R.id.tvAdventureName);
            publish = favoritesView.findViewById(R.id.tvPublish);
            edit = favoritesView.findViewById(R.id.btEdit);
            adventureImage = favoritesView.findViewById(R.id.ivAdventurePhoto);
        }
    }
}