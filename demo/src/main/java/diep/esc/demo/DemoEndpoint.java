package diep.esc.demo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by diep on 4/20/17.
 */

public interface DemoEndpoint {
    @GET("demoApi")
    Call<ApiSelectedMessageData> getData();

    @GET("demoApi2")
    Call<Api2SelectedMessageData> getData2();
}
