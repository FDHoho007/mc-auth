package de.myfdweb.woc.mcplugins.mcauth;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class REST extends Thread {

    private Socket socket;

    public REST(Socket socket) {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream());
            StringTokenizer parse = new StringTokenizer(in.readLine());
            parse.nextToken();
            Token token = Token.getByToken(parse.nextToken().substring(1));
            JSONObject json = new JSONObject();
            if (token == null)
                json.put("error", "The given token cannot be resolved to a UUID. View Docs for more.");
            else if (!token.isValid())
                json.put("error", "This token expired. You only have limited time use a token. Please request a new one.");
            else {
                json.put("uuid", token.getUUID());
                token.remove();
            }
            byte[] output = json.toJSONString().getBytes();
            out.println("HTTP/1.1 200 OK");
            out.println("Server: Minecraft Auth by FDHoho007");
            out.println("Source: github.com/FDHoho007/mc-auth");
            out.println("Date: " + new Date());
            out.println("Content-type: text/json");
            out.println("Content-length: " + output.length);
            out.println();
            out.flush();
            dataOut.write(output, 0, output.length);
            dataOut.flush();
            try {
                in.close();
                out.close();
                dataOut.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
