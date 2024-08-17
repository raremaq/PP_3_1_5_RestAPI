package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // Получаем всех пользователей и session id
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class);
        String sessionId = responseEntity.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        // Сохранить нового пользователя + получение первой части кода
        Map<String, String> newUser = new HashMap<>();
        newUser.put("id", "3");
        newUser.put("name", "James");
        newUser.put("lastName", "Brown");
        newUser.put("age", "25");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(newUser, headers);
        restTemplate.postForObject(url, httpEntity, String.class);
        ResponseEntity<String> postResponse = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println("Response after save: " + postResponse.getBody());

        //Изменить пользователя с id = 3 + получение второй части кода
        Map<String, String> updateUser = new HashMap<>();
        updateUser.put("id", "3");
        updateUser.put("name", "Thomas");
        updateUser.put("lastName", "Shelby");
        updateUser.put("age", "22");

        HttpEntity<Map<String, String>> httpEntity1 = new HttpEntity<>(updateUser, headers);
        ResponseEntity<String> responseEntity1 = restTemplate.exchange(url, HttpMethod.PUT, httpEntity1, String.class);
        System.out.println("Response after update: " + responseEntity1.getBody());

        //Удалить пользователя с id = 3 + получение 3 части кода
        String urlDelete = "http://94.198.50.185:7081/api/users/3";
        HttpEntity<String> httpEntity2 = new HttpEntity<>(urlDelete, headers);
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(urlDelete, HttpMethod.DELETE, httpEntity2, String.class);
        System.out.println("Response after delete: " + responseEntity2.getBody());
        System.out.println("Response all: " + postResponse.getBody() + responseEntity1.getBody() + responseEntity2.getBody());

    }
}