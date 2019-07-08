package fr.wildcodeschool.seeknwild.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.Consumer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.io.IOException;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;

public class AdventureCreateFragment extends Fragment {

    private Uri mFileUri = null;
    private View view;
    private Long idAdventure;

    private AdventureCreateFragment.CreateAdventureListener listener;

    public AdventureCreateFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdventureCreateFragment.CreateAdventureListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_create_adventure, container, false);

        idAdventure = this.getArguments().getLong("idAdventure");

        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        final Long idUser = user.getIdUser();

        ImageView ivLogo = view.findViewById(R.id.ivAdventure);
        Button btCreateTresor = view.findViewById(R.id.btTreasure);

        FloatingActionButton floatBtTakePicTreasure = view.findViewById(R.id.fbTakePicAdventure);
        floatBtTakePicTreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTakeAdventurePicture();
            }
        });

        if (idAdventure != null && idAdventure > -1) {
            Adventure current = null;
            for (Adventure adventure : UserSingleton.getInstance().getUser().getAdventures()) {
                if (adventure.getIdAdventure().equals(idAdventure)) {
                    current = adventure;
                    break;
                }
            }
            if (current != null) {

                Glide.with(getContext()).load(current.getAdventurePicture()).into(ivLogo);

                EditText etTitre = view.findViewById(R.id.etNameAdventure);
                EditText etDescription = view.findViewById(R.id.etDescriptionAdventure);
                etTitre.setText(current.getTitle());
                etDescription.setText(current.getDescription());
            }
        }

        btCreateTresor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etNameAdventure = view.findViewById(R.id.etNameAdventure);
                final EditText etDescriptionAdventure = view.findViewById(R.id.etDescriptionAdventure);
                final Adventure newAdventure = new Adventure();
                newAdventure.setTitle(etNameAdventure.getText().toString());
                newAdventure.setDescription(etDescriptionAdventure.getText().toString());
                if (!etNameAdventure.getText().toString().isEmpty()
                        && !etDescriptionAdventure.getText().toString().isEmpty()) {
                    if (idAdventure == -1) {
                        if (mFileUri == null) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(getString(R.string.photoDeLAventure));
                            builder.setMessage(getString(R.string.veuillezPrendreUnePhotoDelavenrture));
                            builder.setPositiveButton(getString(R.string.ok), null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return;
                        }
                        VolleySingleton.getInstance(getContext()).createAdventure(newAdventure, idUser, new Consumer<Adventure>() {
                            @Override
                            public void accept(final Adventure adventure) {
                                idAdventure = adventure.getIdAdventure();
                                try {
                                    ProgressBar pgPicture = view.findViewById(R.id.pgPicture);
                                    pgPicture.setVisibility(View.VISIBLE);
                                    VolleySingleton.getInstance(getContext()).uploadAdventurePicture(idAdventure, mFileUri, "adventure-" + idUser + "-" + idAdventure + ".jpg",
                                            new Consumer<String>() {
                                                @Override
                                                public void accept(String filePath) {
                                                    if (filePath == null) {
                                                    } else {
                                                        listener.onCreateTreasure(adventure);
                                                    }
                                                }
                                            }
                                    );
                                    return;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    } else {
                        VolleySingleton.getInstance(getContext()).updateAdventure(idAdventure, newAdventure, new Consumer<Adventure>() {
                            @Override
                            public void accept(Adventure adventure) {
                                listener.onCreateTreasure(adventure);
                            }
                        });
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(getString(R.string.warningError));
                    builder.setMessage(getString(R.string.messageErrorEmptyFile));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        return view;
    }

    public void onPictureLoaded(Uri fileUri) {
        mFileUri = fileUri;

        ImageView ivRecupPic = view.findViewById(R.id.ivPic);
        ivRecupPic.setImageURI(mFileUri);
    }

    public interface CreateAdventureListener {

        void onTakeAdventurePicture();

        void onCreateTreasure(Adventure adventure);
    }
}

