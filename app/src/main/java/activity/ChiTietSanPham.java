package activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanhang.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import model.Giohang;
import model.Sanpham;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarchitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;

    int id = 0;
    String Tenchitiet = "";
    int Giachitiet = 0;
    String Hinhanhchitiet = "";
    String Motachitiet = "";
    int Idsanpham = 0;

    int idtimkiem = 0;
    String tentimkiem = "";
    int giatimkiem = 0;
    String hinhanhtimkiem = "";
    String motatimkiem = "";

    CartCounter cartCounter;



    private FirebaseAuth mAuth;
    private DatabaseReference shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        anhxa();

        cartCounter = new CartCounter(findViewById(R.id.cartlayout));

        ActionToolbar();
        GetInformation();
        //GetInfoTimKiem();
        CatchEventSpinner();
        EventButton();

        cartCounter.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietSanPham.this, activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

//    private void GetInfoTimKiem() {
//        TimKiemSanPham timKiemSanPham = (TimKiemSanPham) getIntent().getSerializableExtra("thongtintimkiem");
//        idtimkiem = timKiemSanPham.getIdsanpham();
//        tentimkiem = timKiemSanPham.getTensanpham();
//        giatimkiem = timKiemSanPham.getGiasanpham();
//        hinhanhtimkiem = timKiemSanPham.getHinhanhsanpham();
//        motatimkiem = timKiemSanPham.getMotasanpham();
//        txtten.setText(tentimkiem);
//        DecimalFormat decimalFormat2 = new DecimalFormat("###,###,###");
//        txtgia.setText("Giá: " + decimalFormat2.format(Giachitiet) + " vnđ");
//        txtmota.setText(motatimkiem);
//        Picasso.get().load(hinhanhtimkiem)
//                .placeholder(R.drawable.no_image)
//                .error(R.drawable.cancel)
//                .into(imgChitiet);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                        if (MainActivity.manggiohang.get(i).getIdsp() == id) {
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluongsp() >= 10) {
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(Giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
                            exists = true;
                            cartCounter.setText(MainActivity.manggiohang.size());
                        }
                    }
                    if (exists == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * Giachitiet;
                        MainActivity.manggiohang.add(new Giohang(id, Tenchitiet, Giamoi, Hinhanhchitiet, soluong));
                        cartCounter.setText(MainActivity.manggiohang.size());
                    }
                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * Giachitiet;
                    MainActivity.manggiohang.add(new Giohang(id, Tenchitiet, Giamoi, Hinhanhchitiet, soluong));
                    cartCounter.setText(MainActivity.manggiohang.size());
                }
                Intent intent = new Intent(getApplicationContext(), activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");

        id = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet = sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhanhsanpham();
        Motachitiet = sanpham.getMotasanpham();
        Idsanpham = sanpham.getIDSanpham();

        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: " + decimalFormat.format(Giachitiet) + " vnđ");
        txtmota.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.cancel)
                .into(imgChitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbarchitiet = findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = findViewById(R.id.imageviewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.buttondatmua);
    }

    @Override
    protected void onResume() {
        cartCounter.setText(MainActivity.manggiohang.size());
        super.onResume();
    }

    @Override
    protected void onRestart() {
        cartCounter.setText(MainActivity.manggiohang.size());
        super.onRestart();
    }
}
