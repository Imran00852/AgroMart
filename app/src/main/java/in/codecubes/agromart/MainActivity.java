package in.codecubes.agromart;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.codecubes.agromart.AddPostActivity;
import in.codecubes.agromart.LoginActivity;
import in.codecubes.agromart.Post;
import in.codecubes.agromart.PostAdapter;
import in.codecubes.agromart.ProfileUI;
import in.codecubes.agromart.R;

public class MainActivity extends AppCompatActivity {
    private ImageView account;
    private FloatingActionButton addPostBtn;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    private ProgressBar progress_Bar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;

    private RecyclerView postRecyclerView;
    private ArrayList<Post> postList;
    private PostAdapter adapter;
    private ArrayList<Post> filteredList;
    private CardView delicious_apple, kullu_apple,golden_apple, mahraji_apple,treal_apple,american_apple,pear_apple;
    private String delicious="delicious", kullu="kullu",golden="golden",mahraji="mahraji",treal="treal",american="american",pear="pear";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        addPostBtn = findViewById(R.id.addPost);
        account = findViewById(R.id.goToProfile);
        progress_Bar = findViewById(R.id.progressBar);
        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        actionBar = getSupportActionBar();

        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        showToast("Home selected");
                        break;
                    case R.id.item2:
                        showToast("Profile selected");
                        break;
                    case R.id.item3:
                        showToast("My post selected");
                        break;
                    case R.id.item4:
                        showToast("Logout selected");
                        mAuth.signOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.item5:
                        showToast("About us selected");
                        break;
                }
                return true; // Event handled
            }

            private void showToast(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        postRecyclerView = findViewById(R.id.posts_recycler_view);
        postRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        postRecyclerView.setLayoutManager(layoutManager);

        loadData();

        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AddPostActivity.class);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(ProfileUI.class);
            }
        });
        delicious_apple=findViewById(R.id.delicious);
        golden_apple=findViewById(R.id.golden);
        kullu_apple=findViewById(R.id.kullu);
        mahraji_apple=findViewById(R.id.mahraji);
        treal_apple=findViewById(R.id.treal);
        pear_apple=findViewById(R.id.pear);
        american_apple=findViewById(R.id.american);

        delicious_apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory(delicious);
            }
        });
        american_apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory(american);

            }
        });
        golden_apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory(golden);

            }
        });
        kullu_apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory(kullu);

            }
        });
        mahraji_apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory(mahraji);

            }
        });
        treal_apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory(treal);

            }
        });
        pear_apple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByCategory(pear);

            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            return;
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            progress_Bar.setVisibility(View.INVISIBLE);
            finish();
        }
    }

    public void openActivity(final Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

    public void loadData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("POSTS");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    postList = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Post post = dataSnapshot1.getValue(Post.class);
                        postList.add(post);
                    }
                    adapter = new PostAdapter(MainActivity.this, postList);
                    postRecyclerView.setAdapter(adapter);
                    filteredList = new ArrayList<>(postList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load posts", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void performSearch(String query) {
        ArrayList<Post> searchResults = new ArrayList<>();

        if (query.isEmpty()) {
            searchResults.addAll(postList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Post post : postList) {
                if ((post.getVariety() != null && post.getVariety().toLowerCase().contains(lowerCaseQuery))
                        || (post.getDistrict() != null && post.getDistrict().toLowerCase().contains(lowerCaseQuery))) {
                    searchResults.add(post);
                }
            }
        }

        adapter.setFilteredList(searchResults);
    }
    private void searchByCategory(String category) {

        ArrayList<Post> searchResults = new ArrayList<>();

        if (category.isEmpty()) {
            searchResults.addAll(postList);
        } else {
            String lowerCaseQuery = category.toLowerCase();
            for (Post post : postList) {
                if ((post.getVariety() != null && post.getVariety().toLowerCase().contains(lowerCaseQuery))
                        || (post.getDistrict() != null && post.getDistrict().toLowerCase().contains(lowerCaseQuery))) {
                    searchResults.add(post);
                }
            }
        }

        adapter.setFilteredList(searchResults);
    }
}