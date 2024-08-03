package com.pj.planjourney.global.auth.repository;

import com.pj.planjourney.domain.refreshtoken.Token;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<Token,String> {
}
