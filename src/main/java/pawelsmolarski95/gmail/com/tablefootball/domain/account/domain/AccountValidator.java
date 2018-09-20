package pawelsmolarski95.gmail.com.tablefootball.domain.account.domain;

import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.NotFoundException;
import pawelsmolarski95.gmail.com.tablefootball.infrastructure.exception.ResourceConflictException;

class AccountValidator {

    //    todo ps validation if needed
    void validateExistence(Runnable finder){
        try {
            finder.run();
            throw new ResourceConflictException("Username already exists");
        }catch(NotFoundException e){
            // does nothing
        }
    }

}
