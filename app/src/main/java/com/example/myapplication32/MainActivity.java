package com.example.myapplication32;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText fullnameText = findViewById(R.id.txt_fullname);
        EditText usernameText = findViewById(R.id.txt_username);
        EditText emailText = findViewById(R.id.txt_email);
        EditText passwordText = findViewById(R.id.txt_password);
        EditText confirmPasswordText = findViewById(R.id.txt_confirm_password);
        EditText dobText = findViewById(R.id.txt_dob);
        RadioGroup radioGender = findViewById(R.id.radio_gender);
        TextView resultText = findViewById(R.id.txt_result);
        Button saveButton = findViewById(R.id.btn_save);

        // DatePicker untuk tanggal lahir
        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(selectedYear, selectedMonth, selectedDay);

                                if (isAgeValid(selectedDate)) {
                                    dobText.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                                } else {
                                    Toast.makeText(MainActivity.this, "You must be at least 16 years old", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fullnameText.getText().toString();
                String username = usernameText.getText().toString();
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();
                String dob = dobText.getText().toString();

                // Validasi input kosong
                if (fullname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || dob.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validasi konfirmasi password
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validasi pilihan gender
                int selectedGenderId = radioGender.getCheckedRadioButtonId();
                if (selectedGenderId == -1) {
                    Toast.makeText(MainActivity.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedGender = findViewById(selectedGenderId);
                String gender = selectedGender.getText().toString();

                // Tampilkan hasil di TextView
                resultText.setText("Full Name: " + fullname + "\n" +
                        "Username: " + username + "\n" +
                        "Email: " + email + "\n" +
                        "Password: " + password + "\n" +
                        "Date of Birth: " + dob + "\n" +
                        "Gender: " + gender);
            }
        });
    }

    // Metode untuk mengecek apakah usia setidaknya 16 tahun
    private boolean isAgeValid(Calendar selectedDate) {
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age >= 16;
    }
}
