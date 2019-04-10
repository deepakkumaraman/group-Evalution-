package hk.ust.cse.comp107x.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Studentlogin extends AppCompatActivity implements View.OnClickListener  {

    private EditText StudentUser, StudentPass;
    private Button sLogin, sSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);


        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, Dashboard.class));
            return;
        }

        StudentUser = findViewById(R.id.editText);
        StudentPass = findViewById(R.id.editText3);
        sLogin = findViewById(R.id.button3);
        sSignUp = findViewById(R.id.button4);

       sLogin.setOnClickListener(this);
       sSignUp.setOnClickListener(this);

    }


    private void userLogin(){
        final String email = StudentUser.getText().toString().trim();
        final String password = StudentPass.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getInt("id"),
                                                obj.getString("name"),
                                                obj.getString("roll"),
                                                obj.getString("branch"),
                                                obj.getString("gender"),
                                                obj.getString("phone"),
                                                obj.getString("email")
                                        );
                                Toast.makeText(
                                        getApplicationContext(),
                                        "User Login successful",
                                        Toast.LENGTH_LONG
                                ).show();
                                startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                finish();

                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    @Override
    public void onClick(View v) {
        if(v == sLogin){
            userLogin();
        }
        if(v == sSignUp){
            Intent i=new Intent(this,Studentsignup.class);
            startActivity(i);
        }
    }

}
