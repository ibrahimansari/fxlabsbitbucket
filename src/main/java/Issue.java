import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Issue {
    private String apiEndpoint = "https://api.bitbucket.org/2.0/repositories/ibbiansi/test/issues";
    String token = "Bearer 3e08EA6h65u0paQMkAZ8RAfuNCqVruwz1h_vsIAsXpFxc6AJ5LQvqZdl00hSfYeBHWEFJnG5oFPNGeBMzGo=";
    HttpClient client = HttpClientBuilder.create().build();
    Gson gson = new Gson();

    public static void main(String[] args) {
        Issue issue = new Issue();
        try {
            System.out.println(issue.read());
            //System.out.println(issue.create("test", "Bug", "Details", "minor"));
            System.out.println(issue.update("test", 5, "Fixed", "closed"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String read() throws IOException {
        HttpGet get = new HttpGet(apiEndpoint);
        get.addHeader("Content-Type", "application/json");
        get.addHeader("Authorization", token);
        HttpResponse response = client.execute(get);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public String create(String projectName, String title, String details, String priority) throws IOException {
        String apiEndpoint = "https://api.bitbucket.org/2.0/repositories/ibbiansi/" + projectName + "/issues";
        HttpPost post = new HttpPost(apiEndpoint);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", token);


        String body = "{ \"title\" :" + "\"" + title + "\"" + "," + "\"priority\" :" + "\"" + priority + "\"" + "}";
        StringEntity entity = new StringEntity(body);
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public String update(String projectName, int id, String details, String state) throws IOException {
        String apiEndpoint = "https://api.bitbucket.org/2.0/repositories/ibbiansi/" + projectName + "/issues/" + id;
        HttpPut put = new HttpPut(apiEndpoint);
        put.addHeader("Content-Type", "application/json");
        put.addHeader("Authorization", token);

        String body = "{ \"state\" :" + "\"" + state + "\"" + "}";
        StringEntity entity = new StringEntity(body);
        put.setEntity(entity);

        HttpResponse response = client.execute(put);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}
