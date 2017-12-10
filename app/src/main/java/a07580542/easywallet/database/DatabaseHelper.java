package a07580542.easywallet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dream on 10/12/2560.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Database_name="Life.db";
    private static final int Database_version=1;

    public static final String table_name="LifeMoney";
    public static final String col_id="id";
    public static final String col_picture="picture";
    public static final String col_detail="detail";
    public static final String col_money="money";

    private String sql_create_table="create table "+table_name+" ("
            +col_id+" integer primary key autoincrement,"
            +col_picture+" text,"
            +col_detail+" text,"
            +col_money+" text)";

    public DatabaseHelper(Context context) {
        super(context, Database_name, null, Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_create_table);
        insert(db);
    }

    private void insert(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(col_picture,"ic_income.png");
        cv.put(col_detail,"คุณพ่อให้เงิน");
        cv.put(col_money,"8000");
        db.insert(table_name,null,cv);

        cv = new ContentValues();
        cv.put(col_picture,"ic_expense.png");
        cv.put(col_detail,"จ่ายค่าหอ");
        cv.put(col_money,"2500");
        db.insert(table_name,null,cv);

        cv = new ContentValues();
        cv.put(col_picture,"ic_expense.png");
        cv.put(col_detail,"ซื้อล็อตเตอรี่ 1 ชุด");
        cv.put(col_money,"700");
        db.insert(table_name,null,cv);

        cv = new ContentValues();
        cv.put(col_picture,"ic_income.png");
        cv.put(col_detail,"ถูกล็อตเตอรี่รางวัลที่ 1");
        cv.put(col_money,"30000000");
        db.insert(table_name,null,cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
