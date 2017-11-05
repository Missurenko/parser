package http;

public class Runner {
    public static void main(String[] args) {
        int cfsPort = 7000;
        int listenPort = 5555;
        String cfsHost = "localhost";
        TestHttpClient client = new TestHttpClient(cfsPort, cfsHost);
        TestHttpServer server = new TestHttpServer(listenPort, client);
        try {
            server.start();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
