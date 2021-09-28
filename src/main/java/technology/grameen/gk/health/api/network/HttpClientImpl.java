package technology.grameen.gk.health.api.network;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import technology.grameen.gk.health.api.projection.authserver.AdminAcessToken;
import technology.grameen.gk.health.api.projection.authserver.Role;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpClientImpl implements HttpClient {

    private HttpHeaders httpHeaders;
    private RestTemplate restTemplate;

    private String authServerUrl="http://103.26.136.30:8080";
    private String adminAccessTokenUrl = authServerUrl+"/auth/realms/master/protocol/openid-connect/token";
    private String realmRolesUrl = authServerUrl+"/auth/admin/realms/GK_HEALTH/roles";
    private String clientRolesUrl = authServerUrl+"/auth/admin/realms/GK_HEALTH/clients/3cefb971-7e37-445a-902a-318ec3cf4fd2/roles";

    public HttpClientImpl(){
        httpHeaders = new HttpHeaders();
        restTemplate = new RestTemplate();
    }


    @Override
    public AdminAcessToken getAdminAccessToken() {
        httpHeaders = new HttpHeaders();
        restTemplate = new RestTemplate();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        AdminAcessToken response = null;

        String url = adminAccessTokenUrl;
        MultiValueMap<String,String> requestData = new LinkedMultiValueMap<>();
               requestData.add("grant_type","password");
               requestData.add("username","admin");
               requestData.add("password","admin");
               requestData.add("client_id","admin-cli");
        HttpEntity<MultiValueMap<String,String>> requestBody = new HttpEntity<>(requestData,httpHeaders);
        response = restTemplate.postForObject(url,requestBody,AdminAcessToken.class);


        return response;
    }

    @Override
    public List<Object> getRealmRoles(AdminAcessToken token) {
        List<Object> roles = new ArrayList<>();
        httpHeaders.add("Authorization","Bearer "+token.getAccess_token());
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(httpHeaders);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(realmRolesUrl);
        ResponseEntity<List> rolesEntity = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,List.class);
        rolesEntity.getBody().stream().filter((r) ->{
            LinkedHashMap<String,String> role = (LinkedHashMap<String, String>) r;
            Boolean offlineAccess = role.get("name").contains("offline_access");
            Boolean umaAuthorization = role.get("name").contains("uma_authorization");
            if(!offlineAccess && !umaAuthorization){
                roles.add(r);
            }
            return (!offlineAccess && !umaAuthorization);
        }).collect(Collectors.toList());
        return roles;
    }

    @Override
    public List<Object> getClientRoles(AdminAcessToken token) {
        List<Object> roles = new ArrayList<>();
        httpHeaders.add("Authorization","Bearer "+token.getAccess_token());
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(httpHeaders);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(clientRolesUrl);
        ResponseEntity<List> rolesEntity = restTemplate.exchange(builder.toUriString(),HttpMethod.GET,entity,List.class);
        rolesEntity.getBody().stream().filter((r) ->{
            LinkedHashMap<String,String> role = (LinkedHashMap<String, String>) r;
            Boolean offlineAccess = role.get("name").contains("offline_access");
            Boolean umaAuthorization = role.get("name").contains("uma_authorization");
            if(!offlineAccess && !umaAuthorization){
                roles.add(r);
            }
            return (!offlineAccess && !umaAuthorization);
        }).collect(Collectors.toList());
        return roles;
    }
}
