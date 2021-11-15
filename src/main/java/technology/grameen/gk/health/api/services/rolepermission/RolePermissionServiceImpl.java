package technology.grameen.gk.health.api.services.rolepermission;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technology.grameen.gk.health.api.repositories.MenuPermissionRepository;
import technology.grameen.gk.health.api.requests.RolePermission;

@Service
public class RolePermissionServiceImpl implements RolePermissionService{

    private MenuPermissionRepository menuPermissionRepository;

    RolePermissionServiceImpl(MenuPermissionRepository menuPermissionRepository){
        this.menuPermissionRepository = menuPermissionRepository;
    }

    @Override
    @Transactional
    public Boolean saveRolePermission(RolePermission rolePermission) {
        try {
            this.menuPermissionRepository.saveAll(rolePermission.getPermissions());
        }catch(Exception ex){
            throw new RuntimeException("Sorry! try again later");
        }
        return true;
    }
}
