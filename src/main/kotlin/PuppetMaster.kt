package org.axix.mirai.plugin.puppetmaster

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.jvm.reloadPluginConfig
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.event.events.NewFriendRequestEvent
import net.mamoe.mirai.message.data.Image
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.info

/**
 * 使用 kotlin 版请把
 * `src/main/resources/META-INF.services/net.mamoe.mirai.console.plugin.jvm.JvmPlugin`
 * 文件内容改成 `org.example.mirai.plugin.PluginMain` 也就是当前主类全类名
 *
 * 使用 kotlin 可以把 java 源集删除不会对项目有影响
 *
 * 在 `settings.gradle.kts` 里改构建的插件名称、依赖库和插件版本
 *
 * 在该示例下的 [JvmPluginDescription] 修改插件名称，id和版本，etc
 *
 * 可以使用 `src/test/kotlin/RunMirai.kt` 在 ide 里直接调试，
 * 不用复制到 mirai-console-loader 或其他启动器中调试
 */

object PuppetMaster : KotlinPlugin(
    JvmPluginDescription(
        id = "org.axix.mirai.plugin.puppetmaster",
        name = "木偶大师",
        version = "0.1.0"
    ) {
        author("轩晞宇-AXiX")
        info(
            """
            这是一个实现了通过认证的QQ号码私聊控制机器人QQ活动的插件。
        """.trimIndent()
        )
        // author 和 info 可以删除.
    }
) {
    private var flag:Int = 0
    //private var targetGroup = Array()
    private var p:Long = 0
    override fun onEnable() {
        logger.info { "Plugin loaded" }
        //配置文件目录 "${dataFolder.absolutePath}/"
        reloadPluginConfig(Config)
        //targetGroup = Config.whiteGroupList
        //监听所有bot
        val eventChannel = GlobalEventChannel.parentScope(this)
        //监听群消息
        eventChannel.subscribeAlways<GroupMessageEvent>{
            //群消息
            //复读示例
            /*
            if (message.contentToString().startsWith("复读")) {
                group.sendMessage(message.contentToString().replace("复读", ""))
            }
            if (message.contentToString() == "hi") {
                //群内发送
                group.sendMessage("hi")
                //向发送者私聊发送消息
                sender.sendMessage("hi")
                //不继续处理
                return@subscribeAlways
            }
            //分类示例
            message.forEach {
                //循环每个元素在消息里
                if (it is Image) {
                    //如果消息这一部分是图片
                    val url = it.queryUrl()
                    group.sendMessage("图片，下载地址$url")
                }
                if (it is PlainText) {
                    //如果消息这一部分是纯文本
                    group.sendMessage("纯文本，内容:${it.content}")
                }
            }

             */
        }
        eventChannel.subscribeAlways<FriendMessageEvent>{
            if(sender.id == Config.adminQQ){
                when(flag){
                    0 -> {
                        if(message.content.contentEquals("/send")){
                            flag = 1
                            sender.sendMessage("请输入要发送的群号")
                        }
                    }
                    1 -> {
                        if(message.content.contentEquals("/")){
                            flag = 0
                            sender.sendMessage("已退出")
                        }else{
                            p = message.content.toLong()
                            sender.sendMessage("$p 请输入要发送的内容")
                            flag = 2
                        }
                    }
                    2 -> {
                        if(message.content.contentEquals("/")){
                            flag = 0
                            sender.sendMessage("已退出")
                        }else{
                            val mcb = MessageChainBuilder().append(message)
                            val group = this.bot.getGroup(p)
                            if(group is net.mamoe.mirai.contact.Group){
                                group.sendMessage(mcb.asMessageChain())
                                sender.sendMessage("已发送")
                            }else{
                                flag = 1
                                sender.sendMessage("群号错误请重新发送")
                            }
                        }
                    }
                }
            }
        }
        eventChannel.subscribeAlways<NewFriendRequestEvent>{
            //自动同意好友申请
            //accept()
        }
        eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent>{
            //自动同意加群申请
            //accept()
        }
    }
}