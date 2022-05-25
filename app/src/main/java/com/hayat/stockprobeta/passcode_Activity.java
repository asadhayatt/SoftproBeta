package com.hayat.stockprobeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class passcode_Activity extends AppCompatActivity {

    PasscodeView passcodeView;
    Button forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        passcodeView = findViewById(R.id.passcodeView);
        forgot       = findViewById(R.id.forgot);




        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(passcode_Activity.this,forgot_pin.class));
            finish();
            }
        });
        DBHandler db = new DBHandler(this);
        ArrayList <String> pin1 = db.backup_code();
        String pin     =   pin1.get(1);
        passcodeView.setPasscodeLength(5)
                .setLocalPasscode(pin)
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(passcode_Activity.this,ADDpurchase.class));

                        finish();

                    }
                });
    }
}