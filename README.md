# Retrofit 

## Giới thiệu

-	Trang chủ: https://square.github.io/retrofit/ 
-	Retrofit là một Rest Client (cần tìm hiểu về restful api trước để nắm rõ hơn) cho Android và Java được tạo bởi Square. Thư việc giúp việc nhận và tải JSON (hoặc dữ liệu khác)  một các khá dễ dàng với Webservice dựa trên mô hình REST.
-	Các gói trang bị bộ chuyển đổi gồm: Gson, Jackson, Moshi, Protobuf, Wire, Simple XML

## Hướng dẫn

Để triển khai Retrofit bạn cần xây dựng 3 lớp cơ bản:
-	Model class để mapping với dữ liệu
-	Interface để định nghĩa các API cho Webservice
-	Retrofit.Builder để định nghĩa URL Endpoint cho hoạt động liên qua http

## Cài đặt

-	Cài đặt Retrofit trong Gradle:</br>
    `implementation 'com.squareup.retrofit2:retrofit:2.5.0'` </br>
    `implementation 'com.squareup.retrofit2:converter-gson:2.5.0'`
-	Thêm quyền truy cập Internet dành cho ứng dụng: </br>
    `uses-permission android:name="android.permission.INTERNET"`
## Ví dụ demo
- API do mình tự xây dựng, sử dụng nodejs, link: https://github.com/dinhlamvn/retrofit/tree/master/MyServer . Các bạn có thể tải về và chạy server trên máy của mình bằng cách vào thư mục MyServer, mở cmd và gõ:
`npm install` </br>
`node server.js` 
- File cơ sở dữ liệu cũng có trong link này https://github.com/dinhlamvn/retrofit/blob/master/blogs.sql, các bạn có thể tải về và import vào mysql
- Xây dựng model: (Để tiện các bạn có thể vào trang http://www.jsonschema2pojo.org/ để tạo ra model tự động từ dữ liệu nhận được) 

- Xây dựng interface để giao tiếp với api: Ở đây mình sử dụng 2 phương thức chính là GET và POST </br>
```java
public interface RequestApi {

    @GET("user/")
    Call<User> getInfoUser(@Query("user_id") String userId);

    @POST("signup/")
    Call<Result> signUp(@Body User.UserData user);

    @FormUrlEncoded
    @POST("signin/")
    Call<Result> signIn(@Field("email") String email, @Field("pws") String pws);
}
```
- Tạo client api:
```java
public class APIClient {

    private static final String BASE_URL = "http://localhost:8800/";

    private static Retrofit retrofit = null;
    
    public static Retrofit getClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
```
Ở đây, BASE_URL là http://localhost:8800/ chính là server nodejs đang chạy trên máy mình, máy của các bạn địa chỉ như nào thì chỉ cần sửa chỗ này là được

- Tiến hành gọi api:
1. API Sign Up (POST)
```java
    Retrofit retrofit = APIClient.getClient();

    RequestApi callApi = retrofit.create(RequestApi.class);

    Call<Result> call = callApi.signUp(userData);

    call.enqueue(this);
        
    @Override
    public void onResponse(Call<Result> call, Response<Result> response) {
        // Xử lý response server trả về khi sign up ở đây
    }

    @Override
    public void onFailure(Call<Result> call, Throwable t) {
        // Xử lý khi gọi api bị lỗi
    }
```
2. API Get info (GET)
```java
        Retrofit retrofit = APIClient.getClient();

        RequestApi requestApi = retrofit.create(RequestApi.class);

        Call<User> call = requestApi.getInfoUser(userId);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // Xử lý response server trả về, cụ thể là thông tin user
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gặp lỗi call api
            }
        });
```

Trên đây, mình đã giới thiệu cũng như hướng dẫn các bạn sử dụng Retrofit để gọi api trong Android. Nếu có vấn đề gì chưa rõ, các bạn có thể liên hệ với mình để chúng ta cùng nhau làm rõ hơn.
