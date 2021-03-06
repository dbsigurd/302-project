package ca.ualberta.medroad.view.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ca.ualberta.medroad.R;
import ca.ualberta.medroad.model.Patient;
import ca.ualberta.medroad.view.list_adapters.PatientHistoryAdapter;


public class PatientInfoFragment
		extends Fragment
{
	public static final String ARG_DATA = "patient";

	protected Patient               data;
	protected ViewHolder            view;
	protected PatientHistoryAdapter listAdapter;

	public static PatientInfoFragment newInstance( Patient data )
	{
		PatientInfoFragment fragment = new PatientInfoFragment();

		Bundle args = new Bundle();
		fragment.setArguments( args );
		fragment.data = data;

		return fragment;
	}

	public PatientInfoFragment()
	{
		// Required empty public constructor
	}

	@Override
	public void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		if ( getArguments() != null )
		{
			data = (Patient) getArguments().getSerializable( ARG_DATA );
		}
	}

	@Override
	public void onAttach( Activity activity )
	{
		super.onAttach( activity );
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		listAdapter = null;
	}

	@Override
	public View onCreateView( LayoutInflater inflater,
							  ViewGroup container,
							  Bundle savedInstanceState )
	{
		View resultView = inflater.inflate( R.layout.fragment_patient_info, container, false );

		view = new ViewHolder( resultView );

		return resultView;
	}

	protected class ViewHolder
	{
		public final SimpleDateFormat sdf     = new SimpleDateFormat( "MMM d, yy",
																	  Locale.getDefault() );
		public       TextView         name    = null;
		public       EditText         ahcn    = null;
		public       EditText         dob     = null;
		public       TextView         age     = null;
		public       Spinner          gender  = null;
		public       ListView         history = null;

		public ViewHolder( View v )
		{
			name = (TextView) v.findViewById( R.id.f_patient_info_name );
			ahcn = (EditText) v.findViewById( R.id.f_patient_info_ahcn );
			dob = (EditText) v.findViewById( R.id.f_patient_info_dob );
			age = (TextView) v.findViewById( R.id.f_patient_info_age );
			gender = (Spinner) v.findViewById( R.id.f_patient_info_gender );
			history = (ListView) v.findViewById( R.id.f_patient_info_list );
		}

		public void init( Patient data )
		{
			name.setText( data.getName() );
			ahcn.setText( data.getAhcn() );
			dob.setText( sdf.format( data.getDob() ) );
			listAdapter = new PatientHistoryAdapter( getActivity(), data.getHistoryItems() );
			history.setAdapter( listAdapter );

			int yearsOld = Calendar.getInstance().get( Calendar.YEAR ) - // This year
						   data.getDob().get( Calendar.YEAR ); // DOB year

			age.setText( "Age " + yearsOld );

			switch ( data.getGender() )
			{
			case Male:
				gender.setSelection( 0 );
				break;

			case Female:
				gender.setSelection( 1 );
				break;

			case Other:
			default:
				gender.setSelection( 2 );
				break;
			}

			setupEditors( data );
		}

		private void setupEditors( final Patient data )
		{
			dob.setOnClickListener( new View.OnClickListener()
			{
				@Override
				public void onClick( View v )
				{
					DatePickerFragment datePicker = new DatePickerFragment();
					datePicker.show( getChildFragmentManager(), "datePicker" );
				}
			} );

			gender.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
			{
				@Override
				public void onItemSelected( AdapterView< ? > parent,
											View view,
											int position,
											long id )
				{
					// Resolve the string resource.
					String[] genders = Resources.getSystem().getStringArray( R.array.Genders );
				}

				@Override
				public void onNothingSelected( AdapterView< ? > parent )
				{
					// Do nothing!
				}
			} );
		}
	}

	public static class DatePickerFragment
			extends DialogFragment
			implements DatePickerDialog.OnDateSetListener
	{
		@Override
		public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth )
		{
			Calendar c = Calendar.getInstance();
			c.set( Calendar.SECOND, 0 );
			c.set( Calendar.MINUTE, 0 );
			c.set( Calendar.HOUR, 0 );
			c.set( Calendar.DAY_OF_MONTH, dayOfMonth );
			c.set( Calendar.MONTH, monthOfYear );
			c.set( Calendar.YEAR, year );

		}
	}
}
