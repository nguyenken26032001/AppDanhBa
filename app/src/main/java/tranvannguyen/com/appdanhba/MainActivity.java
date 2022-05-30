package tranvannguyen.com.appdanhba;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private AHBottomNavigation ahBottomNavigation;
    RecyclerView recyclerView;
   public static Database database;
    EditText txtSearch;
    ImageView searchSpeak;
    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(this);
        txtSearch = (EditText) findViewById(R.id.search);
        searchSpeak = (ImageView) findViewById(R.id.Searchspeak);
        addEvent();
        //database.QueryData("Delete from users where id=10");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        showRecyclerView();
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
        ahBottomNavigation.setCurrentItem(2);
        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if(position==0){
                    Intent intent = new Intent(MainActivity.this, ActivityYeuThich.class);
                    startActivity(intent);
                } if(position==3){
                    Intent intent = new Intent(MainActivity.this, ActivitybanPhim.class);
                    startActivity(intent);
                } if(position==1){
                    Intent intent = new Intent(MainActivity.this, ActivityRecent.class);
                    startActivity(intent);
                }
                return false;
            }
        });
        String strCreateTable="create table if not exists users(id  integer primary key autoincrement,firstName varchar(50),lastName varchar(50),phoneNumber varchar(10),hinhAnh BLOB)";//question....
        database.QueryData(strCreateTable);
    }

    private void addEvent() {
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<user>listUsers;
                listUsers = new ArrayList<>();
                 String text = txtSearch.getText().toString();

                if (text.trim().length() > 0) {
            try {
                ahBottomNavigation.setVisibility(View.INVISIBLE);
                // database = new Database(this);
                Cursor cursor = database.getData("SELECT id,firstName,lastName,phoneNumber FROM  users where lastName like '%"+text+"%'");
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String ho = cursor.getString(1);
                        String ten = cursor.getString(2);
                        String phone = cursor.getString(3);
                        String userName = ho +"\t"+ ten;
                        listUsers.add(new user(id,ho,ten,phone));
                    }
                    adapter adapter = new adapter(listUsers, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "data rong", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e)
                    {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    ahBottomNavigation.setVisibility(View.VISIBLE);
                    showRecyclerView();
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //function search by voice
        searchSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Xin mời bạn nói !");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (Exception exception) {
            Toast.makeText(MainActivity.this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK &&  data!=null) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String values = result.get(0);
                    String dataTemplate = "gọi";
                    txtSearch.setText(result.get(0));
                    if (values.contains(dataTemplate)) {
                        Toast.makeText(MainActivity.this, "sẽ gọi cho ???", Toast.LENGTH_SHORT).show();
                        String[] item = values.split("gọi");
                        String SearchResult=item[1];
                        callBySpeech(SearchResult);
                    }
                    else{
                        search(values);
                    }
                }
                break;
            }

        }
    }


    public void showRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.Recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<user> listUser;
        listUser = new ArrayList<>();
            try {
               // database = new Database(this);
                Cursor cursor = database.getData("SELECT id,firstName,lastName,phoneNumber FROM  users");
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                       int id = cursor.getInt(0);
                        String ho = cursor.getString(1);
                       String ten = cursor.getString(2);
                       String phone = cursor.getString(3);
                        String userName = ho +"\t"+ ten;
                        listUser.add(new user(id,ho,ten,phone));
                    }
                    Collections.sort(listUser, new Comparator<user>() {
                        @Override
                        public int compare(user o1, user o2) {
                            return o2.getFirstName().compareTo(o1.getFirstName());
                        }
                    });
                    adapter adapter = new adapter(listUser, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                   recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                }
                else
                {
                    Toast.makeText(MainActivity.this, "data rong", Toast.LENGTH_SHORT).show();
                }
           }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        private  void search(String values){
            ArrayList<user>listUsers;
            listUsers = new ArrayList<>();

            if (values.trim().length() > 0) {
                try {
                    ahBottomNavigation.setVisibility(View.INVISIBLE);
                    // database = new Database(this);
                    Cursor cursor = database.getData("SELECT id,firstName,lastName,phoneNumber FROM  users where lastName like '%"+values+"%'");
                    if (cursor != null && cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            int id = cursor.getInt(0);
                            String ho = cursor.getString(1);
                            String ten = cursor.getString(2);
                            String phone = cursor.getString(3);
                            String userName = ho +"\t"+ ten;
                            listUsers.add(new user(id,ho,ten,phone));
                        }
                        adapter adapter = new adapter(listUsers, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "data rong", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                ahBottomNavigation.setVisibility(View.VISIBLE);
                showRecyclerView();
            }
        }
        private  void callBySpeech(String values){
            Cursor cursor= MainActivity.database.getData("select * from user where firstName like '%"+values+"%' or lastName like '%"+values+"%'");
            if(cursor.getCount()>0){
                String phone=cursor.getString(3);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
            else
            {
                Toast.makeText(MainActivity.this, "Khong tim thay ket qua phu hop", Toast.LENGTH_SHORT).show();
            }

        }


    }
