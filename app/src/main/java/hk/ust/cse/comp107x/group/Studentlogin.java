package hk.ust.cse.comp107x.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Studentlogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);
    }
    public void signup (View v)
    {
        Intent i=new Intent(this,Studentsignup.class);
        startActivity(i);
    }
}
