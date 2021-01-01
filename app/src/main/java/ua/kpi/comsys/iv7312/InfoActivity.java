package ua.kpi.comsys.iv7312;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        MainActivity mainActivity = new MainActivity();
        String index = String.valueOf(mainActivity.getIndex());

        try {
            JSONObject obj = new JSONObject(mainActivity.loadJSONFromAsset(this, index));

            ImageView poster_info = (ImageView) findViewById(R.id.poster_info);
            TextView title_info = (TextView) findViewById(R.id.title_info);
            TextView year_info = (TextView) findViewById(R.id.year_info);
            TextView genre_info = (TextView) findViewById(R.id.genre_info);
            TextView director_info = (TextView) findViewById(R.id.director_info);
            TextView actors_info = (TextView) findViewById(R.id.actors_info);
            TextView country_info = (TextView) findViewById(R.id.country_info);
            TextView language_info = (TextView) findViewById(R.id.language_info);
            TextView production_info = (TextView) findViewById(R.id.production_info);
            TextView released_info = (TextView) findViewById(R.id.released_info);
            TextView runtime_info = (TextView) findViewById(R.id.runtime_info);
            TextView awards_info = (TextView) findViewById(R.id.awards_info);
            TextView rating_info = (TextView) findViewById(R.id.rating_info);
            TextView plot_info = (TextView) findViewById(R.id.plot_info);

            poster_info.setImageResource(mainActivity.getPoster(obj));

            String s;
            SpannableString ss;

            s = "Title: " + obj.getString("Title");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 7, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            title_info.setText(ss);

            s = "Year: " + obj.getString("Year");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 6, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            year_info.setText(ss);

            s = "Genre: " + obj.getString("Genre");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 7, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            genre_info.setText(ss);

            s = "Director: " + obj.getString("Director");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 10, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            director_info.setText(ss);

            s = "Actors: " + obj.getString("Actors");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 8, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            actors_info.setText(ss);

            s = "Country: " + obj.getString("Country");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 9, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            country_info.setText(ss);

            s = "Language: " + obj.getString("Language");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 10, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            language_info.setText(ss);

            s = "Production: " + obj.getString("Production");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 12, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            production_info.setText(ss);

            s = "Released: " + obj.getString("Released");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 10, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            released_info.setText(ss);

            s = "Runtime: " + obj.getString("Runtime");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 9, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            runtime_info.setText(ss);

            s = "Awards: " + obj.getString("Awards");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 8, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            awards_info.setText(ss);

            s = "Rating: " + obj.getString("imdbRating");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 8, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            rating_info.setText(ss);

            s = "Plot: " + obj.getString("Plot");
            ss =  new SpannableString(s);
            ss.setSpan(new ForegroundColorSpan(Color.BLACK), 6, s.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            plot_info.setText(ss);

            float textSize = (float) 22;

            title_info.setTextSize(textSize);
            year_info.setTextSize(textSize);
            genre_info.setTextSize(textSize);
            director_info.setTextSize(textSize);
            actors_info.setTextSize(textSize);
            country_info.setTextSize(textSize);
            language_info.setTextSize(textSize);
            production_info.setTextSize(textSize);
            released_info.setTextSize(textSize);
            runtime_info.setTextSize(textSize);
            awards_info.setTextSize(textSize);
            rating_info.setTextSize(textSize);
            plot_info.setTextSize(textSize);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}