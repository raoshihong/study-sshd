package com.rao.study.sshd;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Channel的类型可以为如下类型：
 * 1)shell - ChannelShell 
 *
 * 2)exec - ChannelExec 
 *
 * 3)direct-tcpip - ChannelDirectTCPIP 
 *
 * 4)sftp - ChannelSftp 
 *
 * 5)subsystem - ChannelSubsystem
 *
 */

public class SshdTest {
    public static void main(String[] args){
        testShell();
    }

    /**
     * 打开一个shell通道
     * @throws Exception
     */
    public static void testShell() {
        try {
            JSch jSch = new JSch();
            //创建一个与虚拟机linux主机地址为192.168.8.102 用户名为hadoop,通过ssh 登录22端口
            Session session = jSch.getSession("hadoop", "192.168.8.102", 22);
            session.setPassword("123456");

//            UserInfo userInfo = new UserInfo() {
//                public String getPassphrase() {
//                    return null;
//                }
//
//                public String getPassword() {
//                    return null;
//                }
//
//                public boolean promptPassword(String message) {
//                    return false;
//                }
//
//                public boolean promptPassphrase(String message) {
//                    return false;
//                }
//
//                public boolean promptYesNo(String message) {
//                    return true;
//                }
//
//                public void showMessage(String message) {
//
//                }
//            };
//
//            session.setUserInfo(userInfo);

            //设置不用叫校验
            session.setConfig("StrictHostKeyChecking","no");

            //进行连接
            session.connect(30000);

            //连接成功,打开shell通道
            ChannelShell channel = (ChannelShell) session.openChannel("shell");

//            //设置输入流,表示通过控制台输入数据,并转发到终端
//            channel.setInputStream(System.in);
            //设置输出流,表示将终端输出的数据重定向输出到控制台
//            channel.setOutputStream(System.out);

            //通道进行连接
            channel.connect(3000);


//            OutputStream os = channel.getOutputStream();
//            os.write("\r".getBytes());
//            os.flush();

            InputStream is = channel.getInputStream();
            byte[] buff = new byte[1024];
            int length = -1;
            while ((length = is.read(buff))!=-1){
                System.out.write(buff,0,length);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
