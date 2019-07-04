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

import java.util.ArrayList;
import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.adapter.HomeAdapter;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;

public class AdventureEditFragment extends Fragment {

    private AdventureEditFragment.AdventureEditListener listener;

    public AdventureEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdventureEditFragment.AdventureEditListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_create_adventure_home, container, false);

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();

        final RecyclerView rvHomeCreate = view.findViewById(R.id.rvAdventureHomeCreate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHomeCreate.setLayoutManager(layoutManager);

        final ArrayList<Adventure> adventures = new ArrayList<>();
        VolleySingleton.getInstance(getContext()).getAdventures(new Consumer<List<Adventure>>() {
            @Override
            public void accept(List<Adventure> adventures) {
                final HomeAdapter adapter = new HomeAdapter(adventures, getContext());
                rvHomeCreate.setAdapter(adapter);
            }
        });

        return view;
    }

    public interface AdventureEditListener {

        void onClicked();
    }
}
