package com.hansellramos.todo.client.android;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hansellramos.todo.client.android.activities.adapter.TaskAdapter;
import com.hansellramos.todo.client.android.dto.Task;
import com.hansellramos.todo.client.android.dto.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;

    //Auth
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //Database
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTaskDatabaseReference;
    private ChildEventListener mTaskChildEventListener;

    private User mCurrentUser;

    private ListView mTaskListView;
    private TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final Button btnSignOut = (Button) findViewById(R.id.btnSignOut);
//        btnSignOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mFirebaseAuth != null) {
//                    mFirebaseAuth.signOut();
//                }
//            }
//        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        //Database reference
        mTaskDatabaseReference = mFirebaseDatabase.getReference().child("tasks");

        //Views references
        mTaskListView = (ListView) findViewById(R.id.taskListView);

        //Adapter
        List<Task> tasks = new ArrayList<>();
        mTaskAdapter = new TaskAdapter(this, R.layout.item_task, tasks);
        mTaskListView.setAdapter(mTaskAdapter);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                if (user != null){
                    mCurrentUser = new User(user.getUid(), user.getEmail());
                    Toast.makeText(MainActivity.this, "Signed In as "+ mCurrentUser.getEmail(), Toast.LENGTH_LONG).show();
                    //signed in
                } else {
                    mCurrentUser = null;
                    startActivityForResult(
                            AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER
                                    , AuthUI.GOOGLE_PROVIDER
                            )
                            .build(),
                            RC_SIGN_IN
                    );
                }
            }
        };

        mTaskChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    Task task = dataSnapshot.getValue(Task.class);
                    mTaskAdapter.add(task);
                } catch (Exception e) {
                    Log.d("Error","UID:"+s+" -- "+e.getMessage());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("Mayha","changed");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        };
        mTaskDatabaseReference.addChildEventListener(mTaskChildEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
