package diep.esc.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import diep.esc.retrofit2.converter.selection.gson.SelectionGsonConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private DemoEndpoint mEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.tv_hello);
        mButton = (Button) findViewById(R.id.button);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextViewClicked(v);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(v);
            }
        });
        mEndpoint= createDemoEndpoint();
    }

    private DemoEndpoint createDemoEndpoint(){
        SelectionGsonConverterFactory factory
                =new SelectionGsonConverterFactory(ResponseMessage.class);

        Retrofit.Builder builder =new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl("http://rawgit.com/TranTienTung/SelectionGsonConverter/master/")
                .addConverterFactory(factory)
                .build();
        DemoEndpoint endpoint = retrofit.create(DemoEndpoint.class);
        return endpoint;
    }

    private void onTextViewClicked(View v){


        Call<ApiSelectedMessageData> call = mEndpoint.getData();
        mTextView.setText("retrieving data...");
        call.enqueue(new Callback<ApiSelectedMessageData>() {
            @Override
            public void onResponse(Call<ApiSelectedMessageData> call,
                                   Response<ApiSelectedMessageData> response) {
                mTextView.setText(response.body().message);
            }

            @Override
            public void onFailure(Call<ApiSelectedMessageData> call, Throwable t) {
                mTextView.setText("getData Failed, make sure your are connected to" +
                        " the internet and try again");
                Log.e("", "onFailure: ",t);
            }
        });
    }
    private void onButtonClicked(View v){
        Call<Api2SelectedMessageData> call =mEndpoint.getData2();
        call.enqueue(new Callback<Api2SelectedMessageData>() {
            @Override
            public void onResponse(Call<Api2SelectedMessageData> call,
                                   Response<Api2SelectedMessageData> response) {
                Toast.makeText(MainActivity.this,response.body().result,Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Api2SelectedMessageData> call, Throwable t) {
                Log.e("", "onFailure: ",t);
            }
        });
    }
}
