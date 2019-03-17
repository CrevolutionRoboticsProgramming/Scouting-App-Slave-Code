package com.example.kyles.teleopscreen;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link teleop.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link teleop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teleop extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public teleop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teleop.
     */
    // TODO: Rename and change types and number of parameters
    public static teleop newInstance(String param1, String param2) {
        teleop fragment = new teleop();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }
    ImageView imageView;
    RadioGroup radioGroup;

    int rocket1LowHatch;
    int rocket1MidHatch;
    int rocket1HighHatch;
    int rocket1LowCargo;
    int rocket1MidCargo;
    int rocket1HighCargo;
    int cargoShipHatch;
    int cargoShipCargo;
    int rocket2LowHatch;
    int rocket2MidHatch;
    int rocket2HighHatch;
    int rocket2LowCargo;
    int rocket2MidCargo;
    int rocket2HighCargo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_endgame, container, false);
        TextView rocket1Info = v.findViewById(R.id.rocketOneInfo);
        TextView cargoShipInfo = v.findViewById(R.id.cargoShipInfo);
        TextView rocket2Info = v.findViewById(R.id.rocket2Info);
        Bundle bundle = getArguments();

        rocket1LowHatch = bundle.getInt("ROCKET1_LOW_HATCH");
        rocket1MidHatch = bundle.getInt("ROCKET1_MID_HATCH");
        rocket1HighHatch = bundle.getInt("ROCKET1_HIGH_HATCH");
        rocket1LowCargo = bundle.getInt("ROCKET1_LOW_CARGO");
        rocket1MidCargo = bundle.getInt("ROCKET1_MID_CARGO");
        rocket1HighCargo = bundle.getInt("ROCKET1_HIGH_CARGO");

        cargoShipHatch = bundle.getInt("CARGO_SHIP_HATCH");
        cargoShipCargo = bundle.getInt("CARGO_SHIP_CARGO");

        rocket2LowHatch = bundle.getInt("ROCKET2_LOW_HATCH");
        rocket2MidHatch = bundle.getInt("ROCKET2_MID_HATCH");
        rocket2HighHatch = bundle.getInt("ROCKET2_HIGH_HATCH");
        rocket2LowCargo = bundle.getInt("ROCKET2_LOW_CARGO");
        rocket2MidCargo = bundle.getInt("ROCKET2_MID_CARGO");
        rocket2HighCargo = bundle.getInt("ROCKET2_HIGH_CARGO");

        String rocket1String = rocket1Info(rocket1LowHatch, rocket1MidHatch, rocket1HighHatch,
                rocket1LowCargo, rocket1MidCargo, rocket1HighCargo);
        String cargoShipString = cargoShipInfo(cargoShipHatch, cargoShipCargo);
        String rocket2String = rocket2Info(rocket2LowHatch, rocket2MidHatch, rocket2HighHatch,
                rocket2LowCargo, rocket2MidCargo, rocket2HighCargo);
        rocket1Info.setText(rocket1String);
        cargoShipInfo.setText(cargoShipString);
        rocket2Info.setText(rocket2String);

        return v;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public String rocket1Info (int rocket1LowHatch, int rocket1MidHatch, int rocket1HighHatch,
                             int rocket1LowCargo, int rocket1MidCargo, int rocket1HighCargo) {

        String rocket1String = "Low Hatches: " + rocket1LowHatch
                + System.lineSeparator() + "Mid Hatches: " + rocket1MidHatch
                + System.lineSeparator() + "High Hatches: " + rocket1HighHatch
                + System.lineSeparator() + "Low Cargo: " + rocket1LowCargo
                + System.lineSeparator() + "Mid Cargo: " + rocket1MidCargo
                + System.lineSeparator() + "High Cargo: " + rocket1HighCargo;
        return rocket1String;
    }
    public String cargoShipInfo(int cargoShipHatch, int cargoShipCargo) {
        String cargoShipString = "Hatches Scored: " + cargoShipHatch
                + System.lineSeparator() + "Cargo Scored: " + cargoShipCargo;
        return cargoShipString;
    }
    public String rocket2Info(int rocket2LowHatch, int rocket2MidHatch, int rocket2HighHatch,
                            int rocket2LowCargo, int rocket2MidCargo, int rocket2HighCargo) {
        String rocket2String = "Low Hatches: " + rocket2LowHatch
                + System.lineSeparator() + "Mid Hatches: " + rocket2MidHatch
                + System.lineSeparator() + "High Hatches: " + rocket2HighHatch
                + System.lineSeparator() + "Low Cargo: " + rocket2LowCargo
                + System.lineSeparator() + "Mid Cargo: " + rocket2MidCargo
                + System.lineSeparator() + "High Cargo: " + rocket2HighCargo;
       return rocket2String;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
