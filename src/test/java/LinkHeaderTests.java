import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class LinkHeaderTests {

   /**
    * RESTEASY-1438 https://issues.jboss.org/browse/RESTEASY-1438
    * javax.ws.rs.client.Client does not correctly unmashall the HTTP Link: header
    */
   @Test
   public void testGitHubPagination() {
      Client client = ClientBuilder.newClient();
      Response response = client.target("https://api.github.com/search/code?q=addClass+user:mozilla").request().
         header("User-Agent", "curl/7.47.1").head();  // pretending to be curl to be let in without token
      response.close();
      assertEquals("Request to GitHub should succeed", 200, response.getStatus());
      Set<Link> links = response.getLinks();
      System.out.println("Navigation links are: " + links);
      assertEquals(2, links.size());
   }
}
