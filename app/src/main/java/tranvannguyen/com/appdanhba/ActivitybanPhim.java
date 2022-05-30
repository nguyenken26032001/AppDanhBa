package tranvannguyen.com.appdanhba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

public class ActivitybanPhim extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 555;
    private AHBottomNavigation ahBottomNavigation;
    Button cong,tru,nhan,chia,mot,hai,ba,bon,nam,sau,bay,tam,chin,khong,xoamot,sao,thang;
    Button btnCall;
    TextView addNumber,text;
//    TextView dataCuocGoi;
    String input,answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityban_phim);
        addControls();
        addEvent();
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.favorite, R.color.tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.clock, R.color.tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.danhba, R.color.tab_3);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.keyboard, R.color.tab_4);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.addItem(item4);
        ahBottomNavigation.setColored(true);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setCurrentItem(3);
        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==0){
                    Intent intent = new Intent(ActivitybanPhim.this, ActivityYeuThich.class);
                    startActivity(intent);
                } if(position==1){
                    Intent intent = new Intent(ActivitybanPhim.this, ActivityRecent.class);
                    startActivity(intent);
                }
                if(position==2){
                    Intent intent = new Intent(ActivitybanPhim.this, MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }

    public  void buttonClick(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();
        switch (data){
            case "delete":
                if (input.length() >0){
                    String newText = input.substring(0,input.length()-1);
                    input= newText;
                }
                break;
            default:
                if(input==null){
                    input = "";
                }
                input += data;
        }
        text.setText(input);
    }
    private  void startAnim(View view){
        Animation animation = AnimationUtils.loadAnimation(ActivitybanPhim.this, R.anim.animbutton);
        view.startAnimation(animation);
    }
    public void addControls() {
        text = (TextView) findViewById(R.id.text_number);
        addNumber = (TextView) findViewById(R.id.addNumber);
        mot = (Button) findViewById(R.id.mot);
        hai = (Button) findViewById(R.id.hai);
        ba = (Button) findViewById(R.id.ba);
        bon = (Button) findViewById(R.id.bon);
        nam = (Button) findViewById(R.id.nam);
        sau = (Button) findViewById(R.id.sau);
        bay = (Button) findViewById(R.id.bay);
        tam = (Button) findViewById(R.id.tam);
        chin = (Button) findViewById(R.id.chin);
        khong = (Button) findViewById(R.id.khong);
        xoamot = (Button) findViewById(R.id.delete);
        btnCall = (Button) findViewById(R.id.call);
        sao = (Button) findViewById(R.id.sao);
        thang = (Button) findViewById(R.id.thang);
        text.setGravity(Gravity.CENTER_HORIZONTAL);
        xoamot.setVisibility(View.INVISIBLE);
        addNumber.setVisibility(View.INVISIBLE);
//        dataCuocGoi = (TextView) findViewById(R.id.datacuocgoi);
    }
    private void addEvent() {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String values = text.getText().toString();
                if(values.trim().length()>0){
                    addNumber.setVisibility(View.VISIBLE);
                    xoamot.setVisibility(View.VISIBLE);
                    Cursor cursor=MainActivity.database.getData("select  * from users where phoneNumber='"+values+"'");
                    if(cursor.getCount()>0){
                         cursor.moveToFirst();
                           String Ho=cursor.getString(1);
                           String ten=cursor.getString(2);
                           String fullName=Ho+"\t"+ten;
                           addNumber.setText(fullName);
                          addNumber.setEnabled(false);
                    }
                    else {
                        addNumber.setEnabled(true);
                        addNumber.setText("Thêm số ");
                    }
                }else
                {
                    xoamot.setVisibility(View.INVISIBLE);
                    addNumber.setVisibility(View.INVISIBLE);
                    addNumber.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(mot);
                startAnim(mot);
            }
        });
        hai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(hai);
                startAnim(hai);
            }
        });
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(ba);
                startAnim(ba);
            }
        });  bon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(bon);
                startAnim(bon);
            }
        });  nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(nam);
                startAnim(nam);
            }
        });  sau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(sau);
                startAnim(sau);
            }
        });  bay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(bay);
                startAnim(bay);
            }
        });  tam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(tam);
                startAnim(tam);
            }
        });  chin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(chin);
                startAnim(chin);
            }
        });  khong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(khong);
                startAnim(khong);
            }
        });
        sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(sao);
                startAnim(sao);
            }
        });
        thang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(thang);
                startAnim(thang);
            }
        });
        xoamot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    buttonClick(xoamot);
                    startAnim(xoamot);
            }
        });
        addNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDialogAddPhoneNumber();
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndCall();
            }
        });
    }
    private void askPermissionAndCall() {

        // With Android Level >= 23, you have to ask the user
        // for permission to Call.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) { // 23
            // Check if we have Call permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSION_REQUEST_CODE_CALL_PHONE
                );
                return;
            }
        }
      this.callNow();
    }
    private void callNow() {
        String data = text.getText().toString().trim();
        if (data.equals("")) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + data));
        startActivity(intent);
    }
    private void showDialogAddPhoneNumber() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addphone_number);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.98);
        int height = (int)(getResources().getDisplayMetrics().heightPixels*0.30);
        dialog.getWindow().setLayout(width, height);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.show();
        Button btnadd = (Button) dialog.findViewById(R.id.adduser);
        Button btncancle = (Button) dialog.findViewById(R.id.cancle);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str;
                 str = text.getText().toString();
                Intent intent = new Intent(ActivitybanPhim.this, ActivityAddUser.class);
                intent.putExtra("phoneNumber", str);
                intent.putExtra("class","A");
                startActivity(intent);
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
    public void getCallLogs() {
        int flag=1;
        StringBuilder callLogs = new StringBuilder();

        ArrayList<String> calllogsBuffer = new ArrayList<String>();
        calllogsBuffer.clear();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI,
                null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }
            calllogsBuffer.add("\nPhone Number: " + phNumber + " \nCall Type: "
                    + dir + " \nCall Date: " + callDayTime
                    + " \nCall duration in sec : " + callDuration + "\n");
        }
        managedCursor.close();
    }

}
