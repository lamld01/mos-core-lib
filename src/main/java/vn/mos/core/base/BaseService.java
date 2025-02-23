package vn.mos.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.mos.core.constants.RedisKey;
import vn.mos.core.filter.RequestFilter;
import vn.mos.core.utils.CacheUtil;
import vn.mos.core.utils.MapperUtil;

@Service
public class BaseService {
  @Autowired protected RedisKey redisKey;
  @Autowired protected CacheUtil cacheUtil;
  @Autowired protected MapperUtil mapperUtil;

  protected Long getRequestUserId() {
    String userId = RequestFilter.getUserId();
    return userId == null ? null : Long.valueOf(userId);
  }
}
