package vn.mos.core.base.type;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {
  private List<T> data;
  private int page;
  private int totalPages;
  private long totalElements;
  public PageResponse(List<T> data, int page, int totalPages, long totalElements) {
    this.data = data;
    this.page = page;
    this.totalPages = totalPages;
    this.totalElements = totalElements;
  }
  public PageResponse(Page<T> page){
    this.data = page.getContent();
    this.page = page.getNumberOfElements();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
  }

  public static <T> PageResponse<T> fromPage(Page<T> page) {
    return new PageResponse<>(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
  }
}
