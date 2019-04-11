package hk.ust.cse.comp107x.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Facultysignup extends AppCompatActivity {

    EditText facultyName,facultyEmail,facultyPassword,facultyDepart;
    Button buttonSubmit,buttonCancel;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultysignup);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updateUI();
    }

    private void updateUI(){
        facultyName = findViewById(R.id.facname);
        facultyDepart = findViewById(R.id.facdepart);
        facultyEmail = findViewById(R.id.facemail);
        facultyPassword = findViewById(R.id.facpassword);
        buttonSubmit = findViewById(R.id.submitbut);
        buttonCancel = findViewById(R.id.Cancelbut);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(Facultysignup.this,R.id.facname, "[a-zA-Z\\s]+",R.string.snamerr);
        awesomeValidation.addValidation(Facultysignup.this,R.id.facdepart, "[a-zA-Z\\s]+",R.string.fdeparter);
        awesomeValidation.addValidation(Facultysignup.this,R.id.facemail,android.util.Patterns.EMAIL_ADDRESS,R.string.semailer);
        awesomeValidation.addValidation(Facultysignup.this,R.id.facpassword,regexPassword,R.string.spassword);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){

                    registerFaculty();
                    Toast.makeText(Facultysignup.this," Data Received Succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else

                {
                    Toast.makeText(Facultysignup.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void registerFaculty(){

        final String name = facultyName.getText().toString().trim();
        final String depart = facultyDepart.getText().toString().trim();
        final String email = facultyEmail.getText().toString().trim();
        final String password = facultyPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_FREGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);

                params.put("depart", depart);

                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    public void cancel(View view) {
        finish();
    }
}




