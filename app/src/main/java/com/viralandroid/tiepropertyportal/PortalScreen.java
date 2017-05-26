package com.viralandroid.tiepropertyportal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by T on 25-05-2017.
 */

public class PortalScreen extends Activity {
    TextView login_btn,register_btn;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portal_screen);
        login_btn = (TextView) findViewById(R.id.login_btn);
        register_btn = (TextView) findViewById(R.id.register_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(PortalScreen.this,LoginPage.class);
                startActivity(login);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(PortalScreen.this,AgentsRegisterPage.class);
                startActivity(register);
            }
        });


    }
}
