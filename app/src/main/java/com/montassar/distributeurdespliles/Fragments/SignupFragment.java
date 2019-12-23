package com.montassar.distributeurdespliles.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


public class SignupFragment extends Fragment {
    private final String TAG = "SignupFragment";
    View view;
    private FirebaseAuth mAuth;
    private ImageView btnSignUp;
    private EditText edtEmail,edtPassword,edtRetypePassword;
    String email,password,retypePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup,container,false);


        mAuth = FirebaseAuth.getInstance();
        btnSignUp = (ImageView) view.findViewById(R.id.btn_sign_up_insc);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtPassword = (EditText) view.findViewById(R.id.edt_password);
        edtRetypePassword = (EditText) view.findViewById(R.id.edt_retype_password);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                retypePassword = edtRetypePassword.getText().toString().trim();

                if (TextUtils.isEmpty(email))
                {
                    edtEmail.setError("required Email...");
                    return;
                }
                if (TextUtils.isEmpty(password))

                {
                    edtPassword.setError("required Password...");
                    return;
                }
                if (!TextUtils.equals(password,retypePassword))
                {
                    edtRetypePassword.setError("Password mismatch...");
                }
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Log.d(TAG, "onComplete: createUserWithEmail :success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                }
                                else {
                                    Log.d(TAG, "onComplete: createUserWithEmail :failed");
                                    Toast.makeText(getContext(),"Authentication Failed.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });


        return view;
    }

    private void updateUI(FirebaseUser currentUser) {
       /* HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,fragment);
        transaction.commit();*/
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }


}
