package tranvannguyen.com.appdanhba;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.material.internal.TextDrawableHelper;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import gun0912.tedbottompicker.TedRxBottomPicker;


public class danhBaDetail<printStackTrace> extends AppCompatActivity {
    ImageView imageView;
    TextView txtuserName,phone;
    TextView txtback,txtUpdate;
    TextView sendMessage;
    Button btnSendMessage, btncall;
    String sdt;
    ArrayList<user> inforUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id;
        setContentView(R.layout.activity_danh_ba_detail);
        imageView = (ImageView) findViewById(R.id.image);
        txtuserName = (TextView) findViewById(R.id.userName);
        phone = (TextView) findViewById(R.id.phoneNumber);
        txtback = (TextView) findViewById(R.id.backPage);
        sendMessage = (TextView) findViewById(R.id.sendMessage);
        btnSendMessage = (Button) findViewById(R.id.message);
        btncall = (Button) findViewById(R.id.call);
        txtUpdate = (TextView) findViewById(R.id.update);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
            user user = (user) bundle.get("object");
            id = user.getId();
        Toast.makeText(danhBaDetail.this, ""+id, Toast.LENGTH_SHORT).show();
        String ho = user.getFirstName();
        String ten = user.getLastName();
        sdt = user.getPhoneNumber();
        txtuserName.setText(ho+"\t"+ten);
        phone.setText(sdt);
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
        sendMessage.setOnClickListener(new View.OnClickListener() {
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
}