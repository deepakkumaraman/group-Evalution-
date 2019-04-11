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

public class Facultylogin extends AppCompatActivity implements View.OnClickListener {

    private EditText FacultyUser, FacultyPass;
    private Button fLogin, fSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultylogin);

        if(SharedPrefManager.getInstance(this).isFacultyLoggedIn()){
            finish();
            startActivity(new Intent(this, FacultyDashboard.class));
            return;
        }
        FacultyUser = findViewById(R.id.editText2);
        FacultyPass = findViewById(R.id.editText4);
        fLogin = findViewById(R.id.button5);
        fSignUp = findViewById(R.id.button6);
    }

    private void facultyLogin(){
        final String email = FacultyUser.getText().toString().trim();
        final String password = FacultyPass.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_FLOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .facultyLogin(
                                                obj.getInt("id"),
                                                obj.getString("name"),
                                                obj.getString("depart"),
                                                obj.getString("email")
                                        );
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Faculty Login successful",
                                        Toast.LENGTH_LONG
                                ).show();
                                startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
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

        if(v == fLogin){
            facultyLogin();
        }
        if(v == fSignUp){
            Intent i=new Intent(this,Facultysignup.class);
            startActivity(i);
        }

    }
}
