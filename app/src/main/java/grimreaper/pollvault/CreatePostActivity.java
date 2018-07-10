package grimreaper.pollvault;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class CreatePostActivity extends AppCompatActivity {

    TabLayout choicesTab;
    TextInputEditText desc , t1 , t2 , t3 , t4;
    Button submitPoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        choicesTab = findViewById(R.id.choicesTab);
        desc = findViewById(R.id.pollContent);
        t1 = findViewById(R.id.optionOne);
        t2 = findViewById(R.id.optionTwo);
        t3 = findViewById(R.id.optionThree);
        t4 = findViewById(R.id.optionFour);
        submitPoll = findViewById(R.id.submitPoll);
        submitPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numTabs = choicesTab.getSelectedTabPosition() + 2;
                String pollDesc = desc.getText().toString();
                String op1 = t1.getText().toString();
                String op2 = t2.getText().toString();
                if(pollDesc.length() == 0 || op1.length() == 0 || op2.length() == 0)
                    return;
                String op3 = "", op4 = "";
                if(numTabs > 2)
                    op3 = t3.getText().toString();
                if(numTabs > 3)
                    op4 = t4.getText().toString();
                createPoll(pollDesc , op1 , op2 , op3 , op4 , numTabs);
            }
        });

        int numTabs = choicesTab.getSelectedTabPosition() + 2;
        createOptions(numTabs);

        choicesTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int numTabs = choicesTab.getSelectedTabPosition() + 2;
                createOptions(numTabs);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void createOptions(int choices){
        t3.setVisibility(View.VISIBLE);
        t4.setVisibility(View.VISIBLE);
        switch(choices){
            case 2:
                t3.setVisibility(View.GONE);
            case 3:
                t4.setVisibility(View.GONE);
        }
    }

    void createPoll(final String desc , final String op1 , final String op2 , final String op3 , final String op4 , final int numTabs){
        String tag_str_obj = "str_obj_req";

        String url = URLS.submitUrl;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String object) {
                try {
                    JSONObject response = new JSONObject(object);
                    Boolean confirmed = response.getBoolean("success");
                    if(confirmed){
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
                params.put("username" , "asdf");
                params.put("postDesc", desc);
                params.put("opt1", op1);
                params.put("opt2", op2);
                if(numTabs > 2){
                    params.put("opt3", "NULL");
                }else{
                    params.put("opt3", op3);
                }
                if(numTabs > 3){
                    params.put("opt4", "NULL");
                }else{
                    params.put("opt4", op4);
                }
                params.put("numTabs", Integer.toString(numTabs));
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_str_obj);
    }
}