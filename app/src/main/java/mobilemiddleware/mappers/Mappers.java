package mobilemiddleware.mappers;

import mobilemiddleware.application.WanderLustApplication;
import mobilemiddleware.responses.RegistrationResponse;
import mobilemiddleware.services.Services;
import mobilemiddleware.wanderlust.SetupRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by revathi on 06/03/18.
 */
public class Mappers {

    private RegistrationListener registrationListener;
    private ResetPasswordListener resetPasswordListener;
    private LoginListener loginListener;
    private checkUserListener checkUserListener;

    public interface RegistrationListener {

        void onSuccess(RegistrationResponse response);

        void onFailure(String message);
    }

    public void register(String email, String name, String password, RegistrationListener listener) {
        this.registrationListener = listener;

        final Services services = SetupRetrofit.createService(Services.class);

        Call<RegistrationResponse> call = services.register(name, email, password);
        WanderLustApplication.getInstance().showProgressDialog("Registering....");
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call,
                                   Response<RegistrationResponse> response) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        registrationListener.onSuccess(response.body());
                    } else {
                        registrationListener.onSuccess(null);
                    }
                } else {
                    registrationListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (t != null) {
                    if (t.getMessage() != null) {
                        registrationListener.onFailure(t.getMessage());
                    } else {
                        registrationListener.onFailure(null);
                    }
                } else {
                    registrationListener.onFailure(null);
                }
            }
        });
    }

    public interface ResetPasswordListener {

        void onSuccess(RegistrationResponse response);

        void onFailure(String message);
    }

    public void resetPassword(String email, ResetPasswordListener listener) {
        this.resetPasswordListener = listener;

        final Services services = SetupRetrofit.createService(Services.class);

        Call<RegistrationResponse> call = services.resetPassword(email);
        WanderLustApplication.getInstance().showProgressDialog("Sending Request....");
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call,
                                   Response<RegistrationResponse> response) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        resetPasswordListener.onSuccess(response.body());
                    } else {
                        resetPasswordListener.onSuccess(null);
                    }
                } else {
                    resetPasswordListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (t != null) {
                    if (t.getMessage() != null) {
                        resetPasswordListener.onFailure(t.getMessage());
                    } else {
                        resetPasswordListener.onFailure(null);
                    }
                } else {
                    resetPasswordListener.onFailure(null);
                }
            }
        });
    }

    public interface LoginListener {

        void onSuccess(RegistrationResponse response);

        void onFailure(String message);
    }

    public void login(String email, String password, LoginListener listener) {
        this.loginListener = listener;

        final Services services = SetupRetrofit.createService(Services.class);

        Call<RegistrationResponse> call = services.login(email, password);
        WanderLustApplication.getInstance().showProgressDialog("Logging in....");
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call,
                                   Response<RegistrationResponse> response) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        loginListener.onSuccess(response.body());
                    } else {
                        loginListener.onSuccess(null);
                    }
                } else {
                    loginListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (t != null) {
                    if (t.getMessage() != null) {
                        loginListener.onFailure(t.getMessage());
                    } else {
                        loginListener.onFailure(null);
                    }
                } else {
                    loginListener.onFailure(null);
                }
            }
        });
    }

    public interface checkUserListener {

        void onSuccess(RegistrationResponse response);

        void onFailure(String message);
    }

    public void checkUserSession(checkUserListener listener) {
        this.checkUserListener = listener;

        final Services services = SetupRetrofit.createService(Services.class);

        Call<RegistrationResponse> call = services.checkUserSession();
        WanderLustApplication.getInstance().showProgressDialog("Checking session....");
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call,
                                   Response<RegistrationResponse> response) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (response != null) {
                    if (response.body() != null) {
                        checkUserListener.onSuccess(response.body());
                    } else {
                        checkUserListener.onSuccess(null);
                    }
                } else {
                    checkUserListener.onSuccess(null);
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                WanderLustApplication.getInstance().hideProgressDialog();
                if (t != null) {
                    if (t.getMessage() != null) {
                        checkUserListener.onFailure(t.getMessage());
                    } else {
                        checkUserListener.onFailure(null);
                    }
                } else {
                    checkUserListener.onFailure(null);
                }
            }
        });
    }
}
