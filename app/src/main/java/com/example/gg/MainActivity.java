package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    Post curPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://developerslife.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonDeveloperslifeApi jsonDeveloperslifeApi = retrofit.create(JsonDeveloperslifeApi.class);

        Call<Post> call = jsonDeveloperslifeApi.getPost();

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textView.setText("Code:" + response.code());
                    return;
                }

                curPost = (Post)response.body();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });


    }

    public void ShowPrev(View view) {

    }

    public void ShowNext(View view) {


        Glide.with(MainActivity.this)
                .load(curPost.getGifURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        textView.setText(curPost.getDescription());

    }
}