package a07580542.easywallet;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import a07580542.easywallet.database.DatabaseHelper;

public class payActivity extends AppCompatActivity {
    EditText input_detail;
    EditText input_money;
    Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        input_detail = findViewById(R.id.input_detail_pay_edittext);
        input_money = findViewById(R.id.input_money_pay_edittext);
        save_button = findViewById(R.id.save_pay_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_detail.getText().toString().equalsIgnoreCase("")||input_money.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(payActivity.this, "กรุณาใส่ข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }else{
                    savedatatodb();
                    setResult(RESULT_OK);
                    finish();
                }

            }
        });
    }
    private void savedatatodb() {
        String detail = input_detail.getText().toString();
        String money = input_money.getText().toString();
        String picture = "ic_expense.png";
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.col_picture,picture);
        cv.put(DatabaseHelper.col_detail,detail);
        cv.put(DatabaseHelper.col_money,money);
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase sqldb = helper.getWritableDatabase();
        sqldb.insert(DatabaseHelper.table_name,null,cv);
    }
}
