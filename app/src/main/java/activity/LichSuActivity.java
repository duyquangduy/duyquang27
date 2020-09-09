package activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import adapter.LichSuAdapter;
import model.LichSu;
import ultil.CheckConnection;
import ultil.Server;

public class LichSuActivity extends AppCompatActivity {

    Toolbar toolbarlichsu;
    ListView lvlichsu;
    LichSuAdapter lichSuAdapter;
    ArrayList<LichSu> manglichsu;
    int iddt = 0;
    int page = 1;

    View footerView;
    boolean isLoading = false;
   // mHandler mHandler;
    boolean limitdata = false;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su);
        //mainActivity.DangNhapFacebook();
        Log.d("userid",MainActivity.userId);
        anhxa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionToolbar();
            GetData();
            //LoadMoreData();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Kiểm tra lại kết nối");
            finish();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

//    private void LoadMoreData() {
//        lvlichsu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
//                intent.putExtra("thongtinsanpham", manglichsu.get(position));
//                startActivity(intent);
//            }
//        });
//        lvlichsu.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0
//                        && isLoading == false && limitdata == false) {
//                    isLoading = true;
//                    ThreadData threadData = new ThreadData();
//                    threadData.start();
//                }
//            }
//        });
//    }

    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //String duongdan = Server.Duongdanlichsumuahang + String.valueOf(Page);
        String duongdan = Server.Duongdanlichsumuahang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int id = 0;
                        String Tensp = "";
                        int Giasp = 0;
                        int slsp = 0;
                        String Hinhanhsp = "";
                        String ngaymua = "";
                        //int Idspdt = 0;
                        if (response != null && response.length() != 2) {
                            lvlichsu.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    id = jsonObject.getInt("madonhang");
                                    Tensp = jsonObject.getString("tensanpham");
                                    Giasp = jsonObject.getInt("giasanpham");
                                    Hinhanhsp = jsonObject.getString("hinhanhsanpham");
                                    ngaymua = jsonObject.getString("ngaymuahang");
                                    slsp = jsonObject.getInt("soluongsanpham");
                                    manglichsu.add(new LichSu(id, Tensp, Giasp, slsp, Hinhanhsp, ngaymua));
                                    lichSuAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            limitdata = true;
                            lvlichsu.removeFooterView(footerView);
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
                param.put("iduser", MainActivity.userId);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarlichsu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlichsu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdloaisanpham() {
        iddt = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("Giatriloaisanpham", iddt + "");
    }

    private void anhxa() {
        toolbarlichsu = findViewById(R.id.toolbarlichsu);
        lvlichsu = findViewById(R.id.listviewlichsu);
        manglichsu = new ArrayList<>();
        lichSuAdapter = new LichSuAdapter(getApplicationContext(), manglichsu);
        lvlichsu.setAdapter(lichSuAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar, null);
        //mHandler = new mHandler();
    }

//    public class mHandler extends Handler {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what) {
//                case 0:
//                    lvlichsu.addFooterView(footerView);
//                    break;
//                case 1:
//                    GetData();
//                    isLoading = false;
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    }
//
//    public class ThreadData extends Thread {
//        @Override
//        public void run() {
//            mHandler.sendEmptyMessage(0);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Message message = mHandler.obtainMessage(1);
//            mHandler.sendMessage(message);
//            super.run();
//        }
//    }

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
                lichSuAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
