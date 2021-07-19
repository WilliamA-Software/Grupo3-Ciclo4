package com.example.luma.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luma.R;
import com.example.luma.ui.DrawerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermsConditions extends AppCompatActivity {

    private FloatingActionButton btn_fab, btn_fab2;
    private TextView tv_terms, tv_privacy_policies;
    private TextView terms_conditions, privacy_policies;
    private Button btn_back;
    private Activity mySelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        mySelf = this;
        tv_terms = findViewById(R.id.tv_terms);
        tv_privacy_policies = findViewById(R.id.tv_privacy_policies);
        terms_conditions = findViewById(R.id.terms_conditions);
        privacy_policies = findViewById(R.id.privacy_policies);
        btn_back = findViewById(R.id.btn_back);
        btn_fab = findViewById(R.id.fab);
        btn_fab2 = findViewById(R.id.fab2);

        tv_terms.setVisibility(View.VISIBLE);
        terms_conditions.setVisibility(View.VISIBLE);
        btn_fab.setVisibility(View.VISIBLE);

        tv_privacy_policies.setVisibility(View.GONE);
        privacy_policies.setVisibility(View.GONE);
        btn_fab2.setVisibility(View.GONE);

        btn_fab.setOnClickListener(v -> {

            tv_terms.setVisibility(View.GONE);
            terms_conditions.setVisibility(View.GONE);
            btn_fab.setVisibility(View.GONE);

            tv_privacy_policies.setVisibility(View.VISIBLE);
            privacy_policies.setVisibility(View.VISIBLE);
            btn_fab2.setVisibility(View.VISIBLE);


        });

        btn_fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_terms.setVisibility(View.VISIBLE);
                terms_conditions.setVisibility(View.VISIBLE);
                btn_fab.setVisibility(View.VISIBLE);

                tv_privacy_policies.setVisibility(View.GONE);
                privacy_policies.setVisibility(View.GONE);
                btn_fab2.setVisibility(View.GONE);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent mainActivity = new Intent(mySelf, DrawerActivity.class);
//                startActivity(mainActivity);
                finish();
            }
        });

    }
}