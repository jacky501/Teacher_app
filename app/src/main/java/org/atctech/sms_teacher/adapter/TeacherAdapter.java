package org.atctech.sms_teacher.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.atctech.sms_teacher.R;
import org.atctech.sms_teacher.TeacherDetailsActivity;
import org.atctech.sms_teacher.customs.CircularImageView;
import org.atctech.sms_teacher.model.TeacherDetails;
import org.atctech.sms_teacher.utils.ConstantValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky on 3/5/2018.
 */

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>{

    private Context context;
    private List<TeacherDetails> teacherDetails;




    public TeacherAdapter(Context context, List<TeacherDetails> teacherDetails) {
        this.context = context;
        this.teacherDetails = teacherDetails;
    }

    @Override
    public TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_teacher,parent,false);

        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, final int position) {

       final TeacherDetails details = teacherDetails.get(position);

        holder.name.setText(details.getFname()+" "+details.getLname());


        try {
            if (details.getPro_pic().equalsIgnoreCase("") && details.getPro_pic().isEmpty()) {
                holder.teacherImage.setImageResource(R.drawable.profile);
            }else{
                Picasso.with(context).load(ConstantValue.IMAGE_URL+ details.getPro_pic()).placeholder(R.drawable.profile).into(holder.teacherImage);

            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }



        holder.teacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog localDialog = new Dialog(context);
                localDialog.requestWindowFeature(1);
                localDialog.setContentView(R.layout.dialog_book_img);
                localDialog.getWindow().setLayout(-1, -1);
                localDialog.show();
                ImageView localImageView1 = localDialog.findViewById(R.id.iv_dialog_img);
                ImageView localImageView2 = localDialog.findViewById(R.id.iv_dialog_cancle);

                try {
                    if (details.getPro_pic().equalsIgnoreCase("") && details.getPro_pic().isEmpty()) {
                        Picasso.with(context).load(R.drawable.profile).into(localImageView1);

                    }else{
                        Picasso.with(context).load(ConstantValue.IMAGE_URL+ details.getPro_pic()).placeholder(R.drawable.profile).into(localImageView1);

                    }
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }



                localImageView2.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View paramAnonymousView)
                    {
                        localDialog.dismiss();
                    }
                });
            }
        });

        holder.tDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bundle = new Intent(context, TeacherDetailsActivity.class);
                bundle.putExtra("fname", teacherDetails.get(position).getFname());
                bundle.putExtra("lname", teacherDetails.get(position).getLname());
                bundle.putExtra("email", teacherDetails.get(position).getEmail());
                bundle.putExtra("phone", teacherDetails.get(position).getPhone());
                bundle.putExtra("address", teacherDetails.get(position).getAddress());
                bundle.putExtra("details", teacherDetails.get(position).getDetails());
                bundle.putExtra("bdate", teacherDetails.get(position).getBdate());
                bundle.putExtra("blood", teacherDetails.get(position).getBlood());
                bundle.putExtra("sex", teacherDetails.get(position).getSex());
                bundle.putExtra("paddress", teacherDetails.get(position).getPaddress());
                bundle.putExtra("pro_pic", teacherDetails.get(position).getPro_pic());
               context.startActivity(bundle);
          }
        });
    }


    @Override
    public int getItemCount() {
        return (null != teacherDetails ? teacherDetails.size() : 0);
    }

    public void setFilter(List<TeacherDetails> teacherModel) {
        teacherDetails = new ArrayList<>();
        teacherDetails.addAll(teacherModel);
        notifyDataSetChanged();
    }


    public class TeacherViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        CircularImageView teacherImage;
        Button tDetails;


        public TeacherViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            teacherImage = itemView.findViewById(R.id.profile_image);
            tDetails = itemView.findViewById(R.id.teacherDetails);

        }
    }

}
