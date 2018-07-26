package org.atctech.sms_teacher;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.atctech.sms_teacher.ApiRequest.ApiRequest;
import org.atctech.sms_teacher.model.ClassNameID;
import org.atctech.sms_teacher.utils.FilePath;
import org.atctech.sms_teacher.utils.RetrofitInterface;

import java.io.File;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiveResultActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonChoose;
    private Button buttonUpload;
    String PdfPathHolder;
    private TextView tvFileName;
    private CoordinatorLayout layout;
    private int PICK_PDF_REQUEST = 777;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Uri filePath;
    private ProgressDialog dialog;
    private Spinner subjectNameSpinner,ClassNameSpinner;
    private ApiRequest service;
    private  List<ClassNameID> classNameIDS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_live_result);


        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] subjectNames = {"Bangla 1st Paper","Bangla 2nd Paper","English 1st Paper","English 2nd Paper","General Math","Higher Math","Social Science"};

        requestStoragePermission();

        layout = findViewById(R.id.coordinator_layout);
        buttonChoose = findViewById(R.id.chooseFileBtn);
        buttonUpload = findViewById(R.id.resultSubmitBtn);
        tvFileName =  findViewById(R.id.uploadingFileName);
        subjectNameSpinner =  findViewById(R.id.subjectNameSpinner);
        ClassNameSpinner =  findViewById(R.id.classNameSpinner);


        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        dialog = new ProgressDialog(LiveResultActivity.this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,subjectNames);
        subjectNameSpinner.setAdapter(arrayAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://esrattanisha85.000webhostapp.com/sms/api/teacher/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            service = retrofit.create(ApiRequest.class);
            Call<List<ClassNameID>> classCall = service.getAllClassName();

            classCall.enqueue(new Callback<List<ClassNameID>>() {
                @Override
                public void onResponse(Call<List<ClassNameID>> call, Response<List<ClassNameID>> response) {
                    if (response.isSuccessful())
                    {
                        classNameIDS = response.body();
                        String[] names;
                        if (classNameIDS != null) {
                            names = new String[classNameIDS.size()];

                            for (int i = 0; i < classNameIDS.size(); i++) {
                                names[i] = classNameIDS.get(i).getName();
                            }

                            ArrayAdapter<String> classAdapter = new ArrayAdapter<>(LiveResultActivity.this,android.R.layout.simple_spinner_dropdown_item,names);
                            ClassNameSpinner.setAdapter(classAdapter);
                            
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<ClassNameID>> call, Throwable t) {

                }
            });

    }

    public void uploadMultipart() {


        if (PdfPathHolder==null)
        {
            Snackbar snackbar = Snackbar
                    .make(layout, "Please Select a File", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }else {
            dialog.setTitle("Uploading");
            dialog.setMessage("Please wait......");
            dialog.show();

            int position = ClassNameSpinner.getSelectedItemPosition();
            String classID = classNameIDS.get(position).getId();

            File file = new File(PdfPathHolder);
            RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            RequestBody subject = RequestBody.create(MediaType.parse("text/plain"), subjectNameSpinner.getSelectedItem().toString());
            RequestBody class_id = RequestBody.create(MediaType.parse("text/plain"), classID);
            ApiRequest apiInterface = RetrofitInterface.getApiClient().create(ApiRequest.class);
            Call<ResponseBody> call = apiInterface.PdfUploadFunction(body, subject, class_id);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        Toast.makeText(LiveResultActivity.this, "success", Toast.LENGTH_SHORT).show();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(LiveResultActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(LiveResultActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }



    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        String[] mimetypes = {"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        }
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
        }catch (ActivityNotFoundException ex){
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            String uriString = filePath.toString();
            File myFile = new File(uriString);
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getContentResolver().query(filePath, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        boolean isValid = checkValidity(displayName);
                        if (!isValid)
                        {
                            PdfPathHolder = null;
                            tvFileName.setText("");
                            displayName = null;
                        }
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }

            if (displayName!=null) {
                PdfPathHolder = FilePath.getPath(this, filePath);
                tvFileName.setText(displayName);
            }else
            {
                Toast.makeText(this, "Please Select a valid file", Toast.LENGTH_SHORT).show();
            }
//            filePath = data.getData();
//            PdfPathHolder = FilePath.getPath(this, filePath);
//            if(PdfPathHolder != null && !PdfPathHolder.equals("")) {
//                String file_name = PdfPathHolder.substring(PdfPathHolder.lastIndexOf("/"));
//                tvFileName.setText(file_name);
//            }
        }
    }


    private Boolean checkValidity(String filePath){
        if(!TextUtils.isEmpty(filePath)) {
            String filePathInLowerCase = filePath.toLowerCase();
            if(filePathInLowerCase.endsWith(".pdf") || filePathInLowerCase.endsWith(".docx") || filePathInLowerCase.endsWith(".doc")) {
                return true;
            }
        }
        return false;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            uploadMultipart();
        }
    }

}
