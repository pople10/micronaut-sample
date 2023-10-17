/* Mohammed Amine AYACHE (C)2023 */
package com.sample.services;

import com.sample.repositories.entities.User;

public interface AuthService {
    User getCurrentUser();

    User getCurrentUserSafe();
}
