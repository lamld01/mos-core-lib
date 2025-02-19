package vn.mos.core.sercurities.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.mos.core.base.type.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenUserInfo {
  private String username;
  private Long userId;
  private Long managerId;
  private UserRole userRole;
}
