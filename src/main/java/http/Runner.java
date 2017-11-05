package http;

public class Runner {
    public static void main(String[] args) {
        TestHttpClient client = new TestHttpClient();
        TestHttpServer server = new TestHttpServer(5555, client);
    }
}
