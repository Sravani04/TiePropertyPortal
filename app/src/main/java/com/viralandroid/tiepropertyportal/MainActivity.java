package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ImageView back_btn,agent_image;
    TextView agent_name,agent_code,level_title;
    LinearLayout agents,callback_customers,commissions,new_property,properties,site_visits,logout_btn,
            edit_btn,reports_list,ba_line,offer_line,new_property_line,home_laons,
            sales_list,sales_list_line,ba_site_visit_line,bas_site_vists,trending_properties;
    String name,image;
    String comm_amt,tds,pay,site,ded,tot,paid,bal,agent,id;
    String first_name,last_name,email,password,address,city_title,state,phone,aadhar,pan;
    int backButtonCount;
    ArrayList<Cities>citiesfrom_api;
    CityAdapter cityAdapter;
    String city_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_btn = (LinearLayout) findViewById(R.id.edit_btn);
        agents = (LinearLayout) findViewById(R.id.agents);
        callback_customers = (LinearLayout) findViewById(R.id.callback_customers);
        commissions = (LinearLayout) findViewById(R.id.commissions);
        new_property = (LinearLayout) findViewById(R.id.new_property);
        properties = (LinearLayout) findViewById(R.id.properties);
        site_visits = (LinearLayout) findViewById(R.id.site_visits);
        agent_name = (TextView) findViewById(R.id.agent_name);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        logout_btn = (LinearLayout) findViewById(R.id.logout_btn);
        agent_image = (ImageView) findViewById(R.id.agent_image);
        reports_list = (LinearLayout) findViewById(R.id.reports_list);
        ba_line = (LinearLayout) findViewById(R.id.ba_line);
        offer_line = (LinearLayout) findViewById(R.id.offer_line);
        new_property_line = (LinearLayout) findViewById(R.id.new_property_line);
        home_laons = (LinearLayout) findViewById(R.id.home_loans);
        sales_list = (LinearLayout) findViewById(R.id.sales_list);
        sales_list_line = (LinearLayout) findViewById(R.id.sales_list_line);
        ba_site_visit_line = (LinearLayout) findViewById(R.id.ba_site_visit_line);
        bas_site_vists = (LinearLayout) findViewById(R.id.bas_site_vists);
        trending_properties = (LinearLayout) findViewById(R.id.trending_properties);
        agent_code = (TextView) findViewById(R.id.agent_code);
        level_title = (TextView)  findViewById(R.id.level_title);

        citiesfrom_api = new ArrayList<>();

        if (getIntent()!=null && getIntent().hasExtra("agent_name")){
            id = getIntent().getStringExtra("agentId");
            Log.e("agent",id);
            name = getIntent().getStringExtra("agent_name");
            image = getIntent().getStringExtra("agent_image");
            comm_amt = getIntent().getStringExtra("tot_amt");
            tds = getIntent().getStringExtra("tds_amt");
            pay = getIntent().getStringExtra("pay_amt");
            site = getIntent().getStringExtra("site_visit");
            ded = getIntent().getStringExtra("ded_visit");
            tot = getIntent().getStringExtra("total_amt");
            paid = getIntent().getStringExtra("paid_amt");
            bal = getIntent().getStringExtra("bal_amt");
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onBackPressed();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.SetUserId(MainActivity.this,"-1");
                Intent intent = new Intent(MainActivity.this,PortalScreen.class);
                startActivity(intent);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AgentsEditProfile.class);
                startActivity(intent);
            }
        });

        if (Session.GetLevelId(MainActivity.this).equals("1")){
            agents.setVisibility(View.VISIBLE);
            ba_line.setVisibility(View.VISIBLE);
            reports_list.setVisibility(View.VISIBLE);
            new_property.setVisibility(View.VISIBLE);
            new_property_line.setVisibility(View.VISIBLE);
            sales_list.setVisibility(View.VISIBLE);
            sales_list_line.setVisibility(View.VISIBLE);
            bas_site_vists.setVisibility(View.VISIBLE);
            ba_site_visit_line.setVisibility(View.VISIBLE);

        }else {
            agents.setVisibility(View.GONE);
            ba_line.setVisibility(View.GONE);
            reports_list.setVisibility(View.VISIBLE);
            new_property.setVisibility(View.GONE);
            new_property_line.setVisibility(View.GONE);
            sales_list.setVisibility(View.GONE);
            sales_list_line.setVisibility(View.GONE);
            bas_site_vists.setVisibility(View.GONE);
            ba_site_visit_line.setVisibility(View.GONE);
        }

        agents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AgentsPage.class);
                startActivity(intent);
            }
        });

        callback_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CallbackCustomersPage.class);
                startActivity(intent);
            }
        });

        new_property.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NewPropertyScreen.class);
                startActivity(intent);
            }
        });

        site_visits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SiteVisitScreen.class);
                startActivity(intent);
            }
        });

        commissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,CommissionsPage.class);
                startActivity(intent);

            }
        });

        reports_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ReportListActivity.class);
                startActivity(intent);
            }
        });

        properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent properties = new Intent(MainActivity.this,PropertiesListActivity.class);
                properties.putExtra("id",city_id);
                startActivity(properties);

            }
        });

        home_laons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HomeLoansPage.class);
                startActivity(intent);
            }
        });

        sales_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SalesListActivity.class);
                startActivity(intent);
            }
        });

        trending_properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_trending_properties();
            }
        });

        bas_site_vists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,BASVisitsScreen.class);
                startActivity(intent);
            }
        });


        JsonParser jsonParser = new JsonParser();
        if (!Session.GetCities(MainActivity.this).equals("-1")){
            JsonArray jsonArray = (JsonArray) jsonParser.parse(Session.GetCities(MainActivity.this));
            for (int i=0;i<jsonArray.size();i++){
                Cities cities = new Cities(jsonArray.get(i).getAsJsonObject(),MainActivity.this);
                citiesfrom_api.add(cities);
            }
        }




        get_agents();
        onBackPressed();


    }




    public void get_agents(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(this)
                .load(Session.SERVER_URL+"agents.php")
                .setBodyParameter("agent_id",Session.GetUserId(MainActivity.this))
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            JsonObject jsonObject = result.get(0).getAsJsonObject();
                            agent_name.setText(jsonObject.get("fname").getAsString() +" "+ jsonObject.get("lname").getAsString());
                            agent_code.setText(jsonObject.get("code").getAsString());
                            Ion.with(MainActivity.this).load(jsonObject.get("image").getAsString()).withBitmap().placeholder(R.drawable.placeholder_person).intoImageView(agent_image);
                            level_title.setText( jsonObject.get("level").getAsJsonObject().get("title").getAsString());
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                });

    }


    private void show_trending_properties(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);


// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater=null;
        inflater = (LayoutInflater) MainActivity.this.
                getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.select_city_popup, null);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        TextView pop_title = (TextView) dialogView.findViewById(R.id.pop_up_title);
        ListView listView = (ListView) dialogView.findViewById(R.id.city_list);
        cityAdapter = new CityAdapter(MainActivity.this,citiesfrom_api);

        listView.setAdapter(cityAdapter);
        pop_title.setText("Select City");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                city_id = citiesfrom_api.get(i).id;

                Log.e("came","came");
                Intent intent = new Intent(MainActivity.this, TrendingPropertyActivity.class);
                intent.putExtra("id",citiesfrom_api.get(i).id);
                intent.putExtra("title",citiesfrom_api.get(i).title);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });

        final EditText search_city = (EditText) dialogView.findViewById(R.id.search_city);
        TextView save = (TextView) dialogView.findViewById(R.id.pop_save_btn);
        TextView cancel = (TextView) dialogView.findViewById(R.id.pop_cancel_btn);
        LinearLayout main_cat_select = (LinearLayout) dialogView.findViewById(R.id.select_product_category);
        main_cat_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });

        search_city.setText("");

        search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(cityAdapter!=null)
                    cityAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });



        cityAdapter.notifyDataSetChanged();


        alertDialog.show();


    }



    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            //Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }




}
