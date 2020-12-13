//package com.arc.tool.activity.test1;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import com.arc.tool.R;
//
///**
// * @author may
// */
//public class ViewActivity extends Activity implements View.OnClickListener {
//    private ProgressBar processBar2;
//    private EditText testEditText;
//
//
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //绑定视图
//        setContentView(R.layout.activity_view);
//        // 在Activity中通过findViewById来查找这个控件
//        findViewById(R.id.testBtn).setOnClickListener(this);
//        testEditText = (EditText) findViewById(R.id.testEditText);
//        // processBar2 是直线加载样式的进度条， 转圈的进度条如何控制？
//        processBar2 = findViewById(R.id.processBar2);
//    }
//
//
//    /**
//     * 按钮点击事件
//     *
//     * @param v
//     */
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.testBtn) {
////            String text = testEditText.getText().toString();//获取输入框的值
////            String msg = "点我了，获取输入框的值=" + text;
////            Toast.makeText(this, "=-=  \n" + msg, Toast.LENGTH_SHORT).show();
//
//            //测试进度条 模拟， 每一次点击增加一点
//            int progress = processBar2.getProgress();
//            progress += 10;
//            if (progress > 100) {
//                progress = 0;
//            }
//            processBar2.setProgress(progress);
//        }
//    }
//
//
//    /**
//     * 测试 弹出框
//     */
//    @Override
//    public void onBackPressed() {
//        //        super.onBackPressed();
//        //返回时拦截
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("退出");
//        builder.setMessage("确认退出吗？");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //点击 ”确定“后的操作
//                dialog.dismiss();
//                ViewActivity.this.finish();
//            }
//
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //取消
//                dialog.dismiss();
//            }
//        });
//        //显示
//        builder.show();
//    }
//}
