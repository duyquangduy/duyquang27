package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import adapter.BannerAdapter;
import com.example.appnhac.R;

import model.Quangcao;

import java.util.ArrayList;
import java.util.List;

import service.APIService;
import service.Dataservice;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {

    View view;

    ViewPager viewPager;
    CircleIndicator circleIndicator;

    BannerAdapter bannerAdapter;

    //2 thu vien nay dung de tu dong next quang cao sau 1 khoang thoi gian
    Runnable runnable;
    Handler handler;
    int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner, container, false);
        AnhXa();
        GetData();
        return view;
    }

    private void AnhXa() {
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.indicatordefault);
    }

    private void GetData() {
        Dataservice dataservice = APIService.getService();
        Call<List<Quangcao>> callback = dataservice.GetDataBanner();
        callback.enqueue(new Callback<List<Quangcao>>() {   //lang nghe du lieu tra ve
            @Override
            public void onResponse(Call<List<Quangcao>> call, Response<List<Quangcao>> response) {
                ArrayList<Quangcao> banners = (ArrayList<Quangcao>) response.body();
                Log.d("CCC", banners.get(0).getTenBaiHat());
                //dua du lieu quang cao vao
                bannerAdapter = new BannerAdapter(getActivity(), banners);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                //tu dong chuyen sang quang cao khac
                handler = new Handler();
                runnable = new Runnable() {   //goi nhung phuong thuc ma handler yeu cau
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if (currentItem >= viewPager.getAdapter().getCount()) {
                            currentItem = 0; //neu next den quang cao cuoi cung thi quay lai dau tien
                        }
                        viewPager.setCurrentItem(currentItem, true);
                        handler.postDelayed(runnable, 4000); //sau 4s next 1 lan
                    }
                };
                handler.postDelayed(runnable, 4000);
            }

            @Override
            public void onFailure(Call<List<Quangcao>> call, Throwable t) {
                Log.d("FFF", t.getMessage());
            }
        });
    }
}
