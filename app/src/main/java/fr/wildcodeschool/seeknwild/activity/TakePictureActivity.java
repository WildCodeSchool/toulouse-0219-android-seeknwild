package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.wildcodeschool.seeknwild.R;

public class TakePictureActivity extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic_moment);

        ImageView ivLogo = findViewById(R.id.ivTreasureToModify);
        String url = "https://i.goopics.net/kwb0o.jpg";
        Glide.with(this).load(url).into(ivLogo);
        actionFloattingButton();

        Button btNext = findViewById(R.id.btNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFileUri != null) {
                    /*try {
                       VolleySingleton.getInstance(TakePictureActivity.this).uploadPicture(mFileUri, "df.jpg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
                startActivity(new Intent(TakePictureActivity.this, NoteActivity.class));
            }
        });
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
    // chemin de la photo dans le téléphone
    private Uri mFileUri = null;

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
