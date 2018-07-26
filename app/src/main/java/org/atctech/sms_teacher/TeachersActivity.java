package org.atctech.sms_teacher;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.atctech.sms_teacher.ApiRequest.ApiRequest;
import org.atctech.sms_teacher.adapter.TeacherAdapter;
import org.atctech.sms_teacher.model.TeacherDetails;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeachersActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ApiRequest service;
    RecyclerView recyclerView;
    List<TeacherDetails> teacherDetails;
    TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teachers);

        getSupportActionBar().setTitle("Teachers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.teacherList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        Call<List<TeacherDetails>> listCall = service.getAllTeacher();

        listCall.enqueue(new Callback<List<TeacherDetails>>() {
            @Override
            public void onResponse(Call<List<TeacherDetails>> call, Response<List<TeacherDetails>> response) {
                if (response.isSuccessful())
                {
                    teacherDetails = response.body();
                    if (teacherDetails!=null) {
                        adapter = new TeacherAdapter(TeachersActivity.this, teacherDetails);
                        recyclerView.setLayoutManager(new GridLayoutManager(TeachersActivity.this,2));
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }else {

                }
            }

            @Override
            public void onFailure(Call<List<TeacherDetails>> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<TeacherDetails> filteredModelList = filter(teacherDetails, newText);
        adapter.setFilter(filteredModelList);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        final MenuItem item = menu.findItem(R.id.ic_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        adapter.setFilter(teacherDetails);
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });

        return super.onCreateOptionsMenu(menu);
    }

    private List<TeacherDetails> filter(List<TeacherDetails> models, String query) {
        query = query.toLowerCase();
        final List<TeacherDetails> filteredModelList = new ArrayList<>();
        for (TeacherDetails model : models) {
            final String text = model.getFname().toLowerCase()+" "+model.getLname().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
