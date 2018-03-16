package mobilemiddleware.wanderlust;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mobilemiddleware.application.WanderLustApplication;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by revathi on 06/03/18.
 */

/**
 * @author revathim initiates retrofit adapter and http cache
 */
public class SetupRetrofit {


    /**
     * Initiates retrofit adapter and http cache
     */
    public static final String API_BASE_URL = WanderLustApplication.getInstance().getResources()
            .getString(R.string.base_url);

    public static <S> S createService(Class<S> serviceClass) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor).writeTimeout(10, TimeUnit.MINUTES).connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES).addNetworkInterceptor(new AuthorizationInterceptor())
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        return retrofit.create(serviceClass);

    }
}

/**
 * checks the requests, fetches parameters and includes authorization header
 *
 * @author revathim
 */
class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response originalResponse;

        request = request.newBuilder()
                .build();

        originalResponse = chain.proceed(request);
        return originalResponse.newBuilder()
                .build();
    }
}