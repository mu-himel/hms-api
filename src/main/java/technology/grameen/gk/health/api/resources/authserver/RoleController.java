package technology.grameen.gk.health.api.resources.authserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import technology.grameen.gk.health.api.network.HttpClient;
import technology.grameen.gk.health.api.projection.authserver.AdminAcessToken;
import technology.grameen.gk.health.api.projection.authserver.Role;
import technology.grameen.gk.health.api.requests.RolePermission;
import technology.grameen.gk.health.api.responses.EntityCollectionResponse;
import technology.grameen.gk.health.api.responses.EntityResponse;
import technology.grameen.gk.health.api.responses.IResponse;
import technology.grameen.gk.health.api.responses.SimpleResponse;
import technology.grameen.gk.health.api.services.rolepermission.RolePermissionService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private HttpClient httpClient;
    private RolePermissionService rolePermissionService;

    RoleController(HttpClient httpClient,RolePermissionService rolePermissionService){
        this.httpClient = httpClient;
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping("")
    public ResponseEntity<IResponse> getRoles(){

        AdminAcessToken adminAccessToken = httpClient.getAdminAccessToken();
        List realmRoles = httpClient.getRealmRoles(adminAccessToken);
        AdminAcessToken adminAccessToken1 = httpClient.getAdminAccessToken();
        List clientRoles = httpClient.getClientRoles(adminAccessToken1);
        Stream<Object> combineResult = Stream.of(realmRoles,clientRoles).flatMap(Collection::stream);
        Set<Object> combinedSet = combineResult.collect(Collectors.toSet());
        List<Object> combinedCollection = Arrays.asList(combinedSet.toArray());
        return new ResponseEntity<>(new EntityCollectionResponse<>(HttpStatus.OK.value(), combinedCollection),
                HttpStatus.OK);
    }

    @PostMapping("/permissions")
    public ResponseEntity<IResponse> setRolePermission(@RequestBody RolePermission rolePermission){
        rolePermissionService.saveRolePermission(rolePermission);
        return new ResponseEntity<>(new SimpleResponse(HttpStatus.OK.value(),
                    "Roles permission changes are saved"),HttpStatus.OK);
    }
}
