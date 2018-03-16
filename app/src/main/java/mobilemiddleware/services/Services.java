package mobilemiddleware.services;

import mobilemiddleware.responses.RegistrationResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by revathi on 06/03/18.
 */

public interface Services {

    @FormUrlEncoded
    @POST("api/RegisterUser")
    Call<RegistrationResponse> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/resetPassword")
    Call<RegistrationResponse> resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("api/Login")
    Call<RegistrationResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("api/GetUser")
    Call<RegistrationResponse> checkUserSession();

}