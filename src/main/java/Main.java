import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Main {
    static String getUrl = "http://94.198.50.185:7081/api/users";//Получение всех пользователей
    static String postUrl = "http://94.198.50.185:7081/api/users";//Добавление пользователя
    static String putUrl = "http://94.198.50.185:7081/api/users";//Изменение пользователя
    static String deleteUrl = "http://94.198.50.185:7081/api/users/{id}"; //Удаление пользователя /api/users/{id}

    public static void main(String[] args) throws URISyntaxException {
        StringBuilder finalHashCode = new StringBuilder();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> httpEntity = new HttpEntity<>(header);

        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<User[]> responseEntity = restTemplate1
                .exchange(getUrl, HttpMethod.GET, httpEntity, User[].class);
        Arrays.stream(responseEntity.getBody()).forEach(user -> System.out.println(user));
        System.out.println(responseEntity.getHeaders().getClass());
        String valueHeaderSetCookie = responseEntity.getHeaders().get("Set-Cookie").get(0);

        HttpHeaders headerForTransfer = new HttpHeaders();
//        headerForTransfer.setContentType(MediaType.APPLICATION_JSON);
        headerForTransfer.add("Cookie", valueHeaderSetCookie);

        User newUser = new User((long) (3), "James", "Brown", (byte) 36);
        URI uri = new URI(postUrl);
        HttpEntity<User> httpEntity2 = new HttpEntity<>(newUser, headerForTransfer);
        RestTemplate restTemplate2 = new RestTemplate();
        ResponseEntity<String> responseEntity2 = restTemplate2.exchange(uri, HttpMethod.POST, httpEntity2, String.class);
        finalHashCode.append(responseEntity2.getBody());
        System.out.println(finalHashCode);

//        RestTemplate restTemplate3 = new RestTemplate();
//        ResponseEntity<User[]> responseEntity3 = restTemplate3
//                .exchange(getUrl, HttpMethod.GET, new HttpEntity<>(headerForTransfer), User[].class);
//        Arrays.stream(responseEntity3.getBody()).forEach(user -> System.out.println(user));

        newUser.setName("Thomas");
        newUser.setLastName("Shelby");

        URI uriPut = new URI(putUrl);
        HttpEntity<User> httpEntity4 = new HttpEntity<>(newUser, headerForTransfer);
        RestTemplate restTemplate4 = new RestTemplate();
        ResponseEntity<String> responseEntity4 = restTemplate4.exchange(uriPut, HttpMethod.PUT, httpEntity4, String.class);
        finalHashCode.append(responseEntity4.getBody());
        System.out.println(finalHashCode);



        HttpEntity<?> httpEntity6 = new HttpEntity<>(headerForTransfer);
        RestTemplate restTemplate6 = new RestTemplate();
        long userIdForDelete = 3;
        ResponseEntity<String> responseEntity6 = restTemplate6.exchange(deleteUrl, HttpMethod.DELETE, httpEntity6, String.class, userIdForDelete);
        finalHashCode.append(responseEntity6.getBody());
        System.out.println("Наконец-таки, ответ: "+finalHashCode);
    }

}
