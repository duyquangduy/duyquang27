package service;

import androidx.annotation.FractionRes;

import model.Album;
import model.Baihat;
import model.ChuDe;
import model.Playlist;
import model.Quangcao;

import java.util.List;

import model.TheLoai;
import model.Theloaitrongngay;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {  //gui phuong thuc len Server va lay du lieu ve(de o dang interface)
    //Get dung de tuong tac voi duong link
    @GET("Server/songbanner.php")
    //gui phuong thuc len
    Call<List<Quangcao>> GetDataBanner();   //tra ve kieu array co cac object ben trong

    @GET("Server/playlistforcurrentday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();

    @GET("Server/chudevatheloaitrongngay.php")
    Call<Theloaitrongngay> GetCategoryMusic();

    @GET("Server/albumhot.php")
    Call<List<Album>> GetAlbumHot();

    @GET("Server/baihatduocthich.php")
    Call<List<Baihat>> GetBaiHatHot();

    @FormUrlEncoded     //de su dung phuong thuc POST de gui du lieu len
    @POST("Server/danhsachbaihat.php")
        //phai trung phuong thuc vs tren Server
    Call<List<Baihat>> GetDanhsachbaihattheoquangcao(@Field("idquangcao") String idquangcao);  //trung ten vs bien $_POST tren server

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoplaylist(@Field("idplaylist") String idplaylist);

    @GET("Server/danhsachcacplaylist.php")
    Call<List<Playlist>> GetDanhsachcacPlaylist();

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhSachBaiHatTheoTheLoai(@Field("idtheloai") String idtheloai);

    @GET("Server/tatcachude.php")
    Call<List<ChuDe>> GetAllChuDe();

    @FormUrlEncoded
    @POST("Server/theloaitheochude.php")
    Call<List<TheLoai>> GetTheloaitheochude(@Field("idchude") String idchude); //key de giong trong $_POST file php

    @GET("Server/tatcaalbum.php")
    Call<List<Album>> GetAlbum();

    @FormUrlEncoded
    @POST("Server/danhsachbaihat.php")
    Call<List<Baihat>> GetDanhsachbaihattheoalbum(@Field("idalbum") String idalbum); //Field lay trung ten vs $_POST file php

    @FormUrlEncoded
    @POST("Server/updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich,
                                 @Field("idbaihat") String idbaihat);   //du lieu tra ve dang chuoi

    @FormUrlEncoded
    @POST("Server/searchbaihat.php")
    Call<List<Baihat>> GetSearchBaihat(@Field("tukhoa") String tukhoa);
}
