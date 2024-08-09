package com.pj.planjourney.domain.refreshtoken.repository;

import com.pj.planjourney.domain.refreshtoken.Token;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<Token,String> {
}
