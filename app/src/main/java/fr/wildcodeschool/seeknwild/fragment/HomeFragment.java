package fr.wildcodeschool.seeknwild.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Consumer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.HomeActivity;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.adapter.HomeAdapter;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;

public class HomeFragment extends Fragment {

    HomeListener listener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (HomeListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();

        final RecyclerView rvHome = view.findViewById(R.id.rvAccueil);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHome.setLayoutManager(layoutManager);

        final ArrayList<Adventure> adventures = new ArrayList<>();
        VolleySingleton.getInstance(getContext()).getAdventures(new Consumer<List<Adventure>>() {
            @Override
            public void accept(List<Adventure> adventures) {
                final HomeAdapter adapter = new HomeAdapter(adventures, getContext());
                rvHome.setAdapter(adapter);
            }
        });

        return view;
    }

    public interface HomeListener {

        void onGalleryClicked();
    }
}
