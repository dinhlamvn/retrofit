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
# GET
- Link api: http://www.dongabank.com.vn/exchange/export

- Xây dựng model: (Để tiện các bạn có thể vào trang http://www.jsonschema2pojo.org/ để tạo ra model tự động từ dữ liệu nhận được) 

- Xây dựng interface để giao tiếp với api: </br>
```java
public interface NgoaiTeAPI { 
    @GET("exchange/export") 
    @Headers({"Content-Type: Application/Json;charset=UTF-8","Accept: Application/Json",})
    Call<String> getGiaNgoaiTe();
}
```
- Tạo client api:
```java
public class APIClient {

    public static Retrofit create(final String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
```
Ở đây mình dùng Scalar mà không dùng chuyển trực tiếp sang json là vì dữ liệu của api có chứa 2 dấu '(' và ')' ở đầu và cuối nên không thể tự động chuyển từ json sang model được

- Tiến hành gọi api:
```java
        Retrofit callApi = APIClient.create(getResources().getString(R.string.base_url));
        NgoaiTeAPI ngoaiTeAPI = callApi.create(NgoaiTeAPI.class);

        Call<String> call = ngoaiTeAPI.getGiaNgoaiTe();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Xử lý dữ liệu ở đây
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Xử lý khi gọi thất bại
            }
        });
```
