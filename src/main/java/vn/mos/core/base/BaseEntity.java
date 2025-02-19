package vn.mos.core.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Lớp cơ sở cho tất cả các Entity trong hệ thống, cung cấp thông tin về ngày tạo, ngày cập nhật và người thực hiện.
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // ✅ Bật auditing cho entity
public abstract class BaseEntity {

  /**
   * Ngày tạo bản ghi.
   * Được set tự động khi entity được tạo.
   */
  @CreatedDate
  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  /**
   * Ngày cập nhật bản ghi.
   * Được cập nhật tự động mỗi khi entity thay đổi.
   */
  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  /**
   * Người tạo bản ghi.
   * Cần có AuditorAware để tự động set giá trị.
   */
  @CreatedBy
  @Column(name = "created_by", updatable = false)
  private String createdBy;

  /**
   * Người cập nhật bản ghi.
   * Cần có AuditorAware để tự động set giá trị.
   */
  @LastModifiedBy
  @Column(name = "updated_by")
  private String updatedBy;
}
