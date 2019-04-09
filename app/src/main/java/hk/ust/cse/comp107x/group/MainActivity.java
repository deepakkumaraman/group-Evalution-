package hk.ust.cse.comp107x.group;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        public void faculty (View v)
        {
            Intent i=new Intent(this,Facultylogin.class);
            startActivity(i);
        }

        public void student (View v)
        {
            Intent i=new Intent(this,Studentlogin.class);
            startActivity(i);
        }

}
