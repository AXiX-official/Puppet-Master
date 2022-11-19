package org.axix.mirai.plugin.puppetmaster

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object Config : AutoSavePluginConfig("config") {
    //val imageAPIs: MutableList<String> by value(mutableListOf(R.IMG_API_URL))
    //val whiteGroupList: MutableList<Long> by value(mutableListOf(526872028))
    //val whiteQQList: MutableList<Long> by value(mutableListOf(2879710747))
    val adminQQ: Long by value<Long>(2879710747)
    //val commands: List<String> by value(listOf(R.DEFAULT_COMMAND))

    // 当网络连接出现故障时，重试的次数
    //val retryCount: Int by value(R.DEFAULT_RETRY_COUNT)

    // 冷却时间ms
    //val cd: Int by value(R.DEFAULT_CD)
}