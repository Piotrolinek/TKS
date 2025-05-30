package pl.lodz.p.rest.model.user;

import lombok.NoArgsConstructor;
import pl.lodz.p.rest.model.RESTMongoUUID;
import pl.lodz.p.rest.model.user.RESTRole;
import pl.lodz.p.rest.model.user.RESTUser;

import java.util.UUID;

@NoArgsConstructor
public class RESTResourceManager extends RESTUser {
    public RESTResourceManager(String firstName, String surname, String username, String password, String emailAddress) {
        super(new RESTMongoUUID(UUID.randomUUID()), firstName, username, password, surname, emailAddress, pl.lodz.p.rest.model.user.RESTRole.RESOURCE_MANAGER, true);
    }

    public RESTResourceManager(RESTMongoUUID uuid, String firstName, String surname, String username, String password, String emailAddress) {
        super(uuid, firstName, username, password, surname, emailAddress, RESTRole.RESOURCE_MANAGER, true);
    }

    @Override
    public String toString() {
        return super.toString() + "::ResourceManager{}";
    }
}
