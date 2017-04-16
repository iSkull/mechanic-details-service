package com.asia.promo.mechanic.model;

import java.util.List;

import javax.persistence.Id;

import com.asia.promo.mechanic.resource.WorkshopServiceType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkshopService {
	@Id
	private String id;
	private String userId;
	private String mechanicName;
	private String checkInDate;
	private String workshopAddress;
	private WorkshopServiceType type;
	private List<WorkshopServiceItem> items;
}
