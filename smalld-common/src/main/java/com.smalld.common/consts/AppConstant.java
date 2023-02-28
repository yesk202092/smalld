
package com.smalld.common.consts;

/**
 * @author : yesk
 * @classname : AppConstant
 * @description : 应用静态常量
 * @date 2022/10/19 2:47
 */
public class AppConstant {

    /**
     * 团队名称
     */
    public static final String TEAM_NAME = "smalld";


    /** 应用启动信息常量 **/
    public static final String APP_START_INFO =
            "\n"+
            "==============================================================\n"+
            "\tApp:\t{}\n"+
            "\tState:\tapp is running\n"+
            "\tPID:\t{}\n"+
            "\tDate:\tstarted at {}\n"+
            "\tAuth:\t"+TEAM_NAME+"\n"+
            "\tURLs:\thttp://{}:{}{}\n"+
            "==============================================================";

}
