package edu.uark.uarkregisterapp.models.api;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import edu.uark.uarkregisterapp.models.api.enums.ProductApiRequestStatus;
import edu.uark.uarkregisterapp.models.api.fields.ProductFieldName;
import edu.uark.uarkregisterapp.models.api.interfaces.ConvertToJsonInterface;
import edu.uark.uarkregisterapp.models.api.interfaces.LoadFromJsonInterface;
import edu.uark.uarkregisterapp.models.transition.ProductTransition;

public class Product implements ConvertToJsonInterface, LoadFromJsonInterface<Product> {

	@Override
	public Product loadFromJson(JSONObject rawJsonObject) {
		String value = rawJsonObject.optString(ProductFieldName.ID.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.id = UUID.fromString(value);
		}
		this.lookupCode = rawJsonObject.optString(ProductFieldName.LOOKUP_CODE.getFieldName());
		this.firstname = rawJsonObject.optString(ProductFieldName.FIRST_NAME.getFieldName());
		this.count = rawJsonObject.optInt(ProductFieldName.COUNT.getFieldName());

		value = rawJsonObject.optString(ProductFieldName.CREATED_ON.getFieldName());
		if (!StringUtils.isBlank(value)) {
			try {
				this.createdOn = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US).parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		this.apiRequestMessage = rawJsonObject.optString(ProductFieldName.API_REQUEST_MESSAGE.getFieldName());

		value = rawJsonObject.optString(ProductFieldName.API_REQUEST_STATUS.getFieldName());
		if (!StringUtils.isBlank(value)) {
			this.apiRequestStatus = ProductApiRequestStatus.mapName(value);
		}

		return this;
	}

	@Override
	public JSONObject convertToJson() {
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put(ProductFieldName.ID.getFieldName(), this.id.toString());
			jsonObject.put(ProductFieldName.LOOKUP_CODE.getFieldName(), this.lookupCode);
			jsonObject.put(ProductFieldName.FIRST_NAME.getFieldName(),this.firstname);
			jsonObject.put(ProductFieldName.COUNT.getFieldName(), this.count);
			jsonObject.put(ProductFieldName.CREATED_ON.getFieldName(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)).format(this.createdOn));
			jsonObject.put(ProductFieldName.API_REQUEST_MESSAGE.getFieldName(), this.apiRequestMessage);
			jsonObject.put(ProductFieldName.API_REQUEST_STATUS.getFieldName(), this.apiRequestStatus.name());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public Product() {
		this.count = -1;
		this.lookupCode = "";
		this.firstname = "";
		this.id = new UUID(0, 0);
		this.createdOn = new Date();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.apiRequestStatus = ProductApiRequestStatus.OK;
	}

	public Product(ProductTransition productTransition) {
		this.id = productTransition.getId();
		this.count = productTransition.getCount();
		this.apiRequestMessage = StringUtils.EMPTY;
		this.createdOn = productTransition.getCreatedOn();
		this.apiRequestStatus = ProductApiRequestStatus.OK;
		this.lookupCode = productTransition.getLookupCode();
		this.firstname = productTransition.getFirstname();
	}

	private UUID id;
	private String lookupCode;
	private String firstname;
	private int count;
	private Date createdOn;
	private ProductApiRequestStatus apiRequestStatus;
	private String apiRequestMessage;

	public UUID getId() {
		return this.id;
	}
	public String getLookupCode() {
		return this.lookupCode;
	}
	public String getFirstName(){return this.firstname;}
	public int getCount() {
		return this.count;
	}
	public Date getCreatedOn() {
		return this.createdOn;
	}
	public ProductApiRequestStatus getApiRequestStatus() {
		return this.apiRequestStatus;
	}
	public String getApiRequestMessage() {
		return this.apiRequestMessage;
	}

	public Product setId(UUID id) {
		this.id = id;
		return this;
	}
	public Product setLookupCode(String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}
	public Product setFirstName(String firstname){
		this.firstname = firstname;
		return this;
	}
	public Product setCount(int count) {
		this.count = count;
		return this;
	}
	public Product setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
		return this;
	}
	public Product setApiRequestStatus(ProductApiRequestStatus apiRequestStatus) {
		if (this.apiRequestStatus != apiRequestStatus) {
			this.apiRequestStatus = apiRequestStatus;
		}
		return this;
	}
	public Product setApiRequestMessage(String apiRequestMessage) {
		if (!StringUtils.equalsIgnoreCase(this.apiRequestMessage, apiRequestMessage)) {
			this.apiRequestMessage = apiRequestMessage;
		}

		return this;
	}

}
