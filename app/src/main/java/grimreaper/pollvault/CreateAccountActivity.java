package grimreaper.pollvault;

import android.app.Application;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    TextView existingAccount;
    ActionBar actionBar;
    Button registerButton;
    TextInputEditText userEdit , emailEdit , passEdit , confPassEdit;
    String username , email , password , confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        existingAccount = findViewById(R.id.existingAccountLink);
        existingAccount.setOnClickListener(this);
        actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.hide();

        userEdit = findViewById(R.id.registerUsername);
        emailEdit = findViewById(R.id.registerEmail);
        passEdit = findViewById(R.id.registerPassword);
        confPassEdit = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == existingAccount.getId()){
            Intent intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
        }else if(view.getId() == registerButton.getId()){
            username = userEdit.getText().toString();
            email = emailEdit.getText().toString();
            password = passEdit.getText().toString();
            confirmPassword = confPassEdit.getText().toString();
            if(!validate(username , email , password , confirmPassword)) return;
            authenticate(username , email ,password);
        }
    }

    public void authenticate(final String user , final String email , final String pass){
        // Tag used to cancel the request
        String tag_str_obj = "str_obj_req";

        final String url = URLS.registerUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String object) {
                        try {
                            Log.d("goku", object);
                            JSONObject response = new JSONObject(object);
                            Boolean confirmed = response.getBoolean("success");
                            if(confirmed){
                                Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                //Toast.makeText(getApplicationContext() , "Account Creation Successful", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }else{
                                //Toast.makeText(CreateAccountActivity.this, "Account Creation Failed", Toast.LENGTH_SHORT).show();
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
                params.put("email", email);
                params.put("password", pass);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_str_obj);
    }

    public boolean validate(String username , String email , String password , String confirmPassword){
        boolean isValid = true;
        if(username.isEmpty()){
            isValid = false;
            setError(0 , "Enter Username");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false;
            setError(1, "Invalid Email Address");
        }
        if(password.length() < 8 || password.length() > 20){
            isValid = false;
            setError(2 , "between 8 and 20 characters");
        }else if(!password.equals(confirmPassword)){
            isValid = false;
            setError(3 , "Passwords do not match");
        }
        return isValid;
    }

    public void setError(int errorCode , String error){
        userEdit.setError(null);
        emailEdit.setError(null);
        passEdit.setError(null);
        confPassEdit.setError(null);
        switch (errorCode){
            case 0:
                userEdit.setError(error);
                break;
            case 1:
                emailEdit.setError(error);
                break;
            case 2:
                passEdit.setError(error);
                break;
            case 3:
                passEdit.setError(error);
                confPassEdit.setError(error);
                break;
        }
    }
}