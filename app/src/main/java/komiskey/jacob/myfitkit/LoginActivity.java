package komiskey.jacob.myfitkit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button login;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.username_sign_in_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Stores User name
                String username = String.valueOf(mUsername.getText());
                // Stores Password
                String password = String.valueOf(mPassword.getText());

                if (username.equals(getText(R.string.username)) && password.equals(getText(R.string.password))) {
                    Intent intent = new Intent(getApplicationContext(), ListItemCustomerActivity.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), getString(R.string.successful_login), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.unsuccessful_login), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}