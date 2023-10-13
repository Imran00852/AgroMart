package in.codecubes.agromart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileUI extends AppCompatActivity {
    private TextView profileName ,profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_ui);
        Button back_btn=findViewById(R.id.back_to_home);

        profileName=findViewById(R.id.profile_name);
        profileEmail=findViewById(R.id.profile_emailID);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeScreen();
            }
        });
    }
    public void homeScreen(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
