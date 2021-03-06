package hk.ust.cse.comp107x.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

import static hk.ust.cse.comp107x.group.Constants.URL_FacultyDashboard;

public class FacultyDashboard extends AppCompatActivity {

    TextView name,dept,email;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        name = findViewById(R.id.textname);
        dept = findViewById(R.id.textDept);
        email = findViewById(R.id.textEmail);
        new Connection().execute();
    }


    class Connection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result ="";
            sharedPreferences = getSharedPreferences("mysharedpref12", Context.MODE_PRIVATE);
            String host =URL_FacultyDashboard +"?id="+sharedPreferences.getInt("facultyid",0);
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

                    String facultyName = students.getString("name");

                    String facultyBranch = students.getString("depart");

                    String facultyEmail = students.getString("email");
                    name.setText(facultyName);

                    dept.setText(facultyBranch);

                     email.setText(facultyEmail);

                }
                else{
                    Toast.makeText(getApplicationContext(), "no any faculty data ", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, Facultylogin.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this, "You clicked settings", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void back(View view) {
        finish();
    }
}
