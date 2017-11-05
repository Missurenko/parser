package http;

import lombok.Data;

@Data
public class TestHttpClient {
    public int port = 80;
    public String hostIpName = "localhost";


    public TestHttpClient(int port, String hostIpName) {
        this.port = port;
        this.hostIpName = hostIpName;
    }

    public TestHttpClient() {
    }

    /**
     *
     * @param rawData Данные что отправить
     * @return данные что ответить
     */
    public String sendData(String rawData){
        //todo send end get
        return rawData;
    }

}
