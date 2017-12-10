package a07580542.easywallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import a07580542.easywallet.adapter.LifeAdapter;
import a07580542.easywallet.database.DatabaseHelper;
import a07580542.easywallet.model.Life;

public class MainActivity extends AppCompatActivity {

    private TextView result_text;
    private ListView listView;
    private Button income_button;
    private Button pay_button;
    private DatabaseHelper helper;
    private SQLiteDatabase sqldb;
    private ArrayList<Life> lifelist = new ArrayList<>();
    private ArrayList<Life> lifelist_income = new ArrayList<>();
    private ArrayList<Life> lifelist_pay = new ArrayList<>();
    private LifeAdapter adapter;
    private DecimalFormat myFormatter = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result_text = findViewById(R.id.result_text);
        listView = findViewById(R.id.listview);
        income_button = findViewById(R.id.income_button);
        pay_button = findViewById(R.id.pay_button);

        helper = new DatabaseHelper(this);
        sqldb = helper.getWritableDatabase();
        loaddatafromdb();
        adapter = new LifeAdapter(this,R.layout.item,lifelist);
        listView.setAdapter(adapter);


        income_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,incomeActivity.class);
                startActivityForResult(intent,123);
            }
        });
        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,payActivity.class);
                startActivityForResult(intent,456);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final Life life = lifelist.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setMessage("ยืนยันลบรายการ "+life.detail+" "+myFormatter.format(Integer.parseInt(life.money))+" บาท?");
                dialog.setNegativeButton("NO",null);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sqldb.delete(DatabaseHelper.table_name,DatabaseHelper.col_detail+"=?",new String[]{life.detail});
                        loaddatafromdb();
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }//End of OnCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==123||requestCode==456){
            if(resultCode==RESULT_OK){
                loaddatafromdb();
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void loaddatafromdb() {
        Cursor cursor = sqldb.query(DatabaseHelper.table_name,null,null,null,null,null,null);
        lifelist.clear();
        lifelist_income.clear();
        lifelist_pay.clear();
        while(cursor.moveToNext()){
            String picture = cursor.getString(cursor.getColumnIndex(DatabaseHelper.col_picture));
            String detail = cursor.getString(cursor.getColumnIndex(DatabaseHelper.col_detail));
            String money = cursor.getString(cursor.getColumnIndex(DatabaseHelper.col_money));
            Life life = new Life(picture,detail,money);
            lifelist.add(life);
            if(picture.equalsIgnoreCase("ic_income.png")){
                lifelist_income.add(life);
            }else if(picture.equalsIgnoreCase("ic_expense.png")){
                lifelist_pay.add(life);
            }
        }
        String result = myFormatter.format(cal_result());
        result_text.setText("คงเหลือ "+result+" บาท");
    }
    private int cal_result(){
        int sum_income=0;
        int sum_pay=0;
        for(int i=0 ; i<lifelist_income.size() ; i++){
            Life life = lifelist_income.get(i);
            int income = Integer.parseInt(life.money);
            sum_income+=income;
        }
        for(int i=0 ; i<lifelist_pay.size() ; i++){
            Life life = lifelist_pay.get(i);
            int pay= Integer.parseInt(life.money);
            sum_pay+=pay;
        }
        return sum_income-sum_pay;
    }
}
