package ua.kpi.comsys.iv7312;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener{

    static ArrayList<State> states = new ArrayList();
    RecyclerView recyclerView;
    static RecyclerViewAdapter recyclerViewAdapter;
    SearchView searchView;
    static private int indexOfInfo;
    private int orientation;
    static private boolean firstEntry = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orientation = getResources().getConfiguration().orientation;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT &
                firstEntry) {
            // начальная инициализация списка
            try {
                setInitialData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            firstEntry = false;
        }
        // получаем элемент ListView
        recyclerView = findViewById(R.id.recyclerView_main);
        // создаем строку поиска
        searchView = findViewById(R.id.search);
        // создаем адаптер
        recyclerViewAdapter = new RecyclerViewAdapter(this, R.layout.list_item, states);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        // устанавливаем адаптер
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toActivityItem2();

        FloatingActionButton fab = findViewById(R.id.fab_button_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNewMovieActivity.class));
            }
        });
    }

    private void toActivityItem2(){
        Button nextActivity = (Button) findViewById(R.id.buttonToNext_main);
        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    public void setIndex(int index){
        indexOfInfo = index;
    }

    public int getIndex(){
        return indexOfInfo;
    }

    public String loadJSONFromAsset(Context context, String index) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("file_" + index + ".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public Integer getPoster(JSONObject obj) throws JSONException {
        Integer poster = R.drawable.poster_null;
        String temp = (String) obj.get("Poster");
        if (temp.equals("poster01.jpg")){
            poster = R.drawable.poster01;
        }else if (temp.equals("poster02.jpg")){
            poster = R.drawable.poster02;
        }
        else if (temp.equals("poster03.jpg")){
            poster = R.drawable.poster03;
        }
        else if (temp.equals("poster05.jpg")){
            poster = R.drawable.poster05;
        }
        else if (temp.equals("poster06.jpg")){
            poster = R.drawable.poster06;
        }
        else if (temp.equals("poster07.jpg")){
            poster = R.drawable.poster07;
        }
        else if (temp.equals("poster08.jpg")){
            poster = R.drawable.poster08;
        }
        else if (temp.equals("poster10.jpg")){
            poster = R.drawable.poster10;
        }
        return poster;
    }

    private void setInitialData() throws JSONException {
        int end = 9;
        for (int i = 0; i <= end; i++) {
            JSONObject obj = new JSONObject(loadJSONFromAsset(this, String.valueOf(i)));
            Integer poster = getPoster(obj);
            states.add(new State (obj.getString("Title"), obj.getString("Year"),
                    obj.getString("Type"), poster, true, i));
        }
    }

    public Boolean compareStates(State state1, State state2){
        if(state1.getTitle().equals(state2.getTitle()) && state1.getType().equals(state2.getType())
                && state1.getYear().equals(state2.getYear()) && state1.getPoster() == state2.getPoster()
                && state1.getIndex().equals(state2.getIndex()) && state1.getIsInfo().equals(state2.getIsInfo())){
            return true;
        }
        return false;
    }

    public State deletedItem(List<State> listBefore, List<State> listAfter){
        boolean isExits = false;
        for (State before: listBefore){
            for (State after: listAfter){
                if (compareStates(before, after)){
                    isExits = true;
                }
            }
            if (!isExits){
                return before;
            }
            isExits = false;
        }
        return null;
    }

    public List<State> removeState(List<State> list, State state){
        List<State> temp = new ArrayList<>();
        boolean deleted = false;
        for (State listElem: list){
            if (compareStates(listElem, state) && !deleted){
                deleted = true;
            } else {
                temp.add(listElem);
            }
        }
        return temp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setMaxWidth( Integer.MAX_VALUE );
        searchView.setOnQueryTextListener(this);

        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                RecyclerViewAdapter.mStatesCopy = new ArrayList<>(states);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        recyclerViewAdapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerViewAdapter.getFilter().filter(newText);
        return true;
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            List<State> tempList = new ArrayList<>(states);
            states.remove(viewHolder.getAdapterPosition());
            State remove = deletedItem(tempList, states);
            if (remove != null){
                RecyclerViewAdapter.mStatesCopy = removeState(RecyclerViewAdapter.mStatesCopy, remove);
            }
            recyclerViewAdapter.notifyDataSetChanged();
        }
    };
}