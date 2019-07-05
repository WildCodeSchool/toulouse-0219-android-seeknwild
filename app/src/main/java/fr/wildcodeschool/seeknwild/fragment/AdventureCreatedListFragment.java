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

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.adapter.CreateHomeAdapter;
import fr.wildcodeschool.seeknwild.model.Adventure;

public class AdventureCreatedListFragment extends Fragment {

    private AdventureCreatedListFragment.AdventureEditListener listener;

    public AdventureCreatedListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdventureCreatedListFragment.AdventureEditListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_create_adventure_home, container, false);

        final RecyclerView rvHomeCreate = view.findViewById(R.id.rvAdventureHomeCreate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHomeCreate.setLayoutManager(layoutManager);

        final CreateHomeAdapter adapter = new CreateHomeAdapter(
                UserSingleton.getInstance().getUser().getAdventures(),
                getContext(), new Consumer<Adventure>() {
            @Override
            public void accept(Adventure adventure) {
                listener.onAdventureEdited(adventure);
            }
        });
        rvHomeCreate.setAdapter(adapter);

        return view;
    }

    public interface AdventureEditListener {

        void onAdventureEdited(Adventure adventure);
    }
}
