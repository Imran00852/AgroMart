package in.codecubes.agromart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class AddPostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseDatabase rootNode;
    private DatabaseReference reference ,reference2;
    private TextInputLayout variety, grade, PackingType,state, district;
    private Button addPostButton;
    private String selectedVariety,selectedGrade,selectedPacking;
    private Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        variety = findViewById(R.id.selectVariety);
        grade = findViewById(R.id.selectGrade);
        addPostButton =findViewById(R.id.addPostButton);
        rootNode =FirebaseDatabase.getInstance();
        reference2=rootNode.getReference("user_data");



        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference = rootNode.getReference("post_details");
                member=new Member(selectedVariety,selectedGrade,selectedPacking);
                reference.setValue(member);
                Toast.makeText(AddPostActivity.this, selectedVariety+selectedGrade+selectedPacking, Toast.LENGTH_SHORT).show();


            }

        });


        variety.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a PopupMenu
                PopupMenu popupMenu = new PopupMenu(AddPostActivity.this, v);

                // Inflate the menu resource file
                popupMenu.getMenuInflater().inflate(R.menu.select_variety, popupMenu.getMenu());

                // Set a listener to handle menu item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Get the selected variety
                        selectedVariety  = item.getTitle().toString();

                        // Set the selected variety to the AutoCompleteTextView
                        AutoCompleteTextView autoCompleteTextView = variety.findViewById(R.id.selectVarietyAutoCompleteTextView);
                        autoCompleteTextView.setText(selectedVariety);

                        return true;
                    }
                });

                // Show the popup menu
                popupMenu.show();
            }
        });


        grade.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a PopupMenu
                PopupMenu popupMenu = new PopupMenu(AddPostActivity.this, v);

                // Inflate the menu resource file
                popupMenu.getMenuInflater().inflate(R.menu.select_grade, popupMenu.getMenu());

                // Set a listener to handle menu item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Get the selected variety
                        selectedGrade = item.getTitle().toString();

                        // Set the selected variety to the AutoCompleteTextView
                        AutoCompleteTextView autoCompleteTextView = grade.findViewById(R.id.selectGradeAutoCompleteTextView);
                        autoCompleteTextView.setText(selectedGrade);

                        return true;
                    }
                });

                // Show the popup menu
                popupMenu.show();
            }
        });

        PackingType = findViewById(R.id.packingType);
        PackingType.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a PopupMenu
                PopupMenu popupMenu = new PopupMenu(AddPostActivity.this, v);

                // Inflate the menu resource file
                popupMenu.getMenuInflater().inflate(R.menu.select_packing_type, popupMenu.getMenu());

                // Set a listener to handle menu item clicks
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Get the selected variety
                        selectedPacking = item.getTitle().toString();

                        // Set the selected variety to the AutoCompleteTextView
                        AutoCompleteTextView autoCompleteTextView = PackingType.findViewById(R.id.selectPackingAutoCompleteTextView);
                        autoCompleteTextView.setText(selectedPacking);

                        return true;
                    }
                });

                // Show the popup menu
                popupMenu.show();
            }

        });
             MaterialAutoCompleteTextView stateAutoCompleteTextView;
             MaterialAutoCompleteTextView districtAutoCompleteTextView;

             String[] states;
             String[][] districts;


                // Initialize the TextInputLayout and MaterialAutoCompleteTextView
                state = findViewById(R.id.setState);
                district = findViewById(R.id.setDistrict);
                stateAutoCompleteTextView = findViewById(R.id.stateAutoCompleteTextView);
                districtAutoCompleteTextView = findViewById(R.id.districtAutoCompleteTextView);



        // Define the states and districts arrays
                states = new String[]{
                        "Andaman and Nicobar Islands",
                        "Andhra Pradesh",
                        "Arunachal Pradesh",
                        "Assam",
                        "Bihar",
                        "Chandigarh",
                        "Chhattisgarh",
                        "Dadra and Nagar Haveli",
                        "Daman and Diu",
                        "Delhi",
                        "Goa",
                        "Gujarat",
                        "Haryana",
                        "Himachal Pradesh",
                        "J&K",
                        "Jharkhand",
                        "Karnataka",
                        "Kerala",
                        "Ladakh",
                        "Lakshadweep",
                        "Madhya Pradesh",
                        "Maharashtra",
                        "Manipur",
                        "Meghalaya",
                        "Mizoram",
                        "Nagaland",
                        "Odisha",
                        "Puducherry",
                        "Punjab",
                        "Rajasthan",
                        "Sikkim",
                        "Tamil Nadu",
                        "Telangana",
                        "Tripura",
                        "Uttarakhand",
                        "Uttar Pradesh",
                        "West Bengal"
                };

        districts = new String[][]{
                getResources().getStringArray(R.array.array_andaman_nicobar_districts),
                getResources().getStringArray(R.array.array_andhra_pradesh_districts),
                getResources().getStringArray(R.array.array_arunachal_pradesh_districts),
                getResources().getStringArray(R.array.array_assam_districts),
                getResources().getStringArray(R.array.array_bihar_districts),
                getResources().getStringArray(R.array.array_chandigarh_districts),
                getResources().getStringArray(R.array.array_chhattisgarh_districts),
                getResources().getStringArray(R.array.array_dadra_nagar_haveli_districts),
                getResources().getStringArray(R.array.array_daman_diu_districts),
                getResources().getStringArray(R.array.array_delhi_districts),
                getResources().getStringArray(R.array.array_goa_districts),
                getResources().getStringArray(R.array.array_gujarat_districts),
                getResources().getStringArray(R.array.array_haryana_districts),
                getResources().getStringArray(R.array.array_himachal_pradesh_districts),
                getResources().getStringArray(R.array.array_jammu_kashmir_districts),
                getResources().getStringArray(R.array.array_jharkhand_districts),
                getResources().getStringArray(R.array.array_karnataka_districts),
                getResources().getStringArray(R.array.array_kerala_districts),
                getResources().getStringArray(R.array.array_ladakh_districts),
                getResources().getStringArray(R.array.array_lakshadweep_districts),
                getResources().getStringArray(R.array.array_madhya_pradesh_districts),
                getResources().getStringArray(R.array.array_maharashtra_districts),
                getResources().getStringArray(R.array.array_manipur_districts),
                getResources().getStringArray(R.array.array_meghalaya_districts),
                getResources().getStringArray(R.array.array_mizoram_districts),
                getResources().getStringArray(R.array.array_nagaland_districts),
                getResources().getStringArray(R.array.array_odisha_districts),
                getResources().getStringArray(R.array.array_puducherry_districts),
                getResources().getStringArray(R.array.array_punjab_districts),
                getResources().getStringArray(R.array.array_rajasthan_districts),
                getResources().getStringArray(R.array.array_sikkim_districts),
                getResources().getStringArray(R.array.array_tamil_nadu_districts),
                getResources().getStringArray(R.array.array_telangana_districts),
                getResources().getStringArray(R.array.array_tripura_districts),
                getResources().getStringArray(R.array.array_uttarakhand_districts),
                getResources().getStringArray(R.array.array_uttar_pradesh_districts),
                getResources().getStringArray(R.array.array_west_bengal_districts)
        };

                // Create an ArrayAdapter for the states AutoCompleteTextView
                ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, states);
                stateAutoCompleteTextView.setAdapter(stateAdapter);


                // Set an item selected listener for the states AutoCompleteTextView
        stateAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Create an ArrayAdapter for the districts AutoCompleteTextView based on the selected state
                ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(AddPostActivity.this, android.R.layout.simple_dropdown_item_1line, districts[position]);
                districtAutoCompleteTextView.setAdapter(districtAdapter);
            }
        });
            }
           private  boolean validateVariety(){
                if(selectedVariety.isEmpty()){
                    Toast.makeText(AddPostActivity.this, "select Variety", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else {
                    return true;
                }

            }
    private boolean validateGrade(){
        if(selectedGrade.isEmpty()){
            Toast.makeText(AddPostActivity.this, "select Variety", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }

    }
    private boolean validatePackingType(){
        if(selectedPacking.isEmpty()){
            Toast.makeText(AddPostActivity.this, "select Variety", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




