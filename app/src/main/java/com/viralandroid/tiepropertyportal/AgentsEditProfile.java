package com.viralandroid.tiepropertyportal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;

/**
 * Created by T on 12-05-2017.
 */

public class AgentsEditProfile extends Activity {
    ImageView close_btn,user_image;
    EditText fname,lname,address,state,phone;
    TextView submit_btn;
    String agent_id;
    int ASK_MULTIPLE_PERMISSION_REQUEST_CODE;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_edit_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        close_btn = (ImageView) findViewById(R.id.close_btn);
        user_image = (ImageView) findViewById(R.id.user_image);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        address = (EditText) findViewById(R.id.address);
        state = (EditText) findViewById(R.id.state);
        phone = (EditText) findViewById(R.id.phone);
        submit_btn = (TextView) findViewById(R.id.submit_btn);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_images();
            }
        });

        if (getIntent()!=null && getIntent().hasExtra("agentsId")){
            agent_id = getIntent().getStringExtra("agentsId");
            Log.e("agents",agent_id);
        }

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname_string = fname.getText().toString();
                String lname_string = lname.getText().toString();
                String address_string = address.getText().toString();
                String state_string = state.getText().toString();
                String phone_string = phone.getText().toString();
                if (fname_string.equals("")){
                    Toast.makeText(AgentsEditProfile.this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
                    fname.requestFocus();
                }else if (lname_string.equals("")){
                    Toast.makeText(AgentsEditProfile.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
                    lname.requestFocus();
                }else if (address_string.equals("")){
                    Toast.makeText(AgentsEditProfile.this,"Please Enter Adddress",Toast.LENGTH_SHORT).show();
                    address.requestFocus();
                }else if (state_string.equals("")){
                    Toast.makeText(AgentsEditProfile.this,"Please Enter State",Toast.LENGTH_SHORT).show();
                    state.requestFocus();
                }else if (phone_string.equals("")){
                    Toast.makeText(AgentsEditProfile.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(AgentsEditProfile.this);
                    progressDialog.setMessage("please wait..");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Ion.with(AgentsEditProfile.this)
                            .load(Session.SERVER_URL+"edit-agent.php")
                            .setBodyParameter("agent_id",Session.GetUserId(getApplicationContext()))
                            .setBodyParameter("fname",fname_string)
                            .setBodyParameter("lname",lname_string)
                            .setBodyParameter("address",address_string)
                            .setBodyParameter("state",state_string)
                            .setBodyParameter("phone",phone_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (progressDialog!=null)
                                        progressDialog.dismiss();
                                    if (result.get("status").getAsString().equals("Success")){
                                        if(selected_image_path.equals("")){
                                            edit_success();
                                        }else {
                                            upload_image();
                                        }
                                    }else {
                                        Toast.makeText(AgentsEditProfile.this,result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        get_agent_details();

    }

    public void show_images(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(AgentsEditProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
                return;
            }
        }
        final CharSequence[] items = {"camera","gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select_image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(items[item].equals("camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);

                }else if(items[item].equals("gallery")){
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto,1);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    String selected_image_path = "";
    protected void onActivityResult(int requestCode,int resultCode,Intent imageReturnedIntent){
        super.onActivityResult(requestCode,resultCode,imageReturnedIntent);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    user_image.setImageURI(selectedImage);
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);

                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    user_image.setImageURI(selectedImage);
                    File new_file = new File(selectedImage.getPath());
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);

                }
                break;
        }
    }

    private void upload_image(){
        final ProgressBar progressBar = new ProgressBar(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait image is loading...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgress(0);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"agent-image.php")
                .uploadProgressBar(progressBar)
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e(String.valueOf(downloaded),String.valueOf(total));
                        progressDialog.setMax((int)total);
                        progressDialog.setProgress((int) downloaded);
                    }
                })
                .setMultipartParameter("agent_id", Session.GetUserId(AgentsEditProfile.this))
                .setMultipartFile("file", "image/png", new File(selected_image_path))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if(progressDialog!=null)
                            progressDialog.dismiss();
                        if(e!=null)
                            e.printStackTrace();
                        else {
                            if(result.isJsonNull())
                                Log.e("json_null", "null");
                            else {
                                Log.e("image_upload_res", result.toString());
                                edit_success();
                            }

                        }

                    }
                });
    }


    private String getRealPathFromURI(Uri contentURI){
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void edit_success(){
        Toast.makeText(AgentsEditProfile.this,"Profile edited Successfully",Toast.LENGTH_SHORT).show();
        AgentsEditProfile.this.onBackPressed();
    }

    public void get_agent_details(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(AgentsEditProfile.this)
                .load(Session.SERVER_URL+"agents.php")
                .setBodyParameter("agent_id",Session.GetUserId(AgentsEditProfile.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        try {
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            fname.setText(jsonObject.get("fname").getAsString());
                            lname.setText(jsonObject.get("lname").getAsString());
                            address.setText(jsonObject.get("address").getAsString());
                            state.setText(jsonObject.get("state").getAsString());
                            phone.setText(jsonObject.get("phone").getAsString());
                            Ion.with(AgentsEditProfile.this)
                                    .load(jsonObject.get("image").getAsString())
                                    .withBitmap()
                                    .placeholder(R.drawable.edit_user)
                                    .intoImageView(user_image);
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });
    }

}
