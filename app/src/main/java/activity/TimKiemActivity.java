package activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.TimKiemAdapter;
import model.Sanpham;
import ultil.Server;

public class TimKiemActivity extends AppCompatActivity {
    EditText edttimkiem;
    Button btntimkiem;
    RecyclerView recyclerViewTimkiem;
    TimKiemAdapter timKiemAdapter;
    ArrayList<Sanpham> mangtimkiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        mangtimkiem = new ArrayList<>();
        anhxa();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.news:
                        startActivity(new Intent(getApplicationContext(),TinTucActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        return true;
                }
                return false;
            }
        });

        EvenButton();
    }

    private void EvenButton() {
        btntimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mangtimkiem.clear();
                final String input = edttimkiem.getText().toString().trim();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String duongdan = Server.Duongdantimkiemsanpham;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response",response);
                                int id = 0;
                                String Tensp = "";
                                int Giasp = 0;
                                String Hinhanhsp = "";
                                String Motasp = "";
                                int idsp = 0;
                                if (response != null && response.length() != 2) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        for (int i = 0; i < response.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            id = jsonObject.getInt("id");
                                            Tensp = jsonObject.getString("tensp");
                                            Giasp = jsonObject.getInt("giasp");
                                            Hinhanhsp = jsonObject.getString("hinhanhsp");
                                            Motasp = jsonObject.getString("motasp");
                                            idsp = jsonObject.getInt("idsp");
                                            mangtimkiem.add(new Sanpham(id,Tensp,Giasp,Hinhanhsp,Motasp,idsp));
                                            Log.d("search",mangtimkiem.get(0).getTensanpham());
                                            timKiemAdapter.notifyDataSetChanged();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
//                                    limitdata = true;
//                                    lvlichsu.removeFooterView(footerView);
                                    //CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("tensanpham", input);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void anhxa() {
        edttimkiem = findViewById(R.id.edt_timkiem);
        btntimkiem = findViewById(R.id.btntimkiem);
        recyclerViewTimkiem = findViewById(R.id.recyclerview_ketqua);

        timKiemAdapter = new TimKiemAdapter(getApplicationContext(), mangtimkiem);

        LinearLayoutManager linearLayoutManager2 = new GridLayoutManager(TimKiemActivity.this,2);
        recyclerViewTimkiem.setHasFixedSize(true);
        recyclerViewTimkiem.setLayoutManager(linearLayoutManager2);
        recyclerViewTimkiem.setAdapter(timKiemAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
