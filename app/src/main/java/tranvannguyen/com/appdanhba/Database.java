package tranvannguyen.com.appdanhba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class Database extends SQLiteOpenHelper {
    private  static  final String Database_name = "qlDanhBa";
    private static final int nversion = 1;
    private   SQLiteDatabase databasee = null;

    //FOREIGN KEY (list_id) REFERENCES lists (id)
    public Database(@Nullable Context context) {
        super(context, Database_name, null, nversion);
    }

    //this funcition use insert,update,delete,create
    public  void QueryData(String str) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(str);
    }
    public void UpdateUsers(String ten,String sdt,String email,String street,String city, byte[] img,Integer position){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("Name",ten);
        contentValues.put("PhoneNumber",sdt);
        contentValues.put("Email",email);
        contentValues.put("Street",street);
        contentValues.put("City",city);
        contentValues.put("HinhAnh",img);
        String where = "id" + "=" + position;
       db.update("users",contentValues, where, null);
    }


    //this function use select data in database
    public Cursor getData(String str) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(str, null);
    }
    public  void insertForImage(String ten,String sdt,String email,String street,String city,byte[]hinhAnh) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Insert into users(id,Name,PhoneNumber,Email,Street,City,HinhAnh)  values(null,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.bindString(2, sdt);
        statement.bindString(3, email);
        statement.bindString(4, street);
        statement.bindString(5, city);
        statement.bindBlob(6, hinhAnh);
        statement.executeInsert();
    }    public  void insertForImageProfile(String ten,String sdt,String email,String street,String city,byte[]hinhAnh) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Insert into Profile(id,Name,PhoneNumber,Email,Street,City,HinhAnh)  values(null,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,ten);
        statement.bindString(2, sdt);
        statement.bindString(3, email);
        statement.bindString(4, street);
        statement.bindString(5, city);
        statement.bindBlob(6, hinhAnh);
        statement.executeInsert();
    }
    public  void insertForImageWithImagenull(String ten,String sdt,String email,String street,String city) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Insert into users(id,Name,PhoneNumber,Email,Street,City)  values(null,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, ten);
        statement.bindString(2, sdt);
        statement.bindString(3, email);
        statement.bindString(4, street);
        statement.bindString(5, city);
        statement.executeInsert();
    }  public  void insertForImageWithImagenullProfile(String ten,String sdt,String email,String street,String city) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Insert into Profile(id,Name,PhoneNumber,Email,Street,City)  values(null,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, ten);
        statement.bindString(2, sdt);
        statement.bindString(3, email);
        statement.bindString(4, street);
        statement.bindString(5, city);
        statement.executeInsert();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
