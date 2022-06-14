package tranvannguyen.com.appdanhba;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.TransactionTooLargeException;
import android.provider.MediaStore;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.snackbar.Snackbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import gun0912.tedbottompicker.TedRxBottomPicker;
public class danhBaDetail<printStackTrace> extends AppCompatActivity {
    CircleImageView imageView;
    TextView txtuserName,phone,email,location;
    TextView txtback,txtUpdate;
    Button btnSendMessage, btncall,btnEmail,btnLocation;
    String sdt;
    String email_acc;
    ArrayList<user> inforUser;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_detail);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (CircleImageView) findViewById(R.id.profile_image);
        txtuserName = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phoneNumber);
        txtback = (TextView) findViewById(R.id.backPage);
        txtUpdate = (TextView) findViewById(R.id.update);
        btnSendMessage = (Button) findViewById(R.id.bt_sms);
        btncall = (Button) findViewById(R.id.bt_call);
        email = (TextView) findViewById(R.id.emailAddress);
        location = (TextView) findViewById(R.id.location);
        btnEmail = (Button) findViewById(R.id.bt_email);
        btnLocation = (Button) findViewById(R.id.bt_location);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
            user user = (user) bundle.get("object");
            id = user.getId();
        Toast.makeText(danhBaDetail.this, ""+id, Toast.LENGTH_SHORT).show();
        String ten = user.getName();
        sdt = user.getPhoneNumber();
        email_acc = user.getEmail();
        String street = user.getStreet()+",";
        String city = user.getCity();
        String Address = street + city;
        txtuserName.setText(ten);
        phone.setText(sdt);
        email.setText(email_acc);
        location.setText(Address);
        Character FirstCharacters = ten.charAt(0);
        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(FirstCharacters), Color.RED);
        imageView.setImageDrawable(drawable);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNow();
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendMessage();
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendMessage();
            }
        });
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNow();
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmailOnClick();
            }
        });
        txtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inforUser = new ArrayList<>();
                inforUser.add(new user(id));
                Intent intent = new Intent(danhBaDetail.this, ActivityAddUser.class);
                intent.putExtra("objectt", user);
                intent.putExtra("class","B");
                startActivity(intent);
           }
        });
    }
    private void sendMessage() {
        Uri uri = Uri.parse("smsto:" + sdt);
        Intent intentsendMessage = new Intent(Intent.ACTION_SENDTO,uri);
        startActivity(intentsendMessage);
    }
    private void callNow() {
        Intent intentCall = new Intent(Intent.ACTION_CALL);
        intentCall.setData(Uri.parse("tel:" +sdt));
        startActivity(intentCall);
    }
    private void setEmailOnClick() {
        if (!email_acc.isEmpty()) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email_acc});
            emailIntent.setType("plain/text");
            startActivity(Intent.createChooser(emailIntent, " Send... "));

        } else {
            Snackbar.make(findViewById(R.id.sv_scroll), "Email not found",
                    Snackbar.LENGTH_LONG)
                    .setAction("Add", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                sendDataToUpdate();
                            } catch (Exception e) {
                                Toast.makeText(danhBaDetail.this,
                                        "Too large data to handle", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .show();
        }
    }

    private void sendDataToUpdate() {
        Intent intent = new Intent(danhBaDetail.this, ActivityAddUser.class);
        intent.putExtra("position", id);
        intent.putExtra("class","C");
        startActivity(intent);

    }
}