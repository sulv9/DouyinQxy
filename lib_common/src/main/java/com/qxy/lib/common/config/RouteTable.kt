package com.qxy.lib.common.config

/**
 * 路由表命名格式：
 * 1. 常量: 模块名_功能描述 如PERSONAL_ENTRY
 * 2. 二级路由: /模块名/功能描述 如/personal/entry
 * 3. 多级路由:
 */
object RouteTable {
    // account模块
    const val ACCOUNT_SERVICE = "/account/service"

    // rank模块
    const val RANK_ENTRY = "/rank/entry"

    // login模块
    const val LOGIN_ENTRY = "/login/entry"

    // personal模块
    const val PERSONAL_ENTRY = "/personal/entry"
}