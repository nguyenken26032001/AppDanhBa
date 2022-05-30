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
    public void UpdateUsers(String Ho,String ten, byte[] img,String sdt,Integer position){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("firstName",Ho);
        contentValues.put("lastName",ten);
        contentValues.put("hinhAnh",img);
        contentValues.put("phoneNumber",sdt);
        String where = "id" + "=" + position;
       db.update("users",contentValues, where, null);
    }


    //this function use select data in database
    public Cursor getData(String str) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(str, null);
    }
    public  void insertForImage(String Ho,String ten,String sdt,byte[]hinhAnh) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Insert into users(id,firstName, lastName,phoneNumber,hinhAnh)  values(null,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,Ho);
        statement.bindString(2, ten);
        statement.bindString(3, sdt);
        statement.bindBlob(4, hinhAnh);
        statement.executeInsert();
    }
    public  void insertForImageWithImagenull(String Ho,String ten,String sdt) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "Insert into users(id,firstName, lastName,phoneNumber)  values(null,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,Ho);
        statement.bindString(2, ten);
        statement.bindString(3, sdt);
        statement.executeInsert();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
