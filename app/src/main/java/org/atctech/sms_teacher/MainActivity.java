package org.atctech.sms_teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import org.atctech.sms_teacher.preferences.Session;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView dashboard,profile,teachers,liveresults,notices;
    private Session session;
    DrawerLayout drawerLayout;
    public static ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session = Session.getInstance(getSharedPreferences("prefs",Context.MODE_PRIVATE));

        dashboard = findViewById(R.id.dashboard);
        profile = findViewById(R.id.profile);
        teachers = findViewById(R.id.teachers);
        liveresults = findViewById(R.id.liveResults);
        notices = findViewById(R.id.notices);
        drawerLayout = findViewById(R.id.drawer_layout);

        dashboard.setOnClickListener(this);
        profile.setOnClickListener(this);
        teachers.setOnClickListener(this);
        liveresults.setOnClickListener(this);
        notices.setOnClickListener(this);

//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, getSupportActionBar(), R.string.drawerOpen, R.string.drawerClose) {
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//
//                invalidateOptionsMenu();
//            }
//        };


//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//
//        actionBarDrawerToggle.syncState();



    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId())
        {
            case R.id.dashboard:
                i = new Intent(MainActivity.this,DashboardActivity.class);
                startActivity(i);
                break;

            case R.id.profile:
                i = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(i);
                break;

            case R.id.teachers:
                i = new Intent(MainActivity.this,TeachersActivity.class);
                startActivity(i);
                break;

            case R.id.liveResults:
                i = new Intent(MainActivity.this,LiveResultActivity.class);
                startActivity(i);
                break;

            case R.id.notices:
                i = new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(i);
                break;

                default:
                    break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        final MenuItem item = menu.findItem(R.id.signOut);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Sign Out")
                        .setIcon(R.drawable.ic_exit_to_app_black)
                        .setMessage("Do you really want to sign out ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                session.deleteTeacher();
                                session.setLoggedIn(false);
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_out_right);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();



                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
