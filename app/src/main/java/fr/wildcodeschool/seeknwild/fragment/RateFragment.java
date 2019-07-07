package fr.wildcodeschool.seeknwild.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Consumer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.UserAdventureSingleton;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.Treasure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class RateFragment extends Fragment {

    private RateFragment.RateFragmentListener listener;
    private View view;
    private User user;
    private RatingBar ratingBar;
    private Treasure treasure;
    private UserAdventure userAdventure;

    public RateFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (RateFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_note, container, false);

        ratingBar = view.findViewById(R.id.rbStar);
        ratingBar.setRating(5);

        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();

        UserAdventureSingleton userAdventureSingleton = UserAdventureSingleton.getInstance();
        userAdventure = userAdventureSingleton.getUserAdventure();
        Button btContinued = view.findViewById(R.id.btContinue);
        final Adventure adventure = userAdventure.getAdventure();
        final Long idAdventure = userAdventure.getAdventure().getIdAdventure();
        btContinued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adventure.setRate(ratingBar.getRating());
                VolleySingleton.getInstance(getContext()).updateAdventure(idAdventure, adventure, new Consumer<Adventure>() {
                    @Override
                    public void accept(Adventure response) {
                        UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                        UserSingleton.getInstance().setUser(user);
                        listener.onRatedAdventure();
                    }
                });
            }
        });
        return view;
    }

    public interface RateFragmentListener {

        void onRatedAdventure();
    }
}
