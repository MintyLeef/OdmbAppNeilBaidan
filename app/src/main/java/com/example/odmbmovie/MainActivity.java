package com.example.odmbmovie;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText searchBar;
    TextView plotTextView;
    TextView ratedTextView;
    TextView runtimeTextView;
    TextView actorsTextView;
    ImageView moviePosterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_bar);
        plotTextView = findViewById(R.id.plot_tv);
        ratedTextView = findViewById(R.id.rated_tv);
        runtimeTextView = findViewById(R.id.runtime_tv);
        actorsTextView = findViewById(R.id.actors_tv);
        moviePosterImageView = findViewById(R.id.movie_poster);
    }

    public void fetchData(View view) {
        String movieSearched = searchBar.getText().toString();
// ...

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.omdbapi.com/?apikey=b59c3e02&t=" + movieSearched;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject responseObject = new JSONObject(response);
                            String moviePosterUrl = responseObject.getString("Poster");
                            Picasso.get().load(moviePosterUrl).into(moviePosterImageView);
                            String plotText = "Plot: " + responseObject.getString("Plot");
                            plotTextView.setText(plotText);
                            String ratedText = "Rated: " + responseObject.getString("Rated");
                            ratedTextView.setText(ratedText);
                            String runtimeText = "Runtime: " + responseObject.getString("Runtime");
                            runtimeTextView.setText(runtimeText);
                            String actorsText = "Actors: " + responseObject.getString("Actors");
                            actorsTextView.setText(actorsText);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
