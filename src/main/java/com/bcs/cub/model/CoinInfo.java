package com.bcs.cub.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Coin")
public class CoinInfo {

	@Id
	@Column(name = "Currency", length = 10, nullable = false)
	private String currency;

	@Column(name = "CurrencyTW", length = 10, nullable = false)
	private String currencyTW;

	@Column(name = "UpdateDateTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDateTime;

	@Column(name = "Rate")
	private BigDecimal rate;

}
