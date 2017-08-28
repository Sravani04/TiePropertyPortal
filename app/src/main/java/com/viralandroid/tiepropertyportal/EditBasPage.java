package com.viralandroid.tiepropertyportal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by T on 25-05-2017.
 */

public class EditBasPage extends Activity {
    EditText fname,lname,email,password,address,state,phone,aadhar,pan_card;
    ImageView back_btn,item_image;
    TextView city,update_btn,status;
    int ASK_MULTIPLE_PERMISSION_REQUEST_CODE;
    String city_id;
    ArrayList<Cities> citiesfrom_api;
    Agents agents;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_bas_page);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        item_image = (ImageView) findViewById(R.id.item_image);
        address = (EditText) findViewById(R.id.address);
        city = (TextView) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        phone = (EditText) findViewById(R.id.phone);
        aadhar = (EditText) findViewById(R.id.aadhar);
        pan_card = (EditText) findViewById(R.id.pan_card);
        update_btn = (TextView) findViewById(R.id.update_btn);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        status = (TextView) findViewById(R.id.status);
        citiesfrom_api = new ArrayList<>();

        if (getIntent()!=null && getIntent().hasExtra("agents")){
            agents = (Agents) getIntent().getSerializableExtra("agents");
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBasPage.this.onBackPressed();
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog  = onCreateDialogSingleChoice();
                dialog.show();
            }
        });

        item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_images();
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = getStatus();
                dialog.show();
            }
        });

        fname.setText(agents.fname);
        lname.setText(agents.lname);
        email.setText(agents.email);
        address.setText(agents.address);
        city.setText(agents.city_id);
        state.setText(agents.state);
        phone.setText(agents.phone);
        aadhar.setText(agents.aadhar);
        pan_card.setText(agents.pancard);
        Picasso.with(EditBasPage.this).load(agents.image).placeholder(R.drawable.placeholder_person).into(item_image);



        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname_string = fname.getText().toString();
                String lastname_string = lname.getText().toString();
                String email_string = email.getText().toString();
                String password_string = password.getText().toString();
                String address_string = address.getText().toString();
                String city_string = city_id;
                String state_string = state.getText().toString();
                String phone_string = phone.getText().toString();
                String aadhar_string = aadhar.getText().toString();
                String pancard_string =pan_card.getText().toString();
                String status_string = status.getText().toString();
                if (firstname_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter First Name",Toast.LENGTH_SHORT).show();
                    fname.requestFocus();
                }else if (lastname_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
                    lname.requestFocus();
                }else if (email_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if (password_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }else if (address_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter Address",Toast.LENGTH_SHORT).show();
                    address.requestFocus();
                }else if (city_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter City",Toast.LENGTH_SHORT).show();
                    city.requestFocus();
                }else if (state_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter State",Toast.LENGTH_SHORT).show();
                    state.requestFocus();
                }else if (phone_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter Phone",Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                }else if (aadhar_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter Aadhar number",Toast.LENGTH_SHORT).show();
                    aadhar.requestFocus();
                }else if (pancard_string.equals("")){
                    Toast.makeText(EditBasPage.this,"Please Enter PanCard number",Toast.LENGTH_SHORT).show();
                    pan_card.requestFocus();
                }else {
                    Ion.with(getApplicationContext())
                            .load(Session.SERVER_URL+"edit-ba.php")
                            .setBodyParameter("agent_id",Session.GetUserId(EditBasPage.this))
                            .setBodyParameter("fname",firstname_string)
                            .setBodyParameter("lname",lastname_string)
                            .setBodyParameter("email",email_string)
                            .setBodyParameter("password",password_string)
                            .setBodyParameter("address",address_string)
                            .setBodyParameter("city",city_string)
                            .setBodyParameter("state",state_string)
                            .setBodyParameter("phone",phone_string)
                            .setBodyParameter("aadhar",aadhar_string)
                            .setBodyParameter("pancard",pancard_string)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("status").getAsString().equals("Success")){
                                        Log.e("resulr",result.toString());
                                        if (selected_image_path.equals("")){
                                            add_success();
                                        }else {
                                            upload_image();
                                        }
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),result.get("message").getAsString(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

       // get_agents();
        get_cities();

    }

    public void get_agents(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"ba-list.php")
                .setBodyParameter("agent_id",Session.GetUserId(EditBasPage.this))
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
                            email.setText(jsonObject.get("email").getAsString());
                            address.setText(jsonObject.get("address").getAsString());
                            city.setText(jsonObject.get("city").getAsJsonObject().get("title").getAsString());
                            state.setText(jsonObject.get("state").getAsString());
                            phone.setText(jsonObject.get("phone").getAsString());
                            aadhar.setText(jsonObject.get("aadhar").getAsString());
                            pan_card.setText(jsonObject.get("pancard").getAsString());

                            status.setText(jsonObject.get("status").getAsString());
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });

    }


    public void show_images(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(EditBasPage.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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
                    item_image.setImageURI(selectedImage);
                    selected_image_path = getRealPathFromURI(selectedImage);
                    Log.e("image_path",selected_image_path);

                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    item_image.setImageURI(selectedImage);
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
                .setMultipartParameter("agent_id", Session.GetUserId(EditBasPage.this))
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
                                add_success();
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

    private void add_success(){
        Toast.makeText(EditBasPage.this,"Profile edited Successfully",Toast.LENGTH_SHORT).show();
        EditBasPage.this.onBackPressed();
    }

    public void get_cities(){
        Ion.with(this)
                .load(Session.SERVER_URL+"cities.php")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            for (int i=0;i<result.size();i++){
                                Log.e("response",result.get(i).toString());
                                Cities cities = new Cities(result.get(i).getAsJsonObject(),EditBasPage.this);
                                citiesfrom_api.add(cities);
                            }

                        }catch (Exception e1){
                            e1.printStackTrace();
                        }

                    }
                });
    }

    public Dialog onCreateDialogSingleChoice() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = new CharSequence[citiesfrom_api.size()];
        for(int i=0;i<citiesfrom_api.size();i++){

            array[i] = citiesfrom_api.get(i).title;
        }
        builder.setTitle("Select City").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                city.setText(selectedItem);
                city_id = citiesfrom_api.get(i).id;

            }
        })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

    public Dialog getStatus() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final CharSequence[] array = {"Active","Inactive"};
        builder.setTitle("Select Status").setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int i) {
                String selectedItem = array[i].toString();
                Log.e("select",selectedItem);
                status.setText(selectedItem);

            }
        })

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }

}
