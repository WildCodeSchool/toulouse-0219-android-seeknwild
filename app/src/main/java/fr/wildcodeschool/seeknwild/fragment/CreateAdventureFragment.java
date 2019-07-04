package fr.wildcodeschool.seeknwild.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.util.Consumer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.activity.CreateAdventureActivity;
import fr.wildcodeschool.seeknwild.activity.SearchTreasureActivity;
import fr.wildcodeschool.seeknwild.activity.TreasureAdventureMapsActivity;
import fr.wildcodeschool.seeknwild.activity.UserAdventureSingleton;
import fr.wildcodeschool.seeknwild.activity.UserSingleton;
import fr.wildcodeschool.seeknwild.activity.VolleySingleton;
import fr.wildcodeschool.seeknwild.model.Adventure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class CreateAdventureFragment extends Fragment {

    private AdventureCreateListener listener;
    public static final int REQUEST_IMAGE_CAPTURE = 1234;
    private Uri mFileUri = null;
    private Long idAdventure;

    public CreateAdventureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AdventureCreateListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        UserSingleton userSingleton = UserSingleton.getInstance();
        User user = userSingleton.getUser();
        final Long idUser = user.getIdUser();

        /*Intent intent = getIntent();
        idAdventure = intent.getLongExtra("idAdventure", -1);*/
        ImageView ivLogo = view.findViewById(R.id.ivAdventure);
        //TODO remplacer String url
        String url = "https://i.goopics.net/5DbkX.jpg";
        Glide.with(this).load(url).into(ivLogo);
        actionFloattingButton();
        Button btCreateTresor = view.findViewById(R.id.btTreasure);
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
                                    //TODO Afficher une fenêtre de chargement
                                    ProgressBar pgPicture = view.findViewById(R.id.pgPicture);
                                    pgPicture.setVisibility(View.VISIBLE);
                                    VolleySingleton.getInstance(getContext()).uploadAdventurePicture(idAdventure, mFileUri, "adventure-" + idUser + "-" + idAdventure + ".jpg",
                                            new Consumer<String>() {
                                                @Override
                                                public void accept(String filePath) {
                                                    //TODO Fermer la fenêtre de chargement
                                                    if (filePath == null) {
                                                        //TODO Afficher un message d'erreur
                                                    } else {
                                                        /*Intent intent = new Intent(CreateAdventureActivity.this, TreasureAdventureMapsActivity.class);
                                                        intent.putExtra("idAdventure", idAdventure);
                                                        startActivity(intent);*/
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
                        VolleySingleton.getInstance(getContext()).updateAdventure(idAdventure, newAdventure, new VolleySingleton.ResponseListener<Adventure>() {
                            @Override
                            public void finished(Adventure response) {
                                etNameAdventure.setText(etNameAdventure.getText().toString());
                                etDescriptionAdventure.setText(etDescriptionAdventure.getText().toString());
                                newAdventure.setTitle(etNameAdventure.getText().toString());
                                newAdventure.setDescription(etDescriptionAdventure.getText().toString());
                                /*Intent intent = new Intent(CreateAdventureActivity.this, TreasureAdventureMapsActivity.class);
                                intent.putExtra("idAdventure", idAdventure);
                                startActivity(intent);*/
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

        if (idAdventure != -1) {
            VolleySingleton.getInstance(getContext()).getAdventureById(idAdventure, new Consumer<Adventure>() {
                @Override
                public void accept(Adventure adventure) {
                    EditText etTitre = view.findViewById(R.id.etNameAdventure);
                    EditText etDescription = view.findViewById(R.id.etDescriptionAdventure);
                    etTitre.setText(adventure.getTitle());
                    etDescription.setText(adventure.getDescription());

                }
            });
        }
        return view;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imgFileName, ".jpg", storageDir);
        return image;
    }

    private void dispatchTakePictureIntent() {
        // ouvrir l'application de prise de photo
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // lors de la validation de la photo
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // créer le fichier contenant la photo
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                // TODO : gérer l'erreur
            }
            if (photoFile != null) {
                // récupèrer le chemin de la photo
                mFileUri = FileProvider.getUriForFile(this,
                        "fr.wildcodeschool.seeknwild.fileprovider",
                        photoFile);
                // déclenche l'appel de onActivityResult
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }

    public interface AdventureCreateListener {

        void onAdventureSelected(Adventure adventure);
    }
}
