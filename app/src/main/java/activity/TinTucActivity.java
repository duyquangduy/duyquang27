package activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.TinTucAdapter;
import model.TinTuc;

public class TinTucActivity extends AppCompatActivity {
    ListView listView;
    TinTucAdapter adapter;
    ArrayList<TinTuc> mangDocBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_tuc);

       BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.news);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.news: ;
                        return true;
                    case R.id.main:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),TimKiemActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        //lấy dữ liệu tin tức
        listView = findViewById(R.id.listViewTinTuc);
        mangDocBao = new ArrayList<TinTuc>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("https://vnexpress.net/rss/so-hoa.rss");
                new ReadData().execute("https://dantri.com.vn/suc-manh-so/dien-thoai.rss");
                new ReadData().execute("https://dantri.com.vn/suc-manh-so/thi-truong-cong-nghe.rss");
                new ReadData().execute("https://dantri.com.vn/suc-manh-so/dien-may.rss");

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, mangDocBao.get(position).getLink(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TinTucActivity.this, IntentNoiDung.class);
                intent.putExtra("link", mangDocBao.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    private class ReadData extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {

            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();

            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");   //lay the trung vs ten
            NodeList nodeListDescription = document.getElementsByTagName("description");

            String hinhAnh = "";
            String title = "";
            String link = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                String cdata = nodeListDescription.item(i +1).getTextContent();  //bo desc dau tien (i = 0) vi no k chua link
                //doc the image
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if (matcher.find()) {
                    hinhAnh = matcher.group(1);
                }

                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title"); //lam app hoan chinh thi khong duoc += nhe
                link = parser.getValue(element, "link");
                mangDocBao.add(new TinTuc(title, link, hinhAnh));
            }
            adapter = new TinTucAdapter(TinTucActivity.this, android.R.layout.simple_list_item_1, mangDocBao);
            listView.setAdapter(adapter);
            super.onPostExecute(s);
        }
    }

    private String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public void onBackPressed() {
       finish();
        super.onBackPressed();
    }
}
