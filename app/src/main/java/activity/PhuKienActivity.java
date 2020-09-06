package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.PhuKienAdapter;
import model.Sanpham;
import ultil.CheckConnection;
import ultil.Server;

public class PhuKienActivity extends AppCompatActivity {

    Toolbar toolbarphukien;
    ListView lvphukien;
    PhuKienAdapter phuKienAdapter;
    ArrayList<Sanpham> mangphukien;
    int idphukien = 0;
    int page = 1;

    View footerView;
    boolean isLoading = false;
    PhuKienActivity.mHandler mHandler;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phu_kien);


        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            anhxa();
            GetIdloaisanpham();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Kiểm tra lại kết nối");
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menugiohang:
//                Intent intent = new Intent(getApplicationContext(), activity.Giohang.class);
//                startActivity(intent);
//        }
        return super.onOptionsItemSelected(item);
    }

    private void anhxa() {
        toolbarphukien = findViewById(R.id.toolbarphukien);
        lvphukien = findViewById(R.id.listviewphukien);
        mangphukien = new ArrayList<>();
        phuKienAdapter = new PhuKienAdapter(getApplicationContext(), mangphukien);
        lvphukien.setAdapter(phuKienAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        mHandler = new PhuKienActivity.mHandler();
    }

    private void GetIdloaisanpham() {
        idphukien = getIntent().getIntExtra("idloaisanpham", -1);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarphukien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarphukien.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdandienthoai + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String TenPhuKien = "";
                        int GiaPhuKien = 0;
                        String HinhAnhPhuKien = "";
                        String MotaPhuKien = "";
                        int IdspPhuKien = 0;
                        if (response != null && response.length() != 2) {  //khac []
                            lvphukien.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    TenPhuKien = jsonObject.getString("tensp");
                                    GiaPhuKien = jsonObject.getInt("giasp");
                                    HinhAnhPhuKien = jsonObject.getString("hinhanhsp");
                                    MotaPhuKien = jsonObject.getString("motasp");
                                    IdspPhuKien = jsonObject.getInt("idsanpham");
                                    mangphukien.add(new Sanpham(id, TenPhuKien, GiaPhuKien, HinhAnhPhuKien, MotaPhuKien, IdspPhuKien));
                                    phuKienAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitdata = true;
                            lvphukien.removeFooterView(footerView);
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
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
            protected Map<String, String> getParams() throws AuthFailureError { //day du lieu len
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idphukien)); //key de giong trong file getsanpham.php
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LoadMoreData() {
        lvphukien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", mangphukien.get(position));
                startActivity(intent);
            }
        });
        lvphukien.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0
                        && isLoading == false && limitdata == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    lvphukien.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);

        MenuItem menuitem = menu.findItem(R.id.menu_search);


        android.widget.SearchView searchView1 = (android.widget.SearchView) menuitem.getActionView();
        searchView1.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                phuKienAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
