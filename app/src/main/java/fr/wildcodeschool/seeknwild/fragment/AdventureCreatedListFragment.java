package fr.wildcodeschool.seeknwild.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import fr.wildcodeschool.seeknwild.model.User;

public class AdventureCreatedListFragment extends Fragment {

    private AdventureCreatedListFragment.AdventureEditListener listener;
    private Long userId;
    private CreateHomeAdapter adapter;

    public AdventureCreatedListFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdventureCreatedListFragment.AdventureEditListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        userId = user.getIdUser();
        View view = inflater.inflate(R.layout.activity_create_adventure_home, container, false);
        final Adventure adventure = new Adventure();
        FloatingActionButton floatbtCreateAdventure = view.findViewById(R.id.fabCreateAdventure);
        floatbtCreateAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAdventureCreate();
            }
        });

        final RecyclerView rvHomeCreate = view.findViewById(R.id.rvAdventureHomeCreate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvHomeCreate.setLayoutManager(layoutManager);

        // TODO : ne pas charger du singleton mais de Volley, sinon les aventures en cours de création n'apparaissent pas
        adapter = new CreateHomeAdapter(
                UserSingleton.getInstance().getUser().getAdventures(),
                getContext(), new Consumer<Adventure>() {
            @Override
            public void accept(Adventure adventure) {
                listener.onAdventureEdited(adventure);
                adapter.notifyDataSetChanged();
            }
        });
        rvHomeCreate.setAdapter(adapter);


        return view;
    }

    public interface AdventureEditListener {

        void onAdventureEdited(Adventure adventure);

        void onAdventureCreate();
    }
}
