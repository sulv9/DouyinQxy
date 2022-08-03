package com.qxy.codeerror

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_send_auth).setOnClickListener {
//            ARouterUtil.getService<IAccountService>("/account/service")
        }
    }
}