package tranvannguyen.com.appdanhba;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.EntityIterator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ActivityAddUser extends AppCompatActivity {
    EditText  txtTen,txtSdt,txtEmail,txtStreet,txtCity;
    TextView txtHuy, txtXong,delete_contact;
    CircleImageView chooseImage;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_user);
        addControls();
        Intent intent = getIntent();
        String flag = intent.getStringExtra("class");
        if(flag.equals("A")){
            String phoneNumber = getIntent().getStringExtra("phoneNumber");
            txtSdt.setText(phoneNumber);
            delete_contact.setVisibility(View.INVISIBLE);
        }else
            if(flag.equals("C"))
            {
               position = getIntent().getIntExtra("position",0);
                Cursor cursor = MainActivity.database.getData("SELECT * FROM  users where id='"+position+"'");
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int id = cursor.getInt(0);
                    String ten = cursor.getString(1);
                    String phone = cursor.getString(2);
                    String email = cursor.getString(3);
                    String street = cursor.getString(4);
                    String city = cursor.getString(5);
                    byte[] dataImage = cursor.getBlob(6);
                    cursor.close();
                    txtTen.setText(ten);
                    txtSdt.setText(phone);
                    txtEmail.setText(email);
                    txtStreet.setText(street);
                    txtCity.setText(city);
                    if (dataImage != null) {
                        Bitmap bitmapp = BitmapFactory.decodeByteArray(dataImage, 0,dataImage.length);
                        chooseImage.setImageBitmap(bitmapp);
                    }
                }
            }
            else
        {
            delete_contact.setVisibility(View.VISIBLE);
            user user = (user) getIntent().getSerializableExtra("objectt");
                position = user.getId();
                Cursor cursor = MainActivity.database.getData("SELECT * FROM  users where id='"+position+"'");
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    int id = cursor.getInt(0);
                    String ten = cursor.getString(1);
                    String phone = cursor.getString(2);
                    String email = cursor.getString(3);
                    String street = cursor.getString(4);
                    String city = cursor.getString(5);
                    byte[] dataImage = cursor.getBlob(6);
                    cursor.close();
                    txtTen.setText(ten);
                    txtSdt.setText(phone);
                    txtEmail.setText(email);
                    txtStreet.setText(street);
                    txtCity.setText(city);
                    if (dataImage != null) {
                        Bitmap bitmapp = BitmapFactory.decodeByteArray(dataImage, 0,dataImage.length);
                        chooseImage.setImageBitmap(bitmapp);
                    }
                }
        }
        addEvent();
    }
    private void addEvent() {
        txtHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] hinhAnh = new byte[0];
                //chuyển data imageview-> byte[]
                BitmapDrawable bitmapDrawable ;
                Bitmap bitmap;
                ByteArrayOutputStream byteArray;
                String ten = txtTen.getText().toString();
                String phonenumber = txtSdt.getText().toString();
                String email = txtEmail.getText().toString();
                String street = txtStreet.getText().toString();
                String city = txtCity.getText().toString();
                if (ten.trim().length()==0) {
                  AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityAddUser.this);
                    dialog.setMessage("Vui lòng điền đầy đủ thông tin !! please");
                    dialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                }
                else
                {
                    if (position != 0) {
                        if (hasImage(chooseImage)==true) {
                             bitmapDrawable  = (BitmapDrawable) chooseImage.getDrawable();
                           bitmap = bitmapDrawable.getBitmap();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] bytesHinhAnh = byteArrayOutputStream.toByteArray();
                            MainActivity.database.UpdateUsers(ten,phonenumber,email,street,city,bytesHinhAnh,position);
                            translateActivity();
                            Toast.makeText(ActivityAddUser.this, "co hinh", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            MainActivity.database.QueryData("update users set Name='"+ten+"',PhoneNumber='"+phonenumber+"' where id='"+position+"'");
                            translateActivity();
                            Toast.makeText(ActivityAddUser.this, " k co hinh", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                      {
                        if (chooseImage.getDrawable() == null) {
                            MainActivity.database.insertForImageWithImagenull(ten,phonenumber,email,street,city);
                            translateActivity();
                             }
                          else
                        {
                            bitmapDrawable   = (BitmapDrawable) chooseImage.getDrawable();
                            bitmap= bitmapDrawable.getBitmap();
                            byteArray = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                            hinhAnh = byteArray.toByteArray();
                            MainActivity.database.insertForImage(ten,phonenumber,email,street,city,hinhAnh);
                          translateActivity();
                        }
                    }
                }
            }
        });
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesPermission();
            }
        });
        
        
        delete_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityAddUser.this);
                alert.setMessage("Bạn có chắc chắn muốn xóa liên hệ này khỏi danh bạ không ?");
                alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.database.QueryData("Delete from users where id='"+position+"'");
                        Toast.makeText(ActivityAddUser.this, "Delete  Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityAddUser.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                alert.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                });
                alert.show();
            }
        });
    }
    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
        }

        return hasImage;
    }
    public void addControls() {
        txtTen = (EditText) findViewById(R.id.name);
        txtSdt = (EditText) findViewById(R.id.phone);
        txtEmail = (EditText) findViewById(R.id.email);
        txtStreet = (EditText) findViewById(R.id.street);
        txtCity = (EditText) findViewById(R.id.city);
        chooseImage = (CircleImageView) findViewById(R.id.chooseImages);
        txtHuy = (TextView) findViewById(R.id.huy);
        txtXong = (TextView) findViewById(R.id.xong);
        delete_contact = (TextView) findViewById(R.id.delete_contact);
    }
    public void requesPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImageformStorage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ActivityAddUser.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }
    private void openImageformStorage() {
        TedBottomPicker.with(ActivityAddUser.this)
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
    private void translateActivity() {
        Toast.makeText(ActivityAddUser.this, "Add User successfully", Toast.LENGTH_SHORT).show();
        Intent intentTranlate = new Intent(ActivityAddUser.this, MainActivity.class);
        startActivity(intentTranlate);

    }
}