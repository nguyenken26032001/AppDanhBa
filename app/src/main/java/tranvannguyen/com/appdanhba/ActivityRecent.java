package tranvannguyen.com.appdanhba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    }
    private void showRecyclerview() {
        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
//            }
//            Cursor cursor = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
//                    null, null, null, CallLog.Calls.DATE + " ASC");
            int permissionCheck = ContextCompat.checkSelfPermission(ActivityRecent.this,
                    Manifest.permission.READ_CALL_LOG);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Cursor cursor = ActivityRecent.this.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                        null,
                        null, CallLog.Calls.DATE + " DESC");
                if (cursor != null && cursor.getCount() > 0) {

                    while (cursor.moveToNext()){
                        callsInformation callModel = new callsInformation();
                        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                         String phone = cursor.getString(number);
                         callModel.setLogPhone(phone);
                         long duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
                         int minutes = (int) (duration / (60));
                         int seconds =   (int) (duration % 60);
                         String time = String.format(new Locale(String.valueOf(duration)), "%02d:%02d",
                                 minutes, seconds);
                         if (minutes == 0) {
                             callModel.setLogDuration(time + " sec");
                         } else {
                             callModel.setLogDuration(time + "min");
                         }
//                         Date date = new Date(cursor.getColumnIndex(CallLog.Calls.DATE));
//                         SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");
//                         String valueDate = dateFormat.format(date);
//                         String timeFormat = new SimpleDateFormat("hh:mm a",
//                                 new Locale("")).format(date).trim();
//                         callModel.setLogDate(valueDate);
//                         callModel.setLogTime(timeFormat);
                        int date1 = cursor.getColumnIndex(CallLog.Calls.DATE);
                        Date date = new Date(Long.valueOf( cursor.getString(date1)));
                         String  dateFormat = new SimpleDateFormat("MM/dd").format(date);
                        String timeFormat = new SimpleDateFormat("hh:mm aaa").format(date).trim();
                        callModel.setLogTime(timeFormat);
                          callModel.setLogDate(dateFormat);
                         String fullName = getContactName(ActivityRecent.this, phone);
                         if (fullName == null) {
                             callModel.setLogName(phone);
                         } else {
                             callModel.setLogName(fullName);
                         }
                         long contactID = getContactIDFromNumber(phone, ActivityRecent.this);

                         // Get Contact Photo
                         InputStream stream = getContactPhoto(contactID, ActivityRecent.this);
                         if (stream != null) {
                             callModel.setLogImage(BitmapFactory.decodeStream(stream));
                         }
                        list_Calls.add(callModel);
                    }
                    callsAdapter adapter = new callsAdapter(list_Calls, this);
                     recyclerView.setAdapter(adapter);
                }else
                {
                    Toast.makeText(ActivityRecent.this, "khong co du lieu ", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e){
            e.getMessage();
        }
    }
    private void showNavigation() {
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.clock, R.color.tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.danhba, R.color.tab_3);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.keyboard, R.color.tab_4);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.addItem(item4);
        ahBottomNavigation.setColored(true);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        ahBottomNavigation.setCurrentItem(0);
        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==1){
                    Intent intent = new Intent(ActivityRecent.this, MainActivity.class);
                    startActivity(intent);
                }
                if(position==2){
                    Intent intent = new Intent(ActivityRecent.this, ActivitybanPhim.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
    private long getContactIDFromNumber(String contactNumber, Context context) {
        long phoneContactID = 0;
        Cursor contactLookupCursor = context.getContentResolver()
                .query(Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                        Uri.encode(contactNumber)),
                        new String[]{ContactsContract.PhoneLookup._ID}, null, null, null);
        if (contactLookupCursor != null) {
            while (contactLookupCursor.moveToNext()) {
                phoneContactID = contactLookupCursor.getLong(contactLookupCursor
                        .getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID));
            }
            contactLookupCursor.close();
        }
        return phoneContactID;
    }
    private InputStream getContactPhoto(long contactId, Context context) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }
    private String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},
                null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                int name=cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                return cursor.getString(name);

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
            finally {
            cursor.close();
        }

        return null;
    }


}