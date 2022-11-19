package org.axix.mirai.plugin.puppetmaster

import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.console.MiraiConsole
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.enable
import net.mamoe.mirai.console.plugin.PluginManager.INSTANCE.load
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader

suspend fun main() {
    MiraiConsoleTerminalLoader.startAsDaemon()

    //如果是Kotlin
    PuppetMaster.load()
    PuppetMaster.enable()
    //如果是Java
//    JavaPluginMain.INSTANCE.load()
//    JavaPluginMain.INSTANCE.enable()

    val bot = MiraiConsole.addBot(3092539395, "1414144gudu") {
        fileBasedDeviceInfo()
    }.alsoLogin()

    MiraiConsole.job.join()
}