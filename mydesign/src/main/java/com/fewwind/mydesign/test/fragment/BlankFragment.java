package com.fewwind.mydesign.test.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.fragment.BaseFragment;
import com.fewwind.mydesign.test.adapter.AgeSelectAdapter;
import com.fewwind.mydesign.test.util.DrawableTintUtil;
import com.fewwind.mydesign.utils.DialogSystemUtils;
import com.orhanobut.logger.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tv;
    private Button mBtnDialog;
    private Button mBtnDialog2;
    private Button mBtnDialog1;
    private ImageView mBtnDialog3;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRv;
    private AgeSelectAdapter mAdapter;

    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Logger.v("当前可见---===onCreate" + mParam1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_blank, container, false);
        tv = (TextView) viewRoot.findViewById(R.id.id_test_frag_tv);
        mBtnDialog = (Button) viewRoot.findViewById(R.id.id_test_btn_dialog);
        mBtnDialog1 = (Button) viewRoot.findViewById(R.id.id_test_btn_dialog1);
        mBtnDialog2 = (Button) viewRoot.findViewById(R.id.id_test_btn_dialog2);
        mBtnDialog3 = (ImageView) viewRoot.findViewById(R.id.id_test_btn_dialog3);
        mRv = (RecyclerView) viewRoot.findViewById(R.id.id_test_rv_age);

        mBtnDialog3.setBackground( DrawableTintUtil.$tint(mBtnDialog3.getBackground(), getResources().getColorStateList(R.color.drawabletint)));


        initRecycleView();
        tv.setText(mParam1);
        Logger.d("当前可见---===onCreateView" + mParam1);
        return viewRoot;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i("当前可见---===onViewCreated" + mParam1);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.w("当前可见---===onActivityCreated" + mParam1);

    }


    public void initRecycleView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRv.setLayoutManager(manager);
        mRv.setAdapter(mAdapter = new AgeSelectAdapter(80, 0, getActivity()));


        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                tv.setText(getMiddlePostion() + "");
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mAdapter.setmHighLight(getMiddlePostion());
                    mRv.scrollToPosition(getScrollPostion());

                }
            }
        });


        mAdapter.setmHighLight(getMiddlePostion());
    }

    public int getMiddlePostion() {
        return getScrollPostion() + (AgeSelectAdapter.ITEM_NUM / 2);
    }

    public int getScrollPostion() {
        return mRv.computeHorizontalScrollOffset() / mAdapter.getItemWidth();
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.e("当前可见---===onStart" + mParam1);
        mBtnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });

        mBtnDialog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAnyWhere();
            }
        });
        mBtnDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCustom();
            }
        });
        mBtnDialog3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(),"不变色",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.v("当前可见---===onResume" + mParam1);
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("当前可见---===onPause" + mParam1);

    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.w("当前可见---===onStop" + mParam1);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Logger.i("当前可见---===onDestroyView" + mParam1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.e("当前可见---===onDestroy" + mParam1);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }

        Logger.v("当前可见---onAttach" + mParam1);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Logger.w("当前可见---onDetach" + mParam1);
    }

    @Override
    protected void loadData() {
        Logger.v("当前可见---loadData" + mParam1);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //展示一个可以从底部弹出的dialog，并且充满全屏
    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_bottom, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
    }

    AlertDialog dialog;

    //展示一个可以从底部弹出的dialog，并且充满全屏
    private void showDialogAnyWhere() {

        if (dialog == null) {
            dialog = new AlertDialog.Builder(getActivity().getApplicationContext())
                    .setPositiveButton("OK", null)
                    .setTitle("我不依赖activity")
                    .setMessage("我可以在任何地方创建，因为我用的是全局上下文")
                    .create();


            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }


        if (!dialog.isShowing()) dialog.show();
    }

    private void showDialogCustom() {
        DialogSystemUtils.INSTANCE.showDialog(getActivity(), "对话框");
    }

}
