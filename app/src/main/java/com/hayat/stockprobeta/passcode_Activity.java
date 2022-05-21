package com.hayat.stockprobeta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hanks.passcodeview.PasscodeView;

public class passcode_Activity extends AppCompatActivity {

    PasscodeView passcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        passcodeView = findViewById(R.id.passcodeView);

        passcodeView.setPasscodeLength(5)
                .setLocalPasscode("12345")
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(passcode_Activity.this, "Enter correct Passcode!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(passcode_Activity.this,ADDpurchase.class));
                        String pinCode = passcodeView.getLocalPasscode();
                        Toast.makeText(passcode_Activity.this, "Your PinCode"+pinCode, Toast.LENGTH_SHORT).show();

                        finish();

                    }
                });
    }
}