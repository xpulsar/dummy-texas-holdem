package vip.dummy.texasholdem;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import vip.dummy.texasholdem.bean.Credential;
import vip.dummy.texasholdem.indication.NewPeerIndication;
import vip.dummy.texasholdem.indication.NewRoundIndication;
import vip.dummy.texasholdem.utils.MD5Util;

import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;

/**
 * Created by the-engine team
 * 2017-09-12
 *
 * Texas Hold'em AI client Java example
 */
public class TheClient {

    // private static final String HOST_ADDR = "ws://116.62.205.153/game/";
    private static final String HOST_ADDR = "ws://localhost:8080";

    public static void main(String[] args) throws DeploymentException, IOException,
            URISyntaxException, InterruptedException {

        // resolve user credential
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("credential.json"));
        Credential credential = gson.fromJson(reader, Credential.class);

        if (null == credential.getPhoneNumber() || credential.getPhoneNumber().equals("")) {
            System.out.println("login username (phone number) is required");
            return;
        }
        System.out.println("your login username : " + credential.getPhoneNumber());
        if (null == credential.getPassword() || credential.getPassword().equals("")) {
            Console console = System.console();
            String password = "";
            while(null == password || password.equals("")) {
                password =
                        new String(console.readPassword("Please enter your password: "));
            }
            credential.setPassword(MD5Util.MD5Encode(password, null));
        } else {
            credential.setPassword(MD5Util.MD5Encode(credential.getPassword(), null));
        }

        System.out.println("your login password : " + credential.getPassword());

        Scanner input = new Scanner(System.in);

        String ticket = "";
        while(null == ticket || ticket.equals("")) {
            System.out.print("Please enter game ticket : ");
            ticket = input.next();
        }
        System.out.println("your game ticket : " + ticket);

        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        WebSocketClient client = new WebSocketClient(credential, ticket);
        container.connectToServer(client,
                new URI(HOST_ADDR));

        while(true) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}