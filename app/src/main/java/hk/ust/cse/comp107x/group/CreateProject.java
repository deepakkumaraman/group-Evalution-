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

public class CreateProject extends AppCompatActivity {
    EditText project_name,student_1,rollno_1,student_2,rollno_2,student_3,rollno_3,student_4,rollno_4,group_id,supervisior;
    Button submit;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updateUI();
    }

private void updateUI(){
     project_name = findViewById(R.id.projectname);
     student_1 = findViewById(R.id.student1);
     student_2 = findViewById(R.id.student2);
     student_3 = findViewById(R.id.student3);
     student_4 = findViewById(R.id.student4);
     rollno_1 = findViewById(R.id.Rollno1);
     rollno_2 = findViewById(R.id.Rollno2);
     rollno_3 = findViewById(R.id.Rollno3);
     rollno_4 = findViewById(R.id.Rollno4);
     group_id = findViewById(R.id.Groupid);
     supervisior = findViewById(R.id.Supervisior);
     submit = findViewById(R.id.Submitproject);


    awesomeValidation.addValidation(CreateProject.this,R.id.projectname, "[a-zA-Z\\s]+",R.string.project_name);
    awesomeValidation.addValidation(CreateProject.this,R.id.student1, "[a-zA-Z\\s]+",R.string.student_1);
    awesomeValidation.addValidation(CreateProject.this,R.id.Rollno1, "[0-9\\s]+",R.string.rollno_1);
    awesomeValidation.addValidation(CreateProject.this,R.id.student2, "[a-zA-Z\\s]+",R.string.student_2);
    awesomeValidation.addValidation(CreateProject.this,R.id.Rollno2, "[0-9\\s]+",R.string.rollno_2);
    awesomeValidation.addValidation(CreateProject.this,R.id.student3, "[a-zA-Z\\s]+",R.string.student_3);
    awesomeValidation.addValidation(CreateProject.this,R.id.Rollno3, "[0-9\\s]+",R.string.rollno_3);
    awesomeValidation.addValidation(CreateProject.this,R.id.student4, "[a-zA-Z\\s]+",R.string.student_4);
    awesomeValidation.addValidation(CreateProject.this,R.id.Rollno4, "[0-9\\s]+",R.string.rollno_4);
    awesomeValidation.addValidation(CreateProject.this,R.id.Groupid, "[a-zA-Z0-9\\s]+",R.string.group_id);
    awesomeValidation.addValidation(CreateProject.this,R.id.Supervisior, "[a-zA-Z\\s]+",R.string.supervisior);


    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // selectedId = radioGroup.getCheckedRadioButtonId();
            //gender = findViewById(selectedId);


            if (awesomeValidation.validate()){




                createProject();
                Toast.makeText(CreateProject.this," Data Received Succesfully", Toast.LENGTH_SHORT).show();
                finish();
            }

            else

            {
                Toast.makeText(CreateProject.this, "Error", Toast.LENGTH_SHORT).show();
            }

        }
    });

}

    private void createProject() {
        final String projectName = project_name.getText().toString().trim();
        final String student1 = student_1.getText().toString().trim();
        final String student2 = student_2.getText().toString().trim();
        final String student3 = student_3.getText().toString().trim();
        final String student4 = student_4.getText().toString().trim();
        final String rollno1 = rollno_1.getText().toString().trim();
        final String rollno2 = rollno_2.getText().toString().trim();
        final String rollno3 = rollno_3.getText().toString().trim();
        final String rollno4 = rollno_4.getText().toString().trim();
        final String groupid = group_id.getText().toString().trim();
        final String supervisitor = supervisior.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_CreateProject,
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
                params.put("project_name", projectName);
                params.put("student_1", student1);
                params.put("rollno_1", rollno1);
                params.put("student_2", student2);
                params.put("rollno_2", rollno2);
                params.put("student_3", student3);
                params.put("rollno_3", rollno3);
                params.put("student_4", student4);
                params.put("rollno_4", rollno4);
                params.put("group_id", groupid);
                params.put("supervisior", supervisitor);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
