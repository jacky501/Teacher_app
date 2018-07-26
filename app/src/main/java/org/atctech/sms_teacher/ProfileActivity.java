package org.atctech.sms_teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.atctech.sms_teacher.preferences.Session;
import org.atctech.sms_teacher.utils.ConstantValue;

public class ProfileActivity extends AppCompatActivity {

    private boolean isOpen = false ;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout ;
    private TextView fullName,email,phone,address,details,bdate,blood,sex,paddress;
    private ImageView tproImage,tCoverImage;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = Session.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));

        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        tproImage = findViewById(R.id.photo);
        constraintLayout = findViewById(R.id.constraint_layout);
        layout2.clone(this,R.layout.profile_expended);
        layout1.clone(constraintLayout);

        tproImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (!isOpen) {
                        TransitionManager.beginDelayedTransition(constraintLayout);
                        layout2.applyTo(constraintLayout);
                        isOpen = !isOpen;
                    } else {

                        TransitionManager.beginDelayedTransition(constraintLayout);

                        layout1.applyTo(constraintLayout);
                        isOpen = !isOpen;

                    }

                }

            }
        });

        fullName = findViewById(R.id.tFullName);
        email = findViewById(R.id.tEmail);
        phone = findViewById(R.id.tPhoneNo);
        address = findViewById(R.id.tAddress);
        details = findViewById(R.id.tDetails);
        bdate = findViewById(R.id.tbDate);
        blood = findViewById(R.id.tbloodGroup);
        sex = findViewById(R.id.tSex);
        paddress = findViewById(R.id.tPermanentAddress);
        tproImage = findViewById(R.id.photo);
        tCoverImage = findViewById(R.id.cover);

        String fname = session.getTeacher().getFname();
        String lname = session.getTeacher().getLname();
        String temail = session.getTeacher().getEmail();
        String tphone = session.getTeacher().getPhone();
        String taddress = session.getTeacher().getAddress();
        String tdetails = session.getTeacher().getDetails();
        String tbdate = session.getTeacher().getBdate();
        String tblood = session.getTeacher().getBlood();
        String tsex = session.getTeacher().getSex();
        String tpaddress = session.getTeacher().getPaddress();
        String tpro_pic = session.getTeacher().getPro_pic();


        try {
            if (tpro_pic.isEmpty() && tpro_pic.equalsIgnoreCase("") && tpro_pic == null)
            {
                Picasso.with(ProfileActivity.this).load(R.drawable.profile).into(tCoverImage);
                Picasso.with(ProfileActivity.this).load(R.drawable.profile).into(tproImage);
            }else {
                Picasso.with(ProfileActivity.this).load(ConstantValue.IMAGE_URL+tpro_pic).placeholder(R.drawable.profile).into(tproImage);
                Picasso.with(ProfileActivity.this).load(ConstantValue.IMAGE_URL+tpro_pic).placeholder(R.drawable.profile).into(tCoverImage);
            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }


        fullName.setText(fname+" "+lname);
        email.setText(temail);
        phone.setText(tphone);
        address.setText(taddress);
        details.setText(tdetails);
        bdate.setText(tbdate);
        blood.setText(tblood);
        sex.setText(tsex);
        paddress.setText(tpaddress);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        final MenuItem item = menu.findItem(R.id.profile_edit);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
