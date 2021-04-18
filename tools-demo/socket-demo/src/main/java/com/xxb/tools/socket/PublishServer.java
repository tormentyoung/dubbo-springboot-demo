package com.xxb.tools.socket;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class PublishServer extends ServerSocket {
    public PublishServer(int port) throws IOException {
        super(port);
        System.out.println(String.format("服务已启动，对应端口:%s", port));
    }


    public void load() throws Exception {
        while (true) {
            Socket socket = accept();
            (new Thread(new Task(socket))).start();
        }
    }

    class Task implements Runnable {
        private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        private Socket socket;

        private DataInputStream dis;

        private FileOutputStream fos;

        public Task(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                this.dis = new DataInputStream(this.socket.getInputStream());
                String fileName = this.dis.readUTF();
                File directory = new File(getCurrentJarPath());
                if (!directory.exists()) {
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
                this.fos = new FileOutputStream(file);


                byte[] bytes = new byte[1024];
                int length = 0;
                while ((length = this.dis.read(bytes, 0, bytes.length)) != -1) {
                    this.fos.write(bytes, 0, length);
                    this.fos.flush();
                }
                System.out.println("======== 文件接收成功 [File Name：" + fileName + "]  " + this.dateTimeFormatter.format(System.currentTimeMillis()) + "========");
                restartServer();
                System.out.println("======== 已重启服务  " + this.dateTimeFormatter.format(System.currentTimeMillis()) + "========");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (this.fos != null)
                        this.fos.close();
                    if (this.dis != null)
                        this.dis.close();
                    this.socket.close();
                } catch (Exception exception) {
                }
            }
        }

        private void restartServer() throws IOException {
            String cmd = "cmd /c start " + getCurrentJarPath() + File.separatorChar + "restart.bat";
            Runtime.getRuntime().exec(cmd);
        }

        public String getCurrentJarPath() {
            String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            if (System.getProperty("os.name").contains("dows")) {
                path = path.substring(1);
            }
            if (path.contains("jar")) {
                path = path.substring(0, path.lastIndexOf("."));
                return path.substring(0, path.lastIndexOf("/"));
            }
            return path.replace("target/classes/", "");
        }
    }

    public static void main(String[] args) {
        String port = System.getProperty("port");
        port = port == null ? "8199" : port;
        try {
            PublishServer server = new PublishServer(Integer.parseInt(port));
            server.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
