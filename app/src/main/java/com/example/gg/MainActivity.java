package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.*;

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
    List<Post> posts;
    int curPostIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        posts = new ArrayList<Post>();
        LoadNewPost();
    }

    public void ShowPrev() {
        if (curPostIndex > 0) {
            curPostIndex--;
            Glide.with(MainActivity.this)
                    .load(posts.get(curPostIndex).getGifURL())
                    .onlyRetrieveFromCache(true)
                    .into(imageView);
            textView.setText(posts.get(curPostIndex).getDescription());
        }
    }

    public void ShowNext() {
        curPostIndex++;
        if (curPostIndex == posts.size() - 1) {
            LoadNewPost();
            Glide.with(MainActivity.this)
                    .load(posts.get(curPostIndex).getGifURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        } else {
            Glide.with(MainActivity.this)
                    .load(posts.get(curPostIndex).getGifURL())
                    .onlyRetrieveFromCache(true)
                    .into(imageView);
        }
        textView.setText(posts.get(curPostIndex).getDescription());

    }

    public void onNextButton(View view){
        ShowNext();
    }

    public void onPrevButton(View view){
        ShowPrev();
    }

    void LoadNewPost(){
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

                posts.add((Post)response.body());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}