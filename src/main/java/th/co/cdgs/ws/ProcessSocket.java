package th.co.cdgs.ws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import th.co.cdgs.employee.ImportEmployeeResource;

@ServerEndpoint("/process/{username}")
@Singleton
public class ProcessSocket {


    private static final Logger LOG = Logger.getLogger(ProcessSocket.class);

    @Inject
    ImportEmployeeResource importEmployeeResource;

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username,
            Throwable throwable) {
        sessions.remove(username);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        if ("gen".equals(message)) {
            importEmployeeResource.genAsyncSignle(username);
        }
    }

    public void sendMessage(String user, String message) {
        Session session = sessions.get(user);
        if (session != null) {
            session.getAsyncRemote().sendObject(message);
        }
    }

}
