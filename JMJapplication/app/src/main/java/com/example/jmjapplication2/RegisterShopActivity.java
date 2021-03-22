package com.example.jmjapplication2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.jmjapplication2.dto.Shop;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

public class RegisterShopActivity extends AppCompatActivity {
    EditText shop_et_id, shop_et_name, shop_et_intro, shop_et_addr;
    Spinner shop_open, shop_close, shop_isres, shop_category;
    Button shop_register;
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataService dataService = new DataService();
        
        String member_id = ((JMJApplication)this.getApplication()).getId();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_shop);

        shop_et_id = findViewById(R.id.shop_et_id);
        shop_et_name = findViewById(R.id.shop_et_name);
        shop_et_intro = findViewById(R.id.shop_et_intro);
        shop_et_addr = findViewById(R.id.shop_et_addr);
        shop_open = findViewById(R.id.shop_et_open);
        shop_close = findViewById(R.id.shop_et_close);
        shop_category = findViewById(R.id.shop_et_category);
        shop_isres = findViewById(R.id.shop_et_isres);
        shop_register = findViewById(R.id.shop_register);

        //spinner
        ArrayAdapter openAdapter = ArrayAdapter.createFromResource(this, R.array.open_time, android.R.layout.simple_spinner_item);
        openAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_open.setAdapter(openAdapter);

        ArrayAdapter closeAdapter = ArrayAdapter.createFromResource(this, R.array.close_time, android.R.layout.simple_spinner_item);
        closeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_close.setAdapter(closeAdapter);

        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_category.setAdapter(categoryAdapter);

        ArrayAdapter isresAdapter = ArrayAdapter.createFromResource(this, R.array.reservation, android.R.layout.simple_spinner_item);
        isresAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop_isres.setAdapter(isresAdapter);

        shop_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("id", shop_et_id.getText().toString());
                map.put("name", shop_et_name.getText().toString());
                map.put("intro", shop_et_intro.getText().toString());
                map.put("address", shop_et_addr.getText().toString());
                map.put("open", shop_open.getSelectedItem().toString());
                map.put("close", shop_close.getSelectedItem().toString());
                map.put("category", shop_category.getSelectedItem().toString());
                map.put("is_re_pos", shop_isres.getSelectedItem().toString());
                map.put("member_id", member_id);

                dataService.registerShop.registerShopOne(map).enqueue(new Callback<Shop>() {
                    @Override
                    public void onResponse(Call<Shop> call, Response<Shop> response) {
                        if(response.isSuccessful()) {
                            Log.d("result : " , "연결성공");
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterShopActivity.this);
                            dialog = builder.setMessage("매장이 등록되었습니다.").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RegisterShopActivity.this, MainActivity_O.class);
                                    startActivity(intent);
                                }
                            }).create();
                            builder.setCancelable(false);
                            dialog.show();
                        } else {
                            Log.d("result : " , "연결실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<Shop> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
