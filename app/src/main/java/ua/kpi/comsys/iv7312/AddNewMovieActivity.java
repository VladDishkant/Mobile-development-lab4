package ua.kpi.comsys.iv7312;

import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddNewMovieActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextType;
    private EditText editTextYear;
    private Button button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_movie);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        editTextTitle = findViewById(R.id.editText_title_new_movie);
        editTextType = findViewById(R.id.editText_type_new_movie);
        editTextYear = findViewById(R.id.editText_year_new_movie);

        editTextYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextYear.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });

        button_add = findViewById(R.id.button_add_new_movie);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTitle.getText().toString().length() != 0 &
                        editTextType.getText().toString().length() != 0 &
                        isNumeric(editTextYear.getText().toString())) {
                    MainActivity.states.add(new State(editTextTitle.getText().toString(),
                            editTextYear.getText().toString(), editTextType.getText().toString(),
                            R.drawable.poster_null, false, -1));
                    MainActivity.recyclerViewAdapter.notifyItemChanged(
                            MainActivity.recyclerViewAdapter.getItemCount() - 1);
                    RecyclerViewAdapter.mStates = MainActivity.states;
                    RecyclerViewAdapter.mStatesCopy = new ArrayList<>(RecyclerViewAdapter.mStates);
                    finish();
                } else {
                    if(editTextYear.getText().toString().length() == 4 &&
                            Integer.parseInt(editTextYear.getText().toString()) >= 1895 &&
                            Integer.parseInt(editTextYear.getText().toString()) <= 2099){
                    Toast.makeText(AddNewMovieActivity.this,
                            "Помилка, введіть правильно дані", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddNewMovieActivity.this,
                                "Помилка, введіть число в діапазоні 1895-2099", Toast.LENGTH_SHORT).show();
                        editTextYear.getText().clear();
                    }
                }
            }
        });
    }

    public static boolean isNumeric(String strNum) {
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
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