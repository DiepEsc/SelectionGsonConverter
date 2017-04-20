package diep.esc.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import diep.esc.retrofit2.converter.selection.gson.SelectionGsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_hello);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextViewClicked(v);
            }
        });
    }

    private void onTextViewClicked(View v){

        SelectionGsonConverterFactory factory
                =new SelectionGsonConverterFactory(ResponseMessage.class);

        Retrofit.Builder builder =new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("http://rawgit.com/TranTienTung/alert-simple-js/master/")
                .addConverterFactory(factory)
                .build();
        DemoEndpoint endpoint = retrofit.create(DemoEndpoint.class);
        Call<SelectedMessageData> call = endpoint.getData();
        mTextView.setText("retrieving data...");
        call.enqueue(new Callback<SelectedMessageData>() {
            @Override
            public void onResponse(Call<SelectedMessageData> call,
                                   Response<SelectedMessageData> response) {
                mTextView.setText(response.body().message);
            }

            @Override
            public void onFailure(Call<SelectedMessageData> call, Throwable t) {
                mTextView.setText("getData Failed, make sure your are connected to the internet and try again");
                Log.e("", "onFailure: ",t);
            }
        });
    }
}
