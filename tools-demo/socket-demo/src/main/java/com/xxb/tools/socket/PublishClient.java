package com.xxb.tools.socket;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class PublishClient extends Socket{

	private static final String SERVER_IP = "192.168.51.7"; // 服务端IP
    private static final int SERVER_PORT = 8090; // 服务端端口
 
    private Socket client;
 
    /**
     * 构造函数<br/>
     * 与服务器建立连接
     * @throws Exception
     */
    public PublishClient() throws Exception {
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
    }
 
    /**
     * 向服务端传输文件
     * @throws Exception
     */
    public void sendFile() throws Exception {
    	FileInputStream fis = null;
        DataOutputStream dos = null;
        try {
            File file = new File(getJarPath());
            if(file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());
 
                // 文件名和长度
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();
 
                // 开始传输文件
                System.out.println("======== 开始传输文件 ========");
                byte[] bytes = new byte[1024];
                int length = 0;
                long sent = 0;
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    sent += length;
                    System.out.println("已传输： "+sent+" / " + file.length());
                }
                System.out.println("======== 文件传输成功 ========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fis != null)
                fis.close();
            if(dos != null)
                dos.close();
            client.close();
        }
    }
    
    private String getJarPath(){
    	File f = new File(PublishClient.class.getResource("/").getPath());
    	return f.getParent() + File.separatorChar + "weaver-ebuilder-app-service-0.0.1-SNAPSHOT.jar";
    }

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			PublishClient client = new PublishClient(); // 启动客户端连接
            client.sendFile(); // 传输文件
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
