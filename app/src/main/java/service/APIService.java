package service;

public class APIService {  //lam trung gian ket hop Dataservice voi APIRetrofitClient
    private static String base_url = "https://daoduyquang27.000webhostapp.com/";  //lay ca dau /

    public  static Dataservice getService(){
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
