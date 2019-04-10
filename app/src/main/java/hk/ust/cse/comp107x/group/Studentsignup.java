package hk.ust.cse.comp107x.group;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Studentsignup extends AppCompatActivity {

    RadioGroup radioGroup ;
    RadioButton gender ;
    String genderString;
    EditText studentName,studentRoll,studentPhone,studentEmail,studentPassword,studentBranch;
    Button buttonSubmit,buttonCancel;
    int selectedId ;
    ProgressDialog progressDialog;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsignup);



        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        updateUI();

    }

    private void updateUI() {

        studentName=(EditText)findViewById(R.id.studentName);
        studentRoll=(EditText)findViewById(R.id.studentRoll);
        studentBranch=(EditText)findViewById(R.id.studentBranch);
        studentPhone=(EditText)findViewById(R.id.studentPhone);
        studentEmail=(EditText)findViewById(R.id.studentEmail);
        studentPassword=(EditText)findViewById(R.id.studentPassword);
        buttonSubmit=(Button)findViewById(R.id.buttonSubmit);




        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(Studentsignup.this,R.id.studentName, "[a-zA-Z\\s]+",R.string.snamerr);
        awesomeValidation.addValidation(Studentsignup.this,R.id.studentRoll, "[0-9\\s]+",R.string.sroller);
        awesomeValidation.addValidation(Studentsignup.this,R.id.studentBranch, "[a-zA-Z\\s]+",R.string.sbrancher);
        awesomeValidation.addValidation(Studentsignup.this,R.id.studentPhone, RegexTemplate.TELEPHONE,R.string.sphoner);
        awesomeValidation.addValidation(Studentsignup.this,R.id.studentEmail,android.util.Patterns.EMAIL_ADDRESS,R.string.semailer);
        awesomeValidation.addValidation(Studentsignup.this,R.id.studentPassword,regexPassword,R.string.spassword);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                // selectedId = group.getCheckedRadioButtonId();
                gender = findViewById(checkedId);
                genderString = (String) gender.getText();
                Toast.makeText(getApplicationContext(), gender.getText(), Toast.LENGTH_SHORT).show();
            }
        });



        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // selectedId = radioGroup.getCheckedRadioButtonId();
                //gender = findViewById(selectedId);


                if (awesomeValidation.validate()){




                    registerUser();
                    Toast.makeText(Studentsignup.this," Data Received Succesfully", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(Studentsignup.this, "Data Received Succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else

                {
                    Toast.makeText(Studentsignup.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void registerUser() {
        final String name = studentName.getText().toString().trim();
        final String roll_no = studentRoll.getText().toString().trim();
        final String branch = studentBranch.getText().toString().trim();

        final RadioGroup gender = radioGroup;
        final String phone = studentPhone.getText().toString().trim();
        final String email = studentEmail.getText().toString().trim();
        final String password = studentPassword.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
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
                params.put("roll_no", roll_no);
                params.put("branch", branch);
                params.put("phone", phone);
                params.put("gender", genderString);
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