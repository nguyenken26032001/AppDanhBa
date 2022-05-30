package tranvannguyen.com.appdanhba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityRecent extends AppCompatActivity {
    private AHBottomNavigation ahBottomNavigation;
    RecyclerView recyclerView;
    ArrayList<callsInformation> list_Calls;
    Button btnCallAll, btnMissedCall,btndeleteAll;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
       showNavigation();
        btnCallAll = (Button) findViewById(R.id.callAll);
        btnMissedCall= (Button) findViewById(R.id.missedCall);
        btndeleteAll= (Button) findViewById(R.id.deleteAll);
        recyclerView= (RecyclerView) findViewById(R.id.Recyclerview_Calls);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        list_Calls = new ArrayList<>();
          showRecyclerview();
//          btnMissedCall.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  showMissedCall();
////                  showRecyclerview();
//              }
//          });
//          btndeleteAll.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                //  getApplicationContext().getContentResolver().delete(CallLog.Calls.CONTENT_URI,null,null);
//              }
//          });
    }
    private void showRecyclerview() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CALL_LOG},1);
        }
        Cursor cursor=getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null,null,null,CallLog.Calls.DATE + " ASC");
        int name=cursor.getColumnIndex(CallLog.Calls.CALL_SCREENING_APP_NAME);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        String hoten = name != -1 ? cursor.getString(name) : "";
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Date date1 = new Date(Long.valueOf( cursor.getString(date)));
            SimpleDateFormat dateFormat = new SimpleDateFormat("E,dd/MM/yyyy");
            String valueDate=dateFormat.format(date1);
            list_Calls.add(new callsInformation(hoten, cursor.getString(number), valueDate, cursor.getString(duration)));
        }
        callsAdapter adapter= new callsAdapter(list_Calls,this);
        recyclerView.setAdapter(adapter);
    }
    private void showMissedCall() {
        String PATH = "content://call_log/calls";
        String[] projection = new String[] {
                CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.TYPE };
        String sortOrder = CallLog.Calls.DATE + " DESC";
        Cursor cursor = getApplicationContext().getContentResolver().query(
                Uri.parse(PATH),
                projection,
               null,
                new String[] { String.valueOf(CallLog.Calls.MISSED_TYPE), "0"
                },sortOrder);
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        while (cursor.moveToNext()) {
            Date date1 = new Date(Long.valueOf( cursor.getString(date)));
            SimpleDateFormat dateFormat = new SimpleDateFormat("E,dd/MM/yyyy");
            String valueDate=dateFormat.format(date1);
            list_Calls.add(new callsInformation("hello", cursor.getString(number), valueDate, cursor.getString(duration)));
        }
        callsAdapter adapter= new callsAdapter(list_Calls,this);
        recyclerView.setAdapter(adapter);
    }
    private void showNavigation() {
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
        ahBottomNavigation.setCurrentItem(1);
        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==0){
                    Intent intent = new Intent(ActivityRecent.this, ActivityYeuThich.class);
                    startActivity(intent);
                } if(position==2){
                    Intent intent = new Intent(ActivityRecent.this, MainActivity.class);
                    startActivity(intent);
                }
                if(position==3){
                    Intent intent = new Intent(ActivityRecent.this, ActivitybanPhim.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}