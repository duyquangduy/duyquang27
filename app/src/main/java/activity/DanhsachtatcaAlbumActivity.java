package activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.appnhac.R;

import java.util.ArrayList;
import java.util.List;

import adapter.AllAlbumAdapter;
import model.Album;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import service.APIService;
import service.Dataservice;

public class DanhsachtatcaAlbumActivity extends AppCompatActivity {
    RecyclerView recyclerViewtatcaalbum;
    Toolbar toolbartatcaalbum;
    AllAlbumAdapter allAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatca_album);
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.GetAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> mangalbum = (ArrayList<Album>) response.body();
                allAlbumAdapter = new AllAlbumAdapter(DanhsachtatcaAlbumActivity.this,mangalbum);
                recyclerViewtatcaalbum.setLayoutManager(new GridLayoutManager(DanhsachtatcaAlbumActivity.this,2));
                recyclerViewtatcaalbum.setAdapter(allAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewtatcaalbum = findViewById(R.id.recyclerviewtatcaalbum);
        toolbartatcaalbum = findViewById(R.id.toolbartatcaalbum);
        setSupportActionBar(toolbartatcaalbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Các Album");
        toolbartatcaalbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
