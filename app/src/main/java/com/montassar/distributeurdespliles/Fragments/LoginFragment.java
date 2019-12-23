package com.montassar.distributeurdespliles.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.montassar.distributeurdespliles.HomeActivity;
import com.montassar.distributeurdespliles.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    View view;
    private final String TAG="LoginFragment";
    private ImageView btnLogin,btnSignUp;
    private EditText edtEmail,edtPassword;
    private FirebaseAuth mAuth;
    private String email,password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login,container,false);

        btnLogin = (ImageView) view.findViewById(R.id.btn_login_co);
        btnSignUp = (ImageView) view.findViewById(R.id.btn_sign_up_co);
        edtEmail = (EditText) view.findViewById(R.id.edt_email_co);
        edtPassword = (EditText) view.findViewById(R.id.edt_password_co);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id)
        {
            case R.id.btn_login_co:
                Toast.makeText(getContext(), "login clicked", Toast.LENGTH_SHORT).show();
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                login();
                //LoginUser(email,password);
                break;
            case R.id.btn_sign_up_co:
                Toast.makeText(getContext(), "signUp clicked", Toast.LENGTH_SHORT).show();
                signUp();
                break;
        }
    }
    private void LoginUser(String mail,String password) {
        mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    // Do your task in success
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();

                }
                else {
                    // Do your task in failure

                }
            }
        });
    }

    private void signUp() {
        SignupFragment fragment = new SignupFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void login() {

        email =edtEmail.getText().toString().trim();
        password =edtPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "signInWithEmail:success "+user.getEmail() , Toast.LENGTH_SHORT).show();
                            /*HomeFragment fragment = new HomeFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.add(R.id.fragment_container,fragment);
                            transaction.commit();*/
                            updateUI(user);
                        }else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });

    }

}
