/*
 * Copyright (c)  2015-2016, Mohit Kanwal
 */

package com.mohitkanwal.weatheroo.ui.locationinput;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mohitkanwal.weatheroo.MainActivity;
import com.mohitkanwal.weatheroo.R;
import com.mohitkanwal.weatheroo.di.DaggerScope;
import com.mohitkanwal.weatheroo.di.DaggerService;
import com.mohitkanwal.weatheroo.flow.Layout;
import com.mohitkanwal.weatheroo.mortar.ScreenComponentFactory;
import com.mohitkanwal.weatheroo.mortar.ScreenScope;
import com.mohitkanwal.weatheroo.mortar.lifecycle.ActivityHelper;
import com.mohitkanwal.weatheroo.mortar.lifecycle.LifecycleActionViewPresenter;
import com.mohitkanwal.weatheroo.mortar.lifecycle.LifecycleOwner;
import com.mohitkanwal.weatheroo.network.protocol.AQS2C;
import com.mohitkanwal.weatheroo.network.rest.WUAutoCompleteService;
import com.mohitkanwal.weatheroo.ui.home.WeatherooHomeScreen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import flow.Flow;
import flow.path.Path;
import timber.log.Timber;

/**
 * User: mohit
 * Date: 19/1/16
 */
@Layout(R.layout.ui_location_input)
public class LocationInputScreen extends Path implements ScreenComponentFactory<MainActivity.Component>, ScreenScope {
  @Override
  public Object createComponent(MainActivity.Component parent) {
    return DaggerLocationInputScreen_Component
        .builder()
        .module(new Module())
        .component(parent)
        .build();
  }

  @Override
  public String getScopeName() {
    return getClass().getSimpleName();
  }

  @DaggerScope(Component.class)
  @dagger.Component(
      dependencies = {
          MainActivity.Component.class
      },
      modules = {
          Module.class
      }
  )
  public interface Component {
    void inject(LocationInputView view);

    WUAutoCompleteService autoCompleteService();

    void inject(AutoCompleteAdapter autoCompleteAdapter);
  }

  @dagger.Module
  public static class Module {

  }

  @DaggerScope(Component.class)
  static class Presenter extends LifecycleActionViewPresenter<LocationInputView> {

    private static final int REQUEST_PLACE_PICKER = 0xbabe;

    private AutoCompleteAdapter mAdapter;

    @Inject
    public Presenter(LifecycleOwner lifecycleOwner, ActivityHelper activityHelper) {
      super(activityHelper, lifecycleOwner);
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
      super.onLoad(savedInstanceState);
      mAdapter = new AutoCompleteAdapter(getView().getContext());
      getView().txtPlaceName.setAdapter(mAdapter);
      getView().txtPlaceName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Timber.v("Item clicked %s", mAdapter.getItem(position).country);
          List<AQS2C.LocationResult> lists = new ArrayList<>();
          lists.add(mAdapter.getItem(position));
          Flow.get(view.getContext()).set(new WeatherooHomeScreen(lists));
        }
      });
    }

    /**
     * Handles incoming action from the view for choosing image
     */
    public void dispatchChooseLocation() {
      try {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        Intent intent = intentBuilder.build(getActivity());
        // Start the Intent by requesting a result, identified by a request code.
        getActivity().startActivityForResult(intent, REQUEST_PLACE_PICKER);

      } catch (GooglePlayServicesRepairableException e) {
        GooglePlayServicesUtil.getErrorDialog(e.getConnectionStatusCode(), getActivity(), 0);
      } catch (GooglePlayServicesNotAvailableException e) {
        Snackbar.make(getView(), "Google Play Services is not available.", Snackbar.LENGTH_LONG)
            .show();
      }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == REQUEST_PLACE_PICKER) {
        if (resultCode == Activity.RESULT_OK) {
          Snackbar.make(getView(), "Place successfully picked", Snackbar.LENGTH_SHORT).show();
          Place place = PlacePicker.getPlace(getContext(), data);
          getView().locationData.setText(place.getName()
              + "\n"
              + place.getAddress()
              + "\n"
              + place.getId()
              + "\n"
              + place.getLatLng().toString());
        } else {
          Snackbar.make(getView(), "Place not picked", Snackbar.LENGTH_SHORT).show();
        }
      }
    }
  }

  /**
   * City auto complete adapter
   */
  static class AutoCompleteAdapter extends ArrayAdapter<AQS2C.LocationResult> implements Filterable {

    @Inject
    WUAutoCompleteService autoCompleteService;

    private LayoutInflater mInflater;

    public AutoCompleteAdapter(final Context context) {
      super(context, -1);
      mInflater = LayoutInflater.from(context);
      DaggerService.<Component>getDaggerComponent(context).inject(this);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
      final TextView tv;
      if (convertView != null) {
        tv = (TextView) convertView;
      } else {
        tv = (TextView) mInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
      }

      tv.setText(createFormattedNamefromItem(getItem(position)));
      return tv;
    }

    private String createFormattedNamefromItem(final AQS2C.LocationResult result) {
      return result.name + "," + result.country;
    }

    @Override
    public Filter getFilter() {
      return new Filter() {
        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
          List<AQS2C.LocationResult> locationResults = null;
          if (constraint != null) {
            try {
              locationResults = autoCompleteService.getAutoCompleteList(constraint.toString())
                  .execute()
                  .body()
                  .results;
            } catch (Exception e) {
              e.printStackTrace();
              Timber.e(e, "Error communicating with the service %s", e.toString());
            }
          }
          if (locationResults == null) {
            locationResults = new ArrayList<>();
          }

          final FilterResults filterResults = new FilterResults();
          filterResults.values = locationResults;
          filterResults.count = locationResults.size();

          return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(final CharSequence contraint, final FilterResults results) {
          clear();
          for (AQS2C.LocationResult result : (List<AQS2C.LocationResult>) results.values) {
            if (!result.type.equals("country"))
              add(result);
          }
          if (results.count > 0) {
            notifyDataSetChanged();
          } else {
            notifyDataSetInvalidated();
          }
        }

        @Override
        public CharSequence convertResultToString(final Object resultValue) {
          return resultValue == null ? "" : ((AQS2C.LocationResult) resultValue).name;
        }
      };
    }
  }
}
