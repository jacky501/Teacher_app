package org.atctech.sms_teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.atctech.sms_teacher.utils.ConstantValue;

import static java.security.AccessController.getContext;

public class TeacherDetailsActivity extends AppCompatActivity {

    private TextView fullName,email,phone,address,details,bdate,blood,sex,paddress;
    private ImageView tproImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_teacher_details);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fullName = findViewById(R.id.tFullName);
        email = findViewById(R.id.tEmail);
        phone = findViewById(R.id.tPhoneNo);
        address = findViewById(R.id.tAddress);
        details = findViewById(R.id.tDetails);
        bdate = findViewById(R.id.tbDate);
        blood = findViewById(R.id.tbloodGroup);
        sex = findViewById(R.id.tSex);
        paddress = findViewById(R.id.tPermanentAddress);
        tproImage = findViewById(R.id.t_image);

        Bundle bundle = getIntent().getExtras();

        String fname = bundle.getString("fname");
        String lname = bundle.getString("lname");
        String temail = bundle.getString("email");
        String tphone = bundle.getString("phone");
        String taddress = bundle.getString("address");
        String tdetails = bundle.getString("details");
        String tbdate = bundle.getString("bdate");
        String tblood = bundle.getString("blood");
        String tsex = bundle.getString("sex");
        String tpaddress = bundle.getString("paddress");
        String tpro_pic = bundle.getString("pro_pic");


        try {
            if (tpro_pic.isEmpty() && tpro_pic.equalsIgnoreCase("") && tpro_pic == null)
            {
                Picasso.with(this).load(R.drawable.profile).into(tproImage);
            }else {
                Picasso.with(this).load(ConstantValue.IMAGE_URL+tpro_pic).placeholder(R.drawable.profile).into(tproImage);
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
}
