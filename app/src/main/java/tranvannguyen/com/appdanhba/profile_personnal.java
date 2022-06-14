package tranvannguyen.com.appdanhba;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class profile_personnal extends AppCompatActivity {
    EditText txtName,txtPhone,txtEmail,txtStreet,txtCity;
    TextView update_profile,back;
    CircleImageView chooseImage;
    public  static  ActivityAddUser activityAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_personal);
        addControls();
        addEvent();
    }

    private void addEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesPermission();
            }
        });
        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String Phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                String street = txtStreet.getText().toString();
                String city = txtCity.getText().toString();
                if (name.trim().length() > 0 && Phone.trim().length() > 0) {
                    if (chooseImage.getDrawable() == null) {
                        MainActivity.database.insertForImageWithImagenullProfile(name,Phone,email,street,city);
                    }
                    else
                    {
                       BitmapDrawable bitmapDrawable   = (BitmapDrawable) chooseImage.getDrawable();
                     Bitmap   bitmap= bitmapDrawable.getBitmap();
                        ByteArrayOutputStream  byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                         byte[]   hinhAnh = byteArray.toByteArray();
                        MainActivity.database.insertForImageProfile(name,Phone,email,street,city,hinhAnh);
                    }
                    translateActivity();

                } else
                {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(profile_personnal.this);
                        dialog.setMessage("Vui lòng điền đầy đủ thông tin !! please");
                        dialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();
                }

            }
        });
    }

    private void translateActivity() {
        Intent intent = new Intent(profile_personnal.this, MainActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        txtName = (EditText) findViewById(R.id.name);
        txtPhone = (EditText) findViewById(R.id.phone);
        txtEmail = (EditText) findViewById(R.id.email);
        txtStreet = (EditText) findViewById(R.id.street);
        txtCity = (EditText) findViewById(R.id.city);
        back = (TextView) findViewById(R.id.back);
        update_profile = (TextView) findViewById(R.id.update_profile);
        chooseImage= (CircleImageView) findViewById(R.id.chooseImages);
    }
    public void requesPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImageformStorage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(profile_personnal.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
    private void openImageformStorage() {
        TedBottomPicker.with(profile_personnal.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected image uri
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            chooseImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}