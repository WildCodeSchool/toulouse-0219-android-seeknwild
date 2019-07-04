package fr.wildcodeschool.seeknwild.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Consumer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.SearchTreasureActivity;
import fr.wildcodeschool.seeknwild.activity.StartAdventureDescription;
import fr.wildcodeschool.seeknwild.activity.UserAdventureSingleton;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class AdventureDescriptionFragment extends Fragment {

    private AdventureDescriptionListener listener;
    private Long idAdventure;

    public AdventureDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdventureDescriptionListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_start_aventure_description, container, false);
        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        final Long userId = user.getIdUser();

        final Long idAdventure = this.getArguments().getLong("idAdventure");

        final TextView title = view.findViewById(R.id.tvTitleAdv);
        final TextView description = view.findViewById(R.id.tvDescriptionAdv);
        final ImageView imageAdv = view.findViewById(R.id.ivAdventure);


        VolleySingleton.getInstance(getContext()).getAdventureById(idAdventure, new Consumer<Adventure>() {
            @Override
            public void accept(Adventure adventure) {
                title.setText(adventure.getTitle());
                description.setText(adventure.getDescription());
                Glide.with(getContext()).load(adventure.getAdventurePicture()).into(imageAdv);
            }
        });

        Button startAdv = view.findViewById(R.id.btStartAdventure);
        startAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolleySingleton.getInstance(getContext()).createUserAdventure(userId, idAdventure,
                        new Consumer<UserAdventure>() {
                            @Override
                            public void accept(UserAdventure userAdventure) {
                                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                                Intent intent = new Intent(getContext(), SearchTreasureActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

        return view;
    }

    public interface AdventureDescriptionListener {

        void onAdventureSelected(Adventure adventure);
    }
}
