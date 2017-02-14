package com.suraj.examples.myweather;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Location;
import service.LocationService;
import service.ServiceCallback;

/**
 * Created by suraj bhattarai on 7/12/15.
 * This class displays search location dialog fragment and displays matches using LocationGridAdapter
 * User selection is passed into the main Activity from the callback interface.
 */
public class LocationDialog extends DialogFragment implements ServiceCallback {

    /** Define variables to store possible location and callback object */
    private static ArrayList<Location> mLocations = null;
    private static LocationDialogCallback mLocationDialogCallback;

    /** Define UI elements */
    private EditText mEditTextLocation;
    private Button mButtonSearch;
    private ProgressDialog mProgressDialog;
    private GridView mLocationGrid;

    /**
     * Constructor for passing callback
     * @return forecast dialog
     */
    static LocationDialog newInstance(LocationDialogCallback dialogCallback) {
        LocationDialog f = new LocationDialog();
        mLocationDialogCallback = dialogCallback;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /** Inflate location layout */
        View mLocationSelectView = inflater.inflate(R.layout.location_fragment, container, false);
        getDialog().setTitle(getActivity().getString(R.string.location_search_title));

        /** Instantiate UI elements */
        mEditTextLocation = (EditText)mLocationSelectView.findViewById(R.id.location_fragment_edit_text_query);
        mButtonSearch = (Button)mLocationSelectView.findViewById(R.id.location_fragment_button_search);
        mLocationGrid = (GridView)mLocationSelectView.findViewById(R.id.location_fragment_location_grid);

        /** Search for possible locations when user taps on Search button */
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = mEditTextLocation.getText().toString();
                if (searchQuery.length() > 0) {
                    getLocationData(searchQuery);
                }
            }
        });

        mLocationGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                mLocationDialogCallback.onSelectLocation(mLocations.get(position));
            }
        });

        mEditTextLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()>3)
                    getLocationData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return mLocationSelectView;
    }

    private void getLocationData(String query) {
        /** Hide soft keyboard */
        //InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
        //        Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(mEditTextLocation.getWindowToken(), 0);

        /** Start the progress dialog */
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.searching_message));
        //mProgressDialog.show();

        /** Call the weather service for weather data */
        LocationService mLocationService = new LocationService(this, getActivity());
        mLocationService.getLocationData(query);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            /**
             * Resize the dialog to match parent Activity
             */
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (mProgressDialog.isShowing()) mProgressDialog.hide();
        ArrayList<Location> locationList = (ArrayList<Location>)data;
        mLocations = locationList;
        /** Create LocationGridAdapter and pass context and location data to display in UI */
        LocationGridAdapter locationGridAdapter = new LocationGridAdapter(getActivity(), mLocations);
        mLocationGrid.setAdapter(locationGridAdapter);
    }

    @Override
    public void onFailure(String message) {
        if (mProgressDialog.isShowing()) mProgressDialog.hide();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}