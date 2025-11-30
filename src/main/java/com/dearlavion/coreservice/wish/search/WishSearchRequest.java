package com.dearlavion.coreservice.wish.search;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class WishSearchRequest {
    private String keyword;          // search in title/body
    private String location;
    private List<String> categories; // multiple categories
    private String status;           // OPEN/ONGOING/COMPLETED
    private String rateType;         // FREE/PAID
    private String username;         // filter owner
    private Instant startDateFrom;
    private Instant startDateTo;
    private BigDecimal amountFrom;
    private BigDecimal amountTo;
    private int page = 0;
    private int size = 10;
    private String sortBy; // values: createdAtDesc, startDateAsc, startDateDesc

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRateType() { return rateType; }
    public void setRateType(String rateType) { this.rateType = rateType; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Instant getStartDateFrom() { return startDateFrom; }
    public void setStartDateFrom(Instant startDateFrom) { this.startDateFrom = startDateFrom; }
    public Instant getStartDateTo() { return startDateTo; }
    public void setStartDateTo(Instant startDateTo) { this.startDateTo = startDateTo; }
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    public int getSize() {return size; }
    public void setSize(int size) { this.size = size; }
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    public BigDecimal getAmountFrom() { return amountFrom; }
    public void setAmountFrom(BigDecimal amountFrom) { this.amountFrom = amountFrom; }
    public BigDecimal getAmountTo() { return amountTo; }
    public void setAmountTo(BigDecimal amountTo) { this.amountTo = amountTo; }
}
