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

import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.adapter.HomeAdapter;
import fr.wildcodeschool.seeknwild.model.Adventure;

public class AdventureListFragment extends Fragment {

    private AdventureChooseListener listener;

    public AdventureListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdventureChooseListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        final RecyclerView rvHome = view.findViewById(R.id.rvAccueil);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHome.setLayoutManager(layoutManager);

        VolleySingleton.getInstance(getContext()).getAdventures(new Consumer<List<Adventure>>() {
            @Override
            public void accept(List<Adventure> adventures) {
                final HomeAdapter adapter = new HomeAdapter(adventures, getContext(), new Consumer<Adventure>() {
                    @Override
                    public void accept(Adventure adventure) {
                        listener.onAdventureChoosed(adventure);
                    }
                });
                rvHome.setAdapter(adapter);
            }
        });

        return view;
    }

    public interface AdventureChooseListener {

        void onAdventureChoosed(Adventure adventure);
    }
}
