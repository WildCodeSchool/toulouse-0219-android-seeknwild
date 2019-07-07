package fr.wildcodeschool.seeknwild.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.UserAdventureSingleton;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.model.Treasure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class CannotRateFragment extends Fragment {

    private CannotRateFragment.CannotRateFragmentListener listener;
    private View view;
    private User user;
    private Treasure treasure;
    private UserAdventure userAdventure;

    public CannotRateFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (CannotRateFragment.CannotRateFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cannot_rate, container, false);

        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();


        UserAdventureSingleton userAdventureSingleton = UserAdventureSingleton.getInstance();
        userAdventure = userAdventureSingleton.getUserAdventure();
        List<Treasure> treasures = userAdventure.getAdventure().getTreasures();
        treasures.get(userAdventure.getNbTreasure());

        Button btContinued = view.findViewById(R.id.btContinue);
        btContinued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                UserSingleton.getInstance().setUser(user);
                listener.onNotRatedAdventure();
            }
        });
        return view;
    }

    public interface CannotRateFragmentListener {

        void onNotRatedAdventure();
    }
}