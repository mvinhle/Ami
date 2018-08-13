package com.app.mv;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int clickDeleteData = 0;
    SharedPreferences sharedPreferences, sharedPreferencesTrust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Tiểu AA - thông báo");
        Button buttonYes = findViewById(R.id.ButtonYes);

        sharedPreferences      = getSharedPreferences(HelpData.KEY_INFORMATION, MODE_PRIVATE);
        sharedPreferencesTrust = getSharedPreferences(HelpData.KEY_SHARED_TRUST, MODE_PRIVATE);

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getString(HelpData.KEY_SUB_NAME, HelpData.KEY_DONT_BUG).equals(HelpData.KEY_DONT_BUG)
                        || sharedPreferences.getString(HelpData.KEY_NAME,HelpData.KEY_DONT_BUG).equals(HelpData.KEY_DONT_BUG)){
                    Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, AmiActivity.class);
                    startActivity(intent);
                }
            }
        });

        Button buttonNo = findViewById(R.id.ButtonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editData:
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteData:
                dialogDeleteAllData();
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Cảm ơn !!!");
                builder.setIcon(R.drawable.iconexit);
                builder.setMessage("\tCảm ơn vì đã tin dùng Ami - hổ trợ học tập\n\tấn vào tiếp tục để thoát khỏi phầm mềm");
                builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){}});
                builder.show();
                break;
                default:
                    Log.d(HelpData.KEY_LOG, "lỗi OptionMenu không nhận được ID");
                    break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void dialogDeleteAllData(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_data);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle(getResources().getString(R.string.deleteData));
        Button buttonYes = dialog.findViewById(R.id.deletaDataYes);
        Button buttonNo  = dialog.findViewById(R.id.deletaDataNo);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDeleteData += 1;
                if (clickDeleteData > 15){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    editor = sharedPreferencesTrust.edit();
                    editor.clear();
                    editor.commit();
                    Toast.makeText(MainActivity.this,getResources().getString(R.string.deleteDataComplete),Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                if (clickDeleteData / 5 == 0 && clickDeleteData > 0){
                    Toast.makeText(
                            MainActivity.this,
                            getResources().getString(R.string.deleteDataCompletess).replace("[n]",String.valueOf(clickDeleteData)),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDeleteData = 0;
                Toast.makeText(
                        MainActivity.this,
                        getResources().getString(R.string.deleteDataError),
                        Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
