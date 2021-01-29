package com.example.morina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.morina.api.Data;
import com.example.morina.api.OnResponseListener;
import com.example.morina.api.Requests;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity implements OnResponseListener {

    private boolean login = false;
    private Requests requests;

    private final static int REQUEST_CODE_LOGIN = 1;
    private final static int REQUEST_CODE_SIGN_UP = 2;
    private final static int REQUEST_CODE_LOGOUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        requests = new Requests(this);
    }

    public void onLoginClick(View view) {
        if (login) {
            this.<TextView>findViewById(R.id.title).setText(R.string.login);
            this.<TextView>findViewById(R.id.button_title).setText(R.string.register);
            findViewById(R.id.block_email).setVisibility(View.GONE);
            this.<FloatingActionButton>findViewById(R.id.button_proceed)
                    .setOnClickListener(this::onLoginProceed);
        } else {
            this.<TextView>findViewById(R.id.title).setText(R.string.register);
            this.<TextView>findViewById(R.id.button_title).setText(R.string.login);
            findViewById(R.id.block_email).setVisibility(View.VISIBLE);
            this.<FloatingActionButton>findViewById(R.id.button_proceed)
                    .setOnClickListener(this::onRegisterProceed);
        }
        login = !login;

        JSONObject json = new JSONObject();
        try {
            json.put("username", Data.get().getLogin());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requests.post("http://cars.areas.su/logout", json, REQUEST_CODE_LOGOUT);
    }

    public void onLoginProceed(View view) {
        String login = this.<EditText>findViewById(R.id.edit_text_login).getText().toString();
        String password = this.<EditText>findViewById(R.id.edit_text_password).getText().toString();
        JSONObject data = new JSONObject();
        try {
            data.put("username", login);
            data.put("password", password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        requests.post("http://cars.areas.su/login", data, REQUEST_CODE_LOGIN);

        JSONObject json = new JSONObject();
        try {
            json.put("username", login);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requests.post("http://cars.areas.su/logout", json, REQUEST_CODE_LOGOUT);
    }

    public void onRegisterProceed(View view) {
        String login = this.<EditText>findViewById(R.id.edit_text_login).getText().toString();
        String password = this.<EditText>findViewById(R.id.edit_text_password).getText().toString();
        String email = this.<EditText>findViewById(R.id.edit_text_email).getText().toString();

        JSONObject data = new JSONObject();
        try {
            data.put("username", login);
            data.put("email", email);
            data.put("password", password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        requests.post("http://cars.areas.su/signup", data, REQUEST_CODE_SIGN_UP);
    }

    @Override
    public void onResponse(JSONObject json, int requestsCode) {
        if (requestsCode == REQUEST_CODE_LOGIN) {
            try {
                JSONObject notice = json.getJSONObject("notice");

                if (notice.has("token")) {
                    Data data = Data.get();
                    data.setToken(notice.getString("token"));
                    data.setLogin(this.<EditText>findViewById(R.id.edit_text_login).getText().toString());
                    startActivity(new Intent(SignInActivity.this, NavActivity.class));
                } else if (notice.has("answer")) {
                    Toast.makeText(this, notice.getString("answer"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (requestsCode == REQUEST_CODE_SIGN_UP) {
            try {
                JSONObject notice = json.getJSONObject("notice");
                String answer = notice.getString("answer");
                Toast.makeText(SignInActivity.this, answer, Toast.LENGTH_SHORT).show();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onResponse(JSONArray json, int requestsCode) {

    }

    @Override
    public void onResponseError(VolleyError error, int requestsCode) {
        Toast.makeText(this,"Error occurred!", Toast.LENGTH_SHORT).show();
    }
}