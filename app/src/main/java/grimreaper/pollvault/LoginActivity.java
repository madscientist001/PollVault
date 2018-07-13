package grimreaper.pollvault;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView createAccount;
    ActionBar actionBar;
    Button signInButton;
    TextInputEditText userEdit , passEdit;
    String username , password;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccount = findViewById(R.id.createAccountLink);
        createAccount.setOnClickListener(this);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();

        }
        signInButton = findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);

        userEdit = findViewById(R.id.userSignIn);
        passEdit = findViewById(R.id.passwordSignIn);

        session = new SessionManager(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == createAccount.getId()){
            Intent intent = new Intent(this , CreateAccountActivity.class);
            startActivity(intent);
        }else if(view.getId() == signInButton.getId()){
            username = userEdit.getText().toString();
            password = passEdit.getText().toString();
            if(!validate(username , password)) return;
            authenticate(username ,password);
        }
    }

    public void authenticate(final String user, final String pass){
        // Tag used to cancel the request
        String tag_str_obj = "str_obj_req";

        String url = URLS.loginUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String object) {
                try {
                    JSONObject response = new JSONObject(object);
                    Boolean confirmed = response.getBoolean("success");
                    if(confirmed){
                        session.createLoginSession(user);
                        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    //VolleyLog.d("goku" , e.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("goku" , error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                params.put("password", pass);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_str_obj);
    }

    public boolean validate(String user , String password){
        if(password.length() == 0 || user.length() == 0) return false;
        if(password.length() < 8 || password.length() > 20){
            passEdit.setError("between 8 and 20 characters");
            return false;
        }
        return true;
    }
}