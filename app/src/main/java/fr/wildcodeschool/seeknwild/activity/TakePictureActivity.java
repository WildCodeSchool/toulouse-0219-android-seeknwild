package fr.wildcodeschool.seeknwild.activity;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.model.Picture;
import fr.wildcodeschool.seeknwild.model.Treasure;
import fr.wildcodeschool.seeknwild.model.User;
import fr.wildcodeschool.seeknwild.model.UserAdventure;

public class TakePictureActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1234;
    private Treasure treasure;
    private UserAdventure userAdventure;
    private Long idUserAdventure;
    private Long idUser;
    private User user;
    private Uri mFileUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic_moment);

        UserSingleton userSingleton = UserSingleton.getInstance();
        user = userSingleton.getUser();
        idUser = user.getIdUser();

        UserAdventureSingleton userAdventureSingleton = UserAdventureSingleton.getInstance();
        userAdventure = userAdventureSingleton.getUserAdventure();
        idUserAdventure = userAdventureSingleton.getUserAdventureId();
        List<Treasure> treasures = userAdventure.getAdventure().getTreasures();
        treasure = treasures.get(userAdventure.getCurrentTreasure());

        ImageView ivLogo = findViewById(R.id.ivTreasureToModify);
        String url = "https://i.goopics.net/kwb0o.jpg";
        Glide.with(this).load(url).into(ivLogo);
        actionFloattingButton();
        //TODO: Associer la photo à un User + renvoyer l'uri dans la base etc.
        //TODO: update user pour lui ajouter les photos !
        Picture pictureCurrent = new Picture();

        try {
            VolleySingleton.getInstance(getApplicationContext()).uploadUserCurrentPicture(idUser, mFileUri, "", new Consumer<String>() {
                @Override
                public void accept(String filePath) {
                    if (filePath == null) {
                        Toast.makeText(TakePictureActivity.this, "prendre une photo", Toast.LENGTH_SHORT).show();
                    } else {
                        VolleySingleton.getInstance(getApplicationContext()).createPicture(idUser, new Consumer<Picture>() {
                            @Override
                            public void accept(Picture picture) {

                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button btNext = findViewById(R.id.btNext);

        if (userAdventure.getNbTreasure() == 4) {
            btNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                    UserSingleton.getInstance().setUser(user);
                    startActivity(new Intent(TakePictureActivity.this, RateActivity.class));
                }
            });

        } else {
            if (userAdventure.getNbTreasure() >= treasures.size()) {
                btNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                        UserSingleton.getInstance().setUser(user);
                        startActivity(new Intent(TakePictureActivity.this, SearchTreasureActivity.class));
                    }
                });
            } else {
                btNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserAdventureSingleton.getInstance().setUserAdventure(userAdventure);
                        UserSingleton.getInstance().setUser(user);
                        startActivity(new Intent(TakePictureActivity.this, SearchTreasureActivity.class));
                    }
                });
            }
        }
    }

    private void actionFloattingButton() {
        FloatingActionButton floatBtTakePicTreasure = findViewById(R.id.fbTakePic);
        floatBtTakePicTreasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imgFileName = "JPEG_" + timeStamp + "_";
        java.io.File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = java.io.File.createTempFile(imgFileName, ".jpg", storageDir);
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
        ImageView ivRecupPic = findViewById(R.id.ivTreasureToModify);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ivRecupPic.setImageURI(mFileUri);
        }
    }
}
