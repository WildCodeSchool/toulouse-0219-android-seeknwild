package fr.wildcodeschool.seeknwild.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v4.util.Consumer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Adventure;

public class CreateAdventureActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1234;
    // chemin de la photo dans le téléphone
    private Uri mFileUri = null;
    private Long idAdventure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_adventure);
        Intent intent = getIntent();
        idAdventure = intent.getLongExtra("idAdventure", -1);
        ImageView ivLogo = findViewById(R.id.ivAdventure);
        String url = "https://i.goopics.net/5DbkX.jpg";
        Glide.with(this).load(url).into(ivLogo);
        actionFloattingButton();
        Button btCreateTresor = findViewById(R.id.btTreasure);
        btCreateTresor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etNameAdventure = findViewById(R.id.etNameAdventure);
                final EditText etDescriptionAdventure = findViewById(R.id.etDescriptionAdventure);
                final Adventure newAdventure = new Adventure();
                newAdventure.setTitle(etNameAdventure.getText().toString());
                newAdventure.setDescription(etDescriptionAdventure.getText().toString());
                if (!etNameAdventure.getText().toString().isEmpty()
                        && !etDescriptionAdventure.getText().toString().isEmpty()) {
                    if (idAdventure == -1) {
                        VolleySingleton.getInstance(getApplicationContext()).createAdventure(newAdventure, new Consumer<Adventure>() {
                            @Override
                            public void accept(Adventure adventure) {
                                idAdventure = adventure.getIdAdventure();
                                Intent intent = new Intent(CreateAdventureActivity.this, TreasureAdventureMapsActivity.class);
                                intent.putExtra("idAdventure", idAdventure);
                                startActivity(intent);
                            }
                        });
                    } else {
                        //TODO mettre à jour l'aventure et aller à la fin de la liste des trésors
                        VolleySingleton.getInstance(getApplicationContext()).updateAdventure(idAdventure, newAdventure, new VolleySingleton.ResponseListener<Adventure>() {
                            @Override
                            public void finished(Adventure response) {
                                etNameAdventure.setText(etNameAdventure.getText().toString());
                                etDescriptionAdventure.setText(etDescriptionAdventure.getText().toString());
                                newAdventure.setTitle(etNameAdventure.getText().toString());
                                newAdventure.setDescription(etDescriptionAdventure.getText().toString());
                            }
                        });
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAdventureActivity.this);
                    builder.setTitle(getString(R.string.warningError));
                    builder.setMessage(getString(R.string.messageErrorEmptyFile));
                    builder.setPositiveButton(getString(R.string.ok), null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        if (idAdventure != -1) {
            VolleySingleton.getInstance(getApplicationContext()).getAdventureById(idAdventure, new Consumer<Adventure>() {
                @Override
                public void accept(Adventure adventure) {
                    EditText etTitre = findViewById(R.id.etNameAdventure);
                    EditText etDescription = findViewById(R.id.etDescriptionAdventure);
                    etTitre.setText(adventure.getTitle());
                    etDescription.setText(adventure.getDescription());

                }
            });
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView ivRecupPic = findViewById(R.id.ivPic);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ivRecupPic.setImageURI(mFileUri);
        }
    }

    private void actionFloattingButton() {
        FloatingActionButton floatBtTakePicTreasure = findViewById(R.id.fbTakePicAdventure);
        floatBtTakePicTreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }
}