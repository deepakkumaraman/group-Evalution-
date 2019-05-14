package hk.ust.cse.comp107x.group;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import static hk.ust.cse.comp107x.group.Constants.URL_StudentDashboard;

public class Student_profile extends AppCompatActivity {

    TextView name,roll,branch,gender,phone,email;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        name = findViewById(R.id.textname);
        roll = findViewById(R.id.textroll);
        branch = findViewById(R.id.textDept);
        gender = findViewById(R.id.textgender);
        phone = findViewById(R.id.textphone);
        email = findViewById(R.id.textEmail);

        new Connection().execute();
    }
    class Connection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result ="";
            sharedPreferences = getSharedPreferences("mysharedpref12", Context.MODE_PRIVATE);
            String host =URL_StudentDashboard +"?id="+sharedPreferences.getInt("userid",0);
            try{
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");
                String line = "";

                while((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            }
            catch (Exception e){
                return new String("There exception: " + e.getMessage());
            }



            return result;
        }
        @Override
        protected void onPostExecute(String result){

            try {

                JSONArray jsonArray = new JSONArray(result);
                JSONObject students = jsonArray.getJSONObject(0);

                int success = 1;

                if(success == 1){

                    String studentName = students.getString("name");
                    String studentRoll = students.getString("roll_no");
                    String studentBranch = students.getString("branch");
                    String studentGender = students.getString("gender");
                    String studentPhone = students.getString("phone");
                    String studentEmail = students.getString("email");
                    name.setText(studentName);
                    roll.setText(studentRoll);
                    branch.setText(studentBranch);
                    gender.setText(studentGender);
                    phone.setText(studentPhone);
                    email.setText(studentEmail);
                }
                else{
                    Toast.makeText(getApplicationContext(), "no any student data ", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void back(View view) {

        finish();
    }
}
